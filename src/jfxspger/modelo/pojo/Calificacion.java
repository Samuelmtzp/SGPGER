/*
* Autor: Samuel Martínez Pazos
* Fecha de creación: 22/05/2023
* Descripción: Clase encargada de modelar una calificación de una actividad
*/

package jfxspger.modelo.pojo;

public class Calificacion {
    
    private int idCalificacion;
    private int idActividad;
    private double calificacion;
    private String comentario;

    public Calificacion() {
    }
    
    public Calificacion(int idCalificacion, int actividad_idActividad, 
            double calificacion, String comentario) {
        this.idCalificacion = idCalificacion;
        this.idActividad = actividad_idActividad;
        this.calificacion = calificacion;
        this.comentario = comentario;
    }

    public int getIdCalificacion() {
        return idCalificacion;
    }

    public void setIdCalificacion(int idCalificacion) {
        this.idCalificacion = idCalificacion;
    }

    public int getIdActividad() {
        return idActividad;
    }

    public void setIdActividad(int idActividad) {
        this.idActividad = idActividad;
    }

    public double getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(double calificacion) {
        this.calificacion = calificacion;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    @Override
    public String toString() {
        return "Calificacion{" + "idCalificacion=" + idCalificacion + ", idActividad=" + 
                idActividad + ", calificacion=" + calificacion + ", comentario=" + comentario + '}';
    }
    
}
