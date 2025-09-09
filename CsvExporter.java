package com.busopt;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class CsvExporter {
    public static void exportRoute(Path out, Graph g, RoutingService.Route r, WeightType wt) throws IOException {
        try (BufferedWriter bw = Files.newBufferedWriter(out)) {
            bw.write("path,total_distance_km,total_time_min,total_cost,selected_weight,selected_weight_value\n");
            bw.write("\"" + r.signature(g) + "\"," +
                    String.format("%.2f", r.totalDistanceKm) + "," +
                    String.format("%.2f", r.totalTimeMin) + "," +
                    String.format("%.2f", r.totalCost) + "," +
                    wt.name() + "," +
                    String.format("%.2f", r.selectedWeightValue) + "\n");
        }
    }
}
