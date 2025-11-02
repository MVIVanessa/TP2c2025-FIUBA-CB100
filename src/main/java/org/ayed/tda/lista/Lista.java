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
        // Implementar.
        primero = null;
        ultimo= null;
        cantidadDatos = 0;
    }

    /**
     * Constructor de copia de Lista.
     *
     * @param lista Lista a copiar.
     *              No puede ser nula.
     * @throws ExcepcionLista si la lista es nula.
     */
    public Lista(Lista<T> lista) {
        // Implementar.

        if(lista == null)
            throw new ExcepcionLista("Lista nula");
        this.primero = new Nodo (lista.dato(0));
        // modifica el nodo dato por el dato ultimo de la lista;
        this.ultimo = new Nodo (lista.dato(lista.tamanio()));
        this.cantidadDatos= lista.tamanio();
    }

    /**
     * Agrega un dato al final de la lista.
     *
     * @param dato Dato a agregar.
     */
    public void agregar(T dato) {
        // Implementar.
        
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
        // Implementar.

    }

    /**
     * Elimina el último dato de la lista
     *
     * @return el dato eliminado.
     * @throws ExcepcionLista si la lista está vacía.
     */
    public T eliminar() {
        // Implementar.
        return (T) new Object();
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
        // Implementar.
        return (T) new Object();
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
        // Implementar.
        return (T) new Object();
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
        // Implementar.
    }

    /**
     * Obtiene el tamaño de la lista.
     *
     * @return el tamaño de la lista.
     */
    public int tamanio() {
        // Implementar.
        return 0;
    }

    /**
     * Evalúa si la lista está vacía.
     *
     * @return true si la lista está vacía.
     */
    public boolean vacio() {
        // Implementar.
        return true;
    }

    /**
     * Obtiene un iterador bidireccional posicionado
     * en el primer dato de la lista.
     *
     * @return el iterador.
     * @see Iterador
     */
    public Iterador<T> iterador() {
        // Implementar.
        return (Iterador<T>) new Object();
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
        // Implementar.
        return (Iterador<T>) new Object();
    }
}
