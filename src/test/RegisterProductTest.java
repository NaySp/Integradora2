package test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import model.Controller;

public class RegisterProductTest {

    private Controller productRegistration;

    @Before
    public void setUp() {
        productRegistration = new Controller();
    }

    @Test
    public void testRegisterProduct() {
        // Test adding a new product
        productRegistration.registerProduct("Product 1", "Description 1", 10.99, "Category 1", 0);
        assertEquals(1, productRegistration.getProductList().size());
    }

    @Test(expected = Exception.class)
    public void testRegisterProductWithDuplicateName() throws Exception {
        // Add the first product
        productRegistration.registerProduct("Product 1", "Description 1", 10.99, "Category 1", 0);

        // Try to add a product with the same name
        try {
            productRegistration.registerProduct("Product 1", "Description 2", 9.99, "Category 2", 0);
            fail("Expected an Exception to be thrown");
        } catch (Exception e) {
            assertEquals("A product with the same name already exists.", e.getMessage());
            throw e;
        }

    }
}
