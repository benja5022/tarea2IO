package com.app.tarea2io.main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * Esta clase contiene los atributos y métodos de una metaheurística de algoritmo genético
 * @author María Ignacia Morales Soriano
 * @author Andrés Vidal Soto
 * @author Benjamín Rojas Henríquez
 * @version 1.3
 */
public class AlgoritmoGenetico {
    private double[] costos;
    private Random generadorNumAleatorio;
    private Ciudad[] ciudades;
    private double[] asignacion; // se debe eliminar
    private int[][] solucionesIniciales;
    
    /**
     * Constructor del algoritmo genético
     * @param costos array que contiene los costos de asignar una antena a una ciudad.
     * @param ciudades array que contiene objetos de clase Ciudad.
     * @param solIniciales array que contiene un conjunto de soluciones iniciales.
     * Cada solución inicial es una array de tamaño n, donde n es el número de ciudades para efectos del problema.
     * Los valores que contiene solIniciales son ceros (0) o unos (1).
     * El valor es 1 si la ciudad tiene una antena y es 0 cuando no tiene asignada una antena.
     * 
     */
    public AlgoritmoGenetico(double[] costos, Ciudad[] ciudades, int[][] solIniciales){
        this.costos = costos;
        this.ciudades = ciudades;
        this.generadorNumAleatorio = new Random();
        this.asignacion = new double[36];
        this.solucionesIniciales = solIniciales;
    }
    
    /**
     * Método que inicia la metaheurística.
     * Esta se divide en 4 pasos, estos son:
     * 1) Selección de los padres que se van a recombinar de las soluciones iniciales (población inicial), a través del método seleccionRuleta()
     * 2) Una vez seleccionados los padres se procede a aplicar la recombinación para generar soluciones hijas.
     * En este caso, se utiliza el cruzamiento en dos puntos a través del método cruzamientoEnDosPuntos()
     * 3) Una vez obtenidos los hijos, se procede a mutar a cada uno de ellos. Esto se realiza a través del método mutacion()
     * 4) Ya mutados los hijos, se sigue el siguiente paso denominado como selección de poblacion Inicial
     * donde se escogeran los mejores individuos para que sean parte de la población inicial.
     * 
     */
    
    public void inicializarMetaheuristica(){
        //int[][] poblacionInicial = this.solucionesIniciales;
        // paso 1) Se seleccionan los padres
        int[][] a = this.seleccionRuleta(2);// (2, solucionesIniciales)
        
        for(int j = 0; j < 1; j++){
            // paso 2) Se cruzan (recombinan) los padres y se crean los hijos
            int[][] hijos = this.cruzamientoEnDosPuntos(a[0], a[1]);
            // paso 3) Se mutan los hijos
            hijos = this.mutacion(hijos);
            
            for(int i = 0; i < 2; i++){
                if(cumpleRestriccion(hijos[i])){
                System.out.println("Los hijos de esta generacion son: " + Arrays.toString(hijos[i])+ " y tienen un valor de: " +calcularSolucionObjetivo(hijos[i]));
                }else{
                    System.out.println("Los hijos de esta generacion son: " + Arrays.toString(hijos[i])+ " y tienen un valor de: " +(calcularSolucionObjetivo(hijos[i])+38.2)+" Pero es una solucion no factible");                
                }
                //System.out.println("Los hijos de esta generacion son: " + Arrays.toString(hijos[i])+ " y tienen un valor de: " + );
            }
            // paso 4)
            this.solucionesIniciales = seleccionElitista(hijos);
        }
    }
    public void mostrarResultados(){
        for(int i = 0; i < 6 ; i++){
            if(cumpleRestriccion(this.solucionesIniciales[i])){
                System.out.println("La solucion: "+i+" Es "+Arrays.toString(this.solucionesIniciales[i])+" y tiene un valor de: "+calcularSolucionObjetivo(this.solucionesIniciales[i]));
            }else{
                System.out.println("La solucion: "+i+" Es "+Arrays.toString(this.solucionesIniciales[i])+" y tiene un valor de: "+(calcularSolucionObjetivo(this.solucionesIniciales[i])+38.2)+" Pero es una solucion no factible");                
            }
            //if(!cumpleRestriccion(this.solucionesIniciales[i])) System.out.println("La solucion: " + i + " No es factible");
        }
        System.out.println(" ");
    }
    private int[][] seleccionElitista(int[][] hijos) {
        int [][] conjuntoSolucion = new int[6][36];
        double [] valoresSoluciones = new double[8];
        double menor = 81; // este valor supera la solucion menos optima
        int i, j;
        for(i = 0; i < 2; i++){
            valoresSoluciones[i] = calcularSolucionObjetivo(hijos[i]);
            if(!cumpleRestriccion(hijos[i])){
                valoresSoluciones[i] += 38.2;
            }
        }
        for(i = 2, j = 0; i < 8; i++,j++){
            valoresSoluciones[i] = calcularSolucionObjetivo(this.solucionesIniciales[j]);
            if(!cumpleRestriccion(this.solucionesIniciales[j])){
                valoresSoluciones[i] += 38.2;
            }
        }
        
        for(i = 0; i < 8; i++){
            //System.out.println("solucion "+ i + " es: " + valoresSoluciones[i]);
        }
        for(i = 0; i < 6; i++){
            int indiceActual = 0;
            menor = 81;
            for(j = 0; j < 8 ; j++){
                if(menor > valoresSoluciones[j]){
                    menor = valoresSoluciones[j];
                    indiceActual = j;
                    
                }
            }
            valoresSoluciones[indiceActual] = 82;
            //System.out.println("la solucion menor es: " + menor + " y se encuentra en la posicion: "+ indiceActual);
            if(indiceActual > 1){
                conjuntoSolucion[i] = this.solucionesIniciales[indiceActual - 2];
            }else{
                conjuntoSolucion[i] = hijos[indiceActual];
            }
            
        }
        
        return conjuntoSolucion;
    }
    
    private double calcularSolucionObjetivo(int hijos[]){
        double solucion = 0.0;
        for(int i = 0 ; i < 36 ; i++){
            if(hijos[i] == 1){
                solucion += this.costos[i];
            }
            
        }
        /*if(!cumpleRestriccion(hijos)){
            solucion += 38.2;
        }*/
        
        return solucion;
    }
    /**
     * Método que realiza el cruzamiento en dos puntos
     * @param solucion1 padre o solución inicial obtenida del método seleccionRuleta()
     * @param solucion2 padre o solución inicial obtenida del método seleccionRuleta()
     * @return Retorna los hijos obtenidos.
     */
    public int[][] cruzamientoEnDosPuntos(int[] solucion1, int[] solucion2){
        int[] hijo1 = solucion1.clone();
        int[] hijo2 = solucion2.clone();
        
        // En primer lugar, se eligen numeros aleatorios para obtener los indices
        
        int limiteInferior = (int) Math.round(this.generadorNumAleatorio.nextDouble() * 35);
        int limiteSuperior = (int) Math.round(this.generadorNumAleatorio.nextDouble() * 35);
        
        // Si el limite inferior es mayor que el limite superior, simplemente intercambian sus valores
        if(limiteSuperior < limiteInferior){
            int aux = limiteSuperior;
            limiteSuperior = limiteInferior;
            limiteInferior = aux;
        }
        
        for(int i = 0; i < 36; i++){

            // Se realiza la recombinacion en los intervalos obtenidos anteriormente
            
            if( i < limiteInferior && i > limiteSuperior){
                hijo1[i] = solucion1[i];
                hijo2[i] = solucion2[i];
            }
            
            if(i >= limiteInferior && i<= limiteSuperior){
                hijo1[i] = solucion2[i];
                hijo2[i] = solucion1[i];
            }
        }
        
        int[][] a= {hijo1, hijo2};
        return  a;
    }
    
    /**
     * @deprecated 
     * 
     */
    
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
        int[][] a= {hijo1, hijo2};
        return  a;
    }
    
    /**
     * Este método muta las nuevas soluciones creadas por el método cruzamientoEnDosPuntos(),
     * esto lo hace a través del operador bitflip con una probabilidad de 0.1.
     * @param hijos Matriz que contiene los hijos obtenidos por el método cruzamientoEnDosPuntos().
     * @return Se retornan los hijos mutados.
     */
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
    
    /**
     * 
     */
    public void seleccionElitista(){
        
    }
    
    /**
     * Este método selecciona a los padres de la población inicial a través de una selcción de ruleta.
     * @param numeroSoluciones Número de soluciones (padres) que se busca obtener de las soluciones iniciales (Población inicial)
     * @return Se retornan los padres seleccionados en base al criterio de Ruleta.
     */
    public int[][] seleccionRuleta(int numeroSoluciones){
        
        if(numeroSoluciones > this.solucionesIniciales.length ){
            return new int[numeroSoluciones][36];
        }
        int[][] solucionesEscogidas = new int[numeroSoluciones][36]; // En este Array se guardan las soluciones escogidas de las soluciones de la población inicial.
        ArrayList<Double> probabilidadSoluciones; // En el arraylist se guardan las probabilidades acumuladas
        int[] indicesUsados = new int[numeroSoluciones]; // Este array guarda los índices de las soluciones de la población inicial que ya han sido seleccionadas.
        double suma; // Este valor guardará la suma de las funciones objetivos de cada solucion de la poblacion Inicial (en este caso, de minimización).
        double aux; // Este valor sirve para calcular las probabilidades acumuladas.
        double nRandom; // Este valor sirve para obtener un número aleatorio entre 0 y 1 para escoger la solución inicial.
        
        for(int i = 0; i < indicesUsados.length; i++){
            indicesUsados[i] = -1;
        }
        int[][] solucionesRestantes ; // En este Array se guardan las soluciones restantes que se pueden escoger de la población inicial.        

        // El ciclo for para hasta que se obtengan las soluciones que se necesitan
        for(int k = 0; k < numeroSoluciones;){
            solucionesRestantes = this.obtenerSolucionesRestantes(indicesUsados, k) ;
            suma = 0;
            aux = 0;
            
            // Se inicializa el ArrayList
            probabilidadSoluciones = new ArrayList<>();
            probabilidadSoluciones.add(0.0);
            
            // En este ciclo for se suman las funciones objetivo de cada solución inicial que no se ha utilizado anteriormente
            for(int i= 0; i < solucionesRestantes.length; i++){
                suma += this.calculoFuncionObjetivoMinimizacion(solucionesRestantes[i]);
            }
            
            // En este ciclo se guardan las probabilidades acumuladas en la variable probabilidadSoluciones
            for(int i = 0; i < solucionesRestantes.length; i++){
                aux += this.calculoFuncionObjetivoMinimizacion(solucionesRestantes[i]);
                probabilidadSoluciones.add(aux/suma);
                
            }
            
            // Se guarda un número aleatorio entre 0 y 1 en la variable nRandom
            nRandom = this.generadorNumAleatorio.nextDouble();
            for(int i = 0; i < probabilidadSoluciones.size(); i++){
                /* Si nRandom toma valores entre los intervalos i e i+1, entonces se guarda la solución restante obtenida
                en solucionesEscogidas*/
                if(probabilidadSoluciones.get(i)< nRandom && nRandom < probabilidadSoluciones.get(i+1) ){
                    solucionesEscogidas[k] = solucionesRestantes[i];                    
                    indicesUsados[k] = i;
                    k++;

                    break;
                }
            }            
        }
        return solucionesEscogidas;
    }
    
    /**
     * Este método busca en el array 'solucionesEscogidas' si existe la solución con índice 'indiceSolucion'.
     * @param indiceSolucion Número que indica el índice de una solución de la población inicial.
     * @param solucionesEscogidas Array que contiene los índices de las soluciones de la población inicial que ya han sido utilizadas.
     * @return retorna true si la solución escogida con índice 'indiceSolucion' se encuentra en el array 'solucionesEscogidas'
     */
    public boolean solucionEscogida(int indiceSolucion, int[] solucionesEscogidas){
        boolean bandera = false; 
        for(int j =0; j < solucionesEscogidas.length; j++){
            /* Si la solución inicial 'indiceSolucion' ya ha sido guardada en solucionesEscogidas, bandera toma como valor true
            indicando que la solución inicial 'indiceSolucion' no puede ser escogida nuevamente*/
            
            if(solucionesEscogidas[j] == indiceSolucion){
                bandera = true;
                break;
            }
        }        
        return bandera;
    }
    /**
     * Este método extrae de la población inicial aquellas soluciones que no han sido escogidas.
     * @param indicesSolucionesEscogidas Array que contiene los índices de las soluciones extraídas anteriormente.
     * @param nSolucionesRestantes Número de soluciones.
     * @return Retorna las soluciones que no han sido escogidas.
     */
    public int[][] obtenerSolucionesRestantes(int[] indicesSolucionesEscogidas, int nSolucionesRestantes){
        
        int[][] solucionesRestantes = new int[this.solucionesIniciales.length - nSolucionesRestantes][36];
        int j=0;
        for(int i =0; i < this.solucionesIniciales.length; i++){
            // Si la solución de la población inicial no ha sido escogida, se agrega en la variable "solucionesRestantes"
            if(!solucionEscogida(i,indicesSolucionesEscogidas)){
                solucionesRestantes[j] = this.solucionesIniciales[i];
                j++;
            }
        }        

        return solucionesRestantes;
    }
    
    /**
     * @deprecated
     */
    
    public boolean cumpleRestriccion(int numSolucion){
        this.asignacion = new double[36];
        
        for(int i = 0; i< 36; i++){
            if(this.solucionesIniciales[numSolucion][i] != 0){

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
            return true;
        }
        return false;
    }
    
    /**
     * Este método revisa si la solución pasada por parámetro es factible.
     * @param solucion Solucion.
     * @return Retorna true si la solución es factible (todas las ciudades se encuentran marcadas).
     */
    public boolean cumpleRestriccion(int[] solucion){
        this.asignacion = new double[36];
        
        for(int i = 0; i< 36; i++){
            if(solucion[i] != 0){
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
    /**
     * Imprime las ciudades
     */
    public void imprimirCiudades(){
        for(int i = 0; i< 36; i++){
            for(int j = 0; j < 36; j++){
                //System.out.print(this.ciudades[i].obtenerEstado(j));
            }
            //System.out.println();
        }
    }
    
    /**
     * Se imprimen todas las soluciones iniciales, solo con fines de verificación
     */
    public void imprimirSoluciones(){
        int sizei = this.solucionesIniciales.length;
        int sizej = 36;
        
        for(int i= 0; i < sizei; i++){
            for(int j = 0; j < sizej; j++){
                //System.out.print(this.solucionesIniciales[i][j]);
            }
            //System.out.println();
        }
    }
    
    /**
     * 
     * @deprecated
     */
    public double calculoFuncionObjetivo(int solucionActual) {
        double valorFuncion = 0.0;
        boolean isSolucion = false;
        for(int i= 0; i < 36; i++){
            if(this.solucionesIniciales[solucionActual][i] == 1){
                valorFuncion += this.costos[i];
            }
        }
        isSolucion = cumpleRestriccion(solucionActual);
        //System.out.println("La funcion "+solucionActual+" es: "+ isSolucion + " y su valor es: " + valorFuncion);
        if(isSolucion == false) valorFuncion -= 38.2; //Explicar por que se utiliza el valor 38.2
        return valorFuncion;
    }
    /**
     * Este método calcula la función objetivo de una solución.
     * @param solucion Solución.
     * @return Retorna la función objetivo de una solución.
     */
    public double calculoFuncionObjetivo(int[] solucion) {
        double valorFuncion = 0.0;
        boolean isSolucion = false;
        for(int i= 0; i < 36; i++){
            if(solucion[i] == 1){
                valorFuncion += this.costos[i] ;
            }
        }
        isSolucion = cumpleRestriccion(solucion);
        if(isSolucion == false) valorFuncion += 38.2;
        return valorFuncion;
    }
    
    /**
     * 
     * @deprecated
     */
    public double calculoFuncionObjetivoMinimizacion(int solucionActual){
        
        double valorFuncion = 0.0;
        
        for(int i= 0; i < 36; i++){
            if(this.solucionesIniciales[solucionActual][i] == 1){
                    valorFuncion += this.costos[i] ;
            }
        }
        
        // si la solución no es factible, se le suma un costo extra
        if(!this.cumpleRestriccion(solucionActual)){
            valorFuncion += 38.2;
        }
        
        if((this.obtenerMaximo() + this.obtenerMinimo() - valorFuncion ) <0){
            return 0.0;
        }
        
        return (this.obtenerMaximo() + this.obtenerMinimo() - valorFuncion);
    }
    /**
     * Este método calcula la función objetivo, pero para un problema de minimización.
     * @param solucion Solución a la que se calculará su función objetivo de minimización.
     * @return Retorna el valor función objetivo para un problema de minimización.
     */
    public double calculoFuncionObjetivoMinimizacion(int[] solucion){
        
        double valorFuncion = 0.0;
        
        for(int i= 0; i < 36; i++){
            if(solucion[i] == 1){
                    
                    valorFuncion += this.costos[i] ;
            }
        }
        
        // Si la solucion no es factible, se le suma un costo extra
        if(!this.cumpleRestriccion(solucion)){
            //System.out.println("valor antes "+valorFuncion);
            valorFuncion += 38.2;
            //System.out.println("valor despues "+valorFuncion);
        }
        
        if((this.obtenerMaximo() + this.obtenerMinimo() - valorFuncion ) <0){
            return 0.0;
        }
        
        return (this.obtenerMaximo() + this.obtenerMinimo() - valorFuncion);
    }
    

    /**
     * Este método busca entre las soluciones de la población inicial, la solución que tiene el mayor valor de función objetivo.
     * @return Retorna, de la población inicial, el valor de la función objetivo de la solución que tiene mayor valor.
     */
    public double obtenerMaximo(){
        double maximo = 0;
        for(int i = 0; i < this.solucionesIniciales.length; i++){
            if(maximo < this.calculoFuncionObjetivo(this.solucionesIniciales[i])){
                maximo = this.calculoFuncionObjetivo(this.solucionesIniciales[i]);
            }
        }
        return maximo;
    }
    

    /**
     * Este método busca entre las soluciones de la población inicial, la solución que tiene el menor valor de función objetivo.
     * @return Retorna, de la población inicial, el valor de la función objetivo de la solución que tiene menor valor.
     */
    public double obtenerMinimo(){
        double minimo = this.calculoFuncionObjetivo(this.solucionesIniciales[0]);
        for(int i = 0; i < this.solucionesIniciales.length; i++){
            if(minimo > this.calculoFuncionObjetivo(this.solucionesIniciales[i])){
                minimo = this.calculoFuncionObjetivo(this.solucionesIniciales[i]);
            }
        }
        return minimo;
    }

    
    
}
