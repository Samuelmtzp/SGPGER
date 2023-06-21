/*
* Autor: Carlos Rodriguez Herrera
* Fecha de creación: 18/06/2023
* Descripción: Clase controladora para consultar los avances por estudiante
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
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import jfxspger.modelo.dao.EntregaDAO;
import jfxspger.modelo.dao.EstudianteDAO;
import jfxspger.modelo.pojo.Entrega;
import jfxspger.modelo.pojo.EntregaRespuesta;
import jfxspger.modelo.pojo.Estudiante;
import jfxspger.modelo.pojo.EstudianteRespuesta;
import jfxspger.utilidades.Constantes;
import jfxspger.utilidades.Utilidades;

public class FXMLAvancesEstudianteController extends FXMLPrincipalAcademicoController {

    @FXML
    private Label lTitulo;
    @FXML
    private TableView<Entrega> tvEntregas;
    @FXML
    private TableColumn cNombreActividad;
    @FXML
    private TableColumn cFechaEntrega;
    @FXML
    private Label lbNombreEstudiante;
    @FXML
    private TableColumn cCalificacion;
    private ObservableList<Entrega> entregas;
    private Estudiante estudiante;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        validarSeccionesPermitidas();
    }
    public void inicializarEntregas(Estudiante estudiante){
        this.estudiante = estudiante;
        lbNombreEstudiante.setText(estudiante.getNombre() + " " + estudiante.getApellidoPaterno() + " " + estudiante.getApellidoMaterno());
        configurarTabla();
        cargarInformacionEntregas(estudiante);
    }

        private void configurarTabla(){
        cNombreActividad.setCellValueFactory(new PropertyValueFactory("titulo"));
        cFechaEntrega.setCellValueFactory(new PropertyValueFactory("fechaEntrega"));
        cCalificacion.setCellValueFactory(new PropertyValueFactory("calificacion"));
                tvEntregas.widthProperty().addListener(new ChangeListener<Number>(){
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue,
                    Number newValue) {
                TableHeaderRow header = (TableHeaderRow) tvEntregas.lookup("TableHeaderRow");
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

    private void cargarInformacionEntregas(Estudiante estudiante){
        int idEstudiante = estudiante.getIdEstudiante();
        entregas = FXCollections.observableArrayList();
        EntregaRespuesta respuestaBD = EntregaDAO.obtenerInformacionEntregasPorEstudiante(idEstudiante);
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
                entregas.addAll(respuestaBD.getEntregas());
                tvEntregas.setItems(entregas);
                break;
        }
    }
    @FXML
    private void clicVentanaAnterior(ActionEvent event) {
        Stage escenarioBase = (Stage) lTitulo.getScene().getWindow();
        escenarioBase.setScene(
                Utilidades.inicializarEscena("vistas/FXMLEstudiantesAcademico.fxml"));
        escenarioBase.setTitle("Estudiantes");
        escenarioBase.show();
    }

    @FXML
    private void clicBtnEvaluarAvance(ActionEvent event) {
        try{
            FXMLLoader accesoControlador = new FXMLLoader(jfxspger.
                    JFXSPGER.class.getResource("/jfxspger/vistas/FXMLEvaluarAvance.fxml"));
            Parent vista = accesoControlador.load();
            FXMLEvaluarAvanceController avance = accesoControlador.getController();
            
            Scene sceneFormulario = new Scene(vista);
            Stage escenarioPrincipal = (Stage) lTitulo.getScene().getWindow();
            escenarioPrincipal.setTitle("Información de entrega");
            escenarioPrincipal.setScene(sceneFormulario);            
        }catch(IOException e){
            Utilidades.mostrarDialogoSimple("Error", 
                    "No se puede mostrar la pantalla de informacion de anteproyecto", 
                    Alert.AlertType.ERROR);  
        }
    }

}
