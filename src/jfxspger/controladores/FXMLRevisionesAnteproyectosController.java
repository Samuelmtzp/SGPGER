/*
* Autor: Luis Angel ElizaLde Arroyo
* Fecha de creación: 22/06/2023
* Descripción: Clase encargada de administrar las revisiones de anteproyectos
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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import jfxspger.modelo.dao.AnteproyectoDAO;
import jfxspger.modelo.pojo.Anteproyecto;
import jfxspger.modelo.pojo.AnteproyectoRespuesta;
import jfxspger.utilidades.Constantes;
import jfxspger.utilidades.SingletonUsuario;
import jfxspger.utilidades.Utilidades;


public class FXMLRevisionesAnteproyectosController extends FXMLPrincipalAcademicoController {

    @FXML
    private Label lbTitulo;
    @FXML
    private TableView<Anteproyecto> tvAnteproyectos;
    @FXML
    private TableColumn columnNombreTrabajo;
    @FXML
    private TableColumn columnNombreProyecto;
    @FXML
    private TableColumn columnDuracion;
    @FXML
    private TableColumn columnDirector;
    @FXML
    private TableColumn columnEstado;
    private ObservableList<Anteproyecto> anteproyectos;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTabla();
        cargarInformacion();
        validarSeccionesPermitidas();
    }    

    @FXML
    private void clicDetallesRevision(ActionEvent event) {
        if( tvAnteproyectos.getSelectionModel().getSelectedIndex() != -1){
        Anteproyecto anteproyectoSeleccionado = 
                    tvAnteproyectos.getSelectionModel().getSelectedItem();
            irDetalleRevision(anteproyectoSeleccionado);
        }else{
             Utilidades.mostrarDialogoSimple("Selección anteproyecto", 
                    "Selecciona un anteproyecto para ver los detalles de su revision", 
                    Alert.AlertType.INFORMATION);
        } 
       
    }
    
     private void configurarTabla(){
        columnNombreTrabajo.setCellValueFactory(new PropertyValueFactory("nombreTrabajo"));
        columnNombreProyecto.setCellValueFactory(new PropertyValueFactory(
                "proyectoInvestigacion"));
        columnDuracion.setCellValueFactory(new PropertyValueFactory("duracionAproximada"));
        columnDirector.setCellValueFactory(new PropertyValueFactory("Director"));
        columnEstado.setCellValueFactory(new PropertyValueFactory("Estado"));
    }
     
      private void cargarInformacion(){
        anteproyectos = FXCollections.observableArrayList();
        AnteproyectoRespuesta respuestaBD;
        if (esMiembroDeCA()) {
            respuestaBD = AnteproyectoDAO.obtenerInformacionRevisionesAnteproyectos(
                SingletonUsuario.getInstancia().getUsuario().getIdcuerpoAcademico());
        } else {
            respuestaBD = AnteproyectoDAO.obtenerInformacionAnteproyectos();
        }
        switch (respuestaBD.getCodigoRespuesta()) {
            case Constantes.ERROR_CONEXION:
                    Utilidades.mostrarDialogoSimple("Sin conexión", 
              "Lo sentimos, por el momento no hay conexión para poder cargar la información", 
                    Alert.AlertType.ERROR);
                break;
            case Constantes.ERROR_CONSULTA:
                    Utilidades.mostrarDialogoSimple("Error al cargar los datos", 
                    "Hubo un error al cargar la información, por favor inténtelo más tarde", 
                    Alert.AlertType.WARNING);
                break;
            case Constantes.OPERACION_EXITOSA:
                    anteproyectos.addAll(respuestaBD.getAnteproyectos());
                    tvAnteproyectos.setItems(anteproyectos);
                break;
        }
    }
      
    private void irDetalleRevision(Anteproyecto anteproyecto){
        try{
            FXMLLoader accesoControlador = new FXMLLoader(jfxspger.
                    JFXSPGER.class.getResource("/jfxspger/vistas/FXMLDetallesRevisiones.fxml"));
            System.out.println("accesoControlador = " + accesoControlador);
            Parent vista = accesoControlador.load();
            System.out.println("vista = " + vista);
            FXMLDetallesRevisionesController formulario = accesoControlador.getController();
            Scene sceneFormulario = new Scene(vista);
            Stage escenarioPrincipal = (Stage) lbTitulo.getScene().getWindow();
            escenarioPrincipal.setTitle("Detalles revision");
            escenarioPrincipal.setScene(sceneFormulario);
            formulario.inicializarInformacion(anteproyecto);
        }catch(IOException e){
            Utilidades.mostrarDialogoSimple("Error", 
                    "No se puede mostrar la pantalla de informacion de revision", 
                    Alert.AlertType.ERROR);  
        }
        
    }  
    
}
