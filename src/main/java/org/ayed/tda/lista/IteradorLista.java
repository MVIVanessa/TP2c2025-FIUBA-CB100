package org.ayed.tda.lista;

import org.ayed.tda.iterador.ExcepcionNoHayDato;
import org.ayed.tda.iterador.Iterador;

/**
 * Iterador bidireccional para la clase Lista.
 * Permite recorrer la lista hacia adelante y hacia atrás,
 * agregar, modificar y eliminar elementos de manera controlada.
 * 
 * @param <T> Tipo de dato almacenado en la lista.
 */

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
        this.cursor = null;	// antes del primer nodo
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
		
		if (indice == 0) {
	        // cursor "antes del primero"
	        this.cursor = null;
	    } else if (indice > 0 && indice <= lista.tamanio()) {
	        // cursor queda en nodo (indice-1)
	        this.cursor = lista.obtenerNodo(indice - 1);
	    } else {
	        // índice después del último → cursor al último
	        this.cursor = lista.ultimo;
	    }
	}
	
	
	/**
     * Devuelve el dato actual del iterador.
     * El "dato actual" se define como el nodo siguiente al cursor.
     *
     * @return Dato actual.
     * @throws ExcepcionNoHayDato si el iterador está al final o la lista está vacía.
     */
	
    @Override
    public T dato() {
    	if (cursor == null) {
            if (lista.primero == null)
                throw new ExcepcionNoHayDato("La lista está vacía.");
            return lista.primero.dato;  // antes del primer nodo → primero es actual
        }

        if (cursor.siguiente == null)
            throw new ExcepcionNoHayDato("No hay dato en esta posición.");

        return cursor.siguiente.dato;   // siguiente al cursor
    }
    
    /**
     * Indica si hay un siguiente dato.
     *
     * @return true si existe un dato siguiente, false si está al final.
     */
    
    @Override
    public boolean haySiguiente() {
    	if (cursor == null) {
            return lista.primero != null;
        }
        return cursor.siguiente != null;
    }
    
    /**
     * Avanza el iterador al siguiente nodo.
     *
     * @throws ExcepcionNoHayDato si no hay siguiente.
     */

    @Override
    public void siguiente() {
    	if (!haySiguiente()) {
            throw new ExcepcionNoHayDato("No hay siguiente.");
        }

        if (cursor == null) {
            cursor = lista.primero;   // cursor pasa al primer nodo
        } else {
            cursor = cursor.siguiente; // cursor avanza al siguiente
        }

        indice++;
    }
    
    /**
     * Indica si hay un dato anterior al cursor.
     *
     * @return true si se puede retroceder, false si está antes del primer nodo.
     */

    @Override
    public boolean hayAnterior() {
    	 return cursor != null;
    }
    
    /**
     * Retrocede el iterador al nodo anterior.
     *
     * @throws ExcepcionNoHayDato si no hay nodo anterior.
     */
    
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
    
    /**
     * Agrega un nuevo dato **después del cursor**.
     * El cursor se mueve al nodo recién agregado.
     *
     * Casos especiales:
     * 1. Lista vacía → nuevo nodo es primero y último.
     * 2. Cursor == null → insertar al principio.
     * 3. Cursor en nodo válido → insertar después del cursor.
     *
     * @param dato Dato a agregar.
     */
    
    @Override
    public void agregar(T dato) {
    	Nodo<T> nuevo = new Nodo<>(dato);

        // CASO 1: lista vacía o cursor == null → insertar al principio o al final
        if (lista.primero == null) {
            // insertar primer nodo
            lista.primero = nuevo;
            lista.ultimo = nuevo;
            cursor = nuevo;
            lista.cantidadDatos++;
            return;
        }

        if (cursor == null) {
            // cursor al final → insertar después del último
            nuevo.anterior = lista.ultimo;
            lista.ultimo.siguiente = nuevo;
            lista.ultimo = nuevo;
            cursor = nuevo;   // MUY IMPORTANTE
            lista.cantidadDatos++;
            return;
        }

        // CASO 2: insertar DESPUÉS del cursor
        Nodo<T> sig = cursor.siguiente;

        nuevo.anterior = cursor;
        nuevo.siguiente = sig;
        cursor.siguiente = nuevo;

        if (sig != null) {
            sig.anterior = nuevo;
        } else {
            // cursor era el último
            lista.ultimo = nuevo;
        }

        cursor = nuevo;   // EL CURSOR SE MUEVE AL NUEVO NODO
        lista.cantidadDatos++;
    }
    
    /**
     * Modifica el dato actual del iterador.
     * El "dato actual" se considera como el nodo siguiente al cursor.
     *
     * @param dato Nuevo valor.
     * @throws ExcepcionNoHayDato si el cursor está al final o la lista está vacía.
     */
    
    @Override
    public void modificarDato(T dato) {
    	
    	 // Caso especial: iterador al final
    	if (cursor != null && cursor.siguiente == null) {
            throw new ExcepcionNoHayDato("No se puede modificar: el iterador está al final.");
        }

        if (cursor == null) {
            if (lista.vacio()) {
                throw new ExcepcionNoHayDato("No hay dato para modificar: lista vacía.");
            }
            // Cursor antes del primero → modificar el primero
            lista.primero.dato = dato;
        } else {
            // Cursor en nodo válido y hay siguiente → modificar ese "dato actual"
            cursor.siguiente.dato = dato;
        }
    }
    
    /**
     * Elimina el dato actual del iterador.
     * El "dato actual" es el nodo siguiente al cursor.
     *
     * Casos especiales:
     * 1. Cursor == null → elimina primero.
     * 2. Cursor en nodo válido → elimina cursor.siguiente.
     * 3. Nodo eliminado era último → actualizar último.
     *
     * @return Dato eliminado.
     * @throws ExcepcionNoHayDato si no hay dato para eliminar.
     */

    @Override
    public T eliminar() {
    	
    	// Caso 1: lista vacía o cursor al final
        if (!haySiguiente()) {
            throw new ExcepcionNoHayDato("No hay dato para eliminar.");
        }

        Nodo<T> actual;

        // Caso 2: cursor == null -> elimina el primero
        if (cursor == null) {
            actual = lista.primero;

            // mover primero
            lista.primero = actual.siguiente;
            if (lista.primero != null) {
                lista.primero.anterior = null;
            } else {
                // la lista quedó vacía
                lista.ultimo = null;
            }

            lista.cantidadDatos--;
            return actual.dato;
        }

        // Caso 3: cursor != null -> elimina cursor.siguiente (actual)
        actual = cursor.siguiente;

        Nodo<T> siguiente = actual.siguiente;
        cursor.siguiente = siguiente;

        if (siguiente != null) {
            siguiente.anterior = cursor;
        } else {
            lista.ultimo = cursor;
        }

        lista.cantidadDatos--;
        return actual.dato;
    }
}