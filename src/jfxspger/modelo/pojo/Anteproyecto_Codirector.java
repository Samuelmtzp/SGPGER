package jfxspger.modelo.pojo;

public class Anteproyecto_Codirector {
    
    private int idCodirector;
    private int idAnteproyecto;

    public Anteproyecto_Codirector() {
    }

    public Anteproyecto_Codirector(int idCodirector, int idAnteproyecto) {
        this.idCodirector = idCodirector;
        this.idAnteproyecto = idAnteproyecto;
    }

    public int getIdCodirector() {
        return idCodirector;
    }

    public void setIdCodirector(int idCodirector) {
        this.idCodirector = idCodirector;
    }

    public int getIdAnteproyecto() {
        return idAnteproyecto;
    }

    public void setIdAnteproyecto(int idAnteproyecto) {
        this.idAnteproyecto = idAnteproyecto;
    }

    @Override
    public String toString() {
        return "Anteproyecto_Codirector{" + "idCodirector=" + idCodirector + ", idAnteproyecto=" + idAnteproyecto + '}';
    }
    
}
