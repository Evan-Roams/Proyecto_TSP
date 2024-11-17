package com.Evan_Roams;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Ruta al archivo .tsp (asegúrate de cambiarla a la ruta correcta en tu sistema)
        String filePath = "data/ulysses22.tsp";

        // Crear una instancia de TSPLoader y cargar los datos del archivo
        TSPLoader loader = new TSPLoader(filePath);

        // Obtener la lista de nodos cargados
        List<Node> nodes = loader.getNodes();


        int numberOfCities = nodes.size();
        double[][] distanceMatrix = new double[numberOfCities][numberOfCities]; //calculamos la matriz en base a el numero de ciudades de el problema

        // Calcular las distancias entre cada par de nodos
        for (int i = 0; i < numberOfCities; i++) {
            for (int j = 0; j < numberOfCities; j++) {
                if (i != j) {
                    // Calcular la distancia Euclidiana entre los nodos i y j
                    double distance = calculateDistance(nodes.get(i), nodes.get(j));
                    distanceMatrix[i][j] = distance;
                }
            }
        }

        // Ejecuta el algoritmo de Vecino Más Cercano
        AlgoritmoTSP tsp = new AlgoritmoTSP(distanceMatrix, numberOfCities);
        int[] tour = tsp.encontrarTour();
        double tourDistance = tsp.calcularDistanciaTour(tour);

        // Imprime el resultado
        System.out.println("Tour encontrado:");
        for (int city : tour) {
            System.out.print(city + " -> ");
        }
        System.out.println("\nDistancia total del tour: " + tourDistance);
    }

    // Método para calcular la distancia Euclidiana entre dos nodos
    private static double calculateDistance(Node node1, Node node2) {
        double dx = node1.getX() - node2.getX();
        double dy = node1.getY() - node2.getY();
        return Math.sqrt(dx * dx + dy * dy);
    }
}
