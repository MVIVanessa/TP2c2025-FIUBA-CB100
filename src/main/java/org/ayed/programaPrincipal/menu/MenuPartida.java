package org.ayed.programaPrincipal.menu;

import org.ayed.gta.Partida;
import org.ayed.programaPrincipal.aplicacion.Controlador;

public class MenuPartida {
	private Partida partida;
	
	public MenuPartida(Partida partida) {
		this.partida = partida;
	}

	public void procesarOpcion(int op, Controlador controlador){
		switch (op) {
			case 1: // Jugar misión.
				controlador.empezarMision();
				break;
			case 2: // Ir a garaje.
				controlador.mostrarMenuGaraje();
				break;
			case 3:
				//Guarda la partida en un archivo.
				controlador.mostrarMensaje("Partida guardada correctamente.", () -> controlador.mostrarMenuPrincipal());
				partida.guardarPartida();
				break;
			case 4: // Ir al concesionario.
				controlador.mostrarMenuConcesionario();
				break;
			case 5: // Mostrar dinero en cuenta.
				controlador.mostrarMensaje("Dinero en cuenta: $"+ partida.dinero(), 
				() -> controlador.mostrarMenuPrincipal());
				System.out.println("Dinero en cuenta: $"+ partida.dinero());
				break;
			case 6: // Salir del juego.
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
