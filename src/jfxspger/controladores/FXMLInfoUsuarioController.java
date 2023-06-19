/*
* Autor: Samuel Martínez Pazos
* Fecha de creación: 13/06/2023
* Descripción: Clase controladora para información de usuario
*/
package jfxspger.controladores;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import jfxspger.modelo.dao.AcademicoDAO;
import jfxspger.modelo.dao.EstudianteDAO;
import jfxspger.modelo.dao.TipoUsuarioDAO;
import jfxspger.modelo.pojo.Academico;
import jfxspger.modelo.pojo.AcademicoRespuesta;
import jfxspger.modelo.pojo.Estudiante;
import jfxspger.modelo.pojo.EstudianteRespuesta;
import jfxspger.modelo.pojo.TipoUsuarioRespuesta;
import jfxspger.modelo.pojo.Usuario;
import jfxspger.utilidades.Utilidades;

public class FXMLInfoUsuarioController extends FXMLPrincipalAdministradorController {

    @FXML
    private Label lbTitulo;
    @FXML
    private Label lbTituloCampoAdicional;
    @FXML
    private Label lbNombre;
    @FXML
    private Label lbApellidoPaterno;
    @FXML
    private Label lbApellidoMaterno;
    @FXML
    private Label lbTipoUsuario;
    @FXML
    private Label lbCorreo;
    @FXML
    private Label lbTelefono;
    @FXML
    private Label lbCampoAdicional;
    
    private final int TIPO_USUARIO_ESTUDIANTE = 2;
    private final int TIPO_USUARIO_ACADEMICO = 3;
    private Usuario usuario;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    
    
    public void inicializarInformacion(Usuario usuario){
        this.usuario = usuario;
        cargarInformacion(); 
    }
    
    private void cargarInformacion(){
        lbNombre.setText(usuario.getNombre());
        lbApellidoPaterno.setText(usuario.getApellidoPaterno());
        lbApellidoMaterno.setText(usuario.getApellidoMaterno());
        lbTelefono.setText(usuario.getTelefono());
        lbCorreo.setText(usuario.getCorreo());
        lbTipoUsuario.setText(obtenerTipoUsuario(usuario.getIdTipoUsuario()));
        switch (usuario.getIdTipoUsuario()) {
            case TIPO_USUARIO_ACADEMICO:
                AcademicoRespuesta informacionAcademico = 
                        AcademicoDAO.obtenerInformacionAcademico(usuario.getIdUsuario());
                Academico academicoEdicion = informacionAcademico.getAcademicos().get(0);
                lbCampoAdicional.setText(String.valueOf(academicoEdicion.getNumeroDePersonal()));
                lbTituloCampoAdicional.setText("Número de personal");
            break;
            case TIPO_USUARIO_ESTUDIANTE:
                EstudianteRespuesta informacionEstudiante = 
                        EstudianteDAO.obtenerInformacionEstudiante(usuario.getIdUsuario());
                Estudiante estudianteEdicion = informacionEstudiante.getEstudiantes().get(0);
                lbCampoAdicional.setText(estudianteEdicion.getMatricula());
                lbTituloCampoAdicional.setText("Matrícula");
            break;
            default:
                lbTituloCampoAdicional.setText("");
                lbCampoAdicional.setText("");
        }
    }
    
    private String obtenerTipoUsuario(int idTipoUsuario){
        TipoUsuarioRespuesta informacionTiposUsuario = TipoUsuarioDAO.
                obtenerInformacionTipoUsuario();
        for (int i = 0; i < informacionTiposUsuario.getTiposUsuario().size(); i++) {
            if(informacionTiposUsuario.getTiposUsuario().get(i).getIdTipoUsuario()== idTipoUsuario)
                return informacionTiposUsuario.getTiposUsuario().get(i).getTipoUsuario();
        }
        return null;
    }

    @FXML
    private void clicIrAdminUsuarios(ActionEvent event) {
        Stage escenarioBase = (Stage) lbTitulo.getScene().getWindow();
        escenarioBase.setScene(
                Utilidades.inicializarEscena("vistas/FXMLAdminUsuarios.fxml"));
        escenarioBase.setTitle("Administración usuarios");
        escenarioBase.show();
    }
}
