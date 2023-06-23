/*
* Autor: Samuel Martínez Pazos
* Fecha de creación: 22/05/2023
* Descripción: Clase encargada de administrar la información de calificación
* en la base de datos
*/
package jfxspger.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import jfxspger.modelo.ConexionBD;
import jfxspger.modelo.pojo.CalificacionRespuesta;
import jfxspger.modelo.pojo.Calificacion;
import jfxspger.utilidades.Constantes;

public class CalificacionDAO {
    
    public static CalificacionRespuesta obtenerInformacionCalificacion() {
        CalificacionRespuesta respuesta = new CalificacionRespuesta();
        Connection conexionBD = ConexionBD.abrirConexionBD();
        respuesta.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
        if (conexionBD != null) {
            try {
                String consulta = "SELECT idCalificacion, idActividad, " +
                        "calificacion, comentario " +
                        "FROM Calificacion";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                ResultSet resultado = prepararSentencia.executeQuery();
                ArrayList<Calificacion> calificacionConsulta = new ArrayList();
                while (resultado.next())
                {
                    Calificacion calificacion = new Calificacion();
                    calificacion.setIdCalificacion(resultado.getInt("idCalificacion"));
                    calificacion.setIdActividad(resultado.getInt("idActividad"));
                    calificacion.setCalificacion(resultado.getDouble("calificacion"));
                    calificacion.setComentario(resultado.getString("comentario"));
                    calificacionConsulta.add(calificacion);
                }
                respuesta.setCalificaciones(calificacionConsulta);
                conexionBD.close();
            } catch (SQLException e) {
                respuesta.setCodigoRespuesta(Constantes.ERROR_CONSULTA);
            }
        } else {
            respuesta.setCodigoRespuesta(Constantes.ERROR_CONEXION);
        }
        return respuesta;
    }
    
    public static Calificacion obtenerInformacionCalificacionPorActividad(int idActividad) {
        CalificacionRespuesta respuesta = new CalificacionRespuesta();
        Calificacion califRespuesta = new Calificacion();
        Connection conexionBD = ConexionBD.abrirConexionBD();
        respuesta.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
        if (conexionBD != null) {
            try {
                String consulta = "SELECT idCalificacion, idActividad, " +
                        "calificacion, comentario " +
                        "FROM Calificacion";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                ResultSet resultado = prepararSentencia.executeQuery();
                ArrayList<Calificacion> calificacionConsulta = new ArrayList();
                while (resultado.next())
                {
                    Calificacion calificacion = new Calificacion();
                    calificacion.setIdCalificacion(resultado.getInt("idCalificacion"));
                    calificacion.setIdActividad(resultado.getInt("idActividad"));
                    calificacion.setCalificacion(resultado.getDouble("calificacion"));
                    calificacion.setComentario(resultado.getString("comentario"));
                    calificacionConsulta.add(calificacion);
                }
                respuesta.setCalificaciones(calificacionConsulta);
                conexionBD.close();
            } catch (SQLException e) {
                respuesta.setCodigoRespuesta(Constantes.ERROR_CONSULTA);
            }
        } else {
            respuesta.setCodigoRespuesta(Constantes.ERROR_CONEXION);
        }
        return califRespuesta;
    }
    
    public static int guardarCalificacion(Calificacion nuevaCalificacion) {
        int respuesta;
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if (conexionBD != null) {
            try {
                String sentencia = "INSERT INTO Calificacion (idActividad, " + 
                        "calificacion, comentario) " +
                        "VALUES (?,?,?)";
                PreparedStatement prepararSentencia =  conexionBD.prepareStatement(sentencia);
                prepararSentencia.setInt(1, nuevaCalificacion.getIdActividad());
                prepararSentencia.setDouble(2, nuevaCalificacion.getCalificacion());
                prepararSentencia.setString(3, nuevaCalificacion.getComentario());
                int filasAfectadas = prepararSentencia.executeUpdate();
                respuesta = (filasAfectadas == 1) ? Constantes.OPERACION_EXITOSA : 
                        Constantes.ERROR_CONSULTA;
                conexionBD.close();
            } catch (SQLException e) {
                respuesta = Constantes.ERROR_CONSULTA;
                e.printStackTrace();
            }
        } else {
            respuesta = Constantes.ERROR_CONEXION;
        }
        return respuesta;
    }
    
    public static int modificarCalificacion(Calificacion calificacionEdicion) {
        int respuesta;
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if (conexionBD != null) {
            try {
                String sentencia = "UPDATE Calificacion SET calificacion = ?, comentario = ? " +                       
                        "WHERE idCalificacion = ?";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);                
                prepararSentencia.setDouble(1, calificacionEdicion.getCalificacion());
                prepararSentencia.setString(2, calificacionEdicion.getComentario());
                prepararSentencia.setInt(3, calificacionEdicion.getIdCalificacion());
                int filasAfectadas = prepararSentencia.executeUpdate();
                respuesta = (filasAfectadas == 1) ? Constantes.OPERACION_EXITOSA : 
                        Constantes.ERROR_CONSULTA;
                conexionBD.close();
            } catch (SQLException e) {
                respuesta = Constantes.ERROR_CONSULTA;
                e.printStackTrace();
            }
        } else {
            respuesta = Constantes.ERROR_CONEXION;
        }
        return respuesta;
    }
    
    public static int eliminarCalificacion(int idCalificacion) {
        int respuesta;
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if (conexionBD != null) {
            try {
                String sentencia = "DELETE FROM Calificacion WHERE idCalificacion = ?";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
                prepararSentencia.setInt(1, idCalificacion);
                int filasAfectadas = prepararSentencia.executeUpdate();
                respuesta = (filasAfectadas == 1) ? Constantes.OPERACION_EXITOSA : 
                        Constantes.ERROR_CONSULTA;
                conexionBD.close();
            } catch (SQLException e) {
                respuesta = Constantes.ERROR_CONSULTA;
                e.printStackTrace();
            }
        } else {
            respuesta = Constantes.ERROR_CONEXION;
        }
        return respuesta;
    }
}
