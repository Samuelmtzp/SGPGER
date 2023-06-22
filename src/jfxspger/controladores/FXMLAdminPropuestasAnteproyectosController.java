/*
* Autor: Luis Angel ElizaLde Arroyo
* Fecha de creación: 20/06/2023
* Descripción: Clase encargada de administrar las propuestas de anteproyectos
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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
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

public class FXMLAdminPropuestasAnteproyectosController extends FXMLPrincipalAcademicoController {

    @FXML
    private TableView<Anteproyecto> tvAnteproyecto;
    @FXML
    private TableColumn columnNombre;
    @FXML
    private TableColumn columNombreProyecto;
    @FXML
    private TableColumn columDuracionAprox;
    @FXML
    private TableColumn columFechaCreacion;
    @FXML
    private TableColumn columDirector;
    @FXML
    private TableColumn columEstado;
    private ObservableList<Anteproyecto> anteproyectos;
   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTabla();
        cargarInformacion();
        validarSeccionesPermitidas();
    }

     private void validarAccionesPermitidas() {
        if (esResponsableDeCA()) {
            btnPropuestas.setDisable(false);            
        }
    }
    
    private void configurarTabla(){
        columnNombre.setCellValueFactory(new PropertyValueFactory("nombreTrabajo"));
        columNombreProyecto.setCellValueFactory(new PropertyValueFactory(
                "proyectoInvestigacion"));
        columDuracionAprox.setCellValueFactory(new PropertyValueFactory("duracionAproximada"));
        columFechaCreacion.setCellValueFactory(new PropertyValueFactory("fechaCreacion"));
        columDirector.setCellValueFactory(new PropertyValueFactory("Director"));
        columEstado.setCellValueFactory(new PropertyValueFactory("Estado"));
    }    
    
    private void cargarInformacion(){
        anteproyectos = FXCollections.observableArrayList();
        AnteproyectoRespuesta respuestaBD = AnteproyectoDAO.
                obtenerInformacionAnteproyectoConValidacionPendiente(
                        SingletonUsuario.getInstancia().getUsuario().
                getIdcuerpoAcademico());
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
                    tvAnteproyecto.setItems(anteproyectos);
                break;
        }
    }
  

    @FXML
    private void clicValidarAnteproyecto(ActionEvent event) {
        if( tvAnteproyecto.getSelectionModel().getSelectedIndex() != -1){
        Anteproyecto anteproyectoSeleccionado = 
                    tvAnteproyecto.getSelectionModel().getSelectedItem();
            irInformacionAnteproyecto(true, anteproyectoSeleccionado);
        }else{
             Utilidades.mostrarDialogoSimple("Selección anteproyecto", 
                    "Selecciona un anteproyecto para su validacion", 
                    Alert.AlertType.INFORMATION);
        }
    }
    
     private void irInformacionAnteproyecto(boolean esValidacion, Anteproyecto anteproyecto){
        try{
            FXMLLoader accesoControlador = new FXMLLoader(jfxspger.
                    JFXSPGER.class.getResource("/jfxspger/vistas/FXMLInfoAnteproyecto.fxml"));
            Parent vista = accesoControlador.load();
            FXMLInfoAnteproyectoController formulario = accesoControlador.getController();
            Scene sceneFormulario = new Scene(vista);
            Stage escenarioPrincipal = (Stage) lbTitulo.getScene().getWindow();
            escenarioPrincipal.setTitle("Informacion de anteproyecto");
            escenarioPrincipal.setScene(sceneFormulario);
            formulario.inicializarInformacion(esValidacion, anteproyecto);
        }catch(IOException e){
            Utilidades.mostrarDialogoSimple("Error", 
                    "No se puede mostrar la pantalla de informacion de anteproyecto", 
                    Alert.AlertType.ERROR);  
        }
        
     }

}
