package org.ayed.tda.vector;

public class Vector<T> {
    private T[] datos;
    private int tamanioFisico;
    private int tamanioLogico;

    /**
     * Constructor de Vector.
     */
    @SuppressWarnings("unchecked")
    public Vector() {
        // Implementar.
        this.tamanioLogico=0;
        this.tamanioFisico= 0;
        // java no me dejara crear un objeto T porque o existe, pero puedo usar Object para que acepte mi T
        this.datos = (T[]) new Object[0];
    }

    /**
     * Constructor de copia de Vector.
     *
     * @param vector Vector a copiar.
     *               No puede ser nulo.
    //  * @throws ExcepcionVector si el vector es nulo.
     */
    @SuppressWarnings("unchecked")
    public Vector(Vector<T> vector) {
        // Implementar.
        if ( vector == null)
            throw new ExcepcionVector("Vector es null");

        this.tamanioLogico= vector.tamanioLogico;
        this.tamanioFisico= vector.tamanioFisico;

        this.datos = (T[]) new Object[tamanioFisico];
        
        System.arraycopy(vector.datos, 0, this.datos, 0, tamanioLogico);

    }
    /**
     * Redicmencionar el tamanio del vector. De USO PRIVADO
     * @param nuevaCapacidad Dato de nuevo tamanio deseado para el vector.
     */
    private void redimencionar(int nuevaCapacidad){
        // if( nuevaCapacidad < 0 )
        //     throw new ExcepcionVector("Problemas al redimencionar vector");        
        
        if(nuevaCapacidad == 0)
            nuevaCapacidad=1;
        T[] copia = (T[]) new Object[nuevaCapacidad]; 
        int i=0;
        while (i<tamanioLogico){
            copia[i]= datos[i]; 
            i++;
        }
        datos= copia;
        tamanioFisico = nuevaCapacidad;
        
    }
    /**
     * Agrega un dato al final del vector.
     *
     * @param dato Dato a agregar.
     */
    public void agregar(T dato) {
        // Implementar.
        // no tiene sentido agregar un dato basura.
        if(dato == null)
           throw new ExcepcionVector("Dato recibido es invalido al ser Nulo");
                
        if (tamanioFisico == tamanioLogico){
            redimencionar(tamanioFisico*2); //duplico
        }
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
     * @throws ExcepcionVector si el índice no es válido.
     */
    public void agregar(T dato, int indice) {
        // Implementar.
        // porque pongo los dos tamaños? evito asi que me metan los numeros en cualqueir lado. Ej: [1,2,3,4, nul,nul,5]
        if( indice > tamanioLogico || indice <0)
            throw new ExcepcionVector("Indice fuera del rango del vector");
        // no tiene sentido agregar un dato basura.
        if(dato == null)
            throw new ExcepcionVector("Dato invalido al ser Nulo");
        
        if (tamanioFisico == tamanioLogico){
            redimencionar(tamanioFisico*2); //duplico
        }
        
        for (int i= tamanioLogico-1; indice<=i; i--){
            datos[i+1] = datos[i];
        }
        datos[indice] = dato;
        tamanioLogico++;
        
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
        T elemento = datos[tamanioLogico-1];
        datos[tamanioLogico-1]=null;
        tamanioLogico--;
        if(tamanioFisico/2 > tamanioLogico)
            redimencionar(tamanioFisico/2);
        return elemento;
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

        if(tamanioLogico<tamanioFisico/2)
            redimencionar(tamanioFisico/2);
        
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
     * Obtiene el tamaño máximo del vector.
     * <p>
     * NOTA: Este método es únicamente para probar
     * el TDA.
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