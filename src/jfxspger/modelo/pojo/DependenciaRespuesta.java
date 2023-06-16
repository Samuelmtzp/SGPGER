/*
* Autor: Samuel Martínez Pazos
* Fecha de creación: 09/05/2023
* Descripción: Clase encargada de obtener la respuesta de la consulta de dependencias
*/
package jfxspger.modelo.pojo;

import java.util.ArrayList;

public class DependenciaRespuesta {
    
    private int codigoRespuesta;
    private ArrayList<Dependencia> dependencias;

    public DependenciaRespuesta() {
    }

    public DependenciaRespuesta(int codigoRespuesta, ArrayList<Dependencia> dependencias) {
        this.codigoRespuesta = codigoRespuesta;
        this.dependencias = dependencias;
    }


    public int getCodigoRespuesta() {
        return codigoRespuesta;
    }

    public void setCodigoRespuesta(int codigoRespuesta) {
        this.codigoRespuesta = codigoRespuesta;
    }

    public ArrayList<Dependencia> getDependencias() {
        return dependencias;
    }

    public void setDependencias(ArrayList<Dependencia> dependencias) {
        this.dependencias = dependencias;
    }

    @Override
    public String toString() {
        return "DependenciaRespuesta{" + "codigoRespuesta=" + codigoRespuesta + 
                ", dependencias=" + dependencias + '}';
    }
    
}
