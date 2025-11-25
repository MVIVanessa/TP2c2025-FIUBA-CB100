package org.ayed.gta.Misiones;

import org.ayed.gta.Garaje;

public class Facil extends Mision {
	// private int recompensaDinero;
	// private int recompensaCreditos;

    public Facil() {
        super(0);
    }
	@Override 
	public int vehiculosPermitidos(Garaje g){
		return 0;
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
