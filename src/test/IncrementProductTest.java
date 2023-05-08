package test;

import model.Controller;
import model.Product;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class IncrementProductTest {

    private Controller controller;


    @Test
    public void testAddProduct_validInput_success() {
        // Arrange
        String name = "Product1";
        String productName = "This is a product";
        double price = 9.99;
        int quantity = 10;

        // Act
        controller.rProduct(name, productName, price,"", quantity);

        // Assert
        assertEquals(1, controller.getProductList().size());
        Product product = controller.getProductList().get(0);
        assertEquals(name, product.getName());
        assertEquals(productName, product.getName());
        assertEquals(price, product.getPrice());
        assertEquals(quantity, product.getNumSales());
    }

    @Test
    public void testAddProduct_missingField_failure() {
        // Arrange
        String name = "Product1";
        String productName = "";
        double price = 9.99;
        int quantity = 10;

        // Act
        controller.rProduct(name, productName, price,"", quantity);

        // Assert
        assertTrue(controller.getProductList().isEmpty());
    }

    @Test
    public void testAddProduct_invalidPriceAndQuantity_failure() {
        // Arrange
        String name = "Product1";
        String productName = "This is a product";
        double price = -1;
        int quantity = 0;

        // Act
        controller.rProduct(name, productName, price,"", quantity);

        // Assert
        assertTrue(controller.getProductList().isEmpty());
    }

    @Test
    public void testIncrementProductQuantity_existingProduct_success() {
        // Arrange
        String name = "Product1";
        String productName = "This is a product";
        double price = 9.99;
        int quantity = 10;
        controller.rProduct(name, productName, price,"", quantity);
        int quantityToAdd = 5;

        // Act
        controller.incrementProductQuantity(name, quantityToAdd);

        // Assert
        Product product = controller.getProductList().get(0);
        assertEquals(quantity + quantityToAdd, product.getNumSales());
    }

    @Test
    public void testIncrementProductQuantity_nonExistingProduct_failure() {
        // Arrange
        String name = "Product1";
        int quantityToAdd = 5;

        // Act
        controller.incrementProductQuantity(name, quantityToAdd);

        // Assert
        assertTrue(controller.getProductList().isEmpty());
    }
}