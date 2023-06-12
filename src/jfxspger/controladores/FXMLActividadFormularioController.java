/*
* Autor: Carlos Rodriguez Herrera
* Fecha de creación: 18/05/2023
* Descripción: Controlador para el formulario de actividades
*/

package jfxspger.controladores;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import jfxspger.interfaz.INotificacionOperacionActividad;
import jfxspger.modelo.dao.ActividadDAO;
import jfxspger.modelo.pojo.Actividad;
import jfxspger.modelo.pojo.Estudiante;
import jfxspger.utilidades.Constantes;
import jfxspger.utilidades.Utilidades;

public class FXMLActividadFormularioController implements Initializable {

    @FXML
    private TextField tfNombreActividad;
    @FXML
    private TextField tfDescripcionActividad;    
    @FXML
    private DatePicker dpFechaInicio;
    @FXML
    private DatePicker dpFechaFin;
    private Actividad actividadEdicion;
    private Estudiante estudiante;
    private boolean esEdicion;
    private INotificacionOperacionActividad interfazNotificacion;
    
    String estiloError = "-fx-border-color: RED; -fx-border-width: 2; -fx-border-radius: 2;";
    String estiloNormal = "-fx-border-width: 0;";
    
    @FXML
    private Label lbTitulo;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        dpFechaInicio.setEditable(false);
        dpFechaFin.setEditable(false);
    }

    public void inicializarInformacionFormulario(boolean esEdicion, Actividad actividadEdicion, INotificacionOperacionActividad interfazNotificacion){
        this.esEdicion = esEdicion;
        this.actividadEdicion = actividadEdicion;
        this.interfazNotificacion = interfazNotificacion;
        
        if(esEdicion){
            lbTitulo.setText("Editanto actividad: " + actividadEdicion.getTitulo());
            cargarInformacionEdicion();
        } else {
            lbTitulo.setText("Programar nueva actividad");
        }
    }
    
    private void cargarInformacionEdicion(){
        tfNombreActividad.setText(actividadEdicion.getTitulo());
        tfDescripcionActividad.setText(actividadEdicion.getDescripcion());
        dpFechaInicio.setValue(LocalDate.parse(actividadEdicion.getFechaInicio()));
        dpFechaFin.setValue(LocalDate.parse(actividadEdicion.getFechaFin()));
    }

    @FXML
    private void clicBtnGuardar(ActionEvent event) {
        validarCampos();
    }
    
    private void validarCampos(){
        establecerEstiloNormal();
        boolean datosValidos = true;
        
        String nomAct = tfNombreActividad.getText();
        String desc = tfDescripcionActividad.getText();
        LocalDate fechaInicio = dpFechaInicio.getValue();
        LocalDate fechaFin = dpFechaFin.getValue(); 
        LocalDate fechaCreacion = LocalDate.now();
//        int idEstudiante = estudiante.getIdEstudiante();
        
        //VALIDACIONES
        if(nomAct.isEmpty()){
            tfNombreActividad.setStyle(estiloError);
            datosValidos = false;
        }
        
        if(desc.isEmpty()){
            tfDescripcionActividad.setStyle(estiloError);
            datosValidos = false;
        }

        if(fechaFin == null){
            dpFechaFin.setStyle(estiloError);
            datosValidos = false;
        }
        
        if(fechaInicio == null){
            dpFechaInicio.setStyle(estiloError);
            datosValidos = false;
        }        
        
        if(fechaFin.isBefore(fechaInicio)){
            dpFechaInicio.setStyle(estiloError);
            dpFechaFin.setStyle(estiloError);
            datosValidos = false;            
        }        
        
        if(datosValidos){
            Actividad actividadValida = new Actividad();
            actividadValida.setTitulo(nomAct);
            actividadValida.setDescripcion(desc);
            actividadValida.setFechaInicio(fechaInicio.toString());
            actividadValida.setFechaFin(fechaFin.toString());
            actividadValida.setFechaCreacion(fechaCreacion.toString());
//            actividadValida.setIdEstudiante(idEstudiante);
            
            if(esEdicion){
                actualizarActividad(actividadValida);
            } else {
                registrarActividad(actividadValida);                
            }
        }
    }
    
    private void registrarActividad(Actividad actividadNueva){
        int codigoRespuesta = ActividadDAO.guardarActividad(actividadNueva);
        switch(codigoRespuesta){
            case Constantes.ERROR_CONEXION:
                Utilidades.mostrarDialogoSimple("Sin conexión", "Lo sentimos, por el momento no hay conexión", Alert.AlertType.ERROR);
                break;
            case Constantes.ERROR_CONSULTA:
                Utilidades.mostrarDialogoSimple("Error al registrar la actividad", 
                        "Hubo un error al registrar la actividad. Por favor intente más tarde", Alert.AlertType.WARNING);
                break;
            case Constantes.OPERACION_EXITOSA:
                Utilidades.mostrarDialogoSimple("Actividad registrada", "Actividad registrada correctamente", 
                        Alert.AlertType.INFORMATION);
                cerrarVentana();
                break;
        }
    }
    
    private void actualizarActividad(Actividad actividadActualizada){
        int codigoRespuesta = ActividadDAO.modificarActividad(actividadActualizada);
        switch(codigoRespuesta){
            case Constantes.ERROR_CONEXION:
                Utilidades.mostrarDialogoSimple("Sin conexión", "Lo sentimos, por el momento no hay conexión", Alert.AlertType.ERROR);
                break;
            case Constantes.ERROR_CONSULTA:
                Utilidades.mostrarDialogoSimple("Error al cargar los datos", 
                        "Hubo un error al cargar la información. Por favor intente más tarde", Alert.AlertType.WARNING);
                break;
            case Constantes.OPERACION_EXITOSA:
                Utilidades.mostrarDialogoSimple("Actividad modificada", "Actividad modificada correctamente", 
                        Alert.AlertType.INFORMATION);
                cerrarVentana();
                break;
        }
        
    }
    
    private void cerrarVentana(){
        Stage escenarioBase = (Stage) tfNombreActividad.getScene().getWindow();
        escenarioBase.close();
    }
    
    private void establecerEstiloNormal(){
        tfNombreActividad.setStyle(estiloNormal);
        dpFechaInicio.setStyle(estiloNormal);
        dpFechaFin.setStyle(estiloNormal);
        tfDescripcionActividad.setStyle(estiloNormal);
    }

    private void clicBtnRegresar(MouseEvent event) {   
    }

    @FXML
    private void clicIrAnteproyecto(ActionEvent event) {
    }

    @FXML
    private void clicIrCronograma(ActionEvent event) {
    }

    @FXML
    private void clicIrCursos(ActionEvent event) {
    }

    @FXML
    private void clicCerrarSesion(ActionEvent event) {
        if (Utilidades.mostrarDialogoConfirmacion(
                "Cerrar sesión", 
                "¿Está seguro de que desea cerrar sesión?")) {
            irVentanaInicioSesion();
        }        
    }
    
        private void irVentanaInicioSesion() {
        Stage escenarioBase = (Stage) lbTitulo.getScene().getWindow();
        escenarioBase.setScene(
                Utilidades.inicializarEscena("vistas/FXMLInicioSesion.fxml"));
        escenarioBase.setTitle("Inicio de sesion");
        escenarioBase.show();
    }

    @FXML
    private void clicIrPropuestas(ActionEvent event) {
    }

    @FXML
    private void clicIrPrincipalEstudiante(ActionEvent event) {
                boolean cerrarVentana = Utilidades.mostrarDialogoConfirmacion("Regresar a ventana anterior", "¿Desea regresar a la ventana anterior? No se guardaran los datos ingresados.");
        if(cerrarVentana){
            cerrarVentana();
        }
    }
}