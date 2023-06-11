package jfxspger.controladores;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import jfxspger.utilidades.Utilidades;

public class FXMLAdminCursosController extends FXMLPrincipalAdministradorController {

    @FXML
    private Label lbTitulo;
    @FXML
    private TableView<?> tvCursos;
    @FXML
    private TableColumn<?, ?> tcExperienciaEducativa;
    @FXML
    private TableColumn<?, ?> tcPeriodo;
    @FXML
    private TableColumn<?, ?> tcProfesor;
    @FXML
    private TableColumn<?, ?> tcNrc;
    @FXML
    private TableColumn<?, ?> tcBloque;
    @FXML
    private TableColumn<?, ?> tcSeccion;
    @FXML
    private TableColumn<?, ?> tcCupo;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    @FXML
    private void clicBtnAgregarCurso(ActionEvent event) {
        Stage escenarioBase = (Stage) lbTitulo.getScene().getWindow();
        escenarioBase.setScene(
                Utilidades.inicializarEscena("vistas/FXMLFormularioCurso1.fxml"));
        escenarioBase.setTitle("Formulario de curso");
        escenarioBase.show();
    }
    
}
