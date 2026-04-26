package com.chaisharma.chaimasala.service;

import com.chaisharma.chaimasala.exception.ResourceNotFoundException;
import com.chaisharma.chaimasala.model.Product;
import com.chaisharma.chaimasala.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    // ── All active products (shown on homepage) ───────────────────────────────
    public List<Product> getAllActiveProducts() {
        return productRepository.findByIsActiveTrue();
    }

    // ── All products including inactive (admin view) ──────────────────────────
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // ── Get single product by ID ──────────────────────────────────────────────
    public Product getProductById(String id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", id));
    }

    // ── Add new product (admin) ───────────────────────────────────────────────
    public Product addProduct(Product product) {
        product.setActive(true);
        return productRepository.save(product);
    }

    // ── Update product ────────────────────────────────────────────────────────
    public Product updateProduct(String id, Product updatedProduct) {
        Product existing = getProductById(id);

        if (updatedProduct.getName() != null)        existing.setName(updatedProduct.getName());
        if (updatedProduct.getDescription() != null) existing.setDescription(updatedProduct.getDescription());
        if (updatedProduct.getPrice() > 0)           existing.setPrice(updatedProduct.getPrice());
        if (updatedProduct.getImageUrl() != null)    existing.setImageUrl(updatedProduct.getImageUrl());
        if (updatedProduct.getCategory() != null)    existing.setCategory(updatedProduct.getCategory());
        if (updatedProduct.getStockQty() >= 0)       existing.setStockQty(updatedProduct.getStockQty());

        return productRepository.save(existing);
    }

    // ── Soft delete — hides from customers, keeps in DB ──────────────────────
    public void deactivateProduct(String id) {
        Product product = getProductById(id);
        product.setActive(false);
        productRepository.save(product);
    }

    // ── Hard delete ───────────────────────────────────────────────────────────
    public void deleteProduct(String id) {
        productRepository.delete(getProductById(id));
    }

    // ── Reduce stock when order is placed ─────────────────────────────────────
    public void reduceStock(String productId, int quantity) {
        Product product = getProductById(productId);
        int newQty = product.getStockQty() - quantity;
        if (newQty < 0) {
            throw new IllegalStateException(
                    "Insufficient stock for: " + product.getName());
        }
        product.setStockQty(newQty);
        productRepository.save(product);
    }
}
