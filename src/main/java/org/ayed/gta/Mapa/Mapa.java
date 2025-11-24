package org.ayed.gta.Mapa;
import java.util.Random;
import org.ayed.tda.lista.Lista;
import org.ayed.tda.iterador.Iterador;

public class Mapa {
    static final int CANTIDAD_COLUMNAS = 12;
    static final int CANTIDAD_FILAS = 12;
    static final int[][] POSICIONES_EDIFICIOS = {
        {1,1}, {1, 3}, {1, 5}, {1, 7}, {1, 9}, {1, 10},
        {3,1}, {3, 3}, {3, 5}, {3, 7}, {3, 9}, {3,10},
        {4,1}, {4, 3}, {4, 9}, {4, 10},
        {5,1}, {5, 3}, {5, 5}, {5, 7}, {5, 9}, {5,10},
        {6,1},
        {7,1}, {7, 3}, {7, 5}, {7, 7}, {7, 9}, {7,10},
        {10,1}, {10, 3}, {10, 5}, {10, 7}, {10, 9}, {10,10}
    };
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
        
        inicializarGrilla();
    }

    /**
     * Inicializa la grilla del mapa estableciendo edificios,
     * celdas de entrada y salida, recompensas y congestiones.
     * @throws ExcepcionMapa si ocurre un error durante la inicialización.
     */
    private void inicializarGrilla() {
        try {
            establecerEdificios();
            establecerCeldaAleatoria(TipoCelda.ENTRADA);
            establecerCeldaAleatoria(TipoCelda.SALIDA);
            establecerCeldaRecompensa();
            establecerCongestion();
        } catch (Exception e) {
            throw new ExcepcionMapa("Error al inicializar la grilla del mapa: " + e.getMessage());
        }
        
    }

    /**
     * Establece las posiciones de los edificios en el mapa.
     * Las posiciones son fijas y predefinidas.
     */
    private void establecerEdificios() {
        for (int[] posicion : POSICIONES_EDIFICIOS) {
            int filaIndex = posicion[0];
            int columnaIndex = posicion[1];
            Lista<TipoCelda> fila = this.grillas.dato(filaIndex);
            fila.modificarDato(TipoCelda.EDIFICIO, columnaIndex);
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
