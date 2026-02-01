package org.ayed.gta.Mapa;

import org.ayed.gta.Misiones.Mision;
import org.ayed.gta.Partida;
import org.ayed.gta.Misiones.ExcepcionMision;
import org.ayed.tda.iterador.Iterador;
import org.ayed.tda.lista.Lista;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class Interfaz extends Application {

    private static Interfaz instancia;

    private Mapa mapa;
    private Gps gps;
    private GridPane gridPane;
    private Mision mision;
    private Coordenadas jugador;
    private static Partida partida;

    public Interfaz() {
        instancia = this;
    }

    public static Interfaz getInstancia() {
        return instancia;
    }

    @Override
    public void start(Stage stage) {
        gridPane = new GridPane();
        Scene scene = new Scene(gridPane);
        stage.setScene(scene);
        stage.setTitle("Mapa GTA - JavaFX");
        stage.show();

        scene.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if (mision == null) return;

            String comando = event.getCode().toString();
            if ("WASDC".contains(comando)) {
                try {
                    mision.moverJugador(comando, null);
                    jugador = mision.obtenerPosicionJugador();
                    dibujarMapa();
                } catch (ExcepcionMision e) {
                    System.out.println(e.getMessage());
                }
            }
        });
    }


    public static void setPartida(Partida p) {
        partida = p;
    }


    public void setMision(Mision nuevaMision) {
        this.mision = nuevaMision;
        this.mapa = nuevaMision.obtenerMapa();
        this.gps = nuevaMision.obtenerGps();
        this.jugador = nuevaMision.obtenerPosicionJugador();
        dibujarMapa();
    }

    private void dibujarMapa() {
        if (mapa == null) return;
        gridPane.getChildren().clear();

        Lista<Lista<TipoCelda>> grilla = mapa.obtenerMapa();
        Iterador<Lista<TipoCelda>> filas = grilla.iterador();
        int fila = 0;

        while (filas.haySiguiente()) {
            Iterador<TipoCelda> columnas = filas.dato().iterador();
            int col = 0;
            while (columnas.haySiguiente()) {
                TipoCelda tipo = columnas.dato();
                Coordenadas coord = new Coordenadas(fila, col);
                Rectangle rect = new Rectangle(35, 35);
                rect.setFill(colorPara(tipo, coord));
                rect.setStroke(Color.LIGHTGRAY);
                gridPane.add(rect, col, fila);
                columnas.siguiente();
                col++;
            }
            filas.siguiente();
            fila++;
        }
    }

    private Color colorPara(TipoCelda tipo, Coordenadas coord) {
        if (jugador != null && coord.compararCoordenadas(jugador)) return Color.BLACK;
        if (gps != null && tipo != TipoCelda.RECOMPENSA && tipo != TipoCelda.ENTRADA && tipo != TipoCelda.SALIDA
                && gps.buscarCoordenadas(coord)) return Color.YELLOW;

        switch (tipo) {
            case TRANSITABLE: return Color.WHITE;
            case EDIFICIO: return Color.DARKGRAY;
            case CONGESTIONADA: return Color.RED;
            case ENTRADA: return Color.BLUE;
            case SALIDA: return Color.GREEN;
            case RECOMPENSA: return Color.MAGENTA;
            default: return Color.LIGHTGRAY;
        }
    }

    public static void lanzar(String[] args) {
        launch(args);
    }
}
