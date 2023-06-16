/*
* Autor: Samuel Martínez Pazos
* Fecha de creación: 09/05/2023
* Descripción: Clase encargada de obtener la respuesta de consulta de los tipos de usuario
*/
package jfxspger.modelo.pojo;

import java.util.ArrayList;

public class TipoUsuarioRespuesta {
    
    private int codigoRespuesta;
    private ArrayList<TipoUsuario> tiposUsuario;

    public TipoUsuarioRespuesta() {
    }

    public TipoUsuarioRespuesta(int codigoRespuesta, ArrayList<TipoUsuario> tiposUsuario) {
        this.codigoRespuesta = codigoRespuesta;
        this.tiposUsuario = tiposUsuario;
    }

    public int getCodigoRespuesta() {
        return codigoRespuesta;
    }

    public void setCodigoRespuesta(int codigoRespuesta) {
        this.codigoRespuesta = codigoRespuesta;
    }

    public ArrayList<TipoUsuario> getTiposUsuario() {
        return tiposUsuario;
    }

    public void setTiposUsuario(ArrayList<TipoUsuario> tiposUsuario) {
        this.tiposUsuario = tiposUsuario;
    }
    
}
