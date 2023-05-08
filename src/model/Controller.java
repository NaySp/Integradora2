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


    public Controller() {
        this.productList = new ArrayList<Product>();
        orders = new ArrayList<>();
    }

    public void rProduct(String name, String description, double price, String category, int numSales) {
        try {
            registerProduct(name, description, price, category, numSales);
        } catch (EqualProductException ep) {
            ep.getMsg();
            ep.getStackTrace();
        }
    }

    private String  registerProduct(String name, String description, double price, String category, int numSales) throws EqualProductException {
        String msj;
        for (Product p : productList) {
            if (p.getName().equals(name)) {
                // Esto debería ser una excepción propia: sameProductException
                throw new EqualProductException("A product with the same name already exists.");
            }
        }
        // Create a new Product object and add it to the list
        Product newProduct = new Product(name, description, price, category, numSales);
        productList.add(newProduct);

        return msj = "Product registered successfully.";

    }

    public void rOrder(String buyerName, List<Product> products, int quantity, int increaseAmount) {
        try {
            registerOrder(buyerName, products, quantity, increaseAmount);
        } catch (InvalidNameException ie) {
            ie.getMsg();
            ie.getStackTrace();
        }
    }

    private void registerOrder(String buyerName, List<Product> products, int quantity, int increaseAmount) throws InvalidNameException {


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
        Order newOrder = new Order(buyerName, products, quantity, totalPrice, LocalDate.now().toString());
        orders.add(newOrder);
        System.out.println("Order registered successfully.");

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
    static int binarySearchDouble(ArrayList<Double> arr, double goal) {
        int left = 0;
        int right = arr.size() - 1;

        while (left <= right) {
            int mid = (right + left) / 2;
            if (goal < arr.get(mid)) {
                right = mid - 1;
            } else if (goal > arr.get(mid)) {
                left = mid + 1;
            } else {
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


    public String validateOrderByBuyerName(String buyerName) {
        ArrayList<String> buyerNames = new ArrayList<String>();
        for (Order o : orders) {
            buyerNames.add(o.getBuyerName().toLowerCase());
        }

        int index = binarySearchStrings2(buyerNames, buyerName.toLowerCase());

        if (index != -1) {
            Order o = orders.get(index);
            return "The order with buyer name " + o.getBuyerName() + " exists ;) \n" + "\n" + "Total amount: " + o.getTotal() + "\nOrder date: " + o.getOrderDate();
        } else {
            return "No order found with buyer name " + buyerName + ".";
        }
    }
    public int binarySearchStrings2(ArrayList<String> list, String key) {
        int low = 0;
        int high = list.size() - 1;
        while (low <= high) {
            int mid = (low + high) / 2;
            String midVal = list.get(mid);
            int cmp = midVal.compareTo(key);
            if (cmp < 0) {
                low = mid + 1;
            } else if (cmp > 0) {
                high = mid - 1;
            } else {
                return mid; // key found
            }
        }
        return -1; // key not found
    }


    public String validateOrderByTotal(double totalAmount) {
        List<String> orderBuyers = new ArrayList<>();

        // Ordenamos la lista de órdenes por el total de la orden para usar búsqueda binaria
        Collections.sort(orders, new Comparator<Order>() {
            public int compare(Order o1, Order o2) {
                return Double.compare(o1.getTotal(), o2.getTotal());
            }
        });

        // Buscamos el total de la orden en la lista ordenada usando búsqueda binaria
        int index = binarySearchDouble(orders.stream().map(Order::getTotal).collect(Collectors.toCollection(ArrayList::new)), totalAmount);
        if (index != -1) {
            // Recorremos las órdenes desde el índice encontrado hacia abajo
            for (int i = index; i >= 0; i--) {
                Order o = orders.get(i);
                if (o.getTotal() == totalAmount) {
                    orderBuyers.add(o.getBuyerName());
                } else {
                    break; // Salimos del loop si ya no encontramos más órdenes con ese total
                }
            }

            // Recorremos las órdenes desde el índice encontrado hacia arriba
            for (int i = index + 1; i < orders.size(); i++) {
                Order o = orders.get(i);
                if (o.getTotal() == totalAmount) {
                    orderBuyers.add(o.getBuyerName());
                } else {
                    break; // Salimos del loop si ya no encontramos más órdenes con ese total
                }
            }
        }
        if (orderBuyers.isEmpty()) {
            return "No orders found with total amount of " + totalAmount;
        } else {
            return "The following orders have a total amount of " + totalAmount + ": \n" + String.join("\n", orderBuyers);
        }
    }

    public int binarySearchDouble2(ArrayList<Double> list, double key) {
        int low = 0;
        int high = list.size() - 1;
        while (low <= high) {
            int mid = (low + high) / 2;
            double midVal = list.get(mid);
            int cmp = Double.compare(midVal, key);
            if (cmp < 0) {
                low = mid + 1;
            } else if (cmp > 0) {
                high = mid - 1;
            } else {
                return mid; // key found
            }
        }
        return -1; // key not found
    }

    public String validateOrderByDate(LocalDate date) {
        // Ordenamos la lista de órdenes por fecha de compra
        Collections.sort(orders, Comparator.comparing(Order::getOrderDate));

        List<String> orderIds = new ArrayList<>();

        // Buscamos la fecha en la lista ordenada usando búsqueda binaria
        int index = binarySearchOrderByDate(orders, date);

        if (index == -1) {
            return "There are no orders for the date " + date;
        } else {
            orderIds.add(orders.get(index).getBuyerName());

            // Buscamos órdenes con la misma fecha de compra hacia la izquierda del índice encontrado
            int i = index - 1;
            while (i >= 0 && orders.get(i).getOrderDate().equals(date)) {
                orderIds.add(orders.get(i).getBuyerName());
                i--;
            }

            // Buscamos órdenes con la misma fecha de compra hacia la derecha del índice encontrado
            i = index + 1;
            while (i < orders.size() && orders.get(i).getOrderDate().equals(date)) {
                orderIds.add(orders.get(i).getBuyerName());
                i++;
            }

            return "The following orders were placed on " + date + ":\n" + String.join("\n", orderIds);
        }
    }

    public static int binarySearchOrderByDate(List<Order> orderList, LocalDate purchaseDate) {
        int left = 0;
        int right = orderList.size() - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            LocalDate orderDate = LocalDate.parse(orderList.get(mid).getOrderDate());

            if (orderDate.equals(purchaseDate)) {
                return mid;
            } else if (orderDate.isBefore(purchaseDate)) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return -1;
    }



    //** */
    public void readData() {

        String msj = "File loaded. ";
        Gson gson = new Gson();

        File projectDir = new File(System.getProperty("user.dir"));
        File dataDirectory = new File(projectDir + "/data");
        File products = new File(projectDir + "/data/products.json");
        File orders = new File(projectDir + "/data/orders.json");

        if (!dataDirectory.exists()) {
            dataDirectory.mkdirs();
            return;
        }

        if (!products.exists() || !orders.exists()) return;

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
            Product[] jsonProducts = gson.fromJson(json, Product[].class);
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
        File dataDirectory = new File(projectDir + "/data");
        File productsFile = new File(projectDir + "/data/products.json");
        File ordersFile = new File(projectDir + "/data/orders.json");

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

    //** */
    public void searchByPriceRange(double minValue, double maxValue, int opt2) {
        List<Product> priceProducts = new ArrayList<>();

        for (Product p : productList) {
            double value = p.getPrice();
            if (value >= minValue && value <= maxValue) {
                priceProducts.add(p);
            }
        }

        if (priceProducts.isEmpty()) {
            System.out.println("There aren't products in that range :c");
        } else {
            if (opt2 == 1) {
                priceProducts.sort(Comparator.comparingDouble(Product::getPrice)); // Orden ascendente
            } else if (opt2 == 2) {
                priceProducts.sort(Comparator.comparingDouble(Product::getPrice).reversed()); // Orden descendente
            } else {
                System.out.println("Invalid option. -.-");
                return;
            }

            for (Product p : priceProducts) {
                System.out.println(p.getName() + " and the price is: " + p.getPrice());
            }
        }
    }

    public void searchByQuantityRange(int minQuantity, int maxQuantity, int opt2) {

        List<Product> quantityProducts = new ArrayList<>();

        for (Product p : productList) {
            double value = p.getNumSales();
            if (value >= minQuantity && value <= maxQuantity) {
                quantityProducts.add(p);
            }
        }

        if (quantityProducts.isEmpty()) {
            System.out.println("There aren't products in that range :c");

        } else {
            if (opt2 == 1) {
                quantityProducts.sort(Comparator.comparingInt(Product::getNumSales)); // Orden ascendente
            } else if (opt2 == 2) {
                quantityProducts.sort(Comparator.comparingInt(Product::getNumSales).reversed()); // Orden descendente
            } else {
                System.out.println("Invalid option. -.-");
                return;
            }

            for (Product p : quantityProducts) {
                System.out.println(p.getName() + " and the quantity is: " + p.getNumSales());
            }
        }
    }

    public void incrementProductQuantity(String productName, int quantity) {
        // Buscar el producto por nombre
        Product product = null;
        for (Product p : productList) {
            if (p.getName().equals(productName)) {
                product = p;
                break;
            }
        }
        // Si el producto existe, incrementar su cantidad
        if (product != null) {
            product.setNumSales(product.getNumSales() + quantity);
            System.out.println("Product quantity updated successfully.");
        } else {
            System.out.println("Product not found.");
        }
    }

    public void searchAndPrintByNameRange(String startPrefix, String endPrefix, int opt2) {
        List<Product> nameProducts = new ArrayList<>();

        for (Product p : productList) {
            String name = p.getName();
            if (name.compareToIgnoreCase(startPrefix) >= 0 && name.compareToIgnoreCase(endPrefix) <= 0) {
                nameProducts.add(p);
            }
        }

        if (nameProducts.isEmpty()) {
            System.out.println("There aren't products in that range :c");
        } else {
            if (opt2 == 1) {
                nameProducts.sort(Comparator.comparing(Product::getName)); // Orden ascendente
            } else if (opt2 == 2) {
                nameProducts.sort(Comparator.comparing(Product::getName).reversed()); // Orden descendente
            } else {
                System.out.println("Invalid option. -.-");
                return;
            }

            for (Product p : nameProducts) {
                System.out.println(p.getName() + " and the quantity is: " + p.getNumSales());
            }
        }
    }






    public List<Product> getProductList() {
        return productList;
    }


}

