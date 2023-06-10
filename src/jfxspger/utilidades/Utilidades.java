/*
* Autor: Samuel Martínez Pazos
* Fecha de creación: 09/05/2023
* Descripción: Clase encargada de administrar los diálogos y la carga de escenas
*/

package jfxspger.utilidades;

import java.io.IOException;
import java.util.Optional;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import jfxspger.JFXSPGER;

public class Utilidades {
    
    public static void mostrarDialogoSimple(String titulo, String mensaje,
        Alert.AlertType tipo) {
        Alert alertaSimple = new Alert(tipo);
        alertaSimple.setTitle(titulo);
        alertaSimple.setContentText(mensaje);
        alertaSimple.setHeaderText(null);
        alertaSimple.showAndWait();
    }
    
    public static Scene inicializarEscena(String ruta) {
        Scene escena = null;
        try
        {
            Parent vista = FXMLLoader.load(JFXSPGER.class.getResource(ruta));
            escena = new Scene(vista);
        } catch (IOException ex)
        {
            System.err.println("ERROR: " + ex.getMessage());
        }
        return escena;
    }
    
    public static boolean mostrarDialogoConfirmacion(String titulo, String mensaje) {
        Alert alertaConfirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        alertaConfirmacion.setTitle(titulo);
        alertaConfirmacion.setContentText(mensaje);
        alertaConfirmacion.setHeaderText(null);
        
        Optional<ButtonType> botonClic = alertaConfirmacion.showAndWait(); 
        
        return (botonClic.get() == ButtonType.OK);
    }
}