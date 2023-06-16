/*
* Autor: Samuel Martínez Pazos
* Fecha de creación: 12/06/2023
* Descripción: Clase controladora de administración de cursos
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
import jfxspger.modelo.dao.CursoDAO;
import jfxspger.modelo.pojo.Curso;
import jfxspger.modelo.pojo.CursoRespuesta;
import jfxspger.utilidades.Constantes;
import jfxspger.utilidades.Utilidades;

public class FXMLAdminCursosController extends FXMLPrincipalAdministradorController {

    @FXML
    private Label lbTitulo;
    @FXML
    private TableView<Curso> tvCursos;
    @FXML
    private TableColumn tcExperienciaEducativa;
    @FXML
    private TableColumn tcPeriodo;
    @FXML
    private TableColumn tcProfesor;
    @FXML
    private TableColumn tcNrc;
    @FXML
    private TableColumn tcBloque;
    @FXML
    private TableColumn tcSeccion;
    @FXML
    private TableColumn tcCupo;
    ObservableList<Curso> cursos;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTabla();
        cargarDatosTabla();
    }
    
    private void configurarTabla(){
        tcExperienciaEducativa.setCellValueFactory(
                new PropertyValueFactory("experienciaEducativa"));
        tcPeriodo.setCellValueFactory(new PropertyValueFactory("periodo"));
        tcProfesor.setCellValueFactory(new PropertyValueFactory("nombreCompletoProfesor"));
        tcNrc.setCellValueFactory(new PropertyValueFactory("nrc"));
        tcBloque.setCellValueFactory(new PropertyValueFactory("bloque"));
        tcSeccion.setCellValueFactory(new PropertyValueFactory("seccion"));
        tcCupo.setCellValueFactory(new PropertyValueFactory("cupo"));
    }
    
    private void cargarDatosTabla() {
        cursos = FXCollections.observableArrayList();
        CursoRespuesta respuestaBD = CursoDAO.obtenerInformacionCurso();
        switch (respuestaBD.getCodigoRespuesta()) {
            case Constantes.ERROR_CONEXION:
                Utilidades.mostrarDialogoSimple("Sin conexión", 
                    "Lo sentimos, por el momento no hay conexión para poder "
                    + "cargar la información", 
                    Alert.AlertType.ERROR);
            break;
            case Constantes.ERROR_CONSULTA:
                Utilidades.mostrarDialogoSimple("Error al cargar los datos", 
                    "Hubo un error al cargar la información, por favor inténtelo más tarde", 
                    Alert.AlertType.WARNING);
            break;
            case Constantes.OPERACION_EXITOSA:
                cursos.addAll(respuestaBD.getCursos());
                tvCursos.setItems(cursos);
            break;
        }
    }

    @FXML
    private void clicBtnAgregarCurso(ActionEvent event) {
        Stage escenarioBase = (Stage) lbTitulo.getScene().getWindow();
        escenarioBase.setScene(
                Utilidades.inicializarEscena("vistas/FXMLFormularioCurso1.fxml"));
        escenarioBase.setTitle("Formulario de curso");
        escenarioBase.show();
    }

    @FXML
    private void clicBtnAgregarEstudiantes(ActionEvent event) {
        Curso cursoSeleccionado = tvCursos.getSelectionModel().getSelectedItem();
        if(cursoSeleccionado != null){
            irEstudiantesCurso(cursoSeleccionado);
        }else{
            Utilidades.mostrarDialogoSimple("Selección necesaria", 
                    "Selecciona el curso en la tabla para poder agregar estudiantes", 
                    Alert.AlertType.WARNING);
        }
        
    }
    
    private void irEstudiantesCurso(Curso curso){
        try{
            FXMLLoader accesoControlador = new FXMLLoader(jfxspger.
                    JFXSPGER.class.getResource("/jfxspger/vistas/FXMLEstudiantesCurso.fxml"));
            Parent vista = accesoControlador.load();
            FXMLEstudiantesCursoController formulario = accesoControlador.getController();
            Scene sceneFormulario = new Scene(vista);
            Stage escenarioPrincipal = (Stage) lbTitulo.getScene().getWindow();
            escenarioPrincipal.setTitle("Agregar estudiantes a curso");
            escenarioPrincipal.setScene(sceneFormulario);
            formulario.inicializarInformacion(curso);
        }catch(IOException e){
            Utilidades.mostrarDialogoSimple("Error", 
                    "No se puede mostrar la pantalla de agregar estudiantes", 
                    Alert.AlertType.ERROR);  
        }
        
    }
    
}
