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
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
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

    @FXML
    private Label lFechaInicioActividad;
    @FXML
    private Label lFechaFinActividad;
    @FXML
    private Label lNombreActividad;
    @FXML
    private Label lDescripcionActividad;
    private Documento archivoActividad;
    private File entregaActividad;
    @FXML
    private Label lTitulo;
    private Actividad actividadInformacion;    
    private INotificacionOperacionActividad interfazNotificacion;
    @FXML
    private Label lFechaCreacion;

    @Override
    public void initialize(URL url, ResourceBundle rb) {        
        cargarInformacionActividad();
    }    

    @FXML
    private void clicBtnModificarActividad(ActionEvent event) {        
        
        irFormulario(true, actividadInformacion);
    }
    
    private void irFormulario(boolean esEdicion, Actividad actividadEdicion){
        try {
            FXMLLoader accesoControlador = new FXMLLoader(jfxspger.JFXSPGER.class.getResource("vistas/FXMLActividadFormulario.fxml"));        
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
        LocalDate fechaFin = LocalDate.parse(actividadInformacion.getFechaFin());
        LocalDate fechaActual = LocalDate.now();
        if(fechaActual.isBefore(fechaFin)){
            FileChooser dialogoEntrega = new FileChooser();
            dialogoEntrega.setTitle("Seleccione un archivo");

            Stage escenarioBase = (Stage) lNombreActividad.getScene().getWindow();
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
        lNombreActividad.setText(actividadInformacion.getTitulo());
        lDescripcionActividad.setText(actividadInformacion.getDescripcion());
        lFechaInicioActividad.setText("Fecha inicio: " + actividadInformacion.getFechaInicio());
        lFechaFinActividad.setText("Fecha fin: " + actividadInformacion.getFechaFin());
        lFechaCreacion.setText("Fecha de creación: " + actividadInformacion.getFechaCreacion());
    }
    
    public void inicializarInformacionActividad(Actividad actividadInformacion, INotificacionOperacionActividad interfazNotificacion){        
        this.actividadInformacion = actividadInformacion;
        this.interfazNotificacion = interfazNotificacion;
                
            lTitulo.setText("Detalles de actividad: " + actividadInformacion.getTitulo());
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
        Stage escenarioBase = (Stage) lNombreActividad.getScene().getWindow();
        escenarioBase.close();
    }

    @FXML
    private void clicBtnRegresar(MouseEvent event) {
        boolean cerrarVentana = Utilidades.mostrarDialogoConfirmacion("Regresar a ventana anterior", "¿Desea regresar a la ventana anterior? No se guardaran los datos ingresados.");
        if(cerrarVentana){
            cerrarVentana();
        }
    }
}
