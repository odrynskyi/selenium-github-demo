package multilayered.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

public class ProductPage extends Page{

    public ProductPage(WebDriver driver){
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @FindBy(css = "span.quantity")
    private WebElement quantityElement;

    @FindBy(name = "add_cart_product")
    private WebElement addProductButton;

    @FindBy(name = "options[Size]")
    public WebElement sizeElement;

    private void selectSize(String size){
        if (driver.findElements(By.cssSelector("select[name='options[Size]']")).size() > 0) {
            wait.until((WebDriver d) -> d.findElement(
                    By.cssSelector(String.format("select[name='options[Size]'] option[value=%s]", size))));
            new Select(driver.findElement(By.cssSelector("select[name='options[Size]']"))).selectByValue(size);
        }
    }

    public void AddProductToCartWithWait(){
        selectSize("Small");
        int currentQuantity = getQuantity();
        addProductButton.click();
        wait.until(ExpectedConditions.textToBe(By.cssSelector("span.quantity"), String.valueOf(currentQuantity + 1)));
    }

    private int getQuantity(){
        return Integer.valueOf(quantityElement.getText());
    }
}
