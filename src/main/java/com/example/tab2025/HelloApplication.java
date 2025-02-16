package com.example.tab2025;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import vistas.Calculadora;

import java.io.IOException;

/*public class HelloApplication extends Application {

    private Button BtnSaludo, BtnSaludo2, BtnSaludo3;
    private VBox vBox;

    @Override
    public void start(Stage stage) throws IOException {

        BtnSaludo = new Button("Bienvenido");
        BtnSaludo.setOnAction(actionEvent -> clicEvent());
        BtnSaludo2 = new Button("Bienvenido");
        BtnSaludo2.setOnAction(actionEvent -> clicEvent());
        BtnSaludo3 = new Button("Bienvenido");

        vBox = new VBox(BtnSaludo, BtnSaludo2, BtnSaludo3);
        vBox.setSpacing(10);
        vBox.setPadding(new Insets(10));

        stage.setTitle("Hola mundo de Eventos :( ");
        stage.setScene(new Scene(vBox,200,200));
        stage.show();
        stage.setMaximized(true);

    }

    public static void main(String[] args) {
        launch();
    }
    void clicEvent(){
        System.out.println("Evento desde un metodo ");
    }
} */

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import vistas.VentasRestaurantes;

public class HelloApplication extends Application {
    private VBox vBox;
    private MenuBar mnbPrincipal;
    private Menu menCompetencia1;
    private MenuItem mitCalculadora, mitRestaurante;
    private Scene escena;

    void CrearUI(){
        mitCalculadora = new MenuItem("Calculadora");
        mitCalculadora.setOnAction(actionEvent -> new Calculadora());
        mitRestaurante= new MenuItem("Restaurante");
        mitRestaurante.setOnAction(actionEvent -> new VentasRestaurantes());
        menCompetencia1 = new Menu("Competencia 1");
        menCompetencia1.getItems().addAll(mitCalculadora,mitRestaurante );
        mnbPrincipal = new MenuBar(menCompetencia1);
        vBox = new VBox(mnbPrincipal);
        escena = new Scene(vBox);

        // Asegurar la correcta carga del CSS
        String css = getClass().getResource("/Styles/Main.Css").toExternalForm();
        escena.getStylesheets().add(css);
    }

    @Override
    public void start(Stage stage) {
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
