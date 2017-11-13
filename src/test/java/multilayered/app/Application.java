package multilayered.app;

import io.github.bonigarcia.wdm.ChromeDriverManager;
import multilayered.pages.CheckOutPage;
import multilayered.pages.MainPage;
import multilayered.pages.ProductPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class Application {

    private WebDriver driver;

    private ProductPage productPage;
    private MainPage mainPage;
    private CheckOutPage checkOutpage;

    public Application(){
        ChromeDriverManager.getInstance().setup();
        driver = new ChromeDriver();
        productPage = new ProductPage(driver);
        mainPage = new MainPage(driver);
        checkOutpage = new CheckOutPage(driver);
    }

    public void quit(){
        driver.quit();
    }

    public void addProductToCart(){
        mainPage.open();
        mainPage.selectProduct.click();
        productPage.AddProductToCartWithWait();
    }

    public void removeAllProductsSequentially(){
        checkOutpage.open();
        checkOutpage.removeAllProducts();
    }

    public boolean isProductTablePresent(){
        return checkOutpage.getProductTable();
    }

}
