/*
* Autor: Samuel Martínez Pazos
* Fecha de creación: 09/05/2023
* Descripción: Clase encargada de modelar un anteproyecto
*/
package jfxspger.modelo.pojo;

public class Anteproyecto {
    
    private int idAnteproyecto;
    private int idCuerpoAcademico;
    private String cuerpoAcademico;
    private int idDirector;
    private String Director;
    private int idEstado;
    private String Estado;
    private int idModalidad;
    private String modalidad;
    private int idLgac;
    private String lgac;
    private String proyectoInvestigacion;
    private String lineaInvestigacion;
    private int idDuracionAproximada;
    private String duracionAproximada;
    private String nombreTrabajo;
    private String requisitos;
    private int cantidadAlumnosParticipantes;
    private int maximoAlumnosParticipantes;
    private String descripcionProyectoInvestigacion;
    private String descripcionTrabajoRecepcional;
    private String resultadosEsperados;
    private String bibliografiaRecomendada;
    private String fechaCreacion;

    public Anteproyecto() {
    }

    public Anteproyecto(int idAnteproyecto, int idCuerpoAcademico, String cuerpoAcademico, 
            int idDirector, String Director, int idEstado, String Estado, int idModalidad, 
            String modalidad, int idLgac, String lgac, String proyectoInvestigacion, 
            String lineaInvestigacion, int idDuracionAproximada, String duracionAproximada, 
            String nombreTrabajo, String requisitos, int cantidadAlumnosParticipantes, 
            int maximoAlumnosParticipantes, String descripcionProyectoInvestigacion, 
            String descripcionTrabajoRecepcional, String resultadosEsperados, 
            String bibliografiaRecomendada, String fechaCreacion) {
        this.idAnteproyecto = idAnteproyecto;
        this.idCuerpoAcademico = idCuerpoAcademico;
        this.cuerpoAcademico = cuerpoAcademico;
        this.idDirector = idDirector;
        this.Director = Director;
        this.idEstado = idEstado;
        this.Estado = Estado;
        this.idModalidad = idModalidad;
        this.modalidad = modalidad;
        this.idLgac = idLgac;
        this.lgac = lgac;
        this.proyectoInvestigacion = proyectoInvestigacion;
        this.lineaInvestigacion = lineaInvestigacion;
        this.idDuracionAproximada = idDuracionAproximada;
        this.duracionAproximada = duracionAproximada;
        this.nombreTrabajo = nombreTrabajo;
        this.requisitos = requisitos;
        this.cantidadAlumnosParticipantes = cantidadAlumnosParticipantes;
        this.maximoAlumnosParticipantes = maximoAlumnosParticipantes;
        this.descripcionProyectoInvestigacion = descripcionProyectoInvestigacion;
        this.descripcionTrabajoRecepcional = descripcionTrabajoRecepcional;
        this.resultadosEsperados = resultadosEsperados;
        this.bibliografiaRecomendada = bibliografiaRecomendada;
        this.fechaCreacion = fechaCreacion;
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

    public String getCuerpoAcademico() {
        return cuerpoAcademico;
    }

    public void setCuerpoAcademico(String cuerpoAcademico) {
        this.cuerpoAcademico = cuerpoAcademico;
    }

    public int getIdDirector() {
        return idDirector;
    }

    public void setIdDirector(int idDirector) {
        this.idDirector = idDirector;
    }

    public String getDirector() {
        return Director;
    }

    public void setDirector(String Director) {
        this.Director = Director;
    }

    public int getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(int idEstado) {
        this.idEstado = idEstado;
    }

    public String getEstado() {
        return Estado;
    }

    public void setEstado(String Estado) {
        this.Estado = Estado;
    }

    public int getIdModalidad() {
        return idModalidad;
    }

    public void setIdModalidad(int idModalidad) {
        this.idModalidad = idModalidad;
    }

    public String getModalidad() {
        return modalidad;
    }

    public void setModalidad(String modalidad) {
        this.modalidad = modalidad;
    }

    public int getIdLgac() {
        return idLgac;
    }

    public void setIdLgac(int idLgac) {
        this.idLgac = idLgac;
    }

    public String getLgac() {
        return lgac;
    }

    public void setLgac(String lgac) {
        this.lgac = lgac;
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

    public int getIdDuracionAproximada() {
        return idDuracionAproximada;
    }

    public void setIdDuracionAproximada(int idDuracionAproximada) {
        this.idDuracionAproximada = idDuracionAproximada;
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

    public int getMaximoAlumnosParticipantes() {
        return maximoAlumnosParticipantes;
    }

    public void setMaximoAlumnosParticipantes(int maximoAlumnosParticipantes) {
        this.maximoAlumnosParticipantes = maximoAlumnosParticipantes;
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

    public String getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    @Override
    public String toString() {
        return "Anteproyecto{" + "idAnteproyecto=" + idAnteproyecto + ", idCuerpoAcademico=" + 
                idCuerpoAcademico + ", cuerpoAcademico=" + cuerpoAcademico + ", idDirector=" + 
                idDirector + ", Director=" + Director + ", idEstado=" + idEstado + ", Estado=" + 
                Estado + ", idModalidad=" + idModalidad + ", modalidad=" + modalidad + 
                ", idLgac=" + idLgac + ", lgac=" + lgac + ", proyectoInvestigacion=" + 
                proyectoInvestigacion + ", lineaInvestigacion=" + lineaInvestigacion + 
                ", idDuracionAproximada=" + idDuracionAproximada + ", duracionAproximada=" + 
                duracionAproximada + ", nombreTrabajo=" + nombreTrabajo + ", requisitos=" + 
                requisitos + ", cantidadAlumnosParticipantes=" + cantidadAlumnosParticipantes + 
                ", maximoAlumnosParticipantes=" + maximoAlumnosParticipantes + 
                ", descripcionProyectoInvestigacion=" + descripcionProyectoInvestigacion + 
                ", descripcionTrabajoRecepcional=" + descripcionTrabajoRecepcional + 
                ", resultadosEsperados=" + resultadosEsperados + ", bibliografiaRecomendada=" + 
                bibliografiaRecomendada + ", fechaCreacion=" + fechaCreacion + '}';
    }

}
