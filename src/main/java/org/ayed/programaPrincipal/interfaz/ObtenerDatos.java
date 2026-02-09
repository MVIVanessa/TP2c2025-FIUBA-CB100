package org.ayed.programaPrincipal.interfaz;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class ObtenerDatos {

    private final Campo[] camposDefinidos;
    private final TextField[] camposUI;
    private String[] datosObtenidos;
    private Runnable onConfirmar;

    public ObtenerDatos(Campo[] camposDefinidos) {
        this.camposDefinidos = camposDefinidos;
        this.camposUI = new TextField[camposDefinidos.length];
    }

    public Pane getRoot() {
        BorderPane root = new BorderPane();
        root.setPrefSize(Double.MAX_VALUE, Double.MAX_VALUE);

        VBox contenido = new VBox(10);
        contenido.setAlignment(Pos.CENTER);

        Label titulo = new Label("Ingrese los datos requeridos");
        titulo.setTextFill(Color.GOLD);
        titulo.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
        contenido.getChildren().add(titulo);

        for (int i = 0; i < camposDefinidos.length; i++) {
            Label lbl = new Label(camposDefinidos[i].nombre);
            TextField tf = new TextField();
            if (camposDefinidos[i].tipo == TipoCampo.ENTERO || camposDefinidos[i].tipo == TipoCampo.DECIMAL) {
                tf.setMaxWidth(200);
            } else {
                tf.setMaxWidth(400);
            }

            tf.setOnKeyPressed(e -> { 
                if (e.getCode() == KeyCode.ENTER) confirmar();});

            camposUI[i] = tf;

            contenido.getChildren().addAll(lbl, tf);
            
        }

        contenido.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) {
                confirmar();
            }
        });

        root.setCenter(contenido);
        return root;
    }

    private void confirmar() {
        datosObtenidos = new String[camposUI.length];

        for (int i = 0; i < camposUI.length; i++) {
            TextField tf = camposUI[i];
            String valor = tf.getText();

            tf.setStyle("");

            if (!validar(valor, camposDefinidos[i].tipo)) {
                marcarError(tf);
                return;
            }

            datosObtenidos[i] = valor;
        }

        if (onConfirmar != null) {
            onConfirmar.run();
        }
    }

    private boolean validar(String valor, TipoCampo tipo) {
        try {
            switch (tipo) {
                case TEXTO:
                    return !valor.isBlank();
                case ENTERO:
                    Integer.valueOf(valor);
                    return true;
                case DECIMAL:
                    Double.valueOf(valor);
                    return true;
            }
        } catch (NumberFormatException e) {
            return false;
        }
        return false;
    }

    private void marcarError(TextField tf) {
        tf.setStyle("-fx-border-color: red;");
        tf.requestFocus();
    }

    public void setOnConfirmar(Runnable onConfirmar) {
        this.onConfirmar = onConfirmar;
    }

    public String[] getDatosObtenidos() {
        return datosObtenidos;
    }
}