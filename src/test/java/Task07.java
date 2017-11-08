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
import java.util.concurrent.TimeUnit;

public class Task07 {

    public static WebDriver chromeDriver;
    private static boolean isElementPresent(By locator) {
        return chromeDriver.findElements(locator).size() > 0;
    }
    @BeforeClass
    public static void start(){
        ChromeDriverManager.getInstance().setup();
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("start-maximized");
        chromeDriver = new ChromeDriver(chromeOptions);
        chromeDriver.get("http://localhost/litecart/admin");
        if(isElementPresent(By.name("login"))){
            chromeDriver.findElement(By.name("username")).sendKeys("admin");
            chromeDriver.findElement(By.name("password")).sendKeys("admin");
            chromeDriver.findElement(By.name("login")).click();
        }
        chromeDriver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
    }

    @Test
    public void isNewWindowOpened(){
        chromeDriver.findElement(By.cssSelector("#box-apps-menu li:nth-child(3)")).click();
        chromeDriver.findElement(By.cssSelector("#content div a")).click();
        String originalWindow = chromeDriver.getWindowHandle();

        List<WebElement> itemsList = chromeDriver.findElements(By.cssSelector("i[class='fa fa-external-link']"));
        for (int i=0; i<itemsList.size(); i++) {
            Set<String> existWindows = chromeDriver.getWindowHandles();
            WebDriverWait wait = new WebDriverWait(chromeDriver, 10);
            itemsList.get(i).click();
            String newWindow = wait.until(anyWindowOtherThan(existWindows));
            chromeDriver.switchTo().window(newWindow);
            chromeDriver.close();
            chromeDriver.switchTo().window(originalWindow);
        }
    }

    private ExpectedCondition<String> anyWindowOtherThan(Set<String> windows) {
        return new ExpectedCondition<String>() {
            public String apply(WebDriver input) {
                Set<String> handles = chromeDriver.getWindowHandles();
                handles.removeAll(windows);
                return handles.size() > 0 ? handles.iterator().next() : null;
            }
        };
    }

}
