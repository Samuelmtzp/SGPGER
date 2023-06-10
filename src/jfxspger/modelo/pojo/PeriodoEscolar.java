/*
* Autor: Samuel Martínez Pazos
* Fecha de creación: 22/05/2023
* Descripción: Clase encargada de modelar un periodo para un curso
*/

package jfxspger.modelo.pojo;

public class PeriodoEscolar {
    
    private int idPeriodoEscolar;
    private String fechaInicio;
    private String fechaFin;
    private String nombre;

    public PeriodoEscolar() {
    }

    public PeriodoEscolar(int idPeriodoEscolar, String fechaInicio, String fechaFin, String nombre) {
        this.idPeriodoEscolar = idPeriodoEscolar;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.nombre = nombre;
    }

    public int getIdPeriodoEscolar() {
        return idPeriodoEscolar;
    }

    public void setIdPeriodoEscolar(int idPeriodoEscolar) {
        this.idPeriodoEscolar = idPeriodoEscolar;
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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "PeriodoEscolar{" + "idPeriodoEscolar=" + idPeriodoEscolar + ", fechaInicio=" + fechaInicio + ", fechaFin=" + fechaFin + ", nombre=" + nombre + '}';
    }
    
}
