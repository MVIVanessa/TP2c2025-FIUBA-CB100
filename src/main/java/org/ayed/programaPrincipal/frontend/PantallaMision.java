package org.ayed.programaPrincipal.frontend;

import java.util.function.Consumer;

import org.ayed.gta.Mapa.Coordenadas;
import org.ayed.gta.Mapa.Gps;
import org.ayed.gta.Mapa.Mapa;
import org.ayed.gta.Mapa.TipoCelda;
import org.ayed.gta.Misiones.ExcepcionMision;
import org.ayed.gta.Misiones.Mision;
import org.ayed.programaPrincipal.aplicacion.Controlador;
import org.ayed.tda.iterador.Iterador;
import org.ayed.tda.lista.Lista;

import javafx.animation.PauseTransition;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class PantallaMision {

    private Mapa mapa;
    private Gps gps;
    private GridPane gridPane;
    private Mision mision;
    private Coordenadas jugador;
    private BorderPane root;
    private Label labelTiempo;
    private Label labelGasolina;
    private Label labelMensaje;

    private Consumer<Boolean> onFinMision;
    private boolean misionFinalizada;

    public PantallaMision() {
        inicializarUI();
    }

    private void inicializarUI() {
        root = new BorderPane();

        // MAPA
        gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        root.setCenter(gridPane);

        // HUD / barra superior
        HBox barraSuperior = crearBarraSuperior();
        root.setTop(barraSuperior);

        // GLOSARIO
        VBox glosario = crearGlosario();
        root.setLeft(glosario);
    }

    public void manejarTeclas(KeyEvent event) {
        if (mision == null || misionFinalizada) return;

        String comando = event.getCode().toString();
        if ("WASDC".contains(comando)) {
            try {
                mision.moverJugador(comando);
                jugador = mision.obtenerPosicionJugador();
                actualizarHUD();
                dibujarMapa();

                if (mapa.datoDeCelda(jugador.obtenerX(), jugador.obtenerY()) == TipoCelda.CONGESTIONADA) {
                    mostrarMensaje("Zona congestionada: perdiste más tiempo ⏱", Color.ORANGE);
                }
                // recompensa
                if (mapa.datoDeCelda(jugador.obtenerX(), jugador.obtenerY()) == TipoCelda.TRANSITABLE_RECOMPENSA) {
                    mostrarMensaje("¡Recompensa recogida!", Color.GREEN);
                    mapa.obtenerMapa().dato(jugador.obtenerX()).modificarDato(TipoCelda.TRANSITABLE, jugador.obtenerY());
                }

                if (mision.misionCompletada()) {
                    misionFinalizada = true;
                    if (onFinMision != null) {
                        onFinMision.accept(true);
                    }
                }
                else if (mision.fracaso()) {
                    misionFinalizada = true;
                    if (onFinMision != null) {
                        onFinMision.accept(false);
                    }
            }

            } catch (ExcepcionMision e) {
                mostrarMensaje(e.getMessage(), Color.RED);
            }
        }
    }

    public void establecerMision(Mision nuevaMision) {
        misionFinalizada = false;
        this.mision = nuevaMision;
        this.mapa = nuevaMision.obtenerMapa();
        this.gps = nuevaMision.obtenerGps();
        this.jugador = nuevaMision.obtenerPosicionJugador();


        gridPane.getChildren().clear();
        dibujarMapa();
        actualizarHUD();
    }

    public void mostrarResultado(String resultado) {
        gridPane.getChildren().clear();

        Label mensaje = new Label(resultado);
        mensaje.setStyle("-fx-font-size: 24px; -fx-text-fill: green;");
        gridPane.add(mensaje, 0, 0);
        misionFinalizada = true;
    }

    public void setOnFinMision(Consumer<Boolean> callback) {
        this.onFinMision = callback;
    }

    // ================= MÉTODOS PRIVADOS =================

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
        if (jugador != null && coord.compararCoordenadas(jugador))
            return Color.DARKBLUE;

        if (gps != null &&
            tipo != TipoCelda.RECOMPENSA &&
            tipo != TipoCelda.ENTRADA &&
            tipo != TipoCelda.SALIDA &&
            gps.buscarCoordenadas(coord))
            return Color.YELLOW;

        switch (tipo) {
            case TRANSITABLE: return Color.WHITE;
            case EDIFICIO: return Color.BLACK;
            case CONGESTIONADA: return Color.RED;
            case ENTRADA: return Color.BLUE;
            case SALIDA: return Color.GREEN;
            case RECOMPENSA: return Color.MAGENTA;
            case TRANSITABLE_RECOMPENSA: return Color.WHITE;
            default: return Color.LIGHTGRAY;
        }
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
            "Negro → Edificio\n" +
            "Azul oscuro → Jugador\n" +
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

    private HBox crearBarraSuperior() {
        labelMensaje = new Label("");
        labelMensaje.setStyle("-fx-font-size: 14px; -fx-text-fill: darkred;");

        VBox mensajes = new VBox(labelMensaje);
        mensajes.setAlignment(Pos.CENTER_LEFT);
        mensajes.setStyle("-fx-padding: 10;");

        VBox hud = crearHUD();

        HBox barra = new HBox(mensajes, hud);
        barra.setAlignment(Pos.CENTER);
        barra.setSpacing(20);
        barra.setStyle("-fx-background-color: rgba(255,255,255,0.85);");

        HBox.setHgrow(mensajes, javafx.scene.layout.Priority.ALWAYS);

        return barra;
    }

    private void mostrarMensaje(String texto, Color color) {
        labelMensaje.setText(texto);
        labelMensaje.setTextFill(color);

        PauseTransition pausa = new PauseTransition(Duration.seconds(0.5));
        pausa.setOnFinished(e -> labelMensaje.setText(""));
        pausa.play();
    }

    // ------------------------ GETTERS --------------------------

    public BorderPane getRoot() {
        return root;
    }
}
