package org.ayed.gta.Misiones;
public class ExcepcionMision extends RuntimeException {
    /**
     * Constructor de ExcepcionArchivoGaraje.
     * Esta clase es para el uso exclusivo de ArchivoGaraje.
     *
     * @param mensaje Mensaje de error.
     * @see ArchivoGaraje
     */
    public ExcepcionMision(String mensaje) {
        super(mensaje);
    }
}
