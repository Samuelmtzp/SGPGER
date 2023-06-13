/*
* Autor: Samuel Martínez Pazos
* Fecha de creación: 09/05/2023
* Descripción: Clase encargada de administrar la información de usuario
* en la base de datos
*/

package jfxspger.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import jfxspger.modelo.ConexionBD;
import jfxspger.modelo.pojo.Usuario;
import jfxspger.modelo.pojo.UsuarioRespuesta;
import jfxspger.utilidades.Constantes;

public class UsuarioDAO {
    
    public static UsuarioRespuesta obtenerInformacionUsuarios() {
        UsuarioRespuesta respuesta = new UsuarioRespuesta();
        Connection conexionBD = ConexionBD.abrirConexionBD();
        respuesta.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
        if (conexionBD != null) {
            try {
                String consulta = "SELECT idUsuario, Usuario.idTipoUsuario, "
                        + "TipoUsuario.tipoUsuario, username, password, correo, "
                        + "nombre, apellidoPaterno, apellidoMaterno, telefono, fechaCreacion "
                        + "FROM Usuario "
                        + "INNER JOIN TipoUsuario "
                        + "ON Usuario.idTipoUsuario = TipoUsuario.idTipoUsuario";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                ResultSet resultado = prepararSentencia.executeQuery();
                ArrayList<Usuario> usuariosConsulta = new ArrayList();
                while (resultado.next())
                {
                    Usuario usuario = new Usuario();
                    usuario.setIdUsuario(resultado.getInt("idUsuario"));
                    usuario.setIdTipoUsuario(resultado.getInt("Usuario.idTipoUsuario"));
                    usuario.setTipoUsuario(resultado.getString("TipoUsuario.tipoUsuario"));
                    usuario.setUsername(resultado.getString("username"));
                    usuario.setPassword(resultado.getString("password"));
                    usuario.setCorreo(resultado.getString("correo"));
                    usuario.setNombre(resultado.getString("nombre"));
                    usuario.setApellidoPaterno(resultado.getString("apellidoPaterno"));
                    usuario.setApellidoMaterno(resultado.getString("apellidoMaterno"));
                    usuario.setTelefono(resultado.getString("telefono"));
                    usuario.setFechaCreacion(resultado.getString("fechaCreacion"));
                    usuariosConsulta.add(usuario);
                }
                respuesta.setUsuarios(usuariosConsulta);
                conexionBD.close();
            } catch (SQLException e) {
                respuesta.setCodigoRespuesta(Constantes.ERROR_CONSULTA);
            }
        } else {
            respuesta.setCodigoRespuesta(Constantes.ERROR_CONEXION);
        }
        return respuesta;
    }
    
    public static UsuarioRespuesta guardarUsuario(Usuario usuarioNuevo) {
        UsuarioRespuesta respuesta = new UsuarioRespuesta();
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if (conexionBD != null) {
            try {
                String sentencia = "INSERT INTO Usuario (idTipoUsuario, username, " + 
                        "password, correo, nombre, apellidoPaterno, apellidoMaterno, telefono, " + 
                        "fechaCreacion) " + 
                        "VALUES (?,?,?,?,?,?,?,?, CURRENT_TIMESTAMP)";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia, 
                                Statement.RETURN_GENERATED_KEYS);
                prepararSentencia.setInt(1, usuarioNuevo.getIdTipoUsuario());
                prepararSentencia.setString(2, usuarioNuevo.getUsername());
                prepararSentencia.setString(3, usuarioNuevo.getPassword());
                prepararSentencia.setString(4, usuarioNuevo.getCorreo());
                prepararSentencia.setString(5, usuarioNuevo.getNombre());                
                prepararSentencia.setString(6, usuarioNuevo.getApellidoPaterno());
                prepararSentencia.setString(7, usuarioNuevo.getApellidoMaterno());
                prepararSentencia.setString(8, usuarioNuevo.getTelefono());
                
                int filasAfectadas = prepararSentencia.executeUpdate();
                if (filasAfectadas == 1) {
                    respuesta.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
                    ResultSet resultadoLlave = prepararSentencia.getGeneratedKeys();
                    if (resultadoLlave.next()) {
                        respuesta.setIdUsuarioRegistrado(resultadoLlave.getInt(1));
                    }
                }
                else
                    respuesta.setCodigoRespuesta(Constantes.ERROR_CONSULTA);
                conexionBD.close();
            } catch (SQLException e) {
                respuesta.setCodigoRespuesta(Constantes.ERROR_CONSULTA);
            }
        } else {
            respuesta.setCodigoRespuesta(Constantes.ERROR_CONEXION);
        }
        return respuesta;
    }
    
    public static int modificarUsuario(Usuario usuarioEdicion) {
        int respuesta;
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if (conexionBD != null) {
            try {
                String sentencia = "UPDATE Usuario SET username = ?, " +
                        "password = ?, correo = ?, nombre = ?, apellidoPaterno = ?, " +
                        "apellidoMaterno = ?, telefono = ? " + 
                        "WHERE idUsuario = ?";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
                prepararSentencia.setString(1, usuarioEdicion.getUsername());
                prepararSentencia.setString(2, usuarioEdicion.getPassword());
                prepararSentencia.setString(3, usuarioEdicion.getCorreo());
                prepararSentencia.setString(4, usuarioEdicion.getNombre());
                prepararSentencia.setString(5, usuarioEdicion.getApellidoPaterno());
                prepararSentencia.setString(6, usuarioEdicion.getApellidoMaterno());
                prepararSentencia.setString(7, usuarioEdicion.getTelefono());
                prepararSentencia.setInt(8, usuarioEdicion.getIdUsuario());
                int filasAfectadas = prepararSentencia.executeUpdate();
                respuesta = (filasAfectadas == 1) ? Constantes.OPERACION_EXITOSA : 
                        Constantes.ERROR_CONSULTA;
                conexionBD.close();
            } catch (SQLException e) {
                respuesta = Constantes.ERROR_CONSULTA;
            }
        } else {
            respuesta = Constantes.ERROR_CONEXION;
        }
        return respuesta;
    }
    
    public static int eliminarUsuario(int idUsuario) {
        int respuesta;
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if (conexionBD != null) {
            try {
                String sentencia = "DELETE FROM Usuario WHERE idUsuario = ?";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
                prepararSentencia.setInt(1, idUsuario);
                int filasAfectadas = prepararSentencia.executeUpdate();
                respuesta = (filasAfectadas == 1) ? Constantes.OPERACION_EXITOSA : 
                        Constantes.ERROR_CONSULTA;
                conexionBD.close();
            } catch (SQLException e) {
                respuesta = Constantes.ERROR_CONSULTA;
            }
        } else {
            respuesta = Constantes.ERROR_CONEXION;
        }
        return respuesta;
    }
}