package org.ayed.programaPrincipal;

import org.ayed.gta.Concesionario.MenuConcesionario;
import org.ayed.gta.Mapa.Interfaz;
import org.ayed.gta.Partida;

public class MenuPartida {
	private Partida partida;
	/**Comienza una Partida */
	public MenuPartida(){
		partida= new Partida();
	}
	public void empezarPartida(){
		ControladorEntradas entrada=new ControladorEntradas();
		//ingreso de nombre de usuario y creacion de archivo
		partida.ingresarNombre(entrada);

		int opcion;
		do{
			opcion = menuJuego(entrada);
			procesarOpcion(opcion,entrada);
		}while(opcion!=6);

		entrada.cerrar();
	}
	/**
	 * Muestra el menu al juagador
	 * @param sc Controlador de entradas
	 * @return opcion ingresada por el usuario
	 */
	private int menuJuego(ControladorEntradas sc){
		System.out.println(" ______________________________________");
		System.out.println( "| 1. Jugar una Mision                  |\n"+
							"| 2. Ir al Garaje                      |\n"+
							"| 3. Guardar partida en un archivo     |\n"+
							"| 4. Ir a Concesionario		       |\n"+
							"| 5. Mostrar dinero en cuenta			|\n"+
							"| 6. Salir (No guarda cambios)         |\n"+
							"|______________________________________|");

		return sc.obtenerOpcion(6);
	}

	/**
	 * Procesa la opcion
	 * @param op opcion ingresada
	 * @param sc controlador de entradas
	 */
	private void procesarOpcion(int op, ControladorEntradas sc){
		switch (op) {
			case 1:
					new Thread(() -> Interfaz.lanzar(new String[]{})).start();

					// Esperar a que la interfaz esté lista
					while (Interfaz.getInstancia() == null) {
						try { Thread.sleep(50); } catch (InterruptedException e) {}
					}

					System.out.println("Ventana abierta, listo para jugar.");

					// Usar la partida ya creada
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
				desplegarConcesionario(sc);
				break;
			case 5:
				System.out.println("Dinero en cuenta: $"+ partida.dinero());
				break;
			case 6:
				System.out.println("Termino la partida");
				break;
			default:
				System.err.println("Error en procesar opcion");
		}

	}
	private void desplegarConcesionario(ControladorEntradas sc){
		MenuConcesionario menuC= new MenuConcesionario(partida);
		menuC.desplegarMenu(sc);
	}


}
