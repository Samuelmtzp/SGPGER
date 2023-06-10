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
    private int idGradoConsolidacion;
    private int idDependencia;

    public CuerpoAcademico() {
    }

    public CuerpoAcademico(int idCuerpoAcademico, String nombre, String clave, int idResponsable, int idGradoConsolidacion, int idDependencia) {
        this.idCuerpoAcademico = idCuerpoAcademico;
        this.nombre = nombre;
        this.clave = clave;
        this.idResponsable = idResponsable;
        this.idGradoConsolidacion = idGradoConsolidacion;
        this.idDependencia = idDependencia;
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

    public int getIdGradoConsolidacion() {
        return idGradoConsolidacion;
    }

    public void setIdGradoConsolidacion(int idGradoConsolidacion) {
        this.idGradoConsolidacion = idGradoConsolidacion;
    }

    public int getIdDependencia() {
        return idDependencia;
    }

    public void setIdDependencia(int idDependencia) {
        this.idDependencia = idDependencia;
    }

    @Override
    public String toString() {
        return "CuerpoAcademico{" + "idCuerpoAcademico=" + idCuerpoAcademico + ", nombre=" + nombre + ", clave=" + clave + ", idResponsable=" + idResponsable + ", idGradoConsolidacion=" + idGradoConsolidacion + ", idDependencia=" + idDependencia + '}';
    }

}
