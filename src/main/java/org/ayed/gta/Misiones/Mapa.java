package org.ayed.gta.Misiones;
import java.util.Vector;

import org.ayed.tda.lista.Lista;

public class Mapa {
	Vector<Lista<Coordenadas>> mapa;		// no segura si esto esta bien REVISAR
	int altura;
	int ancho;

    Mapa() {
    }
	
	/** devuelve el ancho del mapa
	@return ancho 
	 */
	public int ancho(){
		return ancho;
	}          
	/** devuelve el ancho del mapa
	 * @return altura
	 */
	public int alto(){
		return altura;

	}         
	/** Devuelve el contenido de una celda
	 */
	public String datoCelda(int x, int y){
		return null;
	}
	/** Devuelve la posición de salida (S)
	 */
	public Coordenadas posicionInicial(){
		return null;
	}

	/** Devuelve la posición de destino (D)
	 */
	public Coordenadas destino(){
		return null;
		
	}  

	/** Elimina una recompensa del mapa
	 * @param c cordenadas donde esta la recompensa
	 */
	public void recogerRecompensa(Coordenadas c){
		
	}      
	/**
	 * Carga el mapa mediante archivos
	 * @param nombreArchivo
	 */
	public void cargarDesdeArchivo(String nombreArchivo){
		
	}

}
