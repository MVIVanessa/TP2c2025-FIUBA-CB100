package org.ayed.gta.Vehiculos;

public class Auto extends Vehiculo{
	private static final int COSTO_AUTO = 50;
	private static final int RUEDA_AUTO= 4;
	private static final String TIPO ="AUTO"; //para imprimir la info
	
	private String nombre;
	private int precio;
	private int  capacidadGas;
	private int ruedas;
	private String marca;

	/**
	 * Constructor de Auto
     * @param nombre nombre de auto.
     * @param marca la marca de auto
	 * @param precio el valor del costo por litro que requiere como mantenimiento
     * @param capacidadGasolina capacidad de auto que tiene para Gasolina
	 */
	Auto(String nombre, String marca, int precio, int capacidadGasolina) {
		
		super(nombre, marca, RUEDA_AUTO, precio, capacidadGasolina , TIPO);
	}
	@Override
	public int costoMantenimientoVehiculo(){
		int costoM= this.capacidadGas*COSTO_GASOLINA + COSTO_AUTO*this.ruedas;
		return costoM;
	}

}