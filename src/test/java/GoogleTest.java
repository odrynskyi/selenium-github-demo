import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

import java.util.concurrent.TimeUnit;

public class GoogleTest {
    By searchField = By.xpath("//*[@id='lst-ib']");
    By searchButton = By.xpath("//*[@id='sbtc']/div[2]/div[2]/div[1]/div/ul/li[11]/div/span[1]/span/input");

    @Test
    public void chromeGoogleTest() {
        WebDriver chromeDriver = new ChromeDriver();
        chromeDriver.get("https://www.google.com.ua");
        chromeDriver.manage().window().maximize();
        chromeDriver.findElement(searchField).sendKeys("Selenium");
        chromeDriver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        chromeDriver.findElement(searchButton).click(); //Click Find button
        chromeDriver.quit();
    }

    @Test
    public void firefoxGoogleTest(){
        WebDriver firefoxDriver = new FirefoxDriver();
        firefoxDriver.get("https://www.google.com.ua");
        firefoxDriver.manage().window().maximize();
        firefoxDriver.findElement(searchField).sendKeys("WebDriver");
        firefoxDriver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        firefoxDriver.findElement(searchButton).click();  //Click Find button
        firefoxDriver.quit();
    }

    @Test
    public void iExplorerGoogleTest(){
        WebDriver iExplorerDriver = new InternetExplorerDriver();
        iExplorerDriver.get("https://www.google.com.ua");
        iExplorerDriver.manage().window().maximize();
        iExplorerDriver.findElement(searchField).sendKeys("WebDriver"); //Unable to find element with xpath == //*[@id='lst-ib']
        iExplorerDriver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        iExplorerDriver.findElement(searchButton).click();
        iExplorerDriver.quit();
    }
}
