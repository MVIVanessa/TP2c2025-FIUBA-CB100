package org.ayed.programaPrincipal.interfaz;

import java.util.function.Consumer;

import org.ayed.gta.Misiones.Mision;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Interfaz extends Application {

    private static Interfaz instancia;
    private Stage stage;
    private Controlador controlador;
    private misiones pantallaMisiones;
    private Scene scene;
    private ObtenerDatos pantallaObtenerDatos;

    public static Interfaz getInstancia() {
        return instancia;
    }

    @Override
    public void start(Stage stage) {
        instancia = this;
        this.stage = stage;

        stage.setTitle("GTA - Interfaz Gráfica");

        BorderPane root = new BorderPane();
        root.setPrefSize(Double.MAX_VALUE, Double.MAX_VALUE);

        scene = new Scene(root);

        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();

        controlador = new Controlador();
        controlador.iniciar();
    }


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
            opcion -> { controlador.procesarSeleccionVehiculo(opcion); },
            nombreJugador,
            datosJugador
            
        );

        scene.setOnKeyPressed(menu::manejarTeclas);
        cambiarPantalla(menu.getRoot());
    }

    public void mostrarMensaje(String mensaje, Runnable onContinuar) {
        VentanaMensaje ventana = new VentanaMensaje(mensaje, onContinuar);
        scene.setOnKeyPressed(ventana::manejarTeclas);
        cambiarPantalla(ventana.getRoot());
    }

    public void iniciarMision(Mision mision) {
        pantallaMisiones = new misiones();
        pantallaMisiones.establecerMision(mision, controlador);

        pantallaMisiones.setOnFinMision(completada -> {
            controlador.procesarMisionFinalizada(completada);
        });

        scene.setOnKeyPressed(pantallaMisiones::manejarTeclas);
        cambiarPantalla(pantallaMisiones.getRoot());
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

    public void mostrarFormulario(Campo[] camposDefinidos, Consumer<String[]> onConfirmar) {

        pantallaObtenerDatos = new ObtenerDatos(camposDefinidos);

        pantallaObtenerDatos.setOnConfirmar(() -> {
            String[] datos = pantallaObtenerDatos.getDatosObtenidos();
            onConfirmar.accept(datos);
        });

        cambiarPantalla(pantallaObtenerDatos.getRoot());
    }

    public void cambiarPantalla(Pane nuevoRoot) {
        nuevoRoot.setPrefSize(Double.MAX_VALUE, Double.MAX_VALUE);
        scene.setRoot(nuevoRoot);
    }

    public static void lanzar(String[] args) {
        launch(args);
    }

    public static void cerrar() {
        if (instancia != null && instancia.stage != null) {
            instancia.stage.close();
        }
    }
}
