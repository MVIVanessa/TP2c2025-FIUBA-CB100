package org.ayed.gta.Mapa;

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

	public boolean compararCoordenadas(Coordenadas otra) {
		if (otra == null) return false;
		return (this.x == otra.x && this.y == otra.y);
	}

	
	/**
	 * Compara dos objetos
	 * 
	 * @return true si son iguales
	 * @return true si son distintos o si uno no es Coordenadas
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;

		if (!(o instanceof Coordenadas)) return false;
		Coordenadas otro = (Coordenadas) o;
		return (this.x == otro.x && this.y == otro.y);
	}

	@Override
	public int hashCode() {
		return 31 * x + y;
	}

	@Override
	public String toString() {
		return "(" + x + ", " + y + ")";
	}

}