/*
* Autor: Samuel Martínez Pazos
* Fecha de creación: 10/06/2023
* Descripción: Clase controladora para el menú principal de administrador
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

public class FXMLPrincipalAdministradorController implements Initializable {

    @FXML
    protected Label lbTitulo;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
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
    protected void clicIrCursos(ActionEvent event) {
        Stage escenarioBase = (Stage) lbTitulo.getScene().getWindow();
        escenarioBase.setScene(
                Utilidades.inicializarEscena("vistas/FXMLAdminCursos.fxml"));
        escenarioBase.setTitle("Administración cursos");
        escenarioBase.show();
    }

    @FXML
    protected void clicIrLgac(ActionEvent event) {
        Stage escenarioBase = (Stage) lbTitulo.getScene().getWindow();
        escenarioBase.setScene(
                Utilidades.inicializarEscena("vistas/FXMLAdminLgac.fxml"));
        escenarioBase.setTitle("Administración LGAC");
        escenarioBase.show();
    }

    @FXML
    protected void clicIrUsuarios(ActionEvent event) {
        Stage escenarioBase = (Stage) lbTitulo.getScene().getWindow();
        escenarioBase.setScene(
                Utilidades.inicializarEscena("vistas/FXMLAdminUsuarios.fxml"));
        escenarioBase.setTitle("Administración usuarios");
        escenarioBase.show();
    }

    @FXML
    protected void clicIrCuerposAcademicos(ActionEvent event) {
        Stage escenarioBase = (Stage) lbTitulo.getScene().getWindow();
        escenarioBase.setScene(
                Utilidades.inicializarEscena("vistas/FXMLAdminCuerposAcademicos.fxml"));
        escenarioBase.setTitle("Administración cuerpos académicos");
        escenarioBase.show();
    }
    
}
