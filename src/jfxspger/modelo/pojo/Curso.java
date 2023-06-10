/*
* Autor: Samuel Martínez Pazos
* Fecha de creación: 09/05/2023
* Descripción: Clase encargada de modelar un curso
*/
package jfxspger.modelo.pojo;

public class Curso {
    
    private int idCurso;
    private int idExperienciaEducativa;
    private int idPeriodo;
    private int idProfesor;
    private int nrc;
    private int bloque;
    private String seccion;
    private int cupo;

    public Curso() {
    }

    public Curso(int idCurso, int idExperienciaEducativa, int idPeriodo, int idProfesor, int nrc, int bloque, String seccion, int cupo) {
        this.idCurso = idCurso;
        this.idExperienciaEducativa = idExperienciaEducativa;
        this.idPeriodo = idPeriodo;
        this.idProfesor = idProfesor;
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

    public int getIdPeriodo() {
        return idPeriodo;
    }

    public void setIdPeriodo(int idPeriodo) {
        this.idPeriodo = idPeriodo;
    }

    public int getIdProfesor() {
        return idProfesor;
    }

    public void setIdProfesor(int idProfesor) {
        this.idProfesor = idProfesor;
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
        return "Curso{" + "idCurso=" + idCurso + ", idExperienciaEducativa=" + idExperienciaEducativa + ", idPeriodo=" + idPeriodo + ", idProfesor=" + idProfesor + ", nrc=" + nrc + ", bloque=" + bloque + ", seccion=" + seccion + ", cupo=" + cupo + '}';
    }
    
}
