package com.chaisharma.chaimasala.service;

import com.chaisharma.chaimasala.DTO.OrderRequestDTO;
import com.chaisharma.chaimasala.DTO.OrderResponseDTO;
import com.chaisharma.chaimasala.exception.ResourceNotFoundException;
import com.chaisharma.chaimasala.model.Order;
import com.chaisharma.chaimasala.model.Product;
import com.chaisharma.chaimasala.repository.OrderRepository;
import com.chaisharma.chaimasala.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    // ── Place new order ───────────────────────────────────────────────────────
    public OrderResponseDTO placeOrder(String customerName, OrderRequestDTO dto) {

        Product product = productRepository.findById(dto.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Product", "id", dto.getProductId()));

        if (product.getStockQty() < dto.getQuantity()) {
            throw new IllegalStateException(
                    "Only " + product.getStockQty() + " units available in stock.");
        }

        double totalBill = product.getPrice() * dto.getQuantity();

        Order order = new Order();
        order.setCustomerName(customerName);
        order.setProductId(dto.getProductId());
        order.setProductName(product.getName());
        order.setWeight(dto.getWeight());
        order.setQuantity(dto.getQuantity());
        order.setTotalBill(totalBill);
        order.setAddress(dto.getAddress());
        order.setPincode(dto.getPincode());
        order.setPhone(dto.getPhone());
        order.setPaymentType(
                dto.getPaymentType() != null ? dto.getPaymentType() : "Cash on Delivery");
        order.setStatus("PLACED");
        order.setOrderDate(LocalDateTime.now());
        order.setEstimatedDelivery("30-45 Minutes");

        Order saved = orderRepository.save(order);

        // Reduce stock after saving order
        product.setStockQty(product.getStockQty() - dto.getQuantity());
        productRepository.save(product);

        return toDTO(saved);
    }

    // ── All orders (admin panel) ──────────────────────────────────────────────
    public List<OrderResponseDTO> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // ── Orders by customer name (my orders page) ──────────────────────────────
    public List<OrderResponseDTO> getOrdersByCustomer(String customerName) {
        return orderRepository.findByCustomerName(customerName).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // ── Single order by ID ────────────────────────────────────────────────────
    public OrderResponseDTO getOrderById(String id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order", "id", id));
        return toDTO(order);
    }

    // ── Update order status (admin) ───────────────────────────────────────────
    public OrderResponseDTO updateOrderStatus(String orderId, String newStatus) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order", "id", orderId));
        order.setStatus(newStatus);
        return toDTO(orderRepository.save(order));
    }

    // ── Cancel order ──────────────────────────────────────────────────────────
    public OrderResponseDTO cancelOrder(String orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order", "id", orderId));

        if ("DELIVERED".equals(order.getStatus())) {
            throw new IllegalStateException("Cannot cancel a delivered order.");
        }

        order.setStatus("CANCELLED");
        Order saved = orderRepository.save(order);

        // Restore stock when cancelled
        productRepository.findById(order.getProductId()).ifPresent(product -> {
            product.setStockQty(product.getStockQty() + order.getQuantity());
            productRepository.save(product);
        });

        return toDTO(saved);
    }

    // ── Private: Order entity → OrderResponseDTO ──────────────────────────────
    private OrderResponseDTO toDTO(Order order) {
        OrderResponseDTO dto = new OrderResponseDTO();
        dto.setId(order.getId());
        dto.setCustomerName(order.getCustomerName());
        dto.setProductName(order.getProductName());
        dto.setWeight(order.getWeight());
        dto.setQuantity(order.getQuantity());
        dto.setTotalBill(order.getTotalBill());
        dto.setAddress(order.getAddress());
        dto.setPincode(order.getPincode());
        dto.setPhone(order.getPhone());
        dto.setPaymentType(order.getPaymentType());
        dto.setStatus(order.getStatus());
        dto.setOrderDate(order.getOrderDate());
        dto.setEstimatedDelivery(order.getEstimatedDelivery());
        return dto;
    }
}
