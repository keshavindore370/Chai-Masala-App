package com.chaisharma.chaimasala.repository;

import com.chaisharma.chaimasala.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends MongoRepository<Product, String> {

    List<Product> findByIsActiveTrue();                           // homepage — active only

    List<Product> findByCategory(String category);               // filter by category

    List<Product> findByNameContainingIgnoreCase(String keyword); // search by name

    List<Product> findByStockQtyLessThan(int threshold);         // low stock alert
}
