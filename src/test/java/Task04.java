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
        CheckCampaingElements(chromeDriver = new ChromeDriver());
        CheckCampaingElements(firefoxDriver = new FirefoxDriver());
        CheckCampaingElements(iExplorerDriver = new InternetExplorerDriver());
    }

    public void CheckCampaingElements(WebDriver browserDriver) {

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

        Assert.assertEquals(productNameMain, productNamePage);
        Assert.assertEquals(salePriceMain, salePricePage);
        Assert.assertEquals(regularPriceMain, regularPricePage);
        Assert.assertEquals(salePriceColorMain, salePriceColorPage);
        if (browserDriver == firefoxDriver) {
            Assert.assertEquals(regularPriceColorMain, "rgb(119, 119, 119)");
            Assert.assertEquals(regularPriceColorPage, "rgb(102, 102, 102)");
        } else {
            Assert.assertEquals(regularPriceColorMain, "rgba(119, 119, 119, 1)");
            Assert.assertEquals(regularPriceColorPage, "rgba(102, 102, 102, 1)");
        }
        browserDriver.quit();

    }








}
