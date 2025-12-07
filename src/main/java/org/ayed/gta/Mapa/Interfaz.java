package org.ayed.gta.Mapa;

import org.ayed.tda.iterador.Iterador;
import org.ayed.tda.lista.Lista;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class Interfaz extends Application {

    private static final int TAM_CELDA = 35;

    @Override
    public void start(Stage stage) {
        Mapa mapa = new Mapa();
        Lista<Lista<TipoCelda>> grilla = mapa.obtenerMapa();

        GridPane gridPane = new GridPane();
        Iterador<Lista<TipoCelda>> recorridoFilas = grilla.iterador();

        int filaIndice = 0;
        while (recorridoFilas.haySiguiente()) {

            Iterador<TipoCelda> recorridoColumnas = recorridoFilas.dato().iterador();
            int columnaIndice = 0;
            while (recorridoColumnas.haySiguiente()) {
                TipoCelda tipo = recorridoColumnas.dato();

                Rectangle rect = new Rectangle(TAM_CELDA, TAM_CELDA);
                rect.setFill(colorPara(tipo));
                rect.setStroke(Color.LIGHTGRAY);

                gridPane.add(rect, columnaIndice, filaIndice);
                recorridoColumnas.siguiente();
                columnaIndice++;
            }
            recorridoFilas.siguiente();
            filaIndice++;
        }

        Scene scene = new Scene(gridPane);
        stage.setTitle("Mapa GTA - JavaFX");
        stage.setScene(scene);
        stage.show();
    }

    private Color colorPara(TipoCelda tipo) {
        switch (tipo) {
            case TRANSITABLE:   return Color.WHITE;
            case EDIFICIO:      return Color.BLACK;
            case CONGESTIONADA: return Color.RED;
            case ENTRADA:       return Color.BLUE;
            case SALIDA:        return Color.GREEN;
            case RECOMPENSA:    return Color.MAGENTA;
            //case GPS:           return Color.YELLOW;
            default:            return Color.GRAY;
        }
    }
    public static void main(String[] args) {
        launch();
    }
}
