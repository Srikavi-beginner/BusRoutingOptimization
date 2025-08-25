package com.busopt.service;

import com.busopt.model.*;
import java.util.*;

public class RoutingService {
    private Graph graph;

    public RoutingService(Graph graph) {
        this.graph = graph;
    }

    // This is the missing method your Main.java was calling
    public Path findShortestPath(String sourceName, String destName, String weight) {
        City source = graph.getCityByName(sourceName);
        City dest = graph.getCityByName(destName);

        if (source == null || dest == null) {
            System.out.println("Invalid city name(s).");
            return null;
        }
        return dijkstra(source, dest, weight);
    }

    private Path dijkstra(City source, City dest, String weight) {
        Map<City, Double> dist = new HashMap<>();
        Map<City, City> prev = new HashMap<>();
        Map<City, Edge> prevEdge = new HashMap<>();
        PriorityQueue<City> pq = new PriorityQueue<>(Comparator.comparingDouble(dist::get));

        for (City c : graph.getCities()) {
            dist.put(c, Double.MAX_VALUE);
            prev.put(c, null);
        }
        dist.put(source, 0.0);
        pq.add(source);

        while (!pq.isEmpty()) {
            City u = pq.poll();
            if (u.equals(dest)) break;

            for (Edge e : graph.getEdgesFrom(u)) {
                City v = graph.getCityById(e.getToCity());
                double alt = dist.get(u) + e.getWeight(weight);
                if (alt < dist.get(v)) {
                    dist.put(v, alt);
                    prev.put(v, u);
                    prevEdge.put(v, e);
                    pq.add(v);
                }
            }
        }

        if (dist.get(dest) == Double.MAX_VALUE) return null;

        List<Edge> edges = new ArrayList<>();
        for (City at = dest; prev.get(at) != null; at = prev.get(at)) {
            edges.add(prevEdge.get(at));
        }
        Collections.reverse(edges);

        return new Path(edges);
    }
}
