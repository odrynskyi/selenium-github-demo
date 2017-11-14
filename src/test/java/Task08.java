import com.google.common.io.Files;
import io.github.bonigarcia.wdm.ChromeDriverManager;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.events.AbstractWebDriverEventListener;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;

import java.util.List;


public class Task08 {
    public static EventFiringWebDriver driver;
    private static WebDriverWait wait;
    private static boolean isElementPresent(By locator) {
        return driver.findElements(locator).size() > 0;
    }
    @BeforeClass
    public static void start(){
        ChromeDriverManager.getInstance().setup();
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("start-maximized");
        driver = new EventFiringWebDriver(new ChromeDriver(chromeOptions));
        wait = new WebDriverWait(driver, 10);
        driver.register(new MyListener());
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
    public void MenuItems(){
        List<WebElement> menuItems = driver.findElements(By.cssSelector("li#app- span.name"));
        for (int i=0; i<menuItems.size(); i++) {
            driver.findElement(By.cssSelector("li#app-:nth-child(" + (i+1) +")")).click();

            if (driver.findElements(By.cssSelector("ul.docs")).size() == 0)
            {
                Assert.assertTrue("The h1 is not shown on the page.", driver.findElement(By.cssSelector("h1")).isDisplayed());
            }
            else
            {
                List<WebElement> subMenuItems = driver.findElements(By.cssSelector("ul.docs li"));
                for (int j=0; j<subMenuItems.size(); j++) {
                    driver.findElement(By.cssSelector("ul.docs li:nth-child(" + (j+1) +")")).click();
                    Assert.assertTrue("The h1 is not shown on the page.", driver.findElement(By.cssSelector("h1")).isDisplayed());
                }
            }
        }
    }

    public static class MyListener extends AbstractWebDriverEventListener {
        @Override
        public void afterFindBy(By by, WebElement element, WebDriver driver){
            System.out.println("Start searching " + by);
        };

        @Override
        public void beforeFindBy(By by, WebElement element, WebDriver driver){
            System.out.println(by + " found");
        };

        @Override
        public void onException(Throwable throwable, WebDriver driver){
            File tempFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            try {
                Files.copy(tempFile, new File("screen.png"));

            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println(throwable);
        };
    }



}

