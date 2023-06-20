/*
* Autor: Luis Angel Elizalde Arroyo
* Fecha de creación: 14/06/2023
* Descripción: Clase encargada de administrar las LGAC
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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import jfxspger.modelo.dao.LgacDAO;
import jfxspger.modelo.pojo.Lgac;
import jfxspger.modelo.pojo.LgacRespuesta;
import jfxspger.utilidades.Constantes;
import jfxspger.utilidades.Utilidades;

public class FXMLAdminLgacController extends FXMLPrincipalAdministradorController {

    @FXML
    private Label lbTitulo;
    @FXML
    private TableView<Lgac> tvLGAC;
    @FXML
    private TableColumn columbNombre;
    
    private Lgac lgacEdicion;
    private boolean esEdicion;
    private ObservableList<Lgac> lgacs;
    @FXML
    private TableColumn columnCuerpoAcademico;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTabla();
        cargarInformacionTabla();
    }    
    
     private void cargarInformacionTabla() {
        lgacs = FXCollections.observableArrayList();
        LgacRespuesta respuestaBD = LgacDAO.obtenerInformacionLgac();
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
                    lgacs.addAll(respuestaBD.getListaLgac());
                    tvLGAC.setItems(lgacs);
                break;
        }
    }
     
     private void configurarTabla() {
        columbNombre.setCellValueFactory(new PropertyValueFactory("nombre"));
        columnCuerpoAcademico.setCellValueFactory(new PropertyValueFactory("cuerpoAcademico"));
    }
     
    @FXML
    private void clicBtnAgregar(ActionEvent event) {
        irFormulario(false, null);
    }

    @FXML
    private void clicBtnModificarLGAC(ActionEvent event) {
        Lgac lgacSelected = tvLGAC.getSelectionModel().getSelectedItem();
        if(lgacSelected != null){
           irFormulario(true, lgacSelected);
        }else{
            Utilidades.mostrarDialogoSimple("Selecciona una LGAC", 
                    "Selecciona el registro en la tabla de las LGAC para su edicion", 
                    Alert.AlertType.WARNING);
            }
    }
    
     private void irFormulario(boolean esEdicion, Lgac lgacEdicion){
         try{
            FXMLLoader accesoControlador = new FXMLLoader(
                    jfxspger.JFXSPGER.class.getResource("vistas/FXMLLgacFormulario.fxml"));
            Parent vista = accesoControlador.load();
            FXMLLgacFormularioController formulario = accesoControlador.getController();
            Scene sceneFormulario = new Scene(vista);
            Stage escenarioPrincipal = (Stage)lbTitulo.getScene().getWindow();
            escenarioPrincipal.setScene(sceneFormulario);
            formulario.inicializarInformacionFormulario(esEdicion, lgacEdicion);
            
         }catch(IOException ex){
             Logger.getLogger(FXMLLgacFormularioController.class.getName()).
                     log(Level.SEVERE, null, ex);
         }
    }

    @FXML
    private void clicBtnEliminar(ActionEvent event) {
         Lgac lgacSeleccionado = tvLGAC.getSelectionModel().getSelectedItem();
        if(lgacSeleccionado != null){
            boolean borrarRegistro = Utilidades.mostrarDialogoConfirmacion(
                    "Eliminar registro de la LGAC", 
                    "¿Estás seguro de que deseas eliminar la LGAC?");
            if(borrarRegistro==true){
                int codigoRespuesta = LgacDAO.eliminarLgac(lgacSeleccionado.getIdLgac());
                switch(codigoRespuesta){
                    case Constantes.ERROR_CONEXION:
                Utilidades.mostrarDialogoSimple("Error de conexion", 
                        "La lgac no pudo ser eliminada debido a un error en su conexion...", 
                        Alert.AlertType.ERROR);
                break;
                    case Constantes.ERROR_CONSULTA:
                Utilidades.mostrarDialogoSimple("Error al eliminiar", 
                        "La informacion de la LGAC no puedo ser eliminada, "
                        + "por favor intentelo más tarde", 
                        Alert.AlertType.WARNING);
                break;
                    case Constantes.OPERACION_EXITOSA:
                         cargarInformacionTabla();
                break;
                }
            }
        }else{
             Utilidades.mostrarDialogoSimple("Selecciona una LGAC", 
                     "Selecciona el registro en la tabla de la LGAC", 
                    Alert.AlertType.WARNING);
        }
    }
  
}
