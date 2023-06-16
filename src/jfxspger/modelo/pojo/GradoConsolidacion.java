/*
* Autor: Samuel Martínez Pazos
* Fecha de creación: 09/05/2023
* Descripción: Clase encargada de modelar un grado de consolidación
*/
package jfxspger.modelo.pojo;

public class GradoConsolidacion {
    
    private int idGradoConsolidacion;
    private String grado;

    public GradoConsolidacion() {
    }

    public GradoConsolidacion(int idGradoConsolidacion, String grado) {
        this.idGradoConsolidacion = idGradoConsolidacion;
        this.grado = grado;
    }

    public int getIdGradoConsolidacion() {
        return idGradoConsolidacion;
    }

    public void setIdGradoConsolidacion(int idGradoConsolidacion) {
        this.idGradoConsolidacion = idGradoConsolidacion;
    }

    public String getGrado() {
        return grado;
    }

    public void setGrado(String grado) {
        this.grado = grado;
    }

    @Override
    public String toString() {
        return grado;
    }
    
}
