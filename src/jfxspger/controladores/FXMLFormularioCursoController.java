package jfxspger.controladores;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import jfxspger.utilidades.Utilidades;

public class FXMLFormularioCursoController extends FXMLAdminCursosController {

    @FXML
    private Label lbTitulo;
    @FXML
    private ComboBox<?> cbGradoConsolidacion;
    @FXML
    private ComboBox<?> cbResponsable;
    @FXML
    private ComboBox<?> cbDependencia;
    @FXML
    private TextField tfNombre;
    @FXML
    private TextField tfClave;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    

    private void clicIrAdminCursos(ActionEvent event) {
        irAdminCursos();
    }
    
    private void irAdminCursos() {
        Stage escenarioBase = (Stage) lbTitulo.getScene().getWindow();
        escenarioBase.setScene(
                Utilidades.inicializarEscena("vistas/FXMLAdminCursos.fxml"));
        escenarioBase.setTitle("Administración cursos");
        escenarioBase.show();
    }

    @FXML
    private void clicIrAdminCuerposAcademicos(ActionEvent event) {
    }

    @FXML
    private void clicBtnRegistrarCuerpoAcademico(ActionEvent event) {
    }
    
}
