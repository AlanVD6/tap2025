package vistas;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Calculadora extends Stage {
    private Scene escena;
    private TextField txtDisplay;
    private VBox vBox;
    private GridPane gdpTeclado;
    private Button[][] arBtnTeclado;
    private String[] strTeclas = {"7", "8", "9", "*", "4", "5", "6", "/", "1", "2", "3", "+", ".", "0", "=", "-"};
    private String operacion = "";
    private double num1 = 0;
    private boolean nuevoNumero = true;

    public void CrearUI() {
        CrearKeyboard();
        txtDisplay = new TextField("0");
        txtDisplay.setEditable(false);
        txtDisplay.setAlignment(Pos.BASELINE_RIGHT);

        // Botón de borrado
        Button btnBorrar = new Button("C");
        btnBorrar.setStyle("-fx-background-color: #ff4444; -fx-text-fill: white;");
        btnBorrar.setOnAction(actionEvent -> {
            txtDisplay.setText("0");
            operacion = "";
            nuevoNumero = true;
        });

        vBox = new VBox(txtDisplay, btnBorrar, gdpTeclado);
        vBox.setSpacing(10);
        vBox.setPadding(new Insets(10));
        escena = new Scene(vBox, 220, 280);
        String css = getClass().getResource("/Styles/Calcu.css").toExternalForm();
        escena.getStylesheets().add(css);
    }

    public void CrearKeyboard() {
        arBtnTeclado = new Button[4][4];
        gdpTeclado = new GridPane();
        gdpTeclado.setHgap(5);
        gdpTeclado.setVgap(5);
        int pos = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                arBtnTeclado[i][j] = new Button(strTeclas[pos]);
                if(strTeclas[pos].equals("*"))
                    arBtnTeclado[i][j].setId("fontButton");
                int finalPos = pos;
                arBtnTeclado[i][j].setOnAction(actionEvent -> EventoTeclado(strTeclas[finalPos]));
                arBtnTeclado[i][j].setPrefSize(50, 50);
                gdpTeclado.add(arBtnTeclado[i][j], j, i);
                pos++;
            }
        }
    }

    private void EventoTeclado(String strTecla) {
        if (txtDisplay.getText().equals("Math Error")) {
            txtDisplay.setText("0");
            nuevoNumero = true;
        }

        if (strTecla.matches("[0-9]")) {
            if (nuevoNumero) {
                txtDisplay.setText(strTecla);
                nuevoNumero = false;
            } else {
                txtDisplay.appendText(strTecla);
            }
        } else if (strTecla.equals(".")) {
            String[] partes = txtDisplay.getText().split(" ");
            String ultimoNumero = partes[partes.length - 1];
            if (!ultimoNumero.contains(".")) {
                txtDisplay.appendText(".");
                nuevoNumero = false;
            }
        } else if (strTecla.matches("[+*/]")) {
            if (!txtDisplay.getText().isEmpty() && !txtDisplay.getText().endsWith(" ")) {
                txtDisplay.appendText(" " + strTecla + " ");
                operacion = strTecla;
                nuevoNumero = false;
            }
        } else if (strTecla.equals("-")) {
            String textoActual = txtDisplay.getText();

            // Si es un nuevo número, permite un "-" al inicio (para números negativos)
            if (nuevoNumero) {
                txtDisplay.setText("-");
                nuevoNumero = false;
            }
            // Si el texto actual termina con un espacio, permite un "-" (para operaciones)
            else if (textoActual.endsWith(" ")) {
                txtDisplay.appendText("-");
                nuevoNumero = false;
            }
            // Si el texto actual no termina con un espacio, verifica si ya hay un "-"
            else if (!textoActual.endsWith(" ")) {
                // Verifica si el último carácter es un "-"
                if (textoActual.endsWith("-")) {
                    // Si ya hay un "-", muestra "Math Error"
                    txtDisplay.setText("Math Error");
                    nuevoNumero = true;
                } else {
                    // Si no hay un "-", agrega " - " para la operación
                    txtDisplay.appendText(" - ");
                    operacion = "-";
                    nuevoNumero = false;
                }
            }
        } else if (strTecla.equals("=")) {
            String texto = txtDisplay.getText().trim();
            if (operacion.isEmpty() || texto.endsWith(" " + operacion)
                    || texto.matches("[+\\-*/]+")) {
                txtDisplay.setText("Math Error");
                return;
            }
            String[] partes = texto.split(" ");
            double resultado = Double.parseDouble(partes[0]);
            for (int i = 1; i < partes.length - 1; i += 2) {
                String operador = partes[i];
                double num2 = Double.parseDouble(partes[i + 1]);
                if (operador.equals("/") && num2 == 0) {
                    txtDisplay.setText("Math Error");
                    nuevoNumero = true;
                    return;
                }
                resultado = calcularResultado(resultado, num2, operador);
            }
            txtDisplay.setText(String.valueOf(resultado));
            num1 = resultado;
            operacion = "";
            nuevoNumero = true;
        }
    }

    private double calcularResultado(double num1, double num2, String operacion) {
        switch (operacion) {
            case "+": return num1 + num2;
            case "-": return num1 - num2;
            case "*": return num1 * num2;
            case "/": return num2 != 0 ? num1 / num2 : 0;
            default: return num2;
        }
    }

    public Calculadora() {
        CrearUI();
        this.setScene(escena);
        this.setTitle("Calculadora");
        this.show();
    }
}