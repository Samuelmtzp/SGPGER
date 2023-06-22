/*
* Autor: Samuel Martínez Pazos
* Fecha de creación: 22/05/2023
* Descripción: Clase encargada de administrar la información de entrega
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
import jfxspger.modelo.pojo.EntregaRespuesta;
import jfxspger.modelo.pojo.Entrega;
import jfxspger.utilidades.Constantes;

public class EntregaDAO {
    
    public static Entrega obtenerInformacionEntrega(int idActividad) {
        EntregaRespuesta respuesta = new EntregaRespuesta();
        Entrega entrega = new Entrega();
        Connection conexionBD = ConexionBD.abrirConexionBD();
        respuesta.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
        if (conexionBD != null) {
            try {
                String consulta = "SELECT idEntrega, fechaEntrega, fechaCreacion FROM entrega"
                        + " WHERE idActividad = ?";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                prepararSentencia.setInt(1, idActividad);
                ResultSet resultado = prepararSentencia.executeQuery();                
                entrega.setIdEntrega(resultado.getInt("idEntrega"));
                entrega.setFechaEntrega(resultado.getString("fechaEntrega"));
                entrega.setFechaEntrega(resultado.getString("fechaCreacion"));
                conexionBD.close();
            } catch (SQLException e) {
                respuesta.setCodigoRespuesta(Constantes.ERROR_CONSULTA);
            }
        } else {
            respuesta.setCodigoRespuesta(Constantes.ERROR_CONEXION);
        }
        return entrega;
    }
    
    public static EntregaRespuesta obtenerInformacionEntregas() {
        EntregaRespuesta respuesta = new EntregaRespuesta();
        Connection conexionBD = ConexionBD.abrirConexionBD();
        respuesta.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
        if (conexionBD != null) {
            try {
                String consulta = "SELECT e.*, a.titulo " +
                                        "FROM entrega e " +
                                        "INNER JOIN actividad a ON e.idActividad = a.idActividad;";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                ResultSet resultado = prepararSentencia.executeQuery();
                ArrayList<Entrega> entregaConsulta = new ArrayList();
                while (resultado.next())
                {
                    Entrega entrega = new Entrega();
                    entrega.setIdActividad(resultado.getInt("idActividad"));
                    entrega.setIdEntrega(resultado.getInt("idEntrega"));
                    entrega.setFechaEntrega(resultado.getString("fechaEntrega"));
                    entrega.setFechaCreacion(resultado.getString("fechaCreacion"));
                    entrega.setTituloActividad(resultado.getString("titulo"));
                    entregaConsulta.add(entrega);
                }
                respuesta.setEntregas(entregaConsulta);
                conexionBD.close();
            } catch (SQLException e) {
                respuesta.setCodigoRespuesta(Constantes.ERROR_CONSULTA);
            }
        } else {
            respuesta.setCodigoRespuesta(Constantes.ERROR_CONEXION);
        }
        return respuesta;
    }
    
    public static EntregaRespuesta obtenerInformacionEntregasPorAnteproyecto(int idAnteproyecto) {
        EntregaRespuesta respuesta = new EntregaRespuesta();
        Connection conexionBD = ConexionBD.abrirConexionBD();
        respuesta.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
        if (conexionBD != null) {
            try {
                String consulta = "SELECT e.idEntrega, e.idActividad, a.titulo, e.fechaEntrega, e.fechaCreacion " +
                                    "FROM entrega e " +
                                    "INNER JOIN actividad a ON e.idActividad = a.idActividad " +
                                    "INNER JOIN estudiante est ON a.idEstudiante = est.idEstudiante " +
                                    "INNER JOIN anteproyecto ap ON est.idAnteproyecto = ap.idAnteproyecto " +
                                    "WHERE ap.idAnteproyecto = ?;";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                prepararSentencia.setInt(1, idAnteproyecto);
                ResultSet resultado = prepararSentencia.executeQuery();
                ArrayList<Entrega> entregaConsulta = new ArrayList();
                while (resultado.next())
                {
                    Entrega entrega = new Entrega();
                    entrega.setIdEntrega(resultado.getInt("idEntrega"));
                    entrega.setIdActividad(resultado.getInt("idActividad"));                   
                    entrega.setTituloActividad(resultado.getString("titulo"));
                    entrega.setFechaEntrega(resultado.getString("fechaEntrega"));
                    entrega.setFechaCreacion(resultado.getString("fechaCreacion"));
                    
                    entregaConsulta.add(entrega);
                }
                respuesta.setEntregas(entregaConsulta);
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
    
        public static EntregaRespuesta obtenerInformacionEntregasPorEstudiante(int idEstudiante) {
        EntregaRespuesta respuesta = new EntregaRespuesta();
        Connection conexionBD = ConexionBD.abrirConexionBD();
        respuesta.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
        if (conexionBD != null) {
            try {
                String consulta = "SELECT a.idActividad, a.titulo, e.fechaEntrega, c.idCalificacion, c.calificacion " +
                                    "FROM entrega e " +
                                    "JOIN actividad a ON e.idActividad = a.idActividad " +
                                    "JOIN estudiante est ON a.idEstudiante = est.idEstudiante " +
                                    "LEFT JOIN Calificacion c ON a.idActividad = c.idActividad " +
                                    "WHERE est.idEstudiante = ?;";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                prepararSentencia.setInt(1, idEstudiante);
                ResultSet resultado = prepararSentencia.executeQuery();
                ArrayList<Entrega> entregaConsulta = new ArrayList();
                while (resultado.next())
                {
                    Entrega entrega = new Entrega();
                    entrega.setIdEntrega(resultado.getInt("idActividad"));
                    entrega.setFechaEntrega(resultado.getString("fechaEntrega"));
                    entrega.setTituloActividad(resultado.getString("titulo"));
                    entrega.setCalificacion(resultado.getInt("calificacion"));
                    entregaConsulta.add(entrega);
                }
                respuesta.setEntregas(entregaConsulta);
                conexionBD.close();
            } catch (SQLException e) {
                respuesta.setCodigoRespuesta(Constantes.ERROR_CONSULTA);
            }
        } else {
            respuesta.setCodigoRespuesta(Constantes.ERROR_CONEXION);
        }
        return respuesta;
    }
    
    public static EntregaRespuesta guardarEntrega(Entrega nuevaEntrega) {
        EntregaRespuesta respuesta = new EntregaRespuesta();
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if (conexionBD != null) {
            try {
                String sentencia = "INSERT INTO Entrega (idActividad, " + 
                        "fechaEntrega, fechaCreacion) " +
                        "VALUES (?,?,?)";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia, 
                                Statement.RETURN_GENERATED_KEYS);
                prepararSentencia.setInt(1, nuevaEntrega.getIdActividad());
                prepararSentencia.setString(2, nuevaEntrega.getFechaEntrega());
                prepararSentencia.setString(3, nuevaEntrega.getFechaCreacion());
                int filasAfectadas = prepararSentencia.executeUpdate();
                if (filasAfectadas == 1) {
                    respuesta.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
                    ResultSet resultadoLlave = prepararSentencia.getGeneratedKeys();
                    if (resultadoLlave.next()) {
                        respuesta.setIdEntrega(resultadoLlave.getInt(1));
                    }
                }
                else
                    respuesta.setCodigoRespuesta(Constantes.ERROR_CONSULTA);
                conexionBD.close();
            } catch (SQLException e) {
                e.printStackTrace();
                respuesta.setCodigoRespuesta(Constantes.ERROR_CONSULTA);
            }
        } else {
            respuesta.setCodigoRespuesta(Constantes.ERROR_CONEXION);
        }
        return respuesta;
    }
    
    public static int modificarEntrega(Entrega entregaEdicion) {
        int respuesta;
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if (conexionBD != null) {
            try {
                String sentencia = "UPDATE Entrega SET idActividad = ?, " + 
                        "fechaEntrega = ?, fechaCreacion = ?" +
                        "WHERE idEntrega = ?";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
                prepararSentencia.setInt(1, entregaEdicion.getIdActividad());
                prepararSentencia.setString(2, entregaEdicion.getFechaEntrega());
                prepararSentencia.setString(3, entregaEdicion.getFechaCreacion());
                prepararSentencia.setInt(4, entregaEdicion.getIdEntrega());
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
    
    public static int eliminarEntrega(int idEntrega) {
        int respuesta;
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if (conexionBD != null) {
            try {
                String sentencia = "DELETE FROM Entrega WHERE idEntrega = ?";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
                prepararSentencia.setInt(1, idEntrega);
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
