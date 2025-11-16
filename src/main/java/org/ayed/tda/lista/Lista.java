package org.ayed.tda.lista;

import org.ayed.tda.iterador.Iterador;

public class Lista<T> {
    Nodo<T> primero;
    Nodo<T> ultimo;
    int cantidadDatos;

    /**
     * Constructor de Lista.
     */
    public Lista() {
        this.primero = null;
        this.ultimo = null;
        this.cantidadDatos = 0;
    }

    /**
     * Constructor de copia de Lista.
     *
     * @param lista Lista a copiar.
     *              No puede ser nula.
     * @throws ExcepcionLista si la lista es nula.
     */
    public Lista(Lista<T> lista) {
        if (lista == null) {
            throw new ExcepcionLista("La lista a copiar no puede ser nula.");
        }
        int tamanio = lista.tamanio();
        Iterador<T> iterador = lista.iterador();

        for (int i = 0; i < tamanio; i++) {
            T dato = iterador.dato();
            this.agregar(dato);
            iterador.siguiente();
        }
    }

    /**
     * Obtiene el nodo en el índice indicado.
     *
     * @param indice Índice del nodo a obtener.
     *              No puede ser negativo.
     *             No puede ser mayor o igual que el tamaño de la lista.
     * @return el nodo en el índice indicado.
     * @throws ExcepcionLista si el índice no es válido.
     */
    public Nodo<T> obtenerNodo(int indice) {
        if (indice < 0 || indice >= cantidadDatos) {
            throw new ExcepcionLista("Índice no válido.");
        }

        Nodo<T> nodo = primero;
        for (int i = 0; i < indice; i++) {
            nodo = nodo.siguiente;
        }
        return nodo;
    }

    /**
     * Limpia las referencias del nodo indicado.
     *
     * @param nodo Nodo a limpiar.
     */
    private void limpiarNodo(Nodo<T> nodo) {
        nodo.dato = null;
        nodo.anterior = null;
        nodo.siguiente = null;
    }

    /**
     * Agrega un dato al final de la lista.
     *
     * @param dato Dato a agregar.
     */
    public void agregar(T dato) {
        Nodo<T> nuevoNodo = new Nodo<>(dato);

        if (vacio()) {
            primero = nuevoNodo;
            ultimo = nuevoNodo;
        } else {
            ultimo.siguiente = nuevoNodo;
            nuevoNodo.anterior = ultimo;
            ultimo = nuevoNodo;
        }
        cantidadDatos++;
    }

    /**
     * Agrega un dato a la lista en el índice indicado.
     * <p>
     * Ejemplo:
     * <pre>
     * {@code
     * >> 0 -> 1 -> 5 -> 3
     * agregar(4, 1);
     * >> 0 -> 4 -> 1 -> 5 -> 3
     * }
     * </pre>
     *
     * @param dato   Dato a agregar.
     * @param indice Índice en el que se inserta el dato.
     *               No puede ser negativo.
     *               No puede ser mayor que el tamaño de la lista.
     * @throws ExcepcionLista si el índice no es válido.
     */
    public void agregar(T dato, int indice) {
        if (indice < 0 || indice > cantidadDatos) {
            throw new ExcepcionLista("Índice no válido.");
        }

        Nodo<T> nuevoNodo = new Nodo<>(dato);
        
        //Alta al principio
        if (indice == 0) {
            nuevoNodo.siguiente = primero;
    
            if (primero != null) {
                primero.anterior = nuevoNodo;
            }
    
            primero = nuevoNodo;
    
            if (ultimo == null) {
                ultimo = nuevoNodo;
            }
        } else{
            //Alta en el medio o al final
            Nodo<T> nodoAnterior = obtenerNodo(indice - 1);
            nuevoNodo.siguiente = nodoAnterior.siguiente;
            nodoAnterior.siguiente = nuevoNodo;
            nuevoNodo.anterior = nodoAnterior;

            if (nuevoNodo.siguiente == null){
                ultimo = nuevoNodo;
            } else{
                nuevoNodo.siguiente.anterior = nuevoNodo;
            }
        }
        cantidadDatos++;
    }

    /**
     * Elimina el último dato de la lista
     *
     * @return el dato eliminado.
     * @throws ExcepcionLista si la lista está vacía.
     */
    public T eliminar() {
        if (vacio()) {
            throw new ExcepcionLista("La lista está vacía.");
        }
        Nodo<T> nodoEliminado = ultimo;
        T datoEliminado = nodoEliminado.dato;
        if (cantidadDatos == 1) {
            primero = null;
            ultimo = null;
        } else {
            ultimo = ultimo.anterior;
            ultimo.siguiente = null;
        }
        limpiarNodo(nodoEliminado);
        cantidadDatos--;
        return datoEliminado;
    }

    /**
     * Elimina el dato de la lista en el índice indicado por parámetro.
     * <p>
     * Ejemplo:
     * <pre>
     * {@code
     * >> 0 -> 1 -> 5 -> 3
     * eliminar(1);
     * >> 0 -> 5 -> 3
     * }
     * </pre>
     *
     * @param indice Índice del dato a eliminar.
     *               No puede ser negativo.
     *               No puede ser mayor o igual que el tamaño de la lista.
     * @return el dato eliminado.
     */
    public T eliminar(int indice) {
        if (indice < 0 || indice >= cantidadDatos) {
            throw new ExcepcionLista("Índice no válido.");
        }
        
        Nodo<T> nodoEliminado = obtenerNodo(indice);
        T datoEliminado = nodoEliminado.dato;

        if (indice == 0) {
            nodoEliminado = primero;
            primero = primero.siguiente;
            if (primero != null) {
                primero.anterior = null;
            } else {
                ultimo = null;
            }
        } else {
            Nodo<T> nodoAnterior = nodoEliminado.anterior;
            nodoEliminado = nodoAnterior.siguiente;
            nodoAnterior.siguiente = nodoEliminado.siguiente;
            if (nodoEliminado.siguiente != null) {
                nodoEliminado.siguiente.anterior = nodoAnterior;
            } else {
                ultimo = nodoAnterior;
            }
        }
        limpiarNodo(nodoEliminado);
        cantidadDatos--;
        return datoEliminado;
    }

    /**
     * Obtiene el dato de la lista en el índice indicado.
     *
     * @param indice Índice del dato a obtener.
     *               No puede ser negativo.
     *               No puede ser mayor o igual que el tamaño de la lista.
     * @return el dato en el índice indicado.
     * @throws ExcepcionLista si el índice no es válido.
     */
    public T dato(int indice) {
        if (indice < 0 || indice >= cantidadDatos) {
            throw new ExcepcionLista("Índice no válido.");
        }

        Nodo<T> nodo = obtenerNodo(indice);
        T dato = nodo.dato;
        return dato;
    }

    /**
     * Modifica el dato de la lista en el índice indicado
     * por el dato indicado por parámetro.
     *
     * @param indice Índice del dato a modificar.
     *               No puede ser negativo.
     *               No puede ser mayor o igual que el tamaño de la lista.
     * @throws ExcepcionLista si el índice no es válido.
     */
    public void modificarDato(T dato, int indice) {
        if (indice < 0 || indice >= cantidadDatos) {
            throw new ExcepcionLista("Índice no válido.");
        }

        Nodo<T> nodo = obtenerNodo(indice);
        nodo.dato = dato;
    }

    /**
     * Obtiene el tamaño de la lista.
     *
     * @return el tamaño de la lista.
     */
    public int tamanio() {
        return cantidadDatos;
    }

    /**
     * Evalúa si la lista está vacía.
     *
     * @return true si la lista está vacía.
     */
    public boolean vacio() {
        return cantidadDatos == 0;
    }

    /**
     * Obtiene un iterador bidireccional posicionado
     * en el primer dato de la lista.
     *
     * @return el iterador.
     * @see Iterador
     */
    public Iterador<T> iterador() {
        return new IteradorLista<>(this);
    }

    /**
     * Obtiene un iterador bidireccional posicionado
     * en el índice indicado por parámetro.
     *
     * @param indice Índice del nodo inicial del iterador.
     *               No puede ser negativo.
     *               No puede ser mayor que el tamaño de la lista.
     * @return el iterador.
     * @throws ExcepcionLista si el índice no es válido.
     * @see Iterador
     */
    public Iterador<T> iterador(int indice) {
        if (indice < 0 || indice >= cantidadDatos) {
            throw new ExcepcionLista("Índice no válido.");
        }

        return new IteradorLista<>(this, indice);
        
    }
}