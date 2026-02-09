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
import org.ayed.programaPrincipal.interfaz.Controlador;
import org.ayed.programaPrincipal.interfaz.TipoMenu;
import org.ayed.tda.lista.Lista;

public class Partida {
	private final int NO_SEGUIR =2;
	private final Vehiculo VEHICULO_BASICO= new Auto("Auto", "clasico", 0, 100, 10);
	private final int PARTIDAS_INICIALES =3;
	private Lista<Mision> misiones;	
	private String nombreJugador;
	private int dinero;
	private Garaje garaje;
	private boolean noFallo;
	private Mision misionActual;

	/**constructor de Partida
	 */
    public Partida(Garaje garaje) {
		misiones = new Lista<>();
		nombreJugador= null;
		this.garaje = garaje;
		//asignar vehiculo inicial()
		garaje.agregarVehiculo(VEHICULO_BASICO);
		dinero= garaje.obtenerCostoMantenimiento()*PARTIDAS_INICIALES;
		noFallo=true;
    }

	/**
	 * Ingresa el nombre del usuario, util para cuando haya que guardar el archi
	 * @param sc controlador de entrada
	 */
	public void ingresarNombre(){
		System.out.println("Ingrese nombre de jugador para la partida: ");
		//nombreJugador = sc.leerEntrada(false);

	}
	/**
	 * Jugar una partida de misiones
	 * @param controlador controlador de entradas
	 */
	public void jugarPartida(Controlador controlador){
		boolean salir = false;

		controlador.mostrarMenuDificultad();

		if (misionActual == null) return;

		while(!salir && noFallo && dinero-garaje.obtenerCostoMantenimiento()>=0){
			noFallo = jugarMision(controlador, garaje);
			salir = continuarJugando(controlador,garaje);
		}
	}

	/**
	 * Crea la mision segun su modo segu eleccion de jugador
	 * @param i opcion eleghida por jugador
	 * @return el objeto hijo de Mision
	 */
	public void elegirModo(int i){
		switch (i) {
			case 1:
				misionActual= new Facil();
				break;
			case 2:
				misionActual = new Moderada();
				break;
			case 3:
				misionActual = new Dificil();
				break;
			default:
				System.err.println("Eleccion de Modo de juego invalido");
		}

	}

	/** Implementa una mision, si completa sigue sino debe empezar nueva partida
	 * @param sc controlador de entradas
	 * @param garaje garaje de la partida
	*/
	private boolean jugarMision(Controlador controlador, Garaje garaje){
		int cantPerm = misionActual.vehiculosPermitidos(garaje);
		if (cantPerm == 0) {
			controlador.mostrarMensaje("No es posible jugar. No cuenta con vehiculos para la mision", TipoMenu.PRINCIPAL);
			return false;
		}

		Vehiculo vehiculoSeleccionado = misionActual.obtenerVehiculoSeleccionado();
	
		System.out.println("Empezando Mision");
	
		controlador.iniciarMision(misionActual);

		while (!misionActual.misionCompletada() && !misionActual.fracaso()) {
			try {
				Thread.sleep(300); // revisa cada 300 ms si la mision se completo o fracasó (2 veces por segundo)
			} catch (InterruptedException e) {}
		}
		
		return misionActual.misionCompletada();
	}
	

	/**
	 * Pregunta al jugador si desea seguir jugando
	 * SI sigue se descontara el costo por dia de mision
	 * @param sc controlador de entradas
	 * @param g garaje de la partida
	 * @return true si se desea contunuar, false sino, o si no puede seguir aunque quiera al no tener suficiente dinero
	 */
	private boolean continuarJugando(Controlador controlador,Garaje g){
		System.out.println("Desea seguir jugando?( 1.si 2.no): ");
		//comparo con el numero para seguir
		int op= 1; //temporalmente para evitar uso de sc
		return op==NO_SEGUIR;
	}
	/**
	 * Guarda la partida en un archivo con el nombre del jugador.
	 * El archivo tendrá:
	 * - Nombre del jugador
	 * - Dinero Disponible
	 * - Vehículos del garage
	 */
	public void guardarPartida(){
		
		if(nombreJugador == null) {
			System.out.println("No se puede guardar la partida: nombre del jugador no asignado.");
			return;
		}
		
		String nombreArchivo = nombreJugador + "_save.txt";
		
		try {
	        FileWriter fw = new FileWriter(nombreArchivo);
	        fw.write("Jugador:" + nombreJugador + "\n");
	        fw.write("Dinero:" + dinero + "\n");
	        fw.write("Vehiculos:\n");

	        // Recorrer los vehículos del garaje usando tus TDA
	        for (int i = 0; i < garaje.obtenerVehiculo().tamanio(); i++) {
	            Vehiculo v = garaje.obtenerVehiculo().dato(i);
	            fw.write(v.informacionVehiculo() + "\n");
	        }

	        fw.write("FIN\n");
	        fw.close();

	        System.out.println("Partida guardada correctamente en " + nombreArchivo);

	    } catch (IOException e) {
	        System.err.println("Error al guardar la partida: " + e.getMessage());
	    }

	}

	/**devuelde el nombre del jugador de la partida */
	public void restarDinero(int monto){
		if(monto>dinero)
			System.err.println("No hay suficiente dinero");
		dinero-=monto;
	}

	/** si quiso jugar pero no podia, empezar nueva partida
	* @param sc controlador de entradas
	*/ 
	public boolean partidaNueva(){
		System.out.println("Desea abrir una nueva partida?( 1.si 2.no):");
		//boolean salir= NO_SEGUIR == sc.obtenerOpcion(2);
		boolean salir= false; // temporalmente para evitar uso de sc
		return (!noFallo || dinero-garaje.obtenerCostoMantenimiento()<0) && !salir;
	}
	/**devuelde el nombre del jugador de la partida */
	public String nombre(){
		return nombreJugador;
	}

	/**devuelde el nombre del jugador de la partida */
	public int dinero(){
		return dinero;
	}
	/**
	 * Devuelve el garaje de la partida
	 */
	public Garaje garaje(){
		return garaje;
	}

	public Mision misionActual() {
		return misionActual;
	}
}
