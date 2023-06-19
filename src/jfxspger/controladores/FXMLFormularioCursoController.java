/*
* Autor: Samuel Martínez Pazos
* Fecha de creación: 13/06/2023
* Descripción: Clase controladora de formulario de curso
*/
package jfxspger.controladores;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import jfxspger.modelo.dao.CursoDAO;
import jfxspger.modelo.dao.ExperienciaEducativaDAO;
import jfxspger.modelo.dao.PeriodoEscolarDAO;
import jfxspger.modelo.dao.UsuarioDAO;
import jfxspger.modelo.pojo.Curso;
import jfxspger.modelo.pojo.ExperienciaEducativa;
import jfxspger.modelo.pojo.ExperienciaEducativaRespuesta;
import jfxspger.modelo.pojo.PeriodoEscolar;
import jfxspger.modelo.pojo.PeriodoRespuesta;
import jfxspger.modelo.pojo.Usuario;
import jfxspger.modelo.pojo.UsuarioRespuesta;
import jfxspger.utilidades.Constantes;
import jfxspger.utilidades.Utilidades;

public class FXMLFormularioCursoController extends FXMLAdminCursosController {

    @FXML
    private Label lbTitulo;
    @FXML
    private ComboBox<ExperienciaEducativa> cbExperienciaEducativa;
    @FXML
    private ComboBox<Usuario> cbProfesor;
    @FXML
    private ComboBox<PeriodoEscolar> cbPeriodoEscolar;
    @FXML
    private TextField tfNrc;
    @FXML
    private TextField tfBloque;
    @FXML
    private TextField tfSeccion;
    @FXML
    private TextField tfCupo;
    @FXML
    private Label lbNrcNoDisponible;
    private ObservableList<ExperienciaEducativa> experienciasEducativas;
    private ObservableList<PeriodoEscolar> periodosEscolares;
    private ObservableList<Usuario> profesores;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarExperienciasEducativas();
        cargarPeriodosEscolares();
        cargarProfesores();
    }        
    
    private void cargarExperienciasEducativas() {
        experienciasEducativas = FXCollections.observableArrayList();
        ExperienciaEducativaRespuesta experienciasEducativasRespuesta = 
                ExperienciaEducativaDAO.obtenerInformacionExperienciaEducativa();
        if (experienciasEducativasRespuesta.getCodigoRespuesta() == Constantes.OPERACION_EXITOSA){ 
            experienciasEducativas.addAll(
                    experienciasEducativasRespuesta.getExperienciasEducativas());
            cbExperienciaEducativa.setItems(experienciasEducativas);
        } else {
            Utilidades.mostrarDialogoSimple("Error al cargar", 
                    "No se pudo cargar la información de las experiencias educativas", 
                    Alert.AlertType.ERROR);
        }
    }
    
    private void cargarPeriodosEscolares() {
        periodosEscolares = FXCollections.observableArrayList();
        PeriodoRespuesta periodosRespuesta = 
                PeriodoEscolarDAO.obtenerInformacionPeriodos();
        if (periodosRespuesta.getCodigoRespuesta() == Constantes.OPERACION_EXITOSA){ 
            periodosEscolares.addAll(
                    periodosRespuesta.getPeriodos());
            cbPeriodoEscolar.setItems(periodosEscolares);
        } else {
            Utilidades.mostrarDialogoSimple("Error al cargar", 
                    "No se pudo cargar la información de los periodos escolares", 
                    Alert.AlertType.ERROR);
        }
    }
    
    private void cargarProfesores() {
        profesores = FXCollections.observableArrayList();
        UsuarioRespuesta academicosRespuesta = 
                UsuarioDAO.obtenerInformacionAcademicos();
        if (academicosRespuesta.getCodigoRespuesta() == Constantes.OPERACION_EXITOSA){ 
            profesores.addAll(
                    academicosRespuesta.getUsuarios());
            cbProfesor.setItems(profesores);
        } else {
            Utilidades.mostrarDialogoSimple("Error al cargar", 
                    "No se pudo cargar la información de los profesores", 
                    Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void clicIrAdminCursos(ActionEvent event) {
        if (Utilidades.mostrarDialogoConfirmacion(
                "Confirmación de regreso", 
                "¿Está seguro de que desea regresar a la ventana de administración de cursos? " + 
                "La información ingresada en el formulario se descartará")) {
            irAdminCursos();
        }
    }
    
    private void irAdminCursos() {
        Stage escenarioBase = (Stage) lbTitulo.getScene().getWindow();
        escenarioBase.setScene(
                Utilidades.inicializarEscena("vistas/FXMLAdminCursos.fxml"));
        escenarioBase.setTitle("Administración cursos");
        escenarioBase.show();
    }


    @FXML
    private void clicBtnCancelar(ActionEvent event) {
        if (mostrarDialogoConfirmacionCancelacion()) {
            irAdminCursos();
        }
    }
    
    private boolean mostrarDialogoConfirmacionCancelacion() {
        return Utilidades.mostrarDialogoConfirmacion(
                "Confirmación de cancelación", 
                "¿Está seguro de que desea cancelar el registro del curso? " + 
                "La información ingresada en el formulario se descartará");
    }
    
    @FXML
    private void clicBtnRegistrarCurso(ActionEvent event) {
        validarCamposRegistro();
    }
    
    private void validarCamposRegistro(){
        establecerEstiloNormal();
        boolean datosValidos = true;
        
        int posicionExperienciaEducativa = cbExperienciaEducativa.
                getSelectionModel().getSelectedIndex();
        int posicionPeriodo = cbPeriodoEscolar.getSelectionModel().getSelectedIndex();
        int posicionProfesor = cbProfesor.getSelectionModel().getSelectedIndex();
        String seccion = tfSeccion.getText();

        
        if (tfNrc.getLength() == 0) {
            tfNrc.setStyle(Constantes.estiloError);
            datosValidos = false;
        } else if (!esNrcDisponible(Integer.parseInt(tfNrc.getText()))) {
            lbNrcNoDisponible.setText("NRC no disponible");
            tfNrc.setStyle(Constantes.estiloError);
            datosValidos = false;
                
        }
        
        if (tfBloque.getLength() == 0) {
            tfBloque.setStyle(Constantes.estiloError);
            datosValidos = false;
        }
        
        if (seccion.trim().isEmpty()) {
            tfSeccion.setStyle(Constantes.estiloError);
            datosValidos = false;
        } else if (seccion.length() > 255) {
            tfSeccion.setStyle(Constantes.estiloError);
            datosValidos = false;
        }
        
        if(tfCupo.getLength() == 0) {
            tfCupo.setStyle(Constantes.estiloError);
            datosValidos = false;
        }
        
        if (posicionExperienciaEducativa == -1) {
            cbExperienciaEducativa.setStyle(Constantes.estiloError);
            datosValidos = false;
        }
        
        if (posicionPeriodo == -1) {
            cbPeriodoEscolar.setStyle(Constantes.estiloError);
            datosValidos = false;
        }
        
        if (posicionProfesor == -1) {
            cbProfesor.setStyle(Constantes.estiloError);
            datosValidos = false;
        }
        
        if (datosValidos) {
            int nrc = Integer.parseInt(tfNrc.getText());
            int bloque = Integer.parseInt(tfBloque.getText());
            int cupo  = Integer.parseInt(tfCupo.getText());
            int idExperienciaEducativa = experienciasEducativas.get(
                    posicionExperienciaEducativa).getIdExperienciaEducativa();
            int idPeriodo = periodosEscolares.get(
                    posicionPeriodo).getIdPeriodoEscolar();
            int idProfesor = profesores.get(
                    posicionProfesor).getIdAcademico();
            Curso cursoValidado = new Curso();
            cursoValidado.setBloque(bloque);
            cursoValidado.setCupo(cupo);
            cursoValidado.setIdExperienciaEducativa(idExperienciaEducativa);
            cursoValidado.setIdPeriodo(idPeriodo);
            cursoValidado.setIdProfesor(idProfesor);
            cursoValidado.setNrc(nrc);
            cursoValidado.setSeccion(seccion);
            
            registrarCurso(cursoValidado);
        }
    }

    private boolean esNrcDisponible(int nrcVerificacion) {
        int coincidencias = CursoDAO.verificarDisponibilidadNrc(nrcVerificacion);
        return coincidencias == 0;
    }    
        
    
    private void establecerEstiloNormal() {
        cbExperienciaEducativa.setStyle(Constantes.estiloNormal);
        cbPeriodoEscolar.setStyle(Constantes.estiloNormal);
        cbProfesor.setStyle(Constantes.estiloNormal);
        tfNrc.setStyle(Constantes.estiloNormal);
        tfBloque.setStyle(Constantes.estiloNormal);
        tfSeccion.setStyle(Constantes.estiloNormal);
        tfCupo.setStyle(Constantes.estiloNormal);
        lbNrcNoDisponible.setText("");
    }
    
    private void registrarCurso(Curso cursoRegistro) {
        int respuesta = CursoDAO.guardarCurso(cursoRegistro);
        
        switch(respuesta){
            case Constantes.ERROR_CONEXION:
                Utilidades.mostrarDialogoSimple("Error de conexion", "El curso " + 
                        "no pudo ser guardado debido a un error de conexión.", 
                        Alert.AlertType.ERROR);
            break;
            case Constantes.ERROR_CONSULTA:
                Utilidades.mostrarDialogoSimple("Error en la información",
                        "La información del curso no puede ser guardada, "
                       + "por favor inténtelo más tarde." ,
                        Alert.AlertType.WARNING);
            break;
            case Constantes.OPERACION_EXITOSA:
                Utilidades.mostrarDialogoSimple("Curso registrado", 
                        "La información del curso fue guardada correctamente", 
                        Alert.AlertType.INFORMATION);
                irAdminCursos();
            break;
        }
    }


    @FXML
    protected void clicIrCursos(ActionEvent event) {
        if (mostrarDialogoConfirmacionCancelacion()) {
            super.clicIrCursos(event);
        }
    }

    @FXML
    protected void clicIrLgac(ActionEvent event) {
        if (mostrarDialogoConfirmacionCancelacion()) {
            super.clicIrLgac(event);
        }
    }

    @FXML
    protected void clicIrUsuarios(ActionEvent event) {
        if (mostrarDialogoConfirmacionCancelacion()) {
            super.clicIrUsuarios(event);
        }
    }

    @FXML
    protected void clicIrCuerposAcademicos(ActionEvent event) {
        if (mostrarDialogoConfirmacionCancelacion()) {
            super.clicIrCuerposAcademicos(event);
        }
    }
    
    @FXML
    private void permitirInputSoloNumeros(KeyEvent event) {
        String entrada = event.getCharacter();
        if (!".0123456789".contains(entrada)) 
            event.consume();
    }

}
