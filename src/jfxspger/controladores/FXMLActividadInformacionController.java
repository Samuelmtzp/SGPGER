/*
* Autor: Carlos Rodriguez Herrera
* Fecha de creación: 18/05/2023
* Descripción: Controlador para la ventana "Información de actividad"
*/

package jfxspger.controladores;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import jfxspger.JFXSPGER;
import jfxspger.interfaz.INotificacionOperacionActividad;
import jfxspger.modelo.dao.DocumentoDAO;
import jfxspger.modelo.pojo.Actividad;
import jfxspger.modelo.pojo.Documento;
import jfxspger.utilidades.Constantes;
import jfxspger.utilidades.Utilidades;

public class FXMLActividadInformacionController implements Initializable, INotificacionOperacionActividad {
  
    private Documento archivoActividad;
    private File entregaActividad;    
    private Actividad actividadInformacion;    
    private INotificacionOperacionActividad interfazNotificacion;   
    @FXML
    private Label lbTituloActividad;
    @FXML
    private Label lbTitulo;
    @FXML
    private Label lbDescActividad;    
    @FXML
    private Label lbFechaInicio;
    @FXML
    private Label lbFechaFin;

    @Override
    public void initialize(URL url, ResourceBundle rb) {        
        cargarInformacionActividad();
    }    

    private void clicBtnModificarActividad(ActionEvent event) {        
        
        irFormulario(true, actividadInformacion);
    }
    
    private void irFormulario(boolean esEdicion, Actividad actividadEdicion){
        try {
            FXMLLoader accesoControlador = new FXMLLoader(jfxspger.JFXSPGER.class.getResource("vistas/FXMLActividadFormu.fxml"));        
            Parent vista = accesoControlador.load();
            
            FXMLActividadFormularioController formulario = accesoControlador.getController();
            formulario.inicializarInformacionFormulario(esEdicion, actividadEdicion, this);
        
            Stage escenarioFormulario = new Stage();
            escenarioFormulario.setScene(new Scene(vista));
            escenarioFormulario.setTitle("Formulario Activdad");
            escenarioFormulario.initModality(Modality.APPLICATION_MODAL);       
            escenarioFormulario.showAndWait();
        } catch (IOException ex) {
            ex.printStackTrace();
        }                
    }

    @FXML
    private void clicBtnCargarArchivo(ActionEvent event) {
        LocalDateTime fechaFin = LocalDateTime.parse(actividadInformacion.getFechaFin());
        LocalDateTime fechaActual = LocalDateTime.now();
        if(fechaActual.isBefore(fechaFin)){
            FileChooser dialogoEntrega = new FileChooser();
            dialogoEntrega.setTitle("Seleccione un archivo");

            Stage escenarioBase = (Stage) lbTituloActividad.getScene().getWindow();
            entregaActividad = dialogoEntrega.showOpenDialog(escenarioBase);
        } else{
            Utilidades.mostrarDialogoSimple("ACTIVIDAD FINALIZADA", "La actividad ha finalizado. No se puede realizar entrega.", Alert.AlertType.WARNING);
        }
    }  

    @FXML
    private void clicBtnGuardar(ActionEvent event) {
        
        try{
            if(entregaActividad != null){
            archivoActividad.setArchivoDocumento(Files.readAllBytes(entregaActividad.toPath()));
            Utilidades.mostrarDialogoSimple("ENTREGA COMPLETA", "La entrega ha sido registrada!", Alert.AlertType.INFORMATION);
            registrarEntrega(archivoActividad);
        }else {
            Utilidades.mostrarDialogoSimple("ENTREGA VACIA", "Debes adjuntar un archivo de entrega para guardar los cambios.", Alert.AlertType.WARNING);
        }
        }catch(IOException ex){
            
        }
    }
    
    private void cargarInformacionActividad(){
//        lbTituloActividad.setText(actividadInformacion.getTitulo());
//        lbDescActividad.setText(actividadInformacion.getDescripcion());
//        lbFechaInicio.setText(actividadInformacion.getFechaInicio().toString());
//        lbFechaFin.setText(actividadInformacion.getFechaFin().toString());
        
    }
    
    public void inicializarInformacionActividad(Actividad actividadInformacion, INotificacionOperacionActividad interfazNotificacion){        
        this.actividadInformacion = actividadInformacion;
        this.interfazNotificacion = interfazNotificacion;
                
            lbTitulo.setText("Detalles de actividad: " + actividadInformacion.getTitulo());
            cargarInformacionActividad();        
    }    
    
        @Override
    public void notificarOperacionGuardar(String nombreActividad) {
        cargarInformacionActividad();
    }

    @Override
    public void notificarOperacionActualizar(String nombreActividad) {
        cargarInformacionActividad();  
    }
    
    private void registrarEntrega(Documento archivoNuevo){
        int codigoRespuesta = DocumentoDAO.guardarArchivo(archivoNuevo);
        switch(codigoRespuesta){
            case Constantes.ERROR_CONEXION:
                Utilidades.mostrarDialogoSimple("Sin conexión", "Lo sentimos, por el momento no hay conexión", Alert.AlertType.ERROR);
                break;
            case Constantes.ERROR_CONSULTA:
                Utilidades.mostrarDialogoSimple("Error al cargar los datos", 
                        "Hubo un error al cargar la información. Por favor intente más tarde", Alert.AlertType.WARNING);
                break;
            case Constantes.OPERACION_EXITOSA:
                Utilidades.mostrarDialogoSimple("Actividad registrada", "Actividad registrada correctamente", 
                        Alert.AlertType.INFORMATION);
                cerrarVentana();
                break;     
            }
        }
    
        private void cerrarVentana(){
        Stage escenarioBase = (Stage) lbTituloActividad.getScene().getWindow();
        escenarioBase.close();
    }

    @FXML
    private void clicIrAnteproyecto(ActionEvent event) {
        Stage escenarioBase = (Stage) lbTitulo.getScene().getWindow();
        escenarioBase.setScene(Utilidades.inicializarEscena("vistas/FXMLAnteproyectoInformacion.fxml"));
        escenarioBase.setTitle("Informacion de anteproyecto");
        escenarioBase.show();
    }

    @FXML
    private void clicIrCronograma(ActionEvent event) {
        Stage escenarioBase = (Stage) lbTitulo.getScene().getWindow();
        escenarioBase.setScene(Utilidades.inicializarEscena("vistas/FXMLCronogramaActividades.fxml"));
        escenarioBase.setTitle("Cronograma de actividades");
        escenarioBase.show();
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
    private void clicBtnRegresar(ActionEvent event) {
        boolean cerrarVentana = Utilidades.mostrarDialogoConfirmacion("Regresar a ventana anterior", "¿Desea regresar a la ventana anterior? No se guardaran los datos ingresados.");
        if(cerrarVentana){
            cerrarVentana();
        }        
    }
}
