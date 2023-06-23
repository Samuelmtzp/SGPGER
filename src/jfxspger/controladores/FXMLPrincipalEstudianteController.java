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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import jfxspger.modelo.dao.EstudianteDAO;
import jfxspger.utilidades.SingletonUsuario;
import jfxspger.utilidades.Utilidades;


public class FXMLPrincipalEstudianteController implements Initializable {

    @FXML
    protected Label lbTitulo;
    @FXML
    protected Button btnAnteproyecto;
    @FXML
    protected Button btnCronograma;
    @FXML
    protected Button btnCursos;

    @Override
    public void initialize(URL url, ResourceBundle rb) {        
        validarSeccionesPermitidas();
    }
    
    protected void validarSeccionesPermitidas() {        
        if (esEstudianteConCurso()) {
            btnCursos.setDisable(false);
        }
        if (esEstudianteConAnteproyecto()) {
            btnCronograma.setDisable(false);
            btnAnteproyecto.setDisable(false);
        }
    }
    
    protected boolean esEstudianteConCurso() {
        return (EstudianteDAO.obtenerCantidadCursosExistenciaEstudiante(
                SingletonUsuario.getInstancia().getUsuario().getIdEstudiante()) > 0); 
    }    
    
    protected boolean esEstudianteConAnteproyecto() {
        return SingletonUsuario.getInstancia().getUsuario().getIdAnteproyecto() != 0; 
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
    protected void clicIrCronograma(ActionEvent event) {        
        irCronograma();
    }
    
    protected void irCronograma() {
        Stage escenarioBase = (Stage) lbTitulo.getScene().getWindow();
        escenarioBase.setScene(Utilidades.inicializarEscena(
                "vistas/FXMLCronogramaActividades.fxml"));
        escenarioBase.setTitle("Cronograma de actividades");
        escenarioBase.show();
    }

    @FXML
    protected void clicIrCursos(ActionEvent event) {      
    }

    @FXML
    protected void clicIrPropuestas(ActionEvent event) {
        Stage escenarioBase = (Stage) lbTitulo.getScene().getWindow();
        escenarioBase.setScene(Utilidades.inicializarEscena(
                "vistas/FXMLPropuestasEstudiante.fxml"));
        escenarioBase.setTitle("Anteproyectos disponibles");
        escenarioBase.show();        
    }

    @FXML
    protected void clicIrAnteproyecto(ActionEvent event) {
        Stage escenarioBase = (Stage) lbTitulo.getScene().getWindow();
        escenarioBase.setScene(Utilidades.inicializarEscena(
                "vistas/FXMLInfoAnteproyectoEstudiante.fxml"));
        escenarioBase.setTitle("Informacion de anteproyecto");
        escenarioBase.show();        
    }
    
}
