/*
* Autor: Luis Angel ElizaLde Arroyo
* Fecha de creación: 13/06/2023
* Descripción: Clase encargada de crear y modificar la informacion de los anteproyectos
*/
package jfxspger.controladores;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import jfxspger.modelo.dao.AnteproyectoDAO;
import jfxspger.modelo.dao.CuerpoAcademicoDAO;
import jfxspger.modelo.dao.DuracionAproximadaDAO;
import jfxspger.modelo.dao.EstadoAnteproyectoDAO;
import jfxspger.modelo.dao.LgacDAO;
import jfxspger.modelo.dao.ModalidadDAO;
import jfxspger.modelo.pojo.Anteproyecto;
import jfxspger.modelo.pojo.CuerpoAcademico;
import jfxspger.modelo.pojo.CuerpoAcademicoRespuesta;
import jfxspger.modelo.pojo.DuracionAprox;
import jfxspger.modelo.pojo.DuracionAproxRespuesta;
import jfxspger.modelo.pojo.EstadoAnteproyecto;
import jfxspger.modelo.pojo.EstadoAnteproyectoRespuesta;
import jfxspger.modelo.pojo.Lgac;
import jfxspger.modelo.pojo.LgacRespuesta;
import jfxspger.modelo.pojo.Modalidad;
import jfxspger.modelo.pojo.ModalidadRespuesta;
import jfxspger.utilidades.Constantes;
import jfxspger.utilidades.SingletonUsuario;
import jfxspger.utilidades.Utilidades;

public class FXMLFormularioAnteproyectoController extends FXMLPrincipalAcademicoController {

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
    private ObservableList<Lgac> lgac;
    private ObservableList<Modalidad> modalidad;
    private ObservableList<CuerpoAcademico> cuerpoAcademico;
    private ObservableList<EstadoAnteproyecto> estado; 
    private ObservableList<DuracionAprox> duracion;
    private Anteproyecto anteproyectoEdicion;
    private boolean esEdicion;
    @FXML
    private Button btnPostularAnteproyecto;
    @FXML
    private Button btnGuardarAnteproyecto1;
    @FXML
    private ComboBox<DuracionAprox> cbDuracionAprox;
    @FXML
    private Label lbCuerpoAcademico;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        validarSeccionesPermitidas();
        cargarInformacionModalidad();
        cargarInformacionCuerpoAcademico(
                SingletonUsuario.getInstancia().getUsuario().getIdcuerpoAcademico());
        cargarInformacionEstado();
        cargarInformacionDuracion();
        cargarInformacionLGAC(SingletonUsuario.getInstancia().getUsuario().
                getIdcuerpoAcademico());
        
        tfCantidadAlumnos.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, 
            String newValue) {
                if (!newValue.matches("\\d*")) {
                     tfCantidadAlumnos.setText(newValue.replaceAll("[^\\d]", ""));
                 }
        }});
        
        btnGuardarAnteproyecto1.setVisible(false);
        
        
        for(int i=0; i<cuerpoAcademico.size();i++){
            if(cuerpoAcademico.get(i).getIdCuerpoAcademico()== 
                    SingletonUsuario.getInstancia().getUsuario().getIdcuerpoAcademico()){
                lbCuerpoAcademico.setText(cuerpoAcademico.get(i).getNombre());
            }else{
                lbCuerpoAcademico.setText("");
            }
        }
        
    }    

    @FXML
    private void clicBtnPostular(ActionEvent event) {
           validarInformacion();
    }
    
    private void boton (boolean esEdicion){
        if(esEdicion==true){
            btnPostularAnteproyecto.setVisible(false);
        }
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
       int posicionModalidad = cbModalidad.getSelectionModel().getSelectedIndex();
       int posicionLGAC = cbLGAC.getSelectionModel().getSelectedIndex();
       int posicionDuracion = cbDuracionAprox.getSelectionModel().getSelectedIndex();
       boolean sonValidos=true;
       
       if(proyectoInvestigacion.trim().isEmpty()){
           tfProyectoInvestigacion.setStyle(Constantes.estiloError);
           sonValidos=false;
       }else{
           if(proyectoInvestigacion.length()>255){
               tfProyectoInvestigacion.setStyle(Constantes.estiloError);
               sonValidos=false;
           }
       }
       
       if(lineaInvestigacion.trim().isEmpty()){
           tfLineaInvestigacion.setStyle(Constantes.estiloError);
           sonValidos=false;
       }else{
           if(lineaInvestigacion.length()>255){
               tfLineaInvestigacion.setStyle(Constantes.estiloError);
               sonValidos=false;
           }
       }
       
       if(nombreTrabajo.trim().isEmpty()){
           tfNombreTrabajo.setStyle(Constantes.estiloError);
           sonValidos=false;
       }else{
           if(nombreTrabajo.length()>255){
               tfNombreTrabajo.setStyle(Constantes.estiloError);
               sonValidos=false;
           }
       }
       
       if(cantidadAlumnos.trim().isEmpty()){
           tfCantidadAlumnos.setStyle(Constantes.estiloError);
           sonValidos=false;
       }else{
           int cantidad = Integer.parseInt(cantidadAlumnos);
           if(cantidad==0 || cantidad>2){
               tfCantidadAlumnos.setStyle(Constantes.estiloError);
               sonValidos=false;
           }
       }
       
       if(requisitos.trim().isEmpty()){
           taRequisitos.setStyle(Constantes.estiloError);
           sonValidos=false;
       }else{
           if(requisitos.length()>255){
               taRequisitos.setStyle(Constantes.estiloError);
               sonValidos=false;
           }
       }
       
       if(descripcionProyecto.trim().isEmpty()){
           taDescripcionProyecto.setStyle(Constantes.estiloError);
           sonValidos=false;
       }else{
           if(descripcionProyecto.length()>3000){
               taDescripcionProyecto.setStyle(Constantes.estiloError);
               sonValidos=false;
           }
       }
       
       if(descripcionTrabajo.trim().isEmpty()){
           taDescripcionTrabajo.setStyle(Constantes.estiloError);
           sonValidos=false;
       }else{
           if(descripcionTrabajo.length()>3000){
               taDescripcionTrabajo.setStyle(Constantes.estiloError);
               sonValidos=false;
           }
       }
       
       if(resultadosEsperados.trim().isEmpty()){
           taResultadosEsperados.setStyle(Constantes.estiloError);
           sonValidos=false;
       }else{
           if(resultadosEsperados.length()>100){
               taResultadosEsperados.setStyle(Constantes.estiloError);
               sonValidos=false;
           }
       }
       
       if(posicionDuracion == -1){
           cbDuracionAprox.setStyle(Constantes.estiloError);
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
       
        
        if(!bibliografiaRecomendada.trim().isEmpty()){
            if(bibliografiaRecomendada.length()>2000){
                taBibliografia.setStyle(Constantes.estiloError);
                sonValidos=false;
            }
        }
        
        if(sonValidos==true){
            int posEstado=0;
            Anteproyecto anteproyectoValido = new Anteproyecto();
            anteproyectoValido.setIdDirector(
                    SingletonUsuario.getInstancia().getUsuario().getIdAcademico());
            anteproyectoValido.setIdCuerpoAcademico(
                    SingletonUsuario.getInstancia().getUsuario().getIdcuerpoAcademico());
            anteproyectoValido.setIdDuracionAproximada(duracion.get(posicionDuracion).
                    getIdDuracionAproximada());
            anteproyectoValido.setIdModalidad(modalidad.get(posicionModalidad).getIdModalidad());
            anteproyectoValido.setIdLgac(lgac.get(posicionLGAC).getIdLgac());
            anteproyectoValido.setProyectoInvestigacion(proyectoInvestigacion);
            anteproyectoValido.setLineaInvestigacion(lineaInvestigacion);
            anteproyectoValido.setNombreTrabajo(nombreTrabajo);
            anteproyectoValido.setRequisitos(requisitos);
            int cantidad = Integer.parseInt(cantidadAlumnos);
            anteproyectoValido.setMaximoAlumnosParticipantes(cantidad);
            anteproyectoValido.setCantidadAlumnosParticipantes(0);
            anteproyectoValido.setDescripcionProyectoInvestigacion(descripcionProyecto);
            anteproyectoValido.setDescripcionTrabajoRecepcional(descripcionTrabajo);
            anteproyectoValido.setResultadosEsperados(resultadosEsperados);
            anteproyectoValido.setIdEstado(estado.get(posEstado).getIdEstadoAnteproyecto());
            LocalDateTime tiempo = LocalDateTime.now();
            String fecha = tiempo.toString();
            anteproyectoValido.setFechaCreacion(fecha);
            if(!bibliografiaRecomendada.isEmpty()){
                anteproyectoValido.setBibliografiaRecomendada(bibliografiaRecomendada);
            }
            if(esEdicion){
                anteproyectoValido.setIdAnteproyecto(anteproyectoEdicion.getIdAnteproyecto());
                actualizarAnteproyecto(anteproyectoValido);
            }else{
                boolean Postular = Utilidades.mostrarDialogoConfirmacion("Postular anteproyecto", 
                        "¿Está seguro de que desea postular el anteproyecto?");
                if(Postular == true){
                    registrarAnteproyecto(anteproyectoValido);
                }
            } 
        }        
    }
    
    private void registrarAnteproyecto(Anteproyecto anteproyectoRegistro){
        int codigoRespuesta = AnteproyectoDAO.guardarAnteproyecto(anteproyectoRegistro);
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
                irAnteproyectos();
                break;
        }
    }
    
     private void actualizarAnteproyecto(Anteproyecto anteproyectoActualizar){
        int codigoRespuesta = AnteproyectoDAO.modificarAnteproyecto(anteproyectoActualizar);
         switch(codigoRespuesta){
            case Constantes.ERROR_CONEXION:
                Utilidades.mostrarDialogoSimple("Error de conexion", 
                        "El anteproyecto no pudo ser actualizado "
                        + "debido a un error en su conexion...", 
                        Alert.AlertType.ERROR);
                break;
            case Constantes.ERROR_CONSULTA:
                Utilidades.mostrarDialogoSimple("Error en la informacion", 
                        "La informacion del anteproyecto no puede ser actualizada, "
                        + "por favor verifica la informacion", 
                        Alert.AlertType.ERROR);
                break;
            case Constantes.OPERACION_EXITOSA:
                 Utilidades.mostrarDialogoSimple("Anteproyecto actualizado", 
                         "La informacion del anteproyecto fue actualizada correctamente", 
                        Alert.AlertType.INFORMATION);
                irAnteproyectos();
                break;
        }
    }
    
    public void inicializarInformacionFormulario(boolean esEdicion, 
            Anteproyecto anteproyectoEdicion){
        this.esEdicion=esEdicion;
        this.anteproyectoEdicion=anteproyectoEdicion;

        if(esEdicion){
            lbTitulo.setText("Editar informacion de anteproyecto");
            cargarInformacionEdicion();
            btnPostularAnteproyecto.setVisible(false);
            btnGuardarAnteproyecto1.setVisible(true);
        }
    }
    
    private void cargarInformacionLGAC(int idCuerpoAcademico){
        lgac = FXCollections.observableArrayList();
        LgacRespuesta LgacBD=LgacDAO.obtenerInformacionLgacEnCuerpoAcademico(idCuerpoAcademico);
        switch(LgacBD.getCodigoRespuesta()){
            case Constantes.ERROR_CONEXION:
                Utilidades.mostrarDialogoSimple("Error de conexion", 
                        "Error en la conexion con la base de datos", 
                        Alert.AlertType.ERROR);
                break;
            case Constantes.ERROR_CONSULTA:
                Utilidades.mostrarDialogoSimple("Error de consulta", 
                        "Por el momento no se pudo obtener la informacion de la LGAC", 
                        Alert.AlertType.ERROR);
                break;
            case Constantes.OPERACION_EXITOSA:
                lgac.addAll(LgacBD.getListaLgac());
                cbLGAC.setItems(lgac);
                break;
        }
    }
    
    private void cargarInformacionCuerpoAcademico(int idCuerpoAcademico){
        cuerpoAcademico = FXCollections.observableArrayList();
        CuerpoAcademicoRespuesta CuerpoAcademicoBD=CuerpoAcademicoDAO.
                obtenerInformacionCuerpoAcademico(idCuerpoAcademico);
        switch(CuerpoAcademicoBD.getCodigoRespuesta()){
            case Constantes.ERROR_CONEXION:
                Utilidades.mostrarDialogoSimple("Error de conexion", 
                        "Error en la conexion con la base de datos", 
                        Alert.AlertType.ERROR);
                break;
            case Constantes.ERROR_CONSULTA:
                Utilidades.mostrarDialogoSimple("Error de consulta", 
                     "Por el momento no se pudo obtener la informacion del cuerpo academico", 
                        Alert.AlertType.ERROR);
                break;
            case Constantes.OPERACION_EXITOSA:
                cuerpoAcademico.addAll(CuerpoAcademicoBD.getCuerposAcademicos());
                break;
        }
    }
    
    private void cargarInformacionModalidad(){
        modalidad = FXCollections.observableArrayList();
        ModalidadRespuesta ModalidadBD=ModalidadDAO.obtenerInformacionModalidad();
        switch(ModalidadBD.getCodigoRespuesta()){
            case Constantes.ERROR_CONEXION:
                Utilidades.mostrarDialogoSimple("Error de conexion", 
                        "Error en la conexion con la base de datos", 
                        Alert.AlertType.ERROR);
                break;
            case Constantes.ERROR_CONSULTA:
                Utilidades.mostrarDialogoSimple("Error de consulta", 
                        "Por el momento no se pudo obtener la informacion de la modalidad", 
                        Alert.AlertType.ERROR);
                break;
            case Constantes.OPERACION_EXITOSA:
                modalidad.addAll(ModalidadBD.getModalidades());
                cbModalidad.setItems(modalidad);
                break;
        }
    }
    
     private void cargarInformacionEstado(){
        estado = FXCollections.observableArrayList();
        EstadoAnteproyectoRespuesta EstadoBD = 
                EstadoAnteproyectoDAO.obtenerInformacionEstadoAnteproyecto();
        switch(EstadoBD.getCodigoRespuesta()){
            case Constantes.ERROR_CONEXION:
                Utilidades.mostrarDialogoSimple("Error de conexion", 
                        "Error en la conexion con la base de datos", 
                        Alert.AlertType.ERROR);
                break;
            case Constantes.ERROR_CONSULTA:
                Utilidades.mostrarDialogoSimple("Error de consulta", 
                        "Por el momento no se pudo obtener la informacion del estado", 
                        Alert.AlertType.ERROR);
                break;
            case Constantes.OPERACION_EXITOSA:
                estado.addAll(EstadoBD.getEstadosAnteproyecto());
                break;
        }
    }
     
    private void cargarInformacionDuracion(){
        duracion = FXCollections.observableArrayList();
        DuracionAproxRespuesta DuracionBD= DuracionAproximadaDAO.obtenerDuracionAproximada();
        switch(DuracionBD.getCodigoRespuesta()){
            case Constantes.ERROR_CONEXION:
                Utilidades.mostrarDialogoSimple("Error de conexion", 
                        "Error en la conexion con la base de datos", 
                        Alert.AlertType.ERROR);
                break;
            case Constantes.ERROR_CONSULTA:
                Utilidades.mostrarDialogoSimple("Error de consulta", 
                        "Por el momento no se pudo obtener la informacion de la duracion", 
                        Alert.AlertType.ERROR);
                break;
            case Constantes.OPERACION_EXITOSA:
                duracion.addAll(DuracionBD.getDuracionAproximada());
                cbDuracionAprox.setItems(duracion);
                break;
        }
    } 
     
     private void cargarInformacionEdicion(){
        int posModalidad = obtenerPosicionComboModalidad(anteproyectoEdicion.getIdModalidad());
        cbModalidad.getSelectionModel().select(posModalidad);
        int posLGAC = obtenerPosicionComboLGAC(anteproyectoEdicion.getIdLgac());
        cbLGAC.getSelectionModel().select(posLGAC);
        int cantAlum = anteproyectoEdicion.getMaximoAlumnosParticipantes();
        String cantidadAlumnos = Integer.toString(cantAlum);
        tfCantidadAlumnos.setText(cantidadAlumnos);
        int posDuracion = obtenerPosicionComboDuracion(
                anteproyectoEdicion.getIdDuracionAproximada());
        cbDuracionAprox.getSelectionModel().select(posDuracion);
        tfProyectoInvestigacion.setText(anteproyectoEdicion.getProyectoInvestigacion());
        tfLineaInvestigacion.setText(anteproyectoEdicion.getLineaInvestigacion());
        tfNombreTrabajo.setText(anteproyectoEdicion.getNombreTrabajo());
        taDescripcionProyecto.setText(anteproyectoEdicion.getDescripcionProyectoInvestigacion());
        taDescripcionTrabajo.setText(anteproyectoEdicion.getDescripcionTrabajoRecepcional());
        taRequisitos.setText(anteproyectoEdicion.getRequisitos());
        taResultadosEsperados.setText(anteproyectoEdicion.getResultadosEsperados());
        if(anteproyectoEdicion.getBibliografiaRecomendada()==null){
            taBibliografia.setText("");
        }else{
            taBibliografia.setText(anteproyectoEdicion.getBibliografiaRecomendada());
        }
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
        cbDuracionAprox.setStyle(Constantes.estiloNormal);
        cbLGAC.setStyle(Constantes.estiloNormal);
        cbModalidad.setStyle(Constantes.estiloNormal);
    }

    @FXML
    private void clicRegresar(ActionEvent event) {
        boolean Salir = Utilidades.mostrarDialogoConfirmacion("Confirmacion", 
                "¿Seguro que deseas salir? No se guardaran los cambios");
        clicIrAnteproyectos(event);
    }
    
    private int obtenerPosicionComboModalidad(int idModalidad){
        for(int i=0; i <modalidad.size(); i++){
            if(modalidad.get(i).getIdModalidad()== idModalidad){
                return i; 
            }
        }
        return 0;
    }
    
    private int obtenerPosicionComboLGAC(int idLgac){
        for(int i=0; i <lgac.size(); i++){
            if(lgac.get(i).getIdLgac()== idLgac){
                return i; 
            }
        }
        return -1;
    }
    
        
    private int obtenerPosicionComboDuracion(int idDuracionAproximada){
        for(int i=0; i <duracion.size(); i++){
            if(duracion.get(i).getIdDuracionAproximada()== idDuracionAproximada){
                return i; 
            }
        }
        return 0;
    }

}
