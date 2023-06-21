
package jfxspger.controladores;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import jfxspger.modelo.pojo.Entrega;
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
    private TableColumn cFechaCreacion;
    @FXML
    private Label lbNombreEstudiante;

    @Override
    public void initialize(URL url, ResourceBundle rb) {        
        validarSeccionesPermitidas();
    }    

    @FXML
    private void clicVentanaAnterior(ActionEvent event) {
        super.clicIrEstudiantes(event);
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
