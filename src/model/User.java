package model;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String buyerName;
    private List<Order> orders;
    
    public User(String buyerName) {
        this.buyerName = buyerName;
        this.orders = new ArrayList<>();
    }
    

    // getters and setters for name and orders
    public List<Order> getOrders() {
        return orders;
    }
    
    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }
}

