/*
* Autor: Luis Angel ElizaLde Arroyo
* Fecha de creación: 16/06/2023
* Descripción: Clase encargada de asignar los estudiantes a los anteproyectos
*/
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
import jfxspger.modelo.dao.EstudianteDAO;
import jfxspger.modelo.pojo.Usuario;
import jfxspger.modelo.pojo.UsuarioRespuesta;
import jfxspger.utilidades.Constantes;
import jfxspger.utilidades.Utilidades;
import jfxspger.modelo.dao.UsuarioDAO;
import jfxspger.modelo.pojo.Anteproyecto;
import jfxspger.modelo.pojo.Estudiante;

public class FXMLAsignarEstudiantesController extends FXMLPrincipalAcademicoController {

    @FXML
    private TableView<Usuario> tvAlumnosDisponibles;
    @FXML
    private TableView<Usuario> tvAlumnosAsignados;
    
    private ObservableList <Usuario> estudiantesDisponibles;
    private ObservableList <Usuario> estudiantesAsignados;
    
    private Anteproyecto anteproyecto;
    @FXML
    private TableColumn columMatricula;
    @FXML
    private TableColumn columApellidoPaterno;
    @FXML
    private TableColumn columApellidoMaterno;
    @FXML
    private TableColumn columnNombreEstudiante;
    @FXML
    private TableColumn columnNombreDisponible;
    @FXML
    private TableColumn columApellidoPaternoDisponible;
    @FXML
    private TableColumn columApellidoMaternoDisponible;
    @FXML
    private TableColumn columMatriculaDisponible;
    @FXML
    private Label lbTitulo;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }
    
    private void configurarTablas(){
        columnNombreEstudiante.setCellValueFactory(new PropertyValueFactory("nombre"));
        columMatricula.setCellValueFactory(new PropertyValueFactory("matricula"));
        columApellidoPaterno.setCellValueFactory(
                new PropertyValueFactory("apellidoPaterno"));
        columApellidoMaterno.setCellValueFactory(
                new PropertyValueFactory("apellidoMaterno"));
        columnNombreDisponible.setCellValueFactory(new PropertyValueFactory("nombre"));
        columMatriculaDisponible.setCellValueFactory(new PropertyValueFactory("matricula"));
        columApellidoPaternoDisponible.setCellValueFactory(new PropertyValueFactory(
                "apellidoPaterno"));
        columApellidoMaternoDisponible.setCellValueFactory(new PropertyValueFactory(
                "apellidoMaterno"));
    } 
     
    private void cargarDatosTablaEstudiantesAsignados(Anteproyecto anteproyectoAsigandos) {
        estudiantesAsignados = FXCollections.observableArrayList();
        UsuarioRespuesta respuestaBD = UsuarioDAO.consultarEstudiantesEnAnteproyecto(
                anteproyectoAsigandos.getIdAnteproyecto());
        if(respuestaBD != null){
        switch (respuestaBD.getCodigoRespuesta()) {
            case Constantes.ERROR_CONEXION:
                Utilidades.mostrarDialogoSimple("Sin conexión", 
                    "Lo sentimos, por el momento no hay conexión para poder "
                    + "cargar la información de los estudiantes asignados al anteproyecto", 
                    Alert.AlertType.ERROR);
            break;
            case Constantes.ERROR_CONSULTA:
                Utilidades.mostrarDialogoSimple("Error al cargar los datos", 
                    "Hubo un error al cargar información de los estudiantes "
                    + "asignados al anteproyecto, por favor inténtelo más tarde", 
                    Alert.AlertType.WARNING);
            break;
            case Constantes.OPERACION_EXITOSA:
                estudiantesAsignados.addAll(respuestaBD.getUsuarios());
                tvAlumnosAsignados.setItems(estudiantesAsignados);
            break;
          }
        }
    }

    private void cargarDatosTablaEstudiantesDisponibles(){
        estudiantesDisponibles = FXCollections.observableArrayList();
        UsuarioRespuesta respuestaBD = UsuarioDAO.consultarEstudiantesDisponibles();
        if(respuestaBD != null){
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
                    estudiantesDisponibles.addAll(respuestaBD.getUsuarios());
                    tvAlumnosDisponibles.setItems(estudiantesDisponibles);
                break;
            }
        }
    }
    
    public void inicializarInformacion(Anteproyecto anteproyectoSelected){
        this.anteproyecto=anteproyectoSelected;
        configurarTablas();
        cargarDatosTablaEstudiantesAsignados(anteproyectoSelected);
        cargarDatosTablaEstudiantesDisponibles();
    }

    @FXML
    private void clicBtnAsignarEstudiantes(ActionEvent event) {
         if (anteproyecto.getCantidadAlumnosParticipantes() > 0) {
            int posicionEstudianteDisponible = tvAlumnosDisponibles.
                    getSelectionModel().getSelectedIndex();
            if (posicionEstudianteDisponible != -1) {
                Usuario usarioprueba =  tvAlumnosDisponibles.getSelectionModel().getSelectedItem();
                Estudiante estudianteEnAnteproyectoRegistro = new Estudiante();
                estudianteEnAnteproyectoRegistro.setIdAnteproyecto(anteproyecto.
                        getIdAnteproyecto());
                estudianteEnAnteproyectoRegistro.setIdEstudiante(tvAlumnosDisponibles.
                    getSelectionModel().getSelectedItem().getIdEstudiante());
                estudianteEnAnteproyectoRegistro.setIdUsuario(estudiantesDisponibles.get(
                        posicionEstudianteDisponible).getIdUsuario());
                estudianteEnAnteproyectoRegistro.setMatricula(estudiantesDisponibles.get(
                        posicionEstudianteDisponible).getMatricula());
                anteproyecto.setCantidadAlumnosParticipantes(
                        anteproyecto.getCantidadAlumnosParticipantes()-1);
                agregarEstudianteAAnteproyecto(estudianteEnAnteproyectoRegistro);
            } else {
                Utilidades.mostrarDialogoSimple("Selección necesaria", 
                        "Para agregar un estudiante al anteproyecto, debe seleccionarlo "
                        + "previamente de la tabla de estudiantes disponibles", 
                        Alert.AlertType.WARNING);
            }
        } else {
            Utilidades.mostrarDialogoSimple("Cupo lleno", 
                    "No es posible agregar más estudiantes debido a que se ha alcanzado el "
                    + "cupo límite.", 
                    Alert.AlertType.WARNING);
        }
    }
    
    private void agregarEstudianteAAnteproyecto(Estudiante estudianteEnAnteproyecto) {
        int respuesta = EstudianteDAO.agregarEstudianteAnteproyecto(estudianteEnAnteproyecto);
        switch(respuesta) {
            case Constantes.ERROR_CONEXION:
                Utilidades.mostrarDialogoSimple("Error de conexion", "El estudiante " + 
                        "no pudo agregarse al anteproyecto debido a un error de conexión.", 
                        Alert.AlertType.ERROR);
            break;
            case Constantes.ERROR_CONSULTA:
            Utilidades.mostrarDialogoSimple("Error al agregar al estudiante al anteproyecto",
                        "No se puede agregar el estudiante seleccionado al anteproyecto,"
                        + "por favor inténtelo más tarde", 
                        Alert.AlertType.WARNING);
            break;
            case Constantes.OPERACION_EXITOSA:
                 Utilidades.mostrarDialogoSimple("Estudiante asignado al anteproyecto",
                        "Se ha agregado el estudiante seleccionado al anteproyecto", 
                        Alert.AlertType.INFORMATION);
                cargarDatosTablaEstudiantesAsignados(anteproyecto);
                cargarDatosTablaEstudiantesDisponibles();
            break;
        }
    }
    

    @FXML
    private void clicBtnQuitar(ActionEvent event) {
        int posicionEstudianteDisponible = tvAlumnosAsignados.
                    getSelectionModel().getSelectedIndex();
        if(posicionEstudianteDisponible!=-1){
            Usuario usuarioSeleccionado = tvAlumnosAsignados.getSelectionModel().getSelectedItem();
            Estudiante student = new Estudiante();
            student.setIdEstudiante(usuarioSeleccionado.getIdEstudiante());
            if(usuarioSeleccionado != null){
                boolean borrarRegistro = Utilidades.mostrarDialogoConfirmacion(
                        "Eliminar alumno del anteproyecto", 
                        "¿Estás seguro de que deseas eliminar al alumno del anteproyecto?");
                if(borrarRegistro){
                    eliminarEstudianteDeAnteproyecto(student);
                    anteproyecto.setCantidadAlumnosParticipantes(anteproyecto.
                            getCantidadAlumnosParticipantes() + 1);
                }
            }else{
                 Utilidades.mostrarDialogoSimple("Selecciona un Alumno", 
                        "Selecciona a un alumno en la tabla de alumnos asigandos", 
                        Alert.AlertType.WARNING);
            }
        }
    }
    
    private void eliminarEstudianteDeAnteproyecto(Estudiante estudianteEnAnteproyecto) {
        int respuesta = EstudianteDAO.eliminarEstudianteAnteproyecto
        (estudianteEnAnteproyecto);
        switch(respuesta) { 
            case Constantes.ERROR_CONEXION:
                Utilidades.mostrarDialogoSimple("Error de conexion", "El estudiante " + 
                        "no pudo eliminarse del anteproyecto debido a un error de conexión.", 
                        Alert.AlertType.ERROR);
            break;
            case Constantes.ERROR_CONSULTA:
           Utilidades.mostrarDialogoSimple("Error al eliminar al estudiante  al anteproyecto",
                        "No se puede eliminar el estudiante del anteproyecto,"
                        + "por favor inténtelo más tarde", 
                        Alert.AlertType.WARNING);
            break;
            case Constantes.OPERACION_EXITOSA:
                 Utilidades.mostrarDialogoSimple("Estudiante eliminado del anteproyecto",
                        "Se ha eliminado el estudiante seleccionado del anteproyecto", 
                        Alert.AlertType.INFORMATION);
                cargarDatosTablaEstudiantesAsignados(anteproyecto);
                cargarDatosTablaEstudiantesDisponibles();
            break;
        }
    }

    @FXML
    private void clicRegresar(ActionEvent event) {
         boolean Salir =Utilidades.mostrarDialogoConfirmacion("Confirmacion", 
                "¿Seguro que deseas salir? No se guardaran los cambios");
        if(Salir){
          Stage escenarioBase = (Stage) lbTitulo.getScene().getWindow();
          escenarioBase.setScene(
                Utilidades.inicializarEscena("vistas/FXMLAdminAnteproyectos2.fxml"));
          escenarioBase.setTitle("Administración LGAC");
          escenarioBase.show();
        }
    }
   
}
