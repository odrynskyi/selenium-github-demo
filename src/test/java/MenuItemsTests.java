import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import java.util.List;

public class MenuItemsTests extends LoginPage{

    @Test
    public void MenuItems() {

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
}

