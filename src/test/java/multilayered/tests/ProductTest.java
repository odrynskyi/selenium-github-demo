package multilayered.tests;

import org.junit.Assert;
import org.junit.Test;

public class ProductTest extends TestBase{

    @Test
    public void AddAndRemoveProducts(){
        for (int i=0; i<5; i++) {
            app.addProductToCart();
        }
        app.removeAllProductsSequentially();

        Assert.assertFalse("The product table is still present.",app.isProductTablePresent());
    }
}