package jfxspger.controladores;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import jfxspger.utilidades.Utilidades;

public class FXMLPrincipalAdministradorController implements Initializable {

    @FXML
    private Label lbTitulo;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    


    @FXML
    private void clicCerrarSesion(ActionEvent event) {
        if (Utilidades.mostrarDialogoConfirmacion(
                "Cerrar sesión", 
                "¿Está seguro de que desea cerrar sesión?")) {
            irVentanaInicioSesion();
        }
    }
    
    private void irVentanaInicioSesion() {
        Stage escenarioBase = (Stage) lbTitulo.getScene().getWindow();
        escenarioBase.setScene(
                Utilidades.inicializarEscena("vistas/FXMLInicioSesion.fxml"));
        escenarioBase.setTitle("Inicio de sesion");
        escenarioBase.show();
    }

    @FXML
    private void clicIrCursos(ActionEvent event) {
    }

    @FXML
    private void clicIrLgac(ActionEvent event) {
    }

    @FXML
    private void clicIrUsuarios(ActionEvent event) {
    }

    @FXML
    private void clicIrCuerposAcademicos(ActionEvent event) {
    }
    
}
