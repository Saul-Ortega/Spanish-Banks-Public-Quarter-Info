import puppeteer from 'puppeteer'
import type { Browser, ElementHandle, GoToOptions, LaunchOptions, Page, Viewport } from 'puppeteer';

(async () => {
    console.log("Initiating web scraper...");

    const launchOptions: LaunchOptions = { headless: false, slowMo: 200 };
    const goToOptions: GoToOptions = { waitUntil: 'networkidle2' };

    console.log("Launching browser...");
    const browser: Browser = await puppeteer.launch(launchOptions);
    console.log("Adding a new page...");
    const page: Page = await browser.newPage();

    console.log("Going to Spanish Bank web...");
    await page.goto('https://app.bde.es/csfwciu/csfwciuias/xml/Arranque.html', goToOptions);

    const viewport: Viewport = { width: 1080, height: 1024 };
    await page.setViewport(viewport);

    //SEARCHES FOR THE QUARTER PUBLIC INFORMATION LINK AND CLICKS IT
    console.log("Searching quarter public information...");
    await page.evaluate(() => {
        const subMenus: NodeListOf<HTMLSpanElement> = document.querySelectorAll<HTMLSpanElement>(".iasLabel.iasNavegacionSubMenu");

        for ( let i: number = 0; i < subMenus.length; i++ ) {
            if ( subMenus[i].innerText === "Información pública trimestral" ) {
                subMenus[i].click();
                return;
            }
        }
    });
    
    //WAITS UNTIL THE SELECT WITH THE QUARTERS IS AVAILABLE AND CLICKS IT
    console.log("Clicking in quarter select dropdown...");
    await page.waitForSelector(".iasComboBox.iasTextBox.validableValue > option");
    await page.locator(".iasComboBox.iasTextBox.validableValue").click();

    //GET ALL OPTIONS VALUE FROM THE FIRST DROPDOWN
    console.log("Getting all quarters from dropwdown...");
    const optionsValue: string[] = await page.evaluate(() => Array.from(document.querySelector<HTMLSelectElement>("#ComboPeriodos")?.querySelectorAll<HTMLOptionElement>("option") ?? []).map((option) => option.value).filter((value) => value.length > 0));

    for ( const optionValue of optionsValue ) {
        //SELECTS EACH OPTION IN THE DROPDOWN
        console.log(`Selecting quarter option value: ${optionValue}...`);
        await page.waitForSelector("#ComboPeriodos");
        await page.select("#ComboPeriodos", optionValue);

        //CLICKS ON SEARCH BUTTON
        console.log("Clicking on search button...");
        await page.waitForSelector("#Boton0");
        await page.locator("#Boton0").click();

        console.log("Getting next button...");
        let nextButton: ElementHandle | null = await page.$(".botonerasiguienteBtn.ui-button.ui-corner-all.ui-widget.iasWidget.iasButton.ui-state-default.ui-button-text-only.paginationButton");

        let pageNumber: number = 0;

        while ( nextButton ) {
            pageNumber++;

            //WAITS UNTIL THE NEXT TABLE IS LOADED
            await page.waitForNetworkIdle();

            //GETS ALL BANK ROWS
            console.log("Getting all bank table rows...");
            await page.waitForSelector(".ui-widget-content.jqgrow.ui-row-ltr");
            let bankTableRows: Array<ElementHandle> = await page.$$(".ui-widget-content.jqgrow.ui-row-ltr");
    
            for ( let bankRowIndex = 0; bankRowIndex < bankTableRows.length; bankRowIndex++ ) {
                //GETS THE DECLARATION TYPE COLUMN OF THE BANK TABLE ROW
                console.log("Getting bank declaration type...");
                const tableCellDeclarationType: ElementHandle | null = await bankTableRows[bankRowIndex].$("[aria-describedby=TableDataRejillaConPaginacionEnServidorResultadosConsulta_denom_tipo]");
                let declarationType: string = "";
                if ( tableCellDeclarationType ) declarationType = await tableCellDeclarationType.evaluate(td => td.textContent);

                console.log(`Page Number: ${pageNumber}, Table Row: ${1 + bankRowIndex}, Declaration Type: ${declarationType}`);

                //CHECKS IF IS THE QUARTERLY DECLARATION
                if ( declarationType === "TRIMESTRAL" ) {
                    console.log("Clicking on declaration type cell...");
                    await tableCellDeclarationType?.click();

                    //CLICKS ON VIEW DECLARATION
                    console.log("Clicking on view declaration...");
                    await page.waitForSelector("#Boton1");
                    await page.locator("#Boton1").click();
    
                    //CLICKS ON CONTINUE
                    console.log("Clicking on continue button...");
                    await page.waitForSelector("#Boton");
                    await page.locator("#Boton").click();

                    //GETS ALL BUTTONS WITH THE SPECIFIED SELECTOR
                    console.log("Getting all operation buttons...");
                    await page.waitForSelector(".ui-button.ui-corner-all.ui-widget.iasWidget.iasButton");
                    let operationButtons: Array<ElementHandle> = await page.$$(".ui-button.ui-corner-all.ui-widget.iasWidget.iasButton");

                    //ONLY ITERATES OVER THE TOP 3 BUTTONS THAT CORRESPONDS TO THE OPERATIONS
                    for ( let operationButtonIndex = 0; operationButtonIndex < 3; operationButtonIndex++ ) {
                        //CLICKS ON EACH OPERATION BUTTON
                        const operationType: string = await operationButtons[operationButtonIndex].evaluate(operationButton => operationButton.textContent);
                        console.log(`Clicking on operation button: ${operationType}...`);
                        await operationButtons[operationButtonIndex].click();

                        //GETS ALL DETAIL BUTTONS FROM THE OPERATIONS PAGE
                        console.log("Getting all detail buttons...");
                        await page.waitForSelector(".ui-button.ui-corner-all.ui-widget.iasWidget.iasButton");
                        let detailButtons: Array<ElementHandle> = await page.$$(".ui-button.ui-corner-all.ui-widget.iasWidget.iasButton[style*='display: inline']");
    
                        for ( let detailButtonIndex = 0; detailButtonIndex < detailButtons.length; detailButtonIndex++ ) {
                            //CLICKS IN EACH DETAIL BUTTON
                            console.log(`Detail Button Number: ${1 + detailButtonIndex}`);
                            await detailButtons[detailButtonIndex].click();
    
                            //CLICKS IN GO BACK BUTTON FROM DETAILS PAGE
                            console.log("Clicking on go back button from details page...");
                            await page.waitForSelector("#Boton");
                            await page.locator("#Boton").click();

                            //WAITS UNTIL ALL DETAIL BUTTONS ARE LOADED AGAIN
                            console.log("Getting all detail buttons again...");
                            await page.waitForSelector(".ui-button.ui-corner-all.ui-widget.iasWidget.iasButton[style*='display: inline']");
                            detailButtons = await page.$$(".ui-button.ui-corner-all.ui-widget.iasWidget.iasButton[style*='display: inline']");
                        }

                        //WAITS UNTIL OPERATION BUTTONS ARE LOADED AGAIN
                        console.log("Getting operation buttons again...");
                        await page.waitForSelector(".ui-button.ui-corner-all.ui-widget.iasWidget.iasButton");
                        operationButtons = await page.$$(".ui-button.ui-corner-all.ui-widget.iasWidget.iasButton");
                    }

                    //CLICKS ON GO BACK BUTTON FROM C. PASSIVE OPERATIONS
                    console.log("Clicking on go back button...");
                    await page.waitForSelector("#Boton0");
                    await page.locator("#Boton0").click();

                    //WAITS UNTIL THE TABLE IS FULLY LOADED AGAIN
                    console.log("Getting bank table rows again...");
                    await page.waitForSelector(".ui-widget-content.jqgrow.ui-row-ltr");
                    bankTableRows = await page.$$(".ui-widget-content.jqgrow.ui-row-ltr");
                }
            }

            //WHEN ALL INPUT TYPE RADIO FROM BANK ROWS ARE CLICKED, IT CLICKS ON THE NEXT BUTTON IF EXISTS
            console.log("Clicking on nextButton if is visible");
            nextButton = await page.$(".botonerasiguienteBtn.ui-button.ui-corner-all.ui-widget.iasWidget.iasButton.ui-state-default.ui-button-text-only.paginationButton");
            nextButton && await nextButton.isVisible() ? await nextButton.click() : nextButton = null;
        }
    }

    console.log("Closing web scraper...");
    await browser.close();
    console.log("Web scraper closed successfully");
})();
