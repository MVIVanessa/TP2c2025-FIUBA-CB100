package org.ayed.programaPrincipal;

import org.ayed.gta.Garaje.ArchivoGaraje;
import org.ayed.gta.Garaje.Garaje;
import org.ayed.programaPrincipal.interfaz.Campo;
import org.ayed.programaPrincipal.interfaz.Controlador;
import org.ayed.programaPrincipal.interfaz.TipoCampo;

public class MenuGaraje{
	private final String FORMATO_RUTA = ".csv";
	private String ruta;
	private Garaje garaje;
	Controlador controlador;

	/**
	 * Cosntructor
	 * @param ruta ruta del archivo a guardar el garaje+
	 */
    public MenuGaraje(String ruta, Garaje garaje, Controlador controlador) {
        this.ruta = ruta+FORMATO_RUTA;
		this.garaje= garaje;
		this.controlador = controlador;
    }

	/** Procesa la opcion elegida
	 * @param opcion Opcion que eligio el usuario
	 * @param controlador Controlador de entradas para interaccion con usuario
	 * @param garaje Garaje alque aplicaremos cualquiera de las acciones
	 */
	public void procesarOpcion(int opcion, Controlador controlador){

		switch (opcion) {

			case 1 : //Mostrar todos los vehiculos del garaje
				controlador.mostrarTodosLosVehiculos(controlador::mostrarMenuGaraje);
				break;
			case 2 : //Eliminar un vehiculo del garaje (primero muestra todos los vehiculos)
				Campo[] camposEliminar = {
					new Campo("Nombre del Vehiculo a eliminar:", TipoCampo.TEXTO)
				};
				controlador.mostrarTodosLosVehiculos(() -> controlador.mostrarFormularioEliminar(camposEliminar));
				break;
			case 3 : //Mejorar garaje
				if (mejorar()) {
					controlador.mostrarMensaje("Garaje mejorado correctamente. Capacidad actual: " + garaje.capacidadMaxima(),
					() -> controlador.mostrarMenuGaraje());
				} else {
					controlador.mostrarMensaje("No se pudo mejorar el garaje. Verifique que tiene suficientes créditos.", 
					() -> controlador.mostrarMenuGaraje());
				}
				break;
			case 4 : //Agregar creditos al garaje
				Campo[] camposAgregar = {
					new Campo("Monto a agregar:", TipoCampo.ENTERO)
				};
				controlador.mostrarFormularioCreditosAgregados(camposAgregar);
				break;
			case 5 : //Mostrar valor total del garaje
					int monto= garaje.obtenerValorTotal();
					controlador.mostrarMensaje("Valor total en el Garaje: "+ monto, 
					() -> controlador.mostrarMenuGaraje());
				break;                
			case 6 : //Mostrar costo total diario de mantenimiento del garaje
				int costo= garaje.obtenerCostoMantenimiento();
				controlador.mostrarMensaje("El costo total por mantenimiento del garaje es :"+ costo, 
				() -> controlador.mostrarMenuGaraje());
				break;
			case 7 : //Exportar informacion del garaje a un archivo
				exportarGaraje();
				controlador.mostrarMensaje("Garaje exportado correctamente a archivo.\n", 
				() -> controlador.mostrarMenuGaraje());
				break;
			case 8 : //Importar informacion del garaje desde un archivo
				garaje.copiarGaraje(importarGaraje());
				if(!garaje.obtenerVehiculo().vacio())
					controlador.mostrarMensaje("Garaje cargado correctamente desde archivo.\n", 
					() -> controlador.mostrarMenuGaraje());
				break;
			case 9: //Cargar gasolina a un vehiculo segun su indice
				Campo[] camposCargar = new Campo[]{
					new Campo("Indice del Vehiculo:", TipoCampo.ENTERO),
					new Campo("Cantidad de litros a cargar:", TipoCampo.ENTERO)
				};
				controlador.mostrarCargaPorIndice(camposCargar);
				break;
			case 10: //Cargar gasolina a todos los vehiculos del garaje
				cargarVehiculos();
				break;
			case 11 : //Salir del garaje
					System.out.println("-------- SALIENDO --------");
					controlador.mostrarMenuPrincipal();
				break;
			default:
				System.err.println("Error. Opcion no valida");  

		}
	}



	/** Eliminar un Vehiculo con el interaccion del usuario
	 *  @param controlador Controlador de entradas para interaccion con usuario
	 *  @param garaje Garaje al que eliminaremos un vehiculo segun su nombre 
	*/
	public void eliminar(String nombreVehiculo){
		try{
			garaje.eliminarVehiculo(nombreVehiculo);;
		}catch(Exception e){
			System.err.println(e);
		}

	}

	/** Mejora de Almacen de Garaje mostrando alfinal espacio.
	 *  @param garaje Garaje al que mejoramos capacidad de almacen
	 */
	private boolean mejorar(){
		try{
			garaje.mejorarGaraje();
			System.out.println("Capacidad de almacen de Vehiculos en Garaje despues de mejora: "+ garaje.capacidadMaxima());
		}catch(Exception e){
			System.err.println(e.getMessage());
			return false;
		}
		return true;
	}

	/** Agregar creditos

	 +  @param garaje Garaje a vincular cantidad de creditos
	*/
	public void agregarCreditos(String[] datos){
		int monto = Integer.parseInt(datos[0]);
		try {
			garaje.agregarCreditos(monto);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		System.out.println("Se agregaron " + monto + " créditos al garaje. Créditos actuales: " + garaje.obtenerCreditos());

	}

	/** Exportacion de informacion de Garaje a un archivo
	 * @param garaje Garaje a exportar
	 */
	private void exportarGaraje(){
		try{
			ArchivoGaraje arch = new ArchivoGaraje(ruta);
			arch.escribirArchGaraje(garaje);
		}catch(Exception e){
			System.err.println(e);
		}

	}

	/** Importacion de informacion a un Garaje desde un archivo
	 */
	private Garaje importarGaraje(){
		ArchivoGaraje arch= new ArchivoGaraje(ruta);
		return arch.leerArchGaraje();
	}

	/** Cargar un vehiculo
	 *  @param datos datos necesarios para cargar gasolina en un vehiculo
	 * 				[indice, litros]
	 */
	public void cargarVehiculo(int[] datos){
		if (garaje.obtenerCreditos() >= datos[1] * garaje.precioLitro()) {
			try {
				garaje.cargarGasolinaVehiculo(datos[1], datos[0]);
			} catch (Exception e) {
				controlador.mostrarMensaje("Error al cargar gasolina: " + e.getMessage(),
				() -> controlador.mostrarMenuGaraje());
			}
		} else {
			controlador.mostrarMensaje("Créditos insuficientes para cargar gasolina. Créditos actuales: " + garaje.obtenerCreditos(), 
			() -> controlador.mostrarMenuGaraje());
		}
		
	}

	/** Carga todos los vehiculos en el garaje
	 * @param garaje Garaje a vincular cantidad de creditos
	 */
	private void cargarVehiculos(){
		try {
			garaje.cargarTodosVehiculos();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
}

