package org.ayed.gta.Mapa;
import java.io.IOException;
import java.util.Random;
import org.ayed.tda.lista.ExcepcionLista;
import org.ayed.tda.lista.Lista;
import org.ayed.tda.iterador.Iterador;
import org.ayed.gta.Mapa.LectorMapa;

public class Mapa {
    static final int CANTIDAD_COLUMNAS = 12;
    static final int CANTIDAD_FILAS = 12;
    private Lista<Lista<TipoCelda>> grillas;
    //TipoMision tipoMision;

    /**
     * Constructor de Mapa.
     */
    public Mapa(String rutaArchivoCSV) {
        LectorMapa lector = new LectorMapa();
        try {
            this.grillas = lector.leerMapaDesdeCSV(rutaArchivoCSV);
        } catch (IOException e) {
            throw new ExcepcionMapa("Error al cargar el mapa desde CSV: " + e.getMessage());
        }
        //this.tipoMision = tipoMision;
        // ESTABLECER CANT FILAS Y COLUMNAS A PARTIR DE GRILLAS ??
        inicializarGrilla();
    }

    /**
     * Inicializa la grilla del mapa estableciendo edificios,
     * celdas de entrada y salida, recompensas y congestiones.
     * @throws ExcepcionMapa si ocurre un error durante la inicialización.
     */
    private void inicializarGrilla() {
        try {
            establecerCeldaAleatoria(TipoCelda.ENTRADA);
            establecerCeldaAleatoria(TipoCelda.SALIDA);
            establecerCeldaRecompensa();
            establecerCongestion();
        } catch (Exception e) {
            throw new ExcepcionMapa("Error al inicializar la grilla del mapa: " + e.getMessage());
        }
        
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

        while (recorridoFilas.haySiguiente()) {
            Iterador<TipoCelda> recorridoColumnas = recorridoFilas.dato().iterador();

            while (recorridoColumnas.haySiguiente()) {
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
     * Establece una celda aleatoria del tipo especificado en el mapa.
     *
     * @param tipo Tipo de celda a establecer.
     */
    private void establecerCeldaAleatoria(TipoCelda tipo) {
        Random rand = new Random();
        boolean colocada = false;

        while (!colocada) {
            int filaIndex = rand.nextInt(CANTIDAD_FILAS);
            int columnaIndex = rand.nextInt(CANTIDAD_COLUMNAS);

            Lista<TipoCelda> fila = this.grillas.dato(filaIndex);
            TipoCelda celdaActual = fila.dato(columnaIndex);

            if (celdaActual == TipoCelda.TRANSITABLE) {
                fila.modificarDato(tipo, columnaIndex);
                colocada = true;
            }
        }
    }
    
    /**
     * @return la grilla del mapa
     */
    public Lista<Lista<TipoCelda>> obtenerMapa() {
        return this.grillas;
    }

}