package model;

public class Product {

    private String name;
    private String description;
    private double price;
    private String category;
    private int numSales;
    
    public Product(String name, String description, double price, String category, int numSales) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
        this.numSales = numSales;
    }
    


    
    // getters and setters for all attributes
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public double getPrice() {
        return price;
    }
    
    public void setPrice(double price) {
        this.price = price;
    }
    
    public String getCategory() {
        return category;
    }
    
    public void setCategory(String category) {
        this.category = category;
    }
    
    public int getNumSales() {
        return numSales;
    }
    
    public void setNumSales(int numSales) {
        this.numSales = numSales;
    }
}
