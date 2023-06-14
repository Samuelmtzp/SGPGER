/*
* Autor: Samuel Martínez Pazos
* Fecha de creación: 09/05/2023
* Descripción: Clase encargada de modelar un curso
*/
package jfxspger.modelo.pojo;

public class Curso {
    
    private int idCurso;
    private int idExperienciaEducativa;
    private String experienciaEducativa;
    private int idPeriodo;
    private String periodo;
    private int idProfesor;
    private String nombreCompletoProfesor;
    private int nrc;
    private int bloque;
    private String seccion;
    private int cupo;

    public Curso() {
    }

    public Curso(int idCurso, int idExperienciaEducativa, String experienciaEducativa, int idPeriodo, String periodo, int idProfesor, String nombreCompletoProfesor, int nrc, int bloque, String seccion, int cupo) {
        this.idCurso = idCurso;
        this.idExperienciaEducativa = idExperienciaEducativa;
        this.experienciaEducativa = experienciaEducativa;
        this.idPeriodo = idPeriodo;
        this.periodo = periodo;
        this.idProfesor = idProfesor;
        this.nombreCompletoProfesor = nombreCompletoProfesor;
        this.nrc = nrc;
        this.bloque = bloque;
        this.seccion = seccion;
        this.cupo = cupo;
    }

    public int getIdCurso() {
        return idCurso;
    }

    public void setIdCurso(int idCurso) {
        this.idCurso = idCurso;
    }

    public int getIdExperienciaEducativa() {
        return idExperienciaEducativa;
    }

    public void setIdExperienciaEducativa(int idExperienciaEducativa) {
        this.idExperienciaEducativa = idExperienciaEducativa;
    }

    public String getExperienciaEducativa() {
        return experienciaEducativa;
    }

    public void setExperienciaEducativa(String experienciaEducativa) {
        this.experienciaEducativa = experienciaEducativa;
    }

    public int getIdPeriodo() {
        return idPeriodo;
    }

    public void setIdPeriodo(int idPeriodo) {
        this.idPeriodo = idPeriodo;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public int getIdProfesor() {
        return idProfesor;
    }

    public void setIdProfesor(int idProfesor) {
        this.idProfesor = idProfesor;
    }

    public String getNombreCompletoProfesor() {
        return nombreCompletoProfesor;
    }

    public void setNombreCompletoProfesor(String nombreCompletoProfesor) {
        this.nombreCompletoProfesor = nombreCompletoProfesor;
    }

    public int getNrc() {
        return nrc;
    }

    public void setNrc(int nrc) {
        this.nrc = nrc;
    }

    public int getBloque() {
        return bloque;
    }

    public void setBloque(int bloque) {
        this.bloque = bloque;
    }

    public String getSeccion() {
        return seccion;
    }

    public void setSeccion(String seccion) {
        this.seccion = seccion;
    }

    public int getCupo() {
        return cupo;
    }

    public void setCupo(int cupo) {
        this.cupo = cupo;
    }

    @Override
    public String toString() {
        return "Curso{" + "idCurso=" + idCurso + ", idExperienciaEducativa=" + idExperienciaEducativa + ", experienciaEducativa=" + experienciaEducativa + ", idPeriodo=" + idPeriodo + ", periodo=" + periodo + ", idProfesor=" + idProfesor + ", nombreCompletoProfesor=" + nombreCompletoProfesor + ", nrc=" + nrc + ", bloque=" + bloque + ", seccion=" + seccion + ", cupo=" + cupo + '}';
    }

}
