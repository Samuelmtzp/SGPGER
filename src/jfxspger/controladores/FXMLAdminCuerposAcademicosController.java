package jfxspger.controladores;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class FXMLAdminCuerposAcademicosController extends FXMLPrincipalAdministradorController {

    @FXML
    private Label lbTitulo;
    @FXML
    private TableView<?> tvCuerposAcademicos;
    @FXML
    private TableColumn<?, ?> tcCuerpoAcademico;
    @FXML
    private TableColumn<?, ?> tcResponsable;
    @FXML
    private TableColumn<?, ?> tcClave;
    @FXML
    private TableColumn<?, ?> tcGradoConsolidacion;
    @FXML
    private TableColumn<?, ?> tcDependencia;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    @FXML
    private void clicBtnEliminarCuerpoAcademico(ActionEvent event) {
    }

    @FXML
    private void clicBtnModificarCuerpoAcademico(ActionEvent event) {
    }

    @FXML
    private void clicBtnAgregarCuerpoAcademico(ActionEvent event) {
    }
    
}