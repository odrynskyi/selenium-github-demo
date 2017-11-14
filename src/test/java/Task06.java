import io.github.bonigarcia.wdm.ChromeDriverManager;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class Task06 {
    public static WebDriver driver;
    private static WebDriverWait wait;

    @BeforeClass
    public static void start(){
        ChromeDriverManager.getInstance().setup();
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("start-maximized");
        driver = new ChromeDriver(chromeOptions);
        wait = new WebDriverWait(driver, 10);
        driver.get("http://localhost/litecart");
        wait.until((WebDriver d) -> d.findElement(By.cssSelector("img[title='My Store']")));
    }

    @AfterClass
    public static void stop(){
        driver.quit();
        driver = null;
    }

    @Test
    public void AddAndRemoveProducts(){
        WebDriverWait wait = new WebDriverWait(driver,10);

        for (int i = 0; i < 4; i++){
            driver.findElement(By.cssSelector("div#box-most-popular div ul li:nth-child(" + (i+1) +")")).click();

            if (driver.findElements(By.cssSelector("select[name='options[Size]']")).size() > 0) {
            Select purchaseCurrencyCode = new Select (driver.findElement(By.name("options[Size]")));
            purchaseCurrencyCode.selectByValue("Small");}
            Integer currentProductNumber = Integer.valueOf(driver.findElement(By.cssSelector("span.quantity")).getText());
            driver.findElement(By.name("add_cart_product")).click();
            wait.until(ExpectedConditions.textToBe(By.cssSelector("span.quantity"), String.valueOf(currentProductNumber + 1)));
            driver.get("http://localhost/litecart");
        }
        driver.findElement(By.cssSelector("div#cart a.link")).click();
        while (driver.findElements(By.cssSelector("table[class='dataTable rounded-corners']")).size() > 0) {
            if (driver.findElements(By.cssSelector("li.shortcut")).size() > 0){
                driver.findElement(By.cssSelector("li.shortcut")).click();}
                WebElement isTablePresent = driver.findElement(By.cssSelector("table[class='dataTable rounded-corners']"));
                driver.findElement(By.name("remove_cart_item")).click();
                wait.until(ExpectedConditions.stalenessOf(isTablePresent));
            }

        Assert.assertTrue("Product table is still present but should be removed.",driver.findElements(By.cssSelector("table[class='dataTable rounded-corners']")).size() == 0);
    }
}
