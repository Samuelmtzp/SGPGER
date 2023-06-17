/*
* Autor: Samuel Martínez Pazos
* Fecha de creación: 12/06/2023
* Descripción: Clase controladora de administración de cuerpos académicos
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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import jfxspger.modelo.dao.CuerpoAcademicoDAO;
import jfxspger.modelo.pojo.CuerpoAcademico;
import jfxspger.modelo.pojo.CuerpoAcademicoRespuesta;
import jfxspger.utilidades.Constantes;
import jfxspger.utilidades.Utilidades;

public class FXMLAdminCuerposAcademicosController extends FXMLPrincipalAdministradorController {

    @FXML
    private Label lbTitulo;
    @FXML
    private TableView<CuerpoAcademico> tvCuerposAcademicos;
    @FXML
    private TableColumn tcCuerpoAcademico;
    @FXML
    private TableColumn tcResponsable;
    @FXML
    private TableColumn tcClave;
    @FXML
    private TableColumn tcGradoConsolidacion;
    @FXML
    private TableColumn tcDependencia;
    
    private ObservableList<CuerpoAcademico> cuerposAcademicos;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTabla();
        cargarDatosTabla();
    }
    
    private void configurarTabla(){
        tcCuerpoAcademico.setCellValueFactory(new PropertyValueFactory("nombre"));
        tcResponsable.setCellValueFactory(
                new PropertyValueFactory("nombreCompletoResponsable"));
        tcClave.setCellValueFactory(new PropertyValueFactory("clave"));
        tcGradoConsolidacion.setCellValueFactory(
                new PropertyValueFactory("gradoConsolidacion"));
        tcDependencia.setCellValueFactory(new PropertyValueFactory("dependencia"));
    }
    
    private void cargarDatosTabla() {
        cuerposAcademicos = FXCollections.observableArrayList();
        CuerpoAcademicoRespuesta respuestaBD = 
                CuerpoAcademicoDAO.obtenerInformacionCuerpoAcademico();
        switch (respuestaBD.getCodigoRespuesta()) {
            case Constantes.ERROR_CONEXION:
                Utilidades.mostrarDialogoSimple("Sin conexión", 
                    "Lo sentimos, por el momento no hay conexión para poder "
                    + "cargar la información", 
                    Alert.AlertType.ERROR);
            break;
            case Constantes.ERROR_CONSULTA:
                Utilidades.mostrarDialogoSimple("Error al cargar los datos", 
                    "Hubo un error al cargar la información, "
                    + "por favor inténtelo más tarde", 
                    Alert.AlertType.WARNING);
            break;
            case Constantes.OPERACION_EXITOSA:
                cuerposAcademicos.addAll(respuestaBD.getCuerposAcademicos());
                tvCuerposAcademicos.setItems(cuerposAcademicos);
            break;
        }
    }

    @FXML
    private void clicBtnEliminarCuerpoAcademico(ActionEvent event) {
        int posicion = tvCuerposAcademicos.getSelectionModel().getSelectedIndex();
        if (posicion != -1) {
            if (Utilidades.mostrarDialogoConfirmacion("Confirmación de cancelación", 
                    "¿Está seguro de que desea eliminar al cuerpo académico?")) {
                int respuesta = CuerpoAcademicoDAO.eliminarCuerpoAcademico(
                        tvCuerposAcademicos.getSelectionModel().
                        getSelectedItem().getIdCuerpoAcademico());
                switch(respuesta) {
                    case Constantes.ERROR_CONEXION:
                        Utilidades.mostrarDialogoSimple("Error de conexion", 
                                "El cuerpo académico no pudo ser eliminado debido "
                                + "a un error de conexión.", 
                                Alert.AlertType.ERROR);
                    break;
                    case Constantes.ERROR_CONSULTA:
                        Utilidades.mostrarDialogoSimple("Error al eliminar",
                                "No se ha podido eliminar al cuerpo académico, "
                               + "por favor inténtelo más tarde." ,
                                Alert.AlertType.WARNING);
                    break;
                    case Constantes.OPERACION_EXITOSA:
                        cargarDatosTabla();
                        Utilidades.mostrarDialogoSimple("Cuerpo académico eliminado", 
                                "El cuerpo académico fue eliminado correctamente", 
                                Alert.AlertType.INFORMATION);
                    break;
                }
            }
        } else
            Utilidades.mostrarDialogoSimple("Selección necesaria", 
                    "Para eliminar un cuerpo académico, debe seleccionarlo previamente", 
                    Alert.AlertType.WARNING);
    }

    @FXML
    private void clicBtnModificarCuerpoAcademico(ActionEvent event) {
        CuerpoAcademico cuerpoAcademicoSeleccionado = 
                tvCuerposAcademicos.getSelectionModel().getSelectedItem();
        if(cuerpoAcademicoSeleccionado != null){
            irFormularioCuerpoAcademico(true, cuerpoAcademicoSeleccionado);
        }else{
            Utilidades.mostrarDialogoSimple("Atención", "Selecciona el registro "
                    + "en la tabla para poder editarlo", Alert.AlertType.WARNING);
        }
    }

    @FXML
    private void clicBtnAgregarCuerpoAcademico(ActionEvent event) {
        irFormularioCuerpoAcademico(false, null);
    }
    
    private void irFormularioCuerpoAcademico(boolean esEdicion, CuerpoAcademico cuerpoAcademico){
        try{
            FXMLLoader accesoControlador = new FXMLLoader(jfxspger.
                    JFXSPGER.class.getResource(
                    "/jfxspger/vistas/FXMLFormularioCuerpoAcademico.fxml"));
            Parent vista = accesoControlador.load();
            FXMLFormularioCuerpoAcademicoController formulario = accesoControlador.getController();
            Scene sceneFormulario = new Scene(vista);
            Stage escenarioPrincipal = (Stage) lbTitulo.getScene().getWindow();
            escenarioPrincipal.setTitle("Formulario de cuerpo académico");
            escenarioPrincipal.setScene(sceneFormulario);
            formulario.inicializarInformacionFormulario(esEdicion, cuerpoAcademico);
        }catch(IOException e){
            Utilidades.mostrarDialogoSimple("Error", 
                    "No se puede mostrar la pantalla de formulario", 
                    Alert.AlertType.ERROR);  
        }
        
    }
    
}