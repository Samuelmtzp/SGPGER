/*
* Autor: Samuel Martínez Pazos
* Fecha de creación: 22/05/2023
* Descripción: Clase encargada de obtener la respuesta de consulta de cuerpos académicos
*/
package jfxspger.modelo.pojo;

import java.util.ArrayList;

public class CuerpoAcademicoRespuesta {
    private int idCuerpoAcademicoRegistrado;
    private int codigoRespuesta;
    private ArrayList<CuerpoAcademico> cuerposAcademicos;

    public CuerpoAcademicoRespuesta() {
    }

    public CuerpoAcademicoRespuesta(int idCuerpoAcademicoRegistrado, int codigoRespuesta, ArrayList<CuerpoAcademico> cuerposAcademicos) {
        this.idCuerpoAcademicoRegistrado = idCuerpoAcademicoRegistrado;
        this.codigoRespuesta = codigoRespuesta;
        this.cuerposAcademicos = cuerposAcademicos;
    }

    public int getIdCuerpoAcademicoRegistrado() {
        return idCuerpoAcademicoRegistrado;
    }

    public void setIdCuerpoAcademicoRegistrado(int idCuerpoAcademicoRegistrado) {
        this.idCuerpoAcademicoRegistrado = idCuerpoAcademicoRegistrado;
    }

    public int getCodigoRespuesta() {
        return codigoRespuesta;
    }

    public void setCodigoRespuesta(int codigoRespuesta) {
        this.codigoRespuesta = codigoRespuesta;
    }

    public ArrayList<CuerpoAcademico> getCuerposAcademicos() {
        return cuerposAcademicos;
    }

    public void setCuerposAcademicos(ArrayList<CuerpoAcademico> cuerposAcademicos) {
        this.cuerposAcademicos = cuerposAcademicos;
    }

}
