/*
* Autor: Samuel Martínez Pazos
* Fecha de creación: 09/05/2023
* Descripción: Clase encargada de modelar una LGAC
*/

package jfxspger.modelo.pojo;

public class Lgac {
    
    private int idLgac;
    private String nombre;
    private int idCuerpoAcademico;
    private String cuerpoAcademico;

    public Lgac() {
    }

    public Lgac(int idLgac, String nombre, int idCuerpoAcademico, String cuerpoAcademico) {
        this.idLgac = idLgac;
        this.nombre = nombre;
        this.idCuerpoAcademico=idCuerpoAcademico;
        this.cuerpoAcademico=cuerpoAcademico;
    }

    public int getIdLgac() {
        return idLgac;
    }

    public void setIdLgac(int idLgac) {
        this.idLgac = idLgac;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getIdCuerpoAcademico() {
        return idCuerpoAcademico;
    }

    public void setIdCuerpoAcademico(int idCuerpoAcademico) {
        this.idCuerpoAcademico = idCuerpoAcademico;
    }

    public String getCuerpoAcademico() {
        return cuerpoAcademico;
    }

    public void setCuerpoAcademico(String cuerpoAcademico) {
        this.cuerpoAcademico = cuerpoAcademico;
    }

    @Override
    public String toString() {
        return nombre;
    }
    
}
