package com.example.tab2025;


import com.example.Componentes.Hilo;
import com.example.modelos.Conexion;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import vistas.*;

public class HelloApplication extends Application {
    private VBox vBox;
    private MenuBar mnbPrincipal;
    private Menu menCompetencia1, menCompetencia2;
    private MenuItem mitCalculadora, mitRestaurante, mitRompecabeza, mitHilo;
    private Scene escena;

    void CrearUI() {

        mitCalculadora = new MenuItem("Calculadora");
        mitCalculadora.setOnAction(actionEvent -> new Calculadora());

        mitRompecabeza = new MenuItem("Rompecabezas");
        mitRompecabeza.setOnAction(actionEvent -> new Rompecabezas());

        mitRestaurante = new MenuItem("Restaurante");
        mitRestaurante.setOnAction(actionEvent -> new Inicio());


        mitHilo = new MenuItem("Celayork");
        mitHilo.setOnAction(actionEvent -> new Celayork());

        menCompetencia1 = new Menu("Competencia 1");
        menCompetencia1.getItems().addAll(mitCalculadora, mitRestaurante, mitRompecabeza);

        menCompetencia2 = new Menu("Competencia 2");
        menCompetencia2.getItems().addAll(mitHilo);

        mnbPrincipal = new MenuBar(menCompetencia1, menCompetencia2);
        vBox = new VBox(mnbPrincipal);
        escena = new Scene(vBox);
        String css = getClass().getResource("/Styles/Main.Css").toExternalForm();
        escena.getStylesheets().add(css);

    }

    @Override
    public void start(Stage stage) {

        Conexion.createConnection();
        CrearUI();
        stage.setTitle("Hola Mundo de Eventos");
        stage.setScene(escena);
        stage.setMaximized(true);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}