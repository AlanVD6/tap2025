package vistas;

import javafx.scene.Scene;
import javafx.stage.Stage;
import org.kordamp.bootstrapfx.BootstrapFX;
import org.kordamp.bootstrapfx.scene.layout.Panel;


public class VentasRestaurantes extends Stage {

    private Panel pntRestaurante;
    private Scene escena ;

    public VentasRestaurantes(){
        CrearUI();
        this.setTitle("Fonda donña lipe");
        this.setScene(escena);
        this.show();
    }

    void CrearUI(){
        pntRestaurante = new Panel("Tacos el Inge.");
        pntRestaurante.getStyleClass().add("panel-primary");
        escena = new Scene(pntRestaurante,300,200);
        escena.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());

    }
}
