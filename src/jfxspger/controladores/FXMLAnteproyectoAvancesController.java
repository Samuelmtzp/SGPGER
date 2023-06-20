/*
* Autor: Luis Ángel Elizalde Arroyo
* Fecha de creación: 13/06/2023
* Descripción: Clase controladora para los avances del anteproyecto
*/
package jfxspger.controladores;

import com.sun.javafx.scene.control.skin.TableHeaderRow;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import jfxspger.modelo.dao.EntregaDAO;
import jfxspger.modelo.pojo.Anteproyecto;
import jfxspger.modelo.pojo.Entrega;
import jfxspger.modelo.pojo.EntregaRespuesta;
import jfxspger.utilidades.Constantes;
import jfxspger.utilidades.Utilidades;

public class FXMLAnteproyectoAvancesController implements Initializable {

    @FXML
    private Label lTitulo;
    @FXML
    private TableColumn cNombreActividad;
    @FXML
    private TableColumn cFechaEntrega;
    @FXML
    private TableColumn cFechaCreacion;
    @FXML
    private Label lbTituloAnteproyecto;
    @FXML
    private TableView<Entrega> tvEntregas;
    private ObservableList<Entrega> entregas;

    @Override
    public void initialize(URL url, ResourceBundle rb) {        
    }
    
    public void inicializarInformacion(Anteproyecto anteproyecto){
        configurarTabla();
        cargarInformacionAvances();
        lbTituloAnteproyecto.setText(anteproyecto.getNombreTrabajo());
    }

    @FXML
    private void clicIrPrincipalAcademico(ActionEvent event) {
        Stage escenarioBase = (Stage) lbTituloAnteproyecto.getScene().getWindow();
        escenarioBase.setScene(Utilidades.inicializarEscena(
                "vistas/FXMLPrincipalAcademico.fxml"));
        escenarioBase.setTitle("Ventana Principal");
        escenarioBase.show();           
    }
    
    private void configurarTabla(){
        cNombreActividad.setCellValueFactory(new PropertyValueFactory("titulo"));
        cFechaCreacion.setCellValueFactory(new PropertyValueFactory("fechaCreacion"));
        cFechaEntrega.setCellValueFactory(new PropertyValueFactory("fechaEntrega"));        
                tvEntregas.widthProperty().addListener(new ChangeListener<Number>(){
            @Override
            public void changed(ObservableValue<? extends Number> observable, 
                    Number oldValue, Number newValue) {
                TableHeaderRow header = (TableHeaderRow) tvEntregas.lookup("TableHeaderRow");
                header.reorderingProperty().addListener(new ChangeListener<Boolean>(){
                    @Override
                    public void changed(ObservableValue<? extends Boolean> observable, 
                            Boolean oldValue, Boolean newValue) {
                        header.setReordering(false);
                    }
                });
            }
        });
    }
    
    private void cargarInformacionAvances(){
        entregas = FXCollections.observableArrayList();
        EntregaRespuesta respuestaBD = EntregaDAO.obtenerInformacionEntregas();
        switch(respuestaBD.getCodigoRespuesta()){
            case Constantes.ERROR_CONEXION:
                Utilidades.mostrarDialogoSimple("Sin conexión", 
                        "Lo sentimos, por el momento no hay conexión", 
                        Alert.AlertType.ERROR);
                break;
            case Constantes.ERROR_CONSULTA:
                Utilidades.mostrarDialogoSimple("Error al cargar los datos", 
                        "Hubo un error al cargar la información. Por favor intente más tarde", 
                        Alert.AlertType.WARNING);
                break;
            case Constantes.OPERACION_EXITOSA:
                entregas.addAll(respuestaBD.getEntregas());
                tvEntregas.setItems(entregas);                
                break;
        }
    }

    
    @FXML
    private void clicCerrarSesion(ActionEvent event) {
        if (Utilidades.mostrarDialogoConfirmacion(
                "Cerrar sesión", 
                "¿Está seguro de que desea cerrar sesión?")) {
            irVentanaInicioSesion();
        }
    }
    
    private void irVentanaInicioSesion() {
        Stage escenarioBase = (Stage) lbTituloAnteproyecto.getScene().getWindow();
        escenarioBase.setScene(
                Utilidades.inicializarEscena("vistas/FXMLInicioSesion.fxml"));
        escenarioBase.setTitle("Inicio de sesion");
        escenarioBase.show();
    }


    @FXML
    private void clicIrPropuestas(ActionEvent event) {
    }


    @FXML
    private void clicIrAnteproyectos(ActionEvent event) {
    }

    @FXML
    private void clicIrEstudiantes(ActionEvent event) {
    }

    @FXML
    private void clicIrEntregables(ActionEvent event) {
    }

    @FXML
    private void clicIrRevisiones(ActionEvent event) {
    }
    
}
