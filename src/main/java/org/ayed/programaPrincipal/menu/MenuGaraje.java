package org.ayed.programaPrincipal.menu;

import org.ayed.gta.Garaje.ArchivoGaraje;
import org.ayed.gta.Garaje.Garaje;
import org.ayed.programaPrincipal.aplicacion.Controlador;
import org.ayed.programaPrincipal.frontend.formulario.Campo;
import org.ayed.programaPrincipal.frontend.formulario.TipoCampo;

public class MenuGaraje{
	private final String FORMATO_RUTA = ".csv";
	private String ruta="garajeGuardado";
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
				controlador.mostrarVehiculos(controlador::mostrarMenuGaraje, garaje.obtenerVehiculo());
				break;
			case 2 : //Eliminar un vehiculo del garaje (primero muestra todos los vehiculos)
				Campo[] camposEliminar = {
					new Campo("Nombre del Vehiculo a eliminar:", TipoCampo.TEXTO)
				};
				controlador.mostrarVehiculos(() -> controlador.mostrarFormularioEliminar(camposEliminar), garaje.obtenerVehiculo());
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
					new Campo("Indice del Vehiculo(Empieza desde cero):", TipoCampo.ENTERO),
					new Campo("Cantidad de litros a cargar:", TipoCampo.ENTERO)
				};
				controlador.mostrarCargaPorIndice(camposCargar);
				break;
			case 10: //Cargar gasolina a todos los vehiculos del garaje
				
					if(cargarVehiculos()){
						controlador.mostrarMensaje("Vehiculos cargados correctamente.\n", 
						() -> controlador.mostrarMenuGaraje());
					}else{
						controlador.mostrarMensaje("Saldo insuficiente para cargar todos los vehiculos\n", 
						() -> controlador.mostrarMenuGaraje());
					}
				break;
			case 11 : //Volver al menu principal
					System.out.println("-------- SALIENDO --------");
					controlador.mostrarMenuPrincipal();
				break;
			default:
				System.err.println("Error. Opcion no valida");  

		}
	}

	public boolean eliminar(String nombreVehiculo){
		try{
			garaje.eliminarVehiculo(nombreVehiculo);
			return true;
		}catch(Exception e){
			System.err.println(e);
			return false;
		}
	}
	/**
	 * Agrega creditos
	 * @param datos monto que se ingresa para acreditar
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

	/** Cargar un vehiculo
	 *  @param datos [indice, litros]
	 */
	public boolean cargarVehiculo(int[] datos){
		boolean cargado=false;
		if (garaje.obtenerCreditos() >= datos[1] * garaje.precioLitro()) {
			try {
				garaje.cargarGasolinaVehiculo(datos[1], datos[0]);
				cargado=true;
			} catch (Exception e) {
				controlador.mostrarMensaje("Error al cargar gasolina: " + e.getMessage(),
				() -> controlador.mostrarMenuGaraje());
			}
		} else {
			controlador.mostrarMensaje("Créditos insuficientes para cargar gasolina. Créditos actuales: " + garaje.obtenerCreditos(), 
			() -> controlador.mostrarMenuGaraje());
		}
		return cargado;
		
	}

	// ----------------------- MÉTODOS AUXILIARES -------------------------

	private void exportarGaraje(){
		try{
			ArchivoGaraje arch = new ArchivoGaraje(ruta);
			arch.escribirArchGaraje(garaje);
		}catch(Exception e){
			System.err.println(e);
		}

	}

	private Garaje importarGaraje(){
		ArchivoGaraje arch= new ArchivoGaraje(ruta);
		return arch.leerArchGaraje();
	}

	/** 
	 * Carga la gasolina de todos los vehiculos.
	 */
	private boolean cargarVehiculos(){
		boolean cargados=false;
		try {
			garaje.cargarTodosVehiculos();
			cargados= true;
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return cargados;
	}

	private boolean mejorar(){
		try{
			garaje.mejorarGaraje();
			System.out.println("Capacidad de almacen de Vehiculos en Garaje despues de mejora: "+ garaje.capacidadMaxima());
		}catch(Exception e){
			return false;
		}
		return true;
	}

}


