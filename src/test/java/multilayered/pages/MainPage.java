package multilayered.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class MainPage extends Page{

    public MainPage(WebDriver driver){
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @FindBy(css = "div#box-most-popular div ul li")
    public WebElement selectProduct;

    public void open(){
         driver.get(base);
    }
}
