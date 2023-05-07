package model;
import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import exception.EqualProductException;
import exception.InvalidNameException;

import java.io.*;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

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
            ep.getMsg();
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

    public void rOrder(String buyerName, List<Product> products, int quantity, int increaseAmount){
        try{
            registerOrder(buyerName, products,quantity,increaseAmount);
        }catch(InvalidNameException ie){
            ie.getMsg();
            ie.getStackTrace();
        }
    }

    private void registerOrder(String buyerName, List<Product> products, int quantity,int increaseAmount) throws InvalidNameException {


            for (Product p : products) {
                boolean validName = false;
                for (Product inventoryProduct : productList) {
                    if (inventoryProduct.getName().equals(p.getName())) {
                        validName = true;
                        break;
                    }
                }

                }


            // Calculate the total price of the order and update the inventory
            double totalPrice = 0;
            for (Product p : products) {
                for (Product inventoryProduct : productList) {
                    if (inventoryProduct.getName().equals(p.getName())) {
                        totalPrice += p.getNumSales() * inventoryProduct.getPrice();
                        int decreaseAmount = quantity - increaseAmount;
                        try {
                            p.decreaseAmount(decreaseAmount);// Decrease the quantity of the product in inventory
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                        break;
                    }
                }
            }

            // Create a new Order object and add it to the list of orders
            Order newOrder = new Order(buyerName, products,quantity, totalPrice, LocalDate.now().toString());
            orders.add(newOrder);
            System.out.println("Order registered successfully.");

    }


    public List<Order> getOrders() {
        return Collections.unmodifiableList(orders);
    }


    public String validateProductByName(String productName) {
        ArrayList<String> productNames = new ArrayList<String>();
        for (Product p : productList) {
            productNames.add(p.getName().toLowerCase());
        }

        int index = binarySearchStrings(productNames, productName.toLowerCase());

        if (index != -1) {
            Product p = productList.get(index);
            return "The product " + p.getName() + " exists ;) \n" + "There are: " + p.getNumSales() + " units \n" + "Belongs to the category: " + p.getCategory() + "\nFor onlyyy: " + p.getPrice();
        } else {
            return "The product " + productName + " doesn't exist :c";
        }
    }



    private int binarySearchStrings(ArrayList<String> arr, String goal) {
        int left = 0;
        int right = arr.size() - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            int cmp = goal.compareToIgnoreCase(arr.get(mid));

            if (cmp == 0) {
                return mid;
            } else if (cmp < 0) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }

        return -1;
    }



    public String validateProductByPrice(double price) {
        List<String> productNames = new ArrayList<>();

        // Ordenamos la lista de productos por precio para usar búsqueda binaria
        Collections.sort(productList, new Comparator<Product>() {
            public int compare(Product p1, Product p2) {
                return Double.compare(p1.getPrice(), p2.getPrice());
            }
        });

        // Buscamos el precio en la lista ordenada usando búsqueda binaria
        int index = binarySearchDouble(productList.stream().map(Product::getPrice).collect(Collectors.toCollection(ArrayList::new)), price);
        if (index != -1) {
            // Recorremos los productos desde el índice encontrado hacia abajo
            for (int i = index; i >= 0; i--) {
                Product p = productList.get(i);
                if (p.getPrice() == price) {
                    productNames.add(p.getName());
                } else {
                    break; // Salimos del loop si ya no encontramos más productos con ese precio
                }
            }

            // Recorremos los productos desde el índice encontrado hacia arriba
            for (int i = index + 1; i < productList.size(); i++) {
                Product p = productList.get(i);
                if (p.getPrice() == price) {
                    productNames.add(p.getName());
                } else {
                    break; // Salimos del loop si ya no encontramos más productos con ese precio
                }
            }
        }

        if (productNames.isEmpty()) {
            return "There aren't products by " + price;
        } else {
            return "The following products have the price of " + price + ": \n" + String.join("\n", productNames);
        }
    }


    static int binarySearchDouble(ArrayList<Double> arr, double goal){
        int left = 0;
        int right = arr.size() - 1;

        while(left <= right){
            int mid = (right + left)/2;
            if(goal < arr.get(mid)){
                right = mid - 1;
            }
            else if(goal > arr.get(mid)){
                left = mid + 1;
            }
            else {
                return mid;
            }
        }

        return -1;
    }




    public String validateProductByCategory(String category) {
        // Ordenamos la lista de productos por nombre de categoría
        Collections.sort(productList, Comparator.comparing(Product::getCategory));

        // Buscamos el índice del primer producto que pertenece a la categoría dada
        int index = binarySearch(productList, category);
        if (index == -1) {
            return "There aren't products by the category: " + category;
        } else {
            // Creamos una lista auxiliar con los nombres de los productos
            List<String> productNames = new ArrayList<>();
            productNames.add(productList.get(index).getName());
            int i = index - 1;
            while (i >= 0 && productList.get(i).getCategory().equalsIgnoreCase(category)) {
                productNames.add(0, productList.get(i).getName());
                i--;
            }
            i = index + 1;
            while (i < productList.size() && productList.get(i).getCategory().equalsIgnoreCase(category)) {
                productNames.add(productList.get(i).getName());
                i++;
            }

            return "The following products belong to the category " + category + ":\n" + String.join("\n", productNames);
        }
    }



    static int binarySearch(List<Product> arr, String goal) {
        int left = 0; // obtenemos una referencia al puntero inicial
        int right = arr.size() - 1; // obtenemos una referencia al puntero final

        while (left <= right) { // repetimos el siclo mientras estemos en el rango del arreglo
            // calculamos el punto medio del arreglo
            int mid = (right + left) / 2; // mid = left + (right - left)/2;

            // comparamos el elemento central con el valor objetivo
            if (goal.compareToIgnoreCase(arr.get(mid).getCategory()) < 0) {
                right = mid - 1;
            } else if (goal.compareToIgnoreCase(arr.get(mid).getCategory()) > 0) {
                left = mid + 1;
            }
            // si lo encontramos retornamos el elemento
            else {
                return mid;
            }
        }

        // si no retornamos -1
        return -1;
    }




    public String validateProductByTimePurchased(int timeSold) {
        Collections.sort(productList, Comparator.comparing(Product::getNumSales));

        List<String> productNames = new ArrayList<>();
        int index = binarySearchDate(productList, timeSold);

        if (index == -1) {
            return "There are not that number of units in any product";
        } else {
            productNames.add(productList.get(index).getName());

            // Buscamos productos con el mismo número de unidades vendidas hacia la izquierda del índice encontrado
            int i = index - 1;
            while (i >= 0 && productList.get(i).getNumSales() == timeSold) {
                productNames.add(productList.get(i).getName());
                i--;
            }

            // Buscamos productos con el mismo número de unidades vendidas hacia la derecha del índice encontrado
            i = index + 1;
            while (i < productList.size() && productList.get(i).getNumSales() == timeSold) {
                productNames.add(productList.get(i).getName());
                i++;
            }

            return "The following products haven't been sold and " + timeSold + " units exists:\n" + String.join("\n", productNames);
        }
    }


    static int binarySearchDate(List<Product> productList, int goal) {
        int left = 0;
        int right = productList.size() - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (productList.get(mid).getNumSales() < goal) {
                left = mid + 1;
            } else if (productList.get(mid).getNumSales() > goal) {
                right = mid - 1;
            } else {
                return mid;
            }
        }

        return -1;
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

