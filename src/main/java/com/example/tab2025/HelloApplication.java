package com.example.tab2025;

import com.modelos.Conexion;
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
import vistas.ListaClientes;
import vistas.Rompecabezas;
import vistas.VentasRestaurantes;

public class HelloApplication extends Application {
    private VBox vBox;
    private MenuBar mnbPrincipal;
    private Menu menCompetencia1;
    private MenuItem mitCalculadora, mitRestaurante , mitRompecabeza;
    private Scene escena;

    void CrearUI() {

        mitCalculadora = new MenuItem("Calculadora");
        mitCalculadora.setOnAction(actionEvent -> new Calculadora());
        mitRompecabeza= new MenuItem("Rompecabezas");
        mitRompecabeza.setOnAction(actionEvent -> new Rompecabezas());
        mitRestaurante = new MenuItem("Restaurante");
        mitRestaurante.setOnAction(actionEvent -> new ListaClientes());
        menCompetencia1 = new Menu("Competencia 1");
        menCompetencia1.getItems().addAll(mitCalculadora, mitRestaurante, mitRompecabeza);
        mnbPrincipal = new MenuBar(menCompetencia1);
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
