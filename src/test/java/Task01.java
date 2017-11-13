import io.github.bonigarcia.wdm.ChromeDriverManager;
import io.github.bonigarcia.wdm.FirefoxDriverManager;
import io.github.bonigarcia.wdm.InternetExplorerDriverManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;

@RunWith(Parameterized.class)
public class Task01 {

    @Parameterized.Parameter
    public WebDriver driver;
    private String baseURL = "http://localhost/litecart/en/checkout";
    //By searchField = By.xpath("//*[@id='lst-ib']");
    By searchField = By.cssSelector("input[id='lst-ib']");

    public WebDriverWait wait;

    @Before
    public void setUp(){
        wait = new WebDriverWait(driver, 10);
        driver.manage().window().maximize();
    }

    @After
    public void shutdown(){
        driver.quit();
        driver = null;
    }

    @Test
    public void GoogleTest(){
        driver.get("https://www.google.com.ua");
        wait.until((WebDriver d) -> d.findElement(By.cssSelector("input[id='lst-ib']")));
        driver.findElement(searchField).sendKeys("Selenium");
        driver.quit();
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
}
