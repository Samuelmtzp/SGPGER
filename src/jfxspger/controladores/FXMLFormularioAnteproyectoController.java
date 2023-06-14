/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package jfxspger.controladores;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import jfxspger.modelo.dao.AcademicoDAO;
import jfxspger.modelo.dao.CuerpoAcademicoDAO;
import jfxspger.modelo.dao.LgacDAO;
import jfxspger.modelo.dao.ModalidadDAO;
import jfxspger.modelo.pojo.Academico;
import jfxspger.modelo.pojo.AcademicoRespuesta;
import jfxspger.modelo.pojo.CuerpoAcademico;
import jfxspger.modelo.pojo.CuerpoAcademicoRespuesta;
import jfxspger.modelo.pojo.EstadoAnteproyecto;
import jfxspger.modelo.pojo.Lgac;
import jfxspger.modelo.pojo.LgacRespuesta;
import jfxspger.modelo.pojo.Modalidad;
import jfxspger.modelo.pojo.ModalidadRespuesta;
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
    private ComboBox<Academico> cbDirector; //academico como director(no estoy seguro xd)
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
    private TextArea taComentarios;
    
    //ObservableList
    private ObservableList<Lgac> lgac;
    private ObservableList<Modalidad> modalidad;
    private ObservableList<Academico> academico;
    private ObservableList<CuerpoAcademico> cuerpoAcademico;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarInformacionLGAC();
        System.out.println("hola");
    }    

    @FXML
    private void clicBtnPostular(ActionEvent event) {
        
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
                cbCuerpoAcademico.setItems(cuerpoAcademico);
                break;
        }
    }
    
    private void cargarInformacionAcademico(){
        academico = FXCollections.observableArrayList();
        AcademicoRespuesta AcademicoBD=AcademicoDAO.obtenerInformacionAcademico(0);
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
                academico.addAll(AcademicoBD.getAcademicos());
                cbDirector.setItems(academico);
                break;
        }
    }

    @FXML
    private void clicIrAnteproyectos(ActionEvent event) {
        Utilidades.mostrarDialogoConfirmacion("Confirmacion", "Seguro que deseas salir?");
    }
    
    

}
