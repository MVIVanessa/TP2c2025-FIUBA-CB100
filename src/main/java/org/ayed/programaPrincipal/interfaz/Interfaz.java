package org.ayed.programaPrincipal.interfaz;

import org.ayed.gta.Garaje.Garaje;
import org.ayed.gta.Misiones.Mision;

import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Interfaz extends Application {

    private static Interfaz instancia;
    private Stage stage;
    private Controlador controlador;
    private Scene escenaMenuPrincipal;
    private Scene escenaMisiones;
 misiones pantallaMisiones = new misiones();


    public static Interfaz getInstancia() {
        return instancia;
    }

    @Override
    public void start(Stage stage) {
        instancia = this;
        this.stage = stage;

        stage.setTitle("GTA - Interfaz Gráfica");
        controlador = new Controlador();
        controlador.iniciar();

        stage.show();
    }


    public void mostrarMenuPrincipal(Controlador controller) {
        MenuUI menu = new MenuUI(
            "Menu Principal",
            opcionesMenus.opcionesPrincipal,
            controller::procesarMenuPrincipal
        );

        Scene scene = new Scene(menu.getRoot(), 600, 400);
        scene.setOnKeyPressed(menu::manejarTeclas);
        stage.setScene(scene);
    }

    public void mostrarMenuGaraje(Controlador controller, Garaje garaje) {
        MenuUI menu = new MenuUI(
            "Menu Garaje",
            opcionesMenus.opcionesGaraje,
            opcion -> controller.procesarMenuGaraje(opcion, garaje)
        );

        Scene scene = new Scene(menu.getRoot(), 600, 400);
        scene.setOnKeyPressed(menu::manejarTeclas);
        stage.setScene(scene);
    }

    public void mostrarMenuDificultad(Controlador controller) {
        MenuUI menu = new MenuUI(
            "Seleccionar Dificultad",
            opcionesMenus.opcionesDificultad,
            controller::procesarMenuDificultad
            
        );

        Scene scene = new Scene(menu.getRoot(), 600, 400);
        scene.setOnKeyPressed(menu::manejarTeclas);
        stage.setScene(scene);
    }

    public void mostrarVehiculosPermitidos(Mision mision, Controlador controlador) {
        MenuUI menu = new MenuUI(
            "Vehículos Permitidos",
            mision.obtenerVehiculosPermitidos(),
            opcion -> { controlador.procesarSeleccionVehiculo(opcion);
            }
        );

        Scene scene = new Scene(menu.getRoot(), 600, 400);
        scene.setOnKeyPressed(menu::manejarTeclas);
        stage.setScene(scene);
    }

    public void mostrarMensaje(String mensaje, TipoMenu menuAnterior, Controlador controlador) {
        VentanaMensaje ventana = new VentanaMensaje(mensaje, menuAnterior, controlador);
        Scene scene = new Scene(ventana.getRoot(), 400, 200);
        scene.setOnKeyPressed(ventana::manejarTeclas);
        stage.setScene(scene);
    }

    public void iniciarMision(Mision mision) {
        pantallaMisiones.establecerMision(mision, controlador);

        escenaMisiones = new Scene(pantallaMisiones.getRoot(), 800, 600);
        escenaMisiones.setOnKeyPressed(pantallaMisiones::manejarTeclas);

        stage.setScene(escenaMisiones);
    }

    public void mostrarResultadoMision(String resultado) {

        pantallaMisiones.mostrarResultado(resultado);

        PauseTransition pausa = new PauseTransition(Duration.seconds(2));
        pausa.setOnFinished(e -> {
            pantallaMisiones.limpiarMision();
            mostrarMenuPrincipal(controlador);
        });
        pausa.play();
    }


    public static void lanzar(String[] args) {
        launch(args);
    }
}
