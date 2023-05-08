package test;


import org.junit.Before;
import model.*;
import org.junit.Test;
import static org.junit.Assert.assertEquals;


public class ProductFinderTest {

    private Controller productFinder;

    @Before
    public void setUp() {
        productFinder = new Controller();
    }
    @Test
    public void testValidateProductByNameExists() {
        // Arrange

        productFinder.rProduct("Product1", "Description1", 10.0, "Category1", 5);
        productFinder.rProduct("Product2", "Description2", 15.0, "Category2", 10);
        String productName = "Product1";
        String expected = "The product Product1 exists ;) \nThere are: 5 units \nBelongs to the category: Category1\nFor onlyyy: 10.0";

        // Act
        String result = productFinder.validateProductByName(productName);

        // Assert
        assertEquals(expected, result);
    }

    @Test
    public void testValidateProductByNameNotExists() {
        // Arrange
        productFinder.rProduct("Product1", "Description1", 10.0, "Category1", 5);
        productFinder.rProduct("Product2", "Description2", 15.0, "Category2", 10);
        String productName = "Product3";
        String expected = "The product Product3 doesn't exist :c";

        // Act
        String result = productFinder.validateProductByName(productName);

        // Assert
        assertEquals(expected, result);
    }

    @Test
    public void testValidateProductByPriceExists() {
        // Arrange
        productFinder.rProduct("Product1", "Description1", 10.0, "Category1", 5);
        productFinder.rProduct("Product2", "Description2", 15.0, "Category2", 10);
        productFinder.rProduct("Product3", "Description3", 10.0, "Category3", 2);
        double price = 10.0;
        String expected = "The following products have the price of 10.0: \nProduct1\nProduct3";

        // Act
        String result = productFinder.validateProductByPrice(price);

        // Assert
        assertEquals(expected, result);
    }

    @Test
    public void testValidateProductByPriceNotExists() {
        // Arrange
        productFinder.rProduct("Product1", "Description1", 10.0, "Category1", 5);
        productFinder.rProduct("Product2", "Description2", 15.0, "Category2", 10);
        double price = 20.0;
        String expected = "There aren't products by 20.0";

        // Act
        String result = productFinder.validateProductByPrice(price);

        // Assert
        assertEquals(expected, result);
    }

    @Test
    public void testValidateProductByCategoryWhenProductsExist() {
        // Arrange
        String category = "Electronics";
        productFinder.rProduct("Laptop", "Dell Laptop", 800.0, category, 10);
        productFinder.rProduct("Monitor", "Samsung Monitor", 150.0, category, 5);


        // Act
        String result = productFinder.validateProductByCategory(category);

        // Assert
        assertEquals("The following products belong to the category Electronics:\nLaptop\nMonitor", result);
    }

    @Test
    public void testValidateProductByCategoryWhenNoProductsExist() {
        // Arrange
        String category = "Clothing";
        productFinder.rProduct("Shirt", "Cotton Shirt", 20.0, "Men's Clothing", 50);
        productFinder.rProduct("Jeans", "Levi's Jeans", 50.0, "Men's Clothing", 30);

        // Act
        String result = productFinder.validateProductByCategory(category);

        // Assert
        assertEquals("There aren't products by the category: Clothing", result);
    }

    @Test
    public void testValidateProductByTimePurchased_withValidInput() {

        productFinder.rProduct("Product1", "Description1", 10.0, "Category1", 5);
        productFinder.rProduct("Product2", "Description2", 20.0, "Category2", 10);
        productFinder.rProduct("Product3", "Description3", 30.0, "Category3", 5);

        // 2. Llamar a la función con diferentes valores de tiempo vendido
        String result1 = productFinder.validateProductByTimePurchased(5);
        String expected1 = "The following products haven't been sold and 5 units exists:\nProduct1\nProduct3";
        assertEquals(expected1, result1);

        String result2 = productFinder.validateProductByTimePurchased(10);
        String expected2 = "The following products haven't been sold and 10 units exists:\nProduct2";
        assertEquals(expected2, result2);
    }

    @Test
    public void testValidateProductByTimePurchased_withInvalidInput() {
        
        productFinder.rProduct("Product1", "Description1", 10.0, "Category1", 5);
        productFinder.rProduct("Product2", "Description2", 20.0, "Category2", 10);
        productFinder.rProduct("Product3", "Description3", 30.0, "Category3", 5);

        // 2. Llamar a la función con un valor de tiempo vendido que no existe
        String result = productFinder.validateProductByTimePurchased(15);
        String expected = "There are not that number of units in any product";
        assertEquals(expected, result);
    }







}

