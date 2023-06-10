/*
* Autor: Samuel Martínez Pazos
* Fecha de creación: 22/05/2023
* Descripción: Clase encargada de obtener la respuesta de consulta de entregas de actividades
*/
package jfxspger.modelo.pojo;

import java.util.ArrayList;

public class EntregaRespuesta {
    
    private int codigoRespuesta;
    private ArrayList<Entrega> entregas;

    public EntregaRespuesta() {
    }

    public EntregaRespuesta(int codigoRespuesta, ArrayList<Entrega> entregas) {
        this.codigoRespuesta = codigoRespuesta;
        this.entregas = entregas;
    }

    public int getCodigoRespuesta() {
        return codigoRespuesta;
    }

    public void setCodigoRespuesta(int codigoRespuesta) {
        this.codigoRespuesta = codigoRespuesta;
    }

    public ArrayList<Entrega> getEntregas() {
        return entregas;
    }

    public void setEntregas(ArrayList<Entrega> entregas) {
        this.entregas = entregas;
    }
    
}
