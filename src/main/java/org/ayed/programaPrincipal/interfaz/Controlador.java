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

    public Controlador() {
        this.garaje = new Garaje();
        this.partida = new Partida(garaje);
        menuPartida = new MenuPartida(partida);
        menuGaraje = new MenuGaraje(partida.nombre(), partida.garaje());
    }

    public void iniciar() {
        Interfaz.getInstancia().mostrarMenuPrincipal(this);
    }

    //procesar opciones de los menus (redirecciona a la clase correspondiente que maneja la logica). Uso "interno".

    public void procesarMenuPrincipal(int opcion) {
        menuPartida.procesarOpcion(opcion, this);
    }

    public void procesarMenuGaraje(int opcion, Garaje garaje) {
        menuGaraje.procesarOpcion(opcion, garaje, this);
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


    //mostrar menus y mensajes a traves de la interfaz grafica

    public void mostrarMenuPrincipal() {
        Interfaz.getInstancia().mostrarMenuPrincipal(this);
    }

    public void mostrarMenuGaraje() {
        Interfaz.getInstancia().mostrarMenuGaraje(this, garaje);
    }

    public void mostrarMensaje(String msg, TipoMenu tipo) {
        Interfaz.getInstancia().mostrarMensaje(msg, tipo, this);
    }

    public void mostrarMenuDificultad() {
        Interfaz.getInstancia().mostrarMenuDificultad(this);
    }

    public void mostrarVehiculosPermitidos(Mision mision) {
        Interfaz.getInstancia().mostrarVehiculosPermitidos(mision, this);
    }

    public void iniciarMision(Mision mision) {
        Interfaz.getInstancia().iniciarMision(mision);
    }

    public void mostrarResultadoMision(String resultado) {
        System.out.println("MOSTRANDO RESULTADO");
        Interfaz.getInstancia().mostrarResultadoMision(resultado);
    }

    //obtener atributos necesarios

    public Garaje garaje() {
        return garaje;
    }

    public Partida partida() {
        return partida;
    }
}

