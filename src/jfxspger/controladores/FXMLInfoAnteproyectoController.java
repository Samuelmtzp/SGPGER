/*
* Autor: Samuel Martínez Pazos
* Fecha de creación: 13/06/2023
* Descripción: Clase controladora para información de anteproyecto
*/
package jfxspger.controladores;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import jfxspger.modelo.dao.AnteproyectoDAO;
import jfxspger.modelo.dao.RevisionAnteproyectoDAO;
import jfxspger.modelo.dao.UsuarioDAO;
import jfxspger.modelo.pojo.Usuario;
import jfxspger.modelo.pojo.Anteproyecto;
import jfxspger.modelo.pojo.AnteproyectoRespuesta;
import jfxspger.modelo.pojo.RevisionAnteproyecto;
import jfxspger.modelo.pojo.RevisionAnteproyectoRespuesta;
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
    private ObservableList<RevisionAnteproyecto> revisionAnteproyecto;
    @FXML
    private TextArea taComentario;
    private boolean esValidacion, esValido;
    @FXML
    private Button btnValidar;
    @FXML
    private Label lbComentario;
    @FXML
    private Button btnConsultarAvances;
    @FXML
    private Button btnRegresar;
   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        validarSeccionesPermitidas();
        btnValidar.setVisible(false);
        taComentario.setEditable(false);
        btnRegresar.setVisible(false);
    }

    public void inicializarInformacion(boolean esValidacion, Anteproyecto anteproyecto){
        this.esValidacion = esValidacion;
        this.anteproyecto = anteproyecto;
        cargarInformacion();
        cambiarColorEstado();
        cargarEstudiantesAsignados();
        cargarCodirectoresAsignados();
        cargarInformacionRevision();
        if(esValidacion){
            btnValidar.setVisible(true);
            taComentario.setEditable(true);
            btnConsultarAvances.setVisible(false);
            btnAnteproyectos.setVisible(false);
            btnRegresar.setVisible(true);
        }
    }
    
    private void validarRegistro(){
        esValido=true;
        String comentario = taComentario.getText();
        
        if(comentario.isEmpty()){
            taComentario.setStyle(Constantes.estiloError);
            esValido=false;
        }else{
            if(comentario.length()>2000){
                taComentario.setStyle(Constantes.estiloError);
                esValido=false;
            }
        }
        
        RevisionAnteproyecto revisionAnteproyecto = new RevisionAnteproyecto();
        revisionAnteproyecto.setIdAnteproyecto(anteproyecto.getIdAnteproyecto());
        revisionAnteproyecto.setComentarioRevision(comentario);
        LocalDateTime tiempo = LocalDateTime.now();
        String fecha = tiempo.toString();
        System.out.println("fecha = " + fecha);
        revisionAnteproyecto.setFechaRevision(fecha);
        if(esValido){
             registrarRevision(revisionAnteproyecto);
        }
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
    
      private void cargarInformacionRevision(){
        revisionAnteproyecto = FXCollections.observableArrayList();
        RevisionAnteproyectoRespuesta respuestaBD = RevisionAnteproyectoDAO.
               obtenerInformacionRevisionAnteproyecto(anteproyecto.getIdAnteproyecto());
        switch (respuestaBD.getCodigoRespuesta()) {
            case Constantes.ERROR_CONEXION:
                    Utilidades.mostrarDialogoSimple("Sin conexión", 
              "Lo sentimos, por el momento no hay conexión para poder cargar la información", 
                    Alert.AlertType.ERROR);
                break;
            case Constantes.ERROR_CONSULTA:
                    Utilidades.mostrarDialogoSimple("Error al cargar los datos", 
                    "Hubo un error al cargar la información de la revision, "
                            + "por favor inténtelo más tarde", 
                    Alert.AlertType.WARNING);
                break;
            case Constantes.OPERACION_EXITOSA:
                System.out.println("hola");
                for(int i=0; i<revisionAnteproyecto.size(); i++){
                  if(revisionAnteproyecto.get(i).getIdAnteproyecto()== anteproyecto.getIdAnteproyecto()){
                    taComentario.setText(
                        revisionAnteproyecto.get(i).getComentarioRevision());
                         System.out.println("taComentario = " + taComentario.getText());
                    }
                }
                break;
        }
    } 

    @FXML
    private void clicBtnConsultarAvances(ActionEvent event) {
        try{
            FXMLLoader accesoControlador = new FXMLLoader(jfxspger.JFXSPGER.class.getResource("/jfxspger/vistas/FXMLAnteproyectoAvances.fxml"));
            Parent vista = accesoControlador.load();
            FXMLAnteproyectoAvancesController avances = accesoControlador.getController();
            avances.inicializarInformacion(anteproyecto);
            Scene sceneAvances = new Scene(vista);
            Stage escenarioPrincipal = (Stage) lbTitulo.getScene().getWindow();
            escenarioPrincipal.setTitle("Avances de anteproyecto");
            escenarioPrincipal.setScene(sceneAvances);
            
        }catch(IOException e){
            Utilidades.mostrarDialogoSimple("Error", 
                    "No se puede mostrar la pantalla de informacion de anteproyecto", 
                    Alert.AlertType.ERROR);  
        }
    }

    @FXML
    private void clicValidarAnteproyecto(ActionEvent event) {
        validarRegistro();
    }
    
    private void registrarRevision(RevisionAnteproyecto revisionRegistro){
        int codigoRespuesta = RevisionAnteproyectoDAO.guardarRevisionAnteproyecto(revisionRegistro);
        switch(codigoRespuesta){
            case Constantes.ERROR_CONEXION:
                Utilidades.mostrarDialogoSimple("Error de conexion", 
                        "El anteproyecto no pudo ser guardado debido a un error en su conexión...", 
                        Alert.AlertType.ERROR);
                break;
            case Constantes.ERROR_CONSULTA:
                Utilidades.mostrarDialogoSimple("Error en la información", 
                        "La información del anteproyecto no puede ser guardada, "
                        + "por favor verifique su información", 
                        Alert.AlertType.WARNING);
                break;
            case Constantes.OPERACION_EXITOSA:
                Utilidades.mostrarDialogoSimple("Anteproyecto registrado", 
                        "La información del anteproyecto fue guardada correctamente", 
                        Alert.AlertType.INFORMATION);
                actualizarEstadoDisponibleAnteproyecto(anteproyecto.getIdAnteproyecto());
                salir();
                break;
        }
    }
    
     private void actualizarEstadoDisponibleAnteproyecto(int idAnteproyecto) {
        int respuesta = AnteproyectoDAO.actualizarEstadoDisponibleAnteproyecto(idAnteproyecto);
        switch(respuesta) {
            case Constantes.ERROR_CONEXION:
                Utilidades.mostrarDialogoSimple("Error de conexion", "El anteproyecto " + 
                        "no pudo ser actualizado debido a un error de conexion.", 
                        Alert.AlertType.ERROR);
            break;
            case Constantes.ERROR_CONSULTA:
            Utilidades.mostrarDialogoSimple("Error al actualizar anteproyecto",
                        "No se puedo actualizar el anteproyecto,"
                        + "por favor inténtelo más tarde", 
                        Alert.AlertType.WARNING);
            break;
            case Constantes.OPERACION_EXITOSA:
            break;
        }
    }
     
     private void salir(){
        Stage escenarioBase = (Stage) lbTitulo.getScene().getWindow();
        escenarioBase.setScene(
                Utilidades.inicializarEscena("vistas/FXMLAdminPropuestasAnteproyectos.fxml"));
        escenarioBase.setTitle("Administración propuestas");
        escenarioBase.show();
    } 


    @FXML
    private void clicRegresar(ActionEvent event) {
         Stage escenarioBase = (Stage) lbTitulo.getScene().getWindow();
        escenarioBase.setScene(
                Utilidades.inicializarEscena("vistas/FXMLAdminPropuestasAnteproyectos.fxml"));
        escenarioBase.setTitle("Administración propuestas");
        escenarioBase.show();
    }

}
