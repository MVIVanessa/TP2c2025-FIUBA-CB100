package org.ayed.gta.Concesionario;

import org.ayed.gta.Partida;
import org.ayed.gta.Vehiculos.Vehiculo;
import org.ayed.programaPrincipal.ControladorEntradas;
import org.ayed.tda.vector.Vector;

public class MenuConcesionario {
	Partida partidaJugador;
	
	public MenuConcesionario(Partida p){
		partidaJugador=p;
	}
	public void desplegarMenu(ControladorEntradas sc){
		System.out.println(" _____________ CONCESIONARIO ____________");
		System.out.println("| 0. Buscar Vehiculos por Nombre.        |");
		System.out.println("| 1. Buscar Vehiculos por Marca.         |");
		System.out.println("| 2. Mostrar todo el stock de Vehiculos. |");
		System.out.println("| 3. volver a Menu Principal             |");
		System.err.println("|________________________________________|");
		int op=sc.obtenerOpcion(3);
		procesarOpcion(op, sc);
	}

	/** Procesa la opcion elegida
	 * @param opcion Opcion que eligio el usuario
	 * @param controlador Controlador de entradas para interaccion con usuario
	 */
	private void procesarOpcion(int op, ControladorEntradas sc){
		Concesionario conc= new Concesionario();
		Vector<Vehiculo> respuesta=null;
		switch (op) {
			case 0:
				respuesta= busquedaPorNombre(sc, conc);
				break;
			case 1:
				respuesta= busquedaPorMarca(sc, conc);
				break;
			case 2:
				respuesta= conc.obtenerStock();
				break;
			case 3:
				System.out.println("--------SALIENDO DE CONCESIONARIO--------");
			default:
				System.err.println("Error surgio en procesar la opcion");
		}
		if(op<3){
			mostrarInfo(respuesta);
			comprar(sc, conc);
		}
	}
	/**
	 * Busca por el nombre ingresado 
	 * @param sc controlador de entradas
	 * @param c concesionario
	 * @return
	 */
	private Vector<Vehiculo> busquedaPorNombre(ControladorEntradas sc, Concesionario c){
		System.out.println("Ingrese nombre de Vehiculo para buscar");
		String nombre= sc.leerEntrada(false);
		return c.buscarPorNombre(nombre);
	}
	/**
	 * Busca por l marca ingresado 
	 * @param sc controlador de entradas
	 * @param c concesionario
	 * @return
	 */
	private Vector<Vehiculo> busquedaPorMarca(ControladorEntradas sc, Concesionario c){
		System.out.println("Ingrese marca de Vehiculo para buscar");
		String marca= sc.leerEntrada(false);
		return c.buscarPorNombre(marca);

	}

	/** Mostrar informacion de Vehiculos del vector pasado
	 *  @param vehiculos Vector de Vehculos que contiene vehiculos a mostrar su informacion 
	 */
	private void mostrarInfo(Vector<Vehiculo> vehiculos){
		try{
			System.out.println("INFORMACION DE VEHICULOS DE GARAJE: ");
			System.out.println("INDICE | NOMBRE | MARCA | PRECIO | TIPO | CANT RUEDAS | CAPACIDAD GASOLINA | VELOCIDAD ");
			for (int i = 0; i < vehiculos.tamanio(); i++) {
				System.out.println(vehiculos.dato(i).informacionVehiculo());
			};
		} catch (Exception e) {
			System.err.println(e);
		}
	}

	/**
	 * Compra el vehiculo segun el indice pedido al usuario
	 * @param sc
	 * @param respuesta
	 * @param c
	 */
	private void comprar(ControladorEntradas sc, Concesionario c){
		System.out.println("Ingrese el nombre exacto de vehiculo que quiere comprar");
		String nombreExacto= sc.leerEntrada(false);
		try{
			boolean comprado=c.comprar(nombreExacto, partidaJugador);
			if(comprado)
				System.out.println("Compra Exitosa");
			else {
				System.out.println("No pudo realizarse la compra ");
			}
		}catch(Exception e){
			System.err.println(e.getMessage());
		}

	}
}
