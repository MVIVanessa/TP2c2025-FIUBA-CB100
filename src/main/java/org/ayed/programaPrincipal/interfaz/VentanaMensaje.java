package org.ayed.programaPrincipal.interfaz;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class VentanaMensaje {

    private final String mensaje;
    private final Runnable onContinuar;

    public VentanaMensaje(String mensaje, Runnable onContinuar) {
        this.mensaje = mensaje;
        this.onContinuar = onContinuar;
    }

    public Pane getRoot() {
        BorderPane root = new BorderPane();
        VBox contenido = new VBox(10);
        contenido.setAlignment(Pos.CENTER);

        Label lblMensaje = new Label(mensaje);
        lblMensaje.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        Label lblInstruccion = new Label("Presione ENTER para continuar");

        contenido.getChildren().addAll(lblMensaje, lblInstruccion);
        root.setCenter(contenido);
        return root;
    }

    public void manejarTeclas(KeyEvent e) {
        if (e.getCode() == javafx.scene.input.KeyCode.ENTER && onContinuar != null) {
            onContinuar.run(); }
    }
}