package org.ayed.gta.Concesionario;

import org.ayed.programaPrincipal.ControladorEntradas;

public class MenuConcesionario {
	public MenuConcesionario(){

	}
	public void desplegarMenu(ControladorEntradas sc){
		System.out.println(" _____________ CONCESIONARIO ____________");
		System.out.println("| 0. Buscar Vehiculos por Nombre.        |");
		System.out.println("| 1. Buscar Vehiculos por Marca.         |");
		System.out.println("| 2. Mostrar todo el stock de Vehiculos. |");
		System.err.println("|________________________________________|");
		int op=sc.obtenerOpcion(2);
		procesarOpcion(op);
	}

	private void procesarOpcion(int op){
		switch (op) {
			case 0:
				busquedaPorNombre();
				break;
			case 1:
				busquedaPorMarca();
				break;
			case 2:
				mostrarStock();
				break;
			case 3:
				comprarVehiculo();
				break;
			default:
				throw new AssertionError();
		}
	}

	private void busquedaPorNombre(){

	}
	private void busquedaPorMarca(){

	}
	private void mostrarStock(){

	}
	private void comprarVehiculo(){

	}
}
