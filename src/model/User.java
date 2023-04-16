package model;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String name;
    private List<Order> orders;
    
    public User(String name) {
        this.name = name;
        this.orders = new ArrayList<>();
    }
    

    // getters and setters for name and orders
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public List<Order> getOrders() {
        return orders;
    }
    
    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
}

