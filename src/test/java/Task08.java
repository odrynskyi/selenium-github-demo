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
import java.io.File;
import java.io.IOException;
import java.util.EventListener;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Task08 {
    public static EventFiringWebDriver chromeDriver;
    private static boolean isElementPresent(By locator) {
        return chromeDriver.findElements(locator).size() > 0;
    }
    @BeforeClass
    public static void start(){
        ChromeDriverManager.getInstance().setup();
        //ChromeOptions chromeOptions = new ChromeOptions();
        //chromeOptions.addArguments("start-maximized");
        chromeDriver = new EventFiringWebDriver(new ChromeDriver());
        chromeDriver.register(new MyListener());
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
    public void MenuItems(){
        List<WebElement> menuItems = chromeDriver.findElements(By.cssSelector("li#app- span.name"));
        for (int i=0; i<menuItems.size(); i++) {
            chromeDriver.findElement(By.cssSelector("li#app-:nth-child(" + (i+1) +")")).click();

            if (chromeDriver.findElements(By.cssSelector("ul.docs")).size() == 0)
            {
                Assert.assertTrue("The title is not shown on the page.", chromeDriver.findElement(By.cssSelector("h1")).isDisplayed());
            }
            else
            {
                List<WebElement> subMenuItems = chromeDriver.findElements(By.cssSelector("ul.docs li"));
                for (int j=0; j<subMenuItems.size(); j++) {
                    chromeDriver.findElement(By.cssSelector("ul.docs li:nth-child(" + (j+1) +")")).click();
                    Assert.assertTrue("The title is not shown on the page.", chromeDriver.findElement(By.cssSelector("h1")).isDisplayed());
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
            File tempFile = ((TakesScreenshot) chromeDriver).getScreenshotAs(OutputType.FILE);
            try {
                Files.copy(tempFile, new File("screen.png"));

            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println(throwable);
        };
    }



}

