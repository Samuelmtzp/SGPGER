/*
* Autor: Samuel Martínez Pazos
* Fecha de creación: 22/05/2023
* Descripción: Clase encargada de modelar el archivo anexado a una entrega
*/

package jfxspger.modelo.pojo;

public class Documento {
    
    private int idDocumento;
    private byte[] archivoDocumento;
    private String nombre;
    private int idEntrega;

    public Documento() {
    }

    public Documento(int idDocumento, byte[] archivoDocumento, String nombre, int entrega_idEntrega) {
        this.idDocumento = idDocumento;
        this.archivoDocumento = archivoDocumento;
        this.nombre = nombre;
        this.idEntrega = entrega_idEntrega;
    }

    public int getIdDocumento() {
        return idDocumento;
    }

    public void setIdDocumento(int idDocumento) {
        this.idDocumento = idDocumento;
    }

    public byte[] getArchivoDocumento() {
        return archivoDocumento;
    }

    public void setArchivoDocumento(byte[] archivoDocumento) {
        this.archivoDocumento = archivoDocumento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getIdEntrega() {
        return idEntrega;
    }

    public void setIdEntrega(int idEntrega) {
        this.idEntrega = idEntrega;
    }

    @Override
    public String toString() {
        return "Documento{" + "idDocumento=" + idDocumento + ", archivoDocumento=" + archivoDocumento + ", nombre=" + nombre + ", idEntrega=" + idEntrega + '}';
    }

}
