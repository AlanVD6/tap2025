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

public class HelloApplication extends Application {

    private VBox vBox;
    private MenuBar mnbPrincipal;
    private Menu menCompetencia1, menCompetencia2;
    private MenuItem mitCalculadora;

    private Scene escena;

    void Crearv1(){
        mitCalculadora = new MenuItem("Calculadora");
        menCompetencia1 = new Menu("competencia 1 ");
        menCompetencia1.getItems().addAll(mitCalculadora);
        mnbPrincipal = new MenuBar();
        mnbPrincipal.getMenus().addAll(menCompetencia1);
    }

    @Override
    public void start(Stage stage) throws IOException {

        vBox = new VBox();
        stage.setTitle("Hola mundo de Eventos :( ");
        stage.setScene(new Scene(vBox));
        stage.show();
        stage.setMaximized(true);

    }

    public static void main(String[] args) {
        launch();
    }

}
