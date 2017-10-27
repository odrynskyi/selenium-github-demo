import io.github.bonigarcia.wdm.ChromeDriverManager;
import io.github.bonigarcia.wdm.FirefoxDriverManager;
import io.github.bonigarcia.wdm.InternetExplorerDriverManager;
import org.junit.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import java.util.concurrent.TimeUnit;


public class Task04 {
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
    public void CampaignElementsInAllBrowsers() {
        CheckCampaignElements(chromeDriver = new ChromeDriver());
        CheckCampaignElements(firefoxDriver = new FirefoxDriver());
        CheckCampaignElements(iExplorerDriver = new InternetExplorerDriver());
    }

    public void CheckCampaignElements(WebDriver browserDriver) {

        browserDriver.get("http://localhost/litecart");
        WebElement productNameMainLocator = browserDriver.findElement(By.cssSelector("div#box-campaigns .name"));
        String productNameMain = productNameMainLocator.getAttribute("textContent");
        WebElement salePriceLocator = browserDriver.findElement(By.cssSelector("div#box-campaigns .campaign-price "));
        String salePriceMain = salePriceLocator.getAttribute("textContent");
        String salePriceColorMain = salePriceLocator.getCssValue("color");
        WebElement regularPriceMainLocator = browserDriver.findElement(By.cssSelector("div#box-campaigns .regular-price "));
        String regularPriceMain = regularPriceMainLocator.getAttribute("textContent");
        String regularPriceColorMain = regularPriceMainLocator.getCssValue("color");
        productNameMainLocator.click();
        browserDriver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        WebElement productNameLocatorPage = browserDriver.findElement(By.cssSelector(".content h1"));
        String productNamePage = productNameLocatorPage.getAttribute("textContent");
        WebElement salePriceLocatorPage = browserDriver.findElement(By.cssSelector(".campaign-price "));
        String salePricePage = salePriceLocatorPage.getAttribute("textContent");
        String salePriceColorPage = salePriceLocatorPage.getCssValue("color");
        WebElement regularPriceLocatorPage = browserDriver.findElement(By.cssSelector(".regular-price "));
        String regularPricePage = regularPriceLocatorPage.getAttribute("textContent");
        String regularPriceColorPage = regularPriceLocatorPage.getCssValue("color");

        Assert.assertEquals("Product Name on the main page differs from Product Name on the product page.", productNameMain, productNamePage);
        Assert.assertEquals("Sale Price on the main page differs from Sale Price on the product page.", salePriceMain, salePricePage);
        Assert.assertEquals("Regular Price on the main page differs from Regular Price on the product page.", regularPriceMain, regularPricePage);
        Assert.assertEquals("Sale Price color on the main page differs from Sale Price color on the product page.", salePriceColorMain, salePriceColorPage);
        if (browserDriver == firefoxDriver) {
            Assert.assertEquals("Regular Price color on the main page differs from expected.", regularPriceColorMain, "rgb(119, 119, 119)");
            Assert.assertEquals("Regular Price color on the Product page differs from expected.", regularPriceColorPage, "rgb(102, 102, 102)");
        } else {
            Assert.assertEquals("Regular Price color on the Main page differs from expected.", regularPriceColorMain, "rgba(119, 119, 119, 1)");
            Assert.assertEquals("Regular Price color on the Product page differs from expected.", regularPriceColorPage, "rgba(102, 102, 102, 1)");
        }
        browserDriver.quit();

    }








}
