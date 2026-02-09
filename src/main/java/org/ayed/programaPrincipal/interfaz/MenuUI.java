package org.ayed.programaPrincipal.interfaz;

import java.util.function.Consumer;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class MenuUI {

    private final VBox root;
    private final Label[] opciones;
    private int seleccion = 0;
    private final Consumer<Integer> onSeleccion;

    public MenuUI(String titulo, String[] textos, Consumer<Integer> onSeleccion) {
        this.onSeleccion = onSeleccion;

        root = new VBox(10);
        root.setAlignment(Pos.CENTER);

        Label lblTitulo = new Label(titulo);
        lblTitulo.setTextFill(Color.GOLD);
        lblTitulo.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
        root.getChildren().add(lblTitulo);

        opciones = new Label[textos.length];

        for (int i = 0; i < textos.length; i++) {
            Label l = new Label(textos[i]);
            l.setTextFill(Color.BLACK);
            opciones[i] = l;
            root.getChildren().add(l);
        }

        actualizarSeleccion();
    }

    public void manejarTeclas(KeyEvent e) {
        if (opciones.length == 0) return;
        switch (e.getCode()) {
            case W : mover(-1); break;
            case S : mover(1); break;
            case ENTER : {
                if (onSeleccion != null) {
                    onSeleccion.accept(seleccion + 1);
                }
            } break;
            default : {}
        }
    }

    private void mover(int delta) {
        if (opciones.length == 0) return;
        seleccion = (seleccion + delta + opciones.length) % opciones.length;
        actualizarSeleccion();
    }

    private void actualizarSeleccion() {
        for (int i = 0; i < opciones.length; i++) {
            opciones[i].setTextFill(
                i == seleccion ? Color.ROSYBROWN : Color.BLACK
            );
        }
    }
    
    public Pane getRoot() {
        return root;
    }
}