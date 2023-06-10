/*
* Autor: Samuel Martínez Pazos
* Fecha de creación: 22/05/2023
* Descripción: Clase encargada de obtener la respuesta de consulta de cursos
*/
package jfxspger.modelo.pojo;

import java.util.ArrayList;

public class CursoRespuesta {
    
    private int codigoRespuesta;
    private ArrayList<Curso> cursos;

    public CursoRespuesta() {
    }

    public CursoRespuesta(int codigoRespuesta, ArrayList<Curso> cursos) {
        this.codigoRespuesta = codigoRespuesta;
        this.cursos = cursos;
    }

    public int getCodigoRespuesta() {
        return codigoRespuesta;
    }

    public void setCodigoRespuesta(int codigoRespuesta) {
        this.codigoRespuesta = codigoRespuesta;
    }

    public ArrayList<Curso> getCursos() {
        return cursos;
    }

    public void setCursos(ArrayList<Curso> cursos) {
        this.cursos = cursos;
    }

    @Override
    public String toString() {
        return "CursoRespuesta{" + "codigoRespuesta=" + codigoRespuesta + ", cursos=" + cursos + '}';
    }

}
