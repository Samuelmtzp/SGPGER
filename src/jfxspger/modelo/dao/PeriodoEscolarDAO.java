/*
* Autor: Samuel Martínez Pazos
* Fecha de creación: 22/05/2023
* Descripción: Clase encargada de administrar la información de periodo
* en la base de datos
*/
package jfxspger.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import jfxspger.modelo.ConexionBD;
import jfxspger.modelo.pojo.PeriodoRespuesta;
import jfxspger.modelo.pojo.PeriodoEscolar;
import jfxspger.utilidades.Constantes;

public class PeriodoEscolarDAO {
    
    public static PeriodoRespuesta obtenerInformacionPeriodos() {
        PeriodoRespuesta respuesta = new PeriodoRespuesta();
        Connection conexionBD = ConexionBD.abrirConexionBD();
        respuesta.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
        if (conexionBD != null) {
            try {
                String consulta = "SELECT idPeriodoEscolar, fechaInicio, fechaFin, nombre" +
                        "FROM PeriodoEscolar";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                ResultSet resultado = prepararSentencia.executeQuery();
                ArrayList<PeriodoEscolar> periodosConsulta = new ArrayList();
                while (resultado.next())
                {
                    PeriodoEscolar periodo = new PeriodoEscolar();
                    periodo.setIdPeriodoEscolar(resultado.getInt("idPeriodoEscolar"));
                    periodo.setFechaInicio(resultado.getString("fechaInicio"));
                    periodo.setFechaFin(resultado.getString("fechaFin"));
                    periodo.setNombre(resultado.getString("nombre"));
                    periodosConsulta.add(periodo);
                }
                respuesta.setPeriodos(periodosConsulta);
                conexionBD.close();
            } catch (SQLException e) {
                respuesta.setCodigoRespuesta(Constantes.ERROR_CONSULTA);
            }
        } else {
            respuesta.setCodigoRespuesta(Constantes.ERROR_CONEXION);
        }
        return respuesta;
    }
    
    public static int guardarPeriodo(PeriodoEscolar nuevoPeriodo) {
        int respuesta;
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if (conexionBD != null) {
            try {
                String sentencia = "INSERT INTO PeriodoEscolar (fechaInicio, fechaFin, nombre) " +
                        "VALUES (?,?,?)";
                PreparedStatement prepararSentencia =  conexionBD.prepareStatement(sentencia);
                prepararSentencia.setString(1, nuevoPeriodo.getFechaInicio());
                prepararSentencia.setString(2, nuevoPeriodo.getFechaFin());
                prepararSentencia.setString(3, nuevoPeriodo.getNombre());
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
    
    public static int modificarPeriodo(PeriodoEscolar periodoEdicion) {
        int respuesta;
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if (conexionBD != null) {
            try {
                String sentencia = "UPDATE PeriodoEscolar SET fechaInicio = ?, " + 
                        "fechaFin = ?, nombre = ? " + 
                        "WHERE idPeriodoEscolar = ?";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
                prepararSentencia.setString(1, periodoEdicion.getFechaInicio());
                prepararSentencia.setString(2, periodoEdicion.getFechaFin());
                prepararSentencia.setString(3, periodoEdicion.getNombre());
                prepararSentencia.setInt(4, periodoEdicion.getIdPeriodoEscolar());
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
    
    public static int eliminarPeriodo(int idPeriodoEscolar) {
        int respuesta;
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if (conexionBD != null) {
            try {
                String sentencia = "DELETE FROM PeriodoEscolar WHERE idPeriodoEscolar = ?";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
                prepararSentencia.setInt(1, idPeriodoEscolar);
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
