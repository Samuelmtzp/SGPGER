/*
 * Autor: Samuel Martínez Pazos
 * Fecha de creación: 20/06/2023
 * Descripción: Clase singleton para usuario 
 */
package jfxspger.utilidades;

import jfxspger.modelo.pojo.Usuario;

public class SingletonUsuario {
    
    private static SingletonUsuario instancia;
    private Usuario usuario;
    
    private SingletonUsuario() {
    }
    
    public static SingletonUsuario getInstancia() {
        if (instancia == null) {
            instancia = new SingletonUsuario();
        }
        return instancia;
    }
    
    public Usuario getUsuario() {
        return usuario;
    }
    
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
}
