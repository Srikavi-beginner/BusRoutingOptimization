package com.busopt;

import java.util.*;

public class Graph {
    private final Map<Integer, City> cities = new HashMap<>();
    private final Map<String, Integer> nameToId = new HashMap<>();
    private final Map<Integer, List<Edge>> adj = new HashMap<>();
    private int nextCityId = 1;
    private int nextEdgeId = 1;

    public Collection<City> getCities() { return cities.values(); }

    public List<Edge> neighbors(int cityId) { return adj.getOrDefault(cityId, Collections.emptyList()); }

    public City getCity(int id) { return cities.get(id); }

    public String getCityName(int id) {
        City c = cities.get(id);
        return c == null ? ("#" + id) : c.getName();
    }

    public int addCity(String name) {
        String key = name.trim().toLowerCase();
        if (nameToId.containsKey(key)) return nameToId.get(key);
        int id = nextCityId++;
        City c = new City(id, name.trim());
        cities.put(id, c);
        nameToId.put(key, id);
        adj.put(id, new ArrayList<>());
        return id;
    }

    public int getCityIdByName(String name) {
        Integer id = nameToId.get(name.trim().toLowerCase());
        if (id == null) throw new IllegalArgumentException("Unknown city: " + name);
        return id;
    }

    // Add bidirectional edge (common for bus networks)
    public void addEdgeByNames(String fromName, String toName, double distanceKm, double timeMin, double cost) {
        int from = addCity(fromName);
        int to = addCity(toName);
        addEdge(from, to, distanceKm, timeMin, cost);
        addEdge(to, from, distanceKm, timeMin, cost);
    }

    public void addEdge(int fromId, int toId, double distanceKm, double timeMin, double cost) {
        Edge e = new Edge(nextEdgeId++, fromId, toId, distanceKm, timeMin, cost);
        adj.computeIfAbsent(fromId, k -> new ArrayList<>()).add(e);
    }
}
