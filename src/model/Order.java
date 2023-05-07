package model;
import java.time.LocalTime;
import java.util.List;

import java.time.LocalDate;
import java.util.List;

public class Order {
    private String buyerName;
    private List<Product> products;
    private double totalPrice;
    private String orderDate;

    public Order(String buyerName, List<Product> products, double totalPrice, String orderDate) {
        this.buyerName = buyerName;
        this.productList = productList;
        this.quantity = quantity;
        this.total = total;
        this.orderDate = LocalTime.now().Tostring();
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

    public String getOrderDate() {
        return orderDate;
    }

}
