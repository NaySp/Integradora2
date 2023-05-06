package test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import model.Controller;

public class RegisterProductTest {

    private Controller productRegistration;

    @Before
    public void setUp() {
        productRegistration = new Controller();
    }

    @Test
    public void testRegisterProduct() {
        // Test adding a new product
        productRegistration.rProduct("Terrenaitor", "Terranaitor el coche mas poderoso que ha existido con traccion 4x4 y dos turbo motores. Este si es todo terreno, las calles son faciles, metelo al lodo, pasa por la nieve, pasa por el agua. Terrenaitor es el mas potente que haya existido ", 29.95, "Juguetes y juegos", 30);
        assertEquals(1, productRegistration.getProductList().size());
    }

    @Test(expected = Exception.class)
    public void testRegisterProductWithDuplicateName() throws Exception {
        // Add the first product
        productRegistration.rProduct("Heladeria Kreisel Supra", "Heladeria kreisel supra hace helados en minutossssss", 22.07, "Juguetes y juegos", 20);

        // Try to add a product with the same name
        try {
            productRegistration.rProduct("Heladeria Kreisel Supra", "La tienda", 100000.99, "Alimentos y Bebidas", 1);
            //fail("Expected an Exception to be thrown");

        } catch (Exception e) {
            assertEquals("A product with the same name already exists.", e.getMessage());
            throw e;
        }

    }
}
