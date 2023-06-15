/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package jfxspger.controladores;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import jfxspger.modelo.dao.LgacDAO;
import jfxspger.modelo.pojo.Lgac;
import jfxspger.utilidades.Constantes;
import jfxspger.utilidades.Utilidades;

/**
 * FXML Controller class
 *
 * @author king_
 */
public class FXMLLgacFormularioController extends FXMLPrincipalAdministradorController {

    /**
     * Initializes the controller class.
     */
    
    private Lgac lgacEdicion;
    private boolean esEdicion;
    @FXML
    private Label lbTitulo;
    @FXML
    private TextField tfNombre;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
      public void inicializarInformacionFormulario(boolean esEdicion, Lgac lgacEdicion){
        this.esEdicion=esEdicion;
        this.lgacEdicion=lgacEdicion;
        // TO DO  
        if(esEdicion){
            lbTitulo.setText("Editar informacion de LGAC");
            cargarInformacionEdicion();
        }
    }
      
    private void cargarInformacionEdicion(){
        tfNombre.setText(lgacEdicion.getNombre());
    }  

    @FXML
    private void clicBtnRegresar(ActionEvent event) {
        regresar();
    }

    @FXML
    private void clicBtnGuardar(ActionEvent event) {
        validarInformacion();
    }
    
    private void validarInformacion(){
        tfNombre.setStyle(Constantes.estiloNormal);
        boolean datosValidos=true;
        String nombre=tfNombre.getText();
        
        if(nombre.isEmpty()){
            tfNombre.setStyle(Constantes.estiloError);
            datosValidos=false;
        }
        
        if(datosValidos==true){
            Lgac lgacValido= new Lgac();
            lgacValido.setNombre(nombre);
            if(esEdicion){
                lgacValido.setIdLgac(lgacEdicion.getIdLgac());
                actualizarLGAC(lgacValido);
            }else{
                registrarLGAC(lgacValido);
            }
        }
    }
    
    private void actualizarLGAC(Lgac lgacActualizar){
        int codigoRespuesta = LgacDAO.modificarLgac(lgacActualizar);
         switch(codigoRespuesta){
            case Constantes.ERROR_CONEXION:
                Utilidades.mostrarDialogoSimple("Error de conexion", "La LGAC no pudo ser actualizada debido a un error en su conexion...", 
                        Alert.AlertType.ERROR);
                break;
            case Constantes.ERROR_CONSULTA:
                Utilidades.mostrarDialogoSimple("Error en la informacion", "La informacion de la LGAC no puede ser actualizada, por favor verifica la informacion", 
                        Alert.AlertType.ERROR);
                break;
            case Constantes.OPERACION_EXITOSA:
                 Utilidades.mostrarDialogoSimple("LGAC actualizado", "La informacion de la LGAC fue actualizada correctamente", 
                        Alert.AlertType.INFORMATION);
                 //TO DO Regresar
                 regresar();
                break;
        }
    }
    
    private void registrarLGAC(Lgac lgacRegistro){
        int codigoRespuesta = LgacDAO.guardarLgac(lgacRegistro);
        switch(codigoRespuesta){
            case Constantes.ERROR_CONEXION:
                Utilidades.mostrarDialogoSimple("Error de conexion", "La LGAC no pudo ser guardadp debido a un error en su conexión...", 
                        Alert.AlertType.ERROR);
                break;
            case Constantes.ERROR_CONSULTA:
                Utilidades.mostrarDialogoSimple("Error en la información", "La información de la LGAC no puede ser guardada, por favor verifique su información", 
                        Alert.AlertType.WARNING);
                break;
            case Constantes.OPERACION_EXITOSA:
                Utilidades.mostrarDialogoSimple("LGAC registrada", "La información de la LGAC fue guardada correctamente", 
                        Alert.AlertType.INFORMATION);
                //TO DO confirmacion
                regresar();
                break;
        }
    }
    
    private void regresar(){
        Stage escenarioBase = (Stage) lbTitulo.getScene().getWindow();
        escenarioBase.setScene(
                Utilidades.inicializarEscena("vistas/FXMLAdminLgac.fxml"));
        escenarioBase.setTitle("Administración LGAC");
        escenarioBase.show();
    }
    
}
