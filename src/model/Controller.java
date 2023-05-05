package model;
import java.util.ArrayList;
import java.util.List;

public class Controller {
    
    private List<Product> productList;
    private List<Order> orders;


    public Controller(){
        this.productList = new ArrayList<Product>();
        orders = new ArrayList<>();
    }

    public void registerProduct(String name, String description, double price, String category, int numSales) // trhows sameProductException
    {
        try { // el try catch no va acá ...
            // Check if the product name already exists
            for (Product p : productList) {
                if (p.getName().equals(name)) {
                    // Esto debería ser una excepción propia: sameProductException
                    throw new Exception("A product with the same name already exists.");
                }
            }
            // Create a new Product object and add it to the list
            Product newProduct = new Product(name, description, price, category, numSales);
            productList.add(newProduct);
            // nicolas dice que no imprimas nada en el modelo ...
            System.out.println("Product registered successfully.");
        } catch (Exception e) {
            System.out.println("Error registering product: " + e.getMessage());
        }
    }

    public void registerOrder(String buyerName, List<String> listProduct, int amount) {

        try {
            // Check if any of the product names are invalid
            for (String name : listProduct) {
                boolean validName = false;
                for (Product p : productList) {
                    if (p.getName().equals(name)) {
                        validName = true;
                        break;
                    }
                }
                if (!validName) {
                    throw new Exception("Invalid product name: " + name);
                }
            }

            // Calculate the total price of the order and update the inventory

            double totalPrice = 0;
            for (Product p : productList) {
                for (String name : listProduct) {
                    if (p.getName().equals(name)) {
                        totalPrice += p.getPrice();
                        p.decreaseAmount(amount);// Decrease the quantity of the product in inventory
                        break;
                    }
                }
            }

            // Create a new Order object and add it to the list of orders
            Order newOrder = new Order(buyerName, productList, amount);
            orders.add(newOrder);
            System.out.println("Order registered successfully.");

        } catch (Exception e) {
            System.out.println("Error registering order: " + e.getMessage());
        }
    }

    public String validateProductByName(String productName) {

        String msj;
        for (Product p : productList) {
            if (p.getName().equalsIgnoreCase(productName)) {
                msj = "The product " + p.getName() + " exits ;) \n" + "There are: " + p.getNumSales() + " units \n" + "Belongs to the category: " + p.getCategory() + "\nFor onlyyy: " + p.getPrice();
                return msj; // Product with the same name exists
            }
        }
        msj =  msj = "The product " + productName + " doesn't exist :c  ";
        return msj; // Product with the same name does not exist
    }

    public String validateProductByPrice(double price) {
        List<String> productNames = new ArrayList<>();
        for (Product p : productList) {
            if (p.getPrice() == price) {
                productNames.add(p.getName());
            }
        }
        if (productNames.isEmpty()) {
            return "There aren't products by " + price;
        } else {
            return "The following products have the price of " + price + ": \n" + String.join("\n", productNames);
        }
    }


    public String validateProductByCategory(String category) {
        List<String> productNames = new ArrayList<>();
        for (Product p : productList) {
            if (p.getCategory().equalsIgnoreCase(category)) {
                productNames.add(p.getName());
            }
        }
        if (productNames.isEmpty()) {
            return "There aren't products by the category: " + category;
        } else {
            return "The following products belong to the category " + category + ":\n" + String.join("\n", productNames);
        }
    }


    public String validateProductByTimePurchased(int timeSold) {
        List<String> productNames = new ArrayList<>();
        for (Product p : productList) {
            if (p.getNumSales() == timeSold) {
                productNames.add(p.getName());
            }
        }
        if (productNames.isEmpty()) {
            return "There are not that number of units in any product";
        } else {
            return "The following products haven't been sold and " + timeSold + " units exists:\n" + String.join("\n", productNames);
        }
    }



    public List<Product> getProductList() {
        return productList;
    }


}

