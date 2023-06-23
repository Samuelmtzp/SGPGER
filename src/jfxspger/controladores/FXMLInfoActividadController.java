/*
* Autor: Carlos Rodriguez Herrera
* Fecha de creación: 18/05/2023
* Descripción: Controlador para la ventana "Información de actividad"
*/
package jfxspger.controladores;

import java.io.IOException;
import java.net.URL;
import java.io.File;
import java.nio.file.Files;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import jfxspger.modelo.dao.DocumentoDAO;
import jfxspger.modelo.dao.EntregaDAO;
import jfxspger.modelo.pojo.Actividad;
import jfxspger.modelo.pojo.Documento;
import jfxspger.modelo.pojo.DocumentoRespuesta;
import jfxspger.modelo.pojo.EntregaRespuesta;
import jfxspger.utilidades.Constantes;
import jfxspger.utilidades.Utilidades;

public class FXMLInfoActividadController extends FXMLPrincipalEstudianteController {
  
    @FXML
    private Label lbTituloActividad;
    @FXML
    private Label lbFechaInicio;
    @FXML
    private Label lbFechaFin;
    @FXML
    private TableColumn cNombreDocumento;
    @FXML
    private Label lbCalificacion;
    @FXML
    private TextArea taDescripcion;
    @FXML
    private TextArea taComentariosRevision;
    @FXML
    private TableView<Documento> tvArchivos;
    private Actividad actividadInformacion;       
    private ObservableList<Documento> documentos;
    private final int ESTADO_CALIFICADA = 2;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTablaEntregas();
        validarSeccionesPermitidas();
    }
    
    private void configurarTablaEntregas(){
        cNombreDocumento.setCellValueFactory(new PropertyValueFactory("nombre"));     
    }
     
    private void cargarInformacionArchivosDeEntregaActual() {        
        documentos = FXCollections.observableArrayList();
        DocumentoRespuesta respuestaBD = DocumentoDAO.
                obtenerInformacionArchivosDeEntregaActualDeActividad(
                actividadInformacion.getIdActividad());
        switch (respuestaBD.getCodigoRespuesta()) {
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
                tvArchivos.setItems(documentos);
                break;
        }
    }
    
    private void validarEntrega() {
        if (!tvArchivos.getItems().isEmpty()) {
                registrarEntrega();
                
        } else {
            Utilidades.mostrarDialogoSimple("Archivos necesarios", 
                "Debes adjuntar al menos un archivo de entrega para guardar los cambios.", 
                Alert.AlertType.WARNING);
        }
    }

    
    private void cargarInformacionActividad() {
        lbTituloActividad.setText(actividadInformacion.getTitulo());
        taDescripcion.setText(actividadInformacion.getDescripcion());
        lbFechaInicio.setText(actividadInformacion.getFechaInicio());
        lbFechaFin.setText(actividadInformacion.getFechaFin());
        
        if (actividadInformacion.getIdEstado() == ESTADO_CALIFICADA) {
            lbCalificacion.setText(Double.toString(actividadInformacion.getCalificacion()));
            taComentariosRevision.setText(actividadInformacion.getCommentCalif());
        }
    }
    
    public void inicializarInformacionActividad(Actividad actividadInformacion) {
        this.actividadInformacion = actividadInformacion;
        lbTitulo.setText("Detalles de actividad");
        cargarInformacionActividad();
        cargarInformacionArchivosDeEntregaActual();
    }    
    
    private void registrarEntrega() {
        EntregaRespuesta entregaRespuesta = EntregaDAO.guardarEntrega(
                actividadInformacion.getIdActividad());
        switch (entregaRespuesta.getCodigoRespuesta()) {
            case Constantes.ERROR_CONEXION:
                Utilidades.mostrarDialogoSimple("Sin conexión", 
                        "Lo sentimos, por el momento no hay conexión", 
                        Alert.AlertType.ERROR);
                break;
            case Constantes.ERROR_CONSULTA:
                Utilidades.mostrarDialogoSimple("Error al registrar la entrega", 
                        "Hubo un error al registrar la entrega. Por favor intente más tarde", 
                        Alert.AlertType.WARNING);
                break;
            case Constantes.OPERACION_EXITOSA:
                int idEntregaRegistrada = entregaRespuesta.getIdEntrega();
                registrarDocumentos(idEntregaRegistrada);
                break;
        }        
    }
    
    private void registrarDocumentos(int idEntrega) {                
        boolean entregaCorrecta = true;
        for (Documento documento : documentos) {
            documento.setIdEntrega(idEntrega);
            int docRespuesta = DocumentoDAO.guardarArchivo(documento);
            switch (docRespuesta) {
                case Constantes.ERROR_CONEXION:
                    Utilidades.mostrarDialogoSimple("Sin conexión", 
                            "Lo sentimos, por el momento no hay conexión", 
                            Alert.AlertType.ERROR);
                    entregaCorrecta = false;
                    break;
                case Constantes.ERROR_CONSULTA:
                    Utilidades.mostrarDialogoSimple("Error al registrar el documento", 
                            "Hubo un error al registrar el archivo. Por favor intente más tarde", 
                            Alert.AlertType.WARNING);
                    entregaCorrecta = false;
                    break;
            }
        }
        if (entregaCorrecta) {
            Utilidades.mostrarDialogoSimple("Entrega registrada",
                    "Entrega registrada correctamente",
                    Alert.AlertType.INFORMATION);
            irCronograma();
        }
    }

    @FXML
    private void clicBtnRegresar(ActionEvent event) {
        irCronograma();
    }

    @FXML
    private void clicBtnRealizarEntrega(ActionEvent event) {
        validarEntrega();
    }

    @FXML
    private void clicBtnSubirArchivo(ActionEvent event) {
        try
        {
            FileChooser dialogoSeleccionImg = new FileChooser();
            dialogoSeleccionImg.setTitle("Seleccione un archivo");
            
            Stage escenarioBase = (Stage) lbTitulo.getScene().getWindow();
            File archivoSeleccionado = dialogoSeleccionImg.showOpenDialog(escenarioBase);
            Documento nuevoDocumento = new Documento();
            nuevoDocumento.setArchivoDocumento(Files.readAllBytes(archivoSeleccionado.toPath()));
            nuevoDocumento.setNombre(archivoSeleccionado.getName());
            documentos.add(nuevoDocumento);
            tvArchivos.refresh();
        } catch (IOException ex)
        {
            Utilidades.mostrarDialogoSimple("Error al intentar el archivo", 
                    "Ha ocurrido un error al intentar cargar el archivo", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void clicBtnRemoverArchivo(ActionEvent event) {
        int posicion = tvArchivos.getSelectionModel().getSelectedIndex();
        if (posicion != -1) {
            documentos.remove(posicion);
            tvArchivos.refresh();
        } else {
            Utilidades.mostrarDialogoSimple("Selección necesaria", 
                    "Es necesario que seleccione un archivo previamente para poder removerlo", 
                    Alert.AlertType.WARNING);
        }
    }

}
