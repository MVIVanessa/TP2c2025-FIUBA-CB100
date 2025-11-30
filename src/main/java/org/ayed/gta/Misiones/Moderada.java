package org.ayed.gta.Misiones;

import org.ayed.gta.Garaje.Garaje;

public class Moderada extends Mision{

    public Moderada() {
        super(10);
    }
	@Override 
	public int vehiculosPermitidos(Garaje g){
		return 1;
	}
	@Override
	public int recompensaDinero() {
		// IMPLEMENTAR
		return 1;
	}
	@Override
	public int recompensaCredito() {
		//implementar
		return 1;
	}
	
}
