/*
* Autor: Samuel Martínez Pazos
* Fecha de creación: 09/05/2023
* Descripción: Clase encargada de modelar una actividad
*/

package jfxspger.modelo.pojo;

public class Actividad {
    
    private int idActividad;
    private int idAnteproyecto;
    private int idEstudiante;
    private String titulo;
    private String fechaCreacion;
    private String fechaInicio;
    private String fechaFin;
    private String descripcion;

    public Actividad() {
    }

    public Actividad(int idActividad, int idAnteproyecto, int idEstudiante, String titulo, String fechaCreacion, String fechaInicio, String fechaFin, String descripcion) {
        this.idActividad = idActividad;
        this.idAnteproyecto = idAnteproyecto;
        this.idEstudiante = idEstudiante;
        this.titulo = titulo;
        this.fechaCreacion = fechaCreacion;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.descripcion = descripcion;
    }

    public int getIdActividad() {
        return idActividad;
    }

    public void setIdActividad(int idActividad) {
        this.idActividad = idActividad;
    }

    public int getIdAnteproyecto() {
        return idAnteproyecto;
    }

    public void setIdAnteproyecto(int idAnteproyecto) {
        this.idAnteproyecto = idAnteproyecto;
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
        return "Actividad{" + "idActividad=" + idActividad + ", idAnteproyecto=" + idAnteproyecto + ", idEstudiante=" + idEstudiante + ", titulo=" + titulo + ", fechaCreacion=" + fechaCreacion + ", fechaInicio=" + fechaInicio + ", fechaFin=" + fechaFin + ", descripcion=" + descripcion + '}';
    }
    
}
