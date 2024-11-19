package com.Evan_Roams;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TSPLoader {
    private List<Node> nodes;

    public TSPLoader(String filePath) {
        nodes = new ArrayList<>();
        loadTSPData(filePath);
    }

    private void loadTSPData(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean inNodeSection = false;

            while ((line = br.readLine()) != null) {
                line = line.trim();

                if (line.equals("NODE_COORD_SECTION")) {
                    inNodeSection = true;
                    continue;
                }
                if (line.equals("EOF")) {
                    break;
                }

                if (inNodeSection) {
                    String[] parts = line.split("\\s+");

                    if (parts.length >= 3) {
                        try {
                            int id = Integer.parseInt(parts[0]);
                            double x = Double.parseDouble(parts[1]);
                            double y = Double.parseDouble(parts[2]);
                            nodes.add(new Node(id, x, y));
                        } catch (NumberFormatException e) {
                            System.err.println("Error al convertir un valor numérico en la línea: " + line);
                        }
                    } else {
                        System.err.println("Línea inválida (se esperaban al menos 3 elementos): " + line);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error al leer el archivo: " + e.getMessage());
        }
    }

    public List<Node> getNodes() {
        return nodes;
    }
}

