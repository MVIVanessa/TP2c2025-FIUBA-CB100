package org.ayed.gta.Mapa;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;
import org.ayed.tda.lista.Lista;
import org.ayed.tda.iterador.Iterador;

public class Mapa {
    private int CANTIDAD_COLUMNAS;
    private int CANTIDAD_FILAS;
    static final int PROBABILIDAD_CONGESTION = 15; // en porcentaje
    static final int PROBABILIDAD_RECOMPENSA = 5; // en porcentaje
    static final String rutaMapaBase = "pruebaMapa.csv";
    private Lista<Lista<TipoCelda>> grillas;
    //TipoMision tipoMision;

    /**
     * Constructor de Mapa.
     */
    public Mapa() {
        //this.tipoMision = tipoMision;
        this.grillas = leerMapaDesdeCSV(rutaMapaBase);
        this.CANTIDAD_FILAS = this.grillas.tamanio();
        this.CANTIDAD_COLUMNAS = this.grillas.dato(0).tamanio();
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
                if (probabilidad < PROBABILIDAD_RECOMPENSA && recorridoColumnas.dato() == TipoCelda.TRANSITABLE) { // 5% de probabilidad
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
                if (probabilidad < PROBABILIDAD_CONGESTION && recorridoColumnas.dato() == TipoCelda.TRANSITABLE) {
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
     * @return la grilla del mapa como una matriz de listas doblemente enlazadas de TipoCelda.
     */
    public Lista<Lista<TipoCelda>> obtenerMapa() {
        return this.grillas;
    }

    //------------------------------ Archivos ------------------------------------

    /**
     * Lee un archivo CSV y construye la representación del mapa.
     * @param rutaArchivo
     * @return
     */
    private Lista<Lista<TipoCelda>> leerMapaDesdeCSV(String rutaArchivo){
        
        Lista<Lista<TipoCelda>> mapa = new Lista<>();
        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
    
            String linea = br.readLine();
            if (linea == null) throw new ExcepcionMapa("Archivo del mapa vacío.");

            int tamanioPrimeraFila = linea.split(",").length;
            mapa.agregar( procesarLinea(linea, tamanioPrimeraFila) );

            while ((linea = br.readLine()) != null) {
                mapa.agregar( procesarLinea(linea, tamanioPrimeraFila) );
            }
    
            return mapa;  
    
        } catch (IOException e) {
            throw new ExcepcionMapa("Error al leer el archivo CSV del mapa: " + e.getMessage());
        }
    }
    
    private Lista<TipoCelda> procesarLinea(String linea, int tamanio) {
    
        String[] celdas = linea.split(",");
        if (celdas.length != tamanio)
            throw new ExcepcionMapa("Inconsistencia en el tamaño de filas.");
    
        Lista<TipoCelda> fila = new Lista<>();
    
        for (String celdaActual : celdas) {
            String celdaStr = celdaActual.trim().toUpperCase();
            TipoCelda celdaNueva;

            if (celdaStr.equals("C")) celdaNueva = TipoCelda.TRANSITABLE; 
            else if(celdaStr.equals("E")) celdaNueva = TipoCelda.EDIFICIO; 
            else{ throw new ExcepcionMapa("Tipo de celda inválido: " + celdaStr); }
            fila.agregar(celdaNueva);
        }
    
        return fila;
    }
    

}
