/*
* Autor: Luis Angel ElizaLde Arroyo
* Fecha de creación: 13/06/2023
* Descripción: Clase encargada de asignar estudiantes a un anteproyecto
*/
package jfxspger.controladores;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import jfxspger.modelo.pojo.Usuario;
import jfxspger.modelo.pojo.UsuarioRespuesta;
import jfxspger.utilidades.Constantes;
import jfxspger.utilidades.Utilidades;
import jfxspger.modelo.dao.UsuarioDAO;

public class FXMLAsignarEstudiantesController extends FXMLPrincipalAcademicoController {

    @FXML
    private Label lbTitulo;
    @FXML
    private TableView<Usuario> tvAlumnosDisponibles;
    @FXML
    private TableView<Usuario> tvAlumnosAsignados;
    @FXML
    private TableColumn columnNombreEstudiante;
    @FXML
    private TableColumn columEstado;
    @FXML
    private TableColumn columEstudianteAsig;
    
    private ObservableList <Usuario> estudiantes;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    

    private void cargarInformacion(){
        estudiantes = FXCollections.observableArrayList();
        UsuarioRespuesta respuestaBD = UsuarioDAO.obtenerInformacionEstudiantesDisponibles();
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
                    estudiantes.addAll(respuestaBD.getUsuarios());
                    tvAlumnosDisponibles.setItems(estudiantes);
                break;
        }
    }
    
}
