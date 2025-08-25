package com.busopt.app;

import com.busopt.model.*;
import com.busopt.service.*;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Graph graph = new Graph();
        addDemoGraph(graph);

        Scanner sc = new Scanner(System.in);
        System.out.print("Enter source city: ");
        String source = sc.nextLine().trim();
        System.out.print("Enter destination city: ");
        String dest = sc.nextLine().trim();
        System.out.print("Choose weight (distance/time/cost): ");
        String weight = sc.nextLine().trim();
        sc.close();

        RoutingService routingService = new RoutingService(graph);
        Path path = routingService.findShortestPath(source, dest, weight);

        if (path != null) {
            System.out.println("\n=== Route Result ===");
            System.out.println("Path: " + path);
            System.out.println("Total distance: " + path.getTotalDistance());
            System.out.println("Total time: " + path.getTotalTime());
            System.out.println("Total cost: " + path.getTotalCost());
            System.out.println("Selected weight (" + weight + "): " + path.getWeight(weight));

            // Export result to CSV
            CsvExporter.exportPath(path, weight, "routes_export.csv");
        } else {
            System.out.println("No route found from " + source + " to " + dest);
        }

    }

    private static void addDemoGraph(Graph graph) {
        graph.addCity(new City(1, "Madurai"));
        graph.addCity(new City(2, "Trichy"));
        graph.addCity(new City(3, "Chennai"));

        graph.addEdge(new Edge(1, 2, 150, 120, 200)); // Madurai->Trichy
        graph.addEdge(new Edge(2, 3, 310, 300, 250)); // Trichy->Chennai
        graph.addEdge(new Edge(1, 3, 500, 480, 600)); // Madurai->Chennai direct
    }
}
