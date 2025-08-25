package com.busopt.model;

import java.util.*;

public class Path {
    private List<Edge> edges;

    public Path(List<Edge> edges) {
        this.edges = edges;
    }

    public double getTotalDistance() {
        return edges.stream().mapToDouble(Edge::getDistance).sum();
    }

    public double getTotalTime() {
        return edges.stream().mapToDouble(Edge::getTime).sum();
    }

    public double getTotalCost() {
        return edges.stream().mapToDouble(Edge::getCost).sum();
    }

    public double getWeight(String type) {
        switch (type.toLowerCase()) {
            case "time": return getTotalTime();
            case "cost": return getTotalCost();
            default: return getTotalDistance();
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Edge e : edges) {
            sb.append(e.getFromCity()).append(" -> ");
        }
        sb.append(edges.get(edges.size() - 1).getToCity());
        return sb.toString();
    }
}
