import io.github.bonigarcia.wdm.ChromeDriverManager;
import org.junit.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class Task05 {

    public static WebDriver driver;
    private static WebDriverWait wait;
    private static String productDescription = "Duck is the common name for a large number of species in the waterfowl family Anatidae, " +
            "which also includes swans and geese. Ducks are divided among several subfamilies in the family Anatidae.";
    private static boolean isElementPresent(By locator) {
        return driver.findElements(locator).size() > 0;
    }

    @BeforeClass
    public static void start(){
        ChromeDriverManager.getInstance().setup();
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("start-maximized");
        driver = new ChromeDriver(chromeOptions);
        wait = new WebDriverWait(driver, 10);
        driver.get("http://localhost/litecart/admin");
        if(isElementPresent(By.name("login"))){
            driver.findElement(By.name("username")).sendKeys("admin");
            driver.findElement(By.name("password")).sendKeys("admin");
            driver.findElement(By.name("login")).click();
        }
        wait.until((WebDriver d) -> d.findElement(By.cssSelector("#sidebar")));
    }

    @AfterClass
    public static void stop(){
        driver.quit();
        driver = null;
    }

    @Test
    public void CheckNewProductInCatalog(){
        String productId = ProductId();
        driver.findElement(By.cssSelector("li#app-:nth-child(2)")).click();
        driver.findElement(By.cssSelector("td#content div a:nth-child(2)")).click();

        //New Product >> General tab
        driver.findElement(By.cssSelector("input[type=radio]")).click();
        driver.findElement(By.name("name[en]")).sendKeys(productId);
        driver.findElement(By.name("code")).sendKeys("1001");
        driver.findElement(By.cssSelector("input[value='1-3']")).click();
        driver.findElement(By.name("quantity")).clear();
        driver.findElement(By.name("quantity")).sendKeys("5");
        driver.findElement(By.name("date_valid_from")).sendKeys(Keys.HOME + "10/31/2017");
        driver.findElement(By.name("date_valid_to")).sendKeys(Keys.HOME + "11/30/2018");
        //Add image
        addNewImage();

        //Information tab
        driver.findElement(By.cssSelector("a[href='#tab-information']")).click();
        wait.until((WebDriver d) -> d.findElement(By.cssSelector("select[name='manufacturer_id']")));
        Select manufacturer = new Select (driver.findElement(By.name("manufacturer_id")));
        manufacturer.selectByValue("1");
        driver.findElement(By.name("keywords")).sendKeys("Gold Duck, Duck");
        driver.findElement(By.name("short_description[en]")).sendKeys("My love Gold Duck");
        driver.findElement(By.className("trumbowyg-editor")).sendKeys(productDescription);
        driver.findElement(By.name("head_title[en]")).sendKeys("Gold Duck");
        driver.findElement(By.name("meta_description[en]")).sendKeys("My love Gold Duck");

        //Prices tab
        driver.findElement(By.cssSelector("a[href='#tab-prices']")).click();
        driver.findElement(By.name("purchase_price")).clear();
        driver.findElement(By.name("purchase_price")).sendKeys("29.99");
        Select purchaseCurrencyCode = new Select (driver.findElement(By.name("purchase_price_currency_code")));
        purchaseCurrencyCode.selectByValue("USD");
        driver.findElement(By.name("prices[USD]")).sendKeys("29.99");
        driver.findElement(By.name("prices[EUR]")).sendKeys("24.99");
        driver.findElement(By.name("save")).click();

        //Second variant to check that the product is appeared in the catalog.
        Assert.assertTrue(driver.findElement(By.xpath("//*[contains(text(), '"+productId+"')]")).isDisplayed());

//        First variant to check that the product is appeared in the catalog.
//        List<WebElement> list = chromeDriver.findElements(By.cssSelector("tr.row"));
//        ArrayList productList = new ArrayList();
//        for (int i=0; i<list.size(); i++) {
//            String a = list.get(i).getText();
//            productList.add(a);
//        }
//        Assert.assertTrue(productList.contains("Gold Duck"));
    }

    //Auxilary method
    private String ProductId(){
        String uniqueId = UUID.randomUUID().toString().substring(0, 4);
        return "Gold Duck_" + uniqueId;
    }

    private void addNewImage(){
        WebElement fileField = driver.findElement(By.name("new_images[]"));
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("gold_duck.jpg").getFile());
        fileField.sendKeys(file.getAbsolutePath());
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }
}
