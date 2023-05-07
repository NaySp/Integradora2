package model;
import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import exception.EqualProductException;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Controller {
    
    private List<Product> productList;
    private List<Order> orders;



    public Controller(){
        this.productList = new ArrayList<Product>();
        orders = new ArrayList<>();
    }

    public void rProduct(String name, String description, double price, String category, int numSales){
        try{
            registerProduct(name, description, price, category, numSales);
        }catch(EqualProductException ep){
            ep.getMessage();
            ep.getStackTrace();
        }
    }

    private void registerProduct(String name, String description, double price, String category, int numSales) throws EqualProductException{
            for (Product p : productList) {
                if (p.getName().equals(name)) {
                    // Esto debería ser una excepción propia: sameProductException
                    throw new EqualProductException("A product with the same name already exists.");
                }
            }
            // Create a new Product object and add it to the list
            Product newProduct = new Product(name, description, price, category, numSales);
            productList.add(newProduct);
            // nicolas dice que no imprimas nada en el modelo ...
            System.out.println("Product registered successfully.");

    }

    public void registerOrder(String buyerName, List<Product> products) {

        try {
            // Check if any of the product names are invalid
            for (Product p : products) {
                boolean validName = false;
                for (Product inventoryProduct : productList) {
                    if (inventoryProduct.getName().equals(p.getName())) {
                        validName = true;
                        break;
                    }
                }
                if (!validName) {
                    throw new Exception("Invalid product name: " + p.getName());
                }
            }

            // Calculate the total price of the order and update the inventory
            double totalPrice = 0;
            for (Product p : products) {
                for (Product inventoryProduct : productList) {
                    if (inventoryProduct.getName().equals(p.getName())) {
                        totalPrice += p.getNumSales() * inventoryProduct.getPrice();
                        inventoryProduct.decreaseAmount(p.getNumSales());// Decrease the quantity of the product in inventory
                        break;
                    }
                }
            }

            // Create a new Order object and add it to the list of orders
            Order newOrder = new Order(buyerName, products, totalPrice, LocalDate.now());
            orders.add(newOrder);
            System.out.println("Order registered successfully.");

        } catch (Exception e) {
            System.out.println("Error registering order: " + e.getMessage());
        }
    }


    public List<Order> getOrders() {
        return Collections.unmodifiableList(orders);
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

    //** */
    public void readData() {

        String msj = "File loaded. ";
        Gson gson = new Gson();

        File projectDir = new File(System.getProperty("user.dir"));
        File dataDirectory = new File(projectDir+"/data");
        File products = new File(projectDir+"/data/products.json");
        File orders = new File(projectDir+"/data/orders.json");

        if (!dataDirectory.exists()) {
            dataDirectory.mkdirs();
            return;
        }

        if ( !products.exists() || !orders.exists() ) return;

        try {
            FileInputStream pro = new FileInputStream(products);
            FileInputStream ord = new FileInputStream(orders);
            BufferedReader proReader = new BufferedReader(new InputStreamReader(pro));
            BufferedReader ordReader = new BufferedReader(new InputStreamReader(ord));
            // Lee el archivo línea por línea
            String line;
            String json = "";
            while ((line = proReader.readLine()) != null) {
                json += line;
            }
            // Deserializa el contenido JSON
            Product [] jsonProducts = gson.fromJson(json, Product[].class);
            this.productList.addAll(Arrays.asList(jsonProducts));


            json = "";
            while ((line = ordReader.readLine()) != null) {
                json += line;
            }
            Order[] jsonOrders = gson.fromJson(json, Order[].class);
            this.orders.addAll(Arrays.asList(jsonOrders));

        } catch (JsonSyntaxException | JsonIOException | IOException e) {
            System.out.println("Error al leer el archivo JSON: " + e.getMessage());
        }

    }

    public String saveData() {

        String msj = "Data saved. ";
        Gson gson = new Gson();

        File projectDir = new File(System.getProperty("user.dir"));
        File dataDirectory = new File(projectDir+"/data");
        File productsFile = new File(projectDir+"/data/products.json");
        File ordersFile = new File(projectDir+"/data/orders.json");

        if (!dataDirectory.exists()) {
            dataDirectory.mkdirs();
        }

        try {
            FileWriter productsWriter = new FileWriter(productsFile);
            gson.toJson(productList.toArray(), productsWriter);
            productsWriter.close();

            FileWriter ordersWriter = new FileWriter(ordersFile);
            gson.toJson(orders.toArray(), ordersWriter);
            ordersWriter.close();

        } catch (JsonIOException | IOException e) {
            msj = "Error al guardar los datos en archivos JSON: " + e.getMessage();
            return msj;
        }

        return msj;
    }


    public List<Product> getProductList() {
        return productList;
    }


}

