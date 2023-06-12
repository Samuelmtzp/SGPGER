package jfxspger.controladores;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import jfxspger.utilidades.Utilidades;

public class FXMLAdminCursosController extends FXMLPrincipalAdministradorController {

    @FXML
    private Label lbTitulo;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    @FXML
    private void clicIrPrincipalAdministrador(ActionEvent event) {
    }

    @FXML
    private void clicIrFormularioCurso(ActionEvent event) {
        Stage escenarioBase = (Stage) lbTitulo.getScene().getWindow();
        escenarioBase.setScene(
                Utilidades.inicializarEscena("vistas/FXMLFormularioCurso.fxml"));
        escenarioBase.setTitle("Formulario de curso");
        escenarioBase.show();
    }
}
