package jfxspger.controladores;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class FXMLAdminUsuariosController extends FXMLPrincipalAdministradorController {

    @FXML
    private Label lbTitulo;
    @FXML
    private TableView<?> tvUsuarios;
    @FXML
    private TableColumn<?, ?> tcNombre;
    @FXML
    private TableColumn<?, ?> tcApellidoPaterno;
    @FXML
    private TableColumn<?, ?> tcApellidoMaterno;
    @FXML
    private TableColumn<?, ?> tcCorreo;
    @FXML
    private TableColumn<?, ?> tcTipo;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    @FXML
    private void clicBtnEliminarUsuario(ActionEvent event) {
    }

    @FXML
    private void clicBtnModificarUsuario(ActionEvent event) {
    }

    @FXML
    private void clicBtnAgregarUsuario(ActionEvent event) {
    }
    
}
