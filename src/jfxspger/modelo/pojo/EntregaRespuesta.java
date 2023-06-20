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
    private int idEntrega;

    public EntregaRespuesta() {
    }

    public EntregaRespuesta(int codigoRespuesta, ArrayList<Entrega> entregas, int idEntrega) {
        this.codigoRespuesta = codigoRespuesta;
        this.entregas = entregas;
        this.idEntrega = idEntrega;
    }

    public int getIdEntrega() {
        return idEntrega;
    }

    public void setIdEntrega(int idEntrega) {
        this.idEntrega = idEntrega;
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
