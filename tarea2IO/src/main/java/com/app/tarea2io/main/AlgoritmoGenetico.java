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
    
    public void inicializarMetaheuristica(){
        int[][] poblacionInicial = this.solucionesIniciales;
        int[] a = this.seleccionRuleta(2);// (2, solucionesIniciales)
        int solucion1 = -1;
        int solucion2 = -1;
        
        for(int i = 0; i < a.length; i++ ){
            if(a[i] != -1){
                if(solucion1 == -1){
                    solucion1 = a[i];
                }
                else{
                    solucion2 = a[i];
                }
               // System.out.println("Sol1: "+solucion1+ " Sol2: "+solucion2);
            }
        }
        int[][] hijos = this.cruzamientoEnDosPuntos(solucion1, solucion2);
        hijos = this.mutacion(hijos);
        
        for(int i = 0; i < hijos.length; i++){
            for(int j = 0; j < 36; j++){
                System.out.print(hijos[i][j]);
            }
            System.out.println("");
        }
        
        for(int i = 0; i < hijos.length; i++){
            System.out.println(this.calculoFuncionObjetivo(hijos[i]));
            System.out.println(this.cumpleRestriccion(hijos[i]));
        }
        
        
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
                if(this.generadorNumAleatorio.nextDouble() < 0.1){
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
    
    public int[] seleccionRuleta(int numeroSoluciones){
        int[][] solucionesEscogidas = new int[numeroSoluciones][36];
        ArrayList<Double> probabilidadSoluciones;
        int[] indicesUsados = new int[this.solucionesIniciales.length];
        
        for(int i = 0; i < this.solucionesIniciales.length; i++){
            indicesUsados[i] = -1;
        }
        
        double suma = 0;
        double aux = 0;
        double nRandom;
        boolean bandera;
        
        for(int k = 0; k < numeroSoluciones;){
            suma = 0;
            aux = 0;
            
            probabilidadSoluciones = new ArrayList<>();
            probabilidadSoluciones.add(0.0);
            
            for(int i= 0; i < this.solucionesIniciales.length; i++){
                bandera = false;
                for(int j = 0; j < indicesUsados.length; j++){
                    if(i == indicesUsados[j]){
                        bandera = true;
                        break;
                    }
                }
                if(!bandera) suma += this.calculoFuncionObjetivoMinimizacion(i);

                System.out.println("La suma es: "+suma+ " El indice usado es: "+bandera);
            }

            System.out.println();
            for(int i = 0; i < this.solucionesIniciales.length; i++){
                
                bandera = false;
                for(int j = 0; j < indicesUsados.length; j++){
                    if(i == indicesUsados[j]){
                        bandera = true;
                        break;
                    }
                }
                if(!bandera){
                    aux += this.calculoFuncionObjetivoMinimizacion(i);
                    probabilidadSoluciones.add(aux/suma);
                }
            }

            for(int i = 0; i < probabilidadSoluciones.size(); i++){
                System.out.println(probabilidadSoluciones.get(i));
            }

            nRandom = this.generadorNumAleatorio.nextDouble();
            System.out.println("El numero aleatorio es: "+nRandom+"\n");

            for(int i = 0; i < probabilidadSoluciones.size(); i++){
                bandera= false;
                if(probabilidadSoluciones.get(i)< nRandom && nRandom < probabilidadSoluciones.get(i+1) ){
                    for(int j =0; j < indicesUsados.length; j++){
                        if(indicesUsados[j] == i){
                            bandera = true;
                        }
                    }
                    
                    if(!bandera){
                        solucionesEscogidas[k] = this.solucionesIniciales[i];
                        System.out.println("El indice es: "+i);
                        indicesUsados[k] = i;
                        k++;
                    }
                    break;
                }
                System.out.println("k es: "+k+ " El indice usado es: "+i);
            }

            for(int i = 0; i < solucionesEscogidas.length; i++){
                for(int j = 0; j < 36; j++){
                    System.out.print(solucionesEscogidas[i][j]);
                }
                System.out.println();
            }
            
        }
        return indicesUsados;
    }
        
    public boolean cumpleRestriccion(int numSolucion){
        this.asignacion = new double[36];
        
        for(int i = 0; i< 36; i++){
            if(this.solucionesIniciales[numSolucion][i] != 0){
                //System.out.println(i+2);

                for(int j = 0; j < 36; j++){

                    if(this.ciudades[i].obtenerEstado(j) == 1){
                        this.asignacion[j] = 1;
                    }
                }
            }
        }
        
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
    
        public boolean cumpleRestriccion(int[] solucion){
        this.asignacion = new double[36];
        
        for(int i = 0; i< 36; i++){
            if(solucion[i] != 0){
                //System.out.println(i+2);

                for(int j = 0; j < 36; j++){

                    if(this.ciudades[i].obtenerEstado(j) == 1){
                        this.asignacion[j] = 1;
                    }
                }
            }
        }
        
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
        
        for(int i= 0; i < 36; i++){
            if(this.solucionesIniciales[solucionActual][i] == 1){
                valorFuncion += this.costos[i] ;
            }
        }
        
        return valorFuncion;
    }
    
        public double calculoFuncionObjetivo(int[] solucion) {
        double valorFuncion = 0.0;
        
        for(int i= 0; i < 36; i++){
            if(solucion[i] == 1){
                valorFuncion += this.costos[i] ;
            }
        }
        
        return valorFuncion;
    }
    
    public double calculoFuncionObjetivoMinimizacion(int solucionActual){
        
        double valorFuncion = 0.0;
        
        for(int i= 0; i < 36; i++){
            if(this.solucionesIniciales[solucionActual][i] == 1){
                    valorFuncion += this.costos[i] ;
            }
        }
        
        if(!this.cumpleRestriccion(solucionActual)){
            valorFuncion += 40;
        }
        
        return (this.obtenerMaximo() + this.obtenerMinimo() - valorFuncion);
    }
    
//    public double calculoFuncion
    
    public double obtenerMaximo(){
        double maximo = 0;
        for(int i = 0; i < this.solucionesIniciales.length; i++){
            if(maximo < this.calculoFuncionObjetivo(i)){
                maximo = this.calculoFuncionObjetivo(i);
            }
        }
        return maximo;
    }
    public double obtenerMinimo(){
        double minimo = this.calculoFuncionObjetivo(0);
        for(int i = 0; i < this.solucionesIniciales.length; i++){
            if(minimo > this.calculoFuncionObjetivo(i)){
                minimo = this.calculoFuncionObjetivo(i);
            }
        }
        return minimo;
    }
    
}
