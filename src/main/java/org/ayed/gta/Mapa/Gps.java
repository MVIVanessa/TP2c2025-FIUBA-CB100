package org.ayed.gta.Mapa;
import org.ayed.tda.grafo.Grafo;
import org.ayed.tda.grafo.Heuristica;
import org.ayed.tda.lista.Lista;
import org.ayed.gta.Mapa.Coordenadas;

public class Gps {
    private Lista<Coordenadas> camino;
    private Coordenadas inicio;
    private final Coordenadas fin;
    private final Mapa mapa;
    private Grafo<Coordenadas> grafo;
    private AEstrella<Coordenadas> aEstrella;

	/**
     * Contructor de Gps
     * 
     * @param partida coordenadas donde se encuentra el jugador
     * @param mapa mapa del juego como una grilla (lista de listas de TipoCelda)
	 */
    public Gps(Coordenadas partida, Mapa mapa) {
        this.mapa = mapa;
        this.inicio = partida;
        this.fin = mapa.destino(); // Creamos el destino desde el mapa
        this.grafo = new Grafo<>(); // Construimos un grafo usando el mapa
        construirGrafoDesdeMapa();
        Heuristica<Coordenadas> heuristica = new AEstrella.ManhattanHeuristicaCoordenadas(); // Creamos heuristica Manhattan para Coordenadas
        this.aEstrella = new AEstrella<>(grafo, heuristica);
        this.camino = aEstrella.buscarCamino(inicio, fin); // Primer calculo del camino
    }
	
	/**
     * Modifica la cordenada de partida del Gps y recalcula el camino si es necesario
     * 
	* @param c coordenadas nuevas para punto de partida
	*/
	public void modificarPartida(Coordenadas nuevaPosicion){
        if (nuevaPosicion == null) return;
        
        this.inicio = nuevaPosicion;
        actualizarCamino(nuevaPosicion);
	}

    /**
     * Actualiza el camino con el movimiento del jugador
     * 
     * @param nuevaPosicion coordenadas nuevas a las que se movio el jugador
     */
    private void actualizarCamino (Coordenadas nuevaPosicion) {
        if (camino == null || camino.tamanio() == 0){
            camino = aEstrella.buscarCamino(nuevaPosicion, fin); // Si no hay camino, recalculamos uno
            return;
        }
        Coordenadas sigPosicion = camino.dato(0);
        
        // Si el jugador avanzo correctamente por el camino sugerido por el Gps, elimino la grilla que ya avanzo
        if (sigPosicion.compararCoordenadas(nuevaPosicion)){
            camino.eliminar(0);
            return;
        }

        // Si el jugador no siguio el camino sugerido por el Gps, recalculamos el camino completo
        camino = aEstrella.buscarCamino(nuevaPosicion, fin);
    }

    /**
     * @return null sin posicion
     * @return las coordenadas del camino
     */
    public Lista<Coordenadas> obtenerCamino(Coordenadas desde) {
        if (desde == null) return null;
        return camino;
    }


    /**
     * Busca una coordenada dentro del camino actual
     * 
     * @param c coordenadas que se van a buscar dentro del recorrido Gps
     */
    public boolean buscarCoordenadas(Coordenadas c) {
        for (int i = 0; i < camino.tamanio(); i++) {
            Coordenadas actual = camino.dato(i);
            if (actual.compararCoordenadas(c)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Genera el grafo a partir de la grilla del mapa
     */
    private void construirGrafoDesdeMapa() {
        Lista<Lista<TipoCelda>> matriz = mapa.obtenerMapa();
        if (matriz == null || matriz.tamanio() == 0) return;

        int filas = matriz.tamanio();
        int columnas = matriz.dato(0).tamanio();

        for (int f = 0; f < filas; f++) {  // Primero agregamos todos los vertices
            for (int c = 0; c < columnas; c++) {
                if (matriz.dato(f).dato(c) != TipoCelda.EDIFICIO) {
                    grafo.agregarVertice(new Coordenadas(f, c));
                }
            }
        }

        for (int f = 0; f < filas; f++) { // Agregamos las aristas entre vecinos transitables
            for (int c = 0; c < columnas; c++) {
                if (matriz.dato(f).dato(c) != TipoCelda.EDIFICIO) {
                    Coordenadas actual = new Coordenadas(f, c);
                    conectarSiTransitable(actual, f-1, c);
                    conectarSiTransitable(actual, f+1, c);
                    conectarSiTransitable(actual, f, c-1);
                    conectarSiTransitable(actual, f, c+1);
                }
            }
        }
}


    /**
     * Conecta el nodo origen con otro adyacente si este es transitable
     * 
     * @param origen nodo que queremos conectar con otro
     * @param f indice de fila del otro posible nodo
     * @param c indice de columna del otro posible nodo
     * 
     * @return si los indices de fila y columna son mayores a los del mapa
     */
    private void conectarSiTransitable(Coordenadas origen, int f, int c) {
        Lista<Lista<TipoCelda>> matriz = mapa.obtenerMapa();

        if (f < 0 || c<0) return;
        if (f >= matriz.tamanio() || c >= matriz.dato(0).tamanio()) return;

        TipoCelda tipo = matriz.dato(f).dato(c);
        if (tipo != TipoCelda.EDIFICIO) {
            Coordenadas destino = new Coordenadas(f, c);
            grafo.agregarArista(origen, destino, 1);
        }
    }
}