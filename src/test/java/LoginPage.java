import io.github.bonigarcia.wdm.ChromeDriverManager;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

public class LoginPage {

    public static WebDriver chromeDriver;

    @BeforeClass
    public static void start(){
        ChromeDriverManager.getInstance().setup();
        chromeDriver = new ChromeDriver();
        chromeDriver.get("http://localhost/litecart/admin");
        chromeDriver.findElement(By.cssSelector("input[name=username]")).sendKeys("admin");
        chromeDriver.findElement(By.cssSelector("input[name=password]")).sendKeys("admin");
        chromeDriver.findElement(By.cssSelector("button[name=login]")).click();
        chromeDriver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
    }

    @AfterClass
    public static void stop(){
        chromeDriver.quit();
        chromeDriver = null;
    }
}
