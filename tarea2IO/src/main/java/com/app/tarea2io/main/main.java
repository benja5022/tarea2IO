/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.tarea2io.main;

import java.util.ArrayList;

/**
 * Esta clase inicializa los costos para utilizar el algoritmo genético.
 * @author María Ignacia Morales Soriano
 * @author Andrés Vidal Soto
 * @author Benjamín Rojas Henríquez
 * @version 1.3
 */
public class main {
    public static void main(String arg[]){
        int[] valorFuncion = new int[6];
        double[] costos = {1, 1.5, 1.2, 2, 3, 2, 1, 1, 3, 4, 3, 3, 2, 2.5, 1.5, 2, 2, 3,
        2, 2, 3, 2, 3, 3, 1, 2.5, 2, 3.5, 2, 1.5, 2, 3, 3.5, 2, 2.5, 1.5};
        
        //int[] solucionInicial1 = {0,1,0,0,0,1,0,0,0,1,0,0,1,0,0,0,1,0,0,1,0,1,0,0,0,0,1,0,0,0,1,0,0,0,0,1};// = 21.0
        int[] solucionInicial1 = {0,1,0,0,0,1,0,0,0,1,0,0,1,0,0,0,0,0,0,1,0,1,0,0,0,0,1,0,0,0,0,0,0,0,0,1}; //no factible valor 55.2
        //int[] solucionInicial2 = {0,1,1,0,1,0,0,0,1,0,1,0,1,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,1,0,1,0,0,0,0};// = 21.2
        int[] solucionInicial2 = {0,1,1,0,1,0,1,0,1,0,1,0,1,0,0,0,1,0,1,0,1,0,1,0,1,0,1,0,0,1,0,1,0,1,0,0};
        int[] solucionInicial3 = {0,1,0,1,1,0,1,0,0,1,0,0,0,0,1,1,0,1,1,0,1,0,0,1,0,1,1,0,1,0,1,0,0,0,1,0}; 
        int[] solucionInicial4 = {1,0,0,0,0,1,0,1,0,0,0,1,0,0,1,0,1,0,1,0,1,0,1,1,0,0,0,1,1,1,0,1,0,0,1,0};
        int[] solucionInicial5 = {0,1,0,1,1,0,1,0,0,1,0,0,0,0,1,1,1,0,1,0,1,0,0,1,1,0,0,1,0,0,0,1,0,0,1,1};
        //int[] solucionInicial6 = {0,1,1,0,1,0,1,0,0,1,0,0,0,0,0,1,1,1,1,0,1,0,0,1,1,0,1,0,0,0,0,0,1,0,1,1};
        int[] solucionInicial6 = {0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,1,0,0,1,0,0,0};
        int[][] soluciones = {solucionInicial1 , solucionInicial2, solucionInicial3, solucionInicial4, solucionInicial5, solucionInicial6};
        
        String[] nombresCiudades = {"Calle Larga", "San Esteban","Rinconada","Los Andes","Cabildo","La Ligua","Papudo",
            "Petorca","Zapallar","Hijuelas","La Calera","La Cruz","Limache","Nogales","Olmué","Quillota","Algarrobo","Cartagena",
        "El Quisco", "El Tabo", "San Antonio", "Santo Domingo", "Catemu", "Llay-Llay", "Panquehue", "Putaendo", "San Felipe", "Santa María",
        "Quilpué", "Concón", "Puchuncaví", "Casablanca", "Quintero", "Valparaíso", "Villa Alemana", "Viña del Mar"};
        //System.out.println(nombresCiudades.length);
        double[][] matriz = LectorCSV.leerCSV("");
        Ciudad[] coleccionCiudades = new Ciudad[36];
        
        for(int i=0; i< 36; i++){
            coleccionCiudades[i] = new Ciudad(i+1,nombresCiudades[i]);
            coleccionCiudades[i].setImpacto(matriz[i]);
        }
        
        AlgoritmoGenetico ag = new AlgoritmoGenetico(costos, coleccionCiudades,soluciones);
        //ag.imprimirCiudades();
        
        //ag.cruzamientoEnDosPuntos(1, 2);
        
        //ag.seleccionRuleta(2);
        System.out.println("Generacion inicial");
        ag.mostrarResultados();
        for(int i = 0 ; i < 1000; i++){
            
            ag.inicializarMetaheuristica();
            System.out.println(" ");
            System.out.println("Generacion "+ (i+1));
            ag.mostrarResultados();
        }
        
        ag.inicializarMetaheuristica();
        
        //System.out.println(costos.length+ " "+ solucionInicial1.length);
        
        /* Se tiene que repensar la utilización de una matriz n x n para la asignacion 
        de comunas aledañas debido a que consume muchos recursos:
        Solución (la primera que se me ocurre): cada comuna tiene un array SOLO con las comunas que son aledañas a esta. 
        Por ejemplo: Si la comuna '1' tiene como comunas aledañas a '2', '3' y '4'; esta ciudad va a tener un array de la forma [1,2,3,4] y no [1,1,1,1,0,0,0,0,0,0,0,...]
        */ 
    
    }
    
}
