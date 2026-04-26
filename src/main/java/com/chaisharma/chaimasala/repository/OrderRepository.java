package com.chaisharma.chaimasala.repository;

import com.chaisharma.chaimasala.model.Order;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderRepository extends MongoRepository<Order, String> {

    List<Order> findByCustomerName(String customerName);                       // my orders

    List<Order> findByStatus(String status);                                   // filter by status

    List<Order> findByProductId(String productId);                             // admin analytics

    List<Order> findByOrderDateBetween(LocalDateTime from, LocalDateTime to);  // date range report

    List<Order> findByStatusNotIn(List<String> statuses);                      // active orders only
}
