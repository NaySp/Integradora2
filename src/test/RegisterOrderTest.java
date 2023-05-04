package test;
import static org.junit.Assert.*;

import model.*;
import org.junit.Before;
import org.junit.Test;

public class RegisterOrderTest {

    private Controller orderRegistration;

    @Before
    public void setUp() {

        orderRegistration = new Controller();
    }

    @Test
    public void testRegisterOrder() {
        // Add a product
        orderRegistration.registerProduct("Product 1", "Description 1", 10.99, "Category 1", 0);

        // Add an order for the product
        orderRegistration.registerOrder("John Smith", "Product 1", 3);

        // Check that the order was added
        assertEquals(1, orderRegistration.getOrderList().size());
        Order order = orderRegistration.getOrderList().get(0);
        assertEquals("John Smith", order.getBuyerName());
        assertEquals(product, order.getProductName());
        assertEquals(3, order.getQuantity());
    }

    @Test(expected = Exception.class)
    public void testRegisterOrderWithInvalidProduct() throws Exception {
        // Try to add an order for a product that doesn't exist
        try {
            orderRegistration.registerOrder("John Smith", "Product 1", 3);
            fail("Expected an Exception to be thrown");
        } catch (Exception e) {
            assertEquals("Product not found.", e.getMessage());
            throw e;
        }
    }
}
