package org.ayed.tda.diccionario;

import org.ayed.tda.iterador.Iterador;
import org.ayed.tda.lista.Lista;
import org.ayed.tda.tupla.Tupla;
import org.ayed.tda.vector.VectorEstatico;

/**
 * Diccionario asociativo (clave, valor) que NO
 * mantiene necesariamente el orden de los datos.
 * Está implementado con un hash abierto.
 *
 * @param <C> El tipo de dato de la clave.
 *            Este tipo debe implementar el método {@code equals}
 *            e, idealmente, el método {@code hashCode}.
 * @param <V> El tipo de dato del valor.
 *            Este tipo no necesita ser comparable obligatoriamente,
 *            pero puede ser útil para el usuario si se decide
 *            implementar métodos para consultar si un valor está
 *            en el diccionario.
 */
public class Diccionario<C, V> {
    private VectorEstatico<Lista<Tupla<C, V>>> datos;
    // Completar con un valor apropiado según la teoría.
    private static final double FACTOR_DEFAULT = 0.8;
    private double factorDeCarga;
    private int tamanioTabla;
    private int cantidadDatos;

    /**
     * Función de hash. Obtiene el hash de la clave.
     *
     * @param clave Clave a hashear.
     * @return el hash.
     */
    private int hashear(C clave) {
        if (clave == null) {
            return 0;
        }
        int hash = clave.hashCode();
        return hash ^ hash >>> 16;
    }

    /**
     * Obtiene el índice de la tabla para la clave.
     *
     * @param clave Clave a acceder.
     * @return el índice de la clave.
     */
    private int obtenerIndice(C clave) {
        int h = hashear(clave);
        int indice = h % tamanioTabla;
        if (indice < 0) {
        	indice += tamanioTabla;
        }
        return indice;
    }

    /**
     * Calcula el tamaño de la tabla.
     * Debe ser el primer número primo que permita guardar
     * la cantidad de datos deseada, según el factor de
     * carga a utilizar.
     *
     * @param tamanio Cantidad de datos a guardar.
     *                Ya validado.
     * @return el tamaño de la tabla.
     */
    private int calcularTamanioTabla(int tamanio) {
        
    	// Paso 1: calcular tamanio tabla = tamanio (claves) / factor de carga
    	double division = (double) tamanio / factorDeCarga;
    	int minimo = (int) division;
    	
    	//Paso 2: Si el numero no es exacto, lo redondeamos hacia arriba
    	if (division > minimo) {
    		minimo++;
    	}
    	
    	//Paso 3: asegurar que sea al menos 2
    	if (minimo < 2) {
    		minimo = 2;
    	}
        
        //Paso 4: buscar el primer número primo >= minimo
        while (!esPrimo(minimo)) {
        	minimo++;
        }
        
        return minimo;
    }
    
    /**
     * Método para determinar si un número es primo.
     * Primero evaluya si el número es menor o igual a 1.
     * Luego llama a un método recursivo auxiliar, pasa por paramétro el primer divisor posible.
     *
     * @param n Número a evaluar.
     * @return true si el número es primo, false en caso contrario.
     */
    public boolean esPrimo(int n) {
        if (n <= 1) return false;
        return esPrimoAux(n, 2);
    }
    
    /** Método recursivo auxiliar para esPrimo.
     * 
     *  @param n Número a evaluar.
     *  @param d Divisor actual.
     *  @return true si el número es primo, false en caso contrario.
     */
    private boolean esPrimoAux(int n, int d) {
        if (d * d > n) return true;      // si es más grande que la raíz me pasé, no hay divisor -> es primo
        if (n % d == 0) return false;
        return esPrimoAux(n, d + 1);     
    }

    /**
     * Constructor.
     * <p>
     * NOTA: El tamaño de la tabla debe ser el primer número
     * primo que permita guardar la cantidad de datos deseada,
     * según el factor de carga a utilizar.
     *
     * @param tamanio       Cantidad de datos a guardar.
     *                      Debe ser positivo.
     * @param factorDeCarga Factor de carga a utilizar.
     *                      Debe estar entre 0 y 1.
     * @throws ExcepcionDiccionario si el tamaño o el factor
     *                              de carga no es válido.
     */
    public Diccionario(int tamanio, double factorDeCarga) {
    	 
    	if ( tamanio <= 0 || (factorDeCarga <= 0 || factorDeCarga > 1))
             throw new ExcepcionDiccionario("Valor invalido para construir diccionario");

         this.tamanioTabla = calcularTamanioTabla(tamanio);
         this.factorDeCarga = factorDeCarga;
         this.datos = new VectorEstatico<>(tamanioTabla);
         
         for (int i = 0; i < tamanioTabla; i++) {
             datos.agregar(new Lista<>());
         }
         this.cantidadDatos = 0;    // se inicializa con cero datos
    }

    /**
     * Constructor. Usa el factor de carga por defecto.
     *
     * @param tamanio Cantidad de datos a guardar.
     *                Debe ser positivo.
     * @throws ExcepcionDiccionario si el tamaño no es válido.
     */
    
    public Diccionario(int tamanio) {
        this(tamanio, FACTOR_DEFAULT);
    }

    /**
     * Constructor de copia de Diccionario.
     *
     * @param diccionario Diccionario a copiar.
     *                    No puede ser nulo.
     * @throws ExcepcionDiccionario si el diccionario es nulo.
     */
    public Diccionario(Diccionario<C, V> diccionario) {
    	
    	if (diccionario == null) throw new ExcepcionDiccionario("Diccionario a copiar es null");
    	
    	this.factorDeCarga = diccionario.factorDeCarga;
        this.tamanioTabla = diccionario.tamanioTabla;
        this.cantidadDatos = diccionario.cantidadDatos;
        
        datos = new VectorEstatico<>(tamanioTabla);

        for (int i = 0; i < tamanioTabla; i++) {
            Lista<Tupla<C, V>> original = diccionario.datos.dato(i);
            Lista<Tupla<C, V>> copiaLista = new Lista<>();

            Iterador<Tupla<C, V>> it = original.iterador();
            while (it.haySiguiente()) {
                Tupla<C, V> t = it.dato();
                // crear nueva tupla para evitar alias entre diccionarios
                copiaLista.agregar(new Tupla<>(t.clave(), t.valor()));
                it.siguiente();
            }

            datos.agregar(copiaLista);
        }
    }

    /**
     * Agrega un mapeo {clave, valor} al diccionario.
     * Si ya existía la clave en el diccionario, reemplaza
     * el valor anterior y lo devuelve.
     *
     * @param clave Clave a agregar.
     * @param valor Valor a agregar.
     * @return el valor anterior. Si no había un valor
     * anterior, devuelve null.
     */
    public V agregar(C clave, V valor) {

        int indice = obtenerIndice(clave);
        Lista<Tupla<C,V>> lista = datos.dato(indice);
        Iterador<Tupla<C, V>> it = lista.iterador();
        int pos = 0;
        V devolver = null;
        
        while(it.haySiguiente()) {
        	Tupla<C,V> t = it.dato();
        	
            if ((t.clave() == null && clave == null) || 
            	(t.clave() != null && t.clave().equals(clave))) {
                
            	devolver = t.valor();
            	
                lista.eliminar(pos);
                lista.agregar(new Tupla<>(clave, valor), pos);
                
                return devolver;
                
            }
            
            it.siguiente();
            pos++;

        }
        
        // Si la clave no estaba, agrego al final
        lista.agregar(new Tupla<>(clave, valor));
        cantidadDatos++;
        return null;
        
    }

    /**
     * Elimina un mapeo {clave, valor} del diccionario,
     * si existe. Si no existe, el diccionario queda en
     * el mismo estado.
     * <p>
     * NOTA: Para eliminar nodos interiores, se utiliza
     * el sucesor inmediato.
     *
     * @param clave Clave a eliminar.
     * @return el valor eliminado. Si no se eliminó
     * un valor, devuelve null.
     */
    public V eliminar(C clave) {
    	
        int indice = obtenerIndice(clave);
        Lista<Tupla<C,V>> lista = datos.dato(indice);
        Iterador<Tupla<C, V>> it = lista.iterador();
        int pos = 0;
        V devolver = null;
        
        while(it.haySiguiente()){
        	Tupla<C,V> t = it.dato();
        	
        	if ((t.clave() == null && clave == null) ||
                    (t.clave() != null && t.clave().equals(clave))) {
                    devolver = t.valor();
                    
            	lista.eliminar(pos);
                cantidadDatos--;

            }
        	
            it.siguiente();
            pos++;
        }
        return devolver;
    }

    /**
     * Obtiene un mapeo {clave, valor} del diccionario,
     * si existe.
     *
     * @param clave Clave a buscar.
     * @return el valor buscado. Si no existe, devuelve
     * null.
     */
    public V obtenerValor(C clave) {
    	
        int indice = obtenerIndice(clave);
        Lista<Tupla<C,V>> lista = datos.dato(indice);
        Iterador<Tupla<C,V>> it = lista.iterador();
        V devolver = null;
        
        while (it.haySiguiente()){
        	Tupla<C,V> t = it.dato();
        	
        	if ((t.clave() == null && clave == null) ||
                    (t.clave() != null && t.clave().equals(clave))) {
                    devolver = t.valor();
            }
            
            it.siguiente();
        }
        
        return devolver;  
        
        }
    
    /**
     * Obtiene el tamaño del diccionario.
     *
     * @return el tamaño del diccionario.
     */
    public int tamanio() {

        return cantidadDatos;
    }

    /**
     * Evalúa si el diccionario está vacío.
     *
     * @return true si el diccionario está vacío.
     */
    public boolean vacio() {

        return cantidadDatos == 0;
    }

    /**
     * Obtiene una lista de todos los valores.
     * Este método es para testear el TDA, no
     * utilizar en el proyecto.
     *
     * @return los valores.
     */
 
    public Lista<V> valores() {
        Lista<V> valores = new Lista<>();
        
        for (int i = 0; i < tamanioTabla; i++) {
        	Iterador<Tupla<C, V>> iterador = datos.dato(i).iterador();
        	
            while (iterador.haySiguiente()) {
                valores.agregar(iterador.dato().valor());
                iterador.siguiente();
            }
        }
        return valores;
    }
}