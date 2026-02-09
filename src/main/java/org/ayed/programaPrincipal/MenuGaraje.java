package org.ayed.programaPrincipal;

import org.ayed.gta.Garaje.ArchivoGaraje;
import org.ayed.gta.Garaje.Garaje;
import org.ayed.gta.Vehiculos.Auto;
import org.ayed.gta.Vehiculos.Vehiculo;
import org.ayed.programaPrincipal.interfaz.Campo;
import org.ayed.programaPrincipal.interfaz.Controlador;
import org.ayed.programaPrincipal.interfaz.TipoCampo;
import org.ayed.tda.vector.Vector;

public class MenuGaraje{
	private final String FORMATO_RUTA = ".csv";
	private String ruta;
	private Garaje garaje;

	/**
	 * Cosntructor
	 * @param ruta ruta del archivo a guardar el garaje+
	 */
    public MenuGaraje(String ruta, Garaje garaje){
        this.ruta = ruta+FORMATO_RUTA;
		this.garaje= garaje;
    }

	/** Procesa la opcion elegida
	 * @param opcion Opcion que eligio el usuario
	 * @param controlador Controlador de entradas para interaccion con usuario
	 * @param garaje Garaje alque aplicaremos cualquiera de las acciones
	 */
	public void procesarOpcion(int opcion, Garaje garaje, Controlador controlador){

		switch (opcion) {

			case 1 :
				String info = vehiculosAMostrar(garaje);
				controlador.mostrarMensaje(info, () -> controlador.mostrarMenuGaraje());
				break;
			case 2 :
				//eliminando(garaje, controlador);
				break;
			case 3 :
				if (mejorar(garaje)) {
					controlador.mostrarMensaje("Garaje mejorado correctamente. Capacidad actual: " + garaje.capacidadMaxima(),
					() -> controlador.mostrarMenuGaraje());
				} else {
					controlador.mostrarMensaje("No se pudo mejorar el garaje. Verifique que tiene suficientes créditos.", 
					() -> controlador.mostrarMenuGaraje());
				}
				break;
			case 4 :
				Campo[] campos = {
					new Campo("Monto a agregar:", TipoCampo.ENTERO)
				};
				controlador.mostrarFormularioCreditosAgregados(campos);
				break;
			case 5 :
					int monto= garaje.obtenerValorTotal();
					controlador.mostrarMensaje("Valor total en el Garaje: "+ monto, 
					() -> controlador.mostrarMenuGaraje());
				break;                
			case 6 :
				int costo= garaje.obtenerCostoMantenimiento();
				controlador.mostrarMensaje("El costo total por mantenimiento del garaje es :"+ costo, 
				() -> controlador.mostrarMenuGaraje());
				break;
			case 7 :
				exportarGaraje(garaje);
				controlador.mostrarMensaje("Garaje exportado correctamente a archivo.\n", 
				() -> controlador.mostrarMenuGaraje());
				break;
			case 8 :
				garaje.copiarGaraje(importarGaraje());
				if(!garaje.obtenerVehiculo().vacio())
					controlador.mostrarMensaje("Garaje cargado correctamente desde archivo.\n", 
					() -> controlador.mostrarMenuGaraje());
				break;
			case 9:
				//cargarVehiculo(garaje, controlador);
				break;
			case 10:
				cargarVehiculos(garaje);
				break;
			case 11 :
					System.out.println("-------- SALIENDO --------");
					controlador.mostrarMenuPrincipal();
				break;
			default:
				System.err.println("Error. Opcion no valida");  

		}
	}

	/** Agregar un Vehiculo con el interaccion del usuario
	 *  @param controlador Controlador de entradas para interaccion con usuario
	 *  @param garaje Garaje al que agregamos el vehiculo 
	 
	public void agregandoVehiculosssssssssssssssss(Garaje garaje, ControladorEntradas controlador){
		System.out.println("Ingrese la informacion de Vehiculo");
		System.out.print("Nombre: ");

		String n = controlador.leerEntrada(false);   
		System.out.print("Marca: ");
		String m = controlador.leerEntrada(false);
		
		System.out.println("Tipo (Numeros)[1. AUTO  2. MOTO]: ");
		int tipo = controlador.obtenerOpcion(2);

		// como son de tipo numero debo asegurarme que se ingrese adecuadamente valores en numeros
		System.out.print("Precio (numero): ");
		int p = controlador.leerEntrada(true);
		System.out.print("Capacidad de Gasolina(numeros): ");
		int capacidadG = controlador.leerEntrada(true);
		System.out.print("Velocidad (numero): Km/h ");
		int velocidad = controlador.leerEntrada(true);

		// convierto el numero a un tipo
		String tipoV = "AUTO";
		if(tipo == 2) 
			tipoV= "MOTO";
		Vehiculo vehiculo = crearVehiculo(n,m,tipoV,p,capacidadG,velocidad);
		
		try {
			System.out.println(garaje.agregarVehiculo(vehiculo));
			System.out.println("Se Agrego con exito: Vehiculo" + garaje.obtenerVehiculo().tamanio() );
		} catch (Exception e) {
			System.err.println(e);
		}
		
	}*/

	/** Mostrar informacion de Vehiculos en Garaje
	 *  @param garaje Garaje que contiene vehiculos a mostrar su informacion 
	 */
	private String vehiculosAMostrar (Garaje garaje){
		Vector<Vehiculo> vehiculos = garaje.obtenerVehiculo();
		Vehiculo v;
		String infoVehiculos = "INDICE | NOMBRE | MARCA | PRECIO | TIPO | CANT RUEDAS | CAPACIDAD GASOLINA | VELOCIDAD ";
		for (int i = 0; i < vehiculos.tamanio(); i++) {
			v = vehiculos.dato(i);
			infoVehiculos += "\n" + i + " | " + v.informacionVehiculo();
		}
		return infoVehiculos;
	}

	/** Eliminar un Vehiculo con el interaccion del usuario
	 *  @param controlador Controlador de entradas para interaccion con usuario
	 *  @param garaje Garaje al que eliminaremos un vehiculo segun su nombre 
	 
	private void eliminando(Garaje garaje){
		try{
			System.out.print("Ingrese nombre de vehiculo que quiera eliminar: ");
			
			String nombre = controlador.leerEntrada(false);
			garaje.eliminarVehiculo(nombre);
		}catch(Exception e){
			System.err.println(e);
		}

	}*/

	/** Mejora de Almacen de Garaje mostrando alfinal espacio.
	 *  @param garaje Garaje al que mejoramos capacidad de almacen
	 */
	private boolean mejorar(Garaje garaje){
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
	private void exportarGaraje(Garaje garaje){
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

	/**
	 * Interpreta los Las palabras del tipo de Vehiculo
	 * @param nom nombre del vehiculo
	 * @param marc marca del Vehiculo
	 * @param t tipo de Vehiculo que se dice en el archivo
	 * @param int prec precio del vehiculo
	 * @param c capacidad de Gasolina del vehiculo
	 * @param v velocidad del Vehiculo
	 * @return tipo de vehiculo ya vuelto objeto TipoVehiculo 
	* * throw ExcepcionArchivoGaraje el tipo de dato es irreconocible
	*/
	private Vehiculo crearVehiculo(String nom, String marc, String t, int prec,int c, int v){
				Vehiculo vehiculo=null;
				switch (t) {
					case "AUTO":
						vehiculo = new Auto(nom,marc,prec,c,v);
						break;
					case "MOTO":
						//vehiculo = new Moto(nom,marc,prec,c,v);
						break;
					default:
						System.err.println("Tipo de Vehiculo desconocido: " + t);
				}
		return vehiculo;
	}

	/** Cargar un vehiculo
	 *  @param c Controlador de Entradas para Entrada de datos
	 +  @param garaje Garaje a vincular cantidad de creditos
	
	private void cargarVehiculo(Garaje g, ControladorEntradas c){
		System.out.println("Ingrese el indice del Vehiculo:");
		int indice= c.obtenerOpcion(g.capacidadMaxima()-1);
		System.out.println("Ingrese cantidad e litros a llenas(numero): ");
		int litro= c.leerEntrada(true);
		try {
			g.cargarGasolinaVehiculo(litro, indice);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		
	}*/

	/** Carga todos los vehiculos en el garaje
	 * @param garaje Garaje a vincular cantidad de creditos
	 */
	private void cargarVehiculos(Garaje g){
		try {
			g.cargarTodosVehiculos();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
}

