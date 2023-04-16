package model;
import java.util.Date;


public class Order {

    private String buyerName;
    private String productName;
    private int quantity;
    private double total;
    private Date date;
    
    public Order(String buyerName, String productName, int quantity, double total, Date date) {
        this.buyerName = buyerName;
        this.productName = productName;
        this.quantity = quantity;
        this.total = total;
        this.date = date;
    }

    
    
    // getters and setters for all attributes
    public String getBuyerName() {
        return buyerName;
    }
    
    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }
    
    public String getProductName() {
        return productName;
    }
    
    public void setProductName(String productName) {
        this.productName = productName;
    }
    
    public int getQuantity() {
        return quantity;
    }
    
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
    public double getTotal() {
        return total;
    }
    
    public void setTotal(double total) {
        this.total = total;
    }
    
    public Date getDate() {
        return date;
    }
    
    public void setDate(Date date) {
        this.date = date;
    }
}
