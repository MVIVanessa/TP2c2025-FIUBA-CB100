package org.ayed.tda.diccionario;

import org.ayed.tda.comparador.Comparador;
import org.ayed.tda.lista.Lista;
import org.ayed.tda.lista.Cola;
import org.ayed.tda.tupla.Tupla;

/**
 * Diccionario asociativo (clave, valor) que mantiene
 * el orden de los datos en base a su clave y al
 * comparador utilizado. Está implementado con un
 * árbol binario de búsqueda no balanceado.
 *
 * @param <C> El tipo de dato de la clave.
 *            Este tipo debe ser comparable.
 * @param <V> El tipo de dato del valor.
 *            Este tipo no necesita ser comparable obligatoriamente,
 *            pero puede ser útil para el usuario si se decide
 *            implementar métodos para consultar si un valor está
 *            en el diccionario.
 */
public class DiccionarioOrdenado<C, V> {
    private Nodo<C, V> raiz;
    private Comparador<C> comparador;
    private int cantidadDatos;

    /**
     * Constructor.
     *
     * @param comparador Comparador a utilizar.
     *                   No puede ser nulo.
     * @throws ExcepcionDiccionario si el comparador es nulo.
     */
    public DiccionarioOrdenado(Comparador<C> comparador) {
        // Implementar.
        if( comparador == null)
            throw new ExcepcionDiccionario("Comparador nulo");
        this.comparador = comparador;
        raiz=null;
        cantidadDatos= 0;

    }

    /**
     * Constructor de copia de DiccionarioOrdenado.
     * <p>
     * TIP: Implementar un método que clone un subárbol.
     *
     * @param diccionarioOrdenado Diccionario a copiar.
     *                            No puede ser nulo.
     * @throws ExcepcionDiccionario si el diccionario es nulo.
     */
    public DiccionarioOrdenado(DiccionarioOrdenado<C, V> diccionarioOrdenado) {
        // Implementar.
        if(diccionarioOrdenado == null)
            throw new ExcepcionDiccionario("Diccionario nulo");
        this.comparador= diccionarioOrdenado.comparador;
        this.raiz= diccionarioOrdenado.raiz;
        this.cantidadDatos=0;
        if(!vacio())
            agregarNodos(diccionarioOrdenado.raiz);
    }

    /**
     * Agrega todos los nodos apartir de un nodo.
     * @param raiz Nodo inicial, apartir de este se agregaran los elementos
     */
    private void agregarNodos(Nodo<C,V> raiz){
        
        agregar(raiz.clave, raiz.valor);

        if(raiz.hijoDerecho!= null){
            agregarNodos(raiz.hijoDerecho);
        }
        if(raiz.hijoIzquierdo!=null)
            agregarNodos(raiz.hijoIzquierdo);

    }

    /**
     * Obtiene el sucesor inmediato del nodo.
     *
     * @param nodo Nodo inicial.
     * @return el sucesor inmediato.
     */
    private Nodo<C, V> obtenerSucesorInmediato(Nodo<C, V> nodo) {
        // Implementar.
        Nodo<C,V> sucesor = null;
        
        if(nodo.hijoDerecho!=null){
            nodo = nodo.hijoDerecho;
            while(nodo.hijoIzquierdo!= null){
                nodo= nodo.hijoIzquierdo;
            }
            sucesor= nodo;
        }

        return sucesor;
    }

    /**
     * Agrega un mapeo {clave, valor} al diccionario.
     * Si ya existía la clave en el diccionario, reemplaza
     * el valor anterior y lo devuelve.
     *
     * @param clave Clave a agregar.
     * @param valor Valor a agregar.
     * @return el valor anterior. Si no había un valor
     * anterior, devuelve null.
     */
    public V agregar(C clave, V valor) {
        // Implementar.
        V repetido= null;
        boolean insertado=false;
        if(vacio()){
            raiz =new Nodo<C,V> (clave,valor);
            cantidadDatos++;
        }else{    Nodo<C,V> cursor = raiz;
            while( cursor!=null && !insertado){
                if(comparador.comparar(cursor.clave, clave)<0){
                    if(cursor.hijoDerecho==null){
                        cursor.hijoDerecho= new Nodo<C,V>(clave,valor);
                        insertado=true;
                        cantidadDatos++;

                    }else
                        cursor= cursor.hijoDerecho;


                }else if( comparador.comparar(cursor.clave, clave)>0){
                    if(cursor.hijoIzquierdo==null){
                        cursor.hijoIzquierdo=new Nodo<C,V>(clave,valor);
                        insertado=true;
                        cantidadDatos++;
                    }else
                        cursor= cursor.hijoIzquierdo;
                }else{
                    insertado=true;
                    repetido= cursor.valor;
                    cursor.valor= valor;
                }

            }

        }

        return repetido;
    }

    /**
     * Elimina un mapeo {clave, valor} del diccionario,
     * si existe. Si no existe, el diccionario queda en
     * el mismo estado.
     * <p>
     * NOTA: Para eliminar nodos interiores, se utiliza
     * el sucesor inmediato.
     *
     * @param clave Clave a eliminar.
     * @return el valor eliminado. Si no se eliminó
     * un valor, devuelve null.
     */
    public V eliminar(C clave) {
        // Implementar.
        V eliminado= null;
        if(!vacio()){
            Nodo <C,V> cursor= raiz;
            Nodo <C,V> padreCursor= null;
            while( cursor!=null && eliminado==null){
                if(comparador.comparar(cursor.clave, clave)<0){
                    padreCursor=cursor;
                    cursor=cursor.hijoDerecho;
                }else if( comparador.comparar(cursor.clave, clave)>0){
                    padreCursor=cursor;
                    cursor= cursor.hijoIzquierdo;
                }else
                    eliminado= cursor.valor;
            }

            if(eliminado!=null){

                //caso de se tenga el cursor 0 hijos
                if(cursor.hijoDerecho==null && cursor.hijoIzquierdo==null)
                    reemplazaCursor(padreCursor,cursor,null);
                //caso de se tenga el cursor 1 hijo
                else if( cursor.hijoDerecho==null || cursor.hijoIzquierdo==null){
                    if(cursor.hijoIzquierdo!=null)
                        reemplazaCursor(padreCursor, cursor, cursor.hijoIzquierdo);
                    else
                        reemplazaCursor(padreCursor, cursor, cursor.hijoDerecho);
                     

                //caso de se tenga el cursor 2 hijos=> ir a por el sucesor
                }else{  
                        Nodo <C,V> sucesor= obtenerSucesorInmediato(cursor);
                        cursor.clave=sucesor.clave;
                        cursor.valor=sucesor.valor;
                        cambioNodoHastaEliminar(cursor.hijoDerecho, sucesor);
                    
                }
                cantidadDatos--;
            }
        }
        return eliminado;
    }

    /** Funcion que se encarga de remplazar los valores en caso de q el sucesor tenga hijo
     * @param padre padre del cursor;
     * @param sucesor Sucesor del cursor que se calculo anteriorimente
     */
    private void cambioNodoHastaEliminar(Nodo<C,V> padre, Nodo<C,V>sucesor){
        if(padre.hijoIzquierdo==sucesor){
            padre.hijoIzquierdo=sucesor.hijoDerecho;
        }
        else if(padre.hijoIzquierdo!=null)
            cambioNodoHastaEliminar(padre.hijoIzquierdo, sucesor);
        else if(padre.hijoDerecho!=null) 
            cambioNodoHastaEliminar(padre.hijoDerecho,sucesor);
    }

    /** Reemplaza el nodo sin afectar al resto del arbol
     *  @param padre Padre del nodo
     *  @param nodo El nodo a reemplazar
     *  @param remplazo el valor/nodo por el que remplazaremos nuestro nodo puede ser hasta null
     */
    private void reemplazaCursor(Nodo<C,V> padre, Nodo<C,V> nodo, Nodo<C,V>remplazo){
        if(padre == null)
            raiz= remplazo;
        else if(padre.hijoDerecho== nodo)
            padre.hijoDerecho=remplazo;
        else if( padre.hijoIzquierdo == nodo)
            padre.hijoIzquierdo=remplazo;
    }

    /**
     * Obtiene un mapeo {clave, valor} del diccionario,
     * si existe.
     *
     * @param clave Clave a buscar.
     * @return el valor buscado. Si no existe, devuelve
     * null.
     */
    public V obtenerValor(C clave) {
        // Implementar.
        return !vacio() ? buscandoValor(clave, raiz, cantidadDatos) : null;
    }
    /**
     * Metodo que usa la recursividad para hayar ek valor buscado segun la clave
     * @param clave clave con la que se busca
     * @param raiz Nodo por el donde avanzamos
     * @param cant contador de la cantidad de datos que tenermos para tener un alto
     */
    private V buscandoValor( C clave, Nodo <C,V> raiz, int cant){
        V encontrado=null;
        if(cant>0){
            if( raiz.clave==clave)
                return raiz.valor;
            if( raiz.hijoDerecho != null){
                encontrado=buscandoValor(clave, raiz.hijoDerecho,cant--); 
            }
            if( raiz.hijoIzquierdo != null && encontrado==null){
                encontrado=buscandoValor(clave, raiz.hijoIzquierdo,cant--); 
            }
        }

            
        return encontrado;
    }

    /**
     * Devuelve el recorrido inorder del árbol.
     *
     * @return el recorrido.
     */
    public Lista<Tupla<C, V>> inorder() {
        // Implementar.
        Lista <Tupla<C,V>> lista= new Lista<Tupla<C,V>>();
        valoresEnInorden(raiz, lista);
        return lista;
    }

    /**
     * Se encarga de de agregar los valores en tipo de recorrido Inorden a la lista
     */
    private void valoresEnInorden(Nodo<C,V> raiz, Lista<Tupla<C,V>> lista){
        if(raiz==null)
            return;
        if(raiz.hijoIzquierdo!=null)
            valoresEnInorden(raiz.hijoIzquierdo, lista);
        Tupla <C,V> agregarTupla= new Tupla<>(raiz.clave, raiz.valor);
        lista.agregar(agregarTupla);
        if(raiz.hijoDerecho!=null)
            valoresEnInorden(raiz.hijoDerecho, lista);
    }
    /**
     * Devuelve el recorrido preorder del árbol.
     *
     * @return el recorrido.
     */
    public Lista<Tupla<C, V>> preorder() {
        // Implementar.
        Lista <Tupla<C,V>> lista=new Lista<Tupla<C,V>>();
        recorridoPreorden(raiz, lista);
        return lista;
    }

    /**
     * Se encarga de de agregar los valores en tipo de recorrido Preorden a la lista
     */
    private void recorridoPreorden(Nodo<C,V> raiz, Lista<Tupla<C,V>> lista){
        if(raiz==null)
            return;
        Tupla <C,V> agregarTupla= new Tupla<>(raiz.clave, raiz.valor);
        lista.agregar(agregarTupla);
        if(raiz.hijoIzquierdo!=null)
            recorridoPreorden(raiz.hijoIzquierdo, lista);
        if(raiz.hijoDerecho!=null)
            recorridoPreorden(raiz.hijoDerecho, lista);
        
    }
    /**
     * Devuelve el recorrido postorder del árbol.
     *
     * @return el recorrido.
     */
    public Lista<Tupla<C, V>> postorder() {
        // Implementar.
        Lista <Tupla<C,V>> lista=new Lista<Tupla<C,V>>();
        recorridoPostorden(raiz, lista);
        return lista;
    }

    /**
     * Se encarga de de agregar los valores en tipo de recorrido Postorden a la lista
     */
    private void recorridoPostorden(Nodo<C,V> raiz, Lista<Tupla<C,V>> lista){
        if(raiz==null)
            return;
        
        if(raiz.hijoIzquierdo!=null)
            recorridoPostorden(raiz.hijoIzquierdo, lista);
        if(raiz.hijoDerecho!=null)
            recorridoPostorden(raiz.hijoDerecho, lista);
        Tupla <C,V> agregarTupla= new Tupla<>(raiz.clave, raiz.valor);
        lista.agregar(agregarTupla);
    }

    /**
     * Devuelve el recorrido en ancho del árbol.
     *
     * @return el recorrido.
     */
    public Lista<Tupla<C, V>> ancho() {
        // Implementar.            ;
        Lista <Tupla<C,V>> lista=new Lista<Tupla<C,V>>();
        
        if(raiz !=null){
            Tupla <C,V> agregarTupla;

            Cola <Nodo<C,V>> cola= new Cola();
            cola.agregar(raiz);
            Nodo <C,V> cursor;
            while(!cola.vacio()){
                cursor= cola.eliminar();
                agregarTupla= new Tupla<>(cursor.clave, cursor.valor);
                lista.agregar(agregarTupla);
                if(cursor.hijoIzquierdo!=null)
                    cola.agregar(cursor.hijoIzquierdo);
                if(cursor.hijoDerecho!=null)
                    cola.agregar(cursor.hijoDerecho);
            }
        }
        return lista;
    }

    /**
     * Obtiene el tamaño del diccionario.
     *
     * @return el tamaño del diccionario.
     */
    public int tamanio() {
        // Implementar.
        return cantidadDatos;
    }

    /**
     * Evalúa si el diccionario está vacío.
     *
     * @return true si el diccionario está vacío.
     */
    public boolean vacio() {
        // Implementar.
        return cantidadDatos==0;  //voy a asumir que si no hay raiz entonces el diccionario esta vacio
    }

}
