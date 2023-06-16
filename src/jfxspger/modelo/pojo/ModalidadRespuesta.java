/*
* Autor: Samuel Martínez Pazos
* Fecha de creación: 09/05/2023
* Descripción: Clase encargada de obtener la respuesta de consulta de modalidades
*/
package jfxspger.modelo.pojo;

import java.util.ArrayList;

public class ModalidadRespuesta {
    
    private int codigoRespuesta;
    private ArrayList<Modalidad> modalidades;

    public ModalidadRespuesta() {
    }

    public ModalidadRespuesta(int codigoRespuesta, ArrayList<Modalidad> modalidades) {
        this.codigoRespuesta = codigoRespuesta;
        this.modalidades = modalidades;
    }

    public int getCodigoRespuesta() {
        return codigoRespuesta;
    }

    public void setCodigoRespuesta(int codigoRespuesta) {
        this.codigoRespuesta = codigoRespuesta;
    }

    public ArrayList<Modalidad> getModalidades() {
        return modalidades;
    }

    public void setModalidades(ArrayList<Modalidad> modalidades) {
        this.modalidades = modalidades;
    }

}
