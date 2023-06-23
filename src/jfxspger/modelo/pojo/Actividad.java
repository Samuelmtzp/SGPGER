/*
* Autor: Samuel Martínez Pazos
* Fecha de creación: 09/05/2023
* Descripción: Clase encargada de modelar una actividad
*/

package jfxspger.modelo.pojo;

public class Actividad {
    
    private int idActividad;
    private int idEstudiante;
    private int idEntrega;
    private int idCalificacion;
    private int idEstado;
    private double calificacion;
    private String titulo;
    private String fechaCreacion;
    private String fechaInicio;
    private String fechaFin;
    private String descripcion;    
    private String fechaEntrega;
    private String commentCalif;
    private String estado;
    

    public Actividad() {
    }

    public Actividad(int idActividad, int idEstudiante, int idEntrega, int idCalificacion, int idEstado, double calificacion, String titulo, String fechaCreacion, String fechaInicio, String fechaFin, String descripcion, String fechaEntrega, String commentCalif, String estado) {
        this.idActividad = idActividad;
        this.idEstudiante = idEstudiante;
        this.idEntrega = idEntrega;
        this.idCalificacion = idCalificacion;
        this.idEstado = idEstado;
        this.calificacion = calificacion;
        this.titulo = titulo;
        this.fechaCreacion = fechaCreacion;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.descripcion = descripcion;
        this.fechaEntrega = fechaEntrega;
        this.commentCalif = commentCalif;
        this.estado = estado;
    }

    public int getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(int idEstado) {
        this.idEstado = idEstado;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCommentCalif() {
        return commentCalif;
    }

    public void setCommentCalif(String commentCalif) {
        this.commentCalif = commentCalif;
    }
    

    public int getIdCalificacion() {
        return idCalificacion;
    }

    public void setIdCalificacion(int idCalificacion) {
        this.idCalificacion = idCalificacion;
    }

    public double getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(double calificacion) {
        this.calificacion = calificacion;
    }

    public String getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(String fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }   

    public int getIdEntrega() {
        return idEntrega;
    }

    public void setIdEntrega(int idEntrega) {
        this.idEntrega = idEntrega;
    }

    public int getIdActividad() {
        return idActividad;
    }

    public void setIdActividad(int idActividad) {
        this.idActividad = idActividad;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getIdEstudiante() {
        return idEstudiante;
    }

    public void setIdEstudiante(int idEstudiante) {
        this.idEstudiante = idEstudiante;
    }

    @Override
    public String toString() {
        return "Actividad{" + "idActividad=" + idActividad + 
                ", idAnteproyecto=" + ", idEstudiante=" + idEstudiante + 
                ", titulo=" + titulo + ", fechaCreacion=" + fechaCreacion + 
                ", fechaInicio=" + fechaInicio + ", fechaFin=" + fechaFin + 
                ", descripcion=" + descripcion + '}';
    }
    
}
