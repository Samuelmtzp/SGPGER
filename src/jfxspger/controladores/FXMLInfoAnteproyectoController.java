/*
* Autor: Samuel Martínez Pazos
* Fecha de creación: 13/06/2023
* Descripción: Clase controladora para información de anteproyecto
*/
package jfxspger.controladores;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import jfxspger.modelo.dao.UsuarioDAO;
import jfxspger.modelo.pojo.Usuario;
import jfxspger.modelo.pojo.Anteproyecto;
import jfxspger.modelo.pojo.UsuarioRespuesta;
import jfxspger.utilidades.Constantes;
import jfxspger.utilidades.Utilidades;

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
    @FXML
    private Button btnConsultarAvances;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        validarSeccionesPermitidas();
    }

    public void inicializarInformacion(Anteproyecto anteproyecto){
        this.anteproyecto = anteproyecto;
        cargarInformacion();
        cambiarColorEstado();
        cargarEstudiantesAsignados();
        cargarCodirectoresAsignados();
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
        final int ESTADO_VALIDACION_PENDIENTE = 1;
        final int ESTADO_DISPONIBLE = 2;
        final int ESTADO_NO_DISPONIBLE = 3;
        
        switch (anteproyecto.getIdEstado())
        {
            case ESTADO_VALIDACION_PENDIENTE:
                lbEstado.setTextFill(Color.web("#F5A623"));
                break;
            case ESTADO_DISPONIBLE:
                lbEstado.setTextFill(Color.web("#15A010"));
                break;
            case ESTADO_NO_DISPONIBLE:
                lbEstado.setTextFill(Color.web("#D0021B"));
                break;
            default:
                break;
        }
    }
    
    private void cargarEstudiantesAsignados() {
        UsuarioRespuesta respuestaEstudiantesEnAnteproyecto = UsuarioDAO.
                consultarEstudiantesEnAnteproyecto(anteproyecto.getIdAnteproyecto());
        if (respuestaEstudiantesEnAnteproyecto.getCodigoRespuesta() == 
                Constantes.OPERACION_EXITOSA) {
            for (Usuario estudiante : respuestaEstudiantesEnAnteproyecto.getUsuarios()) {
                
                taAlumnosParticipantes.setText(taAlumnosParticipantes.getText() 
                        + estudiante.toString() + "\n");                
            }
        }
    }
    
    private void cargarCodirectoresAsignados() {
        UsuarioRespuesta respuestaCodirectoresEnAnteproyecto = UsuarioDAO.
                consultarCodirectoresEnAnteproyecto(anteproyecto.getIdAnteproyecto());
        if (respuestaCodirectoresEnAnteproyecto.getCodigoRespuesta() == 
                Constantes.OPERACION_EXITOSA) {
            for (Usuario codirector : respuestaCodirectoresEnAnteproyecto.getUsuarios()) {
                taCodirector.setText(taCodirector.getText() 
                        + codirector.toString() + "\n");
            }
        }
    }

    @FXML
    private void clicBtnConsultarAvances(ActionEvent event) {
                try{
            FXMLLoader accesoControlador = new FXMLLoader(jfxspger.
                    JFXSPGER.class.getResource("/jfxspger/vistas/FXMLAnteproyectoAvances.fxml"));
            Parent vista = accesoControlador.load();
            FXMLAnteproyectoAvancesController avances = accesoControlador.getController();
            Scene sceneAvances = new Scene(vista);
            Stage escenarioPrincipal = (Stage) lbTitulo.getScene().getWindow();
            escenarioPrincipal.setTitle("Informacion de anteproyecto");
            escenarioPrincipal.setScene(sceneAvances);
            avances.inicializarInformacion(anteproyecto);
        }catch(IOException e){
            Utilidades.mostrarDialogoSimple("Error", 
                    "No se puede mostrar la pantalla de informacion de anteproyecto", 
                    Alert.AlertType.ERROR);  
        }
    }

}
