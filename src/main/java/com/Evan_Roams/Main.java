package com.Evan_Roams;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Ruta al archivo .tsp (asegúrate de cambiarla a la ruta correcta en tu sistema)
        String filePath = "data/rl5934.tsp";

        // Crear una instancia de TSPLoader y cargar los datos del archivo
        TSPLoader loader = new TSPLoader(filePath);

        // Obtener la lista de nodos cargados
        List<Node> nodes = loader.getNodes();


        int numeroCiudades = nodes.size();
        double[][] matrizDistancia = new double[numeroCiudades][numeroCiudades]; //calculamos la matriz en base a el numero de ciudades de el problema

        // Calcular las distancias entre cada par de nodos
        for (int i = 0; i < numeroCiudades; i++) {
            for (int j = 0; j < numeroCiudades; j++) {
                if (i != j) {
                    // Calcular la distancia Euclidiana entre los nodos i y j (raiz de(x^2 + y^2)) esto es "triangular posicion"
                    Node node1 = nodes.get(i);
                    Node node2 = nodes.get(j);

                    double dx = node1.getX() - node2.getX();
                    double dy = node1.getY() - node2.getY();
                    double distance = Math.sqrt(dx * dx + dy * dy);

                    matrizDistancia[i][j] = distance;
                }
            }
        }

        // Ejecuta el algoritmo de Vecino Más Cercano
        int[] tour = AlgoritmoTSP_tour(matrizDistancia, numeroCiudades);
        double tourDistance = calcularDistanciaTour(tour, matrizDistancia);

        // Imprime el resultado
        System.out.println("Tour encontrado:");
        for (int city : tour) {
            System.out.print(city + " -> ");
        }
        System.out.println("\nDistancia total del tour: " + tourDistance);
    }

    public static int[] AlgoritmoTSP_tour(double[][] matrizDistancia, int numeroCiudades) {
        boolean[] visited = new boolean[numeroCiudades];  // Marca las ciudades visitadas
        int[] tour = new int[numeroCiudades + 1];  // Tour final (con regreso al inicio)
        int ciudadActual = 0;  // Comienza desde la ciudad 0
        visited[ciudadActual] = true;
        tour[0] = ciudadActual;

        for (int i = 1; i < numeroCiudades; i++) {
            int ciudadMasCercana = -1;
            double distanciaMasCercana = Double.MAX_VALUE;

            // Optimización: solo recorremos las ciudades no visitadas
            for (int j = 0; j < numeroCiudades; j++) {
                if (visited[j] == false) {
                    double distance = matrizDistancia[ciudadActual][j];
                    if (distance < distanciaMasCercana) {
                        ciudadMasCercana = j;
                        distanciaMasCercana = distance;
                    }
                }
            }

            // Marca la ciudad más cercana como visitada
            visited[ciudadMasCercana] = true;
            tour[i] = ciudadMasCercana;
            ciudadActual = ciudadMasCercana;
        }

        // Regresa al punto de inicio
        tour[numeroCiudades] = tour[0];
        return tour;
    }

    public static double calcularDistanciaTour(int[] tour, double[][] matrizDistancia) {
        double distanciaTotal = 0.0;
        for (int i = 0; i < tour.length - 1; i++) {
            distanciaTotal += matrizDistancia[tour[i]][tour[i + 1]];
        }

        return distanciaTotal;
    }
}
