package org.ayed;

import org.ayed.programaPrincipal.MenuPartida;

public class Main {
    public static void main(String[] args) {
        // // Lanzar JavaFX en un hilo separado
        // new Thread(() -> Interfaz.lanzar(args)).start();

        // Partida partida = new Partida();
        // ControladorEntradas sc = new ControladorEntradas();

        // // Esperar a que la interfaz esté lista
        // while (Interfaz.getInstancia() == null) {
        //     try { Thread.sleep(50); } catch (InterruptedException e) {}
        // }

        // System.out.println("Ventana abierta, listo para jugar.");

        // // Ahora podemos jugar varias misiones dinámicamente
        // partida.jugarPartida(sc);

        MenuPartida menu = new MenuPartida();
        menu.empezarPartida();
    }
}
