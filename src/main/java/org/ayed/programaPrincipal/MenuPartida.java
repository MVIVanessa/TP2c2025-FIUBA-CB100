package org.ayed.programaPrincipal;

import org.ayed.gta.Partida;
import org.ayed.programaPrincipal.interfaz.Controlador;

public class MenuPartida {
	private Partida partida;
	
	public MenuPartida(Partida partida) {
		this.partida = partida;
	}
	/**
	 * Procesa la opcion
	 * @param op opcion ingresada
	 * @param sc controlador de entradas
	 */
	public void procesarOpcion(int op, Controlador controlador){
		switch (op) {
			case 1:
				controlador.empezarMision();
				break;
			case 2:
				// ir a garaje. Desplega el menu de acciones de garaje
				controlador.mostrarMenuGaraje();
				break;
			case 3:
				// guarda la partida en un archivo
				controlador.mostrarMensaje("Partida guardada correctamente.", () -> controlador.mostrarMenuPrincipal());
				partida.guardarPartida();
				break;
			case 4: 
				controlador.mostrarMenuConcesionario();
				break;
			case 5:
				controlador.mostrarMensaje("Dinero en cuenta: $"+ partida.dinero(), 
				() -> controlador.mostrarMenuPrincipal());
				System.out.println("Dinero en cuenta: $"+ partida.dinero());
				break;
			case 6:
				controlador.mostrarMensaje("Créditos:\nDesarrollado por el grupo 6 de AYED - 2C 2024", 
				() -> controlador.mostrarMenuPrincipal());
				System.out.println("Termino la partida");
				controlador.cerrar();
				break;
			default:
				System.err.println("Error en procesar opcion");
		}

	}
}
