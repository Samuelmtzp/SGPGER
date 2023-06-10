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
