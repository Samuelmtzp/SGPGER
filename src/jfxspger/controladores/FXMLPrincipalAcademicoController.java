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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import jfxspger.modelo.dao.AcademicoDAO;
import jfxspger.utilidades.SingletonUsuario;
import jfxspger.utilidades.Utilidades;

public class FXMLPrincipalAcademicoController implements Initializable {

    @FXML
    protected Label lbTitulo;
    @FXML
    protected Button btnAnteproyectos;
    @FXML
    protected Button btnPropuestas;
    @FXML
    protected Button btnEstudiantes;
    @FXML
    protected Button btnRevisiones;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        validarSeccionesPermitidas();
    }    

    @FXML
    protected void clicIrAnteproyectos(ActionEvent event) {
        System.out.println("hola");
        Stage escenarioBase = (Stage) lbTitulo.getScene().getWindow();
        System.out.println("1");
        escenarioBase.setScene(
                Utilidades.inicializarEscena("vistas/FXMLAdminAnteproyectos3.fxml"));
        System.out.println("2");
        escenarioBase.setTitle("Administracion anteproyecto");
        System.out.println("3");
        escenarioBase.show();
        System.out.println("4");
    }

    @FXML
    protected void clicIrPropuestas(ActionEvent event) {
        Stage escenarioBase = (Stage) lbTitulo.getScene().getWindow();
        escenarioBase.setScene(
                Utilidades.inicializarEscena("vistas/FXMLAdminPropuestasAnteproyectos.fxml"));
        escenarioBase.setTitle("Administracion anteproyecto");
        escenarioBase.show();
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
    protected void clicIrRevisiones(ActionEvent event) {
    }
    
    protected void validarSeccionesPermitidas() {        
        if (esMiembroDeCA()) {
            btnAnteproyectos.setDisable(false);            
        }
        if (esDirector()) {
            btnAnteproyectos.setDisable(false);
            btnEstudiantes.setDisable(false);
            btnRevisiones.setDisable(false);
        }
        if (esCodirector()) {
            btnAnteproyectos.setDisable(false);
            btnEstudiantes.setDisable(false);
        }
        if (esResponsableDeCA()) {
            btnPropuestas.setDisable(false);            
        }
        if (esProfesor()) {
            btnEstudiantes.setDisable(false);
            btnAnteproyectos.setDisable(false);
        }
    }
    
    protected boolean esDirector() {
        return (AcademicoDAO.obtenerCantidadAnteproyectosAcademicoEsDirector(
                SingletonUsuario.getInstancia().getUsuario().getIdAcademico()) > 0); 
    }    
    
    protected boolean esCodirector() {
        return (AcademicoDAO.obtenerCantidadAnteproyectosAcademicoEsCodirector(
                SingletonUsuario.getInstancia().getUsuario().getIdAcademico()) > 0);
    }
    
    protected boolean esMiembroDeCA() {
        return (SingletonUsuario.getInstancia().getUsuario().
                getIdcuerpoAcademico() != 0);
    }
    
    protected boolean esResponsableDeCA() {
        return (AcademicoDAO.obtenerCantidadCuerposAcademicoSAcademicoEsResponsable(
                SingletonUsuario.getInstancia().getUsuario().getIdAcademico()) > 0);
    }
    
    protected boolean esProfesor() {
        return (AcademicoDAO.obtenerCantidadCursosAcademicoEsProfesor(
                SingletonUsuario.getInstancia().getUsuario().getIdAcademico()) > 0);
    }
    
    protected boolean esCodirectorDeAnteproyecto(int idAnteproyecto) {
        return (AcademicoDAO.consultarCoincidenciasCodirectorDeAnteproyecto(
                SingletonUsuario.getInstancia().getUsuario().getIdAcademico(), 
                idAnteproyecto) == 1);
    }
    
}
