package org.ayed.programaPrincipal;

import org.ayed.gta.Concesionario.Concesionario;
import org.ayed.gta.Partida;
import org.ayed.gta.Vehiculos.Vehiculo;
import org.ayed.programaPrincipal.interfaz.Campo;
import org.ayed.programaPrincipal.interfaz.Controlador;
import org.ayed.programaPrincipal.interfaz.TipoCampo;
import org.ayed.tda.vector.Vector;

public class MenuConcesionario {
	private Partida partidaJugador;
	private Controlador controlador;
	private Concesionario concesionario;
	private Vector<Vehiculo> busqueda;
	private Boolean operacionExitosa = false;
	
	public MenuConcesionario(Partida p, Concesionario concesionario, Controlador controlador) {
		partidaJugador=p;
		this.concesionario = concesionario;
		this.controlador = controlador;
	}


	/** Procesa la opcion elegida
	 * @param opcion Opcion que eligio el usuario
	 * @param controlador Controlador de entradas para interaccion con usuario
	 */
	public void procesarOpcion(int op, Controlador controlador){
		
		switch (op) {
			case 1: // Búsqueda por nombre
				Campo[] camposNombre = {
					new Campo("Nombre del Vehiculo a buscar:", TipoCampo.TEXTO)
				};
				controlador.mostrarBusquedaPorNombre(camposNombre);
				break;
			case 2: //Busqueda por marca
				Campo[] camposMarca = {
					new Campo("Marca del Vehiculo a buscar:", TipoCampo.TEXTO)
				};
				controlador.mostrarBusquedaPorMarca(camposMarca);
				break;
			case 3:  //Mostrar stock completo
				busqueda = concesionario.obtenerStock();
				controlador.mostrarVehiculos(controlador::mostrarMenuConcesionario, busqueda);
				break;
			case 4: //Comprar un vehiculo (primero muestra el stock completo)
				if (concesionario.obtenerStock().tamanio() == 0) {
					controlador.mostrarMensaje("No hay vehículos disponibles para comprar.", controlador::mostrarMenuConcesionario);
					return;
				} else {
				controlador.mostrarMenuCompraConcesionario();
				}
				break;
			case 5: //Salir
				controlador.mostrarMenuPrincipal();
				break;
			default:
				System.err.println("Error surgio en procesar la opcion");
		}
	}
	/**
	 * Busca por el nombre ingresado 
	 * @param sc controlador de entradas
	 * @param c concesionario
	 * @return
	*/
	public void busquedaPorNombre(String nombre){
		busqueda = concesionario.buscarPorNombre(nombre);
		if (busqueda.tamanio() == 0) {
			operacionExitosa = false;
		} else {
			operacionExitosa = true;
			controlador.mostrarVehiculos(controlador::mostrarMenuConcesionario, busqueda);
		}
	}

	public void comprar(String nombreVehiculo) {	
		try {
			concesionario.comprar(nombreVehiculo, partidaJugador);
			operacionExitosa = true;
		} catch (Exception e) {
			operacionExitosa = false;
		}
	}

	/**
	 * Busca por l marca ingresado 
	 * @param sc controlador de entradas
	 * @param c concesionario
	 * @return
	*/
	private Vector<Vehiculo> busquedaPorMarca(String marca) {
		busqueda = concesionario.buscarPorMarca(marca);
		if (busqueda.tamanio() == 0) {
			operacionExitosa = false;
		} else {
			operacionExitosa = true;
			controlador.mostrarVehiculos(controlador::mostrarMenuConcesionario, busqueda);
		}
		return busqueda;
	}

	public Boolean operacionExitosa() {
		return operacionExitosa;
	}
}
