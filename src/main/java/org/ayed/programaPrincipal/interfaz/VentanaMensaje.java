package org.ayed.programaPrincipal.interfaz;


import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class VentanaMensaje {

    private final String mensaje;
    private final Runnable onContinuar;

    public VentanaMensaje(String mensaje, TipoMenu menuAnterior, Controlador controlador) {
        this.mensaje = mensaje;
        this.onContinuar = () -> {
            switch (menuAnterior) {
                case PRINCIPAL:
                    controlador.mostrarMenuPrincipal();
                    break;
                case GARAJE:
                    controlador.mostrarMenuGaraje();
                    break;
            }
        };
    }

    public Pane getRoot() {
        VBox root = new VBox(10);
        root.setAlignment(Pos.CENTER);

        Label lblMensaje = new Label(mensaje);
        lblMensaje.setTextFill(Color.BLACK);
        lblMensaje.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        Label lblInstruccion = new Label("Presione ENTER para continuar");
        lblInstruccion.setTextFill(Color.GRAY);

        root.getChildren().addAll(lblMensaje, lblInstruccion);
        return root;
    }

    public void manejarTeclas(KeyEvent e) {
        if (e.getCode() == javafx.scene.input.KeyCode.ENTER && onContinuar != null) {
            onContinuar.run();
        }
    }
}
