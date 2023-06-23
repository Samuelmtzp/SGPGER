/*
* Autor: Carlos Rodriguez Herrera
* Fecha de creación: 18/06/2023
* Descripción: Clase controladora para evaluar los avances del estudiante
*/
package jfxspger.controladores;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import jfxspger.modelo.dao.ActividadDAO;
import jfxspger.modelo.dao.CalificacionDAO;
import jfxspger.modelo.dao.DocumentoDAO;
import jfxspger.modelo.pojo.Actividad;
import jfxspger.modelo.pojo.Calificacion;
import jfxspger.modelo.pojo.CalificacionRespuesta;
import jfxspger.modelo.pojo.Documento;
import jfxspger.modelo.pojo.DocumentoRespuesta;
import jfxspger.modelo.pojo.Estudiante;
import jfxspger.utilidades.Constantes;
import jfxspger.utilidades.Utilidades;

public class FXMLEvaluarAvanceController extends FXMLPrincipalAcademicoController {

    private Estudiante estudiante;
    private Actividad actividadEntrega;
    @FXML
    private Label lbTituloActividad;
    @FXML
    private Label lbFechaInicio;
    @FXML
    private Label lbFechaFin;
    @FXML
    private TableColumn cNombreDocumento;
    @FXML
    private TextField tfCalificacion;
    @FXML
    private TableView<Documento> tvDocumentos;
    @FXML
    private TextArea taComentarios;
    private boolean esEdicion;
    private ObservableList<Documento> documentos;
    private final int ESTADO_CALIFICADA = 2;
    @FXML
    private TextArea taDescripcion;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTablaEntregas();
    }
    
    public void inicializarDetalles(boolean esEdicion, Estudiante estudiante, 
            Actividad actividadEntrega) {
        this.esEdicion = esEdicion;
        this.estudiante = estudiante;
        this.actividadEntrega = actividadEntrega;
        cargarInformacionEntregas(actividadEntrega.getIdActividad());
        lbTituloActividad.setText(actividadEntrega.getTitulo());
        taDescripcion.setText(actividadEntrega.getDescripcion());
        lbFechaInicio.setText(actividadEntrega.getFechaInicio());
        lbFechaFin.setText(actividadEntrega.getFechaFin());
        if (esEdicion) {
//            tfCalificacion.setText(Double.toString(actividadEntrega.getCalificacion()));
//            taComentarios.setText(this.actividadEntrega.getCommentCalif());
            cargarDatosRevision();
        }
        
    }
    
    private void cargarDatosRevision() {
        CalificacionRespuesta calificacionRespuesta = CalificacionDAO.obtenerInformacionCalificacionPorActividad(
                actividadEntrega.getIdActividad());
        switch (calificacionRespuesta.getCodigoRespuesta()) {
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
                if (!calificacionRespuesta.getCalificaciones().isEmpty()){
                    Calificacion calificacion = calificacionRespuesta.getCalificaciones().get(0);
                    taComentarios.setText(calificacion.getComentario());
                    tfCalificacion.setText(String.valueOf(calificacion.getCalificacion()));
                }
                break;
        }    
    }
    
    private void configurarTablaEntregas(){
        cNombreDocumento.setCellValueFactory(new PropertyValueFactory("nombre"));
    }    
    
    private void cargarInformacionEntregas(int idActividad){
        documentos = FXCollections.observableArrayList();
        DocumentoRespuesta respuestaBD = DocumentoDAO.
                obtenerInformacionArchivosDeEntregaActualDeActividad(idActividad);
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
                tvDocumentos.setItems(documentos);                
                break;
        }    
    }

    @FXML
    private void clicIrAvancesEstudiante(ActionEvent event) {
        boolean cerrarVentana = Utilidades.mostrarDialogoConfirmacion(
                "Regresar a ventana anterior", 
                "“¿Está seguro de que desea regresar? La información ingresada no se guardará.");
        if(cerrarVentana){
            regresarAvances();
        }
    }

    @FXML
    private void clicBtnCalificar(ActionEvent event) {
        validarCampos();
    }
    
    private void validarCampos(){
        establecerEstiloNormal();
        boolean datosValidos = true;        
        String puntaje = tfCalificacion.getText().trim();
        String comentarios = taComentarios.getText().trim();
        double puntajeConvertido = 0;
        
        if (puntaje.isEmpty()) {
            tfCalificacion.setStyle(Constantes.estiloError);
            datosValidos = false;
        } else {
            puntajeConvertido = Double.parseDouble(puntaje);
            if (puntajeConvertido < 0 || puntajeConvertido > 10) {
                tfCalificacion.setStyle(Constantes.estiloError);
                datosValidos = false;
            }
        }
        
        if (comentarios.isEmpty()) {
            taComentarios.setStyle(Constantes.estiloError);
            datosValidos = false;
        }
        
        if (datosValidos) {
            Calificacion calificacion = new Calificacion();
            calificacion.setCalificacion(puntajeConvertido);
            calificacion.setComentario(comentarios);
            calificacion.setIdActividad(actividadEntrega.getIdActividad());
            
            if (esEdicion) {
                calificacion.setIdCalificacion(actividadEntrega.getIdCalificacion());
                modificarCalificacion(calificacion);
            } else {
                registrarCalificacion(calificacion);
            }
            
        }
                
    }
    
    private void registrarCalificacion(Calificacion nuevaCalificacion) {
        int codigoRespuesta = CalificacionDAO.guardarCalificacion(nuevaCalificacion);
        switch (codigoRespuesta) {
            case Constantes.ERROR_CONEXION:
                Utilidades.mostrarDialogoSimple("Sin conexión", 
                        "Lo sentimos, por el momento no hay conexión", Alert.AlertType.ERROR);
                break;
            case Constantes.ERROR_CONSULTA:
                Utilidades.mostrarDialogoSimple("Error al registrar la calificacion", 
                        "Hubo un error al registrar la calificacion. Por favor intente más tarde", 
                        Alert.AlertType.WARNING);
                break;
            case Constantes.OPERACION_EXITOSA:                
                actualizaEstadoActividad(ESTADO_CALIFICADA, actividadEntrega.getIdActividad());
                break;
        }
    }
    
    private void actualizaEstadoActividad(int idEstado, int idActividad) {
        int codigoRespuesta = ActividadDAO.actualizarEstadoActividad(idEstado, idActividad);
        switch(codigoRespuesta){
            case Constantes.ERROR_CONEXION:
                Utilidades.mostrarDialogoSimple("Sin conexión", 
                        "Lo sentimos, por el momento no hay conexión", Alert.AlertType.ERROR);
                break;
            case Constantes.ERROR_CONSULTA:
                Utilidades.mostrarDialogoSimple("Error al actualizar el estado de la actividad", 
                        "Hubo un error al actualizar el estado de la actividad. "
                        + "Por favor intente más tarde", 
                        Alert.AlertType.WARNING);
                break;
            case Constantes.OPERACION_EXITOSA:
                Utilidades.mostrarDialogoSimple("Operacion realizada", 
                        "Operacion realizada correctamente.", 
                        Alert.AlertType.INFORMATION);
                regresarAvances();
                break;
        }
    }
    
    private void modificarCalificacion(Calificacion calificacion) {
        int codigoRespuesta = CalificacionDAO.modificarCalificacion(calificacion);
        switch(codigoRespuesta){
            case Constantes.ERROR_CONEXION:
                Utilidades.mostrarDialogoSimple("Sin conexión", 
                        "Lo sentimos, por el momento no hay conexión", Alert.AlertType.ERROR);
                break;
            case Constantes.ERROR_CONSULTA:
                Utilidades.mostrarDialogoSimple("Error al modificar la calificacion", 
                        "Hubo un error al modificar la calificacion. Por favor intente más tarde", 
                        Alert.AlertType.WARNING);
                break;
//            case Constantes.OPERACION_EXITOSA:                
//                actualizaEstadoActividad(idEstado, idActividad);
                
//                break;
        }
    }
    
    private void establecerEstiloNormal(){
        tfCalificacion.setStyle(Constantes.estiloNormal);
        taComentarios.setStyle(Constantes.estiloNormal);
    }
    
    private void regresarAvances() {
        try {
            FXMLLoader accesoControlador = new FXMLLoader(jfxspger.JFXSPGER.
                    class.getResource("vistas/FXMLAvancesEstudiante.fxml"));        
            Parent vista = accesoControlador.load();
            
            FXMLAvancesEstudianteController avances = accesoControlador.getController();
            avances.inicializarEntregas(estudiante);
            
            Scene sceneAvances = new Scene(vista);
            Stage escenarioAvances = (Stage) lbTitulo.getScene().getWindow();
            escenarioAvances.setScene(sceneAvances);
            escenarioAvances.setTitle("Avances de estudiante");            
            escenarioAvances.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }    

    @FXML
    private void clicBtnAbrirArchivo(ActionEvent event) {
        Documento documentoSeleccionado = tvDocumentos.getSelectionModel().getSelectedItem();
        if (documentoSeleccionado != null) {
            File archivo = guardarDocumentoTemporal(documentoSeleccionado);
            if (archivo != null) {
                abrirArchivo(archivo);
            } else {
                Utilidades.mostrarDialogoSimple("Selecciona un documento", 
                    "Debes selecionar un documento para poder"
                    + " abrir el archivo.", Alert.AlertType.WARNING);
            }
        }else {
                Utilidades.mostrarDialogoSimple("Selecciona un documento", 
                    "Debes selecionar un documento para poder"
                    + " abrir el archivo.", Alert.AlertType.WARNING);
        }
    }

    private void abrirArchivo(File archivo) {
        try {
            Desktop.getDesktop().open(archivo);
        } catch (IOException e) {
            Utilidades.mostrarDialogoSimple("Error al abrir el archivo", 
                    "Hubo un error al abrir el archivo. Por favor intentelo más tarde.", 
                    Alert.AlertType.ERROR);
        }
    }

    private File guardarDocumentoTemporal(Documento documento) {
    try {
        String nombreArchivo = documento.getNombre();
        String extension = nombreArchivo.substring(nombreArchivo.lastIndexOf("."));
        String nombreSinExtension = nombreArchivo.substring(0, nombreArchivo.lastIndexOf("."));
                
        File tempFile = File.createTempFile(nombreSinExtension, extension);

        FileOutputStream fos = new FileOutputStream(tempFile);
        fos.write(documento.getArchivoDocumento());
        fos.close();

        return tempFile;
    } catch (IOException e) {
        System.out.println("Error al guardar el archivo temporal: " + e.getMessage());
        return null;
        }
    }
    
    @FXML
    private void permitirInputSoloNumeros(KeyEvent event) {
        String entrada = event.getCharacter();
        if (!".0123456789".contains(entrada)) 
            event.consume();
    }

}

