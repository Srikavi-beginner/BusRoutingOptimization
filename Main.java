package com.busopt;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try {
            Path csv = Paths.get("edges.csv"); 
            System.out.println("Loading graph from: " + csv.toAbsolutePath());
            Graph g = CsvLoader.loadFromEdgesCsv(csv);
            System.out.println("Loaded " + g.getCities().size() + " city(ies).");

            Scanner sc = new Scanner(System.in);
            System.out.print("Enter SOURCE city: ");
            String srcName = sc.nextLine().trim();
            System.out.print("Enter DESTINATION city: ");
            String dstName = sc.nextLine().trim();

            int srcId = g.getCityIdByName(srcName);
            int dstId = g.getCityIdByName(dstName);

            RoutingService routing = new RoutingService();
            // For "cheapest path" we optimize by COST
            RoutingService.Route r = routing.shortestPath(g, srcId, dstId, WeightType.COST);

            if (r == null) {
                System.out.println("No route found between " + srcName + " and " + dstName);
                return;
            }

            System.out.println("\nCheapest path from " + srcName + " to " + dstName + ":");
            System.out.println("Path: " + r.signature(g));
            System.out.println("Total Cost: " + String.format("%.2f", r.totalCost));
            System.out.println("Total Distance (km): " + String.format("%.2f", r.totalDistanceKm));
            System.out.println("Total Time (min): " + String.format("%.2f", r.totalTimeMin));

            // export optional
            try {
                Path out = Paths.get("last_route.csv");
                CsvExporter.exportRoute(out, g, r, WeightType.COST);
                System.out.println("\nExported result to: " + out.toAbsolutePath());
            } catch (Exception ex) {
                System.out.println("Failed to export CSV: " + ex.getMessage());
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
