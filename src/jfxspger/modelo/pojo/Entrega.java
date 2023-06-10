/*
* Autor: Samuel Martínez Pazos
* Fecha de creación: 09/05/2023
* Descripción: Clase encargada de modelar una entrega de actividad
*/

package jfxspger.modelo.pojo;

public class Entrega {
    
    private int idEntrega;
    private int idActividad;
    private String fechaEntrega;
    private String fechaCreacion;

    public Entrega() {
    }

    public Entrega(int idEntrega, int idActividad, String fechaEntrega, String fechaCreacion) {
        this.idEntrega = idEntrega;
        this.idActividad = idActividad;
        this.fechaEntrega = fechaEntrega;
        this.fechaCreacion = fechaCreacion;
    }

    public int getIdEntrega() {
        return idEntrega;
    }

    public void setIdEntrega(int idEntrega) {
        this.idEntrega = idEntrega;
    }

    public int getIdActividad() {
        return idActividad;
    }

    public void setIdActividad(int idActividad) {
        this.idActividad = idActividad;
    }

    public String getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(String fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public String getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    @Override
    public String toString() {
        return "Entrega{" + "idEntrega=" + idEntrega + ", idActividad=" + idActividad + ", fechaEntrega=" + fechaEntrega + ", fechaCreacion=" + fechaCreacion + '}';
    }
    
}
