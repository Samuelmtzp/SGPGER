/*
* Autor: Samuel Martínez Pazos
* Fecha de creación: 22/05/2023
* Descripción: Clase encargada de administrar la información de Archivo
* en la base de datos
*/
package jfxspger.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import jfxspger.modelo.ConexionBD;
import jfxspger.modelo.pojo.ActividadRespuesta;
import jfxspger.modelo.pojo.Actividad;
import jfxspger.utilidades.Constantes;

public class ActividadDAO {
    
    public static ActividadRespuesta obtenerInformacionActividad() {
        ActividadRespuesta respuesta = new ActividadRespuesta();
        Connection conexionBD = ConexionBD.abrirConexionBD();
        respuesta.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
        if (conexionBD != null) {
            try {
                String consulta = "SELECT a.idActividad, a.titulo, a.fechaCreacion, "
                    + "a.fechaInicio, a.fechaFin, a.descripcion, " +
                    "e.idEntrega, e.fechaEntrega, e.fechaCreacion " +
                    "FROM SGPGER.actividad a " +
                    "JOIN SGPGER.entrega e ON a.idActividad = e.idActividad";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                ResultSet resultado = prepararSentencia.executeQuery();
                ArrayList<Actividad> actividadConsulta = new ArrayList();
                while (resultado.next())
                {
                    Actividad actividad = new Actividad();
                    actividad.setIdActividad(resultado.getInt("idActividad"));
                    actividad.setTitulo(resultado.getString("titulo"));
                    actividad.setFechaCreacion(resultado.getString("fechaCreacion"));
                    actividad.setFechaInicio(resultado.getString("fechaInicio"));
                    actividad.setFechaFin(resultado.getString("fechaFin"));
                    actividad.setDescripcion(resultado.getString("descripcion"));
                    actividad.setIdEntrega(resultado.getInt("idEntrega"));
                    actividadConsulta.add(actividad);
                }
                respuesta.setActividades(actividadConsulta);
                conexionBD.close();
            } catch (SQLException e) {
                respuesta.setCodigoRespuesta(Constantes.ERROR_CONSULTA);
                e.printStackTrace();
            }
        } else {
            respuesta.setCodigoRespuesta(Constantes.ERROR_CONEXION);
        }
        return respuesta;
    }
    
    public static int guardarActividad(Actividad nuevaActividad) {
        int respuesta;
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if (conexionBD != null) {
            try {
                String sentencia = "INSERT INTO Actividad (idEstudiante, " + 
                        "titulo, fechaCreacion, fechaInicio, fechaFin, descripcion) " +
                        "VALUES (?,?,?,?,?,?)";
                PreparedStatement prepararSentencia =  conexionBD.prepareStatement(sentencia);
                
                prepararSentencia.setInt(1, nuevaActividad.getIdEstudiante());
                prepararSentencia.setString(2, nuevaActividad.getTitulo());
                prepararSentencia.setString(3, nuevaActividad.getFechaCreacion());
                prepararSentencia.setString(4, nuevaActividad.getFechaInicio());
                prepararSentencia.setString(5, nuevaActividad.getFechaFin());
                prepararSentencia.setString(6, nuevaActividad.getDescripcion());
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
    
    public static int modificarActividad(Actividad actividadEdicion) {
        int respuesta;
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if (conexionBD != null) {
            try {
                String sentencia = "UPDATE Actividad SET titulo = ?, fechaInicio = ?, " + 
                        "fechaFin = ?, descripcion = ? " +
                        "WHERE idActividad = ?";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);                                
                prepararSentencia.setString(1, actividadEdicion.getTitulo());                
                prepararSentencia.setString(2, actividadEdicion.getFechaInicio());
                prepararSentencia.setString(3, actividadEdicion.getFechaFin());
                prepararSentencia.setString(4, actividadEdicion.getDescripcion());
                prepararSentencia.setInt(5, actividadEdicion.getIdActividad());
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
    
    public static int eliminarActividad(int idActividad) {
        int respuesta;
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if (conexionBD != null) {
            try {
                String sentencia = "DELETE FROM Actividad WHERE idActividad = ?";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
                prepararSentencia.setInt(1, idActividad);
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
