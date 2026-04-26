package com.chaisharma.chaimasala.controller;

import com.chaisharma.chaimasala.DTO.OrderResponseDTO;
import com.chaisharma.chaimasala.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    // ── Get all orders — admin only ───────────────────────────────────────────
    @GetMapping
    public ResponseEntity<List<OrderResponseDTO>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    // ── Get single order by ID ────────────────────────────────────────────────
    @GetMapping("/{id}")
    public ResponseEntity<OrderResponseDTO> getOrderById(@PathVariable String id) {
        return ResponseEntity.ok(orderService.getOrderById(id));
    }

    // ── Get my orders (logged-in user) ────────────────────────────────────────
    @GetMapping("/my-orders")
    public ResponseEntity<List<OrderResponseDTO>> getMyOrders(Principal principal) {
        return ResponseEntity.ok(
                orderService.getOrdersByCustomer(principal.getName()));
    }

    // ── Update order status ───────────────────────────────────────────────────
    @PutMapping("/{id}/status")
    public ResponseEntity<OrderResponseDTO> updateStatus(
            @PathVariable String id,
            @RequestParam String status) {
        return ResponseEntity.ok(orderService.updateOrderStatus(id, status));
    }

    // ── Cancel order ──────────────────────────────────────────────────────────
    @PutMapping("/{id}/cancel")
    public ResponseEntity<OrderResponseDTO> cancelOrder(@PathVariable String id) {
        return ResponseEntity.ok(orderService.cancelOrder(id));
    }
}
