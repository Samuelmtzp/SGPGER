/*
* Autor: Samuel Martínez Pazos
* Fecha de creación: 09/05/2023
* Descripción: Clase encargada de obtener la respuesta de consulta de grados 
* de consolidación de anteproyecto
*/
package jfxspger.modelo.pojo;

import java.util.ArrayList;

public class GradoConsolidacionRespuesta {
    
    private int codigoRespuesta;
    private ArrayList<GradoConsolidacion> gradosConsolidacion;

    public GradoConsolidacionRespuesta() {
    }

    public GradoConsolidacionRespuesta(int codigoRespuesta, 
            ArrayList<GradoConsolidacion> gradosConsolidacion) {
        this.codigoRespuesta = codigoRespuesta;
        this.gradosConsolidacion = gradosConsolidacion;
    }

    public int getCodigoRespuesta() {
        return codigoRespuesta;
    }

    public void setCodigoRespuesta(int codigoRespuesta) {
        this.codigoRespuesta = codigoRespuesta;
    }

    public ArrayList<GradoConsolidacion> getGradosConsolidacion() {
        return gradosConsolidacion;
    }

    public void setGradosConsolidacion(ArrayList<GradoConsolidacion> gradosConsolidacion) {
        this.gradosConsolidacion = gradosConsolidacion;
    }
    
}
