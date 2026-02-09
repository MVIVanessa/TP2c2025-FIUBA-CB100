package org.ayed.programaPrincipal.interfaz;

import java.util.function.Consumer;

import org.ayed.gta.Garaje.Garaje;
import org.ayed.gta.Misiones.Mision;

import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

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
        mostrarMenuPrincipal(controlador);
    }


    public void mostrarMenuPrincipal(Controlador controller) {
        MenuUI menu = new MenuUI(
            "Menu Principal",
            opcionesMenus.opcionesPrincipal,
            controller::procesarMenuPrincipal
        );

        scene.setOnKeyPressed(menu::manejarTeclas);
        cambiarPantalla(menu.getRoot());
    }

    public void mostrarMenuGaraje(Controlador controller, Garaje garaje) {
        MenuUI menu = new MenuUI(
            "Menu Garaje",
            opcionesMenus.opcionesGaraje,
            opcion -> controller.procesarMenuGaraje(opcion, garaje)
        );

        scene.setOnKeyPressed(menu::manejarTeclas);
        cambiarPantalla(menu.getRoot());
    }

    public void mostrarMenuDificultad(Controlador controller) {
        MenuUI menu = new MenuUI(
            "Seleccionar Dificultad",
            opcionesMenus.opcionesDificultad,
            controller::procesarMenuDificultad
            
        );

        scene.setOnKeyPressed(menu::manejarTeclas);
        cambiarPantalla(menu.getRoot());
    }

    public void mostrarVehiculosPermitidos(Mision mision, Controlador controlador) {
        MenuUI menu = new MenuUI(
            "Vehículos Permitidos",
            mision.obtenerVehiculosPermitidos(),
            opcion -> { controlador.procesarSeleccionVehiculo(opcion);
            }
        );

        scene.setOnKeyPressed(menu::manejarTeclas);
        cambiarPantalla(menu.getRoot());
    }

    public void mostrarMensaje(String mensaje, TipoMenu menuAnterior, Controlador controlador) {
        VentanaMensaje ventana = new VentanaMensaje(mensaje, menuAnterior, controlador);
        scene.setOnKeyPressed(ventana::manejarTeclas);
        cambiarPantalla(ventana.getRoot());
    }

    public void iniciarMision(Mision mision) {
        pantallaMisiones = new misiones();
        pantallaMisiones.establecerMision(mision, controlador);

        scene.setOnKeyPressed(pantallaMisiones::manejarTeclas);
        cambiarPantalla(pantallaMisiones.getRoot());
    }

    public void mostrarResultadoMision(String resultado) {
        pantallaMisiones.mostrarResultado(resultado);

        PauseTransition pausa = new PauseTransition(Duration.seconds(2));
        pausa.setOnFinished(e -> {
            pantallaMisiones = null;
            mostrarMenuPrincipal(controlador);
        });
        pausa.play();
    }

    public void mostrarMenuContinuarJugando(Controlador controlador) {
        MenuUI menu = new MenuUI(
            "¿Desea continuar jugando?",
            opcionesMenus.opcionesContinuarJugando,
            opcion -> controlador.procesarMenuContinuarJugando(opcion)
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
}
