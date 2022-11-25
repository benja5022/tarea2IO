package com.app.tarea2io.main;

/**
 * Esta clase contiene los atributos y métodos de una ciudad
 * @author María Ignacia Morales Soriano
 * @author Andrés Vidal Soto
 * @author Benjamín Rojas Henríquez
 * @version 1.3
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
