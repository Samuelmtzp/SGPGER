/*
* Autor: Samuel Martínez Pazos
* Fecha de creación: 13/06/2023
* Descripción: Clase controladora para información de anteproyecto
*/
package jfxspger.controladores;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.paint.Color;
import jfxspger.modelo.pojo.Anteproyecto;

public class FXMLInfoAnteproyectoController extends FXMLPrincipalAcademicoController {

    @FXML
    private TextArea taRequisitos;
    @FXML
    private TextArea taDescripcionProyecto;
    @FXML
    private TextArea taDescripcionTrabajo;
    @FXML
    private TextArea taResultadosEsperados;
    @FXML
    private TextArea taBibliografia;
    @FXML
    private TextArea taCuerpoAcademico;
    @FXML
    private TextArea taProyectoInvestigacion;
    @FXML
    private TextArea taLgac;
    @FXML
    private TextArea taLineaInvestigacion;
    @FXML
    private TextArea taDuracionAproximada;
    @FXML
    private TextArea taModalidad;
    @FXML
    private TextArea taNombreTrabajo;
    @FXML
    private TextArea taDirector;
    @FXML
    private TextArea taCodirector;
    @FXML
    private TextArea taAlumnosParticipantes;
    @FXML
    private Label lbEstado;
    @FXML
    private Label lbTitulo;
    private Anteproyecto anteproyecto;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    public void inicializarInformacion(Anteproyecto anteproyecto){
        this.anteproyecto = anteproyecto;
        cargarInformacion();
        cambiarColorEstado();
    }

    private void cargarInformacion() {
        taProyectoInvestigacion.setText(anteproyecto.getProyectoInvestigacion());
        taLineaInvestigacion.setText(anteproyecto.getLineaInvestigacion());
        taNombreTrabajo.setText(anteproyecto.getNombreTrabajo());
        taRequisitos.setText(anteproyecto.getRequisitos());
        taDescripcionProyecto.setText(anteproyecto.getDescripcionProyectoInvestigacion());
        taDescripcionTrabajo.setText(anteproyecto.getDescripcionTrabajoRecepcional());
        taResultadosEsperados.setText(anteproyecto.getResultadosEsperados());
        taBibliografia.setText(anteproyecto.getBibliografiaRecomendada());
        taCuerpoAcademico.setText(String.valueOf(anteproyecto.getCuerpoAcademico()));
        taDirector.setText(String.valueOf(anteproyecto.getDirector()));
        taModalidad.setText(String.valueOf(anteproyecto.getModalidad()));
        taLgac.setText(String.valueOf(anteproyecto.getLgac()));
        taDuracionAproximada.setText(anteproyecto.getDuracionAproximada());
        lbEstado.setText(anteproyecto.getEstado());
    }
    
    private void cambiarColorEstado() {
        if (anteproyecto.getIdEstado() == 1) {
            lbEstado.setTextFill(Color.web("#F5A623"));
        } else if (anteproyecto.getIdEstado() == 2) {
            lbEstado.setTextFill(Color.web("#15A010"));
        } else if (anteproyecto.getIdEstado() == 3) {
            lbEstado.setTextFill(Color.web("#D0021B"));
        }
    }

    @FXML
    private void clicBtnConsultarAvances(ActionEvent event) {
    }

}
