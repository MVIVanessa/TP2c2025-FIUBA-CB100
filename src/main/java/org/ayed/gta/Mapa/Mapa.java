package org.ayed.gta.Mapa;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

import org.ayed.gta.Misiones.TipoDificultad;
import org.ayed.tda.iterador.Iterador;
import org.ayed.tda.lista.Lista;

public class Mapa {
    private final int VALOR_CALLE = 50;
    private final int CANTIDAD_COLUMNAS;
    private final int CANTIDAD_FILAS;
    static final int PROBABILIDAD_CONGESTION = 15; // en porcentaje
    static final int PROBABILIDAD_RECOMPENSA = 5; // en porcentaje
    private final String RUTA_MAPAS = "mapas/";
    private final Lista<Lista<TipoCelda>> grillas;
    private Coordenadas entrada;
    private Coordenadas salida;
    /**
     * Constructor de Mapa.
     */
    public Mapa(TipoDificultad dificultad) {
        this.grillas = leerMapaDesdeCSV(dificultad);
        this.CANTIDAD_FILAS = this.grillas.tamanio();
        this.CANTIDAD_COLUMNAS = this.grillas.dato(0).tamanio();
        inicializarGrilla();
        establecerEntradaYSalida();
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
        Random rand = new Random();
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
        Random rand = new Random();
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
     * Establece entrada y salida una vez que ya estan en el mapa
     */
    private void establecerEntradaYSalida() {
        for (int i = 0; i < CANTIDAD_FILAS; i++) {
            Lista<TipoCelda> fila = grillas.dato(i);
            for (int j = 0; j < CANTIDAD_COLUMNAS; j++) {
                if (fila.dato(j) == TipoCelda.ENTRADA) {
                    this.entrada = new Coordenadas(i, j);
                }
                if (fila.dato(j) == TipoCelda.SALIDA) {
                    this.salida = new Coordenadas(i, j);
                }
            }
        }
    }

    // getter de entrada
    public Coordenadas posicionInicial() {
        return this.entrada;
    }

    // getter de salida
    public Coordenadas destino() {
        return this.salida;
    }
    
    /**
     * @return la grilla del mapa como una matriz de listas doblemente enlazadas de TipoCelda.
     */
    public Lista<Lista<TipoCelda>> obtenerMapa() {
        return this.grillas;
    }

    /**
     * @return altura del mapa
     */
    public int cantFilas() {
        return this.CANTIDAD_FILAS;
    }

    /**
     * @return el ancho del mapa
     */
    public int cantColumnas() {
        return this.CANTIDAD_COLUMNAS;
    }

    /**
     * @return tipo de celda de posicion pedida
     */
    public TipoCelda datoDeCelda(int fila, int columna) {
        if (fila < 0 || fila >= CANTIDAD_FILAS) return null;
        if (columna < 0 || columna >= CANTIDAD_COLUMNAS) return null;
        return this.grillas.dato(fila).dato(columna);
    }

    /**
     * Recoge la recompensa y modifica el tipo de la celda a TRANSITABLE
     * 
     * @param celda de tipo recompensa que recorre el jugador
     * 
     * @return true si pasa por una celda de tipo recompensa
     * @return false si la celda por la que pasa no es de tipo recompensa
     */
    public boolean recogerRecompensa(Coordenadas celda) {
        if (celda == null) return false;
        int cX = celda.obtenerX();
        int cY = celda.obtenerY();
        TipoCelda tipo = datoDeCelda(cX, cY);

        if (tipo == TipoCelda.RECOMPENSA) {
            this.grillas.dato(cX).modificarDato(TipoCelda.TRANSITABLE_RECOMPENSA, cY); // Al recoger recompensa, la celda vuelve a ser RANSITABLE
            return true;
        }
        return false;
    }

    /**
	 * Devuelve el costo de transito de la celda transitable
	 * @param c coordenadas donde esta el jugador
	 * @return
	 */
	public int costoTransito(Coordenadas c){
		int costo=VALOR_CALLE;
        TipoCelda tipo= datoDeCelda((c.obtenerX()), c.obtenerY());
		if(tipo == TipoCelda.CONGESTIONADA)
			costo*=5;
		return costo;
	}

    //------------------------------ Archivos ------------------------------------

    /**
     * Lee un archivo CSV y construye la representación del mapa.
     * @param rutaArchivo
     * @return
     */
    private Lista<Lista<TipoCelda>> leerMapaDesdeCSV(TipoDificultad dificultad) {
        Random rand = new Random();
        int numMapa = rand.nextInt(2) + 1;
        String rutaArchivo = RUTA_MAPAS + dificultad.toString().toLowerCase() + numMapa + ".csv";
        
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
    
    /**
     * Procesa una linea
     * @param linea la linea a procesar del archivo
     * @param tamanio el tamanio
     * @return Lista<TipoCelda> almacenada de la linea 
     */
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
