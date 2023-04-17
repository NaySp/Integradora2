package model;

import java.util.ArrayList;
import java.util.List;

public class Controller {
    
    private List<Product> productList;
    private Order order;

    public Controller(){
        this.productList = new ArrayList<Product>();
    }

    public void registerProduct(String name, String description, double price, String category, int numSales) {
        try {
            // Check if the product name already exists
            for (Product p : productList) {
                if (p.getName().equals(name)) {
                    throw new Exception("A product with the same name already exists.");
                }
            }
            // Create a new Product object and add it to the list
            Product newProduct = new Product(name, description, price, category, numSales);
            productList.add(newProduct);
            System.out.println("Product registered successfully.");
        } catch (Exception e) {
            System.out.println("Error registering product: " + e.getMessage());
        }
    }

    
}

