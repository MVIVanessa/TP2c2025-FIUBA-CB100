package org.ayed.gta.Vehiculos;
public class Moto extends Vehiculo{
    private final int COSTO_MOTO = 50;
	private static final int RUEDA_MOTO = 2;
	private static final String TIPO = "MOTO"; //para imprimir la info
	private final int COSTO_X_KM_MOTO =10;
	private final int CADAKM = 500;
	
	private String nombre;
	private int precio;
	private int  capacidadGas;
	private int ruedas;
	private String marca;
	private int kilometraje;
	private int velocidadMax;
	private int tanqueGasolina;

	/**
	 * Constructor de Moto
     * @param nombre nombre de moto
     * @param marca la marca de moto
	 * @param precio el valor del costo por litro que requiere como mantenimiento
     * @param capacidadGasolina capacidad de moto que tiene para Gasolina
	 */
	public Moto(String nombre, String marca, int precio, int capacidadGasolina, int velocidadMax) {
		
		super(nombre, marca, RUEDA_MOTO, precio, capacidadGasolina , TIPO, velocidadMax);
	}
	@Override
	public int costoMantenimientoVehiculo(){
	
		int costoM = this.capacidadGas*COSTO_GASOLINA + COSTO_MOTO*this.ruedas;
		
		if(CADAKM >= kilometraje)
			costoM += CADAKM * COSTO_X_KM_MOTO;
		return costoM;
	}
}