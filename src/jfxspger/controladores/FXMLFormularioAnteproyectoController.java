/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package jfxspger.controladores;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import jfxspger.modelo.dao.AcademicoDAO;
import jfxspger.modelo.dao.CuerpoAcademicoDAO;
import jfxspger.modelo.dao.LgacDAO;
import jfxspger.modelo.dao.ModalidadDAO;
import jfxspger.modelo.dao.UsuarioDAO;
import jfxspger.modelo.pojo.Academico;
import jfxspger.modelo.pojo.AcademicoRespuesta;
import jfxspger.modelo.pojo.CuerpoAcademico;
import jfxspger.modelo.pojo.CuerpoAcademicoRespuesta;
import jfxspger.modelo.pojo.EstadoAnteproyecto;
import jfxspger.modelo.pojo.Lgac;
import jfxspger.modelo.pojo.LgacRespuesta;
import jfxspger.modelo.pojo.Modalidad;
import jfxspger.modelo.pojo.ModalidadRespuesta;
import jfxspger.modelo.pojo.Usuario;
import jfxspger.modelo.pojo.UsuarioRespuesta;
import jfxspger.utilidades.Constantes;
import jfxspger.utilidades.Utilidades;

/**
 * FXML Controller class
 *
 * @author king_
 */
public class FXMLFormularioAnteproyectoController extends FXMLPrincipalAcademicoController {

    @FXML
    private Label lbTitulo;
    @FXML
    private ComboBox<CuerpoAcademico> cbCuerpoAcademico;
    @FXML
    private ComboBox<Usuario> cbDirector; //academico como director(no estoy seguro xd)
    @FXML
    private ComboBox<Modalidad> cbModalidad;
    @FXML
    private ComboBox<Lgac> cbLGAC;
    @FXML
    private TextField tfProyectoInvestigacion;
    @FXML
    private TextField tfLineaInvestigacion;
    @FXML
    private TextField tfNombreTrabajo;
    @FXML
    private TextArea taRequisitos;
    @FXML
    private TextField tfCantidadAlumnos;
    @FXML
    private TextArea taDescripcionProyecto;
    @FXML
    private TextArea taDescripcionTrabajo;
    @FXML
    private TextArea taResultadosEsperados;
    @FXML
    private TextArea taBibliografia;
    @FXML
    private DatePicker dpFechaInicio;
    @FXML
    private DatePicker dpFechaFin;
    
    //ObservableList
    private ObservableList<Lgac> lgac;
    private ObservableList<Modalidad> modalidad;
    private ObservableList<CuerpoAcademico> cuerpoAcademico;
    private ObservableList<Usuario> usuarios;
   
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarInformacionLGAC();
        cargarInformacionModalidad();
        cargarInformacionAcademico();
        cargarInformacionCuerpoAcademico();
        
        tfCantidadAlumnos.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, 
            String newValue) {
                if (!newValue.matches("\\d*")) {
                     tfCantidadAlumnos.setText(newValue.replaceAll("[^\\d]", ""));
                 }
            }});
    }    

    @FXML
    private void clicBtnPostular(ActionEvent event) {
        validarInformacion();
    }
    
    private void validarInformacion(){
       establecerEstiloNormal();
       String proyectoInvestigacion = tfProyectoInvestigacion.getText();
       String lineaInvestigacion = tfLineaInvestigacion.getText();
       String nombreTrabajo = tfNombreTrabajo.getText();
       String requisitos = taRequisitos.getText();
       String cantidadAlumnos = tfCantidadAlumnos.getText();
       String descripcionProyecto = taDescripcionProyecto.getText();
       String descripcionTrabajo = taDescripcionTrabajo.getText();
       String resultadosEsperados = taResultadosEsperados.getText();
       String bibliografiaRecomendada = taBibliografia.getText();
       int posicionCuerpoAcademico = cbCuerpoAcademico.getSelectionModel().getSelectedIndex();
       int posicionDirector = cbDirector.getSelectionModel().getSelectedIndex();
       int posicionModalidad = cbModalidad.getSelectionModel().getSelectedIndex();
       int posicionLGAC = cbLGAC.getSelectionModel().getSelectedIndex();
       LocalDate fechaInicio =dpFechaInicio.getValue();
       LocalDate fechaFin = dpFechaFin.getValue();
       boolean sonValidos=true;
       
       //Validaciones
       if(proyectoInvestigacion.isEmpty()){
           tfProyectoInvestigacion.setStyle(Constantes.estiloError);
           sonValidos=false;
       }
       
       if(lineaInvestigacion.isEmpty()){
           tfLineaInvestigacion.setStyle(Constantes.estiloError);
           sonValidos=false;
       }
       
       if(nombreTrabajo.isEmpty()){
           tfNombreTrabajo.setStyle(Constantes.estiloError);
           sonValidos=false;
       }
       
       if(cantidadAlumnos.isEmpty()){
           tfCantidadAlumnos.setStyle(Constantes.estiloError);
           sonValidos=false;
       }else{
           int cantidad = Integer.parseInt(cantidadAlumnos);
           if(cantidad==0){
               tfCantidadAlumnos.setStyle(Constantes.estiloError);
           }
       }
       
       if(requisitos.isEmpty()){
           taRequisitos.setStyle(Constantes.estiloError);
           sonValidos=false;
       }
       
       if(descripcionProyecto.isEmpty()){
           taDescripcionProyecto.setStyle(Constantes.estiloError);
           sonValidos=false;
       }
       
       if(descripcionTrabajo.isEmpty()){
           taDescripcionTrabajo.setStyle(Constantes.estiloError);
           sonValidos=false;
       }
       
       if(resultadosEsperados.isEmpty()){
           taResultadosEsperados.setStyle(Constantes.estiloError);
           sonValidos=false;
       }
       
       if(posicionCuerpoAcademico == -1){
           cbCuerpoAcademico.setStyle(Constantes.estiloError);
           sonValidos=false;
       }
       
       if(posicionDirector == -1){
           cbDirector.setStyle(Constantes.estiloError);
           sonValidos=false;
       }
       
       if(posicionLGAC == -1){
           cbLGAC.setStyle(Constantes.estiloError);
           sonValidos=false;
       }
       
       if(posicionModalidad == -1){
           cbModalidad.setStyle(Constantes.estiloError);
           sonValidos=false;
       }
       
       if(fechaInicio == null){
            dpFechaInicio.setStyle(Constantes.estiloError);
            sonValidos=false;
        }else{
           if(fechaFin!=null){
                if(fechaInicio.isAfter(fechaFin)){
                    dpFechaInicio.setStyle(Constantes.estiloError);
                    sonValidos=false;
                }
           }
        }
        
        //Validacion fecha fin
        if(fechaFin==null){
            dpFechaFin.setStyle(Constantes.estiloError);
            sonValidos=false;
        }else{
            if(fechaInicio!=null){
                if(fechaFin.isBefore(fechaInicio)){
                    dpFechaFin.setStyle(Constantes.estiloError);
                    sonValidos=false;
                }
            }
        }
       
        
    }
    
    
    private void cargarInformacionLGAC(){
        lgac = FXCollections.observableArrayList();
        LgacRespuesta LgacBD=LgacDAO.obtenerInformacionLgac();
        switch(LgacBD.getCodigoRespuesta()){
            case Constantes.ERROR_CONEXION:
                Utilidades.mostrarDialogoSimple("Error de conexion", "Error en la conexion con la base de datos", 
                        Alert.AlertType.ERROR);
                break;
            case Constantes.ERROR_CONSULTA:
                Utilidades.mostrarDialogoSimple("Error de consulta", "Por el momento no se pudo obtener la informacion", 
                        Alert.AlertType.ERROR);
                break;
            case Constantes.OPERACION_EXITOSA:
                lgac.addAll(LgacBD.getListaLgac());
                cbLGAC.setItems(lgac);
                break;
        }
    }
    
    private void cargarInformacionCuerpoAcademico(){
        cuerpoAcademico = FXCollections.observableArrayList();
        CuerpoAcademicoRespuesta CuerpoAcademicoBD=CuerpoAcademicoDAO.obtenerInformacionCuerpoAcademico();
        switch(CuerpoAcademicoBD.getCodigoRespuesta()){
            case Constantes.ERROR_CONEXION:
                Utilidades.mostrarDialogoSimple("Error de conexion", "Error en la conexion con la base de datos", 
                        Alert.AlertType.ERROR);
                break;
            case Constantes.ERROR_CONSULTA:
                Utilidades.mostrarDialogoSimple("Error de consulta", "Por el momento no se pudo obtener la informacion", 
                        Alert.AlertType.ERROR);
                break;
            case Constantes.OPERACION_EXITOSA:
                cuerpoAcademico.addAll(CuerpoAcademicoBD.getCuerposAcademicos());
                cbCuerpoAcademico.setItems(cuerpoAcademico);
                break;
        }
    }
    
    private void cargarInformacionModalidad(){
        modalidad = FXCollections.observableArrayList();
        ModalidadRespuesta ModalidadBD=ModalidadDAO.obtenerInformacionModalidad();
        switch(ModalidadBD.getCodigoRespuesta()){
            case Constantes.ERROR_CONEXION:
                Utilidades.mostrarDialogoSimple("Error de conexion", "Error en la conexion con la base de datos", 
                        Alert.AlertType.ERROR);
                break;
            case Constantes.ERROR_CONSULTA:
                Utilidades.mostrarDialogoSimple("Error de consulta", "Por el momento no se pudo obtener la informacion", 
                        Alert.AlertType.ERROR);
                break;
            case Constantes.OPERACION_EXITOSA:
                modalidad.addAll(ModalidadBD.getModalidades());
                cbModalidad.setItems(modalidad);
                break;
        }
    }
    
    private void cargarInformacionAcademico(){
        usuarios = FXCollections.observableArrayList();
        UsuarioRespuesta AcademicoBD=UsuarioDAO.obtenerInformacionAcademicos();
        switch(AcademicoBD.getCodigoRespuesta()){
            case Constantes.ERROR_CONEXION:
                Utilidades.mostrarDialogoSimple("Error de conexion", "Error en la conexion con la base de datos", 
                        Alert.AlertType.ERROR);
                break;
            case Constantes.ERROR_CONSULTA:
                Utilidades.mostrarDialogoSimple("Error de consulta", "Por el momento no se pudo obtener la informacion", 
                        Alert.AlertType.ERROR);
                break;
            case Constantes.OPERACION_EXITOSA:
                usuarios.addAll(AcademicoBD.getUsuarios());
                cbDirector.setItems(usuarios);
                break;
        }
    }

    @FXML
    private void clicIrAnteproyectos(ActionEvent event) {
        Utilidades.mostrarDialogoConfirmacion("Confirmacion", "Seguro que deseas salir?");
    }
    
    private void establecerEstiloNormal(){
        tfCantidadAlumnos.setStyle(Constantes.estiloNormal);
        tfLineaInvestigacion.setStyle(Constantes.estiloNormal);
        tfNombreTrabajo.setStyle(Constantes.estiloNormal);
        tfProyectoInvestigacion.setStyle(Constantes.estiloNormal);
        taBibliografia.setStyle(Constantes.estiloNormal);
        taDescripcionProyecto.setStyle(Constantes.estiloNormal);
        taDescripcionTrabajo.setStyle(Constantes.estiloNormal);
        taRequisitos.setStyle(Constantes.estiloNormal);
        taResultadosEsperados.setStyle(Constantes.estiloNormal);
        dpFechaInicio.setStyle(Constantes.estiloNormal);
        dpFechaFin.setStyle(Constantes.estiloNormal);
        cbCuerpoAcademico.setStyle(Constantes.estiloNormal);
        cbDirector.setStyle(Constantes.estiloNormal);
        cbLGAC.setStyle(Constantes.estiloNormal);
        cbModalidad.setStyle(Constantes.estiloNormal);
    }

}
