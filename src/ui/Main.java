package ui;

import model.Controller;
import model.Product;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main {

    private Controller controller;
    private Scanner reader;

    // constructor de la clase 
    public Main() {
        reader = new Scanner(System.in);
        controller = new Controller();
    }

    public Scanner getReader() {
        return this.reader;
    }

    public static void main(String[] args) {

        Main view = new Main(); // la llamada al constructor de la clase

        view.controller.readData();

        int option = 0;

        do {
            view.menu();
            option = view.validateIntegerInput();
            view.executeOption(option);

        } while (option != 0);
        view.reader.close();
        view.controller.saveData();

    }

    public void menu() {

        System.out.println("1. Register Product \n" +
                "2. Create order\n" +
                "3. Find a product\n" +
                "4. Find order \n" +
                "0. Salir");
        System.out.print("option -> ");

    }

    public void executeOption(int option) {

        switch (option) {

            case 1:
                addProduct();


                break;

            case 2:
                //* registerOrder();


                break;

            case 3:
                //* findProduct();


                break;

            case 0:
                System.out.println("Exit");
                break;

            default:
                System.out.println("Invalid Option, try again :c ");

        }

    }

    public int validateIntegerInput() {
        int option = 0;
        if (reader.hasNextInt()) {
            option = reader.nextInt();
        } else {
            reader.nextLine();// limpiar el scanner 
            option = -1;
            System.out.println("Type an int.");
        }
        return option;
    }

    public double validateDouble() {
        double option = -1;
        try {
            option = reader.nextDouble();
        } catch (InputMismatchException e) {
            reader.next();
            System.out.println("Please enter a valid double.");
        }
        return option;
    }

    public void addProduct(){
        int numSales = 0;

        boolean datosCorrectos = false;

        do {
            System.out.println("Type the name of the product: ");
            String name = reader.next();
            reader.nextLine();
            System.out.println("Type a quick description of the product: ");
            String productName = reader.next();
            reader.nextLine();
            System.out.println("Type the price of the product: ");
            double price = validateDouble();
            System.out.println("Type the quantity of this product: ");
            numSales = validateIntegerInput();

            if (name.isEmpty() || productName.isEmpty() ) {
                System.out.println("You must enter a value for all fields. Please try again.");
            } else if (price <= 0 || numSales <= 0) {
                System.out.println("Price and quantity must be greater than zero. Please try again.");
            } else {
                System.out.println("Choose a category: \n" +
                        "1. Libros \n" +
                        "2. Electrónica \n" +
                        "3. Ropa y accesorios \n" +
                        "4. Alimentos y bebidas \n" +
                        "5. Papelería \n" +
                        "6. Deportes \n" +
                        "7. Productos de belleza y cuidado personal \n" +
                        "8. Juguetes y juegos \n");
                System.out.print("Option -> ");
                int category = reader.nextInt();
                if(category < 0 && category > 9){
                    System.out.println("Choose a valid category");
                }else {
                    switch (category) {

                        case 1://Libros
                            controller.rProduct(name, productName, price, "Libros", numSales);
                            datosCorrectos = true;
                            break;

                        case 2://Electronica
                            controller.rProduct(name, productName, price, "Electronica", numSales);
                            datosCorrectos = true;

                            break;
                        case 3://Ropa y accesorios
                            controller.rProduct(name, productName, price, "Ropa y accesorios", numSales);
                            datosCorrectos = true;
                            break;

                        case 4://Alimentos y bebidas
                            controller.rProduct(name, productName, price, "Alimentos y bebidas", numSales);
                            datosCorrectos = true;
                            break;

                        case 5://Papeleria
                            controller.rProduct(name, productName, price, "Papeleria", numSales);
                            datosCorrectos = true;
                            break;

                        case 7://Deportes
                            controller.rProduct(name, productName, price, "Deportes", numSales);
                            datosCorrectos = true;
                            break;

                        case 8://Productos de belleza y cuidado personal
                            controller.rProduct(name, productName, price, "Productos de belleza y cuidado personal", numSales);
                            datosCorrectos = true;
                            break;
                        case 9:// Juguetes y juegos
                            controller.rProduct(name, productName, price, "Juguetes y juegos", numSales);
                            datosCorrectos = true;

                            break;
                    }
                }
            }

        } while (!datosCorrectos);

    }

   /*
    public void registerOrder() {
        List<Product> products = new ArrayList<>();
        System.out.println("Type your name: ");
        String buyerName = reader.next();

        boolean addingProducts = true;
        while(addingProducts){
            System.out.println("Type the product name you want to add: ");
            String productName = reader.next();
            System.out.println("Type how much of this product you want: ");
            int quantity = validateIntegerInput();

            Product newProduct = new Product(productName, "", 0.0, "", quantity);
            products.add(newProduct);

            System.out.println("Do you want to add another product? \n" +
                    "1. Yes" +
                    "2. No");
            int input = reader.nextInt();
            if(input == 2){
                addingProducts = false;
            }
        }

        controller.registerOrder(buyerName, products);
    }

    public void findProduct() {

        System.out.println("Type by which characteristic you want to search for a product: \n1. Name  \n2. Price  \n3. Category   \n4. Number of times purchased. \n0. Exit.");
        System.out.print("-> option: ");
        int opt = validateIntegerInput();

        switch(opt){

            case 1:
                System.out.println("Type the name of the product u r looking for: ");
                String productName = reader.next();
                System.out.println(controller.validateProductByName(productName));
            break;

            case 2:
                System.out.println("Type the price at which you would like to find a product");
                double price = validateDouble();
                System.out.println(controller.validateProductByPrice(price));
            break;

            case 3:
                System.out.println("Type the category u r looking for: ");
                String category = reader.next();
                System.out.println(controller.validateProductByCategory(category));
            break;

            case 4:
                System.out.println("Enter the number of units for which you want to search for a product");
                int timeSold = validateIntegerInput();
                System.out.println(controller.validateProductByTimePurchased(timeSold));
            break;

            case 0: System.out.println("Exit");
                break;

            default: System.out.println("Invalid Option, try again :c ");

        }
    }*/






}