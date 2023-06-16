/*
* Autor: Samuel Martínez Pazos
* Fecha de creación: 09/05/2023
* Descripción: Clase encargada de modelar una revision para un anteproyecto
*/
package jfxspger.modelo.pojo;

public class RevisionAnteproyecto {
    
    private int idRevisionAnteproyecto;
    private int idAnteproyecto;
    private String fechaRevision;
    private String comentarioRevision;

    public RevisionAnteproyecto() {
    }

    public RevisionAnteproyecto(int idRevisionAnteproyecto, int idAnteproyecto, 
            String fechaRevision, String comentarioRevision) {
        this.idRevisionAnteproyecto = idRevisionAnteproyecto;
        this.idAnteproyecto = idAnteproyecto;
        this.fechaRevision = fechaRevision;
        this.comentarioRevision = comentarioRevision;
    }

    public int getIdRevisionAnteproyecto() {
        return idRevisionAnteproyecto;
    }

    public void setIdRevisionAnteproyecto(int idRevisionAnteproyecto) {
        this.idRevisionAnteproyecto = idRevisionAnteproyecto;
    }

    public int getIdAnteproyecto() {
        return idAnteproyecto;
    }

    public void setIdAnteproyecto(int idAnteproyecto) {
        this.idAnteproyecto = idAnteproyecto;
    }

    public String getFechaRevision() {
        return fechaRevision;
    }

    public void setFechaRevision(String fechaRevision) {
        this.fechaRevision = fechaRevision;
    }

    public String getComentarioRevision() {
        return comentarioRevision;
    }

    public void setComentarioRevision(String comentarioRevision) {
        this.comentarioRevision = comentarioRevision;
    }

    @Override
    public String toString() {
        return "revisionAnteproyecto{" + "idRevisionAnteproyecto=" + idRevisionAnteproyecto + ", idAnteproyecto=" + idAnteproyecto + ", fechaRevision=" + fechaRevision + ", comentarioRevision=" + comentarioRevision + '}';
    }
    
}
