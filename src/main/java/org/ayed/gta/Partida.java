package org.ayed.gta;

import java.io.FileWriter;
import java.io.IOException;

import org.ayed.gta.Garaje.Garaje;
import org.ayed.gta.Misiones.Dificil;
import org.ayed.gta.Misiones.Facil;
import org.ayed.gta.Misiones.Mision;
import org.ayed.gta.Misiones.Moderada;
import org.ayed.gta.Vehiculos.Auto;
import org.ayed.gta.Vehiculos.Vehiculo;
import org.ayed.programaPrincipal.aplicacion.Controlador;

public class Partida {

	private final Vehiculo VEHICULO_BASICO =
			new Auto("Auto", "clasico", 0, 100, 10);

	private int PARTIDAS_INICIALES = 10;

	private String nombreJugador;
	private int dinero;
	private Garaje garaje;

	private boolean noFallo;
	private boolean continuarJugando;
	private int diaActual;

	private Mision misionActual;
	private Controlador controlador;

	// ================= CONSTRUCTOR =================

	public Partida(Garaje garaje, Controlador controlador) {
		this.garaje = garaje;
		this.controlador = controlador;
		garaje.agregarVehiculo(VEHICULO_BASICO);
		this.dinero = garaje.obtenerCostoMantenimiento()*PARTIDAS_INICIALES;
		this.noFallo = true;
		this.continuarJugando = true;
		this.diaActual = 1;
	}

	// ================= FLUJO DE JUEGO =================
	/**
	 * Decide si puede jugar
	 * @return true si cumple las condiciones para seguir jugando
	 */
	public boolean puedeJugar() {
		return (dinero - garaje.obtenerCostoMantenimiento() >= 0)&& noFallo;
	}

	// ================= ELECCIÓN DE MISIÓN =================
	/**
	 * Elegir el modo de la Mision
	 * @param opcion numero de la eleccion
	 */
	public void elegirModo(int opcion) {
		switch (opcion) {
			case 1:
				misionActual = new Facil(garaje);
				break;
			case 2:
				misionActual = new Moderada(garaje);
				break;
			case 3:
				misionActual = new Dificil(garaje);
				break;
			default:
				throw new IllegalArgumentException("Modo de juego inválido");
		}
	}

	public Mision misionActual() {
		return misionActual;
	}

	// ================= RESULTADO DE MISIÓN =================

	public void finalizarMision(boolean completada) {
		if (completada) {
			dinero += misionActual.recompensaDinero();

			garaje.agregarCreditos(misionActual.recompensaCredito() +
			                       misionActual.recompensaCreditosExtra());
			if (misionActual.recompensaExotico() != null) {
				garaje.agregarVehiculo(misionActual.recompensaExotico());
			}
		} else {
			noFallo = false; // ← IMPORTANTE
		}

		diaActual++;
		restarDinero(garaje.obtenerCostoMantenimiento());
	}

	// ================= CONTINUAR JUGANDO =================

	/**
	 * DEcide si abrir una partida nueva
	 * @param opcion elegida por el usuario segun
	 */
	public void partidaNueva(int opcion) {
		switch (opcion) {
			case 1:
				noFallo = true;
				continuarJugando = true;
				break;
			case 2:
				continuarJugando = false;
				break;
			default:
				throw new IllegalArgumentException();
		}
	}

	// ================= GUARDADO =================

	public void guardarNombre(String nombre) {
		this.nombreJugador = nombre;
	}

	public void guardarPartida() {

		if (nombreJugador == null) {
			System.out.println("No se puede guardar: nombre no asignado");
			return;
		}

		String archivo = nombreJugador + "_save.txt";

		try (FileWriter fw = new FileWriter(archivo)) {

			fw.write("Jugador:" + nombreJugador + "\n");
			fw.write("Dinero:" + dinero + "\n");
			fw.write("Vehiculos:\n");

			for (int i = 0; i < garaje.obtenerVehiculo().tamanio(); i++) {
				Vehiculo v = garaje.obtenerVehiculo().dato(i);
				fw.write(v.informacionVehiculo() + "\n");
			}

			fw.write("FIN\n");
			System.out.println("Partida guardada en " + archivo);

		} catch (IOException e) {
			System.err.println("Error al guardar: " + e.getMessage());
		}
	}

	/**Resta un monto de dinero
	 * @param
	 */
	public void restarDinero(int monto){
		if(monto>dinero)
			System.err.println("No hay suficiente dinero");
		else
			dinero-=monto;

	}

	
	/**devuelde el nombre del jugador de la partida */
	public String nombre(){
		return nombreJugador;
	}

	public int dinero() {
		return dinero;
	}

	public Garaje garaje() {
		return garaje;
	}

	public boolean noFallo() {
		return noFallo;
	}

	public int diaActual() {
		return diaActual;
	}
}