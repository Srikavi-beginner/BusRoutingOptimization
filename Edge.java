package com.busopt;

public class Edge {
    private final int id;
    private final int fromId;
    private final int toId;
    private final double distanceKm;
    private final double timeMin;
    private final double cost;

    public Edge(int id, int fromId, int toId, double distanceKm, double timeMin, double cost) {
        this.id = id;
        this.fromId = fromId;
        this.toId = toId;
        this.distanceKm = distanceKm;
        this.timeMin = timeMin;
        this.cost = cost;
    }

    public int getId() { return id; }
    public int getFromId() { return fromId; }
    public int getToId() { return toId; }
    public double getDistanceKm() { return distanceKm; }
    public double getTimeMin() { return timeMin; }
    public double getCost() { return cost; }

    public double weight(WeightType type) {
        switch (type) {
            case DISTANCE: return distanceKm;
            case TIME: return timeMin;
            case COST: return cost;
            default: throw new IllegalArgumentException("Unknown weight");
        }
    }
}
