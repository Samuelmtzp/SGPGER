/*
* Autor: Samuel Martínez Pazos
* Fecha de creación: 22/05/2023
* Descripción: Clase encargada de obtener la respuesta de consulta de academicos
*/
package jfxspger.modelo.pojo;

import java.util.ArrayList;

public class AcademicoRespuesta {
    
    private int codigoRespuesta;
    private ArrayList<Academico> academicos;

    public AcademicoRespuesta() {
    }

    public AcademicoRespuesta(int codigoRespuesta, ArrayList<Academico> academicos) {
        this.codigoRespuesta = codigoRespuesta;
        this.academicos = academicos;
    }

    public int getCodigoRespuesta() {
        return codigoRespuesta;
    }

    public void setCodigoRespuesta(int codigoRespuesta) {
        this.codigoRespuesta = codigoRespuesta;
    }

    public ArrayList<Academico> getAcademicos() {
        return academicos;
    }

    public void setAcademicos(ArrayList<Academico> academicos) {
        this.academicos = academicos;
    }
    
}
