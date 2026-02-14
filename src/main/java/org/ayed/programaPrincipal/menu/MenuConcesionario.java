package org.ayed.programaPrincipal.menu;

import org.ayed.gta.Concesionario.Concesionario;
import org.ayed.gta.Concesionario.TipoOperacion;
import org.ayed.gta.Partida;
import org.ayed.gta.Vehiculos.Vehiculo;
import org.ayed.programaPrincipal.aplicacion.Controlador;
import org.ayed.programaPrincipal.frontend.formulario.Campo;
import org.ayed.programaPrincipal.frontend.formulario.TipoCampo;
import org.ayed.tda.vector.Vector;

public class MenuConcesionario {
	private	final Partida partidaJugador;
	private final Controlador controlador;
	private final Concesionario concesionario;
	private Vector<Vehiculo> busqueda;
	private TipoOperacion estadoOperacion = TipoOperacion.ERROR_DESCONOCIDO;
	
	public MenuConcesionario(Partida p, Concesionario concesionario, Controlador controlador) {
		partidaJugador=p;
		this.concesionario = concesionario;
		concesionario.cargarCatalogoDefault();
		this.controlador = controlador;
	}


	/** Procesa la opcion elegida
	 * @param opcion Opcion que eligio el usuario
	 * @param controlador Controlador de entradas para interaccion con usuario
	 */
	public void procesarOpcion(int op, Controlador controlador){
		
		switch (op) {
			case 1: // Búsqueda por nombre
				busquedaPorNombre();
				break;
			case 2: //Busqueda por marca
				busquedaPorMarca();
				break;
			case 3:  //Mostrar stock completo
				busqueda = concesionario.obtenerStock();
				controlador.mostrarVehiculos(controlador::mostrarMenuConcesionario, busqueda);
				break;
			case 4: //Comprar un vehiculo (primero muestra el stock completo)
				if (concesionario.obtenerStock().tamanio() == 0) {
					controlador.mostrarMensaje("No hay vehículos disponibles para comprar.", controlador::mostrarMenuConcesionario);
				} else {
					Campo[] camposCompra = {
						new Campo("Ingrese el nombre exacto del vehículo a comprar:", TipoCampo.TEXTO)
					};
					busqueda = concesionario.obtenerStock();
					controlador.mostrarVehiculos(() -> controlador.mostrarFormularioCompraConcesionario(camposCompra), busqueda);
				}
				break;
			case 5: //Salir
				controlador.mostrarMenuPrincipal();
				break;
			default:
				System.err.println("Opción inválida en MenuConcesionario.");
		}
	}
	/**
	 * BUsca un vehiculo dentro del concesionario por una fraccion del nombre
	 */
	private  void busquedaPorNombre(){
		Campo[] camposNombre = {
					new Campo("Nombre del Vehiculo a buscar:", TipoCampo.TEXTO)
				};
		controlador.mostrarBusquedaPorNombre(camposNombre);
	}

	/**
	 * Compra de vehiculo por nombre exacto
	 * @param nombreVehiculo para comprar el vehiculos
	 */
	public void comprar(String nombreVehiculo) {	
		try {

			operacionExitosa = concesionario.comprar(nombreVehiculo, partidaJugador);;
		} catch (Exception e) {
			System.err.println("Error al comprar el vehículo: " + e.getMessage());
			estadoOperacion = TipoOperacion.ERROR_DESCONOCIDO;
		}
	}
	/**
	 * BUsca un vehiculo dentro del concesionario por una fraccion del nombre de la marca
	 */
	private void busquedaPorMarca () {
		Campo[] camposMarca = {
			new Campo("Marca del Vehiculo a buscar:", TipoCampo.TEXTO)
		};
		controlador.mostrarBusquedaPorMarca(camposMarca);
	}

	public TipoOperacion obtenerEstadoOperacion() {
		return estadoOperacion;
	}
}
