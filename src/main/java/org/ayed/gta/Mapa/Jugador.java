package org.ayed.gta.Mapa;
import org.ayed.gta.Vehiculos.Vehiculo;
import org.ayed.tda.lista.Lista;
import org.ayed.gta.Mapa.TipoCelda;
import org.ayed.tda.iterador.Iterador;

public class Jugador {
    int posicion;
    int contador;
    Vehiculo vehiculo;
    Iterador recorridoFilas;
    Iterador recorridoColumnas;
    Mapa mapa;

    public Jugador(Vehiculo vehiculo, Mapa mapa) {
        this.mapa = mapa;
        this.vehiculo = vehiculo;
        this.recorridoFilas = mapa.obtenerMapa().iterador();
        this.recorridoColumnas = mapa.obtenerMapa().dato(0).iterador();
        this.posicion = 0;
        this.contador = 0;
    }
}
