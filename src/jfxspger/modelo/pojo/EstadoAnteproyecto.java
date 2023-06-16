/*
* Autor: Samuel Martínez Pazos
* Fecha de creación: 09/05/2023
* Descripción: Clase encargada de modelar un estado de anteproyecto
*/
package jfxspger.modelo.pojo;

public class EstadoAnteproyecto {
    
    private int idEstadoAnteproyecto;
    private String estado;

    public EstadoAnteproyecto() {
    }

    public EstadoAnteproyecto(int idEstadoAnteproyecto, String estado) {
        this.idEstadoAnteproyecto = idEstadoAnteproyecto;
        this.estado = estado;
    }

    public int getIdEstadoAnteproyecto() {
        return idEstadoAnteproyecto;
    }

    public void setIdEstadoAnteproyecto(int idEstadoAnteproyecto) {
        this.idEstadoAnteproyecto = idEstadoAnteproyecto;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return estado;
    }
    
}
