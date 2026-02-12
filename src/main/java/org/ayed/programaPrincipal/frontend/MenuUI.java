package org.ayed.programaPrincipal.frontend;

import java.util.function.Consumer;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class MenuUI {

    private final BorderPane root;
    private final VBox contenido;
    private final Label[] opciones;
    private int seleccion = 0;
    private final Consumer<Integer> onSeleccion;

    /**
     * Constructor de la clase MenuUI.
     * @param titulo
     * @param textos Las opciones del menú.
     * @param onSeleccion Funcion que se ejecuta luego de que el usuario seleccione una opción.
     * @param nombreJugador
     * @param datosJugador
     */
    public MenuUI(
        String titulo,
        String[] textos,
        Consumer<Integer> onSeleccion,
        String nombreJugador,
        int[] datosJugador
    ) {
        this.onSeleccion = onSeleccion;

        root = new BorderPane();

        //  BARRA SUPERIOR
        root.setTop(crearBarraSuperior(
            nombreJugador,
            datosJugador[0],
            datosJugador[1],
            datosJugador[2]
        ));

        // CONTENIDO CENTRAL (MENÚ)
        contenido = new VBox(10);
        contenido.setAlignment(Pos.CENTER);

        Label lblTitulo = new Label(titulo);
        lblTitulo.setTextFill(Color.GOLD);
        lblTitulo.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
        contenido.getChildren().add(lblTitulo);

        opciones = new Label[textos.length];

        for (int i = 0; i < textos.length; i++) {
            Label l = new Label(textos[i]);
            l.setTextFill(Color.BLACK);
            l.setStyle("-fx-font-size: 16px;");
            opciones[i] = l;
            contenido.getChildren().add(l);
        }

        root.setCenter(contenido);
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
            default: break;
        }
    }

    // ----------------------- MÉTODOS AUXILIARES -------------------------

    private Pane crearBarraSuperior(
        String nombreJugador,
        int dia,
        int dinero,
        int creditos
    ) {
        HBox barra = new HBox(30);
        barra.setAlignment(Pos.CENTER_LEFT);
        barra.setStyle("-fx-padding: 10px; -fx-background-color: #2b2b2b;");

        Label lblJugador = new Label("Jugador: " + nombreJugador);
        Label lblDia = new Label("Día: " + dia);
        Label lblDinero = new Label("Dinero: $" + dinero);
        Label lblCreditos = new Label("Créditos: " + creditos);

        for (Label l : new Label[]{ lblJugador, lblDia, lblDinero, lblCreditos }) {
            l.setTextFill(Color.WHITE);
            l.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
        }

        barra.getChildren().addAll(
            lblJugador,
            lblDia,
            lblDinero,
            lblCreditos
        );

        return barra;
    }

    private void mover(int delta) {
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

    // ----------------------- GETTERS -------------------------
    
    public Pane getRoot() {
        return root;
    }
}