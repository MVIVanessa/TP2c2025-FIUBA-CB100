package org.ayed.programaPrincipal.frontend;

import java.util.function.Consumer;

import org.ayed.gta.Misiones.Mision;
import org.ayed.programaPrincipal.aplicacion.Controlador;
import org.ayed.programaPrincipal.frontend.formulario.Campo;
import org.ayed.programaPrincipal.frontend.formulario.FormularioEntrada;
import org.ayed.programaPrincipal.frontend.utilidades.opcionesMenus;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Interfaz extends Application {

    private static Interfaz instancia;
    private Stage stage;
    private Controlador controlador;
    private PantallaMision pantallaMisiones;
    private Scene scene;
    private FormularioEntrada pantallaObtenerDatos;

    public static Interfaz getInstancia() {
        return instancia;
    }

    /**
     * Inicia la aplicación JavaFX y muestra la ventana principal. 
     */
    @Override
    public void start(Stage stage) {
        instancia = this;
        this.stage = stage;

        stage.setTitle("GTA VI - Conduciendo Por La Ciudad");

        BorderPane root = new BorderPane();
        root.setPrefSize(Double.MAX_VALUE, Double.MAX_VALUE);

        scene = new Scene(root);

        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();

        controlador = new Controlador();
        controlador.iniciar();
    }

    // ---------------------------- MÉTODOS PARA MOSTRAR PANTALLAS ----------------------------

    // ------------------------ MENÚS -------------------------
    
    /**
     * Construye el menú principal y lo muestra.
     * Luego pasa al controlador la opción seleccionada.
     */
    public void mostrarMenuPrincipal(String nombreJugador, int[] datosJugador) {
        MenuUI menu = new MenuUI(
            "Menu Principal",
            opcionesMenus.opcionesPrincipal,
            controlador::procesarMenuPrincipal,
            nombreJugador,
            datosJugador
        );

        scene.setOnKeyPressed(menu::manejarTeclas);
        cambiarPantalla(menu.getRoot());
    }

    public void mostrarMenuGaraje(String nombreJugador, int[] datosJugador) {
        MenuUI menu = new MenuUI(
            "Menu Garaje",
            opcionesMenus.opcionesGaraje,
            opcion -> controlador.procesarMenuGaraje(opcion),
            nombreJugador,
            datosJugador
        );

        scene.setOnKeyPressed(menu::manejarTeclas);
        cambiarPantalla(menu.getRoot());
    }

    public void mostrarMenuDificultad(String nombreJugador, int[] datosJugador) {
        MenuUI menu = new MenuUI(
            "Seleccionar Dificultad",
            opcionesMenus.opcionesDificultad,
            controlador::procesarMenuDificultad,
            nombreJugador,
            datosJugador
        );

        scene.setOnKeyPressed(menu::manejarTeclas);
        cambiarPantalla(menu.getRoot());
    }

    public void mostrarVehiculosPermitidos(Mision mision, String nombreJugador, int[] datosJugador) {
        MenuUI menu = new MenuUI(
            "Vehículos Permitidos",
            mision.obtenerVehiculosPermitidos(),
            opcion ->  controlador.procesarSeleccionVehiculo(opcion),
            nombreJugador,
            datosJugador
            
        );

        scene.setOnKeyPressed(menu::manejarTeclas);
        cambiarPantalla(menu.getRoot());
    }

    public void mostrarMenuContinuarJugando(String nombreJugador, int[] datosJugador) {
        MenuUI menu = new MenuUI(
            "¿Desea continuar jugando?",
            opcionesMenus.opcionesContinuarJugando,
            opcion -> controlador.procesarMenuContinuarJugando(opcion),
            nombreJugador,
            datosJugador
        );

        scene.setOnKeyPressed(menu::manejarTeclas);
        cambiarPantalla(menu.getRoot());
    }

    public void mostrarMenuConcesionario(String nombreJugador, int[] datosJugador) {
        MenuUI menu = new MenuUI(
            "Concesionario",
            opcionesMenus.opcionesConcesionario,
            opcion -> controlador.procesarMenuConcesionario(opcion),
            nombreJugador,
            datosJugador
        );

        scene.setOnKeyPressed(menu::manejarTeclas);
        cambiarPantalla(menu.getRoot());
    }

    public void mostrarMenuCompraConcesionario(String nombreJugador, int[] datosJugador, String[] vehiculos) {
        MenuUI menu = new MenuUI(
            "Autos disponibles para compra",
            vehiculos,
            opcion -> controlador.procesarCompraVehiculo(opcion),
            nombreJugador,
            datosJugador
        );

        scene.setOnKeyPressed(menu::manejarTeclas);
        cambiarPantalla(menu.getRoot());
    }

    // ------------------------ PANTALLAS DE INFORMACIÓN Y FORMULARIOS -------------------------

    /**
     * Muestra una ventana emergente con el mensaje especificado.
     * Al final ejecuta la acción proporcionada.
     */
    public void mostrarMensaje(String mensaje, Runnable onContinuar) {
        VentanaMensaje ventana = new VentanaMensaje(mensaje, onContinuar);
        scene.setOnKeyPressed(ventana::manejarTeclas);
        cambiarPantalla(ventana.getRoot());
    }

    /**
     * Inicia la pantalla de misión.
     * Al terminar avisa al controlador si la misión fue completada o no.
     */
    public void iniciarMision(Mision mision) {
        pantallaMisiones = new PantallaMision();
        pantallaMisiones.establecerMision(mision, controlador);

        pantallaMisiones.setOnFinMision(completada -> {
            controlador.procesarMisionFinalizada(completada);
        });

        scene.setOnKeyPressed(pantallaMisiones::manejarTeclas);
        cambiarPantalla(pantallaMisiones.getRoot());
    }

    /**
     * Muestra un formulario para ingresar datos.
     * Al confirmar los valida y luego pasa los datos ingresados al controlador.
     */
    public void mostrarFormulario(Campo[] camposDefinidos, Consumer<String[]> onConfirmar) {

        pantallaObtenerDatos = new FormularioEntrada(camposDefinidos);

        pantallaObtenerDatos.setOnConfirmar(() -> {
            String[] datos = pantallaObtenerDatos.obtenerDatosObtenidos();
            onConfirmar.accept(datos);
        });

        cambiarPantalla(pantallaObtenerDatos.getRoot());
    }

    // ============================= MÉTODOS DE CONTROL DE LA APLICACIÓN =============================

    public static void lanzar(String[] args) {
        launch(args);
    }

    public static void cerrar() {
        if (instancia != null && instancia.stage != null) {
            instancia.stage.close();
        }
    }

    // ----------------------- MÉTODOS AUXILIARES -------------------------

    private void cambiarPantalla(Pane nuevoRoot) {
        nuevoRoot.setPrefSize(Double.MAX_VALUE, Double.MAX_VALUE);
        scene.setRoot(nuevoRoot);
    }

}
