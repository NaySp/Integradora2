package ui;

import model.Controller;
import model.Product;

import java.time.LocalDate;
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
                "5. Search by numeric range\n" +
                "6. Search by String range\n" +
                "0. Salir");
        System.out.print("option -> ");

    }

    public void executeOption(int option) {

        switch (option) {

            case 1:
                addProduct();


                break;

            case 2:
                registerOrder();


                break;

            case 3:
                findProduct();


                break;

            case 4:
                findOrder();

                break;

            case 5:
                searchByRange();


                break;

            case 6:
                searchStringRange();

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

    public void addProduct() {
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

            if (name.isEmpty() || productName.isEmpty()) {
                System.out.println("You must enter a value for all fields. Please try again.");
            } else if (price <= 0 || numSales <= 0) {
                System.out.println("Price and quantity must be greater than zero. Please try again. :(");
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
                if (category < 0 && category > 9) {
                    System.out.println("Choose a valid category");
                } else {
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
                    System.out.println("Do you want to add more quantity to the product? (y/n)");
                    String option = reader.next();
                    if (option.equalsIgnoreCase("y")) {
                        System.out.println("Type the quantity to add:");
                        int quantityToAdd = validateIntegerInput();
                        controller.incrementProductQuantity(name, quantityToAdd);
                    }else{
                        controller.rProduct(name,productName,price, String.valueOf(category), numSales);
                        break;
                    }
                }

            }


        } while (!datosCorrectos);


    }


    public void registerOrder() {
        List<Product> products = new ArrayList<>();
        System.out.println("Type your name: ");
        String buyerName = reader.next();

        boolean addingProducts = true;
        int quantity = 0;
        int increaseAmount = 0;
        while (addingProducts) {
            System.out.println("Type the product name you want to add: ");
            String productName = reader.next();
            System.out.println("Type how much of this product you want: ");
            quantity = validateIntegerInput();

            Product newProduct = new Product(productName, "", 0.0, "", quantity);
            products.add(newProduct);

            System.out.println("Do you want to add another product? \n" +
                    "1. Yes" +
                    "2. No");
            increaseAmount = reader.nextInt();
            if (increaseAmount == 2) {
                addingProducts = false;
            }
        }

        controller.rOrder(buyerName, products, quantity, increaseAmount);
    }

    public void findProduct() {

        System.out.println("Type by which characteristic you want to search for a product: \n1. Name  \n2. Price  \n3. Category   \n4. Number of times purchased. \n0. Exit.");
        System.out.print("-> option: ");
        int opt = validateIntegerInput();

        switch (opt) {

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

            case 0:
                System.out.println("Exit");
                break;

            default:
                System.out.println("Invalid Option, try again :c ");

        }
    }

    public void findOrder() {

        System.out.println("Type by which characteristic you want to search for a order: \n1. Buyer Name  \n2. Total Price  \n0. Exit.");
        System.out.print("-> option: ");
        int opt = validateIntegerInput();

        switch (opt) {

            case 1:
                System.out.println("Type the name of the person who made the order: ");
                String nameBuyer = reader.next();
                System.out.println(controller.validateOrderByBuyerName(nameBuyer));
                break;

            case 2:
                System.out.println("Type the total price of the order you would like to search");
                double totalPrice = validateDouble();
                System.out.println(controller.validateOrderByTotal(totalPrice));
                break;

           /* case 3:
                System.out.println("Type the date of the order ");
                String date = reader.next();
                System.out.println(controller.validateOrderByDate(LocalDate.parse(date)));
                break;
            */


            case 0:
                System.out.println("Exit");
                break;

            default:
                System.out.println("Invalid Option, try again :c ");

        }
    }

    public void searchByRange() {

        int opt2 = 0;
        int opt = 0;

        System.out.println("Enter the type of numeric attribute you wish to search for, such as: \n1. Price \n2. Quantity \n0. Exit.");
        System.out.print("-> option: ");
        opt = validateIntegerInput();

        switch (opt) {

            case 1:
                System.out.println("Type the minimum price ;) ");
                double minValue = validateDouble();
                System.out.println("Type the maximummm price ;)) ");
                double maxValue = validateDouble();

                System.out.println("Finally type 1 if you want to see it in ascending order, 2 if descending.");
                opt2 = validateIntegerInput();

                controller.searchByPriceRange(minValue, maxValue, opt2);


                break;

            case 2:
                System.out.println("Type the minimum quantity ;) ");
                int minQuantity = validateIntegerInput();
                System.out.println("Type the maximummm quantity ;)) ");
                int maxQuantity = validateIntegerInput();

                System.out.println("Finally type 1 if you want to see it in ascending order, 2 if descending.");
                opt2 = validateIntegerInput();

                controller.searchByQuantityRange(minQuantity, maxQuantity, opt2);


                break;

            default:
                System.out.println("invalid optionnnn");
        }
    }

    public void searchStringRange(){

        System.out.println("Type from which letter you want to search for a product ");
        String letter = reader.next();
        System.out.println("type up to which letter you want to search for a product");
        String letter2 = reader.next();

        System.out.println("Finally type 1 if you want to see it in ascending order, 2 if descending.");
        int opt3 = validateIntegerInput();

        controller.searchAndPrintByNameRange(letter, letter2, opt3);
    }

}