package org.ayed.gta.Vehiculos;
public class Exotico extends Vehiculo{
	private final int COSTO_EXOTICO = 50;
	private static final String TIPO ="EXOTICO"; //para imprimir la info
	private final int COSTO_X_EXOTICO= 100;
	
	private String nombre;
	private int precio;
	private int  capacidadGas;
	private int ruedas;
	private String marca;
	private int kilometraje;
	private int velocidadMax;
	private int tanqueGasolina;

	/**
	 * Constructor de Exotico
     * @param nombre nombre de exotico
     * @param marca la marca de exotico
	 * @param precio el valor del costo por litro que requiere como mantenimiento
     * @param capacidadGasolina capacidad de exotico que tiene para Gasolina
	 */
	public Exotico(String nombre, String marca, int ruedas, int precio, int capacidadGasolina, int velocidadMax) {
		
		super(nombre, marca, ruedas, precio, capacidadGasolina , TIPO, velocidadMax);
        this.ruedas=ruedas;
	}
	@Override
	public int costoMantenimientoVehiculo(){
        int costoRuedas = 0;
        for (int r= 1; r<= ruedas; r++){ //podria haber un exotico con 0 ruedas
            costoRuedas+= r*10;
        }
		int costoM = capacidadGasolina()*COSTO_GASOLINA + COSTO_EXOTICO + costoRuedas + COSTO_X_EXOTICO;
		return costoM;
	}
}