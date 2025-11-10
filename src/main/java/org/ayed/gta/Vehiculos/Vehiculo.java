package org.ayed.gta.Vehiculos;

public abstract class Vehiculo {
        // CONSTANTES QUE NO SE CAMBIAN
        private final int COSTO_MOTO= 30;
        private final int RUEDA_MOTO= 2;
        protected final int COSTO_GASOLINA=1;

        private String nombre;
        private int precio;
        private int  capacidadGas;
        private int ruedas;
        private String marca;
        private String tipo;
        private int kilometraje;
        private int velocidadMax;
        private int tanqueGasolina;
    /**
     * Constructor de Vehiculo.
     * @param nombre nombre de marca/modelo del vehiculo.
     * @param marca la marca de vehiculo
     * @param ruedas cantidad de ruedas del vehiculo
     * @param precio el valor del costo por litro que requiere como mantenimiento
     * @param capacidadGasolina capacidad de vehiculo que tiene para Gasolina
     * @param tipo entre MOTO o AUTO o EXOTICO
     * @param maximo velocidad maxima que puede alcanzar el vehiculo.
     */
    public Vehiculo(String nombre,String marca, int ruedas, int precio, int capacidadGasolina, String tipo, int maximo) {
        // Implementar.
        this.nombre = nombre;
        this.marca=marca;
        this.precio=precio;
        capacidadGas= capacidadGasolina;
        this.ruedas=ruedas;
        this.tipo=tipo;         
        kilometraje=0;          //kilometraje empieza desde cero
        tanqueGasolina=0;       //tomare que el tanque esta vacio al inicio
        velocidadMax=maximo;
    }

    /**
     * @return informacion del Vehiculo
     */
    public String informacionVehiculo(){
        String info= nombre +", " +precio+", "+ tipo +", "+ ruedas+ ", "+ capacidadGas;
        return info;
    }

    /**
     * @return costo de MAntenimiento del Vehiculo
     */
    abstract public int costoMantenimientoVehiculo();
    
    /**
     * Llenar en tanque de gasolina al maximo
     */
    public void llenarGasolinaMax(){
        tanqueGasolina=capacidadGas;
    }

    /**
     * LLeganar el tanque hasta cierta parte
     */
    public void llenarGasolina(int litros){
        tanqueGasolina=litros;
    }

    //devolucion de mis atributos, podran ver lso datos pero no modificarlos!
    
    /**
     * @return nombre de Vehiculo
     */
    public String nombreVehiculo(){
        return nombre;
    }
    /**
     * @return nombre de Vehiculo
     */
    public String marcaVehiculo(){
        return marca;
    }

    /**
     * @return precio del Vehiculo */
    public int precioVehiculo(){
        return precio;
    }

        /**
     * @return capacidad de gasolina del Vehiculo */
    public int capacidadGasolina(){
        return capacidadGas;
    }


}
