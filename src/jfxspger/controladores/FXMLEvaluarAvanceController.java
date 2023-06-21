/*
* Autor: Carlos Rodriguez Herrera
* Fecha de creación: 18/06/2023
* Descripción: Clase controladora para evaluar los avances del estudiante
*/
package jfxspger.controladores;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import jfxspger.utilidades.Utilidades;

public class FXMLEvaluarAvanceController extends FXMLPrincipalAcademicoController implements Initializable {

    @FXML
    private TextField tfNombreActividad;
    @FXML
    private TextField tfDescripcionActividad;
    @FXML
    private Label lbTitulo;
    @FXML
    private Button bEliminar;
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
        
    }

    @FXML
    private void clicBtnGuardar(ActionEvent event) {
    }


    @FXML
    private void clicBtnEliminarActividad(ActionEvent event) {
    }

    @FXML
    protected void clicIrAnteproyectos(ActionEvent event) {
    }

    @FXML
    protected void clicIrPropuestas(ActionEvent event) {
    }

    @FXML
    protected void clicIrEstudiantes(ActionEvent event) {
    }

    @FXML
    protected void clicCerrarSesion(ActionEvent event) {
    }

    @FXML
    protected void clicIrRevisiones(ActionEvent event) {
    }

    @FXML
    private void clicIrAvancesEstudiante(ActionEvent event) {
        Stage escenarioBase = (Stage) lbTitulo.getScene().getWindow();
        escenarioBase.setScene(
                Utilidades.inicializarEscena("vistas/FXMLAvancesEstudiante.fxml"));
        escenarioBase.setTitle("Avances de estudiantes");
        escenarioBase.show();
    }
    
}
