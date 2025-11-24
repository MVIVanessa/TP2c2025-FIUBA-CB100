package org.ayed.gta.Mapa;
import java.util.Random;
import org.ayed.gta.Vehiculos.*;
import org.ayed.tda.lista.Lista;
import org.ayed.tda.iterador.Iterador;

public class Mapa {
    static final int CANTIDAD_COLUMNAS = 12;
    static final int CANTIDAD_FILAS = 12;
    private Lista<Lista<TipoCelda>> grillas;
    //TipoMision tipoMision;

    /**
     * Constructor de Mapa.
     */
    public Mapa() {
        //this.tipoMision = tipoMision;
        this.grillas = new Lista<Lista<TipoCelda>>();
        for (int i = 0; i < CANTIDAD_FILAS; i++) {
            Lista<TipoCelda> fila = new Lista<TipoCelda>();
            for (int j = 0; j < CANTIDAD_COLUMNAS; j++) {
                fila.agregar(TipoCelda.TRANSITABLE);
            }
            this.grillas.agregar(fila);
        }
        
        establecerCeldaAleatoria(TipoCelda.ENTRADA, 5);
        establecerCeldaAleatoria(TipoCelda.SALIDA, 5);
        establecerEdificios();
        establecerCeldaRecompensa();
        establecerCongestion();
    }

    private void establecerEdificios() {
        
    }

    /**
     * Establece una celda de recompensa en el mapa con una probabilidad del 5%.
     * Utiliza un generador de números aleatorios para decidir la ubicación de la recompensa.
     * Hay una sola celda de recompensa por mapa.
     */
    private void establecerCeldaRecompensa() {
        Random rand = new Random(); //va a depender del tipo de misión
        boolean hayRecompensa = false;
        Iterador<Lista<TipoCelda>> recorridoFilas = this.grillas.iterador();

        while (!hayRecompensa && recorridoFilas.haySiguiente()) {
            Iterador<TipoCelda> recorridoColumnas = recorridoFilas.dato().iterador();

            while (!hayRecompensa && recorridoColumnas.haySiguiente()) {
                int probabilidad = rand.nextInt(100); // Genera un número entre 0 y 99
                if (probabilidad < 5 && recorridoColumnas.dato() == TipoCelda.TRANSITABLE) { // 5% de probabilidad
                    recorridoColumnas.modificarDato(TipoCelda.RECOMPENSA);
                    hayRecompensa = true;
                }
                recorridoColumnas.siguiente();
            }
            recorridoFilas.siguiente();
        }
    }

    /**
     * Establece celdas congestionadas en el mapa con una probabilidad del 20%.
     * Utiliza un generador de números aleatorios para decidir qué celdas se congestionan.
     */
    private void establecerCongestion() {
        Random rand = new Random(); //va a depender del tipo de misión
        Iterador<Lista<TipoCelda>> recorridoFilas = this.grillas.iterador();

        while (!recorridoFilas.haySiguiente()) {
            Iterador<TipoCelda> recorridoColumnas = recorridoFilas.dato().iterador();

            while (!recorridoColumnas.haySiguiente()) {
                int probabilidad = rand.nextInt(100); // Genera un número entre 0 y 99
                if (probabilidad < 20 && recorridoColumnas.dato() == TipoCelda.TRANSITABLE) { // 20% de probabilidad
                    recorridoColumnas.modificarDato(TipoCelda.CONGESTIONADA);   
                }
                recorridoColumnas.siguiente();
            }
            recorridoFilas.siguiente();
        }
        

    }

    /**
     * Establece una celda de un tipo específico en una posición aleatoria del mapa.
     * Unicamente establece en celdas vacías (TRANSITABLE).
     * @param tipo
     * @param probabilidadPorcentaje
     */
    private void establecerCeldaAleatoria(TipoCelda tipo, int probabilidadPorcentaje) {
        Random rand = new Random();
        boolean colocada = false;
    
        while (!colocada) {
            Iterador<Lista<TipoCelda>> recorridoFilas = this.grillas.iterador(0);
    
            while (recorridoFilas.haySiguiente() || !colocada) {
                Iterador<TipoCelda> recorridoColumnas = recorridoFilas.dato().iterador();
    
                while (recorridoColumnas.haySiguiente() || !colocada) {
                    int probabilidad = rand.nextInt(100);
                    if (probabilidad < probabilidadPorcentaje && recorridoColumnas.dato() == TipoCelda.TRANSITABLE) {
                        recorridoColumnas.modificarDato(tipo);
                        colocada = true;
                    }
                    recorridoColumnas.siguiente();
                }
                recorridoFilas.siguiente();
            }
        }
    }
    

    public Lista<Lista<TipoCelda>> obtenerMapa() {
        return this.grillas;
    }

}
