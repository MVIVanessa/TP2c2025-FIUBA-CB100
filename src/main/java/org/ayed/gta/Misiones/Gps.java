package org.ayed.gta.Misiones;
import org.ayed.tda.lista.Lista;

public class Gps {
	Lista<Coordenadas> camino;
	Coordenadas inicio;
	Coordenadas fin;
	Mapa mapa;

	/**Contructor
	 */
    Gps(Coordenadas partida, Mapa mapa) {
    }
	
	/**modificador de la cordenada de partida del GPS 
	* @param c coordenadas nuevas para punto de partida
	*/
	public void modificarPartida(Coordenadas c){

	}
	/**
	 * 
	 * @param c coordenadas que se van a buscar en dentro del recorrido Gps
	 */
	public boolean buscandocordenadas(Coordenadas c){
		return true;
	}
}
