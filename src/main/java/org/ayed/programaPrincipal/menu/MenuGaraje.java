package org.ayed.programaPrincipal.menu;

import org.ayed.gta.Concesionario.TipoOperacion;
import org.ayed.gta.Garaje.ArchivoGaraje;
import org.ayed.gta.Garaje.Garaje;
import org.ayed.gta.Partida;
import org.ayed.programaPrincipal.aplicacion.Controlador;
import org.ayed.programaPrincipal.frontend.formulario.Campo;
import org.ayed.programaPrincipal.frontend.formulario.TipoCampo;

public class MenuGaraje{
	private final String FORMATO_RUTA = ".csv";
	private final Garaje garaje;
	private final Controlador controlador;
	private final Partida partida;
	private String ruta;

	/**
	 * Cosntructor
	 * @param ruta ruta del archivo a guardar el garaje+
	 */
    public MenuGaraje(Partida partida, Garaje garaje, Controlador controlador) {

		this.garaje= garaje;
		this.controlador = controlador;
		this.partida = partida;
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
				controlador.mostrarMensaje("Garaje exportado correctamente al archivo:\n" + ruta, 
				() -> controlador.mostrarMenuGaraje());
				break;
			case 8 : //Importar informacion del garaje desde un archivo
				Garaje garajeCargado = importarGaraje();
				if(garajeCargado != null && !garajeCargado.obtenerVehiculo().vacio()){ 
					garaje.copiarGaraje(garajeCargado);
					controlador.mostrarMensaje("Garaje cargado correctamente desde el archivo:\n" + ruta, 
					() -> controlador.mostrarMenuGaraje());
				} else {
					controlador.mostrarMensaje("No se pudo cargar el garaje desde el archivo:\n" + ruta, 
					() -> controlador.mostrarMenuGaraje());
				}
				break;
			case 9: //Cargar gasolina a un vehiculo segun su indice
				Campo[] camposCargar = new Campo[]{
					new Campo("Indice del Vehiculo(Empieza desde cero):", TipoCampo.ENTERO),
					new Campo("Cantidad de litros a cargar:", TipoCampo.ENTERO)
				};
				controlador.mostrarVehiculos(() -> controlador.mostrarCargaPorIndice(camposCargar), garaje.obtenerVehiculo());
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
	public TipoOperacion cargarVehiculo(int[] datos){
		if (garaje.obtenerCreditos() >= datos[1] * garaje.precioLitro()) {
			try {
				if (datos[0] < 0 || datos[0] >= garaje.obtenerVehiculo().tamanio()) {
					return TipoOperacion.VEHICULO_NO_ENCONTRADO;
				}
				garaje.cargarGasolinaVehiculo(datos[1], datos[0]);
				garaje.cargarGasolinaVehiculo(datos[1], datos[0]);
			} catch (Exception e) {
				controlador.mostrarMensaje("Error al cargar gasolina: " + e.getMessage(),
				() -> controlador.mostrarMenuGaraje());
			}
			return TipoOperacion.EXITO;
		}
		return TipoOperacion.DINERO_INSUFICIENTE;
		
	}

	// ----------------------- MÉTODOS AUXILIARES -------------------------

	private void exportarGaraje(){
		try{
			ruta = partida.nombre() + FORMATO_RUTA;
			ArchivoGaraje arch = new ArchivoGaraje(ruta);
			arch.escribirArchGaraje(garaje);
		}catch(Exception e){
			System.err.println(e);
		}

	}

	private Garaje importarGaraje(){
		ruta = partida.nombre() + FORMATO_RUTA;
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


