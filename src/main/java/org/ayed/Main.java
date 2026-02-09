package org.ayed;

import org.ayed.programaPrincipal.interfaz.Interfaz;

public class Main {
    public static void main(String[] args) {

        Interfaz.launch(Interfaz.class, args);

        System.out.println("Ventana abierta, listo para jugar.");

        // Ahora podemos jugar varias misiones dinámicamente
        //partida.jugarPartida(sc);
    }
}
