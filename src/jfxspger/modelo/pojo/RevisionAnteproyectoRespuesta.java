/*
* Autor: Samuel Martínez Pazos
* Fecha de creación: 09/05/2023
* Descripción: Clase encargada de obtener la respuesta de consulta de 
* revisiones para un anteproyecto
*/
package jfxspger.modelo.pojo;

import java.util.ArrayList;

public class RevisionAnteproyectoRespuesta {
    
    private int codigoRespuesta;
    private ArrayList<RevisionAnteproyecto> revisionesAnteproyecto;

    public RevisionAnteproyectoRespuesta(int codigoRespuesta, 
            ArrayList<RevisionAnteproyecto> revisionesAnteproyecto) {
        this.codigoRespuesta = codigoRespuesta;
        this.revisionesAnteproyecto = revisionesAnteproyecto;
    }

    public int getCodigoRespuesta() {
        return codigoRespuesta;
    }

    public void setCodigoRespuesta(int codigoRespuesta) {
        this.codigoRespuesta = codigoRespuesta;
    }

    public ArrayList<RevisionAnteproyecto> getRevisionesAnteproyecto() {
        return revisionesAnteproyecto;
    }

    public void setRevisionesAnteproyecto(ArrayList<RevisionAnteproyecto> revisionesAnteproyecto) {
        this.revisionesAnteproyecto = revisionesAnteproyecto;
    }
    
}
