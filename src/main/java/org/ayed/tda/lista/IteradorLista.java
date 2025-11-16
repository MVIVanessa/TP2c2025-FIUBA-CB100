package org.ayed.tda.lista;

import org.ayed.tda.iterador.Iterador;

class IteradorLista<T> implements Iterador<T> {
    private Lista<T> lista;
    private Nodo<T> cursor;
    private int indice;

    /**
     * Constructor de Iter.
     *
     * @param lista Lista a iterar.
     */

	IteradorLista(Lista<T> lista) {
        this.lista = lista;
        this.cursor = lista.obtenerNodo(0);
        this.indice = 0;
    }

    /**
     * Constructor de Iter.
     *
     * @param lista  Lista a iterar.
     * @param indice Índice inicial del iterador.
     */
    
	IteradorLista(Lista<T> lista, int indice) {
        this.lista = lista;
        this.cursor = lista.obtenerNodo(indice);
        this.indice = indice;
    }

    @Override
    public T dato() {
        return cursor.obtenerDato();
    }

    @Override
    public boolean haySiguiente() {
        return cursor != null && cursor.siguiente != null;
    }

    @Override
    public void siguiente() {
        if (haySiguiente()) {
        	cursor = cursor.siguiente;
        	indice++;
        }
    }

    @Override
    public boolean hayAnterior() {
    	return cursor != null && cursor.anterior != null;
    }

    @Override
    public void anterior() {
        if (hayAnterior()) {
        	cursor = cursor.anterior;
        	indice--;
        }
    }

    @Override
    public void agregar(T dato) {
        Nodo<T> nuevo = new Nodo<>(dato, cursor, cursor.siguiente);
        
        if (cursor.siguiente != null) {
        	cursor.siguiente.anterior = nuevo;
        } else {
        	lista.ultimo = nuevo;
        }
        
        cursor.siguiente = nuevo;
        lista.cantidadDatos++;
    }

    @Override
    public void modificarDato(T dato) {
        cursor.dato = dato;
    }

    @Override
    public T eliminar() {
    	
    	if (cursor == null) {
    		return null;
    	}
    	
    	T eliminado = cursor.dato;
    	
    	Nodo<T> anterior = cursor.anterior;
    	Nodo<T> siguiente = cursor.siguiente;
    	
    	if (anterior != null) {
    		anterior.siguiente = siguiente;
    	} else {
    		lista.primero = siguiente;
    	}
    	
    	if (siguiente != null) {
    		siguiente.anterior = anterior;
    		cursor = siguiente;
    		
    	} else {
    		lista.ultimo = anterior;
    		cursor = anterior;
    		indice--;
    	}
    	
    	lista.cantidadDatos--;
    	return eliminado;
    }
}
