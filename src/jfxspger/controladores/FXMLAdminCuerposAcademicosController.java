package jfxspger.controladores;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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
    
    ObservableList<CuerpoAcademico> cuerposAcademicos;

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
    }

    @FXML
    private void clicBtnModificarCuerpoAcademico(ActionEvent event) {
    }

    @FXML
    private void clicBtnAgregarCuerpoAcademico(ActionEvent event) {
        Stage escenarioBase = (Stage) lbTitulo.getScene().getWindow();
        escenarioBase.setScene(
                Utilidades.inicializarEscena("vistas/FXMLFormularioCuerpoAcademico.fxml"));
        escenarioBase.setTitle("Formulario de cuerpo académico");
        escenarioBase.show();
    }
    
}