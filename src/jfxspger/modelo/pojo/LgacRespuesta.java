/*
* Autor: Samuel Martínez Pazos
* Fecha de creación: 22/05/2023
* Descripción: Clase encargada de obtener la respuesta de consulta de la lista de lgac
*/
package jfxspger.modelo.pojo;

import java.util.ArrayList;

public class LgacRespuesta {
    
    private int codigoRespuesta;
    private ArrayList<Lgac> listaLgac;

    public LgacRespuesta() {
    }

    public LgacRespuesta(int codigoRespuesta, ArrayList<Lgac> listaLgac) {
        this.codigoRespuesta = codigoRespuesta;
        this.listaLgac = listaLgac;
    }

    public int getCodigoRespuesta() {
        return codigoRespuesta;
    }

    public void setCodigoRespuesta(int codigoRespuesta) {
        this.codigoRespuesta = codigoRespuesta;
    }

    public ArrayList<Lgac> getListaLgac() {
        return listaLgac;
    }

    public void setListaLgac(ArrayList<Lgac> listaLgac) {
        this.listaLgac = listaLgac;
    }

}
