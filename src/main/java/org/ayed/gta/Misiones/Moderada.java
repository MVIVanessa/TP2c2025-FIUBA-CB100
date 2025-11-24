package org.ayed.gta.Misiones;

import org.ayed.gta.Garaje;

public class Moderada extends Mision{

    public Moderada() {
        super(0);
    }
	@Override 
	public int vehiculosPermitidos(Garaje g){
		return 0;
	}
	
}
