/*
* Autor: Samuel Martínez Pazos
* Fecha de creación: 22/05/2023
* Descripción: Clase encargada de obtener la respuesta de consulta de pedidos
*/
package jfxspger.modelo.pojo;

import java.util.ArrayList;

public class PeriodoRespuesta {
    
    private int codigoRespuesta;
    private ArrayList<PeriodoEscolar> periodos;

    public PeriodoRespuesta() {
    }

    public PeriodoRespuesta(int codigoRespuesta, ArrayList<PeriodoEscolar> periodos) {
        this.codigoRespuesta = codigoRespuesta;
        this.periodos = periodos;
    }

    public int getCodigoRespuesta() {
        return codigoRespuesta;
    }

    public void setCodigoRespuesta(int codigoRespuesta) {
        this.codigoRespuesta = codigoRespuesta;
    }

    public ArrayList<PeriodoEscolar> getPeriodos() {
        return periodos;
    }

    public void setPeriodos(ArrayList<PeriodoEscolar> periodos) {
        this.periodos = periodos;
    }

}
