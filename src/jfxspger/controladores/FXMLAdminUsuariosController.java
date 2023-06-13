package jfxspger.controladores;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
    private final int TIPO_USUARIO_ADMINISTRADOR = 1;
    private final int TIPO_USUARIO_ESTUDIANTE = 2;
    private final int TIPO_USUARIO_ACADEMICO = 3;

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
        int posicion = tvUsuarios.getSelectionModel().getSelectedIndex();
        if (posicion != -1) {
            if (Utilidades.mostrarDialogoConfirmacion("Confirmación de cancelación", 
                    "¿Está seguro de que desea eliminar al usuario?")) {
                int respuesta = UsuarioDAO.eliminarUsuario(
                        tvUsuarios.getSelectionModel().getSelectedItem().getIdUsuario());
                switch(respuesta) {
                    case Constantes.ERROR_CONEXION:
                        Utilidades.mostrarDialogoSimple("Error de conexion", "El usuario " + 
                                "no pudo ser eliminado debido a un error de conexión.", 
                                Alert.AlertType.ERROR);
                    break;
                    case Constantes.ERROR_CONSULTA:
                        Utilidades.mostrarDialogoSimple("Error al eliminar",
                                "No se ha podido eliminar al usuario, "
                               + "por favor inténtelo más tarde." ,
                                Alert.AlertType.WARNING);
                    break;
                    case Constantes.OPERACION_EXITOSA:
                        cargarDatosTabla();
                        Utilidades.mostrarDialogoSimple("Usuario eliminado", 
                                "El usuario fue eliminado correctamente", 
                                Alert.AlertType.INFORMATION);
                    break;
                }
            }
        } else
            Utilidades.mostrarDialogoSimple("Selección necesaria", 
                    "Para eliminar un usuario, debe seleccionarlo previamente", 
                    Alert.AlertType.WARNING);
    }

    @FXML
    private void clicBtnModificarUsuario(ActionEvent event) {
        Usuario usuarioSeleccionado = tvUsuarios.getSelectionModel().getSelectedItem();
        if(usuarioSeleccionado != null){
            irFormularioUsuario(true, usuarioSeleccionado);
        }else{
            Utilidades.mostrarDialogoSimple("Atención", "Selecciona el registro "
                    + "en la tabla para poder editarlo", Alert.AlertType.WARNING);
        }
    }
    
    private void irFormularioUsuario(boolean esEdicion, Usuario usuario){
        try{
            FXMLLoader accesoControlador = new FXMLLoader(jfxspger.
                    JFXSPGER.class.getResource("/jfxspger/vistas/FXMLFormularioUsuario.fxml"));
            Parent vista = accesoControlador.load();
            FXMLFormularioUsuarioController formulario = accesoControlador.getController();
            Scene sceneFormulario = new Scene(vista);
            Stage escenarioPrincipal = (Stage) lbTitulo.getScene().getWindow();
            escenarioPrincipal.setTitle("Formulario de usuario");
            escenarioPrincipal.setScene(sceneFormulario);
            formulario.inicializarInformacionFormulario(esEdicion, usuario);
        }catch(IOException e){
            Utilidades.mostrarDialogoSimple("Error", 
                    "No se puede mostrar la pantalla de formulario", 
                    Alert.AlertType.ERROR);  
        }
        
    }

    @FXML
    private void clicBtnAgregarUsuario(ActionEvent event) {
        irFormularioUsuario(false, null);
    }
    
}
