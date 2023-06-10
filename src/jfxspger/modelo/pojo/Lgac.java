/*
* Autor: Samuel Martínez Pazos
* Fecha de creación: 09/05/2023
* Descripción: Clase encargada de modelar una LGAC
*/

package jfxspger.modelo.pojo;

public class Lgac {
    
    private int idLgac;
    private String nombre;

    public Lgac() {
    }

    public Lgac(int idLgac, String nombre) {
        this.idLgac = idLgac;
        this.nombre = nombre;
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

    @Override
    public String toString() {
        return "Lgac{" + "idLgac=" + idLgac + ", nombre=" + nombre + '}';
    }
    
}
