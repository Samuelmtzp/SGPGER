
package jfxspger.modelo.pojo;

import java.util.ArrayList;

/**
 *
 * @author king_
 */
public class DuracionAproxRespuesta {
    
    private int codigoRespuesta;
    private ArrayList<DuracionAprox> duracionAproximada;

    public DuracionAproxRespuesta() {
    }

    public DuracionAproxRespuesta(int codigoRespuesta,ArrayList<DuracionAprox> duracionAproximada){
        this.codigoRespuesta = codigoRespuesta;
        this.duracionAproximada = duracionAproximada;
    }

    public int getCodigoRespuesta() {
        return codigoRespuesta;
    }

    public void setCodigoRespuesta(int codigoRespuesta) {
        this.codigoRespuesta = codigoRespuesta;
    }

    public ArrayList<DuracionAprox> getDuracionAproximada() {
        return duracionAproximada;
    }

    public void setDuracionAproximada(ArrayList<DuracionAprox> duracionAproximada) {
        this.duracionAproximada = duracionAproximada;
    }
}
