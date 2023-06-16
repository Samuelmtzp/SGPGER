/*
* Autor: Samuel Martínez Pazos
* Fecha de creación: 09/05/2023
* Descripción: Clase encargada de obtener la respuesta de consulta de 
* la relación entre anteproyectos y codirectores
*/
package jfxspger.modelo.pojo;

import java.util.ArrayList;

public class Anteproyecto_CodirectorRespuesta {
    
    private int codigoRespuesta;
    ArrayList<Anteproyecto_Codirector> anteproyectos_codirectores;

    public Anteproyecto_CodirectorRespuesta() {
    }

    public Anteproyecto_CodirectorRespuesta(int codigoRespuesta, 
            ArrayList<Anteproyecto_Codirector> anteproyectos_codirectores) {
        this.codigoRespuesta = codigoRespuesta;
        this.anteproyectos_codirectores = anteproyectos_codirectores;
    }

    public int getCodigoRespuesta() {
        return codigoRespuesta;
    }

    public void setCodigoRespuesta(int codigoRespuesta) {
        this.codigoRespuesta = codigoRespuesta;
    }

    public ArrayList<Anteproyecto_Codirector> getAnteproyectos_codirectores() {
        return anteproyectos_codirectores;
    }

    public void setAnteproyectos_codirectores(ArrayList<Anteproyecto_Codirector> 
            anteproyectos_codirectores) {
        this.anteproyectos_codirectores = anteproyectos_codirectores;
    }
    
}
