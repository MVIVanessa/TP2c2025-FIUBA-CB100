package org.ayed.tda.lista;

import org.ayed.tda.iterador.ExcepcionNoHayDato;
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
        this.cursor = lista.primero;
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
	    if (indice == lista.tamanio()) {
	        // Cursor al final: no hay nodo
	        this.cursor = null;
	    } else {
	        this.cursor = lista.obtenerNodo(indice);
	    }
	    this.indice = indice;
	}

    @Override
    public T dato() {
    	if (cursor == null) {
            throw new ExcepcionNoHayDato("El iterador no apunta a ningún nodo.");
        }
        return cursor.obtenerDato();
    }
    
    @Override
    public boolean haySiguiente() {
    	return (cursor != null && cursor.siguiente != null) || (cursor == null && lista.primero != null);
    }

    @Override
    public void siguiente() {
        if (!haySiguiente()) {
            throw new ExcepcionNoHayDato("No hay siguiente.");
        }
        if (cursor == null) {
            // cursor estaba antes del primer nodo → movemos al primero
            cursor = lista.primero;
            indice = 0;
        } else {
            cursor = cursor.siguiente;
            indice++;
        }
    }
    

    @Override
    public boolean hayAnterior() {
        return (cursor != null && cursor.anterior != null) || (cursor == null && lista.ultimo != null);
   }

    @Override
    public void anterior() {
        if (!hayAnterior()) {
            throw new ExcepcionNoHayDato("No hay anterior.");
        }
        if (cursor == null) {
            // estamos al final → movemos al último nodo
            cursor = lista.ultimo;
            indice = lista.tamanio() - 1;
        } else {
            cursor = cursor.anterior;
            indice--;
        }
    }

    @Override
    public void agregar(T dato) {

        if (lista.vacio()) {
            lista.agregar(dato);
            cursor = lista.primero;
        } else {
            lista.agregar(dato, indice);
            cursor = cursor != null ? cursor.anterior : lista.ultimo;
            indice++;
        }

        lista.cantidadDatos++;
    }

    
    @Override
    public void modificarDato(T dato) {
        if (cursor == null) {
            throw new ExcepcionNoHayDato("No hay dato para modificar.");
        }
        cursor.dato = dato;
    }

    @Override
    public T eliminar() {
        T eliminado;
    	
    	if (cursor == null) {
            throw new ExcepcionNoHayDato("No hay dato para eliminar.");
        }        
    	
    	if (lista.vacio()) {
            throw new ExcepcionNoHayDato("La lista está vacía.");
        } else {
            eliminado = lista.eliminar(indice);
            indice--;
            cursor = cursor.anterior;
        }

        lista.cantidadDatos--;
    	return eliminado;
    }
}