package org.ayed.tda.lista;

public class Pila<T> {
    private Nodo<T> ultimo;
    private int cantidadDatos;

    /**
     * Constructor de Pila.
     */
    public Pila() {
        // Implementar.
        this.ultimo = null;
        this.cantidadDatos = 0;
    }

    /**
     * Constructor de copia de Pila.
     *
     * @param pila Pila a copiar.
     *             No puede ser nula.
     * @throws ExcepcionLista si la pila es nula.
     */
    public Pila(Pila<T> pila) {
        // Implementar.
        if (pila == null) {
            throw new ExcepcionLista("La pila a copiar no puede ser nula.");
        }
        if (pila.vacio()) {
            this.ultimo = null;
            this.cantidadDatos = 0;
        }

        // copia profunda conservando el orden LIFO
        this.ultimo = null;
        this.cantidadDatos = 0;

        if (pila.vacio()) return;

        // guardar los datos desde el fondo hasta el tope
        Object[] datos = new Object[pila.cantidadDatos];
        Nodo<T> actual = pila.ultimo;
        int i = 0;
        while (actual != null) {
            datos[i++] = actual.dato;
            actual = actual.siguiente;
        }

        // reconstruir la pila en el mismo orden LIFO
        for (int j = pila.cantidadDatos - 1; j >= 0; j--) {
            this.agregar((T) datos[j]);
        }
    }

    /**
     * Agrega el dato al final de la pila.
     *
     * @param dato Dato a agregar.
     */
    public void agregar(T dato) {
        // Implementar.
        Nodo<T> nuevo = new Nodo<>(dato, null, ultimo);
        if (ultimo != null) {
            ultimo.anterior = nuevo;
        }
        ultimo = nuevo;
        cantidadDatos++;
    }

    /**
     * Elimina el siguiente dato de la pila (LIFO).
     *
     * @return el siguiente dato de la pila.
     * @throws ExcepcionLista si la pila está vacía.
     */
    public T eliminar() {
        // Implementar.
        if (vacio()) {
            throw new ExcepcionLista("La pila está vacía.");
        }
        T dato = ultimo.dato;
        ultimo = ultimo.siguiente;
        if (ultimo != null) {
            ultimo.anterior = null;
        }
        cantidadDatos--;
        return dato;
    }

    /**
     * Obtiene el siguiente dato de la pila (LIFO).
     *
     * @return el siguiente dato de la pila.
     * @throws ExcepcionLista si la pila está vacía.
     */
    public T siguiente() {
        // Implementar.
        if (vacio()) {
            throw new ExcepcionLista("La pila está vacía.");
        }
        return ultimo.dato;
    }

    /**
     * Obtiene el tamaño de la pila.
     *
     * @return el tamaño de la pila.
     */
    public int tamanio() {
        // Implementar.
        return cantidadDatos;
    }

    /**
     * Evalúa si la pila está vacía.
     *
     * @return true si la pila está vacía.
     */
    public boolean vacio() {
        // Implementar.
        return cantidadDatos == 0;
    }
}
