/*
* Autor: Luis Angel ElizaLde Arroyo
* Fecha de creación: 22/06/2023
* Descripción: Clase encargada de asignar codirectores a un anteproyecto
*/
package jfxspger.controladores;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import jfxspger.modelo.dao.AnteproyectoDAO;
import jfxspger.modelo.dao.Anteproyecto_CodirectorDAO;
import jfxspger.modelo.dao.UsuarioDAO;
import jfxspger.modelo.pojo.Anteproyecto;
import jfxspger.modelo.pojo.Anteproyecto_Codirector;
import jfxspger.modelo.pojo.Usuario;
import jfxspger.modelo.pojo.UsuarioRespuesta;
import jfxspger.utilidades.Constantes;
import jfxspger.utilidades.Utilidades;


public class FXMLAsignarCodirectoresController extends FXMLPrincipalAcademicoController {

    @FXML
    private Label lbTitulo;
    @FXML
    private TableView<Usuario> tvAcademicosDisponibles;
    @FXML
    private TableColumn columnNombreDisponible;
    @FXML
    private TableColumn columApellidoPaternoDisponible;
    @FXML
    private TableColumn columApellidoMaternoDisponible;
    
    @FXML
    private TableView<Usuario> tvCodirectoresAsignados;
    @FXML
    private TableColumn columnNombreAsignado;
    @FXML
    private TableColumn columApellidoPaternoAsignado;
    @FXML
    private TableColumn columApellidoMaternoAsignado;

    private ObservableList <Usuario> academicosDisponibles;
    private ObservableList <Usuario> academicosAsignados;
  
    private Anteproyecto anteproyecto;
  
    
   
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        validarSeccionesPermitidas();
    }
    
      private void configurarTablas(){
        columnNombreAsignado.setCellValueFactory(new PropertyValueFactory("nombre"));
        columApellidoPaternoAsignado.setCellValueFactory(
                new PropertyValueFactory("apellidoPaterno"));
        columApellidoMaternoAsignado.setCellValueFactory(
                new PropertyValueFactory("apellidoMaterno"));
        columnNombreDisponible.setCellValueFactory(new PropertyValueFactory("nombre"));
                columApellidoPaternoDisponible.setCellValueFactory(new PropertyValueFactory(
                "apellidoPaterno"));
        columApellidoMaternoDisponible.setCellValueFactory(new PropertyValueFactory(
                "apellidoMaterno"));
    }
      
    private void cargarDatosTablaCodirectoresAsignados(Anteproyecto anteproyectoAsignados) {
        academicosAsignados = FXCollections.observableArrayList();
        UsuarioRespuesta respuestaBD = UsuarioDAO.consultarCodirectoresEnAnteproyecto(
                anteproyectoAsignados.getIdAnteproyecto());
        if(respuestaBD != null){
        switch (respuestaBD.getCodigoRespuesta()) {
            case Constantes.ERROR_CONEXION:
                Utilidades.mostrarDialogoSimple("Sin conexión", 
                    "Lo sentimos, por el momento no hay conexión para poder "
                    + "cargar la información de los codirectores asignados al anteproyecto", 
                    Alert.AlertType.ERROR);
            break;
            case Constantes.ERROR_CONSULTA:
                Utilidades.mostrarDialogoSimple("Error al cargar los datos", 
                    "Hubo un error al cargar información de los codirectores "
                    + "asignados al anteproyecto, por favor inténtelo más tarde", 
                    Alert.AlertType.WARNING);
            break;
            case Constantes.OPERACION_EXITOSA:
                academicosAsignados.addAll(respuestaBD.getUsuarios());
                tvCodirectoresAsignados.setItems(academicosAsignados);
            break;
          }
        }
    }
    
     private void cargarDatosTablaAcademicosDisponibles(Anteproyecto anteproyectoDisponible){
        academicosDisponibles = FXCollections.observableArrayList();
        UsuarioRespuesta respuestaBD = UsuarioDAO.consultarAcademicosDisponible(
                anteproyectoDisponible.getIdAnteproyecto());
        if(respuestaBD != null){
        switch (respuestaBD.getCodigoRespuesta()) {
            case Constantes.ERROR_CONEXION: 
                    Utilidades.mostrarDialogoSimple("Sin conexión", 
              "Lo sentimos, por el momento no hay conexión para poder cargar la información", 
                    Alert.AlertType.ERROR);
                break;
            case Constantes.ERROR_CONSULTA:
                    Utilidades.mostrarDialogoSimple("Error al cargar los datos", 
                    "Hubo un error al cargar a las academicos disponibles,"
                            + " por favor inténtelo más tarde", 
                    Alert.AlertType.WARNING);
                break;
            case Constantes.OPERACION_EXITOSA:
                    academicosDisponibles.addAll(respuestaBD.getUsuarios());
                    tvAcademicosDisponibles.setItems(academicosDisponibles);
                break;
            }
        }
    }
     
     private void asignarAcademicoCodirector(Anteproyecto_Codirector nuevoAcademico_Anteproyecto) {
        int respuesta = 
              Anteproyecto_CodirectorDAO.guardarAcademico_Anteproyecto(nuevoAcademico_Anteproyecto);
        switch(respuesta) {
            case Constantes.ERROR_CONEXION:
                Utilidades.mostrarDialogoSimple("Error de conexion", "El academico " + 
                        "no pudo ser asignado como codirector al anteproyecto debido a un error "
                        + "de conexión.", 
                        Alert.AlertType.ERROR);
            break;
            case Constantes.ERROR_CONSULTA:
            Utilidades.mostrarDialogoSimple("Error al asignar academico como codirector",
                        "No se puede asignar al academico como director,"
                        + "por favor inténtelo más tarde", 
                        Alert.AlertType.WARNING);
            break;
            case Constantes.OPERACION_EXITOSA:
                 Utilidades.mostrarDialogoSimple("Academico asignado como codirector",
                        "Se ha asignado al academico seleccionado como codirector", 
                        Alert.AlertType.INFORMATION);
                cargarDatosTablaCodirectoresAsignados(anteproyecto);
                cargarDatosTablaAcademicosDisponibles(anteproyecto);
                actualizarCantidadCodirectoresAnteproyecto(
                        anteproyecto.getIdAnteproyecto());
            break;
        }
    }
     
     private void eliminarCodirectorDeAnteproyecto(Anteproyecto_Codirector codirector) {
        int respuesta = Anteproyecto_CodirectorDAO.eliminarAcademico_Anteproyecto(
              codirector.getIdAnteproyecto(), codirector.getIdCodirector());
        switch(respuesta) { 
            case Constantes.ERROR_CONEXION:
                Utilidades.mostrarDialogoSimple("Error de conexion", "El codirector " + 
                        "no pudo eliminarse del anteproyecto debido a un error de conexión.", 
                        Alert.AlertType.ERROR);
            break;
            case Constantes.ERROR_CONSULTA:
           Utilidades.mostrarDialogoSimple("Error al eliminar al codirector del anteproyecto",
                        "No se puede eliminar al codirector del anteproyecto,"
                        + "por favor inténtelo más tarde", 
                        Alert.AlertType.WARNING);
            break;
            case Constantes.OPERACION_EXITOSA:
                 Utilidades.mostrarDialogoSimple("Codirector eliminado del anteproyecto",
                        "Se ha eliminado al academico seleccionado del anteproyecto", 
                        Alert.AlertType.INFORMATION);
                cargarDatosTablaCodirectoresAsignados(anteproyecto);
                cargarDatosTablaAcademicosDisponibles(anteproyecto);
                actualizarEliminarCantidadCodirectoresAnteproyecto(
                        anteproyecto.getIdAnteproyecto());
            break;
        }
    } 
     
     public void inicializarInformacion(Anteproyecto anteproyectoSelected){
        this.anteproyecto=anteproyectoSelected;
        configurarTablas();
        cargarDatosTablaCodirectoresAsignados(anteproyectoSelected);
        cargarDatosTablaAcademicosDisponibles(anteproyectoSelected);
    } 
     
     private void actualizarCantidadCodirectoresAnteproyecto(int idAnteproyecto) {
      int respuesta = AnteproyectoDAO.incrementarContadorCodirectoresEnAnteproyecto(idAnteproyecto);
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
    
     private void actualizarEliminarCantidadCodirectoresAnteproyecto(int idAnteproyecto) {
      int respuesta = AnteproyectoDAO.decrementarContadorCodirectoresEnAnteproyecto(idAnteproyecto);
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

    @FXML
    private void clicBtnAsignarEstudiantes(ActionEvent event) {
        if(anteproyecto.getCantidadCodirectores() < anteproyecto.getCantidadMaximaCodirectores()){ 
             int posicionAcademicoDisponible = tvAcademicosDisponibles.
                    getSelectionModel().getSelectedIndex();
             if(posicionAcademicoDisponible!=-1){
                 Anteproyecto_Codirector codirector = new Anteproyecto_Codirector();
                 codirector.setIdAnteproyecto(anteproyecto.getIdAnteproyecto());
                 codirector.setIdCodirector(
       tvAcademicosDisponibles.getSelectionModel().getSelectedItem().getIdAcademico());
                 asignarAcademicoCodirector(codirector);
                 anteproyecto.setCantidadCodirectores(anteproyecto.getCantidadCodirectores()+1);
             }else{
                  Utilidades.mostrarDialogoSimple("Selección necesaria", 
                        "Para asignar un codirector al anteproyecto, debe seleccionarlo "
                        + "previamente de la tabla de academicos disponibles", 
                        Alert.AlertType.WARNING);
             }
        }else{
               Utilidades.mostrarDialogoSimple("Cupo lleno", 
                    "No es posible agregar más codirectores debido a que se ha alcanzado el "
                    + "cupo límite.", 
                    Alert.AlertType.WARNING);
        }
    }

    @FXML
    private void clicBtnQuitar(ActionEvent event) {
          int posicionCodirector = tvCodirectoresAsignados.
                    getSelectionModel().getSelectedIndex();
        if (posicionCodirector != -1) {
            Anteproyecto_Codirector codirector = new Anteproyecto_Codirector();
            codirector.setIdCodirector(
         tvCodirectoresAsignados.getSelectionModel().getSelectedItem().getIdAcademico());
            codirector.setIdAnteproyecto(anteproyecto.getIdAnteproyecto());
            boolean borrarRegistro = Utilidades.mostrarDialogoConfirmacion(
                    "Eliminar codirector del anteproyecto", 
                    "¿Estás seguro de que deseas eliminar al codirector del anteproyecto?");
            if(borrarRegistro){
                eliminarCodirectorDeAnteproyecto(codirector);
                anteproyecto.setCantidadAlumnosParticipantes(
                        anteproyecto.getCantidadAlumnosParticipantes() - 1);  
            }
        } else
            Utilidades.mostrarDialogoSimple("Selecciona un academico", 
                   "Selecciona a un academico en la tabla de academicos asignados", 
                   Alert.AlertType.WARNING);
        
    }

    @FXML
    private void clicRegresar(ActionEvent event) {
        clicIrAnteproyectos(event);
    }

}
