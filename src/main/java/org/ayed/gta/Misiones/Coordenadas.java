package org.ayed.gta.Misiones;

public class Coordenadas {
	int x;
	int y;

    Coordenadas(int x, int y) {
		this.x=x;
		this.y=y;
    }
	/**
	 * Devuelve el x
	 * @return x
	 */
    public int obtenerX() {
        return x;
    }
	/**
	 * Devuelve el y
	 * @return y
	 */
    public int obtenerY() {
        return y;
    }

	/**
	 * Modific a x
	 * @param nuevoX calor que remplazara al x
	*/
    public void modificarX(int nuevoX) {
        this.x = nuevoX;
    }
	/**
	 * Modific a y
	 * @param nuevoY calor que remplazara al Y
	*/
    public void modificarY(int nuevoY) {
        this.y = nuevoY;
    }
	/**
	 * Comparador de Coordenadas
	 * @param x a comparar contra x
	 * @param y a comparar contra y
	 * @return
	 */
	public boolean compararCoordenadas(int x, int y){
		return this.x==x && this.y==y;
	}
	
}
