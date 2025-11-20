package org.ayed.gta.Vehiculos;
public class ExcepcionVehiculo extends RuntimeException {
    /**
     * Constructor de ExcepcionArchivoGaraje.
     * Esta clase es para el uso exclusivo de ArchivoGaraje.
     *
     * @param mensaje Mensaje de error.
     * @see ArchivoGaraje
     */
    public ExcepcionVehiculo(String mensaje) {
        super(mensaje);
    }
}