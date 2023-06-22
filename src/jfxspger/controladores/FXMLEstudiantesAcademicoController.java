/*
* Autor: Carlos Rodriguez Herrera
* Fecha de creación: 18/06/2023
* Descripción: Clase controladora para consultar la lista de los estudiantes
*/
package jfxspger.controladores;

import com.sun.javafx.scene.control.skin.TableHeaderRow;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import jfxspger.modelo.dao.EstudianteDAO;
import jfxspger.modelo.pojo.Estudiante;
import jfxspger.modelo.pojo.EstudianteRespuesta;
import jfxspger.utilidades.Constantes;
import jfxspger.utilidades.SingletonUsuario;
import jfxspger.utilidades.Utilidades;

public class FXMLEstudiantesAcademicoController extends FXMLPrincipalAcademicoController {

    @FXML
    private TableView<Estudiante> tvEstudiantes;
    @FXML
    private TableColumn cNombreAlumno;
    @FXML
    private TableColumn cMatricula;
    @FXML
    private TableColumn cNombreAnteproyecto;
    @FXML
    private TableColumn cApellidoPat;
    @FXML
    private TableColumn cApellidoMat;
    private ObservableList<Estudiante> estudiantes;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTabla();
        cargarInformacionEstudiantes();
        validarSeccionesPermitidas();
    }
    
    private void configurarTabla(){
        cNombreAlumno.setCellValueFactory(new PropertyValueFactory("nombre"));
        cApellidoPat.setCellValueFactory(new PropertyValueFactory("apellidoPaterno"));
        cApellidoMat.setCellValueFactory(new PropertyValueFactory("apellidoMaterno"));
        cMatricula.setCellValueFactory(new PropertyValueFactory("matricula"));
        cNombreAnteproyecto.setCellValueFactory(new PropertyValueFactory("nombreTrabajo"));
                tvEstudiantes.widthProperty().addListener(new ChangeListener<Number>(){
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, 
                    Number newValue) {
                TableHeaderRow header = (TableHeaderRow) tvEstudiantes.lookup("TableHeaderRow");
                header.reorderingProperty().addListener(new ChangeListener<Boolean>(){
                    @Override
                    public void changed(ObservableValue<? extends Boolean> observable, 
                            Boolean oldValue, Boolean newValue) {
                        header.setReordering(false);
                    }
                });
            }
        });
    }
    
    private void cargarInformacionEstudiantes(){
        estudiantes = FXCollections.observableArrayList();
        EstudianteRespuesta respuestaBD = EstudianteDAO.obtenerInformacionEstudiantesDeProfesor(
                SingletonUsuario.getInstancia().getUsuario().getIdAcademico());
        switch(respuestaBD.getCodigoRespuesta()){
            case Constantes.ERROR_CONEXION:
                Utilidades.mostrarDialogoSimple("Sin conexión", 
                        "Lo sentimos, por el momento no hay conexión", 
                        Alert.AlertType.ERROR);
                break;
            case Constantes.ERROR_CONSULTA:
                Utilidades.mostrarDialogoSimple("Error al cargar los datos", 
                        "Hubo un error al cargar la información. Por favor intente más tarde", 
                        Alert.AlertType.WARNING);
                break;
            case Constantes.OPERACION_EXITOSA:
                estudiantes.addAll(respuestaBD.getEstudiantes());
                tvEstudiantes.setItems(estudiantes);
                break;
        }
    }
    
         private void irAvances(Estudiante estudianteAvances){
         
        try {
            FXMLLoader accesoControlador = new FXMLLoader(jfxspger.JFXSPGER.
                    class.getResource("vistas/FXMLAvancesEstudiante.fxml"));        
            Parent vista = accesoControlador.load();
            
            FXMLAvancesEstudianteController avances = accesoControlador.getController();
            avances.inicializarEntregas(estudianteAvances);
            
            Scene sceneAvances = new Scene(vista);
            Stage escenarioAvances = (Stage) lbTitulo.getScene().getWindow();
            escenarioAvances.setScene(sceneAvances);
            escenarioAvances.setTitle("Avances de estudiante");            
            escenarioAvances.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void clicBtnVerAvances(ActionEvent event) {
        Estudiante estudianteAvances = tvEstudiantes.getSelectionModel().getSelectedItem();
        if(estudianteAvances != null){
            irAvances(estudianteAvances);
        }else{
            Utilidades.mostrarDialogoSimple("Selecciona un estudiante", 
                    "Debes selecionar un estudiante para poder"
                    + " ver los avances.", Alert.AlertType.WARNING);
        }        
    }

}
