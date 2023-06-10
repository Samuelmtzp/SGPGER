/*
* Autor: Samuel Martínez Pazos
* Fecha de creación: 22/05/2023
* Descripción: Clase encargada de obtener la respuesta de consulta de calificaciones
*/
package jfxspger.modelo.pojo;

import java.util.ArrayList;

public class CalificacionRespuesta {
    
    private int codigoRespuesta;
    private ArrayList<Calificacion> calificaciones;

    public CalificacionRespuesta() {
    }

    public CalificacionRespuesta(int codigoRespuesta, ArrayList<Calificacion> calificaciones) {
        this.codigoRespuesta = codigoRespuesta;
        this.calificaciones = calificaciones;
    }

    public int getCodigoRespuesta() {
        return codigoRespuesta;
    }

    public void setCodigoRespuesta(int codigoRespuesta) {
        this.codigoRespuesta = codigoRespuesta;
    }

    public ArrayList<Calificacion> getCalificaciones() {
        return calificaciones;
    }

    public void setCalificaciones(ArrayList<Calificacion> calificaciones) {
        this.calificaciones = calificaciones;
    }
    
}
