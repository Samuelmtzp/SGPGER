/*
* Autor: Samuel Martínez Pazos
* Fecha de creación: 09/05/2023
* Descripción: Clase encargada de modelar las modalidades
*/
package jfxspger.modelo.pojo;

public class Modalidad {
    
    private int idModalidad;
    private String modalidad;

    public Modalidad() {
    }

    public Modalidad(int idModalidad, String modalidad) {
        this.idModalidad = idModalidad;
        this.modalidad = modalidad;
    }

    public int getIdModalidad() {
        return idModalidad;
    }

    public void setIdModalidad(int idModalidad) {
        this.idModalidad = idModalidad;
    }

    public String getModalidad() {
        return modalidad;
    }

    public void setModalidad(String modalidad) {
        this.modalidad = modalidad;
    }

    @Override
    public String toString() {
        return modalidad;
    }
    
}
