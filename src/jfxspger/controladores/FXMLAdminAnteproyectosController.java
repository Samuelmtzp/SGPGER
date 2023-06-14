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
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import jfxspger.modelo.pojo.Anteproyecto;
import jfxspger.modelo.pojo.Lgac;

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
        //TO DO validacion de seleccion en TV
         irFormulario(true, null); //Por el momento es NULL
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
    
}
