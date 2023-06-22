/*
* Autor: Samuel Martínez Pazos
* Fecha de creación: 21/06/2023
* Descripción: Clase controladora para mostrar los anteproyectos disponibles al estudiante
*/
package jfxspger.controladores;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import jfxspger.modelo.dao.AnteproyectoDAO;
import jfxspger.modelo.pojo.Anteproyecto;
import jfxspger.modelo.pojo.AnteproyectoRespuesta;
import jfxspger.utilidades.Constantes;
import jfxspger.utilidades.Utilidades;

public class FXMLPropuestasEstudianteController extends FXMLPrincipalEstudianteController {

    @FXML
    private TableView<Anteproyecto> tvAnteproyecto;
    @FXML
    private TableColumn columnNombre;
    @FXML
    private TableColumn columNombreProyecto;
    @FXML
    private TableColumn columDirector;
    @FXML
    private TextField tfBusqueda;
    @FXML
    private TableColumn columDuracionAprox;
    @FXML
    private TableColumn columFechaCreacion;
    private ObservableList<Anteproyecto> anteproyectos;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarInformacion();
        configurarTabla();
        configurarBusquedaTabla();
        validarSeccionesPermitidas();
    }
    
    private void configurarTabla(){
        columnNombre.setCellValueFactory(new PropertyValueFactory("nombreTrabajo"));
        columNombreProyecto.setCellValueFactory(new PropertyValueFactory(
                "proyectoInvestigacion"));
        columDuracionAprox.setCellValueFactory(new PropertyValueFactory("duracionAproximada"));
        columFechaCreacion.setCellValueFactory(new PropertyValueFactory("fechaCreacion"));
        columDirector.setCellValueFactory(new PropertyValueFactory("Director"));
    }    
    
    private void cargarInformacion(){
        anteproyectos = FXCollections.observableArrayList();
        AnteproyectoRespuesta respuestaBD = 
                AnteproyectoDAO.obtenerInformacionAnteproyectosEstadoDisponible();
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
    private void clicConsultarAnteproyecto(MouseEvent event) {
        if (event.isPrimaryButtonDown() && event.getClickCount() == 2 && 
                tvAnteproyecto.getSelectionModel().getSelectedIndex() != -1) {
            Anteproyecto anteproyectoSeleccionado = 
                    tvAnteproyecto.getSelectionModel().getSelectedItem();
            irInformacionAnteproyecto(anteproyectoSeleccionado);
        }
    }
    
    private void irInformacionAnteproyecto(Anteproyecto anteproyecto){
        Stage escenarioBase = (Stage) lbTitulo.getScene().getWindow();
        escenarioBase.setScene(
                Utilidades.inicializarEscena("vistas/FXMLInfoAnteproyectoEstudiante.fxml"));
        escenarioBase.setTitle("Información anteproyecto estudiante");
        escenarioBase.show();
    }
    
    private void configurarBusquedaTabla() {
        if (!anteproyectos.isEmpty()) {
            FilteredList<Anteproyecto> filtradoAnteproyecto = 
                    new FilteredList<>(anteproyectos, p-> true);
            tfBusqueda.textProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observable, 
                        String oldValue, String newValue) {
                    SortedList<Anteproyecto> sortedListaAnteproyectos = 
                            new SortedList<>(filtradoAnteproyecto);
                    sortedListaAnteproyectos.comparatorProperty().bind(
                            tvAnteproyecto.comparatorProperty());
                    tvAnteproyecto.setItems(sortedListaAnteproyectos);
                    
                    filtradoAnteproyecto.setPredicate(anteproyectoFiltro -> {
                        if (newValue == null || newValue.isEmpty()) {
                            return true;
                        }
                        
                        String lowerNewValue = newValue.toLowerCase();
                        return anteproyectoFiltro.getDirector().toLowerCase().
                                contains(lowerNewValue);
                    });
                }
            });
        }
    }
     
}
