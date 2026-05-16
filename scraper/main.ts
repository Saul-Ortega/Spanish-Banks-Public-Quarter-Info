import puppeteer from 'puppeteer'
import type { Browser, GoToOptions, LaunchOptions, Page, Viewport } from 'puppeteer';

(async () => {
    console.log("Initiating web scraper...")

    const launchOptions: LaunchOptions = { headless: false };
    const goToOptions: GoToOptions = { waitUntil: 'networkidle2' };

    const browser: Browser = await puppeteer.launch(launchOptions);
    const page: Page = await browser.newPage();

    await page.goto('https://app.bde.es/csfwciu/csfwciuias/xml/Arranque.html', goToOptions);

    const viewport: Viewport = { width: 1080, height: 1024 };
    await page.setViewport(viewport);

    //SEARCHES FOR THE QUARTER PUBLIC INFORMATION LINK AND CLICKS IT
    await page.evaluate(() => {
        const subMenus: NodeListOf<HTMLElement> = document.querySelectorAll(".iasLabel.iasNavegacionSubMenu");

        for ( let i: number = 0; i < subMenus.length; i++ ) {
            if ( subMenus[i].innerText === "Información pública trimestral" ) {
                subMenus[i].click();
                return;
            }
        }
    });
    
    //CLICKS IN THE QUARTER OPTIONS SELECT
    await page.locator(".iasComboBox.iasTextBox.validableValue").click();

    await page.waitForSelector(".iasComboBox.iasTextBox.validableValue");

    await page.evaluate(() => {
        const quarterOptions: NodeListOf<HTMLElement> = document.querySelectorAll<HTMLElement>(".iasComboBox.iasTextBox.validableValue > option");

        for ( let i: number = 0; i < quarterOptions.length; i++ ) {
            console.log(quarterOptions[i].innerText);
        }
    });



    await browser.close();
})();
