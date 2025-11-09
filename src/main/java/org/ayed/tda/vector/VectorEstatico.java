package org.ayed.tda.vector;

public class VectorEstatico <T> {
    private T[] datos;
    private int tamanioFisico;
    private int tamanioLogico;

    /**
     * Constructor de Vector Estático.
     *
     * @param tamanio Tamaño físico del vector.
     *                No puede ser negativo.
     * @throws ExcepcionVector si el tamaño físico es negativo.
     */
    @SuppressWarnings("unchecked")
    public VectorEstatico(int tamanio) {
        // Implementar.
        if(tamanio <0 )
            throw new ExcepcionVector("Tamanio Invalido! NO puede ser negativo el tamanio del vector");

        this.tamanioLogico = 0; // aun no hay nada en el el vector
        this.tamanioFisico= tamanio;
        // java no me dejara crear un objeto T porque o existe, pero puedo usar Object para que acepte mi T
        this.datos = (T[]) new Object[tamanio];
    }
    

    /**
     * Constructor de copia de Vector Estático.
     *
     * @param vectorEstatico Vector a copiar.
     *                       No puede ser nulo.
     * @throws ExcepcionVector si el vector es nulo.
     */
    @SuppressWarnings("unchecked")
    public VectorEstatico(VectorEstatico <T> vectorEstatico) {
        // Implementar.
        if (vectorEstatico == null)
            throw new ExcepcionVector("Vector es null");
        this.tamanioLogico= vectorEstatico.tamanioLogico;
        this.tamanioFisico= vectorEstatico.tamanioFisico;

        this.datos = (T[]) new Object[tamanioFisico];
        
        System.arraycopy(vectorEstatico.datos, 0, this.datos, 0, tamanioLogico);
    }

    /**
     * Agrega un dato al final del vector.
     *
     * @param dato Dato a agregar.
     * @throws ExcepcionVector si el vector está lleno.
     */
    public void agregar(T dato) {
        // Implementar.
        // por si el dato es nulo
        if (dato == null)
            throw new ExcepcionVector("Vector es null");
        // veo si el vector ya esta lleno al checar que no sea igual al tamanio Fisico
        if( tamanioLogico == tamanioFisico)
            throw new ExcepcionVector("Vector lleno");

        datos[tamanioLogico]= dato;
        tamanioLogico++;   // hay que actualizar tamanio logico.
        

    }

    /**
     * Agrega un dato al vector en el índice indicado.
     * <p>
     * Ejemplo:
     * <pre>
     * {@code
     * >> [1, 3, 2, 7, 0]
     * agregar(9, 2);
     * >> [1, 3, 9, 2, 7, 0]
     * }
     * </pre>
     *
     * @param dato   Dato a agregar.
     * @param indice Índice en el que se inserta el dato.
     *               No puede ser negativo.
     *               No puede ser mayor que el tamaño del vector.
     * @throws ExcepcionVector si el vector está lleno,
     *                         o si el índice no es válido.
     */
    public void agregar(T dato, int indice) {
        // Implementar.
        if( indice > tamanioLogico || indice <0)
            throw new ExcepcionVector("Indice fuera del rango del vector");
        // no tiene sentido agregar un dato basura.
        else if(dato == null)
            throw new ExcepcionVector("Dato invalido al ser Nulo");
        else if(tamanioLogico == tamanioFisico)
            throw new ExcepcionVector("Vector lleno. NO es posible agregar dato.");
        else {
            for (int i= tamanioLogico-1; indice<=i; i--){
                datos[i+1] = datos[i];  //es posible si no el anterior if me votaba
            }
            datos[indice] = dato;
            tamanioLogico++;
        }
    }

    /**
     * Elimina el último dato del vector.
     *
     * @return el dato eliminado.
     * @throws ExcepcionVector si el vector está vacío.
     */
    public T eliminar() {
        // Implementar.
        if( tamanioLogico == 0)
            throw new ExcepcionVector("Vector vacio. No se puede eliminar elemento");
        T eliminado = datos[tamanioLogico-1];
        datos[tamanioLogico-1]=null;
        tamanioLogico--;
        
        return eliminado;
    }

    /**
     * Elimina el dato del vector en el índice indicado.
     * <p>
     * Ejemplo:
     * <pre>
     * {@code
     * >> [1, 3, 2, 7, 0]
     * eliminar(1);
     * >> [1, 2, 7, 0]
     * }
     * </pre>
     *
     * @param indice Índice del dato a eliminar.
     *               No puede ser negativo.
     *               No puede ser mayor o igual que el tamaño del vector.
     * @return el dato eliminado.
     * @throws ExcepcionVector si el vector está vacío,
     *                         o si el índice no es válido.
     */
    public T eliminar(int indice) {
        // Implementar.
        if( tamanioLogico == 0)
            throw new ExcepcionVector("Vector vacio. No se puede eliminar elemento");
        if( indice >= tamanioLogico || indice <0)
            throw new ExcepcionVector("Indice fuera del rango del vector");
        // no tiene sentido agregar un dato basura.
        T eliminado = datos[indice];
        if(eliminado == null)
            throw new ExcepcionVector("Dato a eliminar es nulo");

        for (int i=indice; i<tamanioLogico-1; i++){
            datos[i] = datos[i+1];  //es posible si no el anterior if me votaba
        }
        tamanioLogico--;
        return eliminado;
    }

    /**
     * Obtiene el dato del vector en el índice indicado.
     *
     * @param indice Índice del dato a obtener.
     *               No puede ser negativo.
     *               No puede ser mayor o igual que el tamaño del vector.
     * @return el dato en el índice indicado.
     * @throws ExcepcionVector si el índice no es válido.
     */
    public T dato(int indice) {
        // Implementar.
        if(indice <0 || indice >= tamanioLogico)
            throw new ExcepcionVector("Indice fuera del rango del vector");

        return datos[indice];
    }

    /**
     * Modifica el dato del vector en el índice indicado
     * por el dato indicado por parámetro.
     *
     * @param indice Índice del dato a modificar.
     *               No puede ser negativo.
     *               No puede ser mayor o igual que el tamaño del vector.
     * @throws ExcepcionVector si el índice no es válido.
     */
    public void modificarDato(T dato, int indice) {
        // Implementar.
        if(indice <0 || indice >= tamanioLogico)    
            throw new ExcepcionVector("Indice fuera del rango del vector");
        datos[indice]= dato;
    }

    /**
     * Obtiene el tamaño del vector.
     *
     * @return el tamaño del vector.
     */
    public int tamanio() {
        // Implementar.
        return tamanioLogico;
    }

    /**
     * Obtiene el tamaño máximo de datos del vector.
     *
     * @return el tamaño máximo del vector.
     */
    public int tamanioMaximo() {
        // Implementar.
        return tamanioFisico;
    }

    /**
     * Evalúa si el vector está vacío.
     *
     * @return true si el vector está vacío.
     */
    public boolean vacio() {
        // Implementar.
        boolean noHay= tamanioLogico==0;
        return noHay;
    }
}