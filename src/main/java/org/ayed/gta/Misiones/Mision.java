package org.ayed.gta.Misiones;
<<<<<<< HEAD
import org.ayed.gta.Garaje;
import org.ayed.gta.Vehiculos.Exotico;
=======
import org.ayed.gta.Concesionario.MenuConcesionario;
import org.ayed.gta.Garaje.Garaje;
import org.ayed.gta.Mapa.Coordenadas;
import org.ayed.gta.Mapa.Gps;
import org.ayed.gta.Mapa.Mapa;
import org.ayed.gta.Mapa.TipoCelda;
>>>>>>> 3d34d1216f2abf8d99510b3708a7844fede2e467
import org.ayed.gta.Vehiculos.Vehiculo;
import org.ayed.programaPrincipal.ControladorEntradas;
import org.ayed.tda.vector.Vector;

public abstract class Mision{
	protected Vector<Vehiculo> permitidos;
	private Vehiculo transporte;		//vehiculo que usara el jugador para la mision
	private double tiempoJuego;			//en segundos
	private double tiempoMision;
	private Coordenadas jugador;
	private Mapa mapa;
	private Gps gps;
	protected int recompensaCreditos;
	protected Vehiculo recompensaExotico;
	
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
		permitidos=null;
		recompensaCreditos=0;
		recompensaExotico=null;
	}

	/**Muestra el mapa de la jugada con la respectiva posicion del jugador
	 *@throws ExcepcionMision si el mapa es nulo
	*/
	public void despliegueDeMapa(){
		try {
			if(mapa==null)
				throw new ExcepcionMision("Juego No puede continuar, no hay mapa");
			System.out.println("############# Mapa #############");
			for (int x = 0; x < mapa.alto(); x++) {
				System.out.print("#");
				for (int y = 0; y < mapa.ancho(); y++) {
					if (x == jugador.obtenerX() && y == jugador.obtenerY()) {
						System.out.print('J');
					}else if(mapa.datoDeCelda(x,y) == TipoCelda.RECOMPENSA)
						System.out.print("R");
					else if(mapa.destino().compararCoordenadas(new Coordenadas(x, y))) // si es true son iguales y es celda salida
						System.out.print("D"); 
					else if ( mapa.datoDeCelda(x, y) == TipoCelda.CONGESTIONADA)
						System.out.print("^");
					else if(gps.buscarCoordenadas(new Coordenadas(x, y)))	// si es true significa que estamos en el camino de gps
						System.out.print('*');
					else if (mapa.datoDeCelda(x,y) == TipoCelda.EDIFICIO){
						System.out.print("E"); 
					}else {
						System.out.print(" "); 
					}
					System.out.print(" "); 
				}
				System.out.println("#");

			}	
			System.out.println("################################");
		} catch (ExcepcionMision e) {
			System.err.println("Error al desplegar Mapa");
		}

	}
	
	/** Mueve al jugador segun el comando ingresado 
	* @param comando comando ingresado por el usuario para moverse en el mapa
	* @throws ExceptionMision cuando se elige un comando no posible por limitaciones del mapa 
	*/
	public void moverJugador(String comando, ControladorEntradas sc){
		boolean movio = false;
		switch (comando) {
			case "W":	//mover Arriba
				if(jugador.obtenerY()-1 <0 || mapa.datoDeCelda(jugador.obtenerX(), jugador.obtenerY()-1).equals("E"))
					throw new ExcepcionMision("No es posible subir");
				jugador.modificarY(jugador.obtenerY() - 1);
				movio = true;
				break;
			case "S":	//mover Abajo
				if(jugador.obtenerY()+1 <mapa.alto()-1 || mapa.datoDeCelda(jugador.obtenerX(), jugador.obtenerY()+1).equals("E"))
					throw new ExcepcionMision("No es posible bajar");
				jugador.modificarY(jugador.obtenerY() + 1);
            	movio = true;
				break;
			case "D":	// mover Derecha
				if(jugador.obtenerX()+1 <mapa.ancho()-1 || mapa.datoDeCelda(jugador.obtenerX()+1, jugador.obtenerY()).equals("E"))
					throw new ExcepcionMision("No es posible ir a la derecha");
				jugador.modificarX(jugador.obtenerX() + 1);
            	movio = true;
				break;
			
			case "A":	// move Izquierda
				if(jugador.obtenerX()-1 <0 || mapa.datoDeCelda(jugador.obtenerX()-1, jugador.obtenerY()).equals("E"))
					throw new ExcepcionMision("No es posible ir a la izquierda");
				jugador.modificarX(jugador.obtenerX() - 1);
            	movio = true;
				break;
			case "C":
				int l = transporte.capacidadGasolina() - transporte.tanque();
				transporte.llenarGasolina(l);
			default:
				System.out.println("Comando invalido");
			
		}
		if(movio) {
			gps.modificarPartida(jugador); 
			incrementoTiempo();
			transporte.rebajarCantidadTanque();
			transporte.subirKilometraje();
			tomarRecompensaAdicional(jugador);
			desplegarConcesionario(jugador, sc);
    	}
	}
	/*Muestra los comando del juego */
	public void mostrarComandosJugador(){
		System.out.println("Para Moverse alrededor del Mapa se usa las letras WASD ");
		System.out.println("W= Arriba");
		System.out.println("S= Abajo");
		System.out.println("D= Derecha");
		System.out.println("A= Izquierda");
		System.out.println("Para llenar tanque del Vehiculo,ingresar 'C'");
	}

	public void glosario(){
		System.out.println("E= Edificio");
		System.out.println("^= Calle congestionada");
		System.out.println("*= GPS hacia la salida");
		System.out.println("ESPACIO= Calle transitable");
		System.out.println("J= Ubicacion de Juagdor");
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
<<<<<<< HEAD
		if(!mapa.datoCelda(c.obtenerX(), c.obtenerY()).equals("R"))	// R = recompensa
			return;
		
		double probabilidad = Math.random();
		
		// 95% → créditos
		if (probabilidad < 0.95) {
			// recompensa de crédito entre 50 y 200
			int valor = 50 + (int)(Math.random() * 151);
			recompensaCreditos += valor;
		}
		
		// 5% → vehículo exótico
		else {
			recompensaExotico = generarExotico();
		}
		
		// borrar el R del mapa
		mapa.recogerRecompensa(c);
		
	}
	
	private Vehiculo generarExotico() {
	    String[] nombres = {"Chiron", "Enzo", "Jesko", "Zonda", "Valkyrie"};
	    String[] marcas = {"Bugatti", "Ferrari", "Koenigsegg", "Pagani", "Lamborghini"};

	    int i = (int)(Math.random() * nombres.length);

	    return new Exotico(nombres[i], marcas[i], 6, 30000, 80, 10000);
=======
		if( mapa.datoDeCelda(c.obtenerX(), c.obtenerY()) == TipoCelda.RECOMPENSA)			// R = reconpensa
		// ver si lo hago o va a mapa;
			mapa.recogerRecompensa(c);
>>>>>>> 3d34d1216f2abf8d99510b3708a7844fede2e467
	}

	/** Incrementa el tiempo segun el costo de transito dividido la velocidad maxima del vehiculo
	 */
	private void incrementoTiempo(){
		tiempoJuego+= mapa.costoTransito(jugador)/transporte.velocidadMaxima();
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
	public Vehiculo seleccionarVehiculo(int i){
		if(i<0 || i > permitidos.tamanio())
			throw new ExcepcionMision("indice de vehiculo a seleccionar invalido");
		return transporte= permitidos.dato(i);
	}

	/** Muestra el listado de Vehculos permitidos por el Modo de mision
	 */
	public void mostrarVehiculosPermitidos() {
    	System.out.println("Vehículos disponibles para esta misión:");
    	Vehiculo v;
		for (int i=0; i< permitidos.tamanio(); i++ ) {
			v= permitidos.dato(i);
        	System.out.println(i + ") " + v.informacionVehiculo());
        	i++;
		}
    }
	
	/**
	 * Reinicia las recompensas.
 	 */
	public void descartarRecompensas(){		// agrego este método por si la misión falla
	    recompensaCreditos = 0;
	    recompensaExotico = null;
	}
	
	public int recompensaCredito() {
	    return recompensaCreditos;
	}

<<<<<<< HEAD
	public Vehiculo recompensaExotico() {
	    return recompensaExotico;
	}
=======
	public void desplegarConcesionario(Coordenadas c, ControladorEntradas sc){
		if(mapa.datoDeCelda(c.obtenerX(), c.obtenerY()) == TipoCelda.CONCESIONARIO){
			MenuConcesionario menuC= new MenuConcesionario();
			menuC.desplegarMenu(sc);
		}
	}

>>>>>>> 3d34d1216f2abf8d99510b3708a7844fede2e467

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
	 * @return
	 */
	public abstract int recompensaDinero();
	
	/**recoge la recompensa de credito
	* @return la recompensa de credito por completar mision 
	*/
}