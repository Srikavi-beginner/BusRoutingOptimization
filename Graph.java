package com.busopt.model;

import java.util.*;

public class Graph {
    private Map<Integer, City> cities = new HashMap<>();
    private Map<Integer, List<Edge>> adjList = new HashMap<>();

    public void addCity(City city) {
        cities.put(city.getId(), city);
        adjList.putIfAbsent(city.getId(), new ArrayList<>());
    }

    public void addEdge(Edge edge) {
        adjList.get(edge.getFromCity()).add(edge);
    }

    public City getCityById(int id) {
        return cities.get(id);
    }

    public City getCityByName(String name) {
        return cities.values()
                .stream()
                .filter(c -> c.getName().equalsIgnoreCase(name))
                .findFirst().orElse(null);
    }

    public List<Edge> getEdgesFrom(City city) {
        return adjList.get(city.getId());
    }

    public Collection<City> getCities() {
        return cities.values();
    }
}
