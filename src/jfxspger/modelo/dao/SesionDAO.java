/*
* Autor: Samuel Martínez Pazos
* Fecha de creación: 09/05/2023
* Descripción: Clase encargada de verificar la sesión del usuario
*/

package jfxspger.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import jfxspger.modelo.ConexionBD;
import jfxspger.modelo.pojo.Usuario;
import jfxspger.utilidades.Constantes;

public class SesionDAO 
{
    public static Usuario verificarUsuarioSesion(String usuario, String password)
    {
        Usuario usuarioVerificado = new Usuario();
        Connection conexion = ConexionBD.abrirConexionBD();
        if (conexion != null)
        {
            try
            {
                String consulta = "SELECT * FROM Usuario INNER JOIN TipoUsuario " + 
                        "ON Usuario.idTipoUsuario = TipoUsuario.idTipoUsuario " +
                        "WHERE username = ? AND password = ?";
                PreparedStatement prepararSentencia = conexion.prepareStatement(consulta);
                prepararSentencia.setString(1, usuario);
                prepararSentencia.setString(2, password);
                ResultSet resultado = prepararSentencia.executeQuery();
                usuarioVerificado.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
                if (resultado.next()) 
                {
                    usuarioVerificado.setIdUsuario(resultado.getInt("idUsuario"));
                    usuarioVerificado.setIdTipoUsuario(resultado.getInt("idTipoUsuario"));
                    usuarioVerificado.setTipoUsuario(resultado.getString("tipoUsuario"));
                    usuarioVerificado.setUsername(resultado.getString("username"));
                    usuarioVerificado.setPassword(resultado.getString("password"));
                    usuarioVerificado.setCorreo(resultado.getString("correo"));
                    usuarioVerificado.setNombre(resultado.getString("nombre"));
                    usuarioVerificado.setApellidoPaterno(resultado.getString("apellidoPaterno"));
                    usuarioVerificado.setApellidoMaterno(resultado.getString("apellidoMaterno"));
                    usuarioVerificado.setTelefono(resultado.getString("telefono"));
                    usuarioVerificado.setFechaCreacion(resultado.getString("fechaCreacion"));
                }
                conexion.close();
            } catch (SQLException ex) {
                usuarioVerificado.setCodigoRespuesta(Constantes.ERROR_CONSULTA);
            }
        } else {
            usuarioVerificado.setCodigoRespuesta(Constantes.ERROR_CONEXION);
        }
        return usuarioVerificado;
    }
}