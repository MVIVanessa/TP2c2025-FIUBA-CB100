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
