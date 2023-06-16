/*
* Autor: Samuel Martínez Pazos
* Fecha de creación: 12/06/2023
* Descripción: Clase controladora para administrar los estudiantes en un curso
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
import jfxspger.modelo.dao.CursoDAO;
import jfxspger.modelo.dao.UsuarioDAO;
import jfxspger.modelo.dao.Estudiante_CursoDAO;
import jfxspger.modelo.pojo.Curso;
import jfxspger.modelo.pojo.Estudiante_Curso;
import jfxspger.modelo.pojo.Usuario;
import jfxspger.modelo.pojo.UsuarioRespuesta;
import jfxspger.utilidades.Constantes;
import jfxspger.utilidades.Utilidades;

public class FXMLEstudiantesCursoController extends FXMLPrincipalAdministradorController {

    @FXML
    private Label lbTitulo;
    @FXML
    private TableView<Usuario> tvEstudiantesAsignados;
    @FXML
    private TableColumn tcNombreAsignados;
    @FXML
    private TableColumn tcApellidoPaternoAsignados;
    @FXML
    private TableColumn tcApellidoMaternoAsignados;
    @FXML
    private TableColumn tcCorreoAsignados;
    @FXML
    private TableColumn tcMatriculaAsignados;
    @FXML
    private TableView<Usuario> tvEstudiantesDisponibles;
    @FXML
    private TableColumn tcNombreDisponibles;
    @FXML
    private TableColumn tcApellidoPaternoDisponibles;
    @FXML
    private TableColumn tcApellidoMaternoDisponibles;
    @FXML
    private TableColumn tcCorreoDisponibles;
    @FXML
    private TableColumn tcMatriculaDisponibles;
    private Curso curso;
    private ObservableList<Usuario> estudiantesAsignados;
    private ObservableList<Usuario> estudiantesDisponibles;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    
    private void configurarTablas(){
        tcApellidoMaternoAsignados.setCellValueFactory(new PropertyValueFactory(
                "apellidoMaterno"));
        tcApellidoPaternoAsignados.setCellValueFactory(new PropertyValueFactory(
                "apellidoPaterno"));
        tcCorreoAsignados.setCellValueFactory(new PropertyValueFactory("correo"));
        tcNombreAsignados.setCellValueFactory(new PropertyValueFactory("nombre"));
        tcMatriculaAsignados.setCellValueFactory(new PropertyValueFactory("matricula"));
        tcApellidoMaternoDisponibles.setCellValueFactory(
                new PropertyValueFactory("apellidoMaterno"));
        tcApellidoPaternoDisponibles.setCellValueFactory(new PropertyValueFactory(
                "apellidoPaterno"));
        tcCorreoDisponibles.setCellValueFactory(new PropertyValueFactory("correo"));
        tcNombreDisponibles.setCellValueFactory(new PropertyValueFactory("nombre"));
        tcMatriculaDisponibles.setCellValueFactory(new PropertyValueFactory("matricula"));
    }
    
    private void cargarDatosTablaEstudiantesAsignados() {
        estudiantesAsignados = FXCollections.observableArrayList();
        UsuarioRespuesta respuestaBD = UsuarioDAO.obtenerInformacionEstudiantesEnCurso(
                curso.getIdCurso());
        switch (respuestaBD.getCodigoRespuesta()) {
            case Constantes.ERROR_CONEXION:
                Utilidades.mostrarDialogoSimple("Sin conexión", 
                    "Lo sentimos, por el momento no hay conexión para poder "
                    + "cargar la información de los estudiantes asignados al curso", 
                    Alert.AlertType.ERROR);
            break;
            case Constantes.ERROR_CONSULTA:
                Utilidades.mostrarDialogoSimple("Error al cargar los datos", 
                    "Hubo un error al cargar información de los estudiantes "
                    + "asignados al curso, por favor inténtelo más tarde", 
                    Alert.AlertType.WARNING);
            break;
            case Constantes.OPERACION_EXITOSA:
                estudiantesAsignados.addAll(respuestaBD.getUsuarios());
                tvEstudiantesAsignados.setItems(estudiantesAsignados);
            break;
        }
    }
    
    private void cargarDatosTablaEstudiantesDisponibles() {
        estudiantesDisponibles = FXCollections.observableArrayList();
        UsuarioRespuesta respuestaBD = UsuarioDAO.obtenerInformacionEstudiantesDisponibles();
        switch (respuestaBD.getCodigoRespuesta()) {
            case Constantes.ERROR_CONEXION:
                Utilidades.mostrarDialogoSimple("Sin conexión", 
                    "Lo sentimos, por el momento no hay conexión para poder "
                    + "cargar la información de los estudiantes disponibles", 
                    Alert.AlertType.ERROR);
            break;
            case Constantes.ERROR_CONSULTA:
                Utilidades.mostrarDialogoSimple("Error al cargar los datos", 
                    "Hubo un error al cargar información de los estudiantes "
                    + "disponibles, por favor inténtelo más tarde", 
                    Alert.AlertType.WARNING);
            break;
            case Constantes.OPERACION_EXITOSA:
                estudiantesDisponibles.addAll(respuestaBD.getUsuarios());
                tvEstudiantesDisponibles.setItems(estudiantesDisponibles);
            break;
        }
    }

    public void inicializarInformacion(Curso curso) {
        this.curso = curso;
        configurarTablas();
        cargarDatosTablaEstudiantesAsignados();
        cargarDatosTablaEstudiantesDisponibles();
    }

    @FXML
    private void clicIrAdminCursos(ActionEvent event) {
        Stage escenarioBase = (Stage) lbTitulo.getScene().getWindow();
        escenarioBase.setScene(
                Utilidades.inicializarEscena("vistas/FXMLAdminCursos.fxml"));
        escenarioBase.setTitle("Administración cursos");
        escenarioBase.show();
    }
    
    @FXML
    private void clicBtnAgregarEstudiante(ActionEvent event) {
        if (curso.getCupo() > 0) {
            int posicionEstudianteDisponible = tvEstudiantesDisponibles.
                    getSelectionModel().getSelectedIndex();
            if (posicionEstudianteDisponible != -1) {
                Estudiante_Curso estudianteEnCursoRegistro = new Estudiante_Curso();
                estudianteEnCursoRegistro.setIdCurso(curso.getIdCurso());
                estudianteEnCursoRegistro.setIdEstudiante(tvEstudiantesDisponibles.
                    getSelectionModel().getSelectedItem().getIdEstudiante());
                curso.setCupo(curso.getCupo() - 1);
                agregarEstudianteACurso(estudianteEnCursoRegistro);
            } else {
                Utilidades.mostrarDialogoSimple("Selección necesaria", 
                        "Para agregar un estudiante al curso, debe seleccionarlo "
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
    
    private void actualizarCurso(Estudiante_Curso estudianteEnCurso) {
        int respuesta = CursoDAO.modificarCurso(curso);
        switch (respuesta) {
            case Constantes.ERROR_CONEXION:
                Utilidades.mostrarDialogoSimple("Sin conexión", 
                    "Lo sentimos, por el momento no hay conexión para poder "
                    + "actualizar el cupo del curso", 
                    Alert.AlertType.ERROR);
            break;
            case Constantes.ERROR_CONSULTA:
                Utilidades.mostrarDialogoSimple("Error al modificar los datos", 
                    "Hubo un error al tratar de actualizar la información del cupo, "
                    + "por favor inténtelo más tarde", 
                    Alert.AlertType.WARNING);
                Estudiante_CursoDAO.eliminarEstudiante_Curso(
                        estudianteEnCurso.getIdEstudiante(), curso.getIdCurso());
            break;
            case Constantes.OPERACION_EXITOSA:
                Utilidades.mostrarDialogoSimple("Estudiante agregado", 
                    "El estudiante fue agregado al curso correctamente", 
                    Alert.AlertType.INFORMATION);
            break;
        }
    }
    
    private void agregarEstudianteACurso(Estudiante_Curso estudianteEnCurso) {
        int respuesta = Estudiante_CursoDAO.guardarEstudiante_Curso(estudianteEnCurso);
        
        switch(respuesta) {
            case Constantes.ERROR_CONEXION:
                Utilidades.mostrarDialogoSimple("Error de conexion", "El estudiante " + 
                        "no pudo agregarse al curso debido a un error de conexión.", 
                        Alert.AlertType.ERROR);
            break;
            case Constantes.ERROR_CONSULTA:
                Utilidades.mostrarDialogoSimple("Error al agregar al estudiante al curso",
                        "No se puede agregar el estudiante seleccionado al curso,"
                        + "por favor inténtelo más tarde", 
                        Alert.AlertType.WARNING);
            break;
            case Constantes.OPERACION_EXITOSA:
                cargarDatosTablaEstudiantesAsignados();
                cargarDatosTablaEstudiantesDisponibles();
                actualizarCurso(estudianteEnCurso);
            break;
        }
    }
    
}
