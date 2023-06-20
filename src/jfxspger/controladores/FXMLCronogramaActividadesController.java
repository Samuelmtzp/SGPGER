/*
* Autor: Carlos Rodriguez Herrera
* Fecha de creación: 18/05/2023
* Descripción: Clase controladora para el cronograma de actividades
*/

package jfxspger.controladores;

import com.sun.javafx.scene.control.skin.TableHeaderRow;
import java.io.IOException;
import java.net.URL;
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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import jfxspger.interfaz.INotificacionOperacionActividad;
import jfxspger.modelo.dao.ActividadDAO;
import jfxspger.modelo.pojo.Actividad;
import jfxspger.modelo.pojo.ActividadRespuesta;
import jfxspger.modelo.pojo.Estudiante;
import jfxspger.utilidades.Constantes;
import jfxspger.utilidades.Utilidades;

public class FXMLCronogramaActividadesController implements Initializable, 
        INotificacionOperacionActividad {

    @FXML
    private TableView<Actividad> tvActividades;
    @FXML
    private TableColumn cNombreActividad;    
    @FXML
    private TableColumn cFechaIncio;
    @FXML
    private TableColumn cFechaFin;
    private ObservableList<Actividad> actividades;
    @FXML
    private Label lTitulo;
    @FXML
    private TableColumn cFechaCreacion;
    public int idEstudiante=3;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTabla();
        cargarInformacionCronograma(); 
   }
    
    
    private void configurarTabla(){
        cNombreActividad.setCellValueFactory(new PropertyValueFactory("titulo"));
        cFechaCreacion.setCellValueFactory(new PropertyValueFactory("fechaCreacion"));
        cFechaIncio.setCellValueFactory(new PropertyValueFactory("fechaInicio"));
        cFechaFin.setCellValueFactory(new PropertyValueFactory("fechaFin"));
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
        ActividadRespuesta respuestaBD = ActividadDAO.obtenerInformacionActividad(idEstudiante);
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
        
        FXMLInicioSesionController inicioSesion = new FXMLInicioSesionController();        
        int id = inicioSesion.getIdEstudiante();
         System.out.println("ID: " + id);
        try {
            FXMLLoader accesoControlador = new FXMLLoader(jfxspger.JFXSPGER.class.
                    getResource("vistas/FXMLActividadFormu.fxml"));
            Parent vista = accesoControlador.load();
            
            FXMLActividadFormularioController formulario = accesoControlador.getController();
            formulario.setIdEstudiante(0);
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
            FXMLLoader accesoControlador = new FXMLLoader(jfxspger.JFXSPGER.
                    class.getResource("vistas/FXMLActividadInformacion.fxml"));        
            Parent vista = accesoControlador.load();
            
            FXMLActividadInformacionController informacion = accesoControlador.getController();
            informacion.inicializarInformacionActividad(actividadInformacion, this);
        
            Stage escenarioDetalles = new Stage();
            escenarioDetalles.setScene(new Scene(vista));
            escenarioDetalles.setTitle("Detalles Activdad");
            escenarioDetalles.initModality(Modality.APPLICATION_MODAL);       
            escenarioDetalles.showAndWait();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void clicBtnModificarActividad(ActionEvent event) {        
        Actividad actividadSeleccionada = tvActividades.getSelectionModel().getSelectedItem();
        if(actividadSeleccionada != null){
            irFormulario(true,actividadSeleccionada);
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
    private void clicIrAnteproyecto(ActionEvent event) {
        Stage escenarioBase = (Stage) lTitulo.getScene().getWindow();
        escenarioBase.setScene(Utilidades.inicializarEscena(
                "vistas/FXMLAnteproyectoInformacion.fxml"));
        escenarioBase.setTitle("Informacion de anteproyecto");
        escenarioBase.show();        
    }

    @FXML
    private void clicIrCronograma(ActionEvent event) {
        Stage escenarioBase = (Stage) lTitulo.getScene().getWindow();
        escenarioBase.setScene(Utilidades.inicializarEscena(
                "vistas/FXMLCronogramaActividades.fxml"));
        escenarioBase.setTitle("Cronograma de actividades");
        escenarioBase.show();        
    }

    @FXML
    private void clicIrCursos(ActionEvent event) {
        Stage escenarioBase = (Stage) lTitulo.getScene().getWindow();
        escenarioBase.setScene(Utilidades.inicializarEscena("vistas/FXMLEstudiantesCurso.fxml"));
        escenarioBase.setTitle("Cursos");
        escenarioBase.show();        
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
        Stage escenarioBase = (Stage) lTitulo.getScene().getWindow();
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
        Stage escenarioBase = (Stage) lTitulo.getScene().getWindow();
        escenarioBase.setScene(Utilidades.inicializarEscena(
                "vistas/FXMLPrincipalEstudiante.fxml"));
        escenarioBase.setTitle("Ventana Principal");
        escenarioBase.show();       
    }

    public int getIdEstudiante() {
        return idEstudiante;
    }

    public void setIdEstudiante(int idEstudiante) {
        this.idEstudiante = idEstudiante;
    }
        
}
