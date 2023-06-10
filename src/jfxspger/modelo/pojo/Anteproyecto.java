/*
* Autor: Samuel Martínez Pazos
* Fecha de creación: 09/05/2023
* Descripción: Clase encargada de modelar un anteproyecto
*/
package jfxspger.modelo.pojo;

public class Anteproyecto {
    
    private int idAnteproyecto;
    private int idCuerpoAcademico;
    private int idDirector;
    private int idEstado;
    private int idModalidad;
    private int idLgac;
    private String proyectoInvestigacion;
    private String lineaInvestigacion;
    private String duracionAproximada;
    private String nombreTrabajo;
    private String requisitos;
    private int cantidadAlumnosParticipantes;
    private String descripcionProyectoInvestigacion;
    private String descripcionTrabajoRecepcional;
    private String resultadosEsperados;
    private String bibliografiaRecomendada;
    private String comentarios;

    public Anteproyecto() {
    }

    public Anteproyecto(int idAnteproyecto, int idCuerpoAcademico, int idDirector, int idEstado, int idModalidad, int idLgac, String proyectoInvestigacion, String lineaInvestigacion, String duracionAproximada, String nombreTrabajo, String requisitos, int cantidadAlumnosParticipantes, String descripcionProyectoInvestigacion, String descripcionTrabajoRecepcional, String resultadosEsperados, String bibliografiaRecomendada, String comentarios) {
        this.idAnteproyecto = idAnteproyecto;
        this.idCuerpoAcademico = idCuerpoAcademico;
        this.idDirector = idDirector;
        this.idEstado = idEstado;
        this.idModalidad = idModalidad;
        this.idLgac = idLgac;
        this.proyectoInvestigacion = proyectoInvestigacion;
        this.lineaInvestigacion = lineaInvestigacion;
        this.duracionAproximada = duracionAproximada;
        this.nombreTrabajo = nombreTrabajo;
        this.requisitos = requisitos;
        this.cantidadAlumnosParticipantes = cantidadAlumnosParticipantes;
        this.descripcionProyectoInvestigacion = descripcionProyectoInvestigacion;
        this.descripcionTrabajoRecepcional = descripcionTrabajoRecepcional;
        this.resultadosEsperados = resultadosEsperados;
        this.bibliografiaRecomendada = bibliografiaRecomendada;
        this.comentarios = comentarios;
    }

    public int getIdAnteproyecto() {
        return idAnteproyecto;
    }

    public void setIdAnteproyecto(int idAnteproyecto) {
        this.idAnteproyecto = idAnteproyecto;
    }

    public int getIdCuerpoAcademico() {
        return idCuerpoAcademico;
    }

    public void setIdCuerpoAcademico(int idCuerpoAcademico) {
        this.idCuerpoAcademico = idCuerpoAcademico;
    }

    public int getIdDirector() {
        return idDirector;
    }

    public void setIdDirector(int idDirector) {
        this.idDirector = idDirector;
    }

    public int getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(int idEstado) {
        this.idEstado = idEstado;
    }

    public int getIdModalidad() {
        return idModalidad;
    }

    public void setIdModalidad(int idModalidad) {
        this.idModalidad = idModalidad;
    }

    public int getIdLgac() {
        return idLgac;
    }

    public void setIdLgac(int idLgac) {
        this.idLgac = idLgac;
    }

    public String getProyectoInvestigacion() {
        return proyectoInvestigacion;
    }

    public void setProyectoInvestigacion(String proyectoInvestigacion) {
        this.proyectoInvestigacion = proyectoInvestigacion;
    }

    public String getLineaInvestigacion() {
        return lineaInvestigacion;
    }

    public void setLineaInvestigacion(String lineaInvestigacion) {
        this.lineaInvestigacion = lineaInvestigacion;
    }

    public String getDuracionAproximada() {
        return duracionAproximada;
    }

    public void setDuracionAproximada(String duracionAproximada) {
        this.duracionAproximada = duracionAproximada;
    }

    public String getNombreTrabajo() {
        return nombreTrabajo;
    }

    public void setNombreTrabajo(String nombreTrabajo) {
        this.nombreTrabajo = nombreTrabajo;
    }

    public String getRequisitos() {
        return requisitos;
    }

    public void setRequisitos(String requisitos) {
        this.requisitos = requisitos;
    }

    public int getCantidadAlumnosParticipantes() {
        return cantidadAlumnosParticipantes;
    }

    public void setCantidadAlumnosParticipantes(int cantidadAlumnosParticipantes) {
        this.cantidadAlumnosParticipantes = cantidadAlumnosParticipantes;
    }

    public String getDescripcionProyectoInvestigacion() {
        return descripcionProyectoInvestigacion;
    }

    public void setDescripcionProyectoInvestigacion(String descripcionProyectoInvestigacion) {
        this.descripcionProyectoInvestigacion = descripcionProyectoInvestigacion;
    }

    public String getDescripcionTrabajoRecepcional() {
        return descripcionTrabajoRecepcional;
    }

    public void setDescripcionTrabajoRecepcional(String descripcionTrabajoRecepcional) {
        this.descripcionTrabajoRecepcional = descripcionTrabajoRecepcional;
    }

    public String getResultadosEsperados() {
        return resultadosEsperados;
    }

    public void setResultadosEsperados(String resultadosEsperados) {
        this.resultadosEsperados = resultadosEsperados;
    }

    public String getBibliografiaRecomendada() {
        return bibliografiaRecomendada;
    }

    public void setBibliografiaRecomendada(String bibliografiaRecomendada) {
        this.bibliografiaRecomendada = bibliografiaRecomendada;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    @Override
    public String toString() {
        return "Anteproyecto{" + "idAnteproyecto=" + idAnteproyecto + ", idCuerpoAcademico=" + idCuerpoAcademico + ", idDirector=" + idDirector + ", idEstado=" + idEstado + ", idModalidad=" + idModalidad + ", idLgac=" + idLgac + ", proyectoInvestigacion=" + proyectoInvestigacion + ", lineaInvestigacion=" + lineaInvestigacion + ", duracionAproximada=" + duracionAproximada + ", nombreTrabajo=" + nombreTrabajo + ", requisitos=" + requisitos + ", cantidadAlumnosParticipantes=" + cantidadAlumnosParticipantes + ", descripcionProyectoInvestigacion=" + descripcionProyectoInvestigacion + ", descripcionTrabajoRecepcional=" + descripcionTrabajoRecepcional + ", resultadosEsperados=" + resultadosEsperados + ", bibliografiaRecomendada=" + bibliografiaRecomendada + ", comentarios=" + comentarios + '}';
    }
    
}
