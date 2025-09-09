package com.busopt;

import java.util.*;

public class RoutingService {

    public static class Route {
        public final List<Integer> nodes;
        public final List<Edge> edges;
        public final double totalDistanceKm;
        public final double totalTimeMin;
        public final double totalCost;
        public final double selectedWeightValue;

        public Route(List<Integer> nodes, List<Edge> edges,
                     double totalDistanceKm, double totalTimeMin, double totalCost, double selectedWeightValue) {
            this.nodes = nodes;
            this.edges = edges;
            this.totalDistanceKm = totalDistanceKm;
            this.totalTimeMin = totalTimeMin;
            this.totalCost = totalCost;
            this.selectedWeightValue = selectedWeightValue;
        }

        public String signature(Graph g) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < nodes.size(); i++) {
                if (i > 0) sb.append("->");
                sb.append(g.getCityName(nodes.get(i)));
            }
            return sb.toString();
        }
    }

    public Route shortestPath(Graph g, int sourceId, int targetId, WeightType weightType) {
        final double INF = Double.POSITIVE_INFINITY;
        Map<Integer, Double> dist = new HashMap<>();
        Map<Integer, Edge> prevEdge = new HashMap<>();

        class Node { int id; double d; Node(int id,double d){this.id=id;this.d=d;} }

        PriorityQueue<Node> pq = new PriorityQueue<>(Comparator.comparingDouble(n -> n.d));
        dist.put(sourceId, 0.0);
        pq.add(new Node(sourceId, 0.0));

        while (!pq.isEmpty()) {
            Node cur = pq.poll();
            double curDist = cur.d;
            int u = cur.id;
            if (curDist > dist.getOrDefault(u, INF)) continue;
            if (u == targetId) break;
            for (Edge e : g.neighbors(u)) {
                int v = e.getToId();
                double w = e.weight(weightType);
                double alt = curDist + w;
                if (alt < dist.getOrDefault(v, INF)) {
                    dist.put(v, alt);
                    prevEdge.put(v, e);
                    pq.add(new Node(v, alt));
                }
            }
        }

        if (!dist.containsKey(targetId)) return null;

        // Reconstruct path
        List<Edge> edges = new ArrayList<>();
        List<Integer> nodes = new ArrayList<>();
        int curNode = targetId;
        nodes.add(curNode);
        double totD = 0, totT = 0, totC = 0;
        while (curNode != sourceId) {
            Edge e = prevEdge.get(curNode);
            if (e == null) break;
            edges.add(e);
            totD += e.getDistanceKm();
            totT += e.getTimeMin();
            totC += e.getCost();
            curNode = e.getFromId();
            nodes.add(curNode);
        }
        Collections.reverse(nodes);
        Collections.reverse(edges);

        return new Route(nodes, edges, totD, totT, totC, dist.get(targetId));
    }
}
