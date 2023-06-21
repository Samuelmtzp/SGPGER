/*
* Autor: Carlos Rodriguez Herrera
* Fecha de creación: 18/05/2023
* Descripción: Controlador para la ventana "Información de actividad"
*/

package jfxspger.controladores;

import com.sun.javafx.scene.control.skin.TableHeaderRow;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import jfxspger.interfaz.INotificacionOperacionActividad;
import jfxspger.modelo.dao.ActividadDAO;
import jfxspger.modelo.dao.DocumentoDAO;
import jfxspger.modelo.dao.EntregaDAO;
import jfxspger.modelo.pojo.Actividad;
import jfxspger.modelo.pojo.ActividadRespuesta;
import jfxspger.modelo.pojo.Documento;
import jfxspger.modelo.pojo.DocumentoRespuesta;
import jfxspger.modelo.pojo.Entrega;
import jfxspger.modelo.pojo.EntregaRespuesta;
import jfxspger.utilidades.Constantes;
import jfxspger.utilidades.Utilidades;

public class FXMLActividadInformacionController extends FXMLPrincipalEstudianteController implements Initializable, 
        INotificacionOperacionActividad {
  
    private Documento archivoActividad;
    private Entrega entregaAct;    
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
    @FXML
    private Label lbActividad;
    @FXML
    private TableView<Documento> tvEntregas;
    @FXML
    private TableColumn cNombreDocumento;
    @FXML
    private TableColumn cFechaEntrega;
    private ObservableList<Documento> documentos;
    @FXML
    private Button bEliminarDocumento;
    private int idActividad;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTablaEntregas();
    }
    
    private void configurarTablaEntregas(){
        cNombreDocumento.setCellValueFactory(new PropertyValueFactory("nombre"));
        cFechaEntrega.setCellValueFactory(new PropertyValueFactory("fechaEntrega"));
        tvEntregas.widthProperty().addListener(new ChangeListener<Number>(){
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, 
                    Number newValue) {
                TableHeaderRow header = (TableHeaderRow) tvEntregas.lookup("TableHeaderRow");
                header.reorderingProperty().addListener(new ChangeListener<Boolean>(){
                    @Override
                    public void changed(ObservableValue<? extends Boolean> observable, 
                            Boolean oldValue, Boolean newValue) {
                        header.setReordering(false);
                    }
                });
            }
        });        
    }
    
        private void cargarInformacionEntregas(Actividad actividadInformacion){
            idActividad = actividadInformacion.getIdActividad();
        documentos = FXCollections.observableArrayList();
            DocumentoRespuesta respuestaBD = DocumentoDAO.obtenerInformacionArchivoPorActividad(idActividad);
        switch(respuestaBD.getCodigoRespuesta()){
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
                documentos.addAll(respuestaBD.getDocumentos());
                tvEntregas.setItems(documentos);                
                break;
        }
    }

    @FXML
    private void clicBtnCargarArchivo(ActionEvent event) {
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyy HH:mm:ss.S");
        LocalDateTime fechaFin = LocalDateTime.parse(actividadInformacion.getFechaFin(), formatter);
        LocalDateTime fechaActual = LocalDateTime.now();
        if(fechaActual.isBefore(fechaFin)){
            FileChooser dialogoEntrega = new FileChooser();
            dialogoEntrega.setTitle("Seleccione un archivo");

            Stage escenarioBase = (Stage) lbTituloActividad.getScene().getWindow();
            entregaActividad = dialogoEntrega.showOpenDialog(escenarioBase);
            lbActividad.setText(entregaActividad.getName());
        } else{
            Utilidades.mostrarDialogoSimple("ACTIVIDAD FINALIZADA", 
                    "La actividad ha finalizado. No se puede realizar entrega.", 
                    Alert.AlertType.WARNING);
        }
    }  

    @FXML
    private void clicBtnGuardar(ActionEvent event) {
                validarentrega();                
    }
    
    @FXML
    private void clicBtnEliminarEntrega(ActionEvent event) {
        Documento documentoSeleccionado = tvEntregas.getSelectionModel().getSelectedItem();
        
        if(documentoSeleccionado != null){
            boolean eliminarAct = Utilidades.mostrarDialogoConfirmacion("Eliminar entrega", 
                "¿Estás seguro que deseas eliminar la entrega?");        
            if(eliminarAct){
                eliminarDocumento(documentoSeleccionado);
            }
        }else{
            Utilidades.mostrarDialogoSimple("Selecciona un documento", 
                    "Debes selecionar un documento para poder"
                    + " eliminar la entrega.", Alert.AlertType.WARNING);
        }
    }
    
    private void eliminarDocumento(Documento documentoSeleccionado){        
        int codigoRespuesta = DocumentoDAO.eliminarArchivo(documentoSeleccionado.getIdDocumento());
        switch(codigoRespuesta){
            case Constantes.ERROR_CONEXION:
                Utilidades.mostrarDialogoSimple("Sin conexión", 
                        "Lo sentimos, por el momento no hay conexión", 
                        Alert.AlertType.ERROR);
                break;
            case Constantes.ERROR_CONSULTA:
                Utilidades.mostrarDialogoSimple("Error al eliminar", 
                        "Hubo un error al eliminar la entrega. Por favor intente más tarde", 
                        Alert.AlertType.WARNING);
                break;
            case Constantes.OPERACION_EXITOSA:
                Utilidades.mostrarDialogoSimple("Entrega eliminada", 
                        "Entrega eliminada correctamente", 
                        Alert.AlertType.INFORMATION);
                notificarOperacionActualizar(actividadInformacion.getTitulo());
                break;
        }        
    }
    
    private void validarentrega() {
        boolean datosValidos = true; 
        entregaAct = new Entrega();
        entregaAct.setIdActividad(actividadInformacion.getIdActividad());
        LocalDateTime fechaActual = LocalDateTime.now();        
        entregaAct.setFechaEntrega(fechaActual.toString());
        LocalDateTime fCreacion = LocalDateTime.now();
        Timestamp fechaCreacion = Timestamp.valueOf(fCreacion);
        entregaAct.setFechaCreacion(fechaCreacion.toString());
        
        if(entregaAct.getIdActividad() == 0){
            datosValidos = false;
            Utilidades.mostrarDialogoSimple("ERROR CON ENTREGA", 
                            "Ha ocurrido un error al asignar una entrega a la actividad. Intentelo de nuevo más tarde.", 
                            Alert.AlertType.WARNING);
        }                
        
        if(datosValidos){
            archivoActividad = new Documento();
            try{
                if(entregaActividad != null){
                    archivoActividad.setArchivoDocumento(Files.readAllBytes(entregaActividad.toPath()));                    
                    archivoActividad.setNombre(entregaActividad.getName().toString());
                    registrarEntrega(entregaAct);
                }else {
                    Utilidades.mostrarDialogoSimple("ENTREGA VACIA", 
                            "Debes adjuntar un archivo de entrega para guardar los cambios.", 
                            Alert.AlertType.WARNING);
                }
            }catch(IOException ex){
              ex.printStackTrace();
            }
        }
    }

    
    private void cargarInformacionActividad(){
        lbTituloActividad.setText(actividadInformacion.getTitulo());
        lbDescActividad.setText(actividadInformacion.getDescripcion());
        lbFechaInicio.setText(actividadInformacion.getFechaInicio());
        lbFechaFin.setText(actividadInformacion.getFechaFin());        
        if(entregaActividad != null){
        lbActividad.setText(entregaActividad.getName());    
        }                
    }
    
    public void inicializarInformacionActividad(Actividad actividadInformacion, 
            INotificacionOperacionActividad interfazNotificacion){        
        this.actividadInformacion = actividadInformacion;
        this.interfazNotificacion = interfazNotificacion;
                
            lbTitulo.setText("Detalles de actividad: " + actividadInformacion.getTitulo());
            cargarInformacionActividad();
            cargarInformacionEntregas(actividadInformacion);
    }    
    
        @Override
    public void notificarOperacionGuardar(String nombreActividad) {
        
            cargarInformacionEntregas(actividadInformacion);
    }

    @Override
    public void notificarOperacionActualizar(String nombreActividad) {
        
        cargarInformacionEntregas(actividadInformacion);
    }
    
    private void registrarEntrega(Entrega entregaNueva){
        
        EntregaRespuesta codigoRespuesta = EntregaDAO.guardarEntrega(entregaNueva);        
        switch(codigoRespuesta.getCodigoRespuesta()){
            case Constantes.ERROR_CONEXION:
                Utilidades.mostrarDialogoSimple("Sin conexión", 
                        "Lo sentimos, por el momento no hay conexión", Alert.AlertType.ERROR);
                break;
            case Constantes.ERROR_CONSULTA:
                Utilidades.mostrarDialogoSimple("Error al registrar la entrega", 
                        "Hubo un error al registrar la entrega. Por favor intente más tarde", 
                        Alert.AlertType.WARNING);
                break;
            case Constantes.OPERACION_EXITOSA:
                archivoActividad.setIdEntrega(codigoRespuesta.getIdEntrega());                
                registrarDocumento(archivoActividad);                
                break;
        }        
    }
    
    private void registrarDocumento(Documento archivoNuevo){
                
                int docRespuesta = DocumentoDAO.guardarArchivo(archivoNuevo);
                switch(docRespuesta){
                    case Constantes.ERROR_CONEXION:
                        Utilidades.mostrarDialogoSimple("Sin conexión", 
                                "Lo sentimos, por el momento no hay conexión", Alert.AlertType.ERROR);
                        break;
                    case Constantes.ERROR_CONSULTA:
                        Utilidades.mostrarDialogoSimple("Error al registrar el documento", 
                                "Hubo un error al registrar la actividad. Por favor intente más tarde", 
                                Alert.AlertType.WARNING);
                        break;
                    case Constantes.OPERACION_EXITOSA:
                        Utilidades.mostrarDialogoSimple("Entrega registrada",
                                "Entrega registrada correctamente",
                                Alert.AlertType.INFORMATION);
                        notificarOperacionGuardar(actividadInformacion.getTitulo());
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
        escenarioBase.setScene(Utilidades.inicializarEscena(
                "vistas/FXMLAnteproyectoInformacion.fxml"));
        escenarioBase.setTitle("Informacion de anteproyecto");
        escenarioBase.show();
    }

    @FXML
    private void clicIrCronograma(ActionEvent event) {
        Stage escenarioBase = (Stage) lbTitulo.getScene().getWindow();
        escenarioBase.setScene(Utilidades.inicializarEscena(
                "vistas/FXMLCronogramaActividades.fxml"));
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
            cerrarVentana();               
    }
}
