import io.github.bonigarcia.wdm.ChromeDriverManager;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.List;

public class Task02 {

    public static WebDriver driver;
    public static WebDriverWait wait;

    @BeforeClass
    public static void start(){
        ChromeDriverManager.getInstance().setup();
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 10);
        driver.get("http://localhost/litecart/admin");
        driver.findElement(By.cssSelector("input[name=username]")).sendKeys("admin");
        driver.findElement(By.cssSelector("input[name=password]")).sendKeys("admin");
        driver.findElement(By.cssSelector("button[name=login]")).click();
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
                Assert.assertTrue("The title is not shown on the page.", driver.findElement(By.cssSelector("h1")).isDisplayed());
            }
            else
            {
                List<WebElement> subMenuItems = driver.findElements(By.cssSelector("ul.docs li"));
                for (int j=0; j<subMenuItems.size(); j++) {
                    driver.findElement(By.cssSelector("ul.docs li:nth-child(" + (j+1) +")")).click();
                    Assert.assertTrue("The title is not shown on the page.", driver.findElement(By.cssSelector("h1")).isDisplayed());
                }
            }
        }
    }
}

