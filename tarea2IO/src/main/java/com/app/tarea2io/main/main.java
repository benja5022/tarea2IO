/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.tarea2io.main;

import java.util.ArrayList;

/**
 *
 * @author benja
 */
public class main {
    public static void main(String arg[]){
        int[] valorFuncion = new int[6];
        double[] costos = {1, 1.5, 1.2, 2, 3, 2, 1, 1, 3, 4, 3, 3, 2, 2.5, 1.5, 2, 2, 3,
        2, 2, 3, 2, 3, 3, 1, 2.5, 2, 3.5, 2, 1.5, 2, 3, 3.5, 2, 2.5, 1.5};
        
        int[] solucionInicial1 = {0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 1, 0, 0, 1, 0, 1, 0,
        0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 1};
        int[] solucionInicial2 = {0, 1, 1, 0, 1, 0, 0, 0, 1, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0,
        0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0};
        int[] solucionInicial3 = {0,1,0,1,1,0,1,0,0,1,0,0,0,0,1,1,0,1,1,0,1,0,0,1,0,1,1,0,1,0,1,0,0,0,1,0};
        int[] solucionInicial4 = {1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0,
        1, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0};
        int[] solucionInicial5 = {0,1,0,1,1,0,1,0,0,1,0,0,0,0,1,1,1,0,1,0,1,0,0,1,1,0,0,1,0,0,0,1,0,0,1,1};
        int[] solucionInicial6 = {0,1,1,0,1,0,1,0,0,1,0,0,0,0,0,1,1,1,1,0,1,0,0,1,1,0,1,0,0,0,0,0,1,0,1,1};
        
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
        ag.imprimirSoluciones();
        
        for(int i = 0; i < soluciones.length ; i++)
        {
            //valorFuncion[i] = ag.calculoFuncionObjetivo(i);
            ag.cumpleRestriccion(i);
        }
        
        System.out.println("");
        for(int i = 0; i < soluciones.length ; i++)
        {
            //valorFuncion[i] = ag.calculoFuncionObjetivo(i);
            System.out.println(ag.calculoFuncionObjetivo(i));
        }
        System.out.println("");
        
        //ag.cruzamientoEnDosPuntos(1, 2);
        
        ag.seleccionRuleta(2);
        
        
        //System.out.println(costos.length+ " "+ solucionInicial1.length);
        
        /* Se tiene que repensar la utilización de una matriz n x n para la asignacion 
        de comunas aledañas debido a que consume muchos recursos:
        Solución (la primera que se me ocurre): cada comuna tiene un array SOLO con las comunas que son aledañas a esta. 
        Por ejemplo: Si la comuna '1' tiene como comunas aledañas a '2', '3' y '4'; esta ciudad va a tener un array de la forma [1,2,3,4] y no [1,1,1,1,0,0,0,0,0,0,0,...]
        */ 
    
    }
    
}
