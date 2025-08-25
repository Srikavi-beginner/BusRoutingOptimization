package com.busopt.service;

import com.busopt.model.Path;
import java.io.FileWriter;
import java.io.IOException;

public class CsvExporter {

    public static void exportPath(Path path, String weight, String fileName) {
        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write("Path,Total Distance,Total Time,Total Cost,Selected Weight(" + weight + ")\n");
            writer.write(
                "\"" + path.toString() + "\"," +
                path.getTotalDistance() + "," +
                path.getTotalTime() + "," +
                path.getTotalCost() + "," +
                path.getWeight(weight) + "\n"
            );
            System.out.println("CSV exported successfully to " + fileName);
        } catch (IOException e) {
            System.err.println("Error exporting CSV: " + e.getMessage());
        }
    }
}
