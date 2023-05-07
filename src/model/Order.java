package model;
import java.time.LocalTime;
import java.util.List;

import java.time.LocalDate;
import java.util.List;


public class Order {

    private String buyerName;
    private List<Product> products;
    private int quantity;
    private double totalPrice;
    private String orderDate;

    public Order(String buyerName, List<Product> products, int quantity, double totalPrice, String orderDate) {
        this.buyerName = buyerName;
        this.products = products;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.orderDate = LocalTime.now().toString();
    }



    // getters and setters for all attributes
    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProductName(List<Product> products) {
        this.products = products;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getTotal() {
        return totalPrice;
    }

    public void setTotal(double total) {
        this.totalPrice = total;
    }


}