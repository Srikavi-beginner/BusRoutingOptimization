package com.busopt.model;

public class Edge {
    private int fromCity;
    private int toCity;
    private double distance;
    private double time;
    private double cost;

    public Edge(int fromCity, int toCity, double distance, double time, double cost) {
        this.fromCity = fromCity;
        this.toCity = toCity;
        this.distance = distance;
        this.time = time;
        this.cost = cost;
    }

    public int getFromCity() { return fromCity; }
    public int getToCity() { return toCity; }
    public double getDistance() { return distance; }
    public double getTime() { return time; }
    public double getCost() { return cost; }

    public double getWeight(String weightType) {
        switch (weightType.toLowerCase()) {
            case "time": return time;
            case "cost": return cost;
            default: return distance;
        }
    }

    @Override
    public String toString() {
        return fromCity + " -> " + toCity;
    }
}
