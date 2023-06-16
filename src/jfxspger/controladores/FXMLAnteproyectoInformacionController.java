/*
* Autor: Carlos Rodriguez Herrera
* Fecha de creación: 18/05/2023
* Descripción: Clase controladora para la información del anteproyecto
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
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import jfxspger.modelo.dao.ActividadDAO;
import jfxspger.modelo.pojo.Actividad;
import jfxspger.modelo.pojo.ActividadRespuesta;
import jfxspger.modelo.pojo.Anteproyecto;
import jfxspger.utilidades.Constantes;
import jfxspger.utilidades.Utilidades;

public class FXMLAnteproyectoInformacionController implements Initializable {

    private Label lTituloAnteproyecto;
    private Anteproyecto anteproyecto;    
    private TableColumn cNomAct;
    private TableColumn cEstado;
    private TableColumn cFechaInicio;
    private TableColumn cFechaFin;
    private TableColumn cFechaCreacion;
    private ObservableList<Actividad> actividades;  
    private TableView<Actividad> tvAvanceActividades;
    @FXML
    private Label lbTitulo;
    @FXML
    private Label lbDescAnteproyecto;
    @FXML
    private Label lbTituloAnteproyecto;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
//        cargarInformacionAnteproyecto();
//        configurarTabla();
    }
    
    private void cargarInformacionAnteproyecto(){
//        lTituloAnteproyecto.setText(anteproyecto.getNombre());
//        lDetallesAnteproyecto.setText(anteproyecto.getComentarioRevision());
        
    }
    
    private void cargarInformacionTabla(){
        actividades = FXCollections.observableArrayList();
        ActividadRespuesta respuestaBD = ActividadDAO.obtenerInformacionActividad();
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
                tvAvanceActividades.setItems(actividades);                
                break;
        }
    }
    
    private void configurarTabla(){        
        
    }

    private void clicBtnVerCronograma(ActionEvent event) {
        irCronograma();
    }
    
    private void irCronograma(){
        try {
            FXMLLoader accesoControlador = new FXMLLoader(jfxspger.JFXSPGER.class.
                    getResource("vistas/FXMLCronogramaActividades.fxml"));        
            Parent vista = accesoControlador.load();
            
            FXMLCronogramaActividadesController cronograma = accesoControlador.getController();
            cronograma.inicializarInformacionCronograma();
        
            Stage escenarioFormulario = new Stage();
            escenarioFormulario.setScene(new Scene(vista));
            escenarioFormulario.setTitle("Cronograma de Actividades");
            escenarioFormulario.initModality(Modality.APPLICATION_MODAL);       
            escenarioFormulario.showAndWait();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }    

    private void clicBtnRegresar(MouseEvent event) {
        Stage escenarioPrincipal = (Stage) lTituloAnteproyecto.getScene().getWindow();
        escenarioPrincipal.close();
    }

    @FXML
    private void clicIrPrincipalEstudiante(ActionEvent event) {
        Stage escenarioBase = (Stage) lbTitulo.getScene().getWindow();
        escenarioBase.setScene(Utilidades.inicializarEscena("vistas/FXMLPrincipalEstudiante.fxml"));
        escenarioBase.setTitle("Ventana Principal");
        escenarioBase.show();           
    }

    @FXML
    private void clicIrAnteproyecto(ActionEvent event) {
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
    private void clicIrPropuestas(ActionEvent event) {
    }
        
}
