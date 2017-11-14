import io.github.bonigarcia.wdm.ChromeDriverManager;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.List;
import java.util.Set;

public class Task07 {

    public static WebDriver driver;
    private static WebDriverWait wait;
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
    public void isNewWindowOpened(){
        driver.findElement(By.cssSelector("#box-apps-menu li:nth-child(3)")).click();
        driver.findElement(By.cssSelector("#content div a")).click();
        String originalWindow = driver.getWindowHandle();

        List<WebElement> itemsList = driver.findElements(By.cssSelector("i[class='fa fa-external-link']"));
        for (int i=0; i<itemsList.size(); i++) {
            Set<String> existWindows = driver.getWindowHandles();
            WebDriverWait wait = new WebDriverWait(driver, 10);
            itemsList.get(i).click();
            String newWindow = wait.until(anyWindowOtherThan(existWindows));
            driver.switchTo().window(newWindow);
            driver.close();
            driver.switchTo().window(originalWindow);
        }
    }

    private ExpectedCondition<String> anyWindowOtherThan(Set<String> windows) {
        return new ExpectedCondition<String>() {
            public String apply(WebDriver input) {
                Set<String> handles = driver.getWindowHandles();
                handles.removeAll(windows);
                return handles.size() > 0 ? handles.iterator().next() : null;
            }
        };
    }
}
