import io.github.bonigarcia.wdm.ChromeDriverManager;
import io.github.bonigarcia.wdm.FirefoxDriverManager;
import io.github.bonigarcia.wdm.InternetExplorerDriverManager;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import java.util.concurrent.TimeUnit;

@RunWith(Parameterized.class)
public class Task04{

    @Parameterized.Parameter
    public static WebDriver driver;
//    public static WebDriver firefoxDriver;
//    public static WebDriver iExplorerDriver;
    String baseURL = "http://localhost/litecart";

    @Before
    public void start(){
        driver.get("http://localhost/litecart/en/");
    }

    @AfterClass
    public static void stop(){
        driver.quit();
    }

    @Test
    public void CheckProductName(){
        WebElement productNameMainLocator = driver.findElement(By.cssSelector("div#box-campaigns .name"));
        String productNameMain = productNameMainLocator.getAttribute("textContent");
        productNameMainLocator.click();
        WebElement productNameLocatorPage = driver.findElement(By.cssSelector(".content h1"));
        String productNamePage = productNameLocatorPage.getAttribute("textContent");

        Assert.assertEquals("Product Name on the main page differs from Product Name on the product page.", productNameMain, productNamePage);
    }

    @Test
    public void CheckDiscountPrice() {
        WebElement salePriceLocator = driver.findElement(By.cssSelector("div#box-campaigns .campaign-price"));
        String salePriceMain = salePriceLocator.getAttribute("textContent");
        ClickCampaignProduct();
        WebElement salePriceLocatorPage = driver.findElement(By.cssSelector(".campaign-price "));
        String salePricePage = salePriceLocatorPage.getAttribute("textContent");

        Assert.assertEquals("Sale Price on the main page differs from Sale Price on the product page.", salePriceMain, salePricePage);
    }

    @Test
    public void CheckRegularPriceColor() {
        WebElement regularPriceMainLocator = driver.findElement(By.cssSelector("div#box-campaigns .regular-price"));
        String regularPriceColorMain = regularPriceMainLocator.getCssValue("color");
        ClickCampaignProduct();
        WebElement regularPriceLocatorPage = driver.findElement(By.cssSelector(".regular-price"));
        String regularPriceColorPage = regularPriceLocatorPage.getCssValue("color");
        if(driver instanceof FirefoxDriver){
            Assert.assertEquals("Regular Price color on the Main page differs from expected.", "rgb(119, 119, 119)", regularPriceColorMain);
            Assert.assertEquals("Regular Price color on the Product page differs from expected.", "rgb(102, 102, 102)", regularPriceColorPage );
        } else {
            Assert.assertEquals("Regular Price color on the Main page differs from expected.", "rgba(119, 119, 119, 1)", regularPriceColorMain);
            Assert.assertEquals("Regular Price color on the Product page differs from expected.", "rgba(102, 102, 102, 1)", regularPriceColorPage);
        }
    }

    @Test
    public void CheckRegularPriceStrike() {
        WebElement regularPriceMainLocator = driver.findElement(By.cssSelector("div#box-campaigns .regular-price"));
        String regularPriceTextStrikeMain = regularPriceMainLocator.getCssValue("text-decoration");
        ClickCampaignProduct();
        WebElement regularPriceLocatorPage = driver.findElement(By.cssSelector(".regular-price"));
        String regularPriceTextStrikePage = regularPriceLocatorPage.getCssValue("text-decoration");
        if(driver instanceof ChromeDriver){
            Assert.assertEquals("Regular Price strike on the Main page differs from expected.", "line-through solid rgb(119, 119, 119)", regularPriceTextStrikeMain);
            Assert.assertEquals("Regular Price strike on the Main page differs from expected.", "line-through solid rgb(102, 102, 102)", regularPriceTextStrikePage);
        }else{
        Assert.assertEquals("Regular Price strike on the Main page differs from expected.", regularPriceTextStrikeMain, regularPriceTextStrikePage);}
    }

    @Test
    public void CheckDiscountPriceColor() {
        WebElement salePriceLocator = driver.findElement(By.cssSelector("div#box-campaigns .campaign-price"));
        String salePriceColorMain = salePriceLocator.getCssValue("color");
        ClickCampaignProduct();
        WebElement salePriceLocatorPage = driver.findElement(By.cssSelector(".campaign-price "));
        String salePriceColorPage = salePriceLocatorPage.getCssValue("color");

        Assert.assertEquals("Sale Price color on the main page differs from Sale Price color on the product page.", salePriceColorMain, salePriceColorPage);
    }

    @Parameterized.Parameters
    public static WebDriver[] getDriver(){
        ChromeDriverManager.getInstance().setup();
        FirefoxDriverManager.getInstance().setup();
        InternetExplorerDriverManager.getInstance().arch32().setup();

        FirefoxOptions fopt = new FirefoxOptions();
        fopt.setCapability(FirefoxDriver.MARIONETTE, false);
        return new WebDriver[]{new FirefoxDriver(), new ChromeDriver(), new InternetExplorerDriver()};
    }

    private void ClickCampaignProduct(){
        WebElement productNameMainLocator = driver.findElement(By.cssSelector("div#box-campaigns .name"));
        productNameMainLocator.click();
    }
}