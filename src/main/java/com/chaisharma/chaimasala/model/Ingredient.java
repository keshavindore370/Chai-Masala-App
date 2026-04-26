package com.chaisharma.chaimasala.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@Document(collection = "ingredients")
public class Ingredient {

    @Id
    private String id;

    private String name;           // e.g. "Ginger Powder", "Cardamom"

    private String unit;           // e.g. "grams", "ml", "pieces"

    private double quantityPerUnit;

    private List<String> usedInProductIds = new ArrayList<>(); // product IDs

    private double stockAvailable = 0.0;

    private boolean isAvailable = true;
}
