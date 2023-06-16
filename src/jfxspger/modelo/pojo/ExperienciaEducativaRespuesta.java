/*
* Autor: Samuel Martínez Pazos
* Fecha de creación: 22/05/2023
* Descripción: Clase encargada de obtener la respuesta de consulta de Experiencias Educativas
*/
package jfxspger.modelo.pojo;

import java.util.ArrayList;

public class ExperienciaEducativaRespuesta {
    
    private int codigoRespuesta;
    private ArrayList<ExperienciaEducativa> experienciasEducativas;

    public ExperienciaEducativaRespuesta() {
    }
    
    public ExperienciaEducativaRespuesta(int codigoRespuesta, 
            ArrayList<ExperienciaEducativa> experienciasEducativas) {
        this.codigoRespuesta = codigoRespuesta;
        this.experienciasEducativas = experienciasEducativas;
    }

    public int getCodigoRespuesta() {
        return codigoRespuesta;
    }

    public void setCodigoRespuesta(int codigoRespuesta) {
        this.codigoRespuesta = codigoRespuesta;
    }

    public ArrayList<ExperienciaEducativa> getExperienciasEducativas() {
        return experienciasEducativas;
    }

    public void setExperienciasEducativas(ArrayList<ExperienciaEducativa> experienciasEducativas) {
        this.experienciasEducativas = experienciasEducativas;
    }
    
}
