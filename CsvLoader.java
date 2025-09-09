package com.busopt;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class CsvLoader {

    /**
     * CSV file format:
     * from,to,distance_km,time_min,cost
     * Chennai,Bangalore,350,360,800
     */
    public static Graph loadFromEdgesCsv(Path csvPath) throws IOException {
        Graph g = new Graph();
        try (BufferedReader br = Files.newBufferedReader(csvPath)) {
            String line = br.readLine(); // header
            if (line == null) return g;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("#")) continue;
                // simple CSV parse assuming no commas inside city names
                String[] parts = line.split(",");
                if (parts.length < 5) continue;
                String from = parts[0].trim();
                String to = parts[1].trim();
                double dist = Double.parseDouble(parts[2].trim().replace("\"", ""));
                double time = Double.parseDouble(parts[3].trim().replace("\"", ""));
                double cost = Double.parseDouble(parts[4].trim().replace("\"", ""));

                g.addEdgeByNames(from, to, dist, time, cost);
            }
        }
        return g;
    }
}
