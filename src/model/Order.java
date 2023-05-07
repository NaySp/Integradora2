package model;
import java.time.LocalTime;
import java.util.List;
import java.time.LocalTime;
import java.util.Locale;

import java.time.LocalDate;
import java.util.List;

public class Order {
    private String buyerName;
    private List<Product> products;
    private double totalPrice;
    private LocalDate orderDate;

    public Order(String buyerName, List<Product> products, double totalPrice, LocalDate orderDate) {
        this.buyerName = buyerName;
        this.products = products;
        this.totalPrice = totalPrice;
        this.orderDate = orderDate;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public List<Product> getProducts() {
        return products;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }
}
