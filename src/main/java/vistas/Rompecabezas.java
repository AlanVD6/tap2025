package vistas;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Parent;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToolBar;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Rompecabezas {

    private Timeline lineaTiempo;
    private Timeline tempo;
    private int seg = 0;
    private Label LbTiempo;
    private List<Pieza> piezas;
    private Mesa table;
    private String tamRompeSelec = "";
    private Group conte;
    private ToolBar Barra;

    public Rompecabezas() {
        Stage escenaPrincipal = new Stage();
        inicio(escenaPrincipal);
        escenaPrincipal.show();
    }

    private void inicio(Stage Principal) {
        conte = new Group();
        Scene escena1 = new Scene(conte, 800, 600);
        Principal.setScene(escena1);
        Principal.setTitle("Rompecabezas");
        Principal.setResizable(false);
        Barra = new ToolBar();
        Button btnRompe1 = new Button("7x4");
        Button btnRompe2 = new Button("5x3");
        Button btnRompe3 = new Button("6x2");

        btnRompe1.setOnAction(event -> LoadRompecabezas("/Image/rompe.png", 7, 4, "7x4"));
        btnRompe2.setOnAction(event -> LoadRompecabezas("/Image/rompe02.jpeg", 5, 3, "5x3"));
        btnRompe3.setOnAction(event -> LoadRompecabezas("/Image/rompe3.jpg", 6, 2, "6x2"));

        Barra.getItems().addAll(btnRompe1, btnRompe2, btnRompe3);

        LbTiempo = new Label("Tiempo restante: 480 segundos");
        LbTiempo.setStyle("-fx-font-size: 1.5em;");

        HBox cajaBotones = new HBox(8);
        VBox cajaVertical = new VBox(10);
        cajaVertical.getChildren().addAll(Barra, cajaBotones, LbTiempo);
        conte.getChildren().addAll(cajaVertical);
    }

    private void LoadRompecabezas(String rutaImagen, int numeroColumnas, int numeroFilas, String tamanoRompecabezas) {
        tamRompeSelec = tamanoRompecabezas;
        if (table != null) {
            table.getChildren().clear();
        }
        detenerTemporizador();
        iniciarTemporizador();

        Image imagen = new Image(getClass().getResourceAsStream(rutaImagen));
        table = new Mesa(numeroColumnas, numeroFilas);
        piezas = new ArrayList<>();
        for (int columna = 0; columna < numeroColumnas; columna++) {
            for (int fila = 0; fila < numeroFilas; fila++) {
                int x = columna * Pieza.TAM;
                int y = fila * Pieza.TAM;
                final Pieza pieza = new Pieza(imagen, x, y, fila > 0,
                        columna > 0, fila < numeroFilas - 1,
                        columna < numeroColumnas - 1, table.getWidth(), table.getHeight());
                piezas.add(pieza);
            }
        }
        table.getChildren().addAll(piezas);

        Button botonRevolver = new Button("MOVER");
        botonRevolver.setStyle("-fx-font-size: 1em;");
        botonRevolver.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (lineaTiempo != null) lineaTiempo.stop();
                lineaTiempo = new Timeline();
                for (final Pieza pieza : piezas) {
                    pieza.setActiva();
                    double revolverX = Math.random() * (table.getWidth() - Pieza.TAM  + 48f) - 24f - pieza.getXCorrecta();
                    double revolverY = Math.random() * (table.getHeight() - Pieza.TAM + 30f) - 15f - pieza.getYCorrecta();
                    lineaTiempo.getKeyFrames().add(new KeyFrame(Duration.seconds(1),
                            new KeyValue(pieza.translateXProperty(), revolverX),
                            new KeyValue(pieza.translateYProperty(), revolverY)));
                }
                lineaTiempo.playFromStart();
            }
        });
        Button botonResolver = new Button("RESOLVER");
        botonResolver.setStyle("-fx-font-size: 1em;");
        botonResolver.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (lineaTiempo != null) lineaTiempo.stop();
                lineaTiempo = new Timeline();
                for (final Pieza pieza : piezas) {
                    pieza.setInactiva();
                    lineaTiempo.getKeyFrames().add(new KeyFrame(Duration.seconds(1),
                            new KeyValue(pieza.translateXProperty(), 0),
                            new KeyValue(pieza.translateYProperty(), 0)));
                }
                lineaTiempo.playFromStart();
            }
        });
        Button botonFinalizar = new Button("Finalizar");
        botonFinalizar.setStyle("-fx-font-size: 1em;");
        botonFinalizar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                detenerTemporizador();
                if (RompeCompletado()) {
                    MensajeFelicitaciones();
                    guardarTiempoArchivo(false); // Guardar en el archivo que se completó el rompecabezas
                } else {
                    MensajeIncompleto();
                    guardarTiempoArchivo(true); // Guardar en el archivo que no se completó el rompecabezas
                }
            }
        });
        HBox cajaBotones = new HBox(8);
        cajaBotones.getChildren().addAll(botonRevolver, botonResolver, botonFinalizar);
        VBox cajaVertical = (VBox) conte.getChildren().get(0);
        cajaVertical.getChildren().clear();
        cajaVertical.getChildren().addAll(Barra, cajaBotones, table, LbTiempo);
    }

    private void iniciarTemporizador() {
        seg = 480;
        tempo = new Timeline(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                seg--;
                LbTiempo.setText("Tiempo restante: " + seg + " segundos");
                if (seg <= 0) {
                    detenerTemporizador();
                    MensajeTiempoAgotado();
                    guardarTiempoArchivo(true);
                    MensajeIncompleto();
                }
            }
        }));
        tempo.setCycleCount(Timeline.INDEFINITE);
        tempo.play();
    }

    private void detenerTemporizador() {
        if (tempo != null) {
            tempo.stop();
        }
    }

    private boolean RompeCompletado() {
        for (Pieza pieza : piezas) {
            if (pieza.getTranslateX() != 0 || pieza.getTranslateY() != 0) {
                return false;
            }
        }
        return true;
    }
    private void MensajeFelicitaciones() {
        javafx.scene.control.Alert alerta = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
        alerta.setTitle("¡Muchas Felicidades!");
        alerta.setHeaderText(null);
        alerta.setContentText("Finalizaste correctamente el rompecabezas con un tiempo de " + (480 - seg) + " segundos.\n" +
                "Escogiste el rompecabezas de tamaño: " + tamRompeSelec);
        alerta.showAndWait();
    }
    private void MensajeIncompleto() {
        javafx.scene.control.Alert alerta = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.WARNING);
        alerta.setTitle("El Rompecabezas está Incompleto");
        alerta.setHeaderText(null);
        alerta.setContentText("El rompecabezas no ha sido completado correctamente. ¡Sigue intentando vamos!");
        alerta.showAndWait();
    }
    private void MensajeTiempoAgotado() {
        javafx.scene.control.Alert alerta = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.WARNING);
        alerta.setTitle("¡Tiempo agotado!");
        alerta.setHeaderText(null);
        alerta.setContentText("El tiempo para completar el rompecabezas ha finalizado. ¡Inténtalo de nuevo!");
        alerta.showAndWait();
    }

    private void guardarTiempoArchivo(boolean tiempoAgotado) {
        try (FileWriter escritor = new FileWriter("tiempos.txt", true)) {
            if (tiempoAgotado) {
                escritor.write("Tiempo agotado - No se completó el rompecabezas (Tamaño: " + tamRompeSelec + ")\n");
            } else {
                escritor.write("Tiempo completado: " + (480 - seg) + " segundos (Tamaño: " + tamRompeSelec + ")\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static class Mesa extends Pane {
        Mesa(int numeroColumnas, int numeroFilas) {
            setStyle("-fx-background-color: #cccccc; " +
                    "-fx-border-color: #464646; " +
                    "-fx-effect: innershadow(two-pass-box, rgba(0,0,0,0.8), 15, 0.0, 0, 4);");
            double ANCHO_MESA = Pieza.TAM * numeroColumnas;
            double ALTO_MESA = Pieza.TAM * numeroFilas;
            setPrefSize(ANCHO_MESA, ALTO_MESA);
            setMaxSize(ANCHO_MESA, ALTO_MESA);
            autosize();

            Path cuadricula = new Path();
            cuadricula.setStroke(Color.rgb(70, 70, 70));
            getChildren().add(cuadricula);

            for (int columna = 0; columna < numeroColumnas - 1; columna++) {
                cuadricula.getElements().addAll(
                        new MoveTo(Pieza.TAM + Pieza.TAM * columna, 5),
                        new LineTo(Pieza.TAM + Pieza.TAM * columna, Pieza.TAM * numeroFilas - 5)
                );
            }

            for (int fila = 0; fila < numeroFilas; fila++) {
                cuadricula.getElements().addAll(
                        new MoveTo(5, Pieza.TAM + Pieza.TAM * fila),
                        new LineTo(Pieza.TAM * numeroColumnas - 5, Pieza.TAM + Pieza.TAM * fila)
                );
            }
        }
    }

    public static class Pieza extends Parent {
        public static final int TAM = 100;
        private final double xCorrecta;
        private final double yCorrecta;
        private final boolean tienePestanaSuperior;
        private final boolean tienePestanaIzquierda;
        private final boolean tienePestanaInferior;
        private final boolean tienePestanaDerecha;
        private double inicioArrastreX;
        private double inicioArrastreY;
        private Shape bordePieza;
        private Shape recortePieza;
        private ImageView vistaImagen = new ImageView();
        private Point2D anclaArrastre;

        public Pieza(Image imagen, final double xCorrecta, final double yCorrecta,
                     boolean pestanaSuperior, boolean pestanaIzquierda, boolean pestanaInferior, boolean pestanaDerecha,
                     final double anchoMesa, final double altoMesa) {
            this.xCorrecta = xCorrecta;
            this.yCorrecta = yCorrecta;
            this.tienePestanaSuperior = pestanaSuperior;
            this.tienePestanaIzquierda = pestanaIzquierda;
            this.tienePestanaInferior = pestanaInferior;
            this.tienePestanaDerecha = pestanaDerecha;

            recortePieza = crearPieza();
            recortePieza.setFill(Color.WHITE);
            recortePieza.setStroke(null);

            bordePieza = crearPieza();
            bordePieza.setFill(null);
            bordePieza.setStroke(Color.BLACK);

            vistaImagen.setImage(imagen);
            vistaImagen.setClip(recortePieza);
            setFocusTraversable(true);
            getChildren().addAll(vistaImagen, bordePieza);
            setCache(true);
            setInactiva();
            setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent eventoMouse) {
                    toFront();
                    inicioArrastreX = getTranslateX();
                    inicioArrastreY = getTranslateY();
                    anclaArrastre = new Point2D(eventoMouse.getSceneX(), eventoMouse.getSceneY());
                }
            });
            setOnMouseReleased(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent eventoMouse) {
                    if (getTranslateX() < (10) && getTranslateX() > (-10) &&
                            getTranslateY() < (10) && getTranslateY() > (-10)) {
                        setTranslateX(0);
                        setTranslateY(0);
                        setInactiva();
                    }
                }
            });
            setOnMouseDragged(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent eventoMouse) {
                    double nuevoTranslateX = inicioArrastreX + eventoMouse.getSceneX() - anclaArrastre.getX();
                    double nuevoTranslateY = inicioArrastreY + eventoMouse.getSceneY() - anclaArrastre.getY();
                    double minTranslateX = -45f - xCorrecta;
                    double maxTranslateX = (anchoMesa - TAM + 50f) - xCorrecta;
                    double minTranslateY = -30f - yCorrecta;
                    double maxTranslateY = (altoMesa - TAM + 70f) - yCorrecta;
                    if ((nuevoTranslateX > minTranslateX) &&
                            (nuevoTranslateX < maxTranslateX) &&
                            (nuevoTranslateY > minTranslateY) &&
                            (nuevoTranslateY < maxTranslateY)) {
                        setTranslateX(nuevoTranslateX);
                        setTranslateY(nuevoTranslateY);
                    }
                }
            });
        }

        private Shape crearPieza() {
            Shape forma = crearRectanguloPieza();
            if (tienePestanaDerecha) {
                forma = Shape.union(forma,
                        crearPestanaPieza(69.5f, 0f, 10f, 17.5f, 50f, -12.5f, 11.5f,
                                25f, 56.25f, -14f, 6.25f, 56.25f, 14f, 6.25f));
            }
            if (tienePestanaInferior) {
                forma = Shape.union(forma,
                        crearPestanaPieza(0f, 69.5f, 17.5f, 10f, -12.5f, 50f, 25f,
                                11f, 14f, 56.25f, 6.25f, 14f, 56.25f, 6.25f));
            }
            if (tienePestanaIzquierda) {
                forma = Shape.subtract(forma,
                        crearPestanaPieza(-31f, 0f, 10f, 17.5f, -50f, -12.5f, 11f,
                                25f, -43.75f, -14f, 6.25f, -43.75f, 14f, 6.25f));
            }
            if (tienePestanaSuperior) {
                forma = Shape.subtract(forma,
                        crearPestanaPieza(0f, -31f, 17.5f, 10f, -12.5f, -50f, 25f,
                                12.5f, -14f, -43.75f, 6.25f, 14f, -43.75f, 6.25f));
            }

            forma.setTranslateX(xCorrecta);
            forma.setTranslateY(yCorrecta);
            forma.setLayoutX(50.0);
            forma.setLayoutY(50.0);
            return forma;
        }
        private Rectangle crearRectanguloPieza() {
            Rectangle rectangulo = new Rectangle();
            rectangulo.setX(-50);
            rectangulo.setY(-50);
            rectangulo.setWidth(TAM);
            rectangulo.setHeight(TAM);
            return rectangulo;
        }
        private Shape crearPestanaPieza(double centroElipseX, double centroElipseY, double radioElipseX, double radioElipseY,
                                        double rectanguloX, double rectanguloY, double anchoRectangulo, double altoRectangulo,
                                        double centroCirculo1X, double centroCirculo1Y, double radioCirculo1,
                                        double centroCirculo2X, double centroCirculo2Y, double radioCirculo2) {
            Ellipse elipse = new Ellipse(centroElipseX, centroElipseY, radioElipseX, radioElipseY);
            Rectangle rectangulo = new Rectangle(rectanguloX, rectanguloY, anchoRectangulo, altoRectangulo);
            Shape pestana = Shape.union(elipse, rectangulo);
            Circle circulo1 = new Circle(centroCirculo1X, centroCirculo1Y, radioCirculo1);
            pestana = Shape.subtract(pestana, circulo1);
            Circle circulo2 = new Circle(centroCirculo2X, centroCirculo2Y, radioCirculo2);
            pestana = Shape.subtract(pestana, circulo2);
            return pestana;
        }
        public void setActiva() {
            setDisable(false);
            setEffect(new DropShadow());
            toFront();
        }
        public void setInactiva() {
            setEffect(null);
            setDisable(true);
        }
        public double getXCorrecta() {
            return xCorrecta;
        }
        public double getYCorrecta() {
            return yCorrecta;
        }
    }
}