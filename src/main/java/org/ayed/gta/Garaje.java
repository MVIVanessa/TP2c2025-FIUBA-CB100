package org.ayed.gta;
import org.ayed.tda.vector.Vector;

public class Garaje {
    private final int CAPACIDAD_INICIAL = 5;
    private final int COSTO_MEJORA = 50;

    private int capacidad;      // difiere de la capacidad max de clase vector
    private Vector<Vehiculo> vehiculos;
    private int creditos;

    // Constructor de Garaje
    public Garaje(){
        // espacioOcupado=0;       // cuando creo un vector desde ya lo dejare vacio
        vehiculos = new Vector();
        capacidad = CAPACIDAD_INICIAL;
        creditos=0;
    }

    // este es un ampliaje de Garaje que no es lo mismo que para el vector.
    /*
    En el garaje va de 5, 6, 7, 8....
    y el vector si no se libera ira de 1, 2, 4, 8, 16, 32,...
    Seria mas como el desbloqueo de cuanta capacidad puede llegar a tener el Garaje
    y para no pedir
    */
    private void ampliarGaraje(){
        capacidad ++;   // de por si el vector aumenta solo, solo debo ampliar el limite "real"
    }

    /**
     * Agrega un Vehiculo al Garaje.
     *
     * @param vehiculo Vehiculo a agregar.
     //  * @throws ExcepcionGaraje si no excede la capacidad .
     */
    public void agregarVehiculo(Vehiculo vehiculo) {
        // Implementar.
        if(vehiculos.tamanio() >= capacidad)
            throw new ExcepcionGaraje("No hay mas espacio para agregar");      

        vehiculos.agregar(vehiculo);
        System.out.println("Agregado: "+ vehiculo.informacionVehiculo()); 
    
    }

        /**
     * Elimina un Vehiculo del Garaje.
     *
     * @param nombre Nombre de Vehiculo a eliminar.
     //  * @throws ExcepcionGaraje si el Garje esta vacio o  No se encuentra el vehiculo.
     */
    public void eliminarVehiculo(String nombre) {
        // Implementar.
        // debere encontrar primero donde esta mi vehiculo. Puedo borrar usando el indice o si es el ultimo con un simple eliminar.
        if(vehiculos.vacio())
            throw new ExcepcionGaraje("No hay ningun Vehiculo en garaje");

        int espacioOcupado = vehiculos.tamanio();
        Vehiculo vehiculo, elim;
        int i=0;
        boolean eliminado=false;
        while( !eliminado && i<espacioOcupado){
            vehiculo=vehiculos.dato(i);
            if (vehiculo!=null && vehiculo.nombreVehiculo().equals(nombre)){
                elim = vehiculos.eliminar(i);         
                System.out.println("Eliminado: "+ elim.informacionVehiculo());       
                eliminado=true;
            }
            i++;
        }
        if(!eliminado)
            throw new ExcepcionGaraje("Nombre de Vehiculo inexistente");

    }
    /** Acredita creditos incresado
    *   @param creditos Creditos a ingresar  
    ** throw ExcepcionGaraje si el credito 
    */
    public void agregarCreditos(int creditos) {
        // Implementar.
        if (creditos <= 0)
            throw new ExcepcionGaraje("Monto de credito invalido");
        this.creditos = creditos;
    }

    /** Mejora la capacidad de almacenamiento del Garaje  
     * Para la mejora se cobra 50 de credito.
    ** throw ExcepcionGaraje si el credito es insuficiente   
    */
    public void mejorarGaraje() {
        // Implementar.
        if( creditos-COSTO_MEJORA < 0)
            throw new ExcepcionGaraje("Credito insuficiente");
        // mejorar el vector de Vehiculos al duplicarla al doble
        ampliarGaraje();
        creditos-=COSTO_MEJORA;
    }

    /** Calcular el Valor total de la suma de todos los precios en Garaje de los vehiculos
     * @return valor final de suma de precios. 
    ** throw ExcepcionGaraje si algun precio es invaldo.   
    */
    public int obtenerValorTotal() {
        // Implementar.
        int valor=0;
        for(int i=0; i<vehiculos.tamanio(); i++){
            if (vehiculos.dato(i).precioVehiculo() < 0 ){
                throw new ExcepcionGaraje("Precio invalido, no puede ser menor a 0");
            }
            else valor+= vehiculos.dato(i).precioVehiculo();
        }
        return valor;
    }
    /** Calcular el Costo total de mantenimiento de todos los vehiculos en Garaje 
     * @return Costo por mantenimiento. 
    ** throw ExcepcionGaraje si algun precio es invaldo.   
    */
    public int obtenerCostoMantenimiento() {
        // Implementar.
        int costo=0;
        for(int i=0; i<vehiculos.tamanio(); i++){
            if (vehiculos.dato(i).precioVehiculo() <0){
                throw new ExcepcionGaraje("Costo invalido al ser menor a 0");
            }
            else costo+= vehiculos.dato(i).costoMantenimientoVehiculo();
        }
        return costo;
    }

    /**
     * Mostrar la informacion de todos los vehiculos en Garaje
     * throw ExcepcionGaraje si el Garaje esta vacio
     */
    public void mostrarVehiculosGaraje(){
        if(vehiculos.vacio())
            throw new ExcepcionGaraje("Garaje esta vacio");
        String info; 
        for(int i=0; i< vehiculos.tamanio() ; i++){
            info= vehiculos.dato(i).informacionVehiculo();
            System.out.println(info);
        }

    }

    //Para uso de archivo
    /** Devolver capacidad maxima de Garaje 
    * @return capacidad
    */
    public int capacidadMaxima(){
        return capacidad;
    }
    /** Devolver cantidad de credito que hay 
    * @return creditos
    */
    public int obtenerCreditos(){
        return creditos;
    }
    /** Devolver los Vehiculos 
    * @return vehiculos
    */
    public Vector<Vehiculo> obtenerVehiculo(){
        return vehiculos;
    }
    // Exclusivamente para el uso de archivoGaraje!
    /**
     * Constructor de Garaje
     * @param vehiculos Vehiculos dentro del Garaje con su propia informacion
     * @param capacidad Capacidad de almacen en el GAraje
     * @param creditos Cantidad de Creditos disponibles.
     */
    public Garaje (Vector<Vehiculo> vehiculos, int capacidad, int creditos ){
        this.vehiculos = vehiculos;
        this.capacidad = capacidad;
        this.creditos = creditos;
    }
    /** Copiar un Garaje
     * @param garaje Garaje a copiar eliminando lo que haya estado antes
    */
    public void copiarGaraje(Garaje garaje){
        this.capacidad = garaje.capacidadMaxima();
        this.creditos = garaje.obtenerCreditos();
        this.vehiculos= new Vector<>();
        for(int i=0; i < garaje.obtenerVehiculo().tamanio(); i++){
            this.vehiculos.agregar(garaje.obtenerVehiculo().dato(i));
        }
    }

}