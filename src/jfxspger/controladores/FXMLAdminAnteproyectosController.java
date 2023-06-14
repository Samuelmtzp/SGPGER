/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package jfxspger.controladores;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafx.stage.Stage;
import jfxspger.modelo.dao.AnteproyectoDAO;
import jfxspger.modelo.pojo.Anteproyecto;
import jfxspger.modelo.pojo.AnteproyectoRespuesta;
import jfxspger.modelo.pojo.Lgac;
import jfxspger.utilidades.Constantes;
import jfxspger.utilidades.Utilidades;

/**
 * FXML Controller class
 *
 * @author king_
 */
public class FXMLAdminAnteproyectosController extends FXMLPrincipalAcademicoController {

    @FXML
    private Label lbTitulo;
    @FXML
    private TableView<Anteproyecto> tvAnteproyecto;
    @FXML
    private TableColumn columnNombre;
    @FXML
    private TableColumn columLGAC;
    @FXML
    private TableColumn columFechaInicio;
    @FXML
    private TableColumn columFechaFin;

    private ObservableList<Anteproyecto> anteproyectos;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void clicCrearAnteproyecto(ActionEvent event) {
        irFormulario(false, null);
    }

    @FXML
    private void clicEliminarAnteproyecto(ActionEvent event) {
    }

    @FXML
    private void clicModificarAnteproyecto(ActionEvent event) {
        Anteproyecto anteproyectoSeleccionado = tvAnteproyecto.getSelectionModel().getSelectedItem();
        if(anteproyectoSeleccionado!= null){
            irFormulario(true, anteproyectoSeleccionado);
        }else{
            Utilidades.mostrarDialogoSimple("Selecciona un anteproyecto", "Selecciona el registro en la tabla de anteproyecto para su edicion", 
                    Alert.AlertType.WARNING);
            }
    }

    @FXML
    private void clicVerDetalles(ActionEvent event) {
    }
    
    private void irFormulario(boolean esEdicion, Anteproyecto anteproyectoEdicion){
         try{
            FXMLLoader accesoControlador = new FXMLLoader(jfxspger.JFXSPGER.class.getResource("vistas/FXMLFormularioAnteproyecto.fxml"));
            Parent vista = accesoControlador.load();
            FXMLFormularioAnteproyectoController formulario = accesoControlador.getController();
            Scene sceneFormulario = new Scene(vista);
            Stage escenarioPrincipal = (Stage)lbTitulo.getScene().getWindow();
            escenarioPrincipal.setScene(sceneFormulario);
            formulario.inicializarInformacionFormulario(esEdicion, anteproyectoEdicion);
         }catch(IOException ex){
             Logger.getLogger(FXMLLgacFormularioController.class.getName()).log(Level.SEVERE, null, ex);
         }
    }
    
     private void configurarTabla() {
        columnNombre.setCellValueFactory(new PropertyValueFactory("nombreTrabajo"));
        columLGAC.setCellValueFactory(new PropertyValueFactory("idLgac"));
        columFechaInicio.setCellValueFactory(new PropertyValueFactory("fechaInicio"));
        columFechaFin.setCellValueFactory(new PropertyValueFactory("fechaFin"));
    }
    
    private void cargarInformacionTabla() {
        anteproyectos = FXCollections.observableArrayList();
        AnteproyectoRespuesta respuestaBD = AnteproyectoDAO.obtenerInformacionAnteproyecto();
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
    
}
