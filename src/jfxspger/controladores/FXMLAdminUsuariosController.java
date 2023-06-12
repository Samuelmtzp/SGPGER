package jfxspger.controladores;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import jfxspger.modelo.dao.UsuarioDAO;
import jfxspger.modelo.pojo.Usuario;
import jfxspger.modelo.pojo.UsuarioRespuesta;
import jfxspger.utilidades.Constantes;
import jfxspger.utilidades.Utilidades;

public class FXMLAdminUsuariosController extends FXMLPrincipalAdministradorController {

    @FXML
    private Label lbTitulo;
    @FXML
    private TableView<Usuario> tvUsuarios;
    @FXML
    private TableColumn tcNombre;
    @FXML
    private TableColumn tcApellidoPaterno;
    @FXML
    private TableColumn tcApellidoMaterno;
    @FXML
    private TableColumn tcCorreo;
    @FXML
    private TableColumn tcTipo;
    
    ObservableList<Usuario> usuarios;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTabla();
        cargarDatosTabla();
    }
    
    private void configurarTabla(){
        tcApellidoMaterno.setCellValueFactory(new PropertyValueFactory("apellidoMaterno"));
        tcApellidoPaterno.setCellValueFactory(new PropertyValueFactory("apellidoPaterno"));
        tcCorreo.setCellValueFactory(new PropertyValueFactory("correo"));
        tcNombre.setCellValueFactory(new PropertyValueFactory("nombre"));
        tcTipo.setCellValueFactory(new PropertyValueFactory("tipoUsuario"));
    }
    
    private void cargarDatosTabla() {
        usuarios = FXCollections.observableArrayList();
        UsuarioRespuesta respuestaBD = UsuarioDAO.obtenerInformacionUsuarios();
        switch (respuestaBD.getCodigoRespuesta()) {
            case Constantes.ERROR_CONEXION:
                Utilidades.mostrarDialogoSimple("Sin conexión", 
                    "Lo sentimos, por el momento no hay conexión para poder "
                    + "cargar la información", 
                    Alert.AlertType.ERROR);
            break;
            case Constantes.ERROR_CONSULTA:
                Utilidades.mostrarDialogoSimple("Error al cargar los datos", 
                    "Hubo un error al cargar la información, por favor inténtelo más tarde", 
                    Alert.AlertType.WARNING);
            break;
            case Constantes.OPERACION_EXITOSA:
                usuarios.addAll(respuestaBD.getUsuarios());
                tvUsuarios.setItems(usuarios);
            break;
        }
    }

    @FXML
    private void clicBtnEliminarUsuario(ActionEvent event) {
    }

    @FXML
    private void clicBtnModificarUsuario(ActionEvent event) {
    }

    @FXML
    private void clicBtnAgregarUsuario(ActionEvent event) {
        Stage escenarioBase = (Stage) lbTitulo.getScene().getWindow();
        escenarioBase.setScene(
                Utilidades.inicializarEscena("vistas/FXMLFormularioUsuario.fxml"));
        escenarioBase.setTitle("Formulario de usuario");
        escenarioBase.show();
    }
    
}
