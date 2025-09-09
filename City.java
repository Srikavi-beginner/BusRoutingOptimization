package com.busopt;

public class City {
    private final int id;
    private final String name;

    public City(int id, String name) {
        this.id = id;
        this.name = name.trim();
    }
    public int getId() { return id; }
    public String getName() { return name; }
    @Override public String toString() { return name + "(" + id + ")"; }
}
