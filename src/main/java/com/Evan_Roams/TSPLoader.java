package com.Evan_Roams;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TSPLoader {
    private double[][] distanciaMatriz;
    private int numeroDeCiudades;

    public TSPLoader(String filepath) throws IOException {
        loadTSPData(filepath);
    }

    private void loadTSPData(String filePath) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line;
        List<double[]> cities = new ArrayList<>();

        while ((line = reader.readLine()) != null) {
            line = line.trim();
            if (line.startsWith("NODE_COORD_SECTION")) {
                break; // Saltar a la sección de coordenadas
            }
        }

        while ((line = reader.readLine()) != null && !line.equals("EOF")) {
            String[] parts = line.trim().split("\\s+");
            double x = Double.parseDouble(parts[1]);
            double y = Double.parseDouble(parts[2]);
            cities.add(new double[]{x, y});
        }

        reader.close();
        this.numeroDeCiudades = cities.size();
        this.distanciaMatriz = new double[numeroDeCiudades][numeroDeCiudades];

        // Calcula la distancia euclidiana entre cada par de ciudades
        for (int i = 0; i < numeroDeCiudades; i++) {
            for (int j = i; j < numeroDeCiudades; j++) {
                if (i == j) {
                    distanciaMatriz[i][j] = 0;
                } else {
                    double dx = cities.get(i)[0] - cities.get(j)[0];
                    double dy = cities.get(i)[1] - cities.get(j)[1];
                    double distance = Math.sqrt(dx * dx + dy * dy);
                    distanciaMatriz[i][j] = distance;
                    distanciaMatriz[j][i] = distance; // matriz simétrica
                }
            }
        }
    }

    public double[][] getdistanciaMatriz() {
        return distanciaMatriz;
    }

    public int getnumeroDeCiudades() {
        return numeroDeCiudades;
    }
}
