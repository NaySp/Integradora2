package ui; 

import java.util.Scanner; 
import model.*;

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

        int option = 0; 

        do{
            view.menu(); 
            option = view.validateIntegerInput(); 
            view.executeOption(option);

        }while(option != 0);


        view.reader.close();
    }

    public void menu(){
         
        System.out.println("1. Register Product"); 
        System.out.println("2. Create order"); 
        System.out.println("3. Find a product"); 
        System.out.println("0. Salir"); 
    }

    public void executeOption(int option){
        
        switch(option){
        
            

            
            case 0 -> System.out.println("Exit");
    
        
            default -> System.out.println("Invalid Option, try again :c ");
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

    
    
}