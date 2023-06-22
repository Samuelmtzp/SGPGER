/*
* Autor: Luis Angel ElizaLde Arroyo
* Fecha de creación: 22/06/2023
* Descripción: Clase encargada de mostrar los detalles de las revisiones de anteproyectos
*/
package jfxspger.controladores;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import jfxspger.modelo.dao.RevisionAnteproyectoDAO;
import jfxspger.modelo.pojo.Anteproyecto;
import jfxspger.modelo.pojo.RevisionAnteproyecto;
import jfxspger.modelo.pojo.RevisionAnteproyectoRespuesta;
import jfxspger.utilidades.Constantes;
import jfxspger.utilidades.Utilidades;


public class FXMLDetallesRevisionesController extends FXMLPrincipalAcademicoController {

    @FXML
    private Label lbTitulo;
    @FXML
    private Label lbFecha;
    @FXML
    private TextArea taComentarios;
    @FXML
    private Button btnAnteproyectos;
    @FXML
    private Button btnPropuestas;
    @FXML
    private Button btnEstudiantes;
    @FXML
    private Button btnRevisiones;
    private Anteproyecto anteproyecto;
    private ObservableList<RevisionAnteproyecto> revision;

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        validarSeccionesPermitidas();
        cargarInformacionRevision();
        cargarDatosRevision();
    }

     public void inicializarInformacion( 
            Anteproyecto anteproyecto){
        this.anteproyecto=anteproyecto;
    }
     
    private void cargarDatosRevision(){
        for(int i=0;i<revision.size();i++){
            if(revision.get(i).getIdAnteproyecto()==anteproyecto.getIdAnteproyecto()){
                taComentarios.setText(revision.get(i).getComentarioRevision());
                lbFecha.setText(revision.get(i).getFechaRevision());
            }
        }
    } 
     
    private void cargarInformacionRevision(){
        revision = FXCollections.observableArrayList();
        RevisionAnteproyectoRespuesta respuestaBD = RevisionAnteproyectoDAO.
              obtenerInformacionRevisionAnteproyecto(anteproyecto.getIdAnteproyecto());
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
                    revision.addAll(respuestaBD.getRevisionesAnteproyecto());
                break;
        }
    }
    
}
