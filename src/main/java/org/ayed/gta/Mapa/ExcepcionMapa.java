package org.ayed.gta.Mapa;

public class ExcepcionMapa extends RuntimeException {
    /**
     * Constructor de ExcepcionMapa.
     * Esta clase es para el uso exclusivo del archivo Mapa.
     *
     * @param mensaje Mensaje de error.
     * @see Mapa
     */
    public ExcepcionMapa(String mensaje) {
        super(mensaje);
    }
}
