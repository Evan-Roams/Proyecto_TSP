package com.Evan_Roams;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        String filePath = "data/usa13509.tsp";

        TSPLoader loader = new TSPLoader(filePath);

        List<Node> nodes = loader.getNodes();


        int numeroCiudades = nodes.size();
        double[][] matrizDistancia = new double[numeroCiudades][numeroCiudades];

        for (int i = 0; i < numeroCiudades; i++) {
            for (int j = 0; j < numeroCiudades; j++) {
                if (i != j) {
                    Node node1 = nodes.get(i);
                    Node node2 = nodes.get(j);

                    double dx = node1.getX() - node2.getX();
                    double dy = node1.getY() - node2.getY();
                    double distancia= Math.sqrt(dx * dx + dy * dy);

                    matrizDistancia[i][j] = distancia;
                }
            }
        }

        int[] tour = AlgoritmoTSP_tour(matrizDistancia, numeroCiudades);
        double tourDistancia= calcularDistanciaTour(tour, matrizDistancia);

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

            //ciudades no visitadas
            for (int j = 0; j < numeroCiudades; j++) {
                if (visitado[j] == false) {  // Si la ciudad no ha sido visitada
                    double distancia = matrizDistancia[ciudadActual][j];

                    if (distancia < distanciaMasCercana) {
                        ciudadMasCercana = j;
                        distanciaMasCercana = distancia;
                    }
                }
            }

            visitado[ciudadMasCercana] = true;
            tour[i] = ciudadMasCercana;
            ciudadActual = ciudadMasCercana;
        }

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
