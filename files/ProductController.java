package com.chaisharma.chaimasala.controller;

import com.chaisharma.chaimasala.DTO.OrderRequestDTO;
import com.chaisharma.chaimasala.DTO.OrderResponseDTO;
import com.chaisharma.chaimasala.service.OrderService;
import com.chaisharma.chaimasala.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderService orderService;

    // ── Home page ─────────────────────────────────────────────────────────────
    @GetMapping("/")
    public String home(Model model,
                       @AuthenticationPrincipal OAuth2User oAuth2User,
                       Principal principal) {
        if (oAuth2User != null) {
            String name = oAuth2User.getAttribute("name");
            model.addAttribute("name", name != null ? name : oAuth2User.getAttribute("email"));
        } else if (principal != null) {
            model.addAttribute("name", principal.getName());
        }
        model.addAttribute("products", productService.getAllActiveProducts());
        return "index";
    }

    // ── Adrak chai detail page ────────────────────────────────────────────────
    @GetMapping("/adrak-chai")
    public String viewAdrakDetails(Model model, Principal principal) {
        if (principal != null) model.addAttribute("name", principal.getName());
        return "adrak-details";
    }

    // ── Cart page ─────────────────────────────────────────────────────────────
    @GetMapping("/cart")
    public String showCartPage(Model model, Principal principal) {
        if (principal != null) model.addAttribute("name", principal.getName());
        model.addAttribute("products", productService.getAllActiveProducts());
        return "cart";
    }

    // ── Place order — standard form POST (no JavaScript needed) ───────────────
    @PostMapping("/place-order")
    public String placeOrderForm(Principal principal,
                                 @AuthenticationPrincipal OAuth2User oAuth2User,
                                 @ModelAttribute OrderRequestDTO orderRequest) {
        String customerName = resolveName(principal, oAuth2User);
        OrderResponseDTO response = orderService.placeOrder(customerName, orderRequest);
        return "redirect:/order-success?id=" + response.getId();
    }

    // ── Place order — JSON API for Fetch/SPA (no page reload) ─────────────────
    @PostMapping(value = "/api/place-order", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<OrderResponseDTO> placeOrderApi(
            Principal principal,
            @AuthenticationPrincipal OAuth2User oAuth2User,
            @RequestBody OrderRequestDTO orderRequest) {
        String customerName = resolveName(principal, oAuth2User);
        return ResponseEntity.ok(orderService.placeOrder(customerName, orderRequest));
    }

    // ── Order success page ────────────────────────────────────────────────────
    @GetMapping("/order-success")
    public String showSuccessPage(@RequestParam(required = false) String id, Model model) {
        if (id != null) model.addAttribute("order", orderService.getOrderById(id));
        model.addAttribute("orderId", id);
        return "order-success";
    }

    // ── Order tracking page ───────────────────────────────────────────────────
    @GetMapping("/order-tracking/{id}")
    public String trackOrder(@PathVariable String id, Model model) {
        model.addAttribute("order", orderService.getOrderById(id));
        return "order-tracking";
    }

    // ── Admin: all orders ─────────────────────────────────────────────────────
    @GetMapping("/admin/orders")
    public String showAdminOrders(Model model) {
        List<OrderResponseDTO> orders = orderService.getAllOrders();
        model.addAttribute("orders", orders);
        return "admin-orders";
    }

    // ── Admin: update order status ────────────────────────────────────────────
    @PostMapping("/admin/update-status")
    public String updateOrderStatus(@RequestParam String orderId,
                                    @RequestParam String status) {
        orderService.updateOrderStatus(orderId, status);
        return "redirect:/admin/orders";
    }

    // ── Cancel order ──────────────────────────────────────────────────────────
    @PostMapping("/order/cancel")
    public String cancelOrder(@RequestParam String orderId) {
        orderService.cancelOrder(orderId);
        return "redirect:/admin/orders";
    }

    // ── Helper: get display name from either login type ───────────────────────
    private String resolveName(Principal principal, OAuth2User oAuth2User) {
        if (oAuth2User != null) {
            String name = oAuth2User.getAttribute("name");
            return name != null ? name : oAuth2User.getAttribute("email");
        }
        return (principal != null) ? principal.getName() : "Guest";
    }
}
