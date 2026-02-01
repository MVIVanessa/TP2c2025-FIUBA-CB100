package org.ayed;

import org.ayed.programaPrincipal.*;

import org.ayed.gta.Mapa.Coordenadas;
import org.ayed.gta.Misiones.Mision;
import org.ayed.gta.Misiones.Facil;
import org.ayed.gta.Mapa.Interfaz;

import javafx.application.Application;

import org.ayed.gta.Vehiculos.Vehiculo;
import org.ayed.gta.Vehiculos.Auto; 
import org.ayed.tda.vector.Vector;

import org.ayed.gta.Partida;

public class Main {
    public static void main(String[] args) {
        // Lanzar JavaFX en un hilo separado
        new Thread(() -> Interfaz.lanzar(args)).start();

        Partida partida = new Partida();
        ControladorEntradas sc = new ControladorEntradas();

        // Esperar a que la interfaz esté lista
        while (Interfaz.getInstancia() == null) {
            try { Thread.sleep(50); } catch (InterruptedException e) {}
        }

        System.out.println("Ventana abierta, listo para jugar.");

        // Ahora podemos jugar varias misiones dinámicamente
        partida.jugarPartida(sc);
    }
}
