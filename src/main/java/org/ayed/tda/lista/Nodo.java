package org.ayed.tda.lista;

/**
 * Nodo para el uso exclusivo del paquete lista (Package-private).
 */
class Nodo <T> {
    T dato;
    Nodo<T> anterior;
    Nodo<T> siguiente;

    /**
     * Constructor de Nodo.
     *
     * @param dato      Dato a guardar.
     * @param anterior  Nodo anterior.
     * @param siguiente Nodo siguiente.
     */
    public Nodo(T dato, Nodo<T> anterior, Nodo<T> siguiente) {
        this.dato = dato;
        this.anterior = anterior;
        this.siguiente = siguiente;
    }

    /**
     * Constructor de Nodo.
     *
     * @param dato Dato a guardar.
     */
    public Nodo(T dato) {
        this(dato, null, null);
    }

    /**
     * retorna el dato del nodo
     */
	public T obtenerDato() {
		return dato;
	}

    /**
     * Cambia dato del nodo
     * @param dato dato por el que cambiar
     */
	public void cambiarDato(T dato) {
		this.dato = dato;
	}
	
    /**
     * Devuelve el nodo siguiente
     */
	public Nodo<T> obtenerSiguiente() {
		return siguiente;
	}
    /**
     * Cambia el nodo siguiente
     * @param primero el nodo que sera el siguiente del nodo actual
     */
	public void cambiarSiguiente(Nodo<T> primero) {
		this.siguiente = primero;
	}
    /**
     * Devuelve el nodo anterior
     */
    	public Nodo<T> obtenerAnterior() {
		return anterior;
	}
    /**
     * Cambia el nodo anterior
     * @param primero el nodo que sera el anterior del nodo actual
     */
	public void cambiarAnterior(Nodo<T> dato) {
		this.anterior = dato;
	}

}
