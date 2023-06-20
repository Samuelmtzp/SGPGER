
package jfxspger.modelo.pojo;

/**
 *
 * @author king_
 */
public class DuracionAprox {
    private int idDuracionAproximada;
    private String duracion;
    
    public DuracionAprox(){
        
    }

    public DuracionAprox(int idDuracionAproximada, String duracion) {
        this.idDuracionAproximada = idDuracionAproximada;
        this.duracion = duracion;
    }

    public int getIdDuracionAproximada() {
        return idDuracionAproximada;
    }

    public void setIdDuracionAproximada(int idDuracionAproximada) {
        this.idDuracionAproximada = idDuracionAproximada;
    }

    public String getDuracion() {
        return duracion;
    }

    public void setDuracion(String duracion) {
        this.duracion = duracion;
    }
    
    @Override
    public String toString(){
        return duracion;
    }
    
}


