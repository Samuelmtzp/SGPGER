/*
* Autor: Carlos Rodriguez Herrera
* Fecha de creación: 18/05/2023
* Descripción: Controlador para el formulario de actividades
*/

package jfxspger.controladores;

import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import jfxspger.modelo.dao.ActividadDAO;
import jfxspger.modelo.pojo.Actividad;
import jfxspger.modelo.pojo.Estudiante;
import jfxspger.utilidades.Constantes;
import jfxspger.utilidades.SingletonUsuario;
import jfxspger.utilidades.Utilidades;

public class FXMLActividadFormularioController extends FXMLPrincipalEstudianteController {

    @FXML
    private TextField tfNombreActividad;
    @FXML
    private TextField tfDescripcionActividad;    
    @FXML
    private DatePicker dpFechaInicio;
    @FXML
    private DatePicker dpFechaFin;
    @FXML
    private Spinner<Integer> spMinutosInicio;
    @FXML
    private Spinner<Integer> spMinutosFin;
    @FXML
    private Spinner<Integer> spHorasInicio;
    @FXML
    private Spinner<Integer> spHorasFin;
    @FXML
    private Button bEliminar;
    
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");
    private Actividad actividadEdicion;
    private Estudiante estudiante;
    private boolean esEdicion;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        dpFechaInicio.setEditable(false);
        dpFechaFin.setEditable(false);        
        configurarSeleccionHora();
        validarSeccionesPermitidas();
    }
    
    private void configurarSeleccionHora() {
        final int valorInicial = 0;
        final int valorMaximoHoras = 23;
        final int valorMaximoMinutos = 59;

        SpinnerValueFactory<Integer> horasInicioValueFactory = 
                new SpinnerValueFactory.IntegerSpinnerValueFactory(
                valorInicial, valorMaximoHoras);
        horasInicioValueFactory.setValue(valorInicial);
        spHorasInicio.setValueFactory(horasInicioValueFactory);

        SpinnerValueFactory<Integer> minutosInicioValueFactory = 
                new SpinnerValueFactory.IntegerSpinnerValueFactory(
                valorInicial, valorMaximoMinutos);
        minutosInicioValueFactory.setValue(valorInicial);
        spMinutosInicio.setValueFactory(minutosInicioValueFactory);

        SpinnerValueFactory<Integer> horasFinValueFactory = 
                new SpinnerValueFactory.IntegerSpinnerValueFactory(
                valorInicial, valorMaximoHoras);
        horasFinValueFactory.setValue(valorInicial);
        spHorasFin.setValueFactory(horasFinValueFactory);

        SpinnerValueFactory<Integer> minutosFinValueFactory = 
                new SpinnerValueFactory.IntegerSpinnerValueFactory(
                valorInicial, valorMaximoMinutos);
        minutosFinValueFactory.setValue(valorInicial);
        spMinutosFin.setValueFactory(minutosFinValueFactory);
    }
    
    public void inicializarInformacionFormulario(boolean esEdicion, Actividad actividadEdicion){
        this.esEdicion = esEdicion;
        this.actividadEdicion = actividadEdicion;
        
        if(esEdicion){
            lbTitulo.setText("Editando actividad: " + actividadEdicion.getTitulo()); 
            bEliminar.setVisible(true);
            cargarInformacionEdicion();
        } else {
            bEliminar.setVisible(false);
            lbTitulo.setText("Programar nueva actividad");
        }
    }
    
    private void cargarInformacionEdicion(){
        tfNombreActividad.setText(actividadEdicion.getTitulo());
        tfDescripcionActividad.setText(actividadEdicion.getDescripcion());
        LocalDateTime fechaInicio = LocalDateTime.parse(
                actividadEdicion.getFechaInicio(), formatter);
        LocalDateTime fechaFin = LocalDateTime.parse(
                actividadEdicion.getFechaFin(), formatter);
        int minutosInicio = fechaInicio.getMinute();
        int minutosFin = fechaFin.getMinute();
        int horasInicio = fechaInicio.getHour();
        int horasFin = fechaFin.getHour();
        LocalDate fInicio = fechaInicio.toLocalDate();
        LocalDate fFin = fechaFin.toLocalDate();
        spMinutosInicio.getValueFactory().setValue(minutosInicio);
        spMinutosFin.getValueFactory().setValue(minutosFin);
        spHorasInicio.getValueFactory().setValue(horasInicio);
        spHorasFin.getValueFactory().setValue(horasFin);
        dpFechaInicio.setValue(fInicio);
        dpFechaFin.setValue(fFin);       
    }

    @FXML
    private void clicBtnGuardar(ActionEvent event) {
        validarCampos();
    }
    
        @FXML
    private void clicBtnEliminarActividad(ActionEvent event) {
        
        boolean eliminarAct = Utilidades.mostrarDialogoConfirmacion(
                "Eliminar actividad", 
                "¿Estás seguro que deseas eliminar la actividad?");
        
        if(eliminarAct){
            eliminarActividad(actividadEdicion);
        }
    }
    
    private void validarCampos(){
        establecerEstiloNormal();
        boolean datosValidos = true;        
        String nomAct = tfNombreActividad.getText().trim();
        String desc = tfDescripcionActividad.getText().trim();
        LocalDate fechaInicio = dpFechaInicio.getValue();
        LocalDate fechaFin = dpFechaFin.getValue();
        LocalTime tiempoInicio = LocalTime.of(spHorasInicio.getValue(), 
                spMinutosInicio.getValue());
        LocalTime tiempoFin = LocalTime.of(spHorasFin.getValue(), spMinutosFin.getValue());        
        
        if(nomAct.isEmpty()){
            tfNombreActividad.setStyle(Constantes.estiloError);
            datosValidos = false;
        }            
                
        if(desc.isEmpty()){
            tfDescripcionActividad.setStyle(Constantes.estiloError);
            datosValidos = false;
        }
        
        if(fechaFin == null){
            dpFechaFin.setStyle(Constantes.estiloError);
            datosValidos = false;
        }
        
        if(fechaInicio == null){
            dpFechaInicio.setStyle(Constantes.estiloError);            
            datosValidos = false;
        }        
        
        if(fechaFin.isBefore(fechaInicio)){
            dpFechaInicio.setStyle(Constantes.estiloError);
            dpFechaFin.setStyle(Constantes.estiloError);            
            datosValidos = false;            
        }        
        
        if(datosValidos){
            Actividad actividadValida = new Actividad();
            actividadValida.setIdEstudiante(
                    SingletonUsuario.getInstancia().getUsuario().getIdEstudiante());
            actividadValida.setTitulo(nomAct);
            actividadValida.setFechaInicio(fechaInicio.toString() + " " + tiempoInicio.toString());
            actividadValida.setFechaFin(fechaFin.toString() + " " + tiempoFin.toString());
            actividadValida.setDescripcion(desc);            
            
            if(esEdicion){
                actividadValida.setIdActividad(actividadEdicion.getIdActividad());
                actualizarActividad(actividadValida);
            } else {
                LocalDateTime fCreacion = LocalDateTime.now();
                Timestamp fechaCreacion = Timestamp.valueOf(fCreacion);        
                actividadValida.setFechaCreacion(fechaCreacion.toString());                
                registrarActividad(actividadValida);                
            }
        }
    }
    
    private void registrarActividad(Actividad actividadNueva){        
        int codigoRespuesta = ActividadDAO.guardarActividad(actividadNueva);
        switch(codigoRespuesta){
            case Constantes.ERROR_CONEXION:
                Utilidades.mostrarDialogoSimple("Sin conexión", 
                        "Lo sentimos, por el momento no hay conexión", Alert.AlertType.ERROR);
                break;
            case Constantes.ERROR_CONSULTA:
                Utilidades.mostrarDialogoSimple("Error al registrar la actividad", 
                        "Hubo un error al registrar la actividad. Por favor intente más tarde", 
                        Alert.AlertType.WARNING);
                break;
            case Constantes.OPERACION_EXITOSA:
                Utilidades.mostrarDialogoSimple("Actividad registrada", 
                        "Actividad registrada correctamente", 
                        Alert.AlertType.INFORMATION);
                regresarCronograma();
                break;
        }
    }
    
    private void actualizarActividad(Actividad actividadActualizada){        
        int codigoRespuesta = ActividadDAO.modificarActividad(actividadActualizada);
        switch(codigoRespuesta){
            case Constantes.ERROR_CONEXION:
                Utilidades.mostrarDialogoSimple("Sin conexión", 
                        "Lo sentimos, por el momento no hay conexión", 
                        Alert.AlertType.ERROR);
                break;
            case Constantes.ERROR_CONSULTA:
                Utilidades.mostrarDialogoSimple("Error al cargar los datos", 
                        "Hubo un error al cargar la información. Por favor intente más tarde", 
                        Alert.AlertType.WARNING);
                break;
            case Constantes.OPERACION_EXITOSA:
                Utilidades.mostrarDialogoSimple("Actividad modificada", 
                        "Actividad modificada correctamente", 
                        Alert.AlertType.INFORMATION);
                regresarCronograma();
                break;
        }        
    }
    
    private void eliminarActividad(Actividad actividadSeleccionada){        
        int codigoRespuesta = ActividadDAO.eliminarActividad(
                actividadSeleccionada.getIdActividad());
        switch(codigoRespuesta){
            case Constantes.ERROR_CONEXION:
                Utilidades.mostrarDialogoSimple("Sin conexión", 
                        "Lo sentimos, por el momento no hay conexión", 
                        Alert.AlertType.ERROR);
                break;
            case Constantes.ERROR_CONSULTA:
                Utilidades.mostrarDialogoSimple("Error al eliminar", 
                        "Hubo un error al eliminar la actividad. Por favor intente más tarde", 
                        Alert.AlertType.WARNING);
                break;
            case Constantes.OPERACION_EXITOSA:
                Utilidades.mostrarDialogoSimple("Actividad eliminada", 
                        "Actividad eliminada correctamente", 
                        Alert.AlertType.INFORMATION);
                regresarCronograma();
                break;
        }        
    }   
    
    private void regresarCronograma(){
        Stage escenarioBase = (Stage) lbTitulo.getScene().getWindow();
        escenarioBase.setScene(Utilidades.inicializarEscena(
                "vistas/FXMLCronogramaActividades.fxml"));
        escenarioBase.setTitle("Cronograma de actividades");
        escenarioBase.show();
    }
    
    private void establecerEstiloNormal(){
        tfNombreActividad.setStyle(Constantes.estiloNormal);
        dpFechaInicio.setStyle(Constantes.estiloNormal);
        dpFechaFin.setStyle(Constantes.estiloNormal);
        tfDescripcionActividad.setStyle(Constantes.estiloNormal);
    }

    @FXML
    private void clicIrPrincipalEstudiante(ActionEvent event) {
        boolean cerrarVentana = Utilidades.mostrarDialogoConfirmacion(
                "Regresar a ventana anterior", 
                "¿Está seguro de que desea regresar? Los cambios realizados no se guardarán.");
            if(cerrarVentana){
                regresarCronograma();
            }
    }       
}