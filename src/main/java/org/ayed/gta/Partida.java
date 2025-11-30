package org.ayed.gta;

import org.ayed.gta.Garaje.Garaje;
import org.ayed.gta.Misiones.Dificil;
import org.ayed.gta.Misiones.ExcepcionMision;
import org.ayed.gta.Misiones.Facil;
import org.ayed.gta.Misiones.Mision;
import org.ayed.gta.Misiones.Moderada;
import org.ayed.gta.Vehiculos.Auto;
import org.ayed.gta.Vehiculos.Vehiculo;
import org.ayed.programaPrincipal.ControladorEntradas;
import org.ayed.tda.lista.Lista;

public class Partida {
	private final int NO_SEGUIR =2;
	private final Vehiculo VEHICULO_BASICO= new Auto("Auto", "clasico", 0, 100, 10);
	private Lista<Mision> misiones;	
	private String nombreJugador;
	private int dinero;
	private Garaje garaje;
	private boolean noFallo;

	/**constructor de Partida
	 */
    public Partida() {
		misiones = new Lista<>();
		nombreJugador= null;
		garaje = new Garaje();
		//asignar vehiculo inicial()
		garaje.agregarVehiculo(VEHICULO_BASICO);
		dinero= garaje.obtenerCostoMantenimiento();
		noFallo=true;
    }

	/**
	 * Ingresa el nombre del usuario, util para cuando haya que guardar el archi
	 * @param sc controlador de entrada
	 */
	public void ingresarNombre(ControladorEntradas sc){
		System.out.println("Ingrese nombre de jugador para la partida: ");
		nombreJugador = sc.leerEntrada(false);

	}
	/**
	 * Jugar una partida de misiones
	 * @param sc controlador de entradas
	 */
	public void jugarPartida(ControladorEntradas sc){
		boolean salir=false;
		while(!salir && noFallo && dinero-garaje.obtenerCostoMantenimiento()>=0){
			noFallo=jugarMision(sc, garaje);
			salir = continuarJugando(sc,garaje);
		}
			// verifica que complua las condiciones para seguir jugando misiones
    	if(noFallo){
			if(dinero < garaje.obtenerCostoMantenimiento())
        		System.out.println("No tienes dinero suficiente para iniciar otra misión.");
			if(salir)
				System.out.println("Finalizaste la partida.");
		}

	}
	/**
	 * Crea la mision segun su modo segu eleccion de jugador
	 * @param i opcion eleghida por jugador
	 * @return el objeto hijo de Mision
	 */
	private Mision elegirModo(int i){
		Mision mision=null;
		switch (i) {
			case 1:
				mision= new Dificil();
				break;
			case 2:
				mision= new Moderada();
				break;
			case 3:
				mision= new Facil();
				break;
			default:
				System.err.println("Eleccion de Modo de juego invalido");
		}
		return mision;

	}

	/** Implementa una mision, si completa sigue sino debe empezar nueva partida
	 * @param sc controlador de entradas
	 * @param garaje garaje de la partida
	*/
	private boolean jugarMision(ControladorEntradas sc, Garaje garaje){
		//elegir modo de mision
		boolean bien= true;
		System.out.println("Eliga el modo de la Mision (1.Dificil, 2.Moderada, 3.Facil): ");
		int eleccion = sc.obtenerOpcion(3);
		Mision mision=elegirModo(eleccion);
		//selecciona vehiculos del listado permitido segun el modo de mision
		int cantPerm = mision.vehiculosPermitidos(garaje);
		if (cantPerm==0){
			System.err.println("No es posible jugar. No cuenta con vehiculos para la mision");
		}else{
			mision.mostrarVehiculosPermitidos();
			System.out.println("Ingrese numero de Vehiculo que quiere usar: ");
			int elegido= sc.obtenerOpcion(cantPerm-1) ;
			Vehiculo v= mision.seleccionarVehiculo(elegido);
			System.out.println("Vehiculo seleccionado: "+ v.nombreVehiculo());

			System.out.println("Empezando Mision");
			//muestra comandos de la mision
			mision.mostrarComandosJugador();
			restarDinero(garaje.obtenerCostoMantenimiento());
			// Jugador se desplaza en el mapa hasta terminar tiempo o llegar al destino
			while(!mision.misionCompletada() &&!mision.fracaso()){
				System.out.println("Tiempo: " + mision.devolverTiempo() +"segundos" );
				System.out.println("Tiempo Limite: " + mision.devolverTiempoLim() +"segundos" );
				mision.glosario();
				mision.despliegueDeMapa();
				
				String movimiento= sc.leerEntrada(false);
				try {
					mision.moverJugador(movimiento, sc);

				} catch (ExcepcionMision e) {
					System.out.println(e.getMessage());
				}

			}
			if (mision.misionCompletada()){
				dinero+= mision.recompensaDinero();
				garaje.agregarCreditos(mision.recompensaCredito());
				System.out.println("¡¡Misión completada!!");
			}

			if(mision.fracaso()){
				System.out.println("Fracaso de misión...");
				bien=false;
			}
			misiones.agregar(mision);
		}
		return bien;
	}

	/**
	 * Pregunta al jugador si desea seguir jugando
	 * SI sigue se descontara el costo por dia de mision
	 * @param sc controlador de entradas
	 * @param g garaje de la partida
	 * @return true si se desea contunuar, false sino, o si no puede seguir aunque quiera al no tener suficiente dinero
	 */
	private boolean continuarJugando(ControladorEntradas sc,Garaje g){
		System.out.println("Desea seguir jugando?( 1.si 2.no): ");
		//comparo con el numero para seguir
		int op= sc.obtenerOpcion(2);
		return op==NO_SEGUIR;
	}
	/**
	 * Guarda la partida en un archivo con el nombre del juador
	 */
	public void guardarPartida(){

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
	public boolean partidaNueva(ControladorEntradas sc){
		System.out.println("Desea abrir una nueva partida?( 1.si 2.no):");
		boolean salir= NO_SEGUIR == sc.obtenerOpcion(2);
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
}
