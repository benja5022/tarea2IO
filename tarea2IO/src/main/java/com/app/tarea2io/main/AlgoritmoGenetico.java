/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.tarea2io.main;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author benja
 */
public class AlgoritmoGenetico {
    private double[] costos;
    private Random generadorNumAleatorio;
    private Ciudad[] ciudades;
    private double[] asignacion;
    private int[][] solucionesIniciales;
    
    public AlgoritmoGenetico(double[] costos, Ciudad[] ciudades, int numSolIniciales, int[][] solIniciales){
        this.costos = costos;
        this.ciudades = ciudades;
        this.generadorNumAleatorio = new Random();
        this.asignacion = new double[36];
        this.solucionesIniciales = solIniciales;
    }
    
    public void seleccionIndividuos(){
        
    }
    
    public int[][] cruzamientoEnDosPuntos(int solucion1, int solucion2){
        int[] hijo1 = this.solucionesIniciales[solucion1].clone();
        int[] hijo2 = this.solucionesIniciales[solucion2].clone();
        int limiteInferior = (int) Math.round(this.generadorNumAleatorio.nextDouble() * 35);
        int limiteSuperior = (int) Math.round(this.generadorNumAleatorio.nextDouble() * 35);
        
        if(limiteSuperior < limiteInferior){
            int aux = limiteSuperior;
            limiteSuperior = limiteInferior;
            limiteInferior = aux;
        }
        
        System.out.println(limiteInferior+" "+limiteSuperior);
        
        for(int i = 0; i < 36; i++){

            if( i < limiteInferior && i > limiteSuperior){
                hijo1[i] = this.solucionesIniciales[solucion1][i];
                hijo2[i] = this.solucionesIniciales[solucion2][i];
            }
            
            if(i >= limiteInferior && i<= limiteSuperior){
                hijo1[i] = this.solucionesIniciales[solucion2][i];
                hijo2[i] = this.solucionesIniciales[solucion1][i];
            }
        }
        
        /*System.out.println("Hijo 1: ");
        for(int i = 0; i < 36; i++){
            System.out.print(hijo1[i]+" ");
        }
        System.out.println("");
        System.out.println("Hijo 2: ");
        for(int i = 0; i < 36; i++){
            System.out.print(hijo2[i]+" ");
        }*/
        int[][] a= {hijo1, hijo2};
        return  a;
    }
    
    public int[][] mutacion(int[][] hijos){
        for(int k = 0; k < hijos.length; k++){
            
            for(int i = 0; i < 36; i++){
                if(this.generadorNumAleatorio.nextDouble() < 0.15){
                    if(hijos[k][i] == 1){
                        hijos[k][i] = 0;
                    }
                    else{
                        hijos[k][i] = 1;
                    }
                }
            }            
        }
        
        return hijos;
    }
    
    public void seleccionPoblacionInicial(){
        
    }
    
    public void seleccionElitista(){
        
    }
    
    public void seleccionRuleta(int numeroSoluciones){
        ArrayList<Double> probabilidadSoluciones = new ArrayList<>();
        double suma = 0;
        
        for(int i= 0; i < this.solucionesIniciales.length; i++){
            suma += this.calculoFuncionObjetivo(i);
            System.out.println(suma);
        }
        
        System.out.println();
        
        probabilidadSoluciones.add(0.0);
        
        for(int i= 1; i < this.solucionesIniciales.length; i++){
            System.out.println("");
            System.out.println(this.calculoFuncionObjetivo(i) +" + "+ probabilidadSoluciones.get(i-1));
            probabilidadSoluciones.add( (this.calculoFuncionObjetivo(i)/suma + probabilidadSoluciones.get(i-1)) );
        }
        
        probabilidadSoluciones.add(probabilidadSoluciones.size(), 1.0);
        
        for(int i = 0; i < probabilidadSoluciones.size(); i++){
            System.out.println(probabilidadSoluciones.get(i));
        }
        
        double nRandom = this.generadorNumAleatorio.nextDouble();
        
        
        
    }
        
    public boolean cumpleRestriccion(int numSolucion){
        this.asignacion = new double[36];
        //System.out.println("--------------");
        for(int i = 0; i< 36; i++){
            if(this.solucionesIniciales[numSolucion][i] != 0){
                //System.out.println(i+2);

                for(int j = 0; j < 36; j++){

                    if(this.ciudades[i].obtenerEstado(j) == 1){
                        this.asignacion[j] = 1;
                    }
                }
            }
            //System.out.println(" "+solucionesIniciales[numSolucion][i]);
        }
        //System.out.println("----------------");
        int cont = 0;
        for(int i = 0; i < 36; i++){
            if(this.asignacion[i] == 1)
            {
                cont ++;
            }
        }
        
        if(cont == 36){
            //System.out.println("Sí");
            return true;
        }
        //System.out.println("No");
        return false;
    }
    
    public void imprimirCiudades(){
        for(int i = 0; i< 36; i++){
            for(int j = 0; j < 36; j++){
                System.out.print(this.ciudades[i].obtenerEstado(j));
            }
            System.out.println();
        }
    }
    
    public void imprimirSoluciones(){
        int sizei = this.solucionesIniciales.length;
        int sizej = 36;
        
        for(int i= 0; i < sizei; i++){
            for(int j = 0; j < sizej; j++){
                System.out.print(this.solucionesIniciales[i][j]);
            }
            System.out.println();
        }
    }
    

    public double calculoFuncionObjetivo(int solucionActual) {
        double valorFuncion = 0.0;
        boolean isSolucion = false;
        for(int i= 0; i < 36; i++){
            if(this.solucionesIniciales[solucionActual][i] == 1){
                valorFuncion += this.costos[i] ;
            }
        }
        isSolucion = cumpleRestriccion(solucionActual);
        System.out.println("La funcion "+solucionActual+" es: "+ isSolucion + " y su valor es: " + valorFuncion);
        if(isSolucion == false) valorFuncion += 38.2; //Explicar por que se utiliza el valor 38.2
        return valorFuncion;
    }
    
}
