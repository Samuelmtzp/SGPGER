/*
* Autor: Samuel Martínez Pazos
* Fecha de creación: 09/05/2023
* Descripción: Clase encargada de obtener la respuesta de consulta de usuarios
*/

package jfxspger.modelo.pojo;

import java.util.ArrayList;

public class UsuarioRespuesta {
    private int idUsuarioRegistrado;
    private int codigoRespuesta;
    private ArrayList<Usuario> usuarios;
    
    public UsuarioRespuesta() {
    }

    public UsuarioRespuesta(int idUsuarioRegistrado, int codigoRespuesta, 
            ArrayList<Usuario> usuarios) {
        this.idUsuarioRegistrado = idUsuarioRegistrado;
        this.codigoRespuesta = codigoRespuesta;
        this.usuarios = usuarios;
    }

    public int getIdUsuarioRegistrado() {
        return idUsuarioRegistrado;
    }

    public void setIdUsuarioRegistrado(int idUsuarioRegistrado) {
        this.idUsuarioRegistrado = idUsuarioRegistrado;
    }

    public int getCodigoRespuesta() {
        return codigoRespuesta;
    }

    public void setCodigoRespuesta(int codigoRespuesta) {
        this.codigoRespuesta = codigoRespuesta;
    }

    public ArrayList<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(ArrayList<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

}
