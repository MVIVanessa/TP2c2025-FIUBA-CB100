package org.ayed.gta.Misiones;
import org.ayed.tda.vector.Vector;

public class Mapa {

	private final int VALOR_CALLE = 1;
	Vector<Vector<String>> mapa;		// no segura si esto esta bien REVISAR
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
		Vector<String> lista= mapa.dato(x);
		return lista.dato(y);
	}
	/** Devuelve la posición de salida (S)
	 */
	public Coordenadas posicionInicial(){
		return null;
	}

	/** Devuelve la posición de destino (D)
	 */
	public String destino(){
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
	/**
	 * Devuelve el costo de transito de la celda transitable
	 * @param c coordenadas donde esta el jugador
	 * @return
	 */
	public int costoTransito(Coordenadas c){
		int costo=VALOR_CALLE;
		if(datoCelda(c.obtenerX(), c.obtenerY()).equals("C"))
			costo*=5;
		return costo;
	}
	

}
