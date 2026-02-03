package org.ayed.gta.Vehiculos;

public abstract class Vehiculo {
        // CONSTANTES QUE NO SE CAMBIAN
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
        tanqueGasolina=capacidadGasolina/4;       //tomare que el tanque esta vacio al inicio
        velocidadMax=maximo;
    }

    /**
     * @return informacion del Vehiculo
     */
    public String informacionVehiculo(){
        String info= nombre +", "+ marca+", " +precio+", "+ tipo +", "+ ruedas+ ", "+ capacidadGas + ", "+ velocidadMax;
        return info;
    }

    /**
     * @return costo de MAntenimiento del Vehiculo
     */
    abstract public int costoMantenimientoVehiculo();

    /**
     * Cargar en tanque una cantidad especifica de gasolina
     */
    public void llenarGasolina(int litros){
        if(capacidadGas<litros + tanqueGasolina)
            throw new ExcepcionVehiculo("Litros mayor a la capacidad de Gasolina del " + tipo);
        tanqueGasolina += litros;
    }

    /**
     * Rebaja de apoco la cantidad de Gasolina en el tanque
     */
    public void rebajarCantidadTanque(){
        tanqueGasolina--;
    }
    /**
     * Aumenta de apoco la cantidad de Gasolina en el tanque
     */
    public void subirKilometraje(){
        kilometraje++;
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
    /**
     * @return gazolina disponible del Vehiculo */
    public int tanque(){
        return tanqueGasolina;
    }
    /**
     * @return velocidad maxima del Vehiculo */
    public int velocidadMaxima(){
        return velocidadMax;
    }
    /**
     * @return tipo de Vehiculo */
    public String tipoVehiculo() {
        return tipo;
    }

}