package org.ayed.programaPrincipal;

import org.ayed.gta.Garaje;
import org.ayed.gta.Misiones.Dificil;
import org.ayed.gta.Misiones.ExcepcionMision;
import org.ayed.gta.Misiones.Facil;
import org.ayed.gta.Misiones.Mision;
import org.ayed.gta.Misiones.Moderada;
import org.ayed.gta.Vehiculos.Auto;
import org.ayed.gta.Vehiculos.Vehiculo;
import org.ayed.tda.lista.Lista;

public class Partida {
	private final int NO_SEGUIR =2;
	private final int DINERO_INICIAL=200;
	private final Vehiculo VEHICULO_BASICO= new Auto("Auto", "clasico", 0, 100, 10);
	Lista<Mision> misiones;	
	String nombreJugador;
	int dinero;
	Garaje garaje;

	/**constructor de Partida
	 */
    public Partida() {
		misiones = new Lista<>();
		nombreJugador= null;
		dinero=DINERO_INICIAL;
		garaje = new Garaje();
		//asignar vehiculo inicial()
		garaje.agregarVehiculo(VEHICULO_BASICO);
    }

	public void empezarPartida(){
		ControladorEntradas entrada=new ControladorEntradas();
		//ingreso de nombre de usuario y creacion de archivo
		ingresarNombre(entrada);

		int opcion = menuJuego(entrada);
		procesarOpcion(opcion,entrada);
	}

	private int menuJuego(ControladorEntradas sc){
		System.err.println("1. Jugar una Mision\n2. Ir al Garaje\n3. Guardar partida en un archivo\n4. Salir");
		return sc.obtenerOpcion(4);
	}

	private void procesarOpcion(int op, ControladorEntradas sc){
		switch (op) {
			case 1:
				boolean salir=false;
				do{
					jugarMision(sc, garaje);
					salir = continuarJugando(sc,garaje);
				}while(!salir);
				
				break;
			case 2:
				// ir a garaje. Desplega el menu de acciones de garaje
				break;
			case 3:
				// guarda la partida en un archivo
				break;
			case 4: 
				System.out.println("Termino la partida");
			default:
				throw new AssertionError();
		}
	}

	private void ingresarNombre(ControladorEntradas sc){
		System.out.println("Ingrese nombre de jugador para la partida: ");
		nombreJugador = sc.leerEntrada(false);

	}

	private Mision elegirModo(int i){
		Mision mision;
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
				throw new ExcepcionMision("Eleccion de Modo de juego invalido");
		}
		return mision;

	}

	private void jugarMision(ControladorEntradas sc, Garaje garaje){
		//elegir modo de mision
		System.out.println("Eliga el modo de la Mision: (1.Dificil, 2.Moderada, 3.Facil): ");
		int eleccion = sc.obtenerOpcion(3);
		misiones.agregar(elegirModo(eleccion));
		Mision mision=misiones.dato(misiones.tamanio()-1);	
		//muestra comandos
		mostrarComandosJugador();
		//selecciona vehiculos del listado permitido segun el modo de mision
		int cantPerm = mision.vehiculosPermitidos(garaje);
		if (cantPerm==0)
			System.err.println("No es posible jugar");
		else{
			System.out.println("Ingrese numero de Vehiculo que quiere usar: ");
			int elegido= sc.obtenerOpcion(cantPerm);
			Vehiculo v= mision.seleccionarVehiculo(elegido);
			System.out.println("Vehiculo seleccionado: "+ v.nombreVehiculo());

			dinero-=garaje.obtenerCostoMantenimiento();
			// Jugador se desplaza en el mapa hasta terminar tiempo o llegar al destino
			while(!mision.misionCompletada() &&!mision.fracaso()){
				System.out.println("Tiempo: " + mision.devolverTiempo() +"segundos" );
				System.out.println("Tiempo Limite: " + mision.devolverTiempoLim() +"segundos" );
				
				mision.despliegueDeMapa();
				
				String movimiento= sc.leerEntrada(false);
				try {
					mision.moverJugador(movimiento);

				} catch (ExcepcionMision e) {
					System.out.println(e.getMessage());
				}

			}
			if (mision.misionCompletada()){
				dinero+= mision.recompensaDinero();
				System.out.println("¡¡Misión completada!!");
			}

			if(mision.fracaso())
				System.out.println("Fracaso de misión...");
		}
	}

	private void mostrarComandosJugador(){
		System.out.println("Para Moverse alrededor del Mapa se usa las letras WASD ");
		System.out.println("W= Arriba");
		System.out.println("S= Abajo");
		System.out.println("D= Derecha");
		System.out.println("A= Izquierda");
	}

	private boolean continuarJugando(ControladorEntradas sc,Garaje g){
		boolean salir= false;
		System.out.println("Desea seguir jugando?( 1.si 2.no): ");
		//comparo con el numero para seguir
		salir= NO_SEGUIR == sc.obtenerOpcion(2);
		// si quiere seguir verifico si PUEDE continuar y si fracaso antes descuento
		if(!salir && dinero - garaje.obtenerCostoMantenimiento() <= 0 ){
			salir=true;	
		}
		return salir;
	}

	
	
}
