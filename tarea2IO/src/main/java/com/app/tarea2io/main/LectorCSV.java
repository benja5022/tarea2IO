/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.tarea2io.main;

import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * Esta clase contiene el método para leer el archivo csv correspondiente a la tarea 2
 * @author María Ignacia Morales Soriano
 * @author Andrés Vidal Soto
 * @author Benjamín Rojas Henríquez
 * @version 1.3
 */
public class LectorCSV {
    public static double[][] leerCSV(String direccion){
        

        int cont = 0;
            double[][] a = new double[36][36];
            int indexi = 0;
            int indexj = 0;
            int vuelta = 0;
        try {

            FileReader entrada = new FileReader("tarea2.csv", StandardCharsets.UTF_8);
            int c;

            do {
                c = entrada.read();
                char caracter = (char) c;
                
                // En este if se verifica que el caracter que se está leyendo del archivo sea util
                if ((caracter == ';')) {
                    cont++;
                } else {
                    
                    if(cont > 0)
                        
                        if(caracter != 'X' && caracter != '\n' && vuelta != 0){
                            if(indexj < 36){
                                a[indexi][indexj] = Integer.valueOf(Character.getNumericValue(caracter));
                            }
                            indexj++;
                            
                        }
                    
                }
                
                if (caracter == '\n' || c == -1) {
                    if(vuelta != 0){
                        indexi += 1;                        
                    }

                    indexj = 0;
                    vuelta++;
                    cont = 0;
                }

            } while (c != -1);

            entrada.close();
        } catch (IOException e) {
            System.out.println("El fichero no existe2");
        }

        return a;
    }
    
}
