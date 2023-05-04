package test;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import model.*;
import org.junit.Before;
import org.junit.Test;

public class ProductFinderTest {

    private Controller productSearch;

    @Before
    public void setUp() {
        List<Product> products = new ArrayList<>();
        productSearch.registerProduct("Product 1", "Description 1", 10.99, "Category 1", 0));
        productSearch.registerProduct("Product 2", "Description 2", 20.99, "Category 2", 0));
        productSearch.registerProduct("Product 3", "Description 3", 30.99, "Category 3", 0));
        productSearch.registerProduct("Product 4", "Description 4", 40.99, "Category 4", 0));
        productSearch.registerProduct("Product 5", "Description 5", 50.99, "Category 5", 0));
        Collections.sort(products);
        productSearch = new ProductSearch(products);
    }

    @Test
    public void testFindProductByNameNotFound() {
        assertNull(productSearch.findProductByName("Product 6"));
    }
}

