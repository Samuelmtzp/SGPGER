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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import jfxspger.modelo.ConexionBD;
import jfxspger.modelo.pojo.ActividadRespuesta;
import jfxspger.modelo.pojo.Actividad;
import jfxspger.utilidades.Constantes;
import jfxspger.utilidades.Utilidades;

public class ActividadDAO {

    public static ActividadRespuesta obtenerInfoEvaluacionActividad(int idEstudiante) {
        ActividadRespuesta respuesta = new ActividadRespuesta();
        Connection conexionBD = ConexionBD.abrirConexionBD();
        respuesta.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
        if (conexionBD != null) {
            try {
                String consulta = "SELECT a.*, e.idEntrega, e.fechaEntrega, ea.idEstadoActividad, ea.estado, c.idCalificacion, c.calificacion, c.comentario " +
                                    "FROM actividad a " +
                                    "INNER JOIN estadoActividad ea ON a.idEstado = ea.idEstadoActividad " +
                                    "LEFT JOIN entrega e ON a.idActividad = e.idActividad " +
                                    "LEFT JOIN calificacion c ON a.idActividad = c.idActividad " +
                                    "WHERE a.idEstudiante = ?;";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                prepararSentencia.setInt(1, idEstudiante);
                ResultSet resultado = prepararSentencia.executeQuery();
                ArrayList<Actividad> actividadConsulta = new ArrayList();            
                while (resultado.next())
                {
                    Actividad actividad = new Actividad();
                    actividad.setIdActividad(resultado.getInt("idActividad"));
                    actividad.setIdEntrega(resultado.getInt("idEntrega"));
                    actividad.setTitulo(resultado.getString("titulo"));
                    actividad.setFechaCreacion(Utilidades.convertirTimeStampAStringFechaHora((resultado.getTimestamp("fechaCreacion"))));
                    actividad.setFechaInicio(Utilidades.convertirTimeStampAStringFechaHora((resultado.getTimestamp("fechaInicio"))));
                    actividad.setFechaFin(Utilidades.convertirTimeStampAStringFechaHora((resultado.getTimestamp("fechaFin"))));
                    actividad.setDescripcion(resultado.getString("descripcion"));                                       
                    actividad.setCalificacion(resultado.getDouble("calificacion"));
                    actividad.setIdCalificacion(resultado.getInt("idCalificacion"));
                    actividad.setIdEstado(resultado.getInt("idEstado"));
                    actividad.setEstado(resultado.getString("estado"));
                    actividad.setCommentCalif(resultado.getString("comentario"));
                    
                    if (resultado.getTimestamp("fechaEntrega") == null){
                    actividad.setFechaEntrega("Sin entrega");
                    } else {
                    actividad.setFechaEntrega(Utilidades.convertirTimeStampAStringFechaHora(resultado.getTimestamp("fechaEntrega")));    
                    }
                    
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
    
  public static ActividadRespuesta obtenerInformacionActividad(int idEstudiante) {
        ActividadRespuesta respuesta = new ActividadRespuesta();
        Connection conexionBD = ConexionBD.abrirConexionBD();
        respuesta.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
        if (conexionBD != null) {
            try {
                String consulta = "SELECT a.*, ea.idEstadoActividad, ea.estado, c.calificacion, c.comentario " +
                                    "FROM actividad a " +
                                    "INNER JOIN estadoActividad ea ON a.idEstado = ea.idEstadoActividad " +
                                    "LEFT JOIN Calificacion c ON a.idActividad = c.idActividad " +
                                    "WHERE a.idEstudiante = ?;";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                prepararSentencia.setInt(1, idEstudiante);
                ResultSet resultado = prepararSentencia.executeQuery();
                ArrayList<Actividad> actividadConsulta = new ArrayList();            
                while (resultado.next())
                {
                    Actividad actividad = new Actividad();
                    actividad.setIdActividad(resultado.getInt("idActividad"));
                    actividad.setTitulo(resultado.getString("titulo"));
                    actividad.setFechaCreacion(Utilidades.convertirTimeStampAStringFechaHora(resultado.getTimestamp("fechaCreacion")));
                    actividad.setFechaInicio(Utilidades.convertirTimeStampAStringFechaHora(resultado.getTimestamp("fechaInicio")));
                    actividad.setFechaFin(Utilidades.convertirTimeStampAStringFechaHora(resultado.getTimestamp("fechaFin")));
                    actividad.setDescripcion(resultado.getString("descripcion"));
                    actividad.setIdEstado(resultado.getInt("idEstadoActividad"));
                    actividad.setEstado(resultado.getString("estado"));
                    actividad.setCalificacion(resultado.getDouble("calificacion"));
                    actividad.setCommentCalif(resultado.getString("comentario"));
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
    
    public static Actividad obtenerDetallesActividad(int idActividad) {
        ActividadRespuesta respuesta = new ActividadRespuesta();
        Actividad actividad = new Actividad();
        Connection conexionBD = ConexionBD.abrirConexionBD();
        respuesta.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
        if (conexionBD != null) {
            try {
                String consulta = "SELECT titulo, fechaCreacion, fechaInicio, fechaFin, descripcion " +
                                    "FROM actividad " +
                                    "WHERE idActividad = ?;";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                prepararSentencia.setInt(1, idActividad);
                ResultSet resultado = prepararSentencia.executeQuery();

                actividad.setTitulo(resultado.getString("titulo"));
                actividad.setFechaCreacion(Utilidades.convertirTimeStampAStringFechaHora(resultado.getTimestamp("fechaCreacion")));
                actividad.setFechaInicio(Utilidades.convertirTimeStampAStringFechaHora(resultado.getTimestamp("fechaInicio")));
                actividad.setFechaFin(Utilidades.convertirTimeStampAStringFechaHora(resultado.getTimestamp("fechaFin")));
                actividad.setDescripcion(resultado.getString("descripcion"));
                    conexionBD.close();
                }            
             catch (SQLException e) {
                respuesta.setCodigoRespuesta(Constantes.ERROR_CONSULTA);
                e.printStackTrace();
            }
        } else {
            respuesta.setCodigoRespuesta(Constantes.ERROR_CONEXION);
        }
        return actividad;
    }
    
    public static ActividadRespuesta obtenerActividadesYEntregas(int idEstudiante) {
        ActividadRespuesta respuesta = new ActividadRespuesta();
        Connection conexionBD = ConexionBD.abrirConexionBD();
        respuesta.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
        if (conexionBD != null) {
            try {
                String consulta = "SELECT a.idActividad, a.titulo, a.fechaInicio, a.fechaFin, e.idEntrega, e.fechaEntrega " +
                                        "FROM actividad a " +
                                        "LEFT JOIN entrega e ON a.idActividad = e.idActividad " +
                                        "WHERE a.idEstudiante = ?;";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                prepararSentencia.setInt(1, idEstudiante);
                ResultSet resultado = prepararSentencia.executeQuery();                
                ArrayList<Actividad> actividadConsulta = new ArrayList();
                while (resultado.next())
                {
                    Actividad actividad = new Actividad();
                    actividad.setIdActividad(resultado.getInt("idActividad"));
                    actividad.setTitulo(resultado.getString("titulo"));                    
                    actividad.setFechaInicio(Utilidades.convertirTimeStampAStringFechaHora(resultado.getTimestamp("fechaInicio")));
                    actividad.setFechaFin(Utilidades.convertirTimeStampAStringFechaHora(resultado.getTimestamp("fechaFin")));
                    actividad.setIdEntrega(resultado.getInt("idEntrega"));
                    actividad.setFechaCreacion(Utilidades.convertirTimeStampAStringFechaHora(resultado.getTimestamp("fechaEntrega")));
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
                String sentencia = "INSERT INTO actividad (idEstudiante, titulo, fechaCreacion, fechaInicio, fechaFin, descripcion, idEstado) " +
                        "VALUES (?,?,?,?,?,?, ?)";
                PreparedStatement prepararSentencia =  conexionBD.prepareStatement(sentencia);
                
                prepararSentencia.setInt(1, nuevaActividad.getIdEstudiante());
                prepararSentencia.setString(2, nuevaActividad.getTitulo());
                prepararSentencia.setString(3, nuevaActividad.getFechaCreacion());
                prepararSentencia.setString(4, nuevaActividad.getFechaInicio());
                prepararSentencia.setString(5, nuevaActividad.getFechaFin());
                prepararSentencia.setString(6, nuevaActividad.getDescripcion());
                prepararSentencia.setInt(7, nuevaActividad.getIdEstado());
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
    
    public static int actualizarEstadoActividad(int idEstado, int idActividad) {
        int respuesta;
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if (conexionBD != null) {
            try {
                String sentencia = "UPDATE Actividad SET idEstado = ? " + 
                        "WHERE idActividad = ?";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
                prepararSentencia.setInt(1, idEstado);
                prepararSentencia.setInt(2, idActividad);
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