/*
* Autor: Luis Ángel Elizalde Arroyo
* Fecha de creación: 13/06/2023
* Descripción: Clase controladora para la información del anteproyecto
*/
package jfxspger.controladores;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import jfxspger.interfaz.INotificacionOperacionActividad;
import jfxspger.modelo.pojo.Anteproyecto;
import jfxspger.utilidades.Utilidades;

public class FXMLAnteproyectoInformacionAcademicoController implements Initializable, 
        INotificacionOperacionActividad {

    @FXML
    private Label lbDescAnteproyecto;
    @FXML
    private Label lbTituloAnteproyecto;
    @FXML
    private Label lbTitulo;
    private INotificacionOperacionActividad interfazNotificacion;
    private Anteproyecto anteproyectoInformacion;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }
    
    public void inicializarInformacionAnteproyecto(Anteproyecto anteproyectoInformacion, 
            INotificacionOperacionActividad interfazNotificacion){        
        this.anteproyectoInformacion = anteproyectoInformacion;
        this.interfazNotificacion = interfazNotificacion;
                
            lbTitulo.setText("Detalles de anteproyecto: " + anteproyectoInformacion.getNombreTrabajo());
            cargarInformacionAnteproyecto();
    }
    
    private void cargarInformacionAnteproyecto(){
        lbTituloAnteproyecto.setText(anteproyectoInformacion.getNombreTrabajo());
        lbDescAnteproyecto.setText(anteproyectoInformacion.getRequisitos());
    }

    @FXML
    private void clicIrPrincipalEstudiante(ActionEvent event) {
    }

    @FXML
    private void clicIrAnteproyectos(ActionEvent event) {
    }

    @FXML
    private void clicIrPropuestas(ActionEvent event) {
    }

    @FXML
    private void clicIrEstudiantes(ActionEvent event) {
    }

    @FXML
    private void clicCerrarSesion(ActionEvent event) {
        if (Utilidades.mostrarDialogoConfirmacion(
                "Cerrar sesión", 
                "¿Está seguro de que desea cerrar sesión?")) {
            irVentanaInicioSesion();
        }        
    }
    
    private void irVentanaInicioSesion() {
        Stage escenarioBase = (Stage) lbTitulo.getScene().getWindow();
        escenarioBase.setScene(
                Utilidades.inicializarEscena("vistas/FXMLInicioSesion.fxml"));
        escenarioBase.setTitle("Inicio de sesion");
        escenarioBase.show();
    }

    @FXML
    private void clicIrEntregables(ActionEvent event) {
    }

    @FXML
    private void clicIrRevisiones(ActionEvent event) {
    }

    @FXML
    private void clicBtnVerAvances(ActionEvent event) {
        Stage escenarioBase = (Stage) lbTitulo.getScene().getWindow();
        escenarioBase.setScene(
                Utilidades.inicializarEscena("vistas/FXMLAnteproyectoAvances.fxml"));
        escenarioBase.setTitle("Avance de anteproyecto");
        escenarioBase.show();        
    }
    
        @Override
    public void notificarOperacionGuardar(String nombreActividad) {
        
    }

    @Override
    public void notificarOperacionActualizar(String nombreActividad) {
        
    } 
}
