package ui;

import com.google.gson.Gson;
import model.Controller;

import java.io.File;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main{

    private Controller controller;
    private Scanner reader;  

    // constructor de la clase 
    public Main(){
        reader = new Scanner(System.in);
        controller = new Controller();
    }
    
    public Scanner getReader(){
        return this.reader;
    }

    public static void main(String[] args){

        Main view = new Main(); // la llamada al constructor de la clase

        Gson gson = new Gson();
        File projectDir = new File(System.getProperty("user.dir"));
        File dataDirectory = new File(projectDir+"/data");
        File result = new File(projectDir+"/data/result.json");

        if(!dataDirectory.exists()){
            dataDirectory.mkdirs();
            System.out.println(dataDirectory.exists());
        }



        int option = 0; 

        do{
            view.menu(); 
            option = view.validateIntegerInput(); 
            view.executeOption(option);

        }while(option != 0);
        view.reader.close();

    }

    public void menu(){
         
        System.out.println("1. Register Product \n" +
                "2. Create order\n" +
                "3. Find a product\n" +
                "0. Salir");
        System.out.print("option -> ");

    }

    public void executeOption(int option){
        
        switch(option){
        
            case 1: addProduct();
            break;

            
            case 0: System.out.println("Exit");
            break;
        
            default: System.out.println("Invalid Option, try again :c ");

        }

    }

    public int validateIntegerInput(){
        int option = 0; 
        if(reader.hasNextInt()){
            option = reader.nextInt(); 
        }
        else{
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
            System.out.println("Type a category: ");
            String category = reader.next();
            System.out.println("Finally, type the quantity of this product: ");
            int numSales = validateIntegerInput();

            if (name.isEmpty() || productName.isEmpty() || category.isEmpty()) {
                System.out.println("You must enter a value for all fields. Please try again.");
            } else if (price <= 0 || numSales <= 0) {
                System.out.println("Price and quantity must be greater than zero. Please try again.");
            } else {
                controller.registerProduct(name, productName, price, category, numSales);
                datosCorrectos = true;
            }
        } while (!datosCorrectos);
        
    }



    
    
}