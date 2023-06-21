/*
* Autor: Samuel Martínez Pazos
* Fecha de creación: 10/06/2023
* Descripción: Clase controladora para el menú principal de académico
*/
package jfxspger.controladores;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import jfxspger.utilidades.Utilidades;

public class FXMLPrincipalAcademicoController implements Initializable {

    @FXML
    protected Label lbTitulo;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    

    @FXML
    protected void clicIrAnteproyectos(ActionEvent event) {
        Stage escenarioBase = (Stage) lbTitulo.getScene().getWindow();
        escenarioBase.setScene(
                Utilidades.inicializarEscena("vistas/FXMLAdminAnteproyectos3.fxml"));
        escenarioBase.setTitle("Administracion anteproyecto");
        escenarioBase.show();
    }

    @FXML
    protected void clicIrPropuestas(ActionEvent event) {
    }

    @FXML
    protected void clicIrEstudiantes(ActionEvent event) {
        Stage escenarioBase = (Stage) lbTitulo.getScene().getWindow();
        escenarioBase.setScene(
                Utilidades.inicializarEscena("vistas/FXMLEstudiantesAcademico.fxml"));
        escenarioBase.setTitle("Estudiantes");
        escenarioBase.show();        
    }

    @FXML
    protected void clicCerrarSesion(ActionEvent event) {
        if (Utilidades.mostrarDialogoConfirmacion(
                "Cerrar sesión", 
                "¿Está seguro de que desea cerrar sesión?")) {
            irVentanaInicioSesion();
        }
    }
    
    protected void irVentanaInicioSesion() {
        Stage escenarioBase = (Stage) lbTitulo.getScene().getWindow();
        escenarioBase.setScene(
                Utilidades.inicializarEscena("vistas/FXMLInicioSesion.fxml"));
        escenarioBase.setTitle("Inicio de sesion");
        escenarioBase.show();
    }
    
    @FXML
    protected void clicIrEntregables(ActionEvent event) {
    }

    @FXML
    protected void clicIrRevisiones(ActionEvent event) {
    }
    
}
