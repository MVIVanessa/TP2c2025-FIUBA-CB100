package org.ayed.gta.Misiones;
import org.ayed.gta.Garaje;
import org.ayed.gta.Vehiculos.Vehiculo;

public abstract class Mision{
	private Vehiculo transporte;		//vehiculo que usara el jugador para la mision
	private double tiempoJuego;			//en segundos
	private double tiempoMision;
	private Coordenadas jugador;
	private Mapa mapa;
	private Gps gps;
	/**
	 * Constructor de Mision
	 * @param tipo Tipo De Mision Elegido pór el usuario: 
	 * 		1=facil		2=Moderada		3=Dificil
	 * @param v Vehiculo con el que jugara el jugador
	 */
    Mision( Vehiculo v, double tiempo) {
		this.tiempoJuego= 0;
		this.transporte=null;
		this.tiempoMision= tiempo;
		this.mapa = new Mapa();
		jugador= mapa.posicionInicial();
		gps= new Gps(jugador, mapa);
	}

	/**Muestra el mapa de la jugada con la respectiva posicion del jugador
	 *@throws ExcepcionMision si el mapa es nulo
	*/
	public void despliegueDeMapa(){
		if(mapa==null)
			throw new ExcepcionMision("Juego No puede continuar, no hay mapa");
		for (int x = 0; x < mapa.ancho(); x++) {
			for (int y = 0; y < mapa.alto(); y++) {
				if (x == jugador.obtenerX() && y == jugador.obtenerY()) {
					System.out.print('J');
				}else if(gps.buscandocordenadas(new Coordenadas(x, y)))
					System.out.print('*');
				else {
					tomarRecompensa(jugador);
					System.out.print(mapa.datoCelda(x,y));
				}
				System.out.print(" "); 
			}
			System.out.println();

		}

	}
	
	/** Mueve al jugador segun el comando ingresado 
	* @param comando comando ingresado por el usuario para moverse en el mapa
	* @throws ExceptionMision cuando se elige un comando no posible por limitaciones del mapa 
	*/
	public void moverJugador(String comando){
		boolean movio = false;
		switch (comando) {
			case "W":	//mover Arriba
				if(jugador.obtenerY()-1 <0 || mapa.datoCelda(jugador.obtenerX(), jugador.obtenerY()-1).equals("E"))
					throw new ExcepcionMision("No es posible subir");
				jugador.modificarY(jugador.obtenerY() - 1);
				movio = true;
				break;
			case "S":	//mover Abajo
				if(jugador.obtenerY()+1 <mapa.alto()-1 || mapa.datoCelda(jugador.obtenerX(), jugador.obtenerY()+1).equals("E"))
					throw new ExcepcionMision("No es posible bajar");
				jugador.modificarY(jugador.obtenerY() + 1);
            	movio = true;
				break;
			case "D":	// mover Derecha
				if(jugador.obtenerX()+1 <mapa.ancho()-1 || mapa.datoCelda(jugador.obtenerX()+1, jugador.obtenerY()).equals("E"))
					throw new ExcepcionMision("No es posible ir a la derecha");
				jugador.modificarX(jugador.obtenerX() + 1);
            	movio = true;
				break;
			
			case "A":	// move Izquierda
				if(jugador.obtenerX()-1 <0 || mapa.datoCelda(jugador.obtenerX()-1, jugador.obtenerY()).equals("E"))
					throw new ExcepcionMision("No es posible ir a la izquierda");
				jugador.modificarX(jugador.obtenerX() - 1);
            	movio = true;
				break;
			default:
				System.out.println("Comando invalido");
			
		}
		if(movio) {
			gps.modificarPartida(jugador); 
			incrementoTiempo();
    	}
	}

	/**
	 * Retorna true o false si la mision esta complatada
	 * Se fracasa si:
	 * - tiempo de jugador termina y no se llego al destino
	 * - La gasolina se acaba
	 * @return true si la mision se completo: el Jugador llega al destino sin fracasar la partida
	 */
	public boolean misionCompletada(){
		boolean completada=false;

		if(mapa.datoCelda(jugador.obtenerX(),jugador.obtenerY()).equals(mapa.destino()) && !fracaso())
			completada=true;
		return completada;
	}
	/**
	 * Devulve true si la mision fracasa
	 * @return true si: tiempo de juego es mayor al tiempo que se le da al jugador
	 * 					el tanque de Gasolina queda vacio
	 */
	private boolean fracaso(){
		return tiempoJuego > tiempoMision || transporte.tanque()==0;
	}
	/**
	 * Recoge la recompensa del mapa, osea la eliminara del mapa
	 * @param c cordenadas donde esta el jugador
	 */
	private void tomarRecompensa(Coordenadas c){
		if( mapa.datoCelda(c.obtenerX(), c.obtenerY()).equals("R"))			// R = reconpensa
		// ver si lo hago o va a mapa;
			mapa.recogerRecompensa(c);
	}

	/** Incrementa el tiempo segun el costo de transito dividido la velocidad maxima del vehiculo
	 */
	private void incrementoTiempo(){
		tiempoJuego+= mapa.costoTransito(jugador)/transporte.velocidadMaxima();
	}

	/**
	 * seleccion de vehiculos permitidos para la mision
	 * @param g
	 * @return Vehiculo que selecciona el usuario
	 */
	abstract void vehiculosPermitidos(Garaje g);
	
	/** Eleccion de vehiculo a usar para la Mision
	 * @param i indice del Vehiculo
	 * @return	Vehiculo selecionado devuelve null si no se encuentra
	 */
	abstract Vehiculo seleccionarVehiculo(int i);


	/**
	 * @return tiempo del juego en progreso
 	 */
	public double devolverTiempo(){
		return tiempoJuego;
	}
}