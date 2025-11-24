package org.ayed.gta.Misiones;

import org.ayed.gta.Garaje;

public class Dificil extends Mision{
	int TIEMPO;
    public Dificil() {
		super(0);
	}
	@Override 
	public int vehiculosPermitidos(Garaje g){
		return 0;
	}
	
}
