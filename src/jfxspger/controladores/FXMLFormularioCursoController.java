package jfxspger.controladores;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import jfxspger.utilidades.Utilidades;

public class FXMLFormularioCursoController extends FXMLAdminCursosController {

    @FXML
    private Label lbTitulo;
    @FXML
    private ComboBox<?> cbExperienciaEducativa;
    @FXML
    private ComboBox<?> cbProfesor;
    @FXML
    private ComboBox<?> cbPeriodoEscolar;
    @FXML
    private TextField tfNrc;
    @FXML
    private TextField tfBloque;
    @FXML
    private TextField tfSeccion;
    @FXML
    private TextField tfCupo;
    @FXML
    private TableView<?> tvEstudiantes;
    @FXML
    private TableColumn<?, ?> tcNombre;
    @FXML
    private TableColumn<?, ?> tcApellidoPaterno;
    @FXML
    private TableColumn<?, ?> tcApellidoMaterno;
    @FXML
    private TableColumn<?, ?> tcCorreo;
    @FXML
    private TableColumn<?, ?> tcMatricula;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    

    @FXML
    private void clicIrAdminCursos(ActionEvent event) {
        if (Utilidades.mostrarDialogoConfirmacion(
                "Confirmación de regreso", 
                "¿Está seguro de que desea regresar a la ventana de administración de cursos? " + 
                "La información ingresada en el formulario se descartará")) {
            irAdminCursos();
        }
    }
    
    private void irAdminCursos() {
        Stage escenarioBase = (Stage) lbTitulo.getScene().getWindow();
        escenarioBase.setScene(
                Utilidades.inicializarEscena("vistas/FXMLAdminCursos.fxml"));
        escenarioBase.setTitle("Administración cursos");
        escenarioBase.show();
    }


    @FXML
    private void clicBtnCancelar(ActionEvent event) {
        if (mostrarDialogoConfirmacionCancelacion()) {
            irAdminCursos();
        }
    }
    
    private boolean mostrarDialogoConfirmacionCancelacion() {
        return Utilidades.mostrarDialogoConfirmacion(
                "Confirmación de cancelación", 
                "¿Está seguro de que desea cancelar el registro del curso? " + 
                "La información ingresada en el formulario se descartará");
    }
    
    @FXML
    private void clicBtnRegistrarCurso(ActionEvent event) {
    }

    @FXML
    private void clicBtnAgregarEstudiante(ActionEvent event) {
    }

    @FXML
    protected void clicIrCursos(ActionEvent event) {
        if (mostrarDialogoConfirmacionCancelacion()) {
            super.clicIrCursos(event);
        }
    }

    @FXML
    protected void clicIrLgac(ActionEvent event) {
        if (mostrarDialogoConfirmacionCancelacion()) {
            super.clicIrLgac(event);
        }
    }

    @FXML
    protected void clicIrUsuarios(ActionEvent event) {
        if (mostrarDialogoConfirmacionCancelacion()) {
            super.clicIrUsuarios(event);
        }
    }

    @FXML
    protected void clicIrCuerposAcademicos(ActionEvent event) {
        if (mostrarDialogoConfirmacionCancelacion()) {
            super.clicIrCuerposAcademicos(event);
        }
    }
    
}
