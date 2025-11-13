package org.ayed.tda.lista;

public class Cola<T> {
    private Nodo<T> primero;
    private Nodo<T> ultimo;
    private int cantidadDatos;

    /**
     * Constructor de Cola.
     */
    public Cola() {
        primero = null;
        ultimo = null;
        cantidadDatos = 0;
    }

    /**
     * Constructor de copia de Cola.
     *
     * @param cola Cola a copiar.
     *             No puede ser nula.
     * @throws ExcepcionLista si la cola es nula.
     */
    public Cola(Cola<T> cola) {
        if (cola == null) {
        throw new ExcepcionLista("La cola es nula.");
        }

        this.primero = null;
        this.ultimo = null;
        this.cantidadDatos = 0;

        Nodo<T> actual = cola.primero;
        while (actual != null) {
            Nodo<T> nuevo = new Nodo<>(actual.dato);
            if (this.ultimo == null) {
                this.primero = nuevo;
            } else {
                this.ultimo.siguiente = nuevo;
                nuevo.anterior = this.ultimo;
            }
            this.ultimo = nuevo;
            this.cantidadDatos++;
            actual = actual.siguiente;
        }
    }

    /**
     * Agrega el dato al final de la cola.
     *
     * @param dato Dato a agregar.
     */
    public void agregar(T dato) {
        Nodo<T> nuevo = new Nodo<>(dato);
        if (cantidadDatos == 0) {
            primero= nuevo;
            ultimo= nuevo;
        }
        else {
            ultimo.siguiente = nuevo;
            nuevo.siguiente = null;
            nuevo.anterior = ultimo;
            ultimo = nuevo;
        }
        cantidadDatos++;
    }

    /**
     * Elimina el siguiente dato de la cola (FIFO).
     *
     * @return el siguiente dato de la cola.
     * @throws ExcepcionLista si la cola está vacía.
     */
    public T eliminar() {
        if (cantidadDatos == 0) {
            throw new ExcepcionLista("La cola está vacía.");
        }

        T datoEliminado = primero.dato;

		if (primero == ultimo) {
            primero = null;
			ultimo = null;
        }
        else {
            primero = primero.siguiente;
            primero.anterior = null;
        }
        cantidadDatos--;
		return datoEliminado;
    }

    /**
     * Obtiene el siguiente dato de la cola (FIFO).
     *
     * @return el siguiente dato de la cola.
     * @throws ExcepcionLista si la cola está vacía.
     */
    public T siguiente() {
        if (cantidadDatos == 0) {
            throw new ExcepcionLista("La cola está vacía.");
        }
        return primero.dato;
    }

    /**
     * Obtiene el tamaño de la cola.
     *
     * @return el tamaño de la cola.
     */
    public int tamanio() {
        return cantidadDatos;
    }

    /**
     * Evalúa si la cola está vacía.
     *
     * @return true si la cola está vacía.
     */
    public boolean vacio() {
        return (cantidadDatos==0);
    }
}
