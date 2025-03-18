package tpo.lab2;

import tpo.lab2.trig.Calc;

import java.io.FileWriter;
import java.io.IOException;

public class CsvWriter {
    public static void writeToCsv(Calc function, double start, double end, double step, double eps, String filename) {
        try (FileWriter writer = new FileWriter(filename)) {
            writer.append("x,y\n");
            for (double x = start; x <= end; x += step) {
                double y = function.compute(x, eps);
                writer.append(String.format("%.5f,%.5f\n", x, y));
            }
            System.out.println("CSV file created: " + filename);
        } catch (IOException e) {
            System.err.println("Error writing CSV file: " + e.getMessage());
        }
    }


}
