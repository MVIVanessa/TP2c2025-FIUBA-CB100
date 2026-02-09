package org.ayed.programaPrincipal.interfaz;

import org.ayed.gta.Garaje.Garaje;
import org.ayed.gta.Misiones.Mision;
import org.ayed.gta.Partida;
import org.ayed.programaPrincipal.MenuGaraje;
import org.ayed.programaPrincipal.MenuPartida;

public class Controlador {
    private final Partida partida;
    private final MenuPartida menuPartida;
    private final MenuGaraje menuGaraje;
    private final Garaje garaje;
    private int[] datosJugador; // [dia actual, dinero, creditos]

    public Controlador() {
        this.garaje = new Garaje();
        this.partida = new Partida(garaje, this);
        menuPartida = new MenuPartida(partida);
        menuGaraje = new MenuGaraje(partida.nombre(), partida.garaje(), this);
    }

    public void iniciar() {
        mostrarMensaje(
            "¡Bienvenido al Garaje de GTA VI!",
            () -> mostrarIngresoNombreJugador(new Campo[] {
                new Campo("Nombre", TipoCampo.TEXTO)
            })
        );
    }
    
    public void empezarMision() {
        if (!partida.puedeJugar()) {
            mostrarMensaje(
                "Game Over\nNo se puede continuar la partida. No tiene suficiente dinero.",
                () -> Interfaz.cerrar()
            );
            return;
        }
        mostrarMenuDificultad();
    }

    //procesar opciones de los menus (redirecciona a la clase correspondiente que maneja la logica). Uso "interno".

    public void procesarMenuPrincipal(int opcion) {
        menuPartida.procesarOpcion(opcion, this);
    }

    public void procesarMenuGaraje(int opcion) {
        menuGaraje.procesarOpcion(opcion, this);
    }

    public void procesarMenuDificultad(int opcion) {
        partida.elegirModo(opcion);

        Mision m = partida.misionActual();
        m.vehiculosPermitidos(garaje);

        mostrarVehiculosPermitidos(m);
    }

    public void procesarSeleccionVehiculo(int opcion) {
        Mision m = partida.misionActual();
        m.seleccionarVehiculo(opcion);

        iniciarMision(m);
    }

    public void procesarMenuContinuarJugando(int opcion) {
        if (opcion == 1) {
            mostrarMenuPrincipal();
        } else {
            mostrarMensaje("Gracias por jugar. ¡Hasta la próxima!", () -> Interfaz.cerrar());
        }
    }

    private void procesarCreditosAgregados(String[] datos) {
        menuGaraje.agregarCreditos(datos);
        mostrarMensaje(
        "Créditos actuales: " + garaje.obtenerCreditos(), () -> mostrarMenuGaraje());
    }

    private void procesarCargaPorIndice(String[] datos) {
        int indice = Integer.parseInt(datos[0]);
        int litros = Integer.parseInt(datos[1]);
        menuGaraje.cargarVehiculo(new int[]{indice, litros});
        mostrarMensaje(
            "Vehículo cargado correctamente.", () -> mostrarMenuGaraje()
        );
    }

    private void procesarNombreJugador(String[] datos) {
        partida.guardarNombre(datos[0]);
        mostrarMensaje("Bienvenido/a " + datos[0] + "!", () -> mostrarMenuPrincipal());
    }

    public void procesarMisionFinalizada(boolean completada) {
        partida.finalizarMision(completada);

        if (completada) {
            mostrarMensaje(
                "¡Misión completada!",
                this::mostrarMenuContinuarJugando
            );
        } else {
            mostrarMensaje(
                "Misión fallida",
                this::mostrarMenuContinuarJugando
            );
        }
    }

    public void procesarEliminacion(String[] datos) {
        try {
            menuGaraje.eliminar(datos[0]);
            mostrarMensaje("Vehículo eliminado correctamente.", () -> mostrarMenuGaraje());
        } catch (Exception e) {
            mostrarMensaje("Error al eliminar vehículo: " + e.getMessage(), () -> mostrarMenuGaraje());
        }
    }

    //mostrar menus y mensajes a traves de la interfaz grafica

    public void mostrarMenuPrincipal() {
        datosJugador = new int[] {partida.diaActual(), partida.dinero(), partida.garaje().obtenerCreditos()};
        Interfaz.getInstancia().mostrarMenuPrincipal(partida.nombre(), datosJugador);
    }

    public void mostrarMenuGaraje() {
        datosJugador = new int[] {partida.diaActual(), partida.dinero(), partida.garaje().obtenerCreditos()};
        Interfaz.getInstancia().mostrarMenuGaraje(partida.nombre(), datosJugador);
    }

    public void mostrarMensaje(String msg, Runnable onContinuar) {
        Interfaz.getInstancia().mostrarMensaje(msg, onContinuar);
    }

    public void mostrarMenuDificultad() {
        datosJugador = new int[] {partida.diaActual(), partida.dinero(), partida.garaje().obtenerCreditos()};
        Interfaz.getInstancia().mostrarMenuDificultad(partida.nombre(), datosJugador);
    }

    public void mostrarVehiculosPermitidos(Mision mision) {
        datosJugador = new int[] {partida.diaActual(), partida.dinero(), partida.garaje().obtenerCreditos()};
        Interfaz.getInstancia().mostrarVehiculosPermitidos(mision, partida.nombre(), datosJugador);
    }

    public void iniciarMision(Mision mision) {
        Interfaz.getInstancia().iniciarMision(mision);
    }

    public void mostrarMenuContinuarJugando() {
        datosJugador = new int[] {partida.diaActual(), partida.dinero(), partida.garaje().obtenerCreditos()};
        Interfaz.getInstancia().mostrarMenuContinuarJugando(partida.nombre(), datosJugador);
    }

    public void mostrarFormularioCreditosAgregados(Campo[] campos) {
        Interfaz.getInstancia().mostrarFormulario(
            campos,
            this::procesarCreditosAgregados
        );
    }

    public void mostrarIngresoNombreJugador(Campo[] campos) {
        Interfaz.getInstancia().mostrarFormulario(
            campos,
             this::procesarNombreJugador
        );
    }

    public void mostrarCargaPorIndice(Campo[] campos) {
        Interfaz.getInstancia().mostrarFormulario(
            campos,
            this::procesarCargaPorIndice
        );
    }

	/** Mostrar informacion de Vehiculos en Garaje
	 *  @param garaje Garaje que contiene vehiculos a mostrar su informacion 
	 */
	public void mostrarTodosLosVehiculos(Runnable callback){
		String autos = garaje.obtenerVehiculosToString();
		if (autos.isEmpty()) {
			mostrarMensaje("No hay vehículos en el garaje.", () -> mostrarMenuGaraje());
		} else {
			mostrarMensaje("Vehículos en el garaje:\n" + autos,
				() -> callback.run());	
		}
	}

    public void mostrarFormularioEliminar(Campo[] campos) {
        Interfaz.getInstancia().mostrarFormulario(
            campos,
            this::procesarEliminacion
        );
    }

    //obtener atributos necesarios

    public Garaje garaje() {
        return garaje;
    }

    public Partida partida() {
        return partida;
    }

    public void cerrar() {
        Interfaz.cerrar();
    }
}

