
package jfxspger.controladores;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import jfxspger.modelo.pojo.Entrega;
import jfxspger.utilidades.Utilidades;

public class FXMLAvancesEstudianteController extends FXMLPrincipalAcademicoController implements Initializable {

    @FXML
    private Label lTitulo;
    @FXML
    private TableView<Entrega> tvEntregas;
    @FXML
    private TableColumn cNombreActividad;
    @FXML
    private TableColumn cFechaEntrega;
    @FXML
    private TableColumn cFechaCreacion;
    @FXML
    private Label lbNombreEstudiante;

    @Override
    public void initialize(URL url, ResourceBundle rb) {        
    }    

    @FXML
    private void clicVentanaAnterior(ActionEvent event) {
        super.clicIrEstudiantes(event);
    }

    @FXML
    protected void clicIrAnteproyectos(ActionEvent event) {
        super.clicIrAnteproyectos(event);
    }

    @FXML
    protected void clicIrPropuestas(ActionEvent event) {
        super.clicIrPropuestas(event);
    }

    @FXML
    protected void clicIrEstudiantes(ActionEvent event) {
        super.clicIrEstudiantes(event);
    }

    @FXML
    protected void clicCerrarSesion(ActionEvent event) {
        super.clicCerrarSesion(event);
    }

    @FXML
    protected void clicIrEntregables(ActionEvent event) {
        super.clicIrEntregables(event);
    }

    @FXML
    protected void clicIrRevisiones(ActionEvent event) {
        super.clicIrRevisiones(event);
    }

    @FXML
    private void clicBtnEvaluarAvance(ActionEvent event) {
                Stage escenarioBase = (Stage) lbTitulo.getScene().getWindow();
        escenarioBase.setScene(
                Utilidades.inicializarEscena("vistas/FXMLEvaluarAvance.fxml"));
        escenarioBase.setTitle("Evaluar avance");
        escenarioBase.show();
    }
    
}
