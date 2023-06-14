/*
* Autor: Samuel Martínez Pazos
* Fecha de creación: 22/05/2023
* Descripción: Clase encargada de modelar una Experiencia Educativa de un curso
*/

package jfxspger.modelo.pojo;

public class ExperienciaEducativa {
    
    private int idExperienciaEducativa;
    private String nombre;

    public ExperienciaEducativa() {
    }

    public ExperienciaEducativa(int idExperienciaEducativa, String nombre) {
        this.idExperienciaEducativa = idExperienciaEducativa;
        this.nombre = nombre;
    }

    public int getIdExperienciaEducativa() {
        return idExperienciaEducativa;
    }

    public void setIdExperienciaEducativa(int idExperienciaEducativa) {
        this.idExperienciaEducativa = idExperienciaEducativa;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return nombre;
    }
    
}
