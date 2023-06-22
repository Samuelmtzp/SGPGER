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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
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
import jfxspger.utilidades.SingletonUsuario;
import jfxspger.utilidades.Utilidades;

public class FXMLAdminAnteproyectosController extends FXMLPrincipalAcademicoController {
    
    @FXML
    private TableView<Anteproyecto> tvAnteproyecto;
    @FXML
    private TableColumn columnNombre;
    @FXML
    private TableColumn columNombreProyecto;
    @FXML
    private TableColumn columDirector;
    @FXML
    private TableColumn columEstado;
    @FXML
    private TextField tfBusqueda;
    @FXML
    private ToggleGroup tgBusqueda;
    @FXML
    private RadioButton rbDisponibles;
    @FXML
    private RadioButton rbValidacionPendiente;
    @FXML
    private TableColumn columDuracionAprox;
    @FXML
    private TableColumn columFechaCreacion;
    @FXML
    private RadioButton rbNoDisponibles;
    private ObservableList<Anteproyecto> anteproyectos;
    private final String ESTADO_VALIDACION_PENDIENTE = "Validación pendiente";
    private final String ESTADO_DISPONIBLE = "Disponible";
    private final String ESTADO_NO_DISPONIBLE = "No disponible";
    @FXML
    private Button btnCrearAnteproyecto;
    @FXML
    private Button btnEliminarAnteproyecto;
    @FXML
    private Button btnModificarAnteproyecto;
    @FXML
    private Button btnAgregarEstudiantes;
    @FXML
    private Label lbTitulo;
    @FXML
    private Button btnCodirectores;
    @FXML
    private Button btnAnteproyectos;
    @FXML
    private Button btnPropuestas;
    @FXML
    private Button btnEstudiantes;
    @FXML
    private Button btnRevisiones;
   

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarInformacion();
        configurarTabla();
        configurarBusquedaTabla();
        validarSeccionesPermitidas();
        validarAccionesPermitidas();
        agregarListenerATabla();
    }
    
    private void agregarListenerATabla() {
        tvAnteproyecto.getSelectionModel().selectedItemProperty().addListener(
                (ObservableValue<? extends Anteproyecto> observable, 
                Anteproyecto oldValue, Anteproyecto newValue) ->
        {
            if (newValue != null) {
                if (SingletonUsuario.getInstancia().getUsuario().getIdAcademico() ==
                        newValue.getIdDirector() && newValue.getEstado().equals(
                                ESTADO_VALIDACION_PENDIENTE)) {
                    btnModificarAnteproyecto.setDisable(false);
                    btnEliminarAnteproyecto.setDisable(false);
                    btnCodirectores.setDisable(false);
                } else if ((SingletonUsuario.getInstancia().getUsuario().getIdAcademico() ==
                        newValue.getIdDirector() || esCodirectorDeAnteproyecto(
                                newValue.getIdAnteproyecto())) && newValue.getEstado().equals(
                                        ESTADO_DISPONIBLE)){
                    btnAgregarEstudiantes.setDisable(false);
                } else {
                    deshabilitarBotonesEdicionAnteproyecto();
                }
            }
        });
    }
    
    private void validarAccionesPermitidas() {
        if (esMiembroDeCA()) {
            btnCrearAnteproyecto.setDisable(false);
        }
    }
    
    private void deshabilitarBotonesEdicionAnteproyecto() {
        btnAgregarEstudiantes.setDisable(true);
        btnModificarAnteproyecto.setDisable(true);
        btnEliminarAnteproyecto.setDisable(true);
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
        AnteproyectoRespuesta respuestaBD;
        if (esMiembroDeCA()) {
            respuestaBD = AnteproyectoDAO.obtenerInformacionAnteproyectosEnCuerpoAcademico(
                    SingletonUsuario.getInstancia().getUsuario().getIdcuerpoAcademico());
        } else {
            respuestaBD = AnteproyectoDAO.obtenerInformacionAnteproyectos();
        }
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
            FXMLLoader accesoControlador = new FXMLLoader(jfxspger.JFXSPGER.class.getResource(
                    "vistas/FXMLFormularioAnteproyecto.fxml"));
            Parent vista = accesoControlador.load();
            FXMLFormularioAnteproyectoController formulario = accesoControlador.getController();
            Scene sceneFormulario = new Scene(vista);
            Stage escenarioPrincipal = (Stage)lbTitulo.getScene().getWindow();
            escenarioPrincipal.setScene(sceneFormulario);
            formulario.inicializarInformacionFormulario(esEdicion, anteproyectoEdicion);
            
         }catch(IOException ex){
             Logger.getLogger(FXMLFormularioLgacController.class.getName()).
                     log(Level.SEVERE, null, ex);
         }
    }

    @FXML
    private void clicCrearAnteproyecto(ActionEvent event) {
        irFormulario(false, null);
    }

    @FXML
    private void clicEliminarAnteproyecto(ActionEvent event) {
        Anteproyecto anteproyectoSeleccionado = tvAnteproyecto.getSelectionModel().
                getSelectedItem();
        int posicion = tvAnteproyecto.
                getSelectionModel().getSelectedIndex();
        if(posicion != -1){
            boolean borrarRegistro = Utilidades.mostrarDialogoConfirmacion(
                    "Eliminar registro del anteproyecto", 
                    "¿Estás seguro de que deseas eliminar el anteproyecto?");
            if (borrarRegistro) {
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
                         tvAnteproyecto.setItems(filtrarAnteproyectos());
                         tfBusqueda.setText("");
                         cargarInformacion();
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

    
    private void irAsignarEstudiantes(Anteproyecto anteproyecto){
           try{
            if(anteproyecto.getIdEstado() != 1){
            FXMLLoader accesoControlador = new 
      FXMLLoader(jfxspger.JFXSPGER.class.getResource(
              "/jfxspger/vistas/FXMLAsignarEstudiantesAnteproyecto.fxml"));
            Parent vista = accesoControlador.load();
            FXMLAsignarEstudiantesAnteproyectoController formulario = accesoControlador.getController();
            Scene sceneFormulario = new Scene(vista);
            Stage escenarioPrincipal = (Stage)lbTitulo.getScene().getWindow();
            escenarioPrincipal.setScene(sceneFormulario);
            formulario.inicializarInformacion(anteproyecto);
            }else{
                 Utilidades.mostrarDialogoSimple("Anteproyecto no validado", 
                    "El anteproyecto debe estar validado"
                            + "para poder asignarle estudiantes", 
                    Alert.AlertType.WARNING);
            }
         }catch(IOException ex){
             Logger.getLogger(FXMLFormularioLgacController.class.getName()).
                     log(Level.SEVERE, null, ex);
         }
    }

    @FXML
    private void clicConsultarAnteproyecto(MouseEvent event) {
        if (event.isPrimaryButtonDown() && event.getClickCount() == 2 && 
                tvAnteproyecto.getSelectionModel().getSelectedIndex() != -1) {
            Anteproyecto anteproyectoSeleccionado = 
                    tvAnteproyecto.getSelectionModel().getSelectedItem();
            irInformacionAnteproyecto(false,anteproyectoSeleccionado);
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
            formulario.inicializarInformacion(esValidacion,anteproyecto);
        }catch(IOException e){
            Utilidades.mostrarDialogoSimple("Error", 
                    "No se puede mostrar la pantalla de informacion de anteproyecto", 
                    Alert.AlertType.ERROR);  
        }
        
    }
    
    private void configurarBusquedaTabla() {
        if (!anteproyectos.isEmpty()) {
            FilteredList<Anteproyecto> filtradoAnteproyecto = 
                    new FilteredList<>(anteproyectos, p-> true);
            tfBusqueda.textProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue, 
                        String newValue) {
                    
                    SortedList<Anteproyecto> sortedListaAnteproyectos = 
                            new SortedList<>(filtradoAnteproyecto);
                    sortedListaAnteproyectos.comparatorProperty().bind(
                            tvAnteproyecto.comparatorProperty());
                    tvAnteproyecto.setItems(sortedListaAnteproyectos);
                    deshabilitarBotonesEdicionAnteproyecto();
                    filtradoAnteproyecto.setPredicate(anteproyectoFiltro -> {
                        if (newValue == null || newValue.isEmpty()) {
                            tvAnteproyecto.setItems(filtrarAnteproyectos());
                            return true;
                        }
                        
                        String lowerNewValue = newValue.toLowerCase();
                        
                        if (rbDisponibles.isSelected()) {
                            return anteproyectoFiltro.getDirector().toLowerCase().
                                    contains(lowerNewValue) 
                                    && anteproyectoFiltro.getEstado().contains(ESTADO_DISPONIBLE);
                        } else if (rbValidacionPendiente.isSelected()) {
                            return anteproyectoFiltro.getDirector().toLowerCase().
                                    contains(lowerNewValue)
                                    && anteproyectoFiltro.getEstado().
                                    contains(ESTADO_VALIDACION_PENDIENTE);
                        } else if (rbNoDisponibles.isSelected()) {
                            return anteproyectoFiltro.getDirector().toLowerCase().
                                    contains(lowerNewValue)
                                    && anteproyectoFiltro.getEstado().
                                    contains(ESTADO_NO_DISPONIBLE);
                        } else                       
                            return anteproyectoFiltro.getDirector().toLowerCase().
                                    contains(lowerNewValue);
                    });
                }
            });
        }
    }
    
    private ObservableList<Anteproyecto> filtrarAnteproyectos() {
        ObservableList<Anteproyecto> anteproyectosFiltrados = FXCollections.observableArrayList();
        boolean busquedaPorDirector = !tfBusqueda.getText().isEmpty();
        boolean busquedaDisponibles = rbDisponibles.isSelected();
        boolean busquedaNoDisponibles = rbNoDisponibles.isSelected();
        boolean busquedaValidacionPendiente = rbValidacionPendiente.isSelected();
        for (Anteproyecto anteproyecto : anteproyectos) {
            if (busquedaPorDirector) {
                if (busquedaDisponibles) {
                    if (anteproyecto.getDirector().toLowerCase().contains(
                            tfBusqueda.getText().toLowerCase()) && 
                            anteproyecto.getEstado().contains(ESTADO_DISPONIBLE)) {
                        anteproyectosFiltrados.add(anteproyecto);
                    }
                } else if (busquedaValidacionPendiente){
                    if (anteproyecto.getDirector().toLowerCase().contains(
                            tfBusqueda.getText().toLowerCase()) && 
                            anteproyecto.getEstado().contains(ESTADO_VALIDACION_PENDIENTE)) {
                        anteproyectosFiltrados.add(anteproyecto);
                    }
                } else if (busquedaNoDisponibles){
                    if (anteproyecto.getDirector().toLowerCase().contains(
                            tfBusqueda.getText().toLowerCase()) && 
                            anteproyecto.getEstado().contains(ESTADO_NO_DISPONIBLE)) {
                        anteproyectosFiltrados.add(anteproyecto);
                    }
                }
            } else {
                if (busquedaDisponibles) {
                    if (anteproyecto.getEstado().contains(ESTADO_DISPONIBLE)) {
                        anteproyectosFiltrados.add(anteproyecto);
                    }
                } else if (busquedaValidacionPendiente){
                    if (anteproyecto.getEstado().contains(ESTADO_VALIDACION_PENDIENTE)) {
                        anteproyectosFiltrados.add(anteproyecto);
                    }
                } else if (busquedaNoDisponibles){
                    if (anteproyecto.getEstado().contains(ESTADO_NO_DISPONIBLE)) {
                        anteproyectosFiltrados.add(anteproyecto);
                    }
                }
            }
        }
        return anteproyectosFiltrados;
    }
    
    @FXML
    private void clicFiltro(ActionEvent event) {
        tvAnteproyecto.setItems(filtrarAnteproyectos());
        deshabilitarBotonesEdicionAnteproyecto();
    }

    @FXML
    private void clicAgregarEstudiante(ActionEvent event) {
    Anteproyecto anteproyectoSeleccionado = tvAnteproyecto.getSelectionModel().getSelectedItem();
            int pos = tvAnteproyecto.getSelectionModel().getSelectedIndex();
            if(pos != -1){
                irAsignarEstudiantes(anteproyectoSeleccionado);
            }else{
                Utilidades.mostrarDialogoSimple("Selecciona un anteproyecto", 
              "Selecciona el registro en la tabla del anteproyecto para asignar estudiantes", 
                 Alert.AlertType.WARNING);
            }
    }    
    
    @FXML
    private void clicBtnCodirectores(ActionEvent event) {
        Anteproyecto anteproyectoSeleccionado = tvAnteproyecto.getSelectionModel().getSelectedItem();
            int pos = tvAnteproyecto.getSelectionModel().getSelectedIndex();
            if(pos != -1){
                //irAsignarCodirectores(anteproyectoSeleccionado);
            }else{
                Utilidades.mostrarDialogoSimple("Selecciona un anteproyecto", 
              "Selecciona el registro en la tabla del anteproyecto para asignar estudiantes", 
                 Alert.AlertType.WARNING);
            }
    }
}
