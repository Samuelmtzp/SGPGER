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
import jfxspger.modelo.dao.ActividadDAO;
import jfxspger.modelo.pojo.Actividad;
import jfxspger.modelo.pojo.ActividadRespuesta;
import jfxspger.modelo.pojo.Anteproyecto;
import jfxspger.modelo.pojo.Estudiante;
import jfxspger.utilidades.Constantes;
import jfxspger.utilidades.Utilidades;

public class FXMLAnteproyectoInformacionController implements Initializable {

    @FXML
    private Label lTituloAnteproyecto;
    @FXML
    private Label lDetallesAnteproyecto;
    private Anteproyecto anteproyecto;    
    @FXML
    private TableColumn cNomAct;
    @FXML
    private TableColumn cEstado;
    @FXML
    private TableColumn cFechaInicio;
    @FXML
    private TableColumn cFechaFin;
    @FXML
    private TableColumn cFechaCreacion;
    private ObservableList<Actividad> actividades;  
    @FXML
    private TableView<Actividad> tvAvanceActividades;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarInformacionAnteproyecto();
        configurarTabla();
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
                Utilidades.mostrarDialogoSimple("Sin conexión", "Lo sentimos, por el momento no hay conexión", Alert.AlertType.ERROR);
                break;
            case Constantes.ERROR_CONSULTA:
                Utilidades.mostrarDialogoSimple("Error al cargar los datos", 
                        "Hubo un error al cargar la información. Por favor intente más tarde", Alert.AlertType.WARNING);
                break;
            case Constantes.OPERACION_EXITOSA:
                actividades.addAll(respuestaBD.getActividades());
                tvAvanceActividades.setItems(actividades);                
                break;
        }
    }
    
    private void configurarTabla(){        
        cNomAct.setCellValueFactory(new PropertyValueFactory("Nombre de la actividad"));
        cEstado.setCellValueFactory(new PropertyValueFactory("Estado"));
        cFechaInicio.setCellValueFactory(new PropertyValueFactory("Fecha de inicio"));
        cFechaFin.setCellValueFactory(new PropertyValueFactory("Fecha de fin"));
        cFechaCreacion.setCellValueFactory(new PropertyValueFactory("Fecha de creación"));
    }

    @FXML
    private void clicBtnVerCronograma(ActionEvent event) {
        irCronograma();
    }
    
    private void irCronograma(){
        try {
            FXMLLoader accesoControlador = new FXMLLoader(jfxspger.JFXSPGER.class.getResource("vistas/FXMLCronogramaActividades.fxml"));        
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

    @FXML
    private void clicBtnRegresar(MouseEvent event) {
        Stage escenarioPrincipal = (Stage) lTituloAnteproyecto.getScene().getWindow();
        escenarioPrincipal.close();
    }
    
    
}
