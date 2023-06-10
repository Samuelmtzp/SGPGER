/*
* Autor: Samuel Martínez Pazos
* Fecha de creación: 09/05/2023
* Descripción: Clase encargada de modelar un estudiante
*/

package jfxspger.modelo.pojo;

public class Estudiante {
    
    private int idEstudiante;
    private int idUsuario;
    private int idAnteproyecto;
    private String matricula;

    public Estudiante() {
    }
    
    public Estudiante(int idEstudiante, int idUsuario, int idAnteproyecto, String matricula) {
        this.idEstudiante = idEstudiante;
        this.idUsuario = idUsuario;
        this.idAnteproyecto = idAnteproyecto;
        this.matricula = matricula;
    }

    public int getIdEstudiante() {
        return idEstudiante;
    }

    public void setIdEstudiante(int idEstudiante) {
        this.idEstudiante = idEstudiante;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getIdAnteproyecto() {
        return idAnteproyecto;
    }

    public void setIdAnteproyecto(int idAnteproyecto) {
        this.idAnteproyecto = idAnteproyecto;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }    

    @Override
    public String toString() {
        return "Estudiante{" + "idEstudiante=" + idEstudiante + ", idUsuario=" + idUsuario + ", idAnteproyecto=" + idAnteproyecto + ", matricula=" + matricula + '}';
    }
    
}
