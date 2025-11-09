package org.ayed.gta;

public class Vehiculo {
        // CONSTANTES QUE NO SE CAMBIAN
        private final int COSTO_AUTO = 50; 
        private final int COSTO_MOTO= 30;
        private final int RUEDA_AUTO = 4; 
        private final int RUEDA_MOTO= 2;
        private final int COSTO_GASOLINA=1;

        private String nombre;
        private TipoVehiculo tipo;
        private int precio;
        private int  capacidadGas;
        private int ruedas;

    /**
     * Constructor de Vehiculo.
     *
     * @param nombre nombre de marca/modelo del vehiculo.
     * @param tipo entre MOTO o AUTO
     * @param precio el valor del costo por litro que requiere como mantenimiento
     * @param capacidadGasolina capacidad de vehiculo que tiene para Gasolina
     */
    public Vehiculo(String nombre, TipoVehiculo tipo, int precio, int capacidadGasolina) {
        // Implementar.
        this.nombre = nombre;
        this.tipo = tipo;
        this.precio=precio;
        capacidadGas= capacidadGasolina;
        ruedas=0;    //por defecto
        switch (tipo) {
            case MOTO:
                ruedas=RUEDA_MOTO;
                break;
            case AUTO:
                ruedas=RUEDA_AUTO;
                break;
            default:
                System.out.println("Error: tipo de vehiculo desconocido.");
        }

    }

    /**
     * @return informacion del Vehiculo
     */
    public String informacionVehiculo(){
        String info= nombre +", " +precio+", "+tipo +", "+ ruedas+ ", "+ capacidadGas;
        return info;
    }

    /**
     * @return costo de MAntenimiento del Vehiculo
     */
    public int costoMantenimientoVehiculo(){
        int costo= this.capacidadGas*COSTO_GASOLINA;
        
        //Ver que tipo de vehiculo para calcular con su respectivo costo
        switch (this.tipo) {
            //en caso de ser tipo AUTO
            case AUTO:
                costo += COSTO_AUTO * RUEDA_AUTO;
                break;
            // caso de tipo MOTO
            case MOTO:
                costo += COSTO_MOTO * RUEDA_MOTO;
                break;
                //Si llegara a pasar que no sea ninguno hay un error
            default:
                System.out.println("Error: tipo de vehiculo desconocido.");
                break;
        }
        return costo;
    }
    
    //devolucion de mis atributos, podran ver lso datos pero no modificarlos!
    
    /**
     * @return nombre de Vehiculo
     */
    public String nombreVehiculo(){
        return nombre;
    }

    /**
     * @return precio del Vehiculo */
    public int precioVehiculo(){
        return precio;
    }

}
