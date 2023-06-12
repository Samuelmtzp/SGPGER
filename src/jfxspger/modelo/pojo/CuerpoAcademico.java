/*
* Autor: Samuel Martínez Pazos
* Fecha de creación: 09/05/2023
* Descripción: Clase encargada de modelar un cuerpo académico
*/

package jfxspger.modelo.pojo;

public class CuerpoAcademico {
    
    private int idCuerpoAcademico;
    private String nombre;
    private String clave;
    private int idResponsable;
    private String nombreCompletoResponsable;
    private int idGradoConsolidacion;
    private String gradoConsolidacion;
    private int idDependencia;
    private String dependencia;

    public CuerpoAcademico() {
    }

    public CuerpoAcademico(int idCuerpoAcademico, String nombre, String clave, int idResponsable, String nombreCompletoResponsable, int idGradoConsolidacion, String gradoConsolidacion, int idDependencia, String dependencia) {
        this.idCuerpoAcademico = idCuerpoAcademico;
        this.nombre = nombre;
        this.clave = clave;
        this.idResponsable = idResponsable;
        this.nombreCompletoResponsable = nombreCompletoResponsable;
        this.idGradoConsolidacion = idGradoConsolidacion;
        this.gradoConsolidacion = gradoConsolidacion;
        this.idDependencia = idDependencia;
        this.dependencia = dependencia;
    }

    public int getIdCuerpoAcademico() {
        return idCuerpoAcademico;
    }

    public void setIdCuerpoAcademico(int idCuerpoAcademico) {
        this.idCuerpoAcademico = idCuerpoAcademico;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public int getIdResponsable() {
        return idResponsable;
    }

    public void setIdResponsable(int idResponsable) {
        this.idResponsable = idResponsable;
    }

    public String getNombreCompletoResponsable() {
        return nombreCompletoResponsable;
    }

    public void setNombreCompletoResponsable(String nombreCompletoResponsable) {
        this.nombreCompletoResponsable = nombreCompletoResponsable;
    }

    public int getIdGradoConsolidacion() {
        return idGradoConsolidacion;
    }

    public void setIdGradoConsolidacion(int idGradoConsolidacion) {
        this.idGradoConsolidacion = idGradoConsolidacion;
    }

    public String getGradoConsolidacion() {
        return gradoConsolidacion;
    }

    public void setGradoConsolidacion(String gradoConsolidacion) {
        this.gradoConsolidacion = gradoConsolidacion;
    }

    public int getIdDependencia() {
        return idDependencia;
    }

    public void setIdDependencia(int idDependencia) {
        this.idDependencia = idDependencia;
    }

    public String getDependencia() {
        return dependencia;
    }

    public void setDependencia(String dependencia) {
        this.dependencia = dependencia;
    }

    @Override
    public String toString() {
        return "CuerpoAcademico{" + "idCuerpoAcademico=" + idCuerpoAcademico + ", nombre=" + nombre + ", clave=" + clave + ", idResponsable=" + idResponsable + ", nombreCompletoResponsable=" + nombreCompletoResponsable + ", idGradoConsolidacion=" + idGradoConsolidacion + ", gradoConsolidacion=" + gradoConsolidacion + ", idDependencia=" + idDependencia + ", dependencia=" + dependencia + '}';
    }

}
