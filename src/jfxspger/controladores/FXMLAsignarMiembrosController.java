/*
* Autor: Samuel Martínez Pazos
* Fecha de creación: 20/06/2023
* Descripción: Clase controladora para agregar miembros a un cuerpo académico
*/

package jfxspger.controladores;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import jfxspger.modelo.dao.AcademicoDAO;
import jfxspger.modelo.dao.CuerpoAcademicoDAO;
import jfxspger.modelo.dao.UsuarioDAO;
import jfxspger.modelo.pojo.CuerpoAcademico;
import jfxspger.modelo.pojo.Usuario;
import jfxspger.modelo.pojo.UsuarioRespuesta;
import jfxspger.utilidades.Constantes;
import jfxspger.utilidades.Utilidades;

public class FXMLAsignarMiembrosController extends FXMLPrincipalAdministradorController {

    @FXML
    private Label lbTitulo;
    @FXML
    private TableColumn columnNombreDisponible;
    @FXML
    private TableColumn columApellidoPaternoDisponible;
    @FXML
    private TableColumn columApellidoMaternoDisponible;
    @FXML
    private TableColumn columnNombreEstudiante;
    @FXML
    private TableColumn columApellidoPaterno;
    @FXML
    private TableColumn columApellidoMaterno;
    @FXML
    private TableView<Usuario> tvMiembros;
    @FXML
    private TableView<Usuario> tvAcademicosDisponibles;
    private ObservableList<Usuario> miembros;
    private ObservableList<Usuario> academicosDisponibles;
    private CuerpoAcademico cuerpoAcademico;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }   
    
    public void inicializarInformacion(CuerpoAcademico cuerpoAcademico) {
        this.cuerpoAcademico = cuerpoAcademico;
        configurarTablas();
        cargarDatosTablaMiembros();
        cargarDatosTablaAcademicosDisponibles();
    }
    
    private void configurarTablas() {
        columnNombreEstudiante.setCellValueFactory(new PropertyValueFactory("nombre"));
        columApellidoPaterno.setCellValueFactory(
                new PropertyValueFactory("apellidoPaterno"));
        columApellidoMaterno.setCellValueFactory(
                new PropertyValueFactory("apellidoMaterno"));
        columnNombreDisponible.setCellValueFactory(new PropertyValueFactory("nombre"));
        columApellidoPaternoDisponible.setCellValueFactory(new PropertyValueFactory(
                "apellidoPaterno"));
        columApellidoMaternoDisponible.setCellValueFactory(new PropertyValueFactory(
                "apellidoMaterno"));
    } 
     
    private void cargarDatosTablaMiembros() {
        miembros = FXCollections.observableArrayList();
        UsuarioRespuesta respuestaMiembros = UsuarioDAO.
                obtenerInformacionAcademicosEnCuerpoAcademico(
                cuerpoAcademico.getIdCuerpoAcademico());
        if (respuestaMiembros != null) {
            switch (respuestaMiembros.getCodigoRespuesta()) {
            case Constantes.ERROR_CONEXION:
                Utilidades.mostrarDialogoSimple("Sin conexión", 
                    "Lo sentimos, por el momento no hay conexión para poder "
                    + "cargar la información de los miembros del cuerpo académico", 
                    Alert.AlertType.ERROR);
            break;
            case Constantes.ERROR_CONSULTA:
                Utilidades.mostrarDialogoSimple("Error al cargar los datos", 
                    "Hubo un error al cargar información de los miembros del "
                    + "cuerpo académido, por favor inténtelo más tarde", 
                    Alert.AlertType.WARNING);
            break;
            case Constantes.OPERACION_EXITOSA:
                miembros.addAll(respuestaMiembros.getUsuarios());
                tvMiembros.setItems(miembros);
            break;
          }
        }
    }

    private void cargarDatosTablaAcademicosDisponibles() {
        academicosDisponibles = FXCollections.observableArrayList();
        UsuarioRespuesta respuestaBD = UsuarioDAO.obtenerInformacionAcademicosNoMiembrosDeCA();
        if (respuestaBD != null) {
            switch (respuestaBD.getCodigoRespuesta()) {
            case Constantes.ERROR_CONEXION: 
                    Utilidades.mostrarDialogoSimple("Sin conexión", 
                    "Lo sentimos, por el momento no hay conexión para poder cargar la información", 
                    Alert.AlertType.ERROR);
                break;
            case Constantes.ERROR_CONSULTA:
                    Utilidades.mostrarDialogoSimple("Error al cargar los datos", 
                    "Hubo un error al cargar la información, por favor inténtelo más tarde", 
                    Alert.AlertType.WARNING);
                break;
            case Constantes.OPERACION_EXITOSA:
                    academicosDisponibles.addAll(respuestaBD.getUsuarios());
                    tvAcademicosDisponibles.setItems(academicosDisponibles);
                break;
            }
        }
    }

    @FXML
    private void clicBtnAgregarMiembro(ActionEvent event) {
        int posicionAcademicoDisponible = tvAcademicosDisponibles.
                getSelectionModel().getSelectedIndex();
        if (posicionAcademicoDisponible != -1) {
            Usuario academicoSeleccionado = tvAcademicosDisponibles.
                    getSelectionModel().getSelectedItem();
            agregarMiembro(academicoSeleccionado.getIdAcademico());
        } else {
            Utilidades.mostrarDialogoSimple("Selecciona un académico", 
                    "Selecciona a un académico en la tabla de académicos disponibles", 
                    Alert.AlertType.WARNING);
        }
    }
    
    private void agregarMiembro(int idAcademico) {
        int respuesta = AcademicoDAO.agregarAcademicoACuerpoAcademico(idAcademico, 
                cuerpoAcademico.getIdCuerpoAcademico());
        switch (respuesta) { 
            case Constantes.ERROR_CONEXION:
                Utilidades.mostrarDialogoSimple("Error de conexion", "El académico " + 
                        "no pudo agregarse al cuerpo académico debido a un error de conexión.", 
                        Alert.AlertType.ERROR);
            break;
            case Constantes.ERROR_CONSULTA:
           Utilidades.mostrarDialogoSimple("Error al agregar al académico al cuerpo académico",
                        "No se puede agregar al académico al cuerpo académico,"
                        + "por favor inténtelo más tarde", 
                        Alert.AlertType.WARNING);
            break;
            case Constantes.OPERACION_EXITOSA:
                 Utilidades.mostrarDialogoSimple("Académico agregado al cuerpo académico",
                        "Se ha agregado al académico al cuerpo académico", 
                        Alert.AlertType.INFORMATION);
                cargarDatosTablaMiembros();
                cargarDatosTablaAcademicosDisponibles();
            break;
        }
    }

    @FXML
    private void clicBtnRemoverMiembro(ActionEvent event) {
        int posicionMiembro = tvMiembros.getSelectionModel().getSelectedIndex();
        if (posicionMiembro != -1) {
            Usuario miembroSeleccionado = tvMiembros.getSelectionModel().getSelectedItem();
            boolean borrarRegistro = Utilidades.mostrarDialogoConfirmacion(
                    "Remover miembro de cuerpo académico", 
                    "¿Estás seguro de que deseas remover al miembro del cuerpo académico?");
            if (borrarRegistro) {
                removerMiembro(miembroSeleccionado.getIdAcademico());
                if (miembroSeleccionado.getIdAcademico() == 
                        cuerpoAcademico.getIdResponsable()) {
                    removerResponsableDeCuerpoAcademico(cuerpoAcademico.getIdCuerpoAcademico());
                }
            }
        } else {
            Utilidades.mostrarDialogoSimple("Selecciona un miembro", 
                    "Selecciona a un miembro en la tabla de miembros del cuerpo académico", 
                    Alert.AlertType.WARNING);
        }
    }    
    
    private void removerMiembro(int idAcademico) {
        int respuesta = AcademicoDAO.removerAcademicoDeCuerpoAcademico(idAcademico);
        switch (respuesta) { 
            case Constantes.ERROR_CONEXION:
                Utilidades.mostrarDialogoSimple("Error de conexion", "El miembro " + 
                        "no pudo removerse del cuerpo académico debido a un error de conexión.", 
                        Alert.AlertType.ERROR);
            break;
            case Constantes.ERROR_CONSULTA:
           Utilidades.mostrarDialogoSimple("Error al remover el miembro del cuerpo académico",
                        "No se puede remover el miembro del cuerpo académico,"
                        + "por favor inténtelo más tarde", 
                        Alert.AlertType.WARNING);
            break;
            case Constantes.OPERACION_EXITOSA:
                 Utilidades.mostrarDialogoSimple("Miembro removido del cuerpo académico",
                        "Se ha removido al miembro del cuerpo académico", 
                        Alert.AlertType.INFORMATION);
                cargarDatosTablaMiembros();
                cargarDatosTablaAcademicosDisponibles();
            break;
        }
    }
    
    private void removerResponsableDeCuerpoAcademico(int idCuerpoAcademico) {
        int respuesta = CuerpoAcademicoDAO.removerResponsableDeCuerpoAcademico(
                            idCuerpoAcademico);
        switch (respuesta) { 
            case Constantes.ERROR_CONEXION:
                Utilidades.mostrarDialogoSimple("Error de conexión", "El responsable " + 
                        "no pudo removerse del cuerpo académico debido a un error de conexión.", 
                        Alert.AlertType.ERROR);
            break;
            case Constantes.ERROR_CONSULTA:
           Utilidades.mostrarDialogoSimple("Error al remover el responsable del cuerpo académico",
                        "No se puede remover al responsable del cuerpo académico,"
                        + "por favor inténtelo más tarde", 
                        Alert.AlertType.WARNING);
            break;
            case Constantes.OPERACION_EXITOSA:
                this.cuerpoAcademico.setIdResponsable(-1);
                cargarDatosTablaMiembros();
                cargarDatosTablaAcademicosDisponibles();
            break;
        }
    }

    @FXML
    private void clicIrModificacionCuerpoAcademico(ActionEvent event) {
        irFormularioCuerpoAcademico(true, this.cuerpoAcademico);
    }
    
    private void irFormularioCuerpoAcademico(boolean esEdicion, 
            CuerpoAcademico cuerpoAcademico) {
        try{
            FXMLLoader accesoControlador = new FXMLLoader(jfxspger.
                    JFXSPGER.class.getResource(
                    "/jfxspger/vistas/FXMLFormularioCuerpoAcademico.fxml"));
            Parent vista = accesoControlador.load();
            FXMLFormularioCuerpoAcademicoController formulario = accesoControlador.getController();
            Scene sceneFormulario = new Scene(vista);
            Stage escenarioPrincipal = (Stage) lbTitulo.getScene().getWindow();
            escenarioPrincipal.setTitle("Formulario de cuerpo académico");
            escenarioPrincipal.setScene(sceneFormulario);
            formulario.inicializarInformacionFormulario(true, cuerpoAcademico);
        } catch (IOException e) {
            Utilidades.mostrarDialogoSimple("Error", 
                    "No se puede mostrar la pantalla de formulario de cuerpo academico", 
                    Alert.AlertType.ERROR);  
        }
    }
    
}
