/*
* Autor: Samuel Martínez Pazos
* Fecha de creación: 09/05/2023
* Descripción: Clase encargada de administrar el inicio de sesión
*/

package jfxspger.controladores;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import jfxspger.modelo.dao.SesionDAO;
import jfxspger.modelo.dao.TipoUsuarioDAO;
import jfxspger.modelo.pojo.TipoUsuarioRespuesta;
import jfxspger.modelo.pojo.TipoUsuario;
import jfxspger.modelo.pojo.Usuario;
import jfxspger.utilidades.Constantes;
import jfxspger.utilidades.Utilidades;

public class FXMLInicioSesionController implements Initializable {

    @FXML
    private TextField tfUsuario;
    @FXML
    private PasswordField tfPassword;
    @FXML
    private Label lbErrorUsuario;
    @FXML
    private Label lbErrorPassword;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    

    @FXML
    private void clicIniciarSesion(ActionEvent event) {
        lbErrorUsuario.setText("");
        lbErrorPassword.setText("");
        validarCampos();
    }
    
    private void validarCampos(){
        String usuario = tfUsuario.getText();
        String password = tfPassword.getText();
        boolean sonValidos = true;
        
        if(usuario.isEmpty()){
            sonValidos = false;
            lbErrorUsuario.setText("El campo usuario es obligatorio");
        }
        if(password.length() == 0){
            sonValidos = false;
            lbErrorPassword.setText("El campo contraseña es requerido");
        }
        if(sonValidos)
            validarCredencialesUsuario(usuario, password);
    }
    
    private void validarCredencialesUsuario(String usuario, String password) {
        Usuario usuarioRespuesta = SesionDAO.verificarUsuarioSesion(usuario, password);
        switch (usuarioRespuesta.getCodigoRespuesta())
        {
            case Constantes.ERROR_CONEXION:
                Utilidades.mostrarDialogoSimple("Error de conexión", 
                        "Por el momento no hay conexión, intentelo más tarde", 
                        Alert.AlertType.ERROR);
                break;
            
            case Constantes.ERROR_CONSULTA:
                Utilidades.mostrarDialogoSimple("Error en la solicitud", 
                        "Por el momento no se puede procesar la solicitud de verificación", 
                        Alert.AlertType.ERROR);
                break;
            
            case Constantes.OPERACION_EXITOSA:
                if (usuarioRespuesta.getIdUsuario() > 0) {
                    Utilidades.mostrarDialogoSimple("Bienvenido(a)", 
                        "Bienvenido(a) "+usuarioRespuesta.toString()+" al sistema...", 
                        Alert.AlertType.INFORMATION);
                    
                    final int TIPO_USUARIO_ADMINISTRADOR = 1;
                    final int TIPO_USUARIO_ESTUDIANTE = 2;
                    final int TIPO_USUARIO_ACADEMICO = 3;
                
                    switch (usuarioRespuesta.getIdTipoUsuario()) {
                        case TIPO_USUARIO_ADMINISTRADOR :
                            irPantallaPrincipalAdministrador();
                            break;
                        case TIPO_USUARIO_ESTUDIANTE :
                            irPantallaPrincipalEstudiante();
                            break;
                        case TIPO_USUARIO_ACADEMICO :
                            irPantallaPrincipalAcademico();
                            break;
                        default :
                            Utilidades.mostrarDialogoSimple("Error al mostrar menú principal", 
                                    "El tipo de usuario es incorrecto", 
                                    Alert.AlertType.ERROR);
                    }
                } else {
                    Utilidades.mostrarDialogoSimple("Credenciales incorrectas", 
                            "El usuario y/o contraseña no son correctos, " + 
                                    "por favor verifica la información", 
                            Alert.AlertType.WARNING);
                }
                break;
            
            default:
                Utilidades.mostrarDialogoSimple("Error de petición", 
                        "El sistema no está disponible por el momento", 
                        Alert.AlertType.ERROR);
        }
    }
    
    private void irPantallaPrincipalAdministrador() {
        Stage escenarioBase = (Stage) tfUsuario.getScene().getWindow();
        escenarioBase.setScene(Utilidades.inicializarEscena("vistas/FXMLPrincipalAdministrador.fxml"));
        configurarEscena(escenarioBase);
    }
    
    private void irPantallaPrincipalEstudiante() {                
        Stage escenarioBase = (Stage) tfUsuario.getScene().getWindow();
        escenarioBase.setScene(Utilidades.inicializarEscena("vistas/FXMLPrincipalEstudiante.fxml"));
        configurarEscena(escenarioBase);
    }
    
    private void irPantallaPrincipalAcademico() {
        Stage escenarioBase = (Stage) tfUsuario.getScene().getWindow();
        escenarioBase.setScene(Utilidades.inicializarEscena("vistas/FXMLPrincipalAcademico.fxml"));
        configurarEscena(escenarioBase);
    }
    
    private void configurarEscena(Stage escenarioBase) {
        escenarioBase.setTitle("Ventana Principal");
        escenarioBase.show();
    }
    
}
