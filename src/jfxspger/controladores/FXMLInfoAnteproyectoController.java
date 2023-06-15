package jfxspger.controladores;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import jfxspger.modelo.dao.CuerpoAcademicoDAO;
import jfxspger.modelo.dao.EstadoAnteproyectoDAO;
import jfxspger.modelo.dao.LgacDAO;
import jfxspger.modelo.dao.ModalidadDAO;
import jfxspger.modelo.dao.UsuarioDAO;
import jfxspger.modelo.pojo.Anteproyecto;
import jfxspger.modelo.pojo.CuerpoAcademico;
import jfxspger.modelo.pojo.CuerpoAcademicoRespuesta;
import jfxspger.modelo.pojo.EstadoAnteproyecto;
import jfxspger.modelo.pojo.EstadoAnteproyectoRespuesta;
import jfxspger.modelo.pojo.Lgac;
import jfxspger.modelo.pojo.LgacRespuesta;
import jfxspger.modelo.pojo.Modalidad;
import jfxspger.modelo.pojo.ModalidadRespuesta;
import jfxspger.modelo.pojo.Usuario;
import jfxspger.modelo.pojo.UsuarioRespuesta;
import jfxspger.utilidades.Constantes;
import jfxspger.utilidades.Utilidades;

public class FXMLInfoAnteproyectoController extends FXMLPrincipalAcademicoController {

    @FXML
    private Label lbTitulo;
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
    private Label lbProyectoDeInvestigacion;
    @FXML
    private Label lbLineaDeInvestigacion;
    @FXML
    private Label lbNombreDelTrabajo;
    @FXML
    private Label lbCantidadAlumnosParticipantes;
    @FXML
    private ComboBox<CuerpoAcademico> cbCuerpoAcademico;
    @FXML
    private ComboBox<Usuario> cbDirector;
    @FXML
    private ComboBox<Modalidad> cbModalidad;
    @FXML
    private ComboBox<Lgac> cbLGAC;
    @FXML
    private DatePicker dpFechaInicio;
    @FXML
    private Anteproyecto anteproyecto;
    private DatePicker dpFechaFin;
    private ObservableList<Lgac> lgac;
    private ObservableList<Modalidad> modalidad;
    private ObservableList<CuerpoAcademico> cuerpoAcademico;
    private ObservableList<Usuario> usuarios;
    private ObservableList<EstadoAnteproyecto> estado;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarInformacionLGAC();
        cargarInformacionModalidad();
        cargarInformacionAcademico();
        cargarInformacionCuerpoAcademico();
        cargarInformacionEstado();
        dpFechaInicio.setEditable(false);
        dpFechaFin.setEditable(false);
    }    
    
    public void inicializarInformacion(Anteproyecto anteproyecto){
        this.anteproyecto = anteproyecto;
        cargarInformacion(); 
    }
    
    private void cargarInformacion(){
        lbProyectoDeInvestigacion.setText(anteproyecto.getProyectoInvestigacion());
        lbLineaDeInvestigacion.setText(anteproyecto.getLineaInvestigacion());
        lbNombreDelTrabajo.setText(anteproyecto.getNombreTrabajo());
        taRequisitos.setText(anteproyecto.getRequisitos());
        lbCantidadAlumnosParticipantes.setText(String.valueOf(
                anteproyecto.getCantidadAlumnosParticipantes()));
        taDescripcionProyecto.setText(anteproyecto.getDescripcionProyectoInvestigacion());
        taDescripcionTrabajo.setText(anteproyecto.getDescripcionTrabajoRecepcional());
        taResultadosEsperados.setText(anteproyecto.getResultadosEsperados());
        taBibliografia.setText(anteproyecto.getBibliografiaRecomendada());
        cbCuerpoAcademico.getSelectionModel().select(
                obtenerPosicionCuerpoAcademico(anteproyecto.getIdCuerpoAcademico()));
        cbDirector.getSelectionModel().select(
                obtenerPosicionDirector(anteproyecto.getIdDirector()));
        cbModalidad.getSelectionModel().select(
                obtenerPosicionModalidad(anteproyecto.getIdModalidad()));
        cbLGAC.getSelectionModel().select(obtenerPosicionLgac(anteproyecto.getIdLgac()));
        dpFechaInicio.setValue(LocalDate.parse(anteproyecto.getFechaInicio()));
        dpFechaFin.setValue(LocalDate.parse(anteproyecto.getFechaFin()));
    }
    
    private int obtenerPosicionCuerpoAcademico(int idCuerpoAcademico){
        for (int i = 0; i < cuerpoAcademico.size(); i++) {
            if (cuerpoAcademico.get(i).getIdCuerpoAcademico() == idCuerpoAcademico)
                return i;
        }
        return 0;
    }
    
    private int obtenerPosicionDirector(int idDirector){
        for (int i = 0; i < usuarios.size(); i++) {
            if (usuarios.get(i).getIdAcademico() == idDirector)
                return i;
        }
        return 0;
    }
    
    private int obtenerPosicionModalidad(int idModalidad){
        for (int i = 0; i < modalidad.size(); i++) {
            if (modalidad.get(i).getIdModalidad() == idModalidad)
                return i;
        }
        return 0;
    }
    
    private int obtenerPosicionLgac(int idLgac){
        for (int i = 0; i < lgac.size(); i++) {
            if (lgac.get(i).getIdLgac()== idLgac)
                return i;
        }
        return 0;
    }
    
    private void cargarInformacionLGAC(){
        lgac = FXCollections.observableArrayList();
        LgacRespuesta LgacBD=LgacDAO.obtenerInformacionLgac();
        switch(LgacBD.getCodigoRespuesta()){
            case Constantes.ERROR_CONEXION:
                Utilidades.mostrarDialogoSimple("Error de conexion", 
                        "Error en la conexion con la base de datos", 
                        Alert.AlertType.ERROR);
                break;
            case Constantes.ERROR_CONSULTA:
                Utilidades.mostrarDialogoSimple("Error de consulta", 
                        "Por el momento no se pudo obtener la informacion", 
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
        CuerpoAcademicoRespuesta CuerpoAcademicoBD =
                CuerpoAcademicoDAO.obtenerInformacionCuerpoAcademico();
        switch(CuerpoAcademicoBD.getCodigoRespuesta()){
            case Constantes.ERROR_CONEXION:
                Utilidades.mostrarDialogoSimple("Error de conexion", 
                        "Error en la conexion con la base de datos", 
                        Alert.AlertType.ERROR);
                break;
            case Constantes.ERROR_CONSULTA:
                Utilidades.mostrarDialogoSimple("Error de consulta", 
                        "Por el momento no se pudo obtener la informacion", 
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
        ModalidadRespuesta ModalidadBD =
                ModalidadDAO.obtenerInformacionModalidad();
        switch(ModalidadBD.getCodigoRespuesta()){
            case Constantes.ERROR_CONEXION:
                Utilidades.mostrarDialogoSimple("Error de conexion", 
                        "Error en la conexion con la base de datos", 
                        Alert.AlertType.ERROR);
                break;
            case Constantes.ERROR_CONSULTA:
                Utilidades.mostrarDialogoSimple("Error de consulta", 
                        "Por el momento no se pudo obtener la informacion", 
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
                Utilidades.mostrarDialogoSimple("Error de conexion", 
                        "Error en la conexion con la base de datos", 
                        Alert.AlertType.ERROR);
                break;
            case Constantes.ERROR_CONSULTA:
                Utilidades.mostrarDialogoSimple("Error de consulta", 
                        "Por el momento no se pudo obtener la informacion", 
                        Alert.AlertType.ERROR);
                break;
            case Constantes.OPERACION_EXITOSA:
                usuarios.addAll(AcademicoBD.getUsuarios());
                cbDirector.setItems(usuarios);
                break;
        }
    }
    
     private void cargarInformacionEstado(){
        estado = FXCollections.observableArrayList();
        EstadoAnteproyectoRespuesta EstadoBD = EstadoAnteproyectoDAO.
                obtenerInformacionEstadoAnteproyecto();
        switch(EstadoBD.getCodigoRespuesta()){
            case Constantes.ERROR_CONEXION:
                Utilidades.mostrarDialogoSimple("Error de conexion", 
                        "Error en la conexion con la base de datos", 
                        Alert.AlertType.ERROR);
                break;
            case Constantes.ERROR_CONSULTA:
                Utilidades.mostrarDialogoSimple("Error de consulta", 
                        "Por el momento no se pudo obtener la informacion", 
                        Alert.AlertType.ERROR);
                break;
            case Constantes.OPERACION_EXITOSA:
                estado.addAll(EstadoBD.getEstadosAnteproyecto());
                break;
        }
    }
    
}
