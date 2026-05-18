import puppeteer from 'puppeteer'
import type { Browser, ElementHandle, GoToOptions, LaunchOptions, Page, Viewport } from 'puppeteer';

(async () => {
    console.log("Initiating web scraper...")

    const launchOptions: LaunchOptions = { headless: false, slowMo: 50 };
    const goToOptions: GoToOptions = { waitUntil: 'networkidle2' };

    const browser: Browser = await puppeteer.launch(launchOptions);
    const page: Page = await browser.newPage();

    await page.goto('https://app.bde.es/csfwciu/csfwciuias/xml/Arranque.html', goToOptions);

    const viewport: Viewport = { width: 1080, height: 1024 };
    await page.setViewport(viewport);

    //SEARCHES FOR THE QUARTER PUBLIC INFORMATION LINK AND CLICKS IT
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
    await page.waitForSelector(".iasComboBox.iasTextBox.validableValue > option");
    await page.locator(".iasComboBox.iasTextBox.validableValue").click();

    //GET ALL OPTIONS VALUE FROM THE FIRST DROPDOWN
    const optionsValue: string[] = await page.evaluate(() => Array.from(document.querySelector<HTMLSelectElement>("#ComboPeriodos")?.querySelectorAll<HTMLOptionElement>("option") ?? []).map((option) => option.value).filter((value) => value.length > 0));

    for ( const optionValue of optionsValue ) {
        //SELECTS EACH OPTION IN THE DROPDOWN
        await page.select("#ComboPeriodos", optionValue);

        //CLICKS ON SEARCH BUTTON
        await page.locator("#Boton0").filter(button => button.textContent === "Buscar").click();

        let nextButton: ElementHandle | null = await page.$(".botonerasiguienteBtn.ui-button.ui-corner-all.ui-widget.iasWidget.iasButton.ui-state-default.ui-button-text-only.paginationButton");

        while ( nextButton ) {
            //WAITS UNTIL THE NEXT TABLE IS LOADED
            await page.waitForNetworkIdle();

            //SELECTS EACH INPUT TYPE RADIO BANK ROW
            await page.waitForSelector(".ui-widget-content.jqgrow.ui-row-ltr");
            const bankTableRows: Array<ElementHandle> = await page.$$(".ui-widget-content.jqgrow.ui-row-ltr");
    
            for ( const bankTableRow of bankTableRows ) {
                const td = await bankTableRow.$("td");
                if ( td ) await td.click();
            }

            //WHEN ALL INPUT TYPE RADIO FROM BANK ROWS ARE CLICKED, IT CLICKS ON THE NEXT BUTTON IF EXISTS
            nextButton = await page.$(".botonerasiguienteBtn.ui-button.ui-corner-all.ui-widget.iasWidget.iasButton.ui-state-default.ui-button-text-only.paginationButton");
            nextButton && await nextButton.isVisible() ? await nextButton.click() : nextButton = null;
        }
    }

    console.log("Closing web scraper...");
    await browser.close();
    console.log("Web scraper closed successfully");
})();
