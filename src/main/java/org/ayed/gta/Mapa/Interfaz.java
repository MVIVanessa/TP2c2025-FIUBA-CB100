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
    private Mapa mapa;
    private Gps gps;
    private GridPane gridPane;

    @Override
    public void start(Stage stage) {
        mapa = new Mapa();
        gps = new Gps(mapa);

        gridPane = new GridPane();
        dibujarMapa();

        Scene scene = new Scene(gridPane);
        stage.setScene(scene);
        stage.show();
    }

    private void dibujarMapa() {
        Lista<Lista<TipoCelda>> grilla = mapa.obtenerMapa();
        Iterador<Lista<TipoCelda>> recorridoFilas = grilla.iterador();

        int filaIndice = 0;
        while (recorridoFilas.haySiguiente()) {

            Iterador<TipoCelda> recorridoColumnas = recorridoFilas.dato().iterador();
            int columnaIndice = 0;
            while (recorridoColumnas.haySiguiente()) {
                TipoCelda tipo = recorridoColumnas.dato();
                Coordenadas coordenada = new Coordenadas(filaIndice, columnaIndice);

                Rectangle rect = new Rectangle(TAM_CELDA, TAM_CELDA);
                rect.setFill(colorPara(tipo, coordenada));
                rect.setStroke(Color.LIGHTGRAY);

                gridPane.add(rect, columnaIndice, filaIndice);
                recorridoColumnas.siguiente();
                columnaIndice++;
            }
            recorridoFilas.siguiente();
            filaIndice++;
        }
    }


    private Color colorPara(TipoCelda tipo, Coordenadas coordenada) {
        if ((tipo != TipoCelda.RECOMPENSA && tipo != TipoCelda.ENTRADA && tipo != TipoCelda.SALIDA)
                && gps.buscarCoordenadas(coordenada)) {
            return Color.YELLOW;}
    
        switch (tipo) {
            case TRANSITABLE:   return Color.WHITE;
            case EDIFICIO:      return Color.BLACK;
            case CONGESTIONADA: return Color.RED;
            case ENTRADA:       return Color.BLUE;
            case SALIDA:        return Color.GREEN;
            case RECOMPENSA:    return Color.MAGENTA;
            default:            return Color.GRAY;
        }
    }
    

    // Llamar cada vez que el jugador se mueve
    public void actualizarPosicionJugador(Coordenadas nuevaPosicion) {
        gps.modificarPartida(nuevaPosicion);
        gridPane.getChildren().clear(); // Limpia
        dibujarMapa(); // Redibuja el mapa
    }
    public static void main(String[] args) {
        launch();
    }
}
