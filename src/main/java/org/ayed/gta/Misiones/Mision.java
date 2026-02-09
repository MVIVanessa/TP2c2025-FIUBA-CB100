package org.ayed.gta.Misiones;

import org.ayed.gta.Garaje.Garaje;
import org.ayed.gta.Mapa.Coordenadas;
import org.ayed.gta.Mapa.Gps;
import org.ayed.gta.Mapa.Mapa;
import org.ayed.gta.Mapa.TipoCelda;
import org.ayed.gta.Vehiculos.Exotico;
import org.ayed.gta.Vehiculos.Vehiculo;
import org.ayed.tda.vector.Vector;

public abstract class Mision{
	protected Vector<Vehiculo> permitidos;
	private Vehiculo transporte;		//vehiculo que usara el jugador para la mision
	private double tiempoJuego;			//en segundos
	private double tiempoMision;
	private Coordenadas jugador;
	private Mapa mapa;
	private Gps gps;
	protected int recompensaCreditosExtra;
	protected Vehiculo recompensaExotico;
	private Vehiculo vehiculoSeleccionado;
	
	/**
	 * Constructor de Mision
	 * @param tiempo tiempo de juego limite para jugar
	 * @param v Vehiculo con el que jugara el jugador
	 */
    Mision( double tiempo) {
		this.tiempoJuego= 0;
		this.transporte=null;
		this.tiempoMision= tiempo;
		this.mapa = new Mapa();
		jugador= mapa.posicionInicial();
		gps= new Gps(mapa);
		permitidos = new Vector<>();
		recompensaCreditosExtra=0;
		recompensaExotico=null;
	}


	
	/** Mueve al jugador segun el comando ingresado 
	* @param comando comando ingresado por el usuario para moverse en el mapa
	* @throws ExceptionMision cuando se elige un comando no posible por limitaciones del mapa 
	*/
	public void moverJugador(String comando){
		boolean movio = false;
		switch (comando.toUpperCase()) {
			case "A":	// move Izquierda
				if(jugador.obtenerY()-1 <0 ||
				mapa.datoDeCelda(jugador.obtenerX(), jugador.obtenerY()-1) == TipoCelda.EDIFICIO)
					throw new ExcepcionMision("No es posible ir a la izquierda");
				jugador.modificarY(jugador.obtenerY() - 1);
				movio = true;
				break;
			case "D":	// mover Derecha
				if(jugador.obtenerY()+1 > mapa.cantColumnas()-1 ||
				 mapa.datoDeCelda(jugador.obtenerX(), jugador.obtenerY()+1)== TipoCelda.EDIFICIO)
					throw new ExcepcionMision("No es posible ir a la derecha");
				jugador.modificarY(jugador.obtenerY() + 1);
            	movio = true;
				break;
			case "S":	//mover Abajo
				if(jugador.obtenerX()+1 >mapa.cantFilas()-1 ||
				 mapa.datoDeCelda(jugador.obtenerX()+1, jugador.obtenerY()) == TipoCelda.EDIFICIO)
					throw new ExcepcionMision("No es posible bajar");
				jugador.modificarX(jugador.obtenerX() + 1);
            	movio = true;
				break;
			
			case "W":	//mover Arriba
				if(jugador.obtenerX()-1 <0 || 
				mapa.datoDeCelda(jugador.obtenerX()-1, jugador.obtenerY()) == TipoCelda.EDIFICIO)
					throw new ExcepcionMision("No es posible subir");
				jugador.modificarX(jugador.obtenerX() - 1);
            	movio = true;
				break;
			case "C": //lena el tanque al maximo solo tres veces
				int l = transporte.capacidadGasolina() - transporte.tanque();
				transporte.llenarGasolina(l);
			default:
				System.out.println("Comando invalido");
			
		}
		if(movio && !fracaso()) {
			gps.modificarPartida(jugador); 
			incrementoTiempo();
			transporte.rebajarCantidadTanque();
			transporte.subirKilometraje();
			tomarRecompensaAdicional(jugador);
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

		if(mapa.datoDeCelda(jugador.obtenerX(),jugador.obtenerY()) == TipoCelda.SALIDA)
			completada=true;
		return completada;
	}
	/**
	 * Devulve true si la mision fracasa
	 * @return true si: tiempo de juego es mayor al tiempo que se le da al jugador
	 * 					el tanque de Gasolina queda vacio
	 */
	public boolean fracaso(){
		return tiempoJuego > tiempoMision || transporte.tanque()==0;
	}
	/**
	 * Recoge la recompensa del mapa, osea la eliminara del mapa
	 * @param c cordenadas donde esta el jugador
	 */
	private void tomarRecompensaAdicional(Coordenadas c){
		if( mapa.datoDeCelda(c.obtenerX(), c.obtenerY()) == TipoCelda.RECOMPENSA){	// R = recompensa
				// borrar el R del mapa
			mapa.recogerRecompensa(c);	
			return;
		}
		double probabilidad = Math.random();
		
		// 95% → créditos
		if (probabilidad < 0.95) {
			// recompensa de crédito entre 50 y 200
			int valor = 50 + (int)(Math.random() * 151);
			recompensaCreditosExtra += valor;
		}
		
		// 5% → vehículo exótico
		else {
			recompensaExotico = generarExotico();
		}
		
	}
	
	private Vehiculo generarExotico() {
	    String[] nombres = {"Chiron", "Enzo", "Jesko", "Zonda", "Valkyrie"};
	    String[] marcas = {"Bugatti", "Ferrari", "Koenigsegg", "Pagani", "Lamborghini"};

	    int i = (int)(Math.random() * nombres.length);

	    return new Exotico(nombres[i], marcas[i], 6, 30000, 80, 10000);
	}

	/** Incrementa el tiempo segun el costo de transito dividido la velocidad maxima del vehiculo
	 */
	private void incrementoTiempo(){
		tiempoJuego += (double)mapa.costoTransito(jugador)/(double)transporte.velocidadMaxima();
	}

	/**
	 * seleccion de vehiculos permitidos para la mision
	 * @param g Garaje del usuario
	 * @return cantidad e vehiculos permitidos dentro del garaje
	 */
	public abstract int vehiculosPermitidos(Garaje g);
	
	/** Eleccion de vehiculo a usar para la Mision
	 * @param i indice del Vehiculo
	 * @return	Vehiculo selecionado devuelve null si no se encuentra
	 */
	public void seleccionarVehiculo(int i){
		vehiculoSeleccionado = permitidos.dato(i - 1);
		transporte = vehiculoSeleccionado;
	}

	
	/**
	 * Reinicia las recompensas.
 	 */
	public void descartarRecompensas(){		// agrego este método por si la misión falla
	    recompensaCreditosExtra = 0;
	    recompensaExotico = null;
	}
	/** devuelve la reconpoensaCredito Extra del casillo rosa 
	* @retunr valor de la reconpensa recogida del mapa
	*/
	public int recompensaCreditosExtra() {
	    return recompensaCreditosExtra;
	}
	/**devuelve la reconpoensaCredito 
	* @retrun la recompensa de credito por completar mision 
	*/
	public abstract int recompensaCredito();

	/*Devuelve la reconpensa de un Cehiculo exotico */
	public Vehiculo recompensaExotico() {
	    return recompensaExotico;
	}

	/**
	 * @return tiempo del juego en progreso
 	 */
	public double devolverTiempo(){
		return tiempoJuego;
	}
	/**
	 * @return tiempo de la mision limite
 	 */
	public double devolverTiempoLim(){
		return tiempoMision;
	}

	/**
	 * Devolver transporte que usa mision
	 * @return transporte
	 */
	public Vehiculo transporte(){
		return transporte;
	}
	/**
	 * Devuelve el valor del dinero de recompensa
	 * @return la recompensa de dinero por completar mision 
	 */
	public abstract int recompensaDinero();

	public Mapa obtenerMapa(){
		return this.mapa;
	}
	/**devuelve el GPS segun la posicion actual del jugador
	* @return GPS segun coordenadas del jugador
	*/
	public Gps obtenerGps(){
		return this.gps;
	}

	/**devuelve las coordenadas del jugador
	* @return coordenadas de la posicion del jugador
	*/
	public Coordenadas obtenerPosicionJugador() {
		return jugador;
	}

	public String[] obtenerVehiculosPermitidos(){
		Vehiculo v;
		String[] autos = new String[permitidos.tamanio()];
		for(int i=0; i<permitidos.tamanio(); i++) {
			v= permitidos.dato(i);
			autos[i]= v.informacionVehiculo();
		}
		return autos;
	}

	public Vehiculo obtenerVehiculoSeleccionado() {
		return this.vehiculoSeleccionado;
	}
	
}