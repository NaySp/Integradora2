package model;
import java.time.LocalTime;
import java.util.List;
import java.time.LocalTime;
import java.util.Locale;

public class Order {

    private String buyerName;
    private List<Product> productList;
    private int quantity;
    private double total;
    private LocalTime date;
    
    public Order(String buyerName, List<Product> productList, int quantity) {
        this.buyerName = buyerName;
        this.productList = productList;
        this.quantity = quantity;
        this.total = total;
        date = LocalTime.now();
    }



    // getters and setters for all attributes
    public String getBuyerName() {
        return buyerName;
    }
    
    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
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


    public LocalTime getDate() {
        return date;
    }

    public void setDate(LocalTime date) {
        this.date = date;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }
}
