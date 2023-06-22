/*
* Autor: Carlos Rodriguez Herrera
* Fecha de creación: 18/06/2023
* Descripción: Clase controladora para evaluar los avances del estudiante
*/
package jfxspger.controladores;

import com.sun.javafx.scene.control.skin.TableHeaderRow;
import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.web.WebView;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import jfxspger.modelo.dao.CalificacionDAO;
import jfxspger.modelo.dao.DocumentoDAO;
import jfxspger.modelo.pojo.Actividad;
import jfxspger.modelo.pojo.Calificacion;
import jfxspger.modelo.pojo.Documento;
import jfxspger.modelo.pojo.DocumentoRespuesta;
import jfxspger.modelo.pojo.Entrega;
import jfxspger.modelo.pojo.Estudiante;
import jfxspger.utilidades.Constantes;
import jfxspger.utilidades.Utilidades;

public class FXMLEvaluarAvanceController extends FXMLPrincipalAcademicoController implements Initializable {

    @FXML
    private Label lbTitulo;
    @FXML
    private Button btnAnteproyectos;
    @FXML
    private Button btnPropuestas;
    @FXML
    private Button btnEstudiantes;
    @FXML
    private Button btnRevisiones;
    private Estudiante estudiante;
    private Entrega entrega;
    private Actividad actividadEntrega;
    @FXML
    private Label lbDescActividad;
    @FXML
    private Label lbTituloActividad;
    @FXML
    private Label lbFechaInicio;
    @FXML
    private Label lbFechaFin;
    @FXML
    private TableView<Documento> tvEntregas;
    @FXML
    private TableColumn cNombreDocumento;
    @FXML
    private TableColumn cFechaEntrega;
    @FXML
    private TextField tfCalif;
    @FXML
    private TextField tfComent;
    private Calificacion calificacion;
    private ObservableList<Documento> documentos;
    private int idActividad;
    String estiloError = "-fx-border-color: RED; -fx-border-width: 2; -fx-border-radius: 2;";
    String estiloNormal = "-fx-border-width: 0;";
    private double calific;
    @FXML
    private Button bEliminarEval;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTablaEntregas();
    }
    
    public void inicializarDetalles(Estudiante estudiante, Actividad actividadEntrega){
        this.estudiante = estudiante;
        this.actividadEntrega = actividadEntrega;
        idActividad = actividadEntrega.getIdActividad();
        cargarInformacionEntregas(actividadEntrega.getIdActividad());
        lbTituloActividad.setText(actividadEntrega.getTitulo());
        lbDescActividad.setText(actividadEntrega.getDescripcion());
        lbFechaInicio.setText(actividadEntrega.getFechaInicio());
        lbFechaFin.setText(actividadEntrega.getFechaFin());
        
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
    
    private void cargarInformacionEntregas(int idActividad){
            
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
        String calif = tfCalif.getText().trim();
        String comen = tfComent.getText().trim();
        
        if(calif.isEmpty()){
            tfCalif.setStyle(estiloError);
            datosValidos = false;
        }
        
        if(comen.isEmpty()){
            tfComent.setStyle(estiloError);
            datosValidos = false;
        }
        
        try{
            calific = Double.parseDouble(calif);
            if (calific < 0 || calific > 10) {
                tfCalif.setStyle(estiloError);
                datosValidos=false;
            }
        }catch(NumberFormatException e){
            tfCalif.setStyle(estiloError);
            datosValidos=false;
        }                                
        
        
        if(datosValidos){
            Calificacion calificacion = new Calificacion();
            calificacion.setCalificacion(calific);
            calificacion.setComentario(comen);
            calificacion.setIdActividad(idActividad);
            
            registrarCalificacion(calificacion);
        }
                
    }
    
    private void registrarCalificacion(Calificacion nuevaCalificacion){        
        int codigoRespuesta = CalificacionDAO.guardarCalificacion(nuevaCalificacion);
        switch(codigoRespuesta){
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
                Utilidades.mostrarDialogoSimple("Calificacion registrada", 
                        "Actividad calificada correctamente", 
                        Alert.AlertType.INFORMATION);
                regresarAvances();
                
                break;
        }
    }
    
    private void establecerEstiloNormal(){
        tfCalif.setStyle(estiloNormal);
        tfComent.setStyle(estiloNormal);
    }
    
    private void regresarAvances(){
        try {
            FXMLLoader accesoControlador = new FXMLLoader(jfxspger.JFXSPGER.
                    class.getResource("vistas/FXMLAvancesEstudiante.fxml"));        
            Parent vista = accesoControlador.load();
            
            FXMLAvancesEstudianteController avances = accesoControlador.getController();
            avances.inicializarEntregas(estudiante);
            
            Scene sceneAvances = new Scene(vista);
            Stage escenarioAvances = (Stage) tfCalif.getScene().getWindow();
            escenarioAvances.setScene(sceneAvances);
            escenarioAvances.setTitle("Avances de estudiante");            
            escenarioAvances.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }    

    @FXML
    private void clicBtnAbrirArchivo(ActionEvent event) {
        Documento documentoSeleccionado = tvEntregas.getSelectionModel().getSelectedItem();
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
            System.out.println("Error al abrir el archivo: " + e.getMessage());
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
    private void clicBtnEliminarEvaluacion(ActionEvent event) {
    }

}

