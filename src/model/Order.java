package model;
import java.time.LocalTime;
import java.util.List;


public class Order {

    private String buyerName;
    private List<Product> productList;
    private int quantity;
    private double total;
    private String date;  //** Se modificó el localTime.now() y al momento de importarlo
    // donde vayamos a importarlo sería tipo LocalDate.now.toString() porque sino el gson genera
    // conflicto con las fechas. */
    
    public Order(String buyerName, List<Product> productList, int quantity) {
        this.buyerName = buyerName;
        this.productList = productList;
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

    public String getDate(){return date;}

    public void setDate(String date){this.date = date;}


    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }
}
