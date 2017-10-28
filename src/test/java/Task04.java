import io.github.bonigarcia.wdm.ChromeDriverManager;
import io.github.bonigarcia.wdm.FirefoxDriverManager;
import io.github.bonigarcia.wdm.InternetExplorerDriverManager;
import org.junit.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;

import java.util.concurrent.TimeUnit;


public class Task04{
    public static WebDriver chromeDriver;
    public static WebDriver firefoxDriver;
    public static WebDriver iExplorerDriver;

    @BeforeClass
    public static void start(){
        ChromeDriverManager.getInstance().setup(); // Chrome 62.0
        FirefoxDriverManager.getInstance().setup(); // FF 56.0
        InternetExplorerDriverManager.getInstance().arch32().setup(); //IE 11.0
    }

    @AfterClass
    public static void stop(){
        iExplorerDriver = null;
        firefoxDriver = null;
        chromeDriver = null;
    }

    @Test
    public void CheckCampaignElementsInChromeBrowser(){
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("start-maximized");
        chromeDriver = new ChromeDriver(chromeOptions);
        chromeDriver.get("http://localhost/litecart");
        WebElement productNameMainLocator = chromeDriver.findElement(By.cssSelector("div#box-campaigns .name"));
        String productNameMain = productNameMainLocator.getAttribute("textContent");
        WebElement salePriceLocator = chromeDriver.findElement(By.cssSelector("div#box-campaigns .campaign-price"));
        String salePriceMain = salePriceLocator.getAttribute("textContent");
        String salePriceColorMain = salePriceLocator.getCssValue("color");
        WebElement regularPriceMainLocator = chromeDriver.findElement(By.cssSelector("div#box-campaigns .regular-price"));
        String regularPriceMain = regularPriceMainLocator.getAttribute("textContent");
        String regularPriceColorMain = regularPriceMainLocator.getCssValue("color");
        String regularPriceTextStrikeMain = regularPriceMainLocator.getCssValue("text-decoration");
        productNameMainLocator.click();
        chromeDriver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        WebElement productNameLocatorPage = chromeDriver.findElement(By.cssSelector(".content h1"));
        String productNamePage = productNameLocatorPage.getAttribute("textContent");
        WebElement salePriceLocatorPage = chromeDriver.findElement(By.cssSelector(".campaign-price "));
        String salePricePage = salePriceLocatorPage.getAttribute("textContent");
        String salePriceColorPage = salePriceLocatorPage.getCssValue("color");
        WebElement regularPriceLocatorPage = chromeDriver.findElement(By.cssSelector(".regular-price"));
        String regularPricePage = regularPriceLocatorPage.getAttribute("textContent");
        String regularPriceColorPage = regularPriceLocatorPage.getCssValue("color");
        String regularPriceTextStrikePage = regularPriceLocatorPage.getCssValue("text-decoration");

        Assert.assertEquals("Product Name on the main page differs from Product Name on the product page.", productNameMain, productNamePage);
        Assert.assertEquals("Sale Price on the main page differs from Sale Price on the product page.", salePriceMain, salePricePage);
        Assert.assertEquals("Regular Price on the main page differs from Regular Price on the product page.", regularPriceMain, regularPricePage);
        Assert.assertEquals("Sale Price color on the main page differs from Sale Price color on the product page.", salePriceColorMain, salePriceColorPage);
        Assert.assertEquals("Regular Price color on the Main page differs from expected.", regularPriceColorMain, "rgba(119, 119, 119, 1)");
        Assert.assertEquals("Regular Price color on the Product page differs from expected.", regularPriceColorPage, "rgba(102, 102, 102, 1)");
        Assert.assertEquals("Regular Price strike on the Main page differs from expected.", regularPriceTextStrikeMain, "line-through solid rgb(119, 119, 119)");
        Assert.assertEquals("Regular Price strike on the Product page differs from expected.", regularPriceTextStrikePage, "line-through solid rgb(102, 102, 102)");
        chromeDriver.quit();
    }

    @Test
    public void CheckCampaignElementsInIexplorerBrowser(){
        iExplorerDriver = new InternetExplorerDriver();
        iExplorerDriver.get("http://localhost/litecart");
        WebElement productNameMainLocator = iExplorerDriver.findElement(By.cssSelector("div#box-campaigns .name"));
        String productNameMain = productNameMainLocator.getAttribute("textContent");
        WebElement salePriceLocator = iExplorerDriver.findElement(By.cssSelector("div#box-campaigns .campaign-price"));
        String salePriceMain = salePriceLocator.getAttribute("textContent");
        String salePriceColorMain = salePriceLocator.getCssValue("color");
        WebElement regularPriceMainLocator = iExplorerDriver.findElement(By.cssSelector("div#box-campaigns .regular-price"));
        String regularPriceMain = regularPriceMainLocator.getAttribute("textContent");
        String regularPriceColorMain = regularPriceMainLocator.getCssValue("color");
        String regularPriceTextStrikeMain = regularPriceMainLocator.getCssValue("text-decoration");
        productNameMainLocator.click();
        iExplorerDriver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        WebElement productNameLocatorPage = iExplorerDriver.findElement(By.cssSelector(".content h1"));
        String productNamePage = productNameLocatorPage.getAttribute("textContent");
        WebElement salePriceLocatorPage = iExplorerDriver.findElement(By.cssSelector(".campaign-price "));
        String salePricePage = salePriceLocatorPage.getAttribute("textContent");
        String salePriceColorPage = salePriceLocatorPage.getCssValue("color");
        WebElement regularPriceLocatorPage = iExplorerDriver.findElement(By.cssSelector(".regular-price"));
        String regularPricePage = regularPriceLocatorPage.getAttribute("textContent");
        String regularPriceColorPage = regularPriceLocatorPage.getCssValue("color");
        String regularPriceTextStrikePage = regularPriceLocatorPage.getCssValue("text-decoration");

        Assert.assertEquals("Product Name on the main page differs from Product Name on the product page.", productNameMain, productNamePage);
        Assert.assertEquals("Sale Price on the main page differs from Sale Price on the product page.", salePriceMain, salePricePage);
        Assert.assertEquals("Regular Price on the main page differs from Regular Price on the product page.", regularPriceMain, regularPricePage);
        Assert.assertEquals("Sale Price color on the main page differs from Sale Price color on the product page.", salePriceColorMain, salePriceColorPage);
        Assert.assertEquals("Regular Price color on the Main page differs from expected.",  "rgba(119, 119, 119, 1)", regularPriceColorMain);
        Assert.assertEquals("Regular Price color on the Product page differs from expected.", "rgba(102, 102, 102, 1)", regularPriceColorPage);
        Assert.assertEquals("Regular Price strike on the Product page differs from expected.", regularPriceTextStrikeMain, regularPriceTextStrikePage);
        iExplorerDriver.quit();
    }

    @Test
    public void CheckCampaignElementsInFirefoxBrowser() {
        FirefoxOptions firefoxOptions = new FirefoxOptions();
        firefoxOptions.addArguments("start-maximized");
        firefoxDriver = new FirefoxDriver(firefoxOptions);
        firefoxDriver.get("http://localhost/litecart");
        WebElement productNameMainLocator = firefoxDriver.findElement(By.cssSelector("div#box-campaigns .name"));
        String productNameMain = productNameMainLocator.getAttribute("textContent");
        WebElement salePriceLocator = firefoxDriver.findElement(By.cssSelector("div#box-campaigns .campaign-price"));
        String salePriceMain = salePriceLocator.getAttribute("textContent");
        String salePriceColorMain = salePriceLocator.getCssValue("color");
        WebElement regularPriceMainLocator = firefoxDriver.findElement(By.cssSelector("div#box-campaigns .regular-price"));
        String regularPriceMain = regularPriceMainLocator.getAttribute("textContent");
        String regularPriceColorMain = regularPriceMainLocator.getCssValue("color");
        String regularPriceTextStrikeMain = regularPriceMainLocator.getCssValue("text-decoration");
        productNameMainLocator.click();
        firefoxDriver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        WebElement productNameLocatorPage = firefoxDriver.findElement(By.cssSelector(".content h1"));
        String productNamePage = productNameLocatorPage.getAttribute("textContent");
        WebElement salePriceLocatorPage = firefoxDriver.findElement(By.cssSelector(".campaign-price "));
        String salePricePage = salePriceLocatorPage.getAttribute("textContent");
        String salePriceColorPage = salePriceLocatorPage.getCssValue("color");
        WebElement regularPriceLocatorPage = firefoxDriver.findElement(By.cssSelector(".regular-price"));
        String regularPricePage = regularPriceLocatorPage.getAttribute("textContent");
        String regularPriceColorPage = regularPriceLocatorPage.getCssValue("color");
        String regularPriceTextStrikePage = regularPriceLocatorPage.getCssValue("text-decoration");
        firefoxDriver.quit();

        Assert.assertEquals("Product Name on the main page differs from Product Name on the product page.", productNameMain, productNamePage);
        Assert.assertEquals("Sale Price on the main page differs from Sale Price on the product page.", salePriceMain, salePricePage);
        Assert.assertEquals("Regular Price on the main page differs from Regular Price on the product page.", regularPriceMain, regularPricePage);
        Assert.assertEquals("Sale Price color on the main page differs from Sale Price color on the product page.", salePriceColorMain, salePriceColorPage);
        Assert.assertEquals("Regular Price color on the Main page differs from expected.", "rgb(119, 119, 119)", regularPriceColorMain);
        Assert.assertEquals("Regular Price color on the Product page differs from expected.", "rgb(102, 102, 102)", regularPriceColorPage );
        Assert.assertEquals("Regular Price strike on the Main page differs from expected.", regularPriceTextStrikeMain, regularPriceTextStrikePage);
    }
}
