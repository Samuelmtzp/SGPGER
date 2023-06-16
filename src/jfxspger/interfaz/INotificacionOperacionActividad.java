/*
* Autor: Carlos Rodriguez Herrera
* Fecha de creación: 30/05/2023
* Descripción: Interfaz para notificar acciones de actualizar o guardar
*/
package jfxspger.interfaz;

public interface INotificacionOperacionActividad {
    public void notificarOperacionGuardar(String nombreActividad);    
    public void notificarOperacionActualizar(String nombreActividad);
}
