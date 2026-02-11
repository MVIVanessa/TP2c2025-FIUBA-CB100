package org.ayed.programaPrincipal.frontend.formulario;

public class Campo {

    public final String nombre;
    public final TipoCampo tipo;

    /**
     * Crea un campo para el formulario de entrada.
     * @param nombre
     * @param tipo Tipo de dato (ENTERO, DECIMAL, TEXTO).
     */
    public Campo(String nombre, TipoCampo tipo) {
        this.nombre = nombre;
        this.tipo = tipo;
    }
}