package com.chaisharma.chaimasala;

import com.chaisharma.chaimasala.model.Order;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class OrderTest {

    @Test
    public void testOrderStatus() {
        Order order = new Order();
        order.setStatus("PLACED");
        assertEquals("PLACED", order.getStatus(), "Status match hona chahiye!");
    }

    @Test
    public void testOrderPrice() {
        double price = 75.0;
        int quantity = 2;
        assertEquals(150.0, price * quantity, "Calculation sahi honi chahiye!");
    }
}