/*
* Autor: Carlos Rodriguez Herrera
* Fecha de creación: 18/05/2023
* Descripción: 
*/

package jfxspger.controladores;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import jfxspger.interfaz.INotificacionOperacionActividad;
import jfxspger.modelo.dao.ActividadDAO;
import jfxspger.modelo.pojo.Actividad;
import jfxspger.modelo.pojo.ActividadRespuesta;
import jfxspger.utilidades.Constantes;
import jfxspger.utilidades.Utilidades;

public class FXMLCronogramaActividadesController implements Initializable, INotificacionOperacionActividad {

    @FXML
    private TableView<Actividad> tvActividades;
    @FXML
    private TableColumn cNombreActividad;
    @FXML
    private TableColumn cEstado;
    @FXML
    private TableColumn cFechaIncio;
    @FXML
    private TableColumn cFechaFin;
    private ObservableList<Actividad> actividades;
    @FXML
    private Label lTitulo;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTabla();
        cargarInformacionCronograma();
    }
    
    private void configurarTabla(){
        cNombreActividad.setCellValueFactory(new PropertyValueFactory("Nombre"));
        cEstado.setCellValueFactory(new PropertyValueFactory("Estado"));
        cFechaIncio.setCellValueFactory(new PropertyValueFactory("Fecha de inicio"));
        cFechaFin.setCellValueFactory(new PropertyValueFactory("Fecha de fin"));
    }
    
    private void cargarInformacionCronograma(){
        actividades = FXCollections.observableArrayList();
        ActividadRespuesta respuestaBD = ActividadDAO.obtenerInformacionActividad();
        switch(respuestaBD.getCodigoRespuesta()){
            case Constantes.ERROR_CONEXION:
                Utilidades.mostrarDialogoSimple("Sin conexión", "Lo sentimos, por el momento no hay conexión", Alert.AlertType.ERROR);
                break;
            case Constantes.ERROR_CONSULTA:
                Utilidades.mostrarDialogoSimple("Error al cargar los datos", 
                        "Hubo un error al cargar la información. Por favor intente más tarde", Alert.AlertType.WARNING);
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
     
     private void irDetalles(Actividad actividadInformacion){
        try {
            FXMLLoader accesoControlador = new FXMLLoader(jfxspger.JFXSPGER.class.getResource("vistas/FXMLActividadInformacion.fxml"));        
            Parent vista = accesoControlador.load();
            
            FXMLActividadInformacionController informacion = accesoControlador.getController();
            informacion.inicializarInformacionActividad(actividadInformacion, this);
        
            Stage escenarioFormulario = new Stage();
            escenarioFormulario.setScene(new Scene(vista));
            escenarioFormulario.setTitle("Detalles Activdad");
            escenarioFormulario.initModality(Modality.APPLICATION_MODAL);       
            escenarioFormulario.showAndWait();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void clicBtnModificarActividad(ActionEvent event) {
        Actividad activdadSeleccionada = tvActividades.getSelectionModel().getSelectedItem();
        if(activdadSeleccionada != null){
            irFormulario(true,activdadSeleccionada);
        }else{
            Utilidades.mostrarDialogoSimple("Selecciona una actividad", "Debes selecionar una activdad del cronograma para poder"
                    + " realizar la modificación.", Alert.AlertType.WARNING);
        }
    }

    @FXML
    private void clicBtnVerDetalles(ActionEvent event) {
        Actividad activdadSeleccionada = tvActividades.getSelectionModel().getSelectedItem();
        if(activdadSeleccionada != null){
            irDetalles(activdadSeleccionada);
        }else{
            Utilidades.mostrarDialogoSimple("Selecciona una actividad", "Debes selecionar una activdad del cronograma para poder"
                    + " ver los detalles.", Alert.AlertType.WARNING);
        }
    }
    public void inicializarInformacionCronograma(){
                
            lTitulo.setText("Cronograma de actividades");
            cargarInformacionCronograma();        
    }
    
    @Override
    public void notificarOperacionGuardar(String nombreActividad) {
        cargarInformacionCronograma();
    }

    @Override
    public void notificarOperacionActualizar(String nombreActividad) {
        cargarInformacionCronograma();  
    }

    @FXML
    private void clicBtnRegresar(MouseEvent event) {
        Stage escenarioPrincipal = (Stage) lTitulo.getScene().getWindow();
        escenarioPrincipal.close();        
    }    
}
