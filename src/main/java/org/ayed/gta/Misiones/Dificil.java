package org.ayed.gta.Misiones;

import org.ayed.gta.Garaje.Garaje;
import org.ayed.gta.Vehiculos.Vehiculo;
import org.ayed.tda.vector.Vector;

public class Dificil extends Mision {

    private final int RECOMPENSA_DINERO = 7500;
    private final int RECOMPENSA_CREDITOS = 50;
    private static final double TIEMPO_MAX = 250;

    private String tipoRequerido;

    private static final String[] TIPOS = {"AUTO", "MOTO", "EXOTICO"};

    public Dificil(Garaje g) {
        super(TIEMPO_MAX,g);
        generarTipoRequerido();
    }

    private void generarTipoRequerido() {
        int idx = (int)(Math.random() * TIPOS.length);
        tipoRequerido = TIPOS[idx];
    }

    @Override
    public int vehiculosPermitidos(Garaje g) {

        this.permitidos = new Vector<>();

        Vector<Vehiculo> lista = g.obtenerVehiculo();

        // Filtrar vehículos por el tipo requerido
        for (int i = 0; i < lista.tamanio(); i++) {
            Vehiculo v = lista.dato(i);
            if (v.tipoVehiculo().equalsIgnoreCase(tipoRequerido)) {
                permitidos.agregar(v);
            }
        }

        if (permitidos.tamanio() == 0) {
            System.out.println("No hay vehículos del tipo requerido: " + tipoRequerido);
            return 0;
        }

        System.out.println("Misión difícil - Tipo requerido: " + tipoRequerido);
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
