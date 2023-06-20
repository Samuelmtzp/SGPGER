/*
* Autor: Samuel Martínez Pazos
* Fecha de creación: 10/06/2023
* Descripción: Clase controladora para el menú principal de estudiante
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


public class FXMLPrincipalEstudianteController implements Initializable {

    @FXML
    private Label lbTitulo;
    public int idEstudiante;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public int getIdEstudiante() {
        return idEstudiante;
    }

    public void setIdEstudiante(int idEstudiante) {
        this.idEstudiante = idEstudiante;
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
    private void clicIrCronograma(ActionEvent event) {
        System.out.println("ID ALMACENADO: " + getIdEstudiante());
        FXMLInicioSesionController inicioSesion = new FXMLInicioSesionController();
        FXMLCronogramaActividadesController cronograma = new FXMLCronogramaActividadesController();
        cronograma.setIdEstudiante(inicioSesion.getIdEstudiante());
        System.out.println("ID: " + cronograma.getIdEstudiante());
        Stage escenarioBase = (Stage) lbTitulo.getScene().getWindow();
        escenarioBase.setScene(Utilidades.inicializarEscena(
                "vistas/FXMLCronogramaActividades.fxml"));
        escenarioBase.setTitle("Cronograma de actividades");
        escenarioBase.show();        
    }

    @FXML
    private void clicIrCursos(ActionEvent event) {      
    }

    @FXML
    private void clicIrPropuestas(ActionEvent event) {
    }

    @FXML
    private void clicIrAnteproyecto(ActionEvent event) {
        Stage escenarioBase = (Stage) lbTitulo.getScene().getWindow();
        escenarioBase.setScene(Utilidades.inicializarEscena(
                "vistas/FXMLAnteproyectoInformacion.fxml"));
        escenarioBase.setTitle("Informacion de anteproyecto");
        escenarioBase.show();        
    }
    
}
