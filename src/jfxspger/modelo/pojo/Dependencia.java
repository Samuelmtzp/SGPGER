/*
* Autor: Samuel Martínez Pazos
* Fecha de creación: 09/05/2023
* Descripción: Clase encargada de modelar una dependencia
*/
package jfxspger.modelo.pojo;

public class Dependencia {
    
    private int idDependencia;
    private String dependencia;

    public Dependencia() {
    }
    
    public Dependencia(int idDependencia, String dependencia) {
        this.idDependencia = idDependencia;
        this.dependencia = dependencia;
    }

    public int getIdDependencia() {
        return idDependencia;
    }

    public void setIdDependencia(int idDependencia) {
        this.idDependencia = idDependencia;
    }

    public String getDependencia() {
        return dependencia;
    }

    public void setDependencia(String dependencia) {
        this.dependencia = dependencia;
    }

    @Override
    public String toString() {
        return dependencia;
    }
    
}