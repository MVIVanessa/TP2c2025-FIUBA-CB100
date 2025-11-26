package org.ayed.gta;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import org.ayed.gta.Vehiculos.Auto;
import org.ayed.gta.Vehiculos.Exotico;
import org.ayed.gta.Vehiculos.Moto;
import org.ayed.gta.Vehiculos.Vehiculo;
import org.ayed.tda.vector.Vector;

public class ArchivoGaraje{
	private String ruta;

	/**
	 * Constructor de Archivo garaje
	 * @param ruta ruta de archivo
	 */
    public ArchivoGaraje(String ruta) {
        this.ruta = ruta;
    }

	/**
	 * Escribe en el Archivo la informacion en Garaje
	 * @param g Garaje a importa
	* * throw ExcepcionArchivoGaraje cuando hubo algun problema durante la importacion
	 */
	public void escribirArchGaraje(Garaje g){
		try {
			FileWriter arch = new FileWriter(ruta);
			// Capacidad Maxima, Creditos
			arch.write(g.capacidadMaxima() +", " +g.obtenerCreditos()+ "\n"); 
			Vector <Vehiculo> v= g.obtenerVehiculo();
			int logico= v.tamanio();		//tamanio logico del Garaje
			for(int i=0; i<logico; i++){
				arch.write(v.dato(i).informacionVehiculo()+"\n");
			}
			arch.close();			
		} catch (IOException e) {
			throw new ExcepcionArchivoGaraje("No se logro importar al Archivo, "+ e.getMessage());
		}

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
	private Vehiculo crearVehiculo(String nom, String marc, String t, int r, int prec,int c, int v){
                Vehiculo vehiculo=null;
				switch (t) {
                    case "AUTO":
                        vehiculo = new Auto(nom,marc,prec,c,v);
                        break;
                    case "MOTO":
                        vehiculo = new Moto(nom,marc,prec,c,v);
                        break;
					case "EXOTICO":
						vehiculo = new Exotico(nom,marc,r, prec,c,v);
						break;
					default:
                        throw new ExcepcionArchivoGaraje("Tipo de Vehiculo desconocido: " + t);
                }
		return vehiculo;
	}

	/** Pocesa el archivo csv detectando los "," para seccionarlo en partes
		asi hasta que ya no pueda leer mas lineas del archivo;
		@param a Archivo el cual leemos y se convierte en un Objeto tipo Garaje
		@return nuevoGaraje
	**	throw ExcepcionArchivoGaraje En caso de que haya algun error al convertir el string en numero
	*/
	private Garaje procesarArchivo(FileReader a){
		Scanner entrada = new Scanner(a);
		Garaje nuevGaraje= null;

		//primera linea diferente 
		if(entrada.hasNextLine()){
			String linea= entrada.nextLine().trim();
			String[] partes= linea.split(",");
			int capacidad= Integer.parseInt(partes[0].trim());
			int credito= Integer.parseInt(partes[1].trim());
		
			Vector<Vehiculo> vec = new Vector<>();
			// mientras pueda seguir leyendose el archivo
			while (entrada.hasNextLine()) {
				linea = entrada.nextLine().trim();
				partes = linea.split(",");
				try {
					String nombre = partes[0].trim();
					String marca = partes[1].trim();
					int precio = Integer.parseInt(partes[2].trim());

					String tipo = partes[3].trim().toUpperCase(); // procesara la enumeracion y vaolvera a tipoVehiculo 	
					//no necesito la Lectura de CANTIDAD RUEDAS en mi constructor lo hace de na
					int ruedas = Integer.parseInt(partes[4].trim());
					int capacidadGas = Integer.parseInt(partes[5].trim());
					int velocidad= Integer.parseInt(partes[6].trim());
					Vehiculo v = crearVehiculo(nombre,marca,tipo,ruedas, precio, capacidadGas,velocidad);
					vec.agregar(v);

				} catch (NumberFormatException e) {
					throw new ExcepcionArchivoGaraje("Error al convertir linea en secciones");
				}
			}
			nuevGaraje = new Garaje(vec, capacidad, credito);

		}
		entrada.close();
		return nuevGaraje; 
	}

	/*
		Leer un archivo .csv;
	*	throw RuntimeException ocurrio algun al leer o abrir el Archivo
	 */
	public Garaje leerArchGaraje(){
		Garaje g;
		try {
			FileReader arch = new FileReader(ruta);
			g = procesarArchivo(arch);
			arch.close();

		} catch (IOException e) {
			System.err.println(e);
			// creo el archico como no existe
			g= new Garaje();
			escribirArchGaraje(g);
		}
		return g;
	}
}