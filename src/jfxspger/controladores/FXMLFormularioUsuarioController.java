/*
* Autor: Samuel Martínez Pazos
* Fecha de creación: 13/06/2023
* Descripción: Clase controladora de formulario de usuario
*/
package jfxspger.controladores;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import jfxspger.modelo.dao.EstudianteDAO;
import jfxspger.modelo.dao.TipoUsuarioDAO;
import jfxspger.modelo.dao.UsuarioDAO;
import jfxspger.modelo.dao.AcademicoDAO;
import jfxspger.modelo.pojo.Academico;
import jfxspger.modelo.pojo.AcademicoRespuesta;
import jfxspger.modelo.pojo.Estudiante;
import jfxspger.modelo.pojo.EstudianteRespuesta;
import jfxspger.modelo.pojo.TipoUsuario;
import jfxspger.modelo.pojo.TipoUsuarioRespuesta;
import jfxspger.modelo.pojo.Usuario;
import jfxspger.modelo.pojo.UsuarioRespuesta;
import jfxspger.utilidades.Constantes;
import jfxspger.utilidades.Utilidades;

public class FXMLFormularioUsuarioController extends FXMLPrincipalAdministradorController {

    @FXML
    private Label lbTitulo;
    @FXML
    private ComboBox<TipoUsuario> cbTipoUsuario;
    @FXML
    private TextField tfApellidoMaterno;
    @FXML
    private TextField tfApellidoPaterno;
    @FXML
    private TextField tfNombre;
    @FXML
    private TextField tfCorreo;
    @FXML
    private TextField tfTelefono;
    @FXML
    private TextField tfUsername;
    @FXML
    private PasswordField tfPassword;
    @FXML
    private TextField tfCampoAdicional;
    @FXML
    private Label lbCampoAdicional;
    private ObservableList<TipoUsuario> tiposUsuario;
    @FXML
    private Label lbTelefonoError;
    private final int TIPO_USUARIO_ADMINISTRADOR = 1;
    private final int TIPO_USUARIO_ESTUDIANTE = 2;
    private final int TIPO_USUARIO_ACADEMICO = 3;
    private boolean esEdicion;
    private Usuario usuarioEdicion;
    private Usuario copiaUsuarioEdicion;
    @FXML
    private Button btnRegistrarUsuario;
    @FXML
    private Button btnActualizarUsuario;
    @FXML
    private Label lbUsernameNoDisponible;
    @FXML
    private Label lbCampoAdicionalNoDisponible;
    @FXML
    private Label lbCorreoNoDisponible;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarTiposUsuario();
        configurarListenerCBTipoUsuario();
    }    
    
    public void inicializarInformacionFormulario(boolean esEdicion, Usuario usuario){
        this.esEdicion = esEdicion;
        this.usuarioEdicion = usuario;
        
        if(esEdicion){
            lbTitulo.setText("Edición de usuario");
            btnRegistrarUsuario.setVisible(false);
            cargarInformacionEdicion();   
            inicializarCopiaDeUsuarioEdicion();
        }else{
            lbTitulo.setText("Formulario de usuario");
            btnActualizarUsuario.setVisible(false);
        }
    }
    
    private void inicializarCopiaDeUsuarioEdicion() {
        copiaUsuarioEdicion = new Usuario();
        copiaUsuarioEdicion.setApellidoMaterno(usuarioEdicion.getApellidoMaterno());
        copiaUsuarioEdicion.setApellidoPaterno(usuarioEdicion.getApellidoPaterno());
        copiaUsuarioEdicion.setCodigoRespuesta(usuarioEdicion.getCodigoRespuesta());
        copiaUsuarioEdicion.setCorreo(usuarioEdicion.getCorreo());
        copiaUsuarioEdicion.setFechaCreacion(usuarioEdicion.getFechaCreacion());
        copiaUsuarioEdicion.setIdAcademico(usuarioEdicion.getIdAcademico());
        copiaUsuarioEdicion.setIdEstudiante(usuarioEdicion.getIdEstudiante());
        copiaUsuarioEdicion.setIdTipoUsuario(usuarioEdicion.getIdTipoUsuario());
        copiaUsuarioEdicion.setIdUsuario(usuarioEdicion.getIdUsuario());
        copiaUsuarioEdicion.setMatricula(usuarioEdicion.getMatricula());
        copiaUsuarioEdicion.setNombre(usuarioEdicion.getNombre());
        copiaUsuarioEdicion.setPassword(usuarioEdicion.getPassword());
        copiaUsuarioEdicion.setTelefono(usuarioEdicion.getTelefono());
        copiaUsuarioEdicion.setTipoUsuario(usuarioEdicion.getTipoUsuario());
        copiaUsuarioEdicion.setUsername(usuarioEdicion.getUsername());
    }
    
    private void cargarInformacionEdicion(){
        tfNombre.setText(usuarioEdicion.getNombre());
        tfApellidoPaterno.setText(usuarioEdicion.getApellidoPaterno());
        tfApellidoMaterno.setText(usuarioEdicion.getApellidoMaterno());
        tfTelefono.setText(usuarioEdicion.getTelefono());
        tfCorreo.setText(usuarioEdicion.getCorreo());
        int posicionTipoUsuario = obtenerPosicionComboTipoUsuario(
                usuarioEdicion.getIdTipoUsuario());
        cbTipoUsuario.getSelectionModel().select(posicionTipoUsuario);
        
        tfUsername.setText(usuarioEdicion.getUsername());
        tfPassword.setText(usuarioEdicion.getPassword());
        
        tfCampoAdicional.setEditable(false);
        cbTipoUsuario.setDisable(true);
        switch (usuarioEdicion.getIdTipoUsuario()) {
            case TIPO_USUARIO_ACADEMICO:
                AcademicoRespuesta informacionAcademico = 
                        AcademicoDAO.obtenerInformacionAcademico(usuarioEdicion.getIdUsuario());
                Academico academicoEdicion = informacionAcademico.getAcademicos().get(0);
                tfCampoAdicional.setText(String.valueOf(academicoEdicion.getNumeroDePersonal()));
            break;
            case TIPO_USUARIO_ESTUDIANTE:
                EstudianteRespuesta informacionEstudiante = 
                        EstudianteDAO.obtenerInformacionEstudiante(usuarioEdicion.getIdUsuario());
                Estudiante estudianteEdicion = informacionEstudiante.getEstudiantes().get(0);
                tfCampoAdicional.setText(estudianteEdicion.getMatricula());
            break;
        }
    }
    
    private int obtenerPosicionComboTipoUsuario(int idTipoUsuario){
        for (int i = 0; i < tiposUsuario.size(); i++) {
            if(tiposUsuario.get(i).getIdTipoUsuario()== idTipoUsuario)
                return i;
        }
        return 0;
    }
    
    private void cargarTiposUsuario() {
        tiposUsuario = FXCollections.observableArrayList();
        TipoUsuarioRespuesta tipoUsuarioRespuesta = TipoUsuarioDAO.obtenerInformacionTipoUsuario();
        if (tipoUsuarioRespuesta.getCodigoRespuesta() == Constantes.OPERACION_EXITOSA){ 
            tiposUsuario.addAll(tipoUsuarioRespuesta.getTiposUsuario());
            cbTipoUsuario.setItems(tiposUsuario);
        } else {
            Utilidades.mostrarDialogoSimple("Error al cargar", 
                    "No se pudo cargar la información de tipos de usuario", 
                    Alert.AlertType.ERROR);
        }
    }
    
    private void configurarListenerCBTipoUsuario() {
        cbTipoUsuario.valueProperty().addListener(new ChangeListener<TipoUsuario>(){
            @Override
            public void changed(ObservableValue<? extends TipoUsuario> observable, 
                    TipoUsuario oldValue, TipoUsuario newValue) {
                if(newValue != null){
                                    
                    switch(cbTipoUsuario.getSelectionModel().getSelectedItem().getIdTipoUsuario()){
                        case TIPO_USUARIO_ACADEMICO:
                        case TIPO_USUARIO_ESTUDIANTE:
                            habilitarCampoAdicional();
                        break;
                        case TIPO_USUARIO_ADMINISTRADOR:
                            deshabilitarCampoAdicional();
                        break;
                    }
                }
            }
        });
    }
    
    private void habilitarCampoAdicional() {
        lbCampoAdicional.setDisable(false);
        tfCampoAdicional.setDisable(false);
    }
    
    private void deshabilitarCampoAdicional() {
        lbCampoAdicional.setDisable(true);
        tfCampoAdicional.setDisable(true);
        tfCampoAdicional.setText("");
    }

    @FXML
    private void clicIrAdminUsuarios(ActionEvent event) {
        if (Utilidades.mostrarDialogoConfirmacion(
                "Confirmación de regreso", 
                "¿Está seguro de que desea regresar a la ventana de administración de usuarios? " + 
                "La información ingresada en el formulario se descartará")) {
            irAdminUsuarios();
        }
    }
    
    private void irAdminUsuarios() {
        Stage escenarioBase = (Stage) lbTitulo.getScene().getWindow();
        escenarioBase.setScene(
                Utilidades.inicializarEscena("vistas/FXMLAdminUsuarios.fxml"));
        escenarioBase.setTitle("Administración usuarios");
        escenarioBase.show();
    }

    @FXML
    private void clicBtnRegistrarUsuario(ActionEvent event) {
        validarCamposRegistro();
    }
    
    private void validarCamposRegistro(){
        establecerEstiloNormal();
        boolean datosValidos = true;
        
        String nombre = tfNombre.getText();
        String apellidoPaterno = tfApellidoPaterno.getText();
        String apellidoMaterno = tfApellidoMaterno.getText();
        String telefono = tfTelefono.getText();
        String correo = tfCorreo.getText();
        int posicionTipoUsuario = cbTipoUsuario.getSelectionModel().getSelectedIndex();
        String username = tfUsername.getText();
        String password = tfPassword.getText();
        String campoAdicional = tfCampoAdicional.getText();
        
        if (nombre.isEmpty()) {
            tfNombre.setStyle(Constantes.estiloError);
            datosValidos = false;
        }
        
        if (apellidoPaterno.isEmpty()) {
            tfApellidoPaterno.setStyle(Constantes.estiloError);
            datosValidos = false;
        }
        
        if (apellidoMaterno.isEmpty()) {
            tfApellidoMaterno.setStyle(Constantes.estiloError);
            datosValidos = false;
        }
        
        if(tfTelefono.getLength() != 10) {
            tfTelefono.setStyle(Constantes.estiloError);
            datosValidos = false;
            lbTelefonoError.setText("Número telefónico a 10 dígitos");
        }
        
        if (!Utilidades.correoValido(correo)) {
            tfCorreo.setStyle(Constantes.estiloError);
            datosValidos = false;
        } else {
            if (!esEdicion || (esEdicion && !correo.equals(copiaUsuarioEdicion.getCorreo()))) {
                if (!esCorreoDisponible(correo)) {
                    lbCorreoNoDisponible.setText(
                            "Correo no disponible");
                    tfCorreo.setStyle(Constantes.estiloError);
                    datosValidos = false;
                }
            }
        }
        
        if (posicionTipoUsuario == -1) {
            cbTipoUsuario.setStyle(Constantes.estiloError);
            datosValidos = false;
        } else {
            if (!esEdicion) {
                switch(cbTipoUsuario.getSelectionModel().getSelectedItem().getIdTipoUsuario()) {
                    case TIPO_USUARIO_ACADEMICO:
                        if (campoAdicional.isEmpty()) {
                            tfCampoAdicional.setStyle(Constantes.estiloError);
                            datosValidos = false;
                        } else {
                            try {
                                int numeroDePersonal = Integer.parseInt(campoAdicional);
                                if (!esNumeroDePersonalDisponible(numeroDePersonal)) {
                                    lbCampoAdicionalNoDisponible.setText(
                                            "Número de personal no disponible");
                                    tfCampoAdicional.setStyle(Constantes.estiloError);
                                    datosValidos = false;
                                }
                            } catch (NumberFormatException e) {
                                tfCampoAdicional.setStyle(Constantes.estiloError);
                                datosValidos = false;
                            }
                        }

                    break;
                    case TIPO_USUARIO_ESTUDIANTE:
                        if (campoAdicional.isEmpty()) {
                            tfCampoAdicional.setStyle(Constantes.estiloError);
                            datosValidos = false;    
                        } else {
                            if (!esMatriculaDisponible(campoAdicional)) {
                                lbCampoAdicionalNoDisponible.setText(
                                        "Matrícula no disponible");
                                tfCampoAdicional.setStyle(Constantes.estiloError);
                                datosValidos = false;
                            }
                        }
                    break;
                }
            }
        }
        
        if (username.isEmpty()) {
            tfUsername.setStyle(Constantes.estiloError);
            datosValidos = false;
        } else {
            if (!esEdicion || (esEdicion && !username.equals(copiaUsuarioEdicion.getUsername()))) {
                if (!esUsernameDisponible(username)) {
                    lbUsernameNoDisponible.setText("Nombre de usuario no disponible");
                    tfUsername.setStyle(Constantes.estiloError);
                    datosValidos = false;
                }
            }
        }
        
        if (password.isEmpty()) {
            tfPassword.setStyle(Constantes.estiloError);
            datosValidos = false;
        }
        
        if (datosValidos) {
            int idTipoUsuario = tiposUsuario.get(posicionTipoUsuario).getIdTipoUsuario();
            Usuario usuarioValidado = new Usuario();
            usuarioValidado.setNombre(nombre);
            usuarioValidado.setApellidoPaterno(apellidoPaterno);
            usuarioValidado.setApellidoMaterno(apellidoMaterno);
            usuarioValidado.setTelefono(telefono);
            usuarioValidado.setCorreo(correo);
            usuarioValidado.setIdTipoUsuario(idTipoUsuario);
            usuarioValidado.setUsername(username);
            usuarioValidado.setPassword(password);
            if (esEdicion) {
                usuarioValidado.setIdUsuario(usuarioEdicion.getIdUsuario());
                actualizarUsuario(usuarioValidado);
            } else {
                registrarUsuario(usuarioValidado, campoAdicional);
            }
        }
    }
    
    private boolean esUsernameDisponible(String usernameVerificacion) {
        int coincidencias = UsuarioDAO.verificarDisponibilidadUsername(usernameVerificacion);
        return coincidencias == 0;
    }
    
    private boolean esCorreoDisponible(String correoVerificacion) {
        int coincidencias = UsuarioDAO.verificarDisponibilidadCorreo(correoVerificacion);
        return coincidencias == 0;
    }
    
    private boolean esMatriculaDisponible(String matriculaVerificacion) {
        int coincidencias = EstudianteDAO.verificarDisponibilidadMatricula(matriculaVerificacion);
        return coincidencias == 0;
    }
    
    private boolean esNumeroDePersonalDisponible(int numeroDePersonalVerificacion) {
        int coincidencias = AcademicoDAO.
                verificarDisponibilidadNumeroDePersonal(numeroDePersonalVerificacion);
        return coincidencias == 0;
    }
    
    @FXML
    private void clicBtnActualizarUsuario(ActionEvent event) {
        validarCamposRegistro();
    }
    
    private void actualizarUsuario(Usuario usuarioEdicion){
        int respuesta = UsuarioDAO.modificarUsuario(usuarioEdicion);
        switch(respuesta){
            case Constantes.ERROR_CONEXION:
                Utilidades.mostrarDialogoSimple("Error de conexion", "El usuario " + 
                        "no pudo ser modificado debido a un error de conexión.", 
                        Alert.AlertType.ERROR);
            break;
            case Constantes.ERROR_CONSULTA:
                Utilidades.mostrarDialogoSimple("Error en la información",
                        "La información del estudiante no puede ser modificada, "
                       + "por favor inténtelo más tarde." ,
                        Alert.AlertType.WARNING);
            break;
            case Constantes.OPERACION_EXITOSA:
                Utilidades.mostrarDialogoSimple("Usuario actualizado", 
                        "La información del estudiante fue actualizada correctamente", 
                        Alert.AlertType.INFORMATION);
                irAdminUsuarios();
            break;
        }
    }
    
    private void registrarUsuario(Usuario usuarioRegistro, String campoAdicional){
        UsuarioRespuesta usuarioRespuesta = UsuarioDAO.guardarUsuario(usuarioRegistro);
        
        switch(usuarioRespuesta.getCodigoRespuesta()){
            case Constantes.ERROR_CONEXION:
                Utilidades.mostrarDialogoSimple("Error de conexion", "El usuario " + 
                        "no pudo ser guardado debido a un error de conexión.", 
                        Alert.AlertType.ERROR);
            break;
            case Constantes.ERROR_CONSULTA:
                System.out.println("usuarioRegistro = " + usuarioRegistro);
                Utilidades.mostrarDialogoSimple("Error en la información",
                        "La información del usuario no puede ser guardada,"
                       + "por favor inténtelo más tarde." , 
                        Alert.AlertType.WARNING);
            break;
            case Constantes.OPERACION_EXITOSA:
                switch (usuarioRegistro.getIdTipoUsuario()) {
                    case TIPO_USUARIO_ACADEMICO:
                        Academico academicoValidado = new Academico();
                        academicoValidado.setIdUsuario(usuarioRespuesta.getIdUsuarioRegistrado());
                        academicoValidado.setNumeroDePersonal(Integer.parseInt(campoAdicional));
                        registrarAcademico(academicoValidado);
                    break;
                    case TIPO_USUARIO_ESTUDIANTE:
                        Estudiante estudianteValidado = new Estudiante();
                        estudianteValidado.setIdUsuario(usuarioRespuesta.getIdUsuarioRegistrado());
                        estudianteValidado.setMatricula(campoAdicional);
                        registrarEstudiante(estudianteValidado);
                    break;
                    case TIPO_USUARIO_ADMINISTRADOR:
                        Utilidades.mostrarDialogoSimple("Administrador registrado", 
                            "La información del usuario fue guardada correctamente", 
                            Alert.AlertType.INFORMATION);
                    break;
                }
                irAdminUsuarios();
            break;
        }
    }
    
    private void registrarEstudiante(Estudiante estudianteRegistro){
        int respuesta = EstudianteDAO.guardarEstudiante(estudianteRegistro);
        
        switch(respuesta){
            case Constantes.ERROR_CONEXION:
                Utilidades.mostrarDialogoSimple("Error de conexion", "El estudiante " + 
                        "no pudo ser guardado debido a un error de conexión.", 
                        Alert.AlertType.ERROR);
                UsuarioDAO.eliminarUsuario(estudianteRegistro.getIdUsuario());
            break;
            case Constantes.ERROR_CONSULTA:
                Utilidades.mostrarDialogoSimple("Error en la información",
                        "La información del estudiante no puede ser guardada, "
                       + "por favor inténtelo más tarde." ,
                        Alert.AlertType.WARNING);
                UsuarioDAO.eliminarUsuario(estudianteRegistro.getIdUsuario());
            break;
            case Constantes.OPERACION_EXITOSA:
                Utilidades.mostrarDialogoSimple("Estudiante registrado", 
                        "La información del estudiante fue guardada correctamente", 
                        Alert.AlertType.INFORMATION);
            break;
        }
    }
    
    private void registrarAcademico(Academico academicoRegistro){
        int respuesta = AcademicoDAO.guardarAcademico(academicoRegistro);
        
        switch(respuesta){
            case Constantes.ERROR_CONEXION:
                Utilidades.mostrarDialogoSimple("Error de conexion", "El academico " + 
                        "no pudo ser guardado debido a un error de conexión.", 
                        Alert.AlertType.ERROR);
                UsuarioDAO.eliminarUsuario(academicoRegistro.getIdUsuario());
            break;
            case Constantes.ERROR_CONSULTA:
                Utilidades.mostrarDialogoSimple("Error en la información",
                        "La información del academico no puede ser guardada, "
                       + "por favor inténtelo más tarde." ,
                        Alert.AlertType.WARNING);
                UsuarioDAO.eliminarUsuario(academicoRegistro.getIdUsuario());
            break;
            case Constantes.OPERACION_EXITOSA:
                Utilidades.mostrarDialogoSimple("Academico registrado", 
                        "La información del academico fue guardada correctamente", 
                        Alert.AlertType.INFORMATION);
            break;
        }
    }
    
    private void establecerEstiloNormal() {
        tfUsername.setStyle(Constantes.estiloNormal);
        tfPassword.setStyle(Constantes.estiloNormal);
        tfNombre.setStyle(Constantes.estiloNormal);
        tfApellidoPaterno.setStyle(Constantes.estiloNormal);
        tfApellidoMaterno.setStyle(Constantes.estiloNormal);
        tfCorreo.setStyle(Constantes.estiloNormal);
        tfTelefono.setStyle(Constantes.estiloNormal);
        lbTelefonoError.setText("");
        lbCampoAdicionalNoDisponible.setText("");
        lbUsernameNoDisponible.setText("");
        lbCorreoNoDisponible.setText("");
        cbTipoUsuario.setStyle(Constantes.estiloNormal);
        tfCampoAdicional.setStyle(Constantes.estiloNormal);
    }

    @FXML
    private void clicBtnCancelar(ActionEvent event) {
        if (mostrarDialogoConfirmacionCancelacion()) {
            irAdminUsuarios();
        }
    }
    
    private boolean mostrarDialogoConfirmacionCancelacion() {
        if (esEdicion) {
            return Utilidades.mostrarDialogoConfirmacion(
                "Confirmación de cancelación", 
                "¿Está seguro de que desea cancelar la actualización de "
                + "información del usuario? " + 
                "La información modificada se descartará");
        } else {
            return Utilidades.mostrarDialogoConfirmacion(
                "Confirmación de cancelación", 
                "¿Está seguro de que desea cancelar el registro del usuario? " + 
                "La información ingresada en el formulario se descartará");
        }
        
    }

    @FXML
    protected void clicIrCursos(ActionEvent event) {
        if (mostrarDialogoConfirmacionCancelacion()) {
            super.clicIrCursos(event);
        }
    }

    @FXML
    protected void clicIrLgac(ActionEvent event) {
        if (mostrarDialogoConfirmacionCancelacion()) {
            super.clicIrLgac(event);
        }
    }

    @FXML
    protected void clicIrUsuarios(ActionEvent event) {
        if (mostrarDialogoConfirmacionCancelacion()) {
            super.clicIrUsuarios(event);
        }
    }

    @FXML
    protected void clicIrCuerposAcademicos(ActionEvent event) {
        if (mostrarDialogoConfirmacionCancelacion()) {
            super.clicIrCuerposAcademicos(event);
        }
    }
    
    @FXML
    private void permitirInputSoloNumeros(KeyEvent event) {
        String entrada = event.getCharacter();
        if (!".0123456789".contains(entrada)) 
            event.consume();
    }
}
