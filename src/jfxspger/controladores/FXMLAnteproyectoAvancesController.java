/*
* Autor: Luis Ángel Elizalde Arroyo
* Fecha de creación: 13/06/2023
* Descripción: Clase controladora para los avances del anteproyecto
*/
package jfxspger.controladores;

import com.sun.javafx.scene.control.skin.TableHeaderRow;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import jfxspger.modelo.dao.EntregaDAO;
import jfxspger.modelo.pojo.Anteproyecto;
import jfxspger.modelo.pojo.Entrega;
import jfxspger.modelo.pojo.EntregaRespuesta;
import jfxspger.utilidades.Constantes;
import jfxspger.utilidades.Utilidades;

public class FXMLAnteproyectoAvancesController extends FXMLPrincipalAcademicoController {

    @FXML
    private Label lTitulo;
    @FXML
    private TableColumn cTituloActividad;
    @FXML
    private TableColumn cFechaEntrega;
    @FXML
    private Label lbTituloAnteproyecto;
    @FXML
    private TableView<Entrega> tvEntregas;
    private ObservableList<Entrega> entregas;
    private Anteproyecto anteproyecto;

    @Override
    public void initialize(URL url, ResourceBundle rb) {       
        validarSeccionesPermitidas();
    }
    
    public void inicializarInformacion(Anteproyecto anteproyecto){
        this.anteproyecto = anteproyecto;
        configurarTabla();
        cargarInformacionAvances(anteproyecto.getIdAnteproyecto());
        lbTituloAnteproyecto.setText(anteproyecto.getNombreTrabajo());
    }
    
    @FXML
    private void clicIrInfoAnteproyecto(ActionEvent event) {
        try{
            FXMLLoader accesoControlador = new FXMLLoader(jfxspger.
                    JFXSPGER.class.getResource("/jfxspger/vistas/FXMLInfoAnteproyecto.fxml"));
            Parent vista = accesoControlador.load();
            FXMLInfoAnteproyectoController formulario = accesoControlador.getController();
            Scene sceneFormulario = new Scene(vista);
            Stage escenarioPrincipal = (Stage) lTitulo.getScene().getWindow();
            escenarioPrincipal.setTitle("Informacion de anteproyecto");
            escenarioPrincipal.setScene(sceneFormulario);
            formulario.inicializarInformacion(false,anteproyecto);
        }catch(IOException e){
            Utilidades.mostrarDialogoSimple("Error", 
                    "No se puede mostrar la pantalla de informacion de anteproyecto", 
                    Alert.AlertType.ERROR);  
        }
    }

     private void configurarTabla(){
        
        cFechaEntrega.setCellValueFactory(new PropertyValueFactory("fechaEntrega"));
        cTituloActividad.setCellValueFactory(new PropertyValueFactory("tituloActividad"));
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
    
     private void cargarInformacionAvances(int idAnteproyecto){
          entregas = FXCollections.observableArrayList();
        EntregaRespuesta respuestaBD = EntregaDAO.obtenerInformacionEntregasPorAnteproyecto(idAnteproyecto);
        switch(respuestaBD.getCodigoRespuesta()){
             case Constantes.ERROR_CONEXION:
                Utilidades.mostrarDialogoSimple("Sin conexión", 
                        "Lo sentimos, por el momento no hay conexión", 
                        Alert.AlertType.ERROR);
                break;
            case Constantes.ERROR_CONSULTA:
                Utilidades.mostrarDialogoSimple("Error al cargar los datos", 
                        "Hubo un error al cargar los avances del anteproyecto.", 
                        Alert.AlertType.WARNING);
                break;
            case Constantes.OPERACION_EXITOSA:
                entregas.addAll(respuestaBD.getEntregas());
                tvEntregas.setItems(entregas);                
                break;
        }    
     }
    
}
