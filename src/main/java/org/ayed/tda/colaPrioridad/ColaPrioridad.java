package org.ayed.tda.colaPrioridad;

import org.ayed.tda.comparador.Comparador;
import org.ayed.tda.vector.Vector;

public class ColaPrioridad<T> {
    // NOTA: Implementar el vector con factor-of-two
    // para tener complejidades logarítmicas.
    private Vector<T> datos;
    private Comparador<T> comparador;

    /**
     * Constructor de ColaPrioridad.
     *
     * @param comparador Comparador a utilizar.
     *                   No puede ser nulo.
     * @throws ExcepcionColaPrioridad si el comparador es nulo.
     */
    public ColaPrioridad(Comparador<T> comparador) {
        // Implementar.
        if (comparador == null) {
            throw new ExcepcionColaPrioridad("El comparador no puede ser nulo.");
        }
        this.comparador = comparador;
        this.datos = new Vector<>();
    }

    /**
     * Constructor de copia de ColaPrioridad.
     *
     * @param colaPrioridad Cola a copiar.
     *                      No puede ser nula.
     * @throws ExcepcionColaPrioridad si la cola es nula.
     */
    public ColaPrioridad(ColaPrioridad<T> colaPrioridad) {
        // Implementar.
        if (colaPrioridad == null) {
            throw new ExcepcionColaPrioridad("La cola a copiar no puede ser nula.");
        }
        this.comparador = colaPrioridad.comparador;
        this.datos = new Vector<>(colaPrioridad.datos);
    }
    
    /**
     * Intercambia los datos en las posiciones i y j del vector.
     *
     * @param i Índice del primer dato.
     * @param j Índice del segundo dato.
     */
    private void intercambiar(int i, int j) {
        T temp = datos.dato(i);
        datos.modificarDato(datos.dato(j), i);
        datos.modificarDato(temp, j);
    }

    /**
     * Reordena el Heap para mantener el invariante.
     * Desplaza datos hacia arriba, comparando el dato actual
     * con su padre, hasta cumplir con el invariante.
     * Inicia en el último dato del vector.
     */
    private void heapificarHaciaArriba() {
        // Implementar.
        int indice = datos.tamanio() - 1;

        while (indice > 0) {
            int padre = (indice - 1) / 2;
            T actual = datos.dato(indice);
            T padreDato = datos.dato(padre);

            if (comparador.comparar(actual, padreDato) > 0) {
                intercambiar(indice, padre);
                indice = padre;
            } else {
                indice = 0;
            }
        }
    }

    /**
     * Reordena el Heap para mantener el invariante.
     * Desplaza datos hacia abajo, comparando el dato actual
     * con el hijo con mayor prioridad, hasta cumplir con el
     * invariante. Inicia en el primer dato del vector.
     */
    private void heapificarHaciaAbajo() {
        // Implementar.
        int indice = 0;
        int tamanio = datos.tamanio();
        boolean seguir = true;

        while (seguir) {
            int hijoIzq = 2 * indice + 1;
            int hijoDer = 2 * indice + 2;
            int mayor = indice;

            if (hijoIzq < tamanio && comparador.comparar(datos.dato(hijoIzq), datos.dato(mayor)) > 0) {
                mayor = hijoIzq;
            }
            if (hijoDer < tamanio && comparador.comparar(datos.dato(hijoDer), datos.dato(mayor)) > 0) {
                mayor = hijoDer;
            }

            if (mayor != indice) {
                intercambiar(indice, mayor);
                indice = mayor;
            } else {
                seguir = false;
            }
        }
    }


    /**
     * Agrega el dato a la cola, manteniendo el invariante
     * del Heap.
     *
     * @param dato Dato a agregar.
     */
    public void agregar(T dato) {
        // Implementar.
        if (dato == null) {
            throw new ExcepcionColaPrioridad("El dato a agregar no puede ser nulo.");
        }
        datos.agregar(dato);
        heapificarHaciaArriba();
    }

    /**
     * Elimina el siguiente dato de la cola (mayor prioridad),
     * manteniendo el invariante del Heap.
     *
     * @return el dato con mayor prioridad en la cola.
     * @throws ExcepcionColaPrioridad si la cola está vacía.
     */
    public T eliminar() {
        // Implementar.
        if (vacio()) {
            throw new ExcepcionColaPrioridad("La cola está vacía.");
        }
        T raiz = datos.dato(0);
        T ultimo = datos.eliminar();
        if (!vacio()) {
            datos.modificarDato(ultimo, 0);
            heapificarHaciaAbajo();
        }
        return raiz;
    }

    /**
     * Obtiene el siguiente dato de la cola (mayor prioridad).
     *
     * @return el dato con mayor prioridad en la cola.
     * @throws ExcepcionColaPrioridad si la cola está vacía.
     */
    public T siguiente() {
        // Implementar.
        if (vacio()) {
            throw new ExcepcionColaPrioridad("La cola está vacía.");
        }
        return datos.dato(0);
    }

    /**
     * Obtiene el tamaño de la cola.
     *
     * @return el tamaño de la cola.
     */
    public int tamanio() {
        // Implementar.
        return datos.tamanio();
    }

    /**
     * Evalúa si la cola está vacía.
     *
     * @return true si la cola está vacía.
     */
    public boolean vacio() {
        // Implementar.
        return datos.tamanio() == 0;
    }
}
