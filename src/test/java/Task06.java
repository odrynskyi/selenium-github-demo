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
    public static WebDriver chromeDriver;

    @BeforeClass
    public static void start(){
        ChromeDriverManager.getInstance().setup();
        //ChromeOptions chromeOptions = new ChromeOptions();
        //chromeOptions.addArguments("start-maximized");
        chromeDriver = new ChromeDriver();
        chromeDriver.get("http://localhost/litecart");
        chromeDriver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
    }

    @AfterClass
    public static void stop(){
        chromeDriver.quit();
        chromeDriver = null;
    }

    @Test
    public void AddAndRemoveProducts(){
        WebDriverWait wait = new WebDriverWait(chromeDriver,10);

        for (int i = 0; i < 4; i++){
            chromeDriver.findElement(By.cssSelector("div#box-most-popular div ul li:nth-child(" + (i+1) +")")).click();

            if (chromeDriver.findElements(By.cssSelector("select[name='options[Size]']")).size() > 0) {
            Select purchaseCurrencyCode = new Select (chromeDriver.findElement(By.name("options[Size]")));
            purchaseCurrencyCode.selectByValue("Small");}
            Integer currentProductNumber = Integer.valueOf(chromeDriver.findElement(By.cssSelector("span.quantity")).getText());
            chromeDriver.findElement(By.name("add_cart_product")).click();
            wait.until(ExpectedConditions.textToBe(By.cssSelector("span.quantity"), String.valueOf(currentProductNumber + 1)));
            chromeDriver.get("http://localhost/litecart");
        }
        chromeDriver.findElement(By.cssSelector("div#cart a.link")).click();

        while (chromeDriver.findElements(By.cssSelector("table[class='dataTable rounded-corners']")).size() > 0) {
            if (chromeDriver.findElements(By.cssSelector("li.shortcut")).size() > 0){
                chromeDriver.findElement(By.cssSelector("li.shortcut")).click();}
                WebElement isTablePresent = chromeDriver.findElement(By.cssSelector("table[class='dataTable rounded-corners']"));
                chromeDriver.findElement(By.name("remove_cart_item")).click();
                wait.until(ExpectedConditions.stalenessOf(isTablePresent));
            }

        Assert.assertTrue("False",chromeDriver.findElements(By.cssSelector("table[class='dataTable rounded-corners']")).size() == 0);




    }



}
