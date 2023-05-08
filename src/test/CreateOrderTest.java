package test;

import exception.InvalidNameException;
import model.*;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

public class CreateOrderTest {

    private Controller orderRegistration;


    @Test
    public void testCreateOrder(){
        List<Product> products = new ArrayList<>();
        orderRegistration.rProduct("Producto 1", "", 10.0, "", 100);
        orderRegistration.rProduct("Producto 2", "", 20.0, "", 200);
        products.add(orderRegistration.getProductList().get(0));
        products.add(orderRegistration.getProductList().get(1));


    }
    @Test
    public void testRegisterOrderWithValidProducts() throws InvalidNameException {
        List<Product> products = new ArrayList<>();
        orderRegistration.rProduct("Producto 1", "", 10.0, "", 100);
        orderRegistration.rProduct("Producto 2", "", 20.0, "", 200);
        products.add(orderRegistration.getProductList().get(0));
        products.add(orderRegistration.getProductList().get(1));
        int quantity = 2;
        int increaseAmount = 1;
        orderRegistration.registerOrder("Comprador 1", products, quantity, increaseAmount);
        assertEquals(1, orderRegistration.getOrders().size());
    }

    @Test(expected = InvalidNameException.class)
    public void testRegisterOrderWithInvalidProduct() throws InvalidNameException {
        List<Product> products = new ArrayList<>();
        products.add(new Product("Producto inválido","", 10.0, "",100));
        int quantity = 2;
        int increaseAmount = 1;
        orderRegistration.registerOrder("Comprador 2", products, quantity, increaseAmount);
    }

    @Test
    public void testValidateOrderByDateWithMatchingDates() {
        // Preparación de los datos
        List<Product> products1 = Arrays.asList(new Product("Product 1","", 10.0, "", 4), new Product("Product 2","", 20.0, "", 5));
        List<Product> products2 = Arrays.asList(new Product("Product 3", "", 30.0, "", 6), new Product("Product 4", "",  40.0, "", 45));
        Order order1 = new Order("John Doe", products1, 2, 50.0, LocalDate.of(2022, 5, 7).toString());
        Order order2 = new Order("Jane Smith", products2, 2, 70.0, LocalDate.of(2022, 5, 7).toString());
        Order order3 = new Order("Bob Johnson", products2, 3, 110.0, LocalDate.of(2022, 5, 8).toString());
        List<Order> orders = new ArrayList<>();
        orders.add(order1);
        orders.add(order2);
        orders.add(order3);
        LocalDate date = LocalDate.of(2022, 5, 7);

        // Ejecución del método a probar
        String result = orderRegistration.validateOrderByDate(date);

        // Comprobación del resultado
        assertEquals("The following orders were placed on 2022-05-07:\nJohn Doe\nJane Smith", result);
    }

    @Test
    public void testValidateOrderByDateWithNoMatchingDates() {
        // Preparación de los datos
        List<Product> products1 = Arrays.asList(new Product("Product 1", "",10.0, "",20), new Product("Product 2", "",20.0, "", 45));
        List<Product> products2 = Arrays.asList(new Product("Product 3","", 30.0, "", 3), new Product("Product 4", "",40.0, "", 4));
        Order order1 = new Order("John Doe", products1, 2, 50.0, LocalDate.of(2022, 5, 7).toString());
        Order order2 = new Order("Jane Smith", products2, 2, 70.0, LocalDate.of(2022, 5, 7).toString());
        Order order3 = new Order("Bob Johnson", products2, 3, 110.0, LocalDate.of(2022, 5, 8).toString());
        List<Order> orders = new ArrayList<>();
        orders.add(order1);
        orders.add(order2);
        orders.add(order3);
        LocalDate date = LocalDate.of(2022, 5, 6);

        // Ejecución del método a probar
        String result = orderRegistration.validateOrderByDate(date);

        // Comprobación del resultado
        assertEquals("There are no orders for the date 2022-05-06", result);
    }









}
