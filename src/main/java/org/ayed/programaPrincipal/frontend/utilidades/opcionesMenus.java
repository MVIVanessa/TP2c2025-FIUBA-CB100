package org.ayed.programaPrincipal.frontend.utilidades;

public class opcionesMenus {

    private final static String ruta = "garajeGuardado.csv";
    
    public final static String[] opcionesPrincipal = {
		"Jugar Partida",
		"Ir al Garaje",
		"Guardar Partida",
		"Ir al Concesionario",
		"Mostrar Dinero en Cuenta",
		"Salir del Juego"
	};

    public final static String[] opcionesGaraje = {
		"Mostrar informacion de todos los vehiculos.",
		"Eliminar un vehiculo.",
		"Mejorar el garaje.",
		"Agregar creditos.",
		"Mostrar el valor total del garaje.",
		"Mostrar el costo total diario de mantenimiento.",
		"Exportar la informacion del garaje en archivo " + ruta,
		"Cargar un garaje a partir de el archivo " + ruta,
		"Cargar gasolina un vehiculo segun su indice",
		"Cargar gasolina todos los Vehiculos en el garaje.",
		"Salir"
	};

	public final static String[] opcionesConcesionario = {
		"Buscar vehiculo por Nombre",
		"Buscar Vehiculo por Marca",
		"Mostrar Stock Completo",
		"Comprar un Vehiculo",
		"Salir del Concesionario"
	};

	public final static String[] opcionesDificultad = {
		"Facil",
		"Medio",
		"Dificil",
	};

	public final static String[] opcionesContinuarJugando = {
		"Continuar",
		"Salir"
	};

}
