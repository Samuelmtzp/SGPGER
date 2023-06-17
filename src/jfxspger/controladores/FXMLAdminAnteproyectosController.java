/*
* Autor: Luis Angel ElizaLde Arroyo
* Fecha de creación: 13/06/2023
* Descripción: Clase encargada de administrar los anteproyectos
*/
package jfxspger.controladores;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import jfxspger.modelo.dao.AnteproyectoDAO;
import jfxspger.modelo.pojo.Anteproyecto;
import jfxspger.modelo.pojo.AnteproyectoRespuesta;
import jfxspger.utilidades.Constantes;
import jfxspger.utilidades.Utilidades;

public class FXMLAdminAnteproyectosController extends FXMLPrincipalAcademicoController {

    @FXML
    private Label lbTitulo;
    @FXML
    private TableView<Anteproyecto> tvAnteproyecto;
    @FXML
    private TableColumn columnNombre;
    @FXML
    private TableColumn columNombreProyecto;
    @FXML
    private TableColumn columFechaInicio;
    @FXML
    private TableColumn columFechaFin;
    @FXML
    private TableColumn columDirector;
    @FXML
    private TableColumn columEstado;
    @FXML
    private TextField tfBusqueda;
    @FXML
    private ToggleGroup tgBusqueda;
    private ObservableList<Anteproyecto> anteproyectos;
    @FXML
    private RadioButton rbDisponibles;
    @FXML
    private RadioButton rbValidacionPendiente;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarInformacion();
        configurarTabla();
        configurarBusquedaTabla();
    }    

    private void configurarTabla(){
        columnNombre.setCellValueFactory(new PropertyValueFactory("nombreTrabajo"));
        columNombreProyecto.setCellValueFactory(new PropertyValueFactory("proyectoInvestigacion"));
        columFechaInicio.setCellValueFactory(new PropertyValueFactory("fechaInicio"));
        columFechaFin.setCellValueFactory(new PropertyValueFactory("fechaFin"));
        columDirector.setCellValueFactory(new PropertyValueFactory("Director"));
        columEstado.setCellValueFactory(new PropertyValueFactory("Estado"));
    }    
    
    private void cargarInformacion(){
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
    
    private void irFormulario(boolean esEdicion, Anteproyecto anteproyectoEdicion){
           try{
            FXMLLoader accesoControlador = new FXMLLoader(jfxspger.JFXSPGER.class.getResource("vistas/FXMLFormularioAnteproyecto1.fxml"));
            Parent vista = accesoControlador.load();
            FXMLFormularioAnteproyectoController formulario = accesoControlador.getController();
            Scene sceneFormulario = new Scene(vista);
            Stage escenarioPrincipal = (Stage)lbTitulo.getScene().getWindow();
            escenarioPrincipal.setScene(sceneFormulario);
            formulario.inicializarInformacionFormulario(esEdicion, anteproyectoEdicion);
            
         }catch(IOException ex){
             Logger.getLogger(FXMLLgacFormularioController.class.getName()).
                     log(Level.SEVERE, null, ex);
         }
    }

    @FXML
    private void clicCrearAnteproyecto(ActionEvent event) {
        irFormulario(false, null);
    }

    @FXML
    private void clicEliminarAnteproyecto(ActionEvent event) {
        Anteproyecto anteproyectoSeleccionado = tvAnteproyecto.
                getSelectionModel().getSelectedItem();
        if(anteproyectoSeleccionado != null){
            boolean borrarRegistro = Utilidades.mostrarDialogoConfirmacion(
                    "Eliminar registro del anteproyecto", 
                    "¿Estás seguro de que deseas eliminar el anteproyecto?");
            if (borrarRegistro==true) {
                int codigoRespuesta = AnteproyectoDAO.
                        eliminarAnteproyecto(anteproyectoSeleccionado.getIdAnteproyecto());
                switch(codigoRespuesta){
                    case Constantes.ERROR_CONEXION:
                Utilidades.mostrarDialogoSimple("Error de conexion", 
                        "El anteproyecto no pudo ser eliminado debido a un error en su conexion...", 
                        Alert.AlertType.ERROR);
                break;
                    case Constantes.ERROR_CONSULTA:
                Utilidades.mostrarDialogoSimple("Error al eliminiar", 
                        "La informacion del anteproyecto no puedo ser eliminada, "
                                + "por favor intentelo más tarde", 
                        Alert.AlertType.WARNING);
                break;
                    case Constantes.OPERACION_EXITOSA:
                         cargarInformacion();
                         activarFiltroRbSeleccion();
                         tfBusqueda.setText("");
                break;
                }
            }
        }else{
             Utilidades.mostrarDialogoSimple("Selecciona un anteproyecto", 
                     "Selecciona el registro en la tabla del anteproyecto", 
                    Alert.AlertType.WARNING);
        }
    }

    @FXML
    private void clicModificarAnteproyecto(ActionEvent event) {
        Anteproyecto anteproyectoSeleccionado = 
                tvAnteproyecto.getSelectionModel().getSelectedItem();
        if(anteproyectoSeleccionado!= null){
            irFormulario(true, anteproyectoSeleccionado);
        }else{
            Utilidades.mostrarDialogoSimple("Selecciona un anteproyecto", 
                    "Selecciona el registro en la tabla del anteproyecto para su edicion", 
                    Alert.AlertType.WARNING);
            }
    }

    @FXML
    private void clicIrAnteproyectos(ActionEvent event) {
    }
    
    private void irAsignarEstudiantes(Anteproyecto anteproyecto){
           try{
            FXMLLoader accesoControlador = new FXMLLoader(jfxspger.JFXSPGER.class.getResource("vistas/FXMLAsignarEstudiantes.fxml"));
            Parent vista = accesoControlador.load();
            FXMLAsignarEstudiantesController formulario = accesoControlador.getController();
            Scene sceneFormulario = new Scene(vista);
            Stage escenarioPrincipal = (Stage)lbTitulo.getScene().getWindow();
            escenarioPrincipal.setScene(sceneFormulario);
            formulario.inicializarInformacionFormulario(anteproyecto);
            
         }catch(IOException ex){
             Logger.getLogger(FXMLLgacFormularioController.class.getName()).log(Level.SEVERE, null, ex);
         }
    }

    @FXML
    private void clicConsultarAnteproyecto(MouseEvent event) {
        if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
            Node node = ((Node) event.getTarget()).getParent();
            TableRow row;
            if (node instanceof TableRow) {
                row = (TableRow) node;
                Anteproyecto anteproyectoSeleccionado = tvAnteproyecto.getSelectionModel().getSelectedItem();
                int pos = tvAnteproyecto.getSelectionModel().getSelectedIndex();
                if(pos != -1){
                    irAsignarEstudiantes(anteproyectoSeleccionado);
                }
            } 
            else {
                row = (TableRow) node.getParent();
            }
        }
    }
    
    private void irInformacionAnteproyecto(Anteproyecto anteproyecto){
        try{
            FXMLLoader accesoControlador = new FXMLLoader(jfxspger.
                    JFXSPGER.class.getResource("/jfxspger/vistas/FXMLInfoAnteproyecto.fxml"));
            Parent vista = accesoControlador.load();
            FXMLInfoAnteproyectoController formulario = accesoControlador.getController();
            Scene sceneFormulario = new Scene(vista);
            Stage escenarioPrincipal = (Stage) lbTitulo.getScene().getWindow();
            escenarioPrincipal.setTitle("Informacion de anteproyecto");
            escenarioPrincipal.setScene(sceneFormulario);
            formulario.inicializarInformacion(anteproyecto);
        }catch(IOException e){
            Utilidades.mostrarDialogoSimple("Error", 
                    "No se puede mostrar la pantalla de informacion de usuario", 
                    Alert.AlertType.ERROR);  
        }
        
    }
    
    private void configurarBusquedaTabla(){
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
                            activarFiltroRbSeleccion();
                            return true;
                        }
                        String lowerNewValue = newValue.toLowerCase();
                        if (rbDisponibles.isSelected()) {
                            return anteproyectoFiltro.getDirector().toLowerCase().contains(newValue)
                                    && anteproyectoFiltro.getEstado().contains("Disponible");
                        } else if (rbValidacionPendiente.isSelected()) {
                            return anteproyectoFiltro.getDirector().toLowerCase().contains(newValue)
                                    && anteproyectoFiltro.getEstado().contains(
                                            "Validacion pendiente");
                        } else                       
                        return anteproyectoFiltro.getDirector().toLowerCase().contains(newValue);
                    });
                    
                }
            });
        }
    }
    
    private void activarFiltroRbSeleccion() {
        if (rbDisponibles.isSelected()) {
            activarFiltroDisponible();
        } else {
            activarFiltroValidacionPendiente();
        }
    }
    
    private void activarFiltroDisponible() {
        tvAnteproyecto.setItems(filtrarAnteproyectosPorEstado("Disponible"));
    }
    
    private void activarFiltroValidacionPendiente() {
        tvAnteproyecto.setItems(filtrarAnteproyectosPorEstado("Validacion pendiente"));
    }
    
    private ObservableList<Anteproyecto> filtrarAnteproyectosPorEstado(String filtro) {
        ObservableList<Anteproyecto> anteproyectosFiltrados = FXCollections.observableArrayList();
        boolean busquedaPorDirector = !tfBusqueda.getText().isEmpty();
        boolean busquedaDisponibles = rbDisponibles.isSelected();
        
        for (Anteproyecto anteproyecto : anteproyectos) {
            if (busquedaPorDirector) {
                if (busquedaDisponibles) {
                    if (anteproyecto.getDirector().toLowerCase().contains(
                            tfBusqueda.getText().toLowerCase()) && 
                            anteproyecto.getEstado().contains("Disponible")) {
                        anteproyectosFiltrados.add(anteproyecto);
                    }
                } else {
                    if (anteproyecto.getDirector().toLowerCase().contains(
                            tfBusqueda.getText().toLowerCase()) && 
                            anteproyecto.getEstado().contains("Validacion pendiente")) {
                        anteproyectosFiltrados.add(anteproyecto);
                    }
                }
            } else {
                if (busquedaDisponibles) {
                    if (anteproyecto.getEstado().contains("Disponible")) {
                        anteproyectosFiltrados.add(anteproyecto);
                    }
                } else {
                    if (anteproyecto.getEstado().contains("Validacion pendiente")) {
                        anteproyectosFiltrados.add(anteproyecto);
                    }
                }
            }
        }
        return anteproyectosFiltrados;
    }
    
    @FXML
    private void clicRbDisponibles(ActionEvent event) {
        activarFiltroDisponible();
    }

    @FXML
    private void clicRbValidacionPendiente(ActionEvent event) {
        activarFiltroValidacionPendiente();
    }
}
