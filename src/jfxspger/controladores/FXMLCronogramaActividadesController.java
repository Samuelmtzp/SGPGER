/*
* Autor: Carlos Rodriguez Herrera
* Fecha de creación: 18/05/2023
* Descripción: Clase controladora para el cronograma de actividades
*/

package jfxspger.controladores;

import com.sun.javafx.scene.control.skin.TableHeaderRow;
import java.io.IOException;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import jfxspger.modelo.dao.ActividadDAO;
import jfxspger.modelo.pojo.Actividad;
import jfxspger.modelo.pojo.ActividadRespuesta;
import jfxspger.utilidades.Constantes;
import jfxspger.utilidades.SingletonUsuario;
import jfxspger.utilidades.Utilidades;

public class FXMLCronogramaActividadesController extends FXMLPrincipalEstudianteController {

    @FXML
    private TableView<Actividad> tvActividades;
    @FXML
    private TableColumn cNombreActividad;    
    @FXML
    private TableColumn cFechaIncio;
    @FXML
    private TableColumn cFechaFin;
    @FXML
    private TableColumn cCalificacion;
    @FXML
    private TableColumn cEstado;
    @FXML
    private Button btnAnteproyecto;
    @FXML
    private Button btnCronograma;
    @FXML
    private Button btnCursos;
    @FXML
    private Label lbTitulo;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyy HH:mm:ss.S");
    private ObservableList<Actividad> actividades;    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTabla();
        cargarInformacionCronograma(); 
        validarSeccionesPermitidas();
   }
    
    
    private void configurarTabla(){
        cNombreActividad.setCellValueFactory(new PropertyValueFactory("titulo"));        
        cFechaIncio.setCellValueFactory(new PropertyValueFactory("fechaInicio"));
        cFechaFin.setCellValueFactory(new PropertyValueFactory("fechaFin"));
        cCalificacion.setCellValueFactory(new PropertyValueFactory("calificacion"));
        cEstado.setCellValueFactory(new PropertyValueFactory("estado"));
                tvActividades.widthProperty().addListener(new ChangeListener<Number>(){
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, 
                    Number newValue) {
                TableHeaderRow header = (TableHeaderRow) tvActividades.lookup("TableHeaderRow");
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
    
    private void cargarInformacionCronograma(){
        actividades = FXCollections.observableArrayList();
        ActividadRespuesta respuestaBD = ActividadDAO.obtenerInformacionActividad(
                SingletonUsuario.getInstancia().getUsuario().getIdEstudiante());
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
                actividades.addAll(respuestaBD.getActividades());
                tvActividades.setItems(actividades);                
                break;
        }
    }    

    @FXML
    private void clicBtnProgramarActividad(ActionEvent event) {
        irFormulario(false, null);
        cargarInformacionCronograma();
    }
     private void irFormulario(boolean esEdicion, Actividad actividadEdicion){

        try {
            FXMLLoader accesoControlador = new FXMLLoader(jfxspger.JFXSPGER.class.
                    getResource("vistas/FXMLFormularioActividad.fxml"));
            Parent vista = accesoControlador.load();
            
            FXMLFormularioActividadController formulario = accesoControlador.getController();
            formulario.inicializarInformacionFormulario(esEdicion, actividadEdicion);
                       
            Scene sceneFormulario =  new Scene(vista);
            Stage escenarioFormulario = (Stage) lbTitulo.getScene().getWindow();
            escenarioFormulario.setScene(sceneFormulario);
            escenarioFormulario.setTitle("Formulario Activdad");      
            escenarioFormulario.show();
                        
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
     
     private void irDetalles(Actividad actividadInformacion){
         
        try {
            FXMLLoader accesoControlador = new FXMLLoader(jfxspger.JFXSPGER.
                    class.getResource("vistas/FXMLInfoActividad.fxml"));        
            Parent vista = accesoControlador.load();
            
            FXMLInfoActividadController informacion = accesoControlador.getController();
            informacion.inicializarInformacionActividad(actividadInformacion);                    
            
            Scene sceneDetalles = new Scene(vista);
            Stage escenarioDetalles = (Stage) lbTitulo.getScene().getWindow();
            escenarioDetalles.setScene(sceneDetalles);
            escenarioDetalles.setTitle("Detalles Activdad");      
            escenarioDetalles.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void clicBtnModificarActividad(ActionEvent event) {        
        Actividad actividadSeleccionada = tvActividades.getSelectionModel().getSelectedItem();
        if(actividadSeleccionada != null){
            if(actividadSeleccionada.getIdEstado() == 1){
                irFormulario(true,actividadSeleccionada);
            } else {
                Utilidades.mostrarDialogoSimple("Actividad calificada", 
                    "No puedes modificar una actividad "
                    + "que ya ha sido calificada.", Alert.AlertType.WARNING);
            }
                
        }else{
            Utilidades.mostrarDialogoSimple("Selecciona una actividad", 
                    "Debes selecionar una activdad del cronograma para poder"
                    + " realizar la modificación.", Alert.AlertType.WARNING);
        }
    }

    @FXML
    private void clicBtnVerDetalles(ActionEvent event) {
        Actividad actividadInformacion = tvActividades.getSelectionModel().getSelectedItem();
        if(actividadInformacion != null){
            irDetalles(actividadInformacion);
        }else{
            Utilidades.mostrarDialogoSimple("Selecciona una actividad", 
                    "Debes selecionar una activdad del cronograma para poder"
                    + " ver los detalles.", Alert.AlertType.WARNING);
        }
    }
    
    public void inicializarInformacionCronograma(){                
            lbTitulo.setText("Cronograma de actividades");
            cargarInformacionCronograma();        
    }

}
