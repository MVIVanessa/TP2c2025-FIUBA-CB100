package org.ayed.gta;
import java.util.Scanner;

public class Menu{
	    final String RUTA = "Garaje.csv"; 

			/** Mostrara el menu de opciones
		 */
		public void mostrarMenu(){
			Scanner entrada= new Scanner(System.in);

			Garaje garaje= new Garaje();
			int opcion;
			do{
				System.out.println("-------MENU DE GARAJE--------");
				System.out.println("Funciones");
				System.out.println("1. Agregar vehiculo al Garaje.");
				System.out.println("2. Mostrar informacion de todos los vehiculos.");
				System.out.println("3. Eliminar un vehiculo.");
				System.out.println("4. Mejorar el garaje.");
				System.out.println("5. Agregar creditos.");
				System.out.println("6. Mostrar el valor total del garaje.");
				System.out.println("7. Mostrar el costo total diario de mantenimiento.");
				System.out.println("8. Exportar la informacion del garaje en archivo 'Garaje.csv' ");
				System.out.println("9. Cargar un garaje a partir de el archivo 'Garaje.csv' ");
				System.out.println("10. Salir");
				System.out.println("Ingrese numero de opcion que quiera seleccionar: ");
				opcion= entrada.nextInt();
				procesarOpcion(opcion,entrada, garaje);
				System.out.println("Presione enter o cualquier tecla para continuar....");
				entrada.nextLine();
				
			}while(opcion!=10); 

			entrada.close();
		}

		/** Procesa la opcion elegida
		 * @param opcion Opcion que eligio el usuario
		 * @param sc Scanner necesario para la entrada de datos por parte del Usuario
		 * @param garaje Garaje alque aplicaremos cualquiera de las acciones
		 */
		private void procesarOpcion(int opcion,Scanner sc, Garaje garaje){
			switch (opcion) {
				case 1 :
					agregandoVehiculo(sc, garaje);
					break;
				case 2 :
					mostrarInfo(garaje);
					break;
				case 3 :
					eliminando(sc, garaje);
					break;
				case 4 :
					mejorar(garaje);
					break;
				case 5 :
					creditos(sc, garaje);
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

				case 10:
					System.out.println("-------- SALIENDO --------"); 
					break;
				default:
					System.out.println("Eleccion de opcion invalida, Ingrese de 1 al 9");
				// Despues de cualquir proceso exceptuando el 10, mostrar el menu de opciones  
	
			}
		}

		/** Verifica que lo que el usuario ingrese si sean numeros puros
		 * El proceso se repetira hasta que el usuario ingrese correctamente un numero puto:  NO "2mil"; SI "2000"
		 * @param mensaje Es el mensaje que se le mostrara al usuario 
		 * @param sc Scanner donde el usuario ingresara los datos
		 * @return datoNum Es el dato ya convertido en numero correctamente
		 */
		private int verificarQueSeaNumero(String mensaje, Scanner sc){
			String linea;
			boolean valido;
			int datoNum=0;
			do { 
				System.out.print(mensaje);
				linea = sc.nextLine();
				try {
					datoNum = Integer.parseInt(linea);
					valido= true;
				} catch (NumberFormatException e) {
					System.out.println(mensaje);
					valido= false;
				}
			} while (!valido);

			return datoNum; 
		}
		/** Agregar un Vehiculo con el interaccion del usuario
		 *  @param sc Scanner Entrada de datos
		 *  @param garaje Garaje al que agregamos el vehiculo 
		 */
		private void agregandoVehiculo(Scanner sc, Garaje garaje){
			System.out.print("Ingrese la informacion de Vehiculo\n Nombre: ");
			sc.nextLine();
			String n = sc.nextLine();   
			
			// como son de tipo numero debo asegurarme que se ingrese adecuadamente valores en numeros
			int p=verificarQueSeaNumero("Precio (numero):$", sc);
			int capacidadG= verificarQueSeaNumero("Capacidad de Gasolina(numeros): ", sc);
			int tipo;
			
			//para que no se ingrese numero diferente a 1 y 2
			do{
				tipo= verificarQueSeaNumero("Tipo (Numeros)[1. AUTO  2. MOTO]: ", sc);
			}while(tipo !=1 && tipo!=2);

			// convierto el numero a un tipo
			TipoVehiculo tipoV = TipoVehiculo.AUTO;
			if(tipo == 2) 
				tipoV= TipoVehiculo.MOTO;

			Vehiculo vehiculo= new Vehiculo (n,tipoV,p, capacidadG);
			
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
				System.out.println("NOMBRE | PRECIO | TIPO | CANT RUEDAS | CAPACIDAD GASOLINA ");
				garaje.mostrarVehiculosGaraje();
			} catch (Exception e) {
				System.err.println(e);
			}
		}

		/** Eliminar un Vehiculo con el interaccion del usuario
		 *  @param sc Scanner Entrada de datos
		 *  @param garaje Garaje al que eliminaremos un vehiculo segun su nombre 
		 */
		private void eliminando(Scanner sc, Garaje garaje){
			try{
				System.out.print("Ingrese nombre de vehiculo que quiera eliminar: ");
				sc.nextLine();
				String nombre= sc.nextLine();
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
		private void creditos(Scanner sc,Garaje garaje){
			try{
				int credito= verificarQueSeaNumero("Monto de credito a ingresar: ", sc);
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


}

