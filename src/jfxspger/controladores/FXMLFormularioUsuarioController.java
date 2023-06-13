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
import jfxspger.modelo.pojo.Estudiante;
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
            cargarInformacionEdicion();
            
        }else{
            lbTitulo.setText("Formulario de usuario");
        }
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
        
//        switch (usuarioEdicion.getIdTipoUsuario()) {
//            case TIPO_USUARIO_ACADEMICO:
//                
//                tfCampoAdicional.setText();
//                tfCampoAdicional.setEditable(false);
//            break;
//            case TIPO_USUARIO_ESTUDIANTE:
//                tfCampoAdicional.setText();
//                tfCampoAdicional.setEditable(false);
//                
//            break;
//            case TIPO_USUARIO_ADMINISTRADOR:
//                
//            break;
//        }
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
                                    
                    switch(cbTipoUsuario.getSelectionModel().getSelectedItem().getIdTipoUsuario()) {
                        case TIPO_USUARIO_ACADEMICO:
                            habilitarCampoAdicional();
                        break;
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
        }
        
        if (posicionTipoUsuario == -1) {
            cbTipoUsuario.setStyle(Constantes.estiloError);
            datosValidos = false;
        } else {
            switch(cbTipoUsuario.getSelectionModel().getSelectedItem().getIdTipoUsuario()) {
                case TIPO_USUARIO_ACADEMICO:
                    try {
                        Integer.parseInt(tfCampoAdicional.getText());
                    } catch (NumberFormatException e) {
                        tfCampoAdicional.setStyle(Constantes.estiloError);
                        datosValidos = false;
                    }
                    
                    if (campoAdicional.isEmpty()) {
                        tfCampoAdicional.setStyle(Constantes.estiloError);
                        datosValidos = false;
                    }
                break;
                case TIPO_USUARIO_ESTUDIANTE:
                    if (campoAdicional.isEmpty()) {
                        tfCampoAdicional.setStyle(Constantes.estiloError);
                        datosValidos = false;    
                    }
                break;
            }
        }
        
        if (username.isEmpty()) {
            tfUsername.setStyle(Constantes.estiloError);
            datosValidos = false;
        }
        
        if (password.isEmpty()) {
            tfPassword.setStyle(Constantes.estiloError);
            datosValidos = false;
        }     
        
        if (datosValidos) {
            System.out.println("DatosValidos = " + datosValidos);
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
            
            registrarUsuario(usuarioValidado, campoAdicional);
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
                Utilidades.mostrarDialogoSimple("Error en la información",
                        "La información del usuario no puede ser guardada,"
                       + "por favor verifique que sea correcta" , 
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
        System.out.println("idUsuario = " + estudianteRegistro.getIdUsuario());
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
                       + "por favor verifique que sea correcta" ,
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
                       + "por favor verifique que sea correcta" ,
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
        return Utilidades.mostrarDialogoConfirmacion(
                "Confirmación de cancelación", 
                "¿Está seguro de que desea cancelar el registro del usuario? " + 
                "La información ingresada en el formulario se descartará");
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
