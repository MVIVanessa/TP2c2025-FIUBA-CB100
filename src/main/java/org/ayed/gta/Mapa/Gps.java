package org.ayed.gta.Mapa;
import org.ayed.tda.lista.Lista;
import org.ayed.tda.grafo.Grafo;
import org.ayed.tda.grafo.Heuristica;

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
    Gps(Coordenadas partida, Mapa mapa) {
        this.mapa = mapa;
        this.inicio = partida;
        this.fin = buscarSalida(mapa); //Creamos el destino desde el mapa
        this.grafo = new Grafo<>(); //Construimos un grafo usando el mapa
        construirGrafoDesdeMapa();
        Heuristica<Coordenadas> heuristica = new AEstrella.ManhattanHeuristicaCoordenadas(); //Creamos heuristica Manhattan para Coordenadas
        this.aEstrella = new AEstrella<>(grafo, heuristica);
        recalcularCamino(); //Calculamos el primer camino
    }
	
	/**
     * Modifica la cordenada de partida del Gps y recalcula el camino
     * 
	* @param c coordenadas nuevas para punto de partida
	*/
	public void modificarPartida(Coordenadas c){
        if (c == null) return;
        this.inicio = c;
        recalcularCamino();
	}

    /**
     * Busca una coordenada dentro del camino actual
     * 
     * @param c coordenadas que se van a buscar dentro del recorrido Gps
     */
    public boolean buscarcoordenadas(Coordenadas c) {
        for (int i = 0; i < camino.tamanio(); i++) {
            Coordenadas actual = camino.dato(i);
            if (actual.obtenerX() == c.obtenerX() && actual.obtenerY() == c.obtenerY()) {
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

        for (int f = 0; f < filas; f++) { //Creamos nodos y adyacencias
            for (int c = 0; c < columnas; c++) {
                TipoCelda tipo = matriz.dato(f).dato(c);
                if (tipo != TipoCelda.EDIFICIO) { //Si es transitable
                    Coordenadas actual = new Coordenadas(f, c);
                    grafo.agregarVertice(actual); //Creamos nodo

                    //Intentamos conectar con nodos vecinos (arriba, abajo, izquierda, derecha)
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

    /**
     * Encuentra la posición de la celda SALIDA en la grilla
     * 
     * @param mapa mapa del juego como una grilla
     * 
     * @return coordenadas de la salida
     */
    private Coordenadas buscarSalida(Mapa mapa) {
        Lista<Lista<TipoCelda>> m = mapa.obtenerMapa();
        if (m == null) return null;

        for (int i = 0; i < m.tamanio(); i++) {
            for (int j = 0; j < m.dato(i).tamanio(); j++) {
                if (m.dato(i).dato(j) == TipoCelda.SALIDA) {
                    return new Coordenadas(i, j);
                }
            }
        }
        return null;
    }

    /**
     * Recalcula el camino usando A*
     */
    private void recalcularCamino() {
        this.camino = aEstrella.buscarCamino(inicio, fin);
    }
}