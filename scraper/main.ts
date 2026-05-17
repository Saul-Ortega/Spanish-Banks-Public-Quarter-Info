import puppeteer from 'puppeteer'
import type { Browser, GoToOptions, LaunchOptions, Page, Viewport } from 'puppeteer';

(async () => {
    console.log("Initiating web scraper...")

    const launchOptions: LaunchOptions = { headless: false, slowMo: 200 };
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
    const optionsValue: string[] = await page.evaluate(() => Array.from(document.querySelector<HTMLSelectElement>(".iasComboBox.iasTextBox.validableValue")?.querySelectorAll<HTMLOptionElement>("option") ?? []).map((option) => option.value));

    for ( const optionValue of optionsValue ) {
        //SELECTS EACH OPTION IN THE DROPDOWN
        await page.select(".iasComboBox.iasTextBox.validableValue", optionValue);

        //CLICKS ON SEARCH BUTTON
        await page.evaluate(() => {
            const buttons: Array<HTMLElement> = Array.from(document.querySelectorAll<HTMLElement>(".ui-button.ui-corner-all.ui-widget.iasWidget.iasButton") ?? []);

            for ( let buttonIndex: number = 0; buttonIndex < buttons.length; buttonIndex++ ) {
                console.log("Button Index", buttonIndex);
                const button: HTMLElement = document.querySelectorAll<HTMLElement>(".ui-button.ui-corner-all.ui-widget.iasWidget.iasButton")[buttonIndex];
                if ( button.querySelector("span")?.innerText === "Buscar" ) {
                    button.click();
                    return;
                }
            }
        });
    }

    await browser.close();
})();
