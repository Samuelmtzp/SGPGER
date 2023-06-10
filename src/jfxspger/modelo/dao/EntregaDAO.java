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
import java.util.ArrayList;
import jfxspger.modelo.ConexionBD;
import jfxspger.modelo.pojo.EntregaRespuesta;
import jfxspger.modelo.pojo.Entrega;
import jfxspger.utilidades.Constantes;

public class EntregaDAO {
    
    public static EntregaRespuesta obtenerInformacionEntrega() {
        EntregaRespuesta respuesta = new EntregaRespuesta();
        Connection conexionBD = ConexionBD.abrirConexionBD();
        respuesta.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
        if (conexionBD != null) {
            try {
                String consulta = "SELECT idEntrega, idActividad, fechaEntrega, fechaCreacion " +
                        "FROM Entrega";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                ResultSet resultado = prepararSentencia.executeQuery();
                ArrayList<Entrega> entregaConsulta = new ArrayList();
                while (resultado.next())
                {
                    Entrega entrega = new Entrega();
                    entrega.setIdEntrega(resultado.getInt("idEntrega"));
                    entrega.setIdActividad(resultado.getInt("idActividad"));
                    entrega.setFechaEntrega(resultado.getString("fechaEntrega"));
                    entrega.setFechaEntrega(resultado.getString("fechaCreacion"));
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
    
    public static int guardarEntrega(Entrega nuevaEntrega) {
        int respuesta;
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if (conexionBD != null) {
            try {
                String sentencia = "INSERT INTO Entrega (idActividad, " + 
                        "fechaEntrega, fechaCreacion) " +
                        "VALUES (?,?,?)";
                PreparedStatement prepararSentencia =  conexionBD.prepareStatement(sentencia);
                prepararSentencia.setInt(1, nuevaEntrega.getIdActividad());
                prepararSentencia.setString(2, nuevaEntrega.getFechaEntrega());
                prepararSentencia.setString(3, nuevaEntrega.getFechaCreacion());
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
