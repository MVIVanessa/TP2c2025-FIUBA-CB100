package org.ayed.gta;

import org.ayed.gta.Misiones.Dificil;
import org.ayed.gta.Misiones.ExcepcionMision;
import org.ayed.gta.Misiones.Facil;
import org.ayed.gta.Misiones.Mision;
import org.ayed.gta.Misiones.Moderada;
import org.ayed.gta.Vehiculos.Vehiculo;
import org.ayed.programaPrincipal.ControladorEntradas;
import org.ayed.tda.lista.Lista;

public class JuegoPrincipal {
	Lista<Mision> misiones;	
	String nombreJugador;
	int dinero;
    public JuegoPrincipal(String nombre) {
		misiones = new Lista<>();
		nombreJugador= nombre;
		dinero=0;
    }
	public void empezarPartida(){
		ControladorEntradas entrada=new ControladorEntradas();
		Garaje garaje = new Garaje();
		do{
			System.out.println("Eliga el modo de la Mision: (1.Dificil, 2.Moderada, 3.Facil): ");
			int eleccion = entrada.obtenerOpcion(3);
			elegirModo(eleccion);
			jugarMision(entrada, garaje);

		}while(!misiones.dato(misiones.tamanio()-1).fracaso());

	}

	public void elegirModo(int i){
		Mision mision;
		switch (i) {
			case 1:
				mision= new Dificil();
				break;
			case 2:
				mision= new Moderada();
				break;
			case 3:
				mision= new Facil();
				break;
			default:
				throw new ExcepcionMision("Eleccion de Modo de juego invalido");
		}
		misiones.agregar(mision);

	}

	public void jugarMision(ControladorEntradas sc, Garaje garaje){
		Mision mision= misiones.dato(misiones.tamanio()-1);
		mostrarComandosJugador();
		int cantPerm = mision.vehiculosPermitidos(garaje);
		System.out.println("Ingrese numero de Vehiculo que quiere usar: ");
		int elegido= sc.obtenerOpcion(cantPerm);
		Vehiculo v= mision.seleccionarVehiculo(elegido);
		System.out.println("Vehiculo seleccionado: "+ v.nombreVehiculo());
		while(!mision.misionCompletada() &&!mision.fracaso()){
			mision.despliegueDeMapa();
			
			String movimiento= sc.leerEntrada(false);
			try {
				mision.moverJugador(movimiento);
				dinero-= garaje.obtenerCostoMantenimiento();

			} catch (ExcepcionMision e) {
				System.out.println(e.getMessage());
			}
			if (mision.misionCompletada())
            	System.out.println("¡¡Misión completada!!");
		}
		if(mision.fracaso())
            System.out.println("Fracaso de misión...");
	}

	private void mostrarComandosJugador(){
		System.out.println("Para Moverse alrededor del Mapa se usa las letras WASD ");
		System.out.println("W= Arriba");
		System.out.println("S= Abajo");
		System.out.println("D= Derecha");
		System.out.println("A= Izquierda");
	}
	
}
