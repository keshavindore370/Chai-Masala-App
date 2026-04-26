package com.chaisharma.chaimasala.service;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import org.springframework.stereotype.Service;
import jakarta.annotation.PostConstruct;

@Service
public class ChaiGraphService {

    // This creates a graph where the points (vertices) are Strings
    private Graph<String, DefaultEdge> chaiGraph = new SimpleGraph<>(DefaultEdge.class);

    @PostConstruct
    public void init() {
        // Adding your Chai types and ingredients as nodes
        chaiGraph.addVertex("Masala Chai");
        chaiGraph.addVertex("Ginger");
        chaiGraph.addVertex("Cardamom");

        // Connecting them
        chaiGraph.addEdge("Masala Chai", "Ginger");
        chaiGraph.addEdge("Masala Chai", "Cardamom");

        System.out.println("--- Chai Graph Initialized! ---");
    }

    public Graph<String, DefaultEdge> getChaiGraph() {
        return chaiGraph;
    }
}