package com.Evan_Roams;

public class AlgoritmoTSP {
    private double[][] distanceMatrix;
    private int numberOfCities;

    public AlgoritmoTSP(double[][] distanceMatrix, int numberOfCities) {
        this.distanceMatrix = distanceMatrix;
        this.numberOfCities = numberOfCities;
    }

    public int[] encontrarTour() {
        boolean[] visited = new boolean[numberOfCities];  // Marca las ciudades visitadas
        int[] tour = new int[numberOfCities + 1];  // Tour final (con regreso al inicio)
        int currentCity = 0;  // Comienza desde la ciudad 0
        visited[currentCity] = true;
        tour[0] = currentCity;

        for (int i = 1; i < numberOfCities; i++) {
            int nearestCity = -1;
            double nearestDistance = Double.MAX_VALUE;

            // Optimización: solo recorremos las ciudades no visitadas
            for (int j = 0; j < numberOfCities; j++) {
                if (!visited[j]) {
                    double distance = distanceMatrix[currentCity][j];
                    if (distance < nearestDistance) {
                        nearestCity = j;
                        nearestDistance = distance;
                    }
                }
            }

            // Marca la ciudad más cercana como visitada
            visited[nearestCity] = true;
            tour[i] = nearestCity;
            currentCity = nearestCity;
        }

        // Regresa al punto de inicio
        tour[numberOfCities] = tour[0];
        return tour;
    }

    public double calcularDistanciaTour(int[] tour) {
        double distanciaTotal = 0.0;

        // Optimización: se usa tour.length directamente
        for (int i = 0; i < tour.length - 1; i++) {
            distanciaTotal += distanceMatrix[tour[i]][tour[i + 1]];
        }

        return distanciaTotal;
    }
}

