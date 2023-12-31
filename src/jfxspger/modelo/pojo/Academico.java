/*
* Autor: Samuel Martínez Pazos
* Fecha de creación: 09/05/2023
* Descripción: Clase encargada de modelar un academico
*/

package jfxspger.modelo.pojo;

public class Academico {
    
    private int idAcademico;
    private int idUsuario;
    private int numeroDePersonal;
    private int idCuerpoAcademico;

    public Academico() {
    }

    public Academico(int idAcademico, int idUsuario, int numeroDePersonal, int idCuerpoAcademico) {
        this.idAcademico = idAcademico;
        this.idUsuario = idUsuario;
        this.numeroDePersonal = numeroDePersonal;
        this.idCuerpoAcademico = idCuerpoAcademico;
    }

    public int getIdAcademico() {
        return idAcademico;
    }

    public void setIdAcademico(int idAcademico) {
        this.idAcademico = idAcademico;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getNumeroDePersonal() {
        return numeroDePersonal;
    }

    public void setNumeroDePersonal(int numeroDePersonal) {
        this.numeroDePersonal = numeroDePersonal;
    }

    public int getIdCuerpoAcademico() {
        return idCuerpoAcademico;
    }

    public void setIdCuerpoAcademico(int idCuerpoAcademico) {
        this.idCuerpoAcademico = idCuerpoAcademico;
    }

    @Override
    public String toString() {
        return "Academico{" + "idAcademico=" + idAcademico + ", idUsuario=" + 
                idUsuario + ", numeroDePersonal=" + numeroDePersonal + 
                ", idCuerpoAcademico=" + idCuerpoAcademico + '}';
    }

}
