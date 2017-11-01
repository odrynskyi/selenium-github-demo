
import io.github.bonigarcia.wdm.ChromeDriverManager;
import org.junit.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.Select;


import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class Task05 {

    public static WebDriver chromeDriver;
    public static String productDescription = "Duck is the common name for a large number of species in the waterfowl family Anatidae, " +
            "which also includes swans and geese. Ducks are divided among several subfamilies in the family Anatidae.";
    public static boolean isElementPresent(By locator) {
        return chromeDriver.findElements(locator).size() > 0;
    }
    @BeforeClass
    public static void start(){
        //ChromeDriverManager.getInstance().setup();
        //ChromeOptions chromeOptions = new ChromeOptions();
        //chromeOptions.addArguments("start-maximized");
        chromeDriver = new ChromeDriver();
        chromeDriver.get("http://localhost/litecart/admin");
        if(isElementPresent(By.name("login"))){
            chromeDriver.findElement(By.name("username")).sendKeys("admin");
            chromeDriver.findElement(By.name("password")).sendKeys("admin");
            chromeDriver.findElement(By.name("login")).click();
        }
        chromeDriver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
    }

    @AfterClass
    public static void stop(){
        chromeDriver.quit();
        chromeDriver = null;
    }

    @Test
    public void CheckNewProductInCatalog(){
        String productId = ProductId();
        chromeDriver.findElement(By.cssSelector("li#app-:nth-child(2)")).click();
        chromeDriver.findElement(By.cssSelector("td#content div a:nth-child(2)")).click();
        //New Product>>General
        chromeDriver.findElement(By.cssSelector("input[type=radio]")).click();
        chromeDriver.findElement(By.name("name[en]")).sendKeys(productId);
        chromeDriver.findElement(By.name("code")).sendKeys("1001");
        chromeDriver.findElement(By.cssSelector("input[value='1-3']")).click();
        chromeDriver.findElement(By.name("quantity")).clear();
        chromeDriver.findElement(By.name("quantity")).sendKeys("5");
        chromeDriver.findElement(By.name("date_valid_from")).sendKeys(Keys.HOME + "10/31/2017");
        chromeDriver.findElement(By.name("date_valid_to")).sendKeys(Keys.HOME + "11/30/2018");
        //Add image
        WebElement fileField = chromeDriver.findElement(By.name("new_images[]"));
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("gold_duck.jpg").getFile());
        fileField.sendKeys(file.getAbsolutePath());
        chromeDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        //Information tab
        chromeDriver.findElement(By.cssSelector("a[href='#tab-information']")).click();
        Select manufacturer = new Select (chromeDriver.findElement(By.name("manufacturer_id")));
        manufacturer.selectByValue("1");
        chromeDriver.findElement(By.name("keywords")).sendKeys("Gold Duck, Duck");
        chromeDriver.findElement(By.name("short_description[en]")).sendKeys("My love Gold Duck");
        chromeDriver.findElement(By.className("trumbowyg-editor")).sendKeys(productDescription);
        chromeDriver.findElement(By.name("head_title[en]")).sendKeys("Gold Duck");
        chromeDriver.findElement(By.name("meta_description[en]")).sendKeys("My love Gold Duck");
        //Prices tab
        chromeDriver.findElement(By.cssSelector("a[href='#tab-prices']")).click();
        chromeDriver.findElement(By.name("purchase_price")).clear();
        chromeDriver.findElement(By.name("purchase_price")).sendKeys("29.99");
        Select purchaseCurrencyCode = new Select (chromeDriver.findElement(By.name("purchase_price_currency_code")));
        purchaseCurrencyCode.selectByValue("USD");
        chromeDriver.findElement(By.name("prices[USD]")).sendKeys("29.99");
        chromeDriver.findElement(By.name("prices[EUR]")).sendKeys("24.99");
        chromeDriver.findElement(By.name("save")).click();

        //Third variant to check that the product is appeared in the catalog.
        Assert.assertTrue(chromeDriver.findElement(By.xpath("//*[contains(text(), '"+productId+"')]")).isDisplayed());

        /* Second variant to check that the product is appeared in the catalog.
        WebElement newProduct = chromeDriver.findElement(By.cssSelector("tr.row:nth-child(4) > td:nth-child(3) a"));
        Assert.assertEquals("Message", "Gold Duck", newProduct.getText());
        */

        //First variant to check that the product is appeared in the catalog.
        //List<WebElement> list = chromeDriver.findElements(By.cssSelector("tr.row"));
        //ArrayList b = new ArrayList();
        //for (int i=0; i<list.size(); i++) {
        //    String a = list.get(i).getText();
        //    b.add(a);
        //}
        //Assert.assertTrue(b.contains("Gold Duck"));
    }
    public String ProductId(){
        String uniqueId = UUID.randomUUID().toString().substring(0, 3);
        String productId = "Gold Duck_" + uniqueId;
        return productId;
    }
}
