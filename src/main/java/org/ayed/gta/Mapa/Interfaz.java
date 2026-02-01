package org.ayed.gta.Mapa;

import org.ayed.gta.Misiones.ExcepcionMision;
import org.ayed.gta.Misiones.Mision;
import org.ayed.tda.iterador.Iterador;
import org.ayed.tda.lista.Lista;

import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Interfaz extends Application {

    private static Interfaz instancia;

    private Mapa mapa;
    private Gps gps;
    private GridPane gridPane;
    private Mision mision;
    private Coordenadas jugador;
    private Label mensajeEspera;
    private BorderPane root;
    private Label labelTiempo;
    private Label labelGasolina;

    public Interfaz() {
        instancia = this;
    }

    public static Interfaz getInstancia() {
        return instancia;
    }

    @Override
    public void start(Stage stage) {
        root = new BorderPane();

        //----- MAPA -----//
        gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);

        mensajeEspera = new Label("Esperando misión :)");
        mensajeEspera.setStyle("-fx-font-size: 24px; -fx-text-fill: gray;");
        gridPane.add(mensajeEspera, 0, 0);

        root.setCenter(gridPane);

        //----- HUD (arriba derecha) -----//
        VBox hud = crearHUD();
        root.setTop(hud);
        BorderPane.setAlignment(hud, Pos.TOP_RIGHT);

        //----- GLOSARIO (izquierda) -----//
        VBox glosario = crearGlosario();
        root.setLeft(glosario);

        Scene scene = new Scene(root, 800, 600);
        stage.setScene(scene);
        stage.setTitle("Mapa GTA - JavaFX");
        stage.show();

        //----- CONTROLES -----//
        scene.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if (mision == null) return;

            String comando = event.getCode().toString();
            if ("WASDC".contains(comando)) {
                try {
                    mision.moverJugador(comando, null);
                    jugador = mision.obtenerPosicionJugador();
                    actualizarHUD();
                    dibujarMapa();
                } catch (ExcepcionMision e) {
                    System.out.println(e.getMessage());
                }
            }
        });
    }


    public void establecerMision(Mision nuevaMision) {
        this.mision = nuevaMision;
        this.mapa = nuevaMision.obtenerMapa();
        this.gps = nuevaMision.obtenerGps();
        this.jugador = nuevaMision.obtenerPosicionJugador();
    
        gridPane.getChildren().clear();
        dibujarMapa();
    }

    public void limpiarMision() {
        mision = null;
        mapa = null;
        gps = null;
        jugador = null;
    
        gridPane.getChildren().clear();
        gridPane.add(mensajeEspera, 0, 0);
    }

    public void mostrarResultado(String resultado) {
        gridPane.getChildren().clear();

        Label mensaje = new Label(resultado);
        mensaje.setStyle("-fx-font-size: 24px; -fx-text-fill: green;");
        gridPane.add(mensaje, 0, 0);

        pausar(1, this::limpiarMision);
    }

    

    //------------------------- Métodos privados -------------------------//

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

    private void pausar(int segundos, Runnable accionAlFinal) {
        PauseTransition pausa = new PauseTransition(Duration.seconds(segundos));
        pausa.setOnFinished(e -> accionAlFinal.run());
        pausa.play();
    }

    public static void lanzar(String[] args) {
        launch(args);
    }
    
    private VBox crearHUD() {
        labelTiempo = new Label("Tiempo: --");
        labelGasolina = new Label("Gasolina: --");

        labelTiempo.setStyle("-fx-font-size: 16px;");
        labelGasolina.setStyle("-fx-font-size: 16px;");

        VBox hud = new VBox(5, labelTiempo, labelGasolina);
        hud.setAlignment(Pos.TOP_RIGHT);
        hud.setStyle("-fx-padding: 10; -fx-background-color: rgba(255,255,255,0.8);");

        return hud;
    }

    private void actualizarHUD() {
        if (mision == null) return;

        labelTiempo.setText("Tiempo: " + mision.devolverTiempo());
        labelGasolina.setText(
            "Gasolina: " +
            mision.transporte().tanque() + "/" +
            mision.transporte().capacidadGasolina()
        );
    }

    private VBox crearGlosario() {
        Label titulo = new Label("Glosario");
        titulo.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        Label teclas = new Label(
            "Controles:\n" +
            "W → Arriba\n" +
            "A → Izquierda\n" +
            "S → Abajo\n" +
            "D → Derecha\n" +
            "C → Acción"
        );

        Label colores = new Label(
            "Colores:\n" +
            "Blanco → Transitable\n" +
            "Negro → Jugador\n" +
            "Rojo → Congestionada\n" +
            "Azul → Entrada\n" +
            "Verde → Salida\n" +
            "Magenta → Recompensa\n" +
            "Amarillo → GPS"
        );

        VBox glosario = new VBox(10, titulo, teclas, colores);
        glosario.setStyle("-fx-padding: 10;");
        glosario.setPrefWidth(200);

        return glosario;
    }

}
