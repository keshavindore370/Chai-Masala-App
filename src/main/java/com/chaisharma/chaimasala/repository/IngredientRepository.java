package com.chaisharma.chaimasala.repository;

import com.chaisharma.chaimasala.model.Ingredient;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IngredientRepository extends MongoRepository<Ingredient, String> {

    Optional<Ingredient> findByName(String name);

    List<Ingredient> findByUsedInProductIdsContaining(String productId);

    List<Ingredient> findByIsAvailableTrue();
}
