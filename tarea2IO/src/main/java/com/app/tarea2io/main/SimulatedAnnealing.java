/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.tarea2io.main;

import java.util.Random;

/**
 *
 * @author benja
 */
public class SimulatedAnnealing {
    private double temperatura;
    private double alpha;
    private double[] costos;
    private Random generadorNumAleatorio;
    
    public SimulatedAnnealing(double t, double alpha, double[] costos){
        this.temperatura = t;
        this.alpha = alpha;
        this.costos = costos;
        this.generadorNumAleatorio = new Random();
    }
    
    public void criterioMetropoli(int[] solInicial, int[] solNueva){

        
        if( funcionDeCosto(solNueva) <= funcionDeCosto(solInicial)){
            solInicial = solNueva;
        }
        
        double deltaS = funcionDeCosto(solNueva) - funcionDeCosto(solInicial);
        double prob = Math.exp( -(deltaS / this.temperatura));
        
        
        
    }
    
    public double funcionDeCosto(int[] sol){
        double sum = 0;
        for(int i = 0; i < this.costos.length; i++){
            sum = sum + sol[i] * this.costos[i];
        }
        return sum;
    }
    
}
