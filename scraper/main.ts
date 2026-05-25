import puppeteer from 'puppeteer'
import type { Browser, ElementHandle, GoToOptions, LaunchOptions, Page, Viewport } from 'puppeteer';
import process from 'node:process';
import { Bank } from './types/Bank.types';
import { Declaration } from './types/Declaration.types';
import { fetchBankByDenomination, saveBank } from './services/Bank.service';
import { fetchDeclarationByQuarter, saveDeclaration } from './services/Declaration.service';

(async () => {
    process.stdout.write("Initiating web scraper...");
    process.stdout.write("\tDone\n");

    const launchOptions: LaunchOptions = { headless: false, slowMo: 200 };
    const goToOptions: GoToOptions = { waitUntil: 'networkidle2' };

    process.stdout.write("Launching browser...");
    const browser: Browser = await puppeteer.launch(launchOptions);
    process.stdout.write("\tDone\n");
    process.stdout.write("Adding a new page...");
    const page: Page = await browser.newPage();
    process.stdout.write("\tDone\n");

    process.stdout.write("Going to Spanish Bank web...");
    await page.goto('https://app.bde.es/csfwciu/csfwciuias/xml/Arranque.html', goToOptions);
    process.stdout.write("\tDone\n");

    const viewport: Viewport = { width: 1080, height: 1024 };
    await page.setViewport(viewport);

    //SEARCHES FOR THE QUARTER PUBLIC INFORMATION LINK AND CLICKS IT
    process.stdout.write("Searching quarter public information...");
    await page.evaluate(() => {
        const subMenus: NodeListOf<HTMLSpanElement> = document.querySelectorAll<HTMLSpanElement>(".iasLabel.iasNavegacionSubMenu");

        for ( let i: number = 0; i < subMenus.length; i++ ) {
            if ( subMenus[i].innerText === "Información pública trimestral" ) {
                subMenus[i].click();
                return;
            }
        }
    });
    process.stdout.write("\tDone\n");
    
    //WAITS UNTIL THE SELECT WITH THE QUARTERS IS AVAILABLE AND CLICKS IT
    process.stdout.write("Clicking in quarter select dropdown...");
    await page.waitForSelector(".iasComboBox.iasTextBox.validableValue > option");
    await page.locator(".iasComboBox.iasTextBox.validableValue").click();
    process.stdout.write("\tDone\n");

    //GET ALL OPTIONS VALUE FROM THE FIRST DROPDOWN
    process.stdout.write("Getting all quarters from dropwdown...");
    const optionsValue: string[] = await page.evaluate(() => Array.from(document.querySelector<HTMLSelectElement>("#ComboPeriodos")?.querySelectorAll<HTMLOptionElement>("option") ?? []).map((option) => option.value).filter((value) => value.length > 0));
    process.stdout.write("\tDone\n");

    for ( const optionValue of optionsValue ) {
        //SELECTS EACH OPTION IN THE DROPDOWN
        process.stdout.write(`Selecting quarter option value: ${optionValue}...`);
        await page.waitForSelector("#ComboPeriodos");
        await page.select("#ComboPeriodos", optionValue);
        process.stdout.write("\tDone\n");

        //CLICKS ON SEARCH BUTTON
        process.stdout.write("Clicking on search button...");
        await page.waitForSelector("#Boton0");
        await page.locator("#Boton0").click();
        process.stdout.write("\tDone\n");

        process.stdout.write("Getting next button...");
        let nextButton: ElementHandle | null = await page.$(".botonerasiguienteBtn.ui-button.ui-corner-all.ui-widget.iasWidget.iasButton.ui-state-default.ui-button-text-only.paginationButton");
        process.stdout.write("\tDone\n");

        let pageNumber: number = 0;

        while ( nextButton ) {
            pageNumber++;

            //WAITS UNTIL THE NEXT TABLE IS LOADED
            await page.waitForNetworkIdle();

            //GETS ALL BANK ROWS
            process.stdout.write("Getting all bank table rows...");
            await page.waitForSelector(".ui-widget-content.jqgrow.ui-row-ltr");
            let bankTableRows: Array<ElementHandle> = await page.$$(".ui-widget-content.jqgrow.ui-row-ltr");
            process.stdout.write("\tDone\n");
    
            for ( let bankRowIndex = 0; bankRowIndex < bankTableRows.length; bankRowIndex++ ) {
                //GETS THE ENITITY, DENOMINATION AND DECLARATION TYPE COLUMNS OF THE BANK TABLE ROW
                process.stdout.write("Getting bank entity, denomination and declaration type...");
                const tableCellEntity: ElementHandle | null = await bankTableRows[bankRowIndex].$("[aria-describedby=TableDataRejillaConPaginacionEnServidorResultadosConsulta_codigo]");
                const tableCellDenomination: ElementHandle | null = await bankTableRows[bankRowIndex].$("[aria-describedby=TableDataRejillaConPaginacionEnServidorResultadosConsulta_denominacion]");
                const tableCellDeclarationDate: ElementHandle | null = await bankTableRows[bankRowIndex].$("[aria-describedby=TableDataRejillaConPaginacionEnServidorResultadosConsulta_fechaDeclaracion]");
                const tableCellDeclarationType: ElementHandle | null = await bankTableRows[bankRowIndex].$("[aria-describedby=TableDataRejillaConPaginacionEnServidorResultadosConsulta_denom_tipo]");
                let entity: string = "";
                let denomination: string = "";
                let declarationDate: string = "";
                let declarationType: string = "";
                if ( tableCellEntity ) entity = await tableCellEntity.evaluate(td => td.textContent);
                if ( tableCellDenomination ) denomination = await tableCellDenomination.evaluate(td => td.textContent);
                if ( tableCellDeclarationDate ) declarationDate = (await tableCellDeclarationDate.evaluate(td => td.textContent)).split('/').reverse().join('-');
                if ( tableCellDeclarationType ) declarationType = await tableCellDeclarationType.evaluate(td => td.textContent);
                process.stdout.write("\tDone\n");

                process.stdout.write(`Page Number: ${pageNumber}, Table Row: ${1 + bankRowIndex}, Declaration Type: ${declarationType}\n`);

                let bank: Bank = { entity, denomination };

                process.stdout.write("Checking if bank exists in database...");
                const foundBank: Bank | null = await fetchBankByDenomination(bank.denomination);
                process.stdout.write("\tDone\n");

                //CHECKS IF THE BANK DOES NOT EXIST AND SAVES IT IN DATABASE 
                if ( !foundBank ) {
                    process.stdout.write("Saving bank in database...");
                    const savedBank: Bank | null = await saveBank(bank);

                    if ( savedBank ) {
                        bank = savedBank;
                        process.stdout.write("\tSaved\n");
                    } else {
                        process.stdout.write("\tNot Saved\n");
                    }
                } else {
                    bank.id = foundBank.id;
                    process.stdout.write("Bank Already Exists\n");
                }

                //CHECKS IF IS THE QUARTERLY DECLARATION
                if ( declarationType === "TRIMESTRAL" ) {
                    process.stdout.write("Clicking on declaration type cell...");
                    await tableCellDeclarationType?.click();
                    process.stdout.write("\tDone\n");

                    //CLICKS ON VIEW DECLARATION
                    process.stdout.write("Clicking on view declaration...");
                    await page.waitForSelector("#Boton1");
                    await page.locator("#Boton1").click();
                    process.stdout.write("\tDone\n");
    
                    //CLICKS ON CONTINUE
                    process.stdout.write("Clicking on continue button...");
                    await page.waitForSelector("#Boton");
                    await page.locator("#Boton").click();
                    process.stdout.write("\tDone\n");

                    process.stdout.write("Getting published date...");
                    const inputPublishedDate: ElementHandle | null = await page.waitForSelector("#PConsultarPDF_CajaFechaModificacion")
                    let publishedDate: string = "";
                    if ( inputPublishedDate ) publishedDate = (await inputPublishedDate.evaluate(input => (input as HTMLInputElement).value)).split('/').reverse().join('-');
                    process.stdout.write("\tDone\n");

                    let declaration: Declaration = { bankId: bank.id, quarter: optionValue, type: declarationType, declarationDate, publishedDate};

                    process.stdout.write("Checking if declaration exists in database...");
                    let foundDeclaration: Declaration | null = null;
                    if ( declaration.bankId ) {

                        foundDeclaration = await fetchDeclarationByQuarter(declaration.bankId, declaration.quarter);
                    }
                    process.stdout.write("\tDone\n");

                    //CHECKS IF THE DECLARATION DOES NOT EXIST AND SAVES IT IN DATABASE 
                    if ( !foundDeclaration ) {
                        process.stdout.write("Saving declaration in database...");
                        const savedDeclaration: Declaration | null = await saveDeclaration(declaration);

                        if ( savedDeclaration ) {
                            declaration = savedDeclaration;
                            process.stdout.write("\tSaved\n");
                        } else {
                            process.stdout.write("\tNot Saved\n");
                        }
                    } else {
                        declaration.id = foundDeclaration.id;
                        process.stdout.write("Declaration Already Exists\n");
                    }

                    //GETS ALL BUTTONS WITH THE SPECIFIED SELECTOR
                    process.stdout.write("Getting all operation buttons...");
                    await page.waitForSelector(".ui-button.ui-corner-all.ui-widget.iasWidget.iasButton");
                    let operationButtons: Array<ElementHandle> = await page.$$(".ui-button.ui-corner-all.ui-widget.iasWidget.iasButton");
                    process.stdout.write("\tDone\n");

                    //ONLY ITERATES OVER THE TOP 3 BUTTONS THAT CORRESPONDS TO THE OPERATIONS
                    for ( let operationButtonIndex = 0; operationButtonIndex < 3; operationButtonIndex++ ) {
                        //CLICKS ON EACH OPERATION BUTTON
                        const operationType: string = await operationButtons[operationButtonIndex].evaluate(operationButton => operationButton.textContent);
                        process.stdout.write(`Clicking on operation button: ${operationType}...`);
                        await operationButtons[operationButtonIndex].click();
                        process.stdout.write("\tDone\n");

                        //GETS ALL DETAIL BUTTONS FROM THE OPERATIONS PAGE
                        process.stdout.write("Getting all detail buttons...");
                        await page.waitForSelector(".ui-button.ui-corner-all.ui-widget.iasWidget.iasButton");
                        let detailButtons: Array<ElementHandle> = await page.$$(".ui-button.ui-corner-all.ui-widget.iasWidget.iasButton[style*='display: inline']");
                        process.stdout.write("\tDone\n");
    
                        for ( let detailButtonIndex = 0; detailButtonIndex < detailButtons.length; detailButtonIndex++ ) {
                            //CLICKS IN EACH DETAIL BUTTON
                            process.stdout.write(`Detail Button Number: ${1 + detailButtonIndex}`);
                            await detailButtons[detailButtonIndex].click();
                            process.stdout.write("\tDone\n");
    
                            //CLICKS IN GO BACK BUTTON FROM DETAILS PAGE
                            process.stdout.write("Clicking on go back button from details page...");
                            await page.waitForSelector("#Boton");
                            await page.locator("#Boton").click();
                            process.stdout.write("\tDone\n");

                            //WAITS UNTIL ALL DETAIL BUTTONS ARE LOADED AGAIN
                            process.stdout.write("Getting all detail buttons again...");
                            await page.waitForSelector(".ui-button.ui-corner-all.ui-widget.iasWidget.iasButton[style*='display: inline']");
                            detailButtons = await page.$$(".ui-button.ui-corner-all.ui-widget.iasWidget.iasButton[style*='display: inline']");
                            process.stdout.write("\tDone\n");
                        }

                        //WAITS UNTIL OPERATION BUTTONS ARE LOADED AGAIN
                        process.stdout.write("Getting operation buttons again...");
                        await page.waitForSelector(".ui-button.ui-corner-all.ui-widget.iasWidget.iasButton");
                        operationButtons = await page.$$(".ui-button.ui-corner-all.ui-widget.iasWidget.iasButton");
                        process.stdout.write("\tDone\n");
                    }

                    //CLICKS ON GO BACK BUTTON FROM C. PASSIVE OPERATIONS
                    process.stdout.write("Clicking on go back button...");
                    await page.waitForSelector("#Boton0");
                    await page.locator("#Boton0").click();
                    process.stdout.write("\tDone\n");

                    //WAITS UNTIL THE TABLE IS FULLY LOADED AGAIN
                    process.stdout.write("Getting bank table rows again...");
                    await page.waitForSelector(".ui-widget-content.jqgrow.ui-row-ltr");
                    bankTableRows = await page.$$(".ui-widget-content.jqgrow.ui-row-ltr");
                    process.stdout.write("\tDone\n");
                }
            }

            //WHEN ALL INPUT TYPE RADIO FROM BANK ROWS ARE CLICKED, IT CLICKS ON THE NEXT BUTTON IF EXISTS
            process.stdout.write("Clicking on nextButton if is visible...");
            nextButton = await page.$(".botonerasiguienteBtn.ui-button.ui-corner-all.ui-widget.iasWidget.iasButton.ui-state-default.ui-button-text-only.paginationButton");
            nextButton && await nextButton.isVisible() ? await nextButton.click() : nextButton = null;
            process.stdout.write("\tDone\n");
        }
    }

    process.stdout.write("Closing web scraper...");
    await browser.close();
    process.stdout.write("\tDone\n");
})();
