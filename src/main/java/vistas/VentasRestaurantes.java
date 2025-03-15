package vistas;

import javafx.scene.Scene;
import javafx.stage.Stage;
import org.kordamp.bootstrapfx.scene.layout.Panel;
import org.kordamp.bootstrapfx.BootstrapFX;

public class VentasRestaurantes extends Stage {

    private Panel pnlRestaurante;
    private Scene escena;

    public VentasRestaurantes(){
        CrearUI();
        this.setTitle("Fondita Doña Lupe");
        this.setScene(escena);
        this.show();
    }

    void CrearUI(){
        pnlRestaurante = new Panel("Tacos el Inge.");
        pnlRestaurante.getStyleClass().add("panel-primary");
        escena = new Scene(pnlRestaurante,300,200);
        escena.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
    }

}