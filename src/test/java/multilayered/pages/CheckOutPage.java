package multilayered.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class CheckOutPage extends Page{

    public CheckOutPage(WebDriver driver){
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @FindBy(css = "table[class='dataTable rounded-corners']")
    private List<WebElement> productTable;

    @FindBy(css = "li.shortcut")
    private List<WebElement> shortcut;

    @FindBy(css = "button[name = 'remove_cart_item']")
    private WebElement removeButton;

    public void open(){
        driver.get(base + "checkout");
    }

    public void removeAllProducts(){
        if (shortcut.size() > 0){
            shortcut.get(0).click();
        }
        while(productTable.size() > 0){
            WebElement productTableElement = driver.findElement(By.cssSelector("table[class='dataTable rounded-corners']"));
            removeButton.click();
            wait.until(ExpectedConditions.stalenessOf(productTableElement));
        }
    }

    public boolean getProductTable(){
        return(productTable.size() > 0);
    }

}
