package org.ayed.programaPrincipal;

import org.ayed.gta.ArchivoGaraje;
import org.ayed.gta.Garaje;
import org.ayed.gta.Vehiculos.Auto;
import org.ayed.gta.Vehiculos.Vehiculo;

public class MenuGaraje{
	final String RUTA; 

	/**
	 * Cosntructor
	 * @param ruta ruta del archivo a guardar el garaje+
	 */
    public MenuGaraje(String ruta) {
        this.RUTA = ruta;
    }
	/** Mostrara el menu de opciones
	 */
	public void mostrarMenu(){
		ControladorEntradas controlador= new ControladorEntradas();
		Garaje garaje= new Garaje();
	
		System.out.println("-------MENU DE GARAJE--------");
		System.out.println("Funciones");
		System.out.println("1. Salir");
		System.out.println("2. Mostrar informacion de todos los vehiculos.");
		System.out.println("3. Eliminar un vehiculo.");
		System.out.println("4. Mejorar el garaje.");
		System.out.println("5. Agregar creditos.");
		System.out.println("6. Mostrar el valor total del garaje.");
		System.out.println("7. Mostrar el costo total diario de mantenimiento.");
		System.out.println("8. Exportar la informacion del garaje en archivo 'Garaje.csv' ");
		System.out.println("9. Cargar un garaje a partir de el archivo 'Garaje.csv' ");
		System.out.println("Ingrese numero de opcion que quiera seleccionar: ");

		int opcion = controlador.obtenerOpcion(10);
		procesarOpcion(opcion, garaje, controlador);
		System.out.println("Presione enter o cualquier tecla para continuar....");

	}

	/** Procesa la opcion elegida
	 * @param opcion Opcion que eligio el usuario
	 * @param controlador Controlador de entradas para interaccion con usuario
	 * @param garaje Garaje alque aplicaremos cualquiera de las acciones
	 */
	private void procesarOpcion(int opcion, Garaje garaje, ControladorEntradas controlador){
		switch (opcion) {
			case 1 :
				System.out.println("-------- SALIENDO --------");
				break;
			case 2 :
				mostrarInfo(garaje);
				break;
			case 3 :
				eliminando(garaje, controlador);
				break;
			case 4 :
				mejorar(garaje);
				break;
			case 5 :
				creditos(garaje, controlador);
				break;
			case 6 :
				mostrarPrecioTotal(garaje);
				break;                
			case 7 :
				mostrarCostoMantenimiento(garaje);
				break;
			case 8 :
				exportarGaraje(garaje);
				break;
			case 9 :
				garaje.copiarGaraje(importarGaraje());
				if(!garaje.obtenerVehiculo().vacio())
					System.out.println("Garaje cargado correctamente desde archivo.\n");
				break;
			default:
				System.out.println("Eleccion de opcion invalida, Ingrese de 1 al 9");
			// Despues de cualquir proceso exceptuando el 10, mostrar el menu de opciones  

		}
	}

	/** Agregar un Vehiculo con el interaccion del usuario
	 *  @param controlador Controlador de entradas para interaccion con usuario
	 *  @param garaje Garaje al que agregamos el vehiculo 
	 */
	private void agregandoVehiculo(Garaje garaje, ControladorEntradas controlador){
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
			garaje.agregarVehiculo(vehiculo);
			System.out.println("Se Agrego con exito: Vehiculo" + garaje.obtenerVehiculo().tamanio() );
		} catch (Exception e) {
			System.err.println(e);
		}
		
	}
	/** Mostrar informacion de Vehiculos en Garaje
	 *  @param garaje Garaje que contiene vehiculos a mostrar su informacion 
	 */
	private void mostrarInfo(Garaje garaje){
		try{
			System.out.println("INFORMACION DE VEHICULOS DE GARAJE: ");
			System.out.println("NOMBRE | MARCA | PRECIO | TIPO | CANT RUEDAS | CAPACIDAD GASOLINA | VELOCIDAD ");
			garaje.mostrarVehiculosGaraje();
		} catch (Exception e) {
			System.err.println(e);
		}
	}

	/** Eliminar un Vehiculo con el interaccion del usuario
	 *  @param controlador Controlador de entradas para interaccion con usuario
	 *  @param garaje Garaje al que eliminaremos un vehiculo segun su nombre 
	 */
	private void eliminando(Garaje garaje, ControladorEntradas controlador){
		try{
			System.out.print("Ingrese nombre de vehiculo que quiera eliminar: ");
			
			String nombre = controlador.leerEntrada(false);
			garaje.eliminarVehiculo(nombre);
		}catch(Exception e){
			System.err.println(e);
		}

	}

	/** Mejora de  Almacen de Garaje mostrando alfinal espacio.
	 *  @param garaje Garaje al que mejoramos capacidad de almacen
	 */
	private void mejorar(Garaje garaje){
		try{
			garaje.mejorarGaraje();
			System.out.println("Capacidad de almcen de Vehiculos en Garaje despues de mejora: "+ garaje.capacidadMaxima());
		}catch(Exception e){
			System.err.println(e);
		}
		
	}

	/** Mostrar costo de mantenimiento
	 * @param garaje Garaje a calcular costo de mantenimiento
	 */
	private void mostrarCostoMantenimiento(Garaje garaje){
		int costo= garaje.obtenerCostoMantenimiento();
		System.out.println("El costo total por mantenimiento del garaje es :"+ costo);
	}
	/** Agregar creditos
	 *  @param sc Scanner para Entrada de datos
	 +  @param garaje Garaje a vincular cantidad de creditos
	*/
	private void creditos(Garaje garaje, ControladorEntradas controlador){
		try{
			System.out.print("Ingrese monto de creditos a agregar: ");
			int credito = controlador.leerEntrada(true);
			garaje.agregarCreditos(credito);
			System.out.println("Creditos disponibles: "+ garaje.obtenerCreditos());
		}catch(Exception e){
			System.err.println(e);
		}

	}

	/** Mostrar Precio total en Garaje 
	 * @param garaje GArane precio de monto total de precios en vehiculos dentro.
	 */
	private void mostrarPrecioTotal(Garaje garaje){
		int monto= garaje.obtenerValorTotal();
		System.out.println("Valor total en el Garaje: "+ monto);

	}

	/** Exportacion de informacion de Garaje a un archivo
	 * @param garaje Garaje a exportar
	 */
	private void exportarGaraje(Garaje garaje){
		try{
			ArchivoGaraje arch = new ArchivoGaraje(RUTA);
			arch.escribirArchGaraje(garaje);
		}catch(Exception e){
			System.err.println(e);
		}

	}

	/** Importacion de informacion a un Garaje desde un archivo
	 */
	private Garaje importarGaraje(){
		ArchivoGaraje arch= new ArchivoGaraje(RUTA);
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

}

