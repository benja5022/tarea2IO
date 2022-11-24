/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.tarea2io.main;

/**
 *
 * @author benja
 */
public class Ciudad {
    private int id;
    private String nombre;
    private double[] impacto;
    
    public Ciudad(int id, String nombre){
        this.id = id;
        this.nombre = nombre;
        this.impacto = new double[36];
    }
    
    public void setImpacto(double[] impacto){
        this.impacto = impacto;
    }
    
    public double[] getImpacto(double[] impacto){
        return this.impacto;
    }
    
    public int obtenerEstado(int i){
        return (int) this.impacto[i];
    }
    
    
    
}
