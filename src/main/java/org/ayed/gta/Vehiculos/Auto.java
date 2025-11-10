package org.ayed.gta.Vehiculos;

public class Auto extends Vehiculo{
	private final int COSTO_AUTO = 50;
	private static final int RUEDA_AUTO= 4;
	private static final String TIPO ="AUTO"; //para imprimir la info
	private final int COSTO_X_KM_AUTO=10;
	private final int CADAKM= 1000;
	
	private String nombre;
	private int precio;
	private int  capacidadGas;
	private int ruedas;
	private String marca;
	private int kilometraje;
	private int velocidadMax;
	private int tanqueGasolina;

	/**
	 * Constructor de Auto
     * @param nombre nombre de auto.
     * @param marca la marca de auto
	 * @param precio el valor del costo por litro que requiere como mantenimiento
     * @param capacidadGasolina capacidad de auto que tiene para Gasolina
	 */
	Auto(String nombre, String marca, int precio, int capacidadGasolina, int velocidadMax) {
		
		super(nombre, marca, RUEDA_AUTO, precio, capacidadGasolina , TIPO, velocidadMax);
	}
	@Override
	public int costoMantenimientoVehiculo(){
	
		int costoM= this.capacidadGas*COSTO_GASOLINA + COSTO_AUTO*this.ruedas;
		
		if(CADAKM >= kilometraje)
			costoM+= CADAKM * COSTO_X_KM_AUTO;
		return costoM;
	}

}