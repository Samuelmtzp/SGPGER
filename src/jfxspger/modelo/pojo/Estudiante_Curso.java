/*
* Autor: Samuel Martínez Pazos
* Fecha de creación: 22/05/2023
* Descripción: Clase encargada de modelar la relación existente entre un estudiante y un curso
*/

package jfxspger.modelo.pojo;

public class Estudiante_Curso {
    
    private int idEstudiante;
    private int idCurso;

    public Estudiante_Curso() {
    }

    public Estudiante_Curso(int idEstudiante, int idCurso) {
        this.idEstudiante = idEstudiante;
        this.idCurso = idCurso;
    }

    public int getIdEstudiante() {
        return idEstudiante;
    }

    public void setIdEstudiante(int idEstudiante) {
        this.idEstudiante = idEstudiante;
    }

    public int getIdCurso() {
        return idCurso;
    }

    public void setIdCurso(int idCurso) {
        this.idCurso = idCurso;
    }
    
}
