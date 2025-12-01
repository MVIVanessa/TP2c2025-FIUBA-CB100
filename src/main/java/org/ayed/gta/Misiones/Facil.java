package org.ayed.gta.Misiones;

import org.ayed.gta.Garaje.Garaje;
import org.ayed.gta.Vehiculos.Vehiculo;
import org.ayed.tda.vector.Vector;

public class Facil extends Mision {
	// private int recompensaDinero;
	// private int recompensaCreditos;
	protected Vector<Vehiculo> permitidos;


    public Facil() {
        super(20);
    }
	@Override 
	public int vehiculosPermitidos(Garaje g){
		permitidos = g.obtenerVehiculo();   //para probar
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
