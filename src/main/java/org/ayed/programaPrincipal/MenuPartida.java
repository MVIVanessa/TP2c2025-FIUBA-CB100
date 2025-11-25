package org.ayed.programaPrincipal;

public class MenuPartida {
	Partida partida;
	/**Comienza una Partida */
	public MenuPartida(){
		partida= new Partida();
	}
	public void empezarPartida(){
		ControladorEntradas entrada=new ControladorEntradas();
		//ingreso de nombre de usuario y creacion de archivo
		partida.ingresarNombre(entrada);

		int opcion = menuJuego(entrada);
		procesarOpcion(opcion,entrada);
	}
	/**
	 * Muestra el menu al juagador
	 * @param sc Controlador de entradas
	 * @return opcion ingresada por el usuario
	 */
	private int menuJuego(ControladorEntradas sc){
		System.err.println("1. Jugar una Mision\n2. Ir al Garaje\n3. Guardar partida en un archivo\n4. Salir (No guarda cambios)");
		return sc.obtenerOpcion(4);
	}

	/**
	 * Procesa la opcion
	 * @param op opcion ingresada
	 * @param sc controlador de entradas
	 */
	private void procesarOpcion(int op, ControladorEntradas sc){
		switch (op) {
			case 1:
				partida.jugarPartida(sc);
				if(partida.partidaNueva(sc)){
					partida.guardarPartida();
					empezarPartida();
				}
				break;
			case 2:
				// ir a garaje. Desplega el menu de acciones de garaje
				MenuGaraje menu= new MenuGaraje(partida.nombre(), partida.garaje());
				menu.mostrarMenu();
				break;
			case 3:
				// guarda la partida en un archivo
				partida.guardarPartida();
				break;
			case 4: 
				System.out.println("Termino la partida");
				break;
			default:
				System.err.println("Error en procesar opcion");
			
			if(op<4)
				menuJuego(sc);
		}
	}


}
