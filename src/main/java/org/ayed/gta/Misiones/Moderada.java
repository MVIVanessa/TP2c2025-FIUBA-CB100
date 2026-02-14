package org.ayed.gta.Misiones;

import org.ayed.gta.Garaje.Garaje;
import org.ayed.gta.Vehiculos.Vehiculo;
import org.ayed.tda.vector.Vector;

public class Moderada extends Mision {

    private final int RECOMPENSA_DINERO = 2500;
    private final int RECOMPENSA_CREDITOS = 20;
    private static final double TIEMPO_MAX = 500;

    public Moderada(Garaje g) {
        super(TIEMPO_MAX,g);
    }

    @Override
    public int vehiculosPermitidos(Garaje g) {

        this.permitidos = new Vector<>();

        Vector<Vehiculo> lista = g.obtenerVehiculo();

        for (int i = 0; i < lista.tamanio(); i++) {
            // todos los vehículos están permitidos
            this.permitidos.agregar(lista.dato(i));
        }

        if (permitidos.tamanio() == 0)
            return 0;

        return permitidos.tamanio();
    }

    @Override
    public int recompensaDinero() {
        return RECOMPENSA_DINERO;
    }

    @Override
    public int recompensaCredito() {
        return RECOMPENSA_CREDITOS;
    }
}
