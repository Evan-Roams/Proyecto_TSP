package com.Evan_Roams;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Ruta al archivo .tsp (asegúrate de cambiarla a la ruta correcta en tu sistema)
        String filePath = "data/berlin52.tsp";

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
                    double distancia= Math.sqrt(dx * dx + dy * dy);

                    matrizDistancia[i][j] = distancia;
                }
            }
        }

        // Ejecuta el algoritmo de Vecino Más Cercano
        int[] tour = AlgoritmoTSP_tour(matrizDistancia, numeroCiudades);
        double tourDistancia= calcularDistanciaTour(tour, matrizDistancia);

        // Imprime el resultado
        System.out.println("Tour encontrado:");
        for (int city : tour) {
            System.out.print(city + " -> ");
        }
        System.out.println("\nDistancia total del tour: " + tourDistancia + " Km");
    }

    public static int[] AlgoritmoTSP_tour(double[][] matrizDistancia, int numeroCiudades) {
        boolean[] visitado = new boolean[numeroCiudades];
        int[] tour = new int[numeroCiudades + 1];
        int ciudadActual = 0;
        visitado[ciudadActual] = true;
        tour[0] = ciudadActual;


        for (int i = 1; i < numeroCiudades; i++) {
            int ciudadMasCercana = -1;
            double distanciaMasCercana = Double.MAX_VALUE;

            //solo ciudades no visitadas
            for (int j = 0; j < numeroCiudades; j++) {
                if (visitado[j] == false) {
                    double distancia= matrizDistancia[ciudadActual][j];

                    if (distancia< distanciaMasCercana) {
                        ciudadMasCercana = j;
                        distanciaMasCercana = distancia;
                    } else if (j+1 > matrizDistancia[0].length) {
                        double distancia2 = matrizDistancia[ciudadActual][j+1];


                        // esta optimizacion funciona para determinados casos (para todos los evaluados aparentemente funciona)
                        if (distancia2 < distancia) {
                            ciudadMasCercana = j+1;
                            distanciaMasCercana = distancia2;
                            j=j+1;
                        }
                    }

                }
            }

            // Marca la ciudad más cercana como visitada
            visitado[ciudadMasCercana] = true;
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
