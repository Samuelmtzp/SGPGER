/*
* Autor: Samuel Martínez Pazos
* Fecha de creación: 13/06/2023
* Descripción: Clase controladora de formulario de cuerpo académico
*/
package jfxspger.controladores;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import jfxspger.modelo.dao.GradoConsolidacionDAO;
import jfxspger.modelo.dao.CuerpoAcademicoDAO;
import jfxspger.modelo.dao.DependenciaDAO;
import jfxspger.modelo.dao.UsuarioDAO;
import jfxspger.modelo.pojo.CuerpoAcademico;
import jfxspger.modelo.pojo.Dependencia;
import jfxspger.modelo.pojo.DependenciaRespuesta;
import jfxspger.modelo.pojo.GradoConsolidacion;
import jfxspger.modelo.pojo.GradoConsolidacionRespuesta;
import jfxspger.modelo.pojo.Usuario;
import jfxspger.modelo.pojo.UsuarioRespuesta;
import jfxspger.utilidades.Constantes;
import jfxspger.utilidades.Utilidades;

public class FXMLFormularioCuerpoAcademicoController extends FXMLPrincipalAdministradorController {

    @FXML
    private Label lbTitulo;
    @FXML
    private ComboBox<GradoConsolidacion> cbGradoConsolidacion;
    @FXML
    private ComboBox<Usuario> cbResponsable;
    @FXML
    private ComboBox<Dependencia> cbDependencia;
    @FXML
    private TextField tfNombre;
    @FXML
    private TextField tfClave;
    @FXML
    private Button btnRegistrarCuerpoAcademico;
    @FXML
    private Button btnActualizarCuerpoAcademico;
    @FXML
    private Label lbNombreNoDisponible;
    @FXML
    private Label lbClaveNoDisponible;
    private CuerpoAcademico cuerpoAcademicoEdicion;
    private boolean esEdicion;
    private ObservableList<GradoConsolidacion> gradosConsolidacion;
    private ObservableList<Usuario> academicos;
    private ObservableList<Dependencia> dependencias;
    private CuerpoAcademico copiaCuerpoAcademicoEdicion;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarGradosConsolidacion();
        cargarAcademicosDisponibles();
        cargarDependencias();
    }   
    
    public void inicializarInformacionFormulario(boolean esEdicion, 
            CuerpoAcademico cuerpoAcademicoEdicion) {
        this.esEdicion = esEdicion;
        this.cuerpoAcademicoEdicion = cuerpoAcademicoEdicion;
        if (esEdicion) {
            lbTitulo.setText("Edición de cuerpo académico");
            btnRegistrarCuerpoAcademico.setVisible(false);
            inicializarCopiaCuerpoAcademicoEdicion();
            cargarInformacionEdicion();
        }else{
            lbTitulo.setText("Formulario de cuerpo académico");
            btnActualizarCuerpoAcademico.setVisible(false);
        }
    }
    
    private void inicializarCopiaCuerpoAcademicoEdicion() {
        copiaCuerpoAcademicoEdicion = new CuerpoAcademico();
        copiaCuerpoAcademicoEdicion.setClave(cuerpoAcademicoEdicion.getClave());
        copiaCuerpoAcademicoEdicion.setDependencia(cuerpoAcademicoEdicion.getDependencia());
        copiaCuerpoAcademicoEdicion.setGradoConsolidacion(
                cuerpoAcademicoEdicion.getGradoConsolidacion());
        copiaCuerpoAcademicoEdicion.setIdCuerpoAcademico(
                cuerpoAcademicoEdicion.getIdCuerpoAcademico());
        copiaCuerpoAcademicoEdicion.setIdDependencia(cuerpoAcademicoEdicion.getIdDependencia());
        copiaCuerpoAcademicoEdicion.setIdGradoConsolidacion(
                cuerpoAcademicoEdicion.getIdGradoConsolidacion());
        copiaCuerpoAcademicoEdicion.setIdResponsable(cuerpoAcademicoEdicion.getIdResponsable());
        copiaCuerpoAcademicoEdicion.setNombre(cuerpoAcademicoEdicion.getNombre());
        copiaCuerpoAcademicoEdicion.setNombreCompletoResponsable(
                cuerpoAcademicoEdicion.getNombreCompletoResponsable());
    }
    
    private void cargarInformacionEdicion(){
        tfClave.setText(cuerpoAcademicoEdicion.getClave());
        tfNombre.setText(cuerpoAcademicoEdicion.getNombre());
        int posicionDependencia = obtenerPosicionComboDependencia(
                cuerpoAcademicoEdicion.getIdDependencia());
        
        if (cuerpoAcademicoEdicion.getIdResponsable() != -1) {
            cbResponsable.getItems().add(obtenerResponsableEdicion());
            int posicionResponsable = obtenerPosicionComboResponsable(
                    cuerpoAcademicoEdicion.getIdResponsable());
            cbResponsable.getSelectionModel().select(posicionResponsable);
        }
        int posicionGradoConsolidacion = obtenerPosicionGradoConsolidacion(
                cuerpoAcademicoEdicion.getIdGradoConsolidacion());
        cbDependencia.getSelectionModel().select(posicionDependencia);
        cbGradoConsolidacion.getSelectionModel().select(posicionGradoConsolidacion);
    }
    
    // Debido a que el DAO de responsable solo obtiene los academicos que 
    // estan disponibles, se debe de incluir también el responsable actual
    private Usuario obtenerResponsableEdicion() {
        UsuarioRespuesta usuarioRespuesta = UsuarioDAO.
                obtenerInformacionAcademicoEnCuerpoAcademico(
                cuerpoAcademicoEdicion.getIdCuerpoAcademico());
        return usuarioRespuesta.getUsuarios().get(0);
    }
    
    private int obtenerPosicionComboDependencia(int idDependencia) {
        for (int i = 0; i < dependencias.size(); i++) {
            if(dependencias.get(i).getIdDependencia()== idDependencia)
                return i;
        }
        return -1;
    }
    
    private int obtenerPosicionComboResponsable(int idResponsable) {
        for (int i = 0; i < academicos.size(); i++) {
            if(academicos.get(i).getIdAcademico()== idResponsable)
                return i;
        }
        return -1;
    }
    
    private int obtenerPosicionGradoConsolidacion(int idGradoConsolidacion) {
        for (int i = 0; i < gradosConsolidacion.size(); i++) {
            if(gradosConsolidacion.get(i).getIdGradoConsolidacion()== idGradoConsolidacion)
                return i;
        }
        return -1;
    }
    
    private void cargarGradosConsolidacion() {
        gradosConsolidacion = FXCollections.observableArrayList();
        GradoConsolidacionRespuesta gradosConsolidacionRespuesta = 
                GradoConsolidacionDAO.obtenerInformacionGradoConsolidacion();
        if (gradosConsolidacionRespuesta.getCodigoRespuesta() == Constantes.OPERACION_EXITOSA){ 
            gradosConsolidacion.addAll(
                    gradosConsolidacionRespuesta.getGradosConsolidacion());
            cbGradoConsolidacion.setItems(gradosConsolidacion);
        } else {
            Utilidades.mostrarDialogoSimple("Error al cargar", 
                    "No se pudo cargar la información de los grados de consolidacion", 
                    Alert.AlertType.ERROR);
        }
    }
    
    private void cargarAcademicosDisponibles() {
        academicos = FXCollections.observableArrayList();
        UsuarioRespuesta academicosDisponiblesRespuesta = 
                UsuarioDAO.obtenerInformacionAcademicosDisponibles();
        if (academicosDisponiblesRespuesta.getCodigoRespuesta() == Constantes.OPERACION_EXITOSA){ 
            academicos.addAll(
                    academicosDisponiblesRespuesta.getUsuarios());
            cbResponsable.setItems(academicos);
        } else {
            Utilidades.mostrarDialogoSimple("Error al cargar", 
                    "No se pudo cargar la información de los academicos disponibles", 
                    Alert.AlertType.ERROR);
        }
    }
    
    private void cargarDependencias() {
        dependencias = FXCollections.observableArrayList();
        DependenciaRespuesta dependenciasRespuesta = 
                DependenciaDAO.obtenerInformacionDependencia();
        if (dependenciasRespuesta.getCodigoRespuesta() == Constantes.OPERACION_EXITOSA){ 
            dependencias.addAll(
                    dependenciasRespuesta.getDependencias());
            cbDependencia.setItems(dependencias);
        } else {
            Utilidades.mostrarDialogoSimple("Error al cargar", 
                    "No se pudo cargar la información de las dependencias", 
                    Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void clicIrAdminCuerposAcademicos(ActionEvent event) {
        if (Utilidades.mostrarDialogoConfirmacion(
                "Confirmación de regreso", 
                "¿Está seguro de que desea regresar a la ventana de administración de cuerpos " + 
                "académicos? La información ingresada en el formulario se descartará")) {
            irAdminCuerposAcademicos();
        }
    }
    
    private void irAdminCuerposAcademicos() {
        Stage escenarioBase = (Stage) lbTitulo.getScene().getWindow();
        escenarioBase.setScene(
                Utilidades.inicializarEscena("vistas/FXMLAdminCuerposAcademicos.fxml"));
        escenarioBase.setTitle("Administración cuerpos académicos");
        escenarioBase.show();
    }

    @FXML
    private void clicBtnRegistrarCuerpoAcademico(ActionEvent event) {
        validarCamposRegistro();
    }
    
    private void validarCamposRegistro(){
        establecerEstiloNormal();
        boolean datosValidos = true;
        
        int posicionGradoConsolidacion = cbGradoConsolidacion.
                getSelectionModel().getSelectedIndex();
        int posicionResponsable = cbResponsable.
                getSelectionModel().getSelectedIndex();
        int posicionDependencia = cbDependencia.
                getSelectionModel().getSelectedIndex();
        String nombre = tfNombre.getText();
        String clave = tfClave.getText();
        
        if (nombre.trim().isEmpty()) {
            tfNombre.setStyle(Constantes.estiloError);
            datosValidos = false;
        } else if (nombre.length() > 255) {
            tfNombre.setStyle(Constantes.estiloError);
            datosValidos = false;
        } else {
            if (!esEdicion || (esEdicion && !nombre.equals(
                    copiaCuerpoAcademicoEdicion.getNombre()))) {
                if (!esNombreDisponible(nombre)) {
                    lbNombreNoDisponible.setText("Nombre no disponible");
                    tfNombre.setStyle(Constantes.estiloError);
                    datosValidos = false;
                }
            }
        }
        
        if (clave.trim().isEmpty()) {
            tfClave.setStyle(Constantes.estiloError);
            datosValidos = false;
        } else if (clave.length() > 20) {
            tfClave.setStyle(Constantes.estiloError);
            datosValidos = false;
        } else {
            if (!esEdicion || (esEdicion && !clave.equals(
                    copiaCuerpoAcademicoEdicion.getClave()))) {
                if (!esClaveDisponible(clave)) {
                    lbClaveNoDisponible.setText("Clave no disponible");
                    tfClave.setStyle(Constantes.estiloError);
                    datosValidos = false;
                }
            }
        }
        
        if (posicionGradoConsolidacion == -1) {
            cbGradoConsolidacion.setStyle(Constantes.estiloError);
            datosValidos = false;
        }
        
        if (posicionResponsable == -1) {
            cbResponsable.setStyle(Constantes.estiloError);
            datosValidos = false;
        }
        
        if (posicionDependencia == -1) {
            cbDependencia.setStyle(Constantes.estiloError);
            datosValidos = false;
        }
        
        if (datosValidos) {
            int idGradoConsolidacion = gradosConsolidacion.get(
                    posicionGradoConsolidacion).getIdGradoConsolidacion();
            int idResponsable = academicos.get(
                    posicionResponsable).getIdAcademico();
            int idDependencia = dependencias.get(
                    posicionDependencia).getIdDependencia();
            CuerpoAcademico cuerpoAcademicoValidado = new CuerpoAcademico();
            cuerpoAcademicoValidado.setClave(clave);
            cuerpoAcademicoValidado.setIdDependencia(idDependencia);
            cuerpoAcademicoValidado.setIdGradoConsolidacion(idGradoConsolidacion);
            cuerpoAcademicoValidado.setIdResponsable(idResponsable);
            cuerpoAcademicoValidado.setNombre(nombre);
            
            if (esEdicion) {
                cuerpoAcademicoValidado.setIdCuerpoAcademico(
                        cuerpoAcademicoEdicion.getIdCuerpoAcademico());
                actualizarCuerpoAcademico(cuerpoAcademicoValidado);
            } else {
                registrarCuerpoAcademico(cuerpoAcademicoValidado);
            }
        }   
    }
    
    private boolean esNombreDisponible(String nombreVerificacion) {
        int coincidencias = CuerpoAcademicoDAO.verificarDisponibilidadNombre(nombreVerificacion);
        return coincidencias == 0;
    }
     
    private boolean esClaveDisponible(String claveVerificacion) {
        int coincidencias = CuerpoAcademicoDAO.verificarDisponibilidadClave(claveVerificacion);
        return coincidencias == 0;
    }
    
    private void establecerEstiloNormal() {
        cbDependencia.setStyle(Constantes.estiloNormal);
        cbGradoConsolidacion.setStyle(Constantes.estiloNormal);
        cbResponsable.setStyle(Constantes.estiloNormal);
        tfNombre.setStyle(Constantes.estiloNormal);
        tfClave.setStyle(Constantes.estiloNormal);
        lbClaveNoDisponible.setText("");
        lbNombreNoDisponible.setText("");
    }
    
    private void registrarCuerpoAcademico(CuerpoAcademico cuerpoAcademicoRegistro) {
        int respuesta = CuerpoAcademicoDAO.guardarCuerpoAcademico(cuerpoAcademicoRegistro);
        
        switch(respuesta){
            case Constantes.ERROR_CONEXION:
                Utilidades.mostrarDialogoSimple("Error de conexion", "El cuerpo académico " + 
                        "no pudo ser guardado debido a un error de conexión.", 
                        Alert.AlertType.ERROR);
            break;
            case Constantes.ERROR_CONSULTA:
                Utilidades.mostrarDialogoSimple("Error en la información",
                        "La información del cuerpo académico no puede ser guardada, "
                       + "por favor por favor inténtelo más tarde" ,
                        Alert.AlertType.WARNING);
            break;
            case Constantes.OPERACION_EXITOSA:
                Utilidades.mostrarDialogoSimple("Cuerpo académico registrado", 
                        "La información del cuerpo académico fue guardada correctamente", 
                        Alert.AlertType.INFORMATION);
                irAdminCuerposAcademicos();
            break;
        }
    }
    
    private void actualizarCuerpoAcademico(CuerpoAcademico cuerpoAcademicoModificacion) {
        int respuesta = CuerpoAcademicoDAO.modificarCuerpoAcademico(cuerpoAcademicoModificacion);
        
        switch(respuesta){
            case Constantes.ERROR_CONEXION:
                Utilidades.mostrarDialogoSimple("Error de conexion", "El cuerpo académico " + 
                        "no pudo ser modificado debido a un error de conexión.", 
                        Alert.AlertType.ERROR);
            break;
            case Constantes.ERROR_CONSULTA:
                Utilidades.mostrarDialogoSimple("Error en la información",
                        "La información del cuerpo académico no puede ser modificada, "
                       + "por favor inténtelo más tarde" ,
                        Alert.AlertType.WARNING);
            break;
            case Constantes.OPERACION_EXITOSA:
                Utilidades.mostrarDialogoSimple("Cuerpo académico modificado", 
                        "La información del cuerpo académico fue modificada correctamente", 
                        Alert.AlertType.INFORMATION);
                irAdminCuerposAcademicos();
            break;
        }
    }

    @FXML
    private void clicBtnCancelar(ActionEvent event) {
        if (mostrarDialogoConfirmacionCancelacion()) {
            irAdminCuerposAcademicos();
        }
    }
    
    private boolean mostrarDialogoConfirmacionCancelacion() {
        
        if (esEdicion) {
            return Utilidades.mostrarDialogoConfirmacion(
                "Confirmación de cancelación", 
                "¿Está seguro de que desea cancelar la actualización de "
                + "información del cuerpo académico? " + 
                "La información modificada se descartará");
        } else {
            return Utilidades.mostrarDialogoConfirmacion(
                "Confirmación de cancelación", 
                "¿Está seguro de que desea cancelar el registro del cuerpo académico? " + 
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
    private void clicBtnActualizarCuerpoAcademico(ActionEvent event) {
        validarCamposRegistro();
    }

}
