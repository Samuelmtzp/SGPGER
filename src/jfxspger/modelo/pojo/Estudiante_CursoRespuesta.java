/*
* Autor: Samuel Martínez Pazos
* Fecha de creación: 22/05/2023
* Descripción: Clase encargada de obtener la respuesta de consulta de la relación de estudiantes
* en cursos
*/
package jfxspger.modelo.pojo;

import java.util.ArrayList;

public class Estudiante_CursoRespuesta {
    
    private int codigoRespuesta;
    private ArrayList<Estudiante_Curso> estudiantes_curso;

    public Estudiante_CursoRespuesta() {
    }

    public Estudiante_CursoRespuesta(int codigoRespuesta, ArrayList<Estudiante_Curso> estudiantes_curso) {
        this.codigoRespuesta = codigoRespuesta;
        this.estudiantes_curso = estudiantes_curso;
    }

    public int getCodigoRespuesta() {
        return codigoRespuesta;
    }

    public void setCodigoRespuesta(int codigoRespuesta) {
        this.codigoRespuesta = codigoRespuesta;
    }

    public ArrayList<Estudiante_Curso> getEstudiantes_curso() {
        return estudiantes_curso;
    }

    public void setEstudiantes_curso(ArrayList<Estudiante_Curso> estudiantes_curso) {
        this.estudiantes_curso = estudiantes_curso;
    }
    
}
