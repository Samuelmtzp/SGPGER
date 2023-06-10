/*
* Autor: Samuel Martínez Pazos
* Fecha de creación: 22/05/2023
* Descripción: Clase encargada de administrar la información de cuerpo académico
* en la base de datos
*/
package jfxspger.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import jfxspger.modelo.ConexionBD;
import jfxspger.modelo.pojo.CuerpoAcademicoRespuesta;
import jfxspger.modelo.pojo.CuerpoAcademico;
import jfxspger.utilidades.Constantes;

public class CuerpoAcademicoDAO {
    public static CuerpoAcademicoRespuesta obtenerInformacionCuerpoAcademico() {
        CuerpoAcademicoRespuesta respuesta = new CuerpoAcademicoRespuesta();
        Connection conexionBD = ConexionBD.abrirConexionBD();
        respuesta.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
        if (conexionBD != null) {
            try {
                String consulta = "SELECT idCuerpoAcademico, nombre, clave, idResponsable, " +
                        "idGradoConsolidacion, idDependencia " +
                        "FROM CuerpoAcademico";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                ResultSet resultado = prepararSentencia.executeQuery();
                ArrayList<CuerpoAcademico> cuerpoAcademicoConsulta = new ArrayList();
                while (resultado.next())
                {
                    CuerpoAcademico cuerpoAcademico = new CuerpoAcademico();
                    cuerpoAcademico.setIdCuerpoAcademico(resultado.getInt("idCuerpoAcademico"));
                    cuerpoAcademico.setNombre(resultado.getString("nombre"));
                    cuerpoAcademico.setClave(resultado.getString("clave"));
                    cuerpoAcademico.setIdResponsable(resultado.getInt("idResponsable"));
                    cuerpoAcademico.setIdGradoConsolidacion(
                            resultado.getInt("idGradoConsolidacion"));
                    cuerpoAcademico.setIdDependencia(resultado.getInt("idDependencia"));
                    cuerpoAcademicoConsulta.add(cuerpoAcademico);
                }
                respuesta.setCuerposAcademicos(cuerpoAcademicoConsulta);
                conexionBD.close();
            } catch (SQLException e) {
                respuesta.setCodigoRespuesta(Constantes.ERROR_CONSULTA);
            }
        } else {
            respuesta.setCodigoRespuesta(Constantes.ERROR_CONEXION);
        }
        return respuesta;
    }
    
    public static int guardarCuerpoAcademico(CuerpoAcademico nuevoCuerpoAcademico) {
        int respuesta;
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if (conexionBD != null) {
            try {
                String sentencia = "INSERT INTO CuerpoAcademico (nombre, clave, idResponsable, " + 
                        "idGradoConsolidacion, idDependencia) " + 
                        "VALUES (?,?,?,?,?)";
                PreparedStatement prepararSentencia =  conexionBD.prepareStatement(sentencia);
                prepararSentencia.setString(1, nuevoCuerpoAcademico.getNombre());
                prepararSentencia.setString(2, nuevoCuerpoAcademico.getClave());
                prepararSentencia.setInt(3, nuevoCuerpoAcademico.getIdResponsable());
                prepararSentencia.setInt(4, nuevoCuerpoAcademico.getIdGradoConsolidacion());
                prepararSentencia.setInt(5, nuevoCuerpoAcademico.getIdDependencia());
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
    
    public static int modificarCuerpoAcademico(CuerpoAcademico cuerpoAcademicoEdicion) {
        int respuesta;
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if (conexionBD != null) {
            try {
                String sentencia = "UPDATE CuerpoAcademico SET nombre = ?, clave = ?, "  +
                        "idResponsable = ?, idGradoConsolidacion = ?, idDependencia = ? " + 
                        "WHERE idCuerpoAcademico = ?";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
                prepararSentencia.setString(1, cuerpoAcademicoEdicion.getNombre());
                prepararSentencia.setString(2, cuerpoAcademicoEdicion.getClave());
                prepararSentencia.setInt(3, cuerpoAcademicoEdicion.getIdResponsable());
                prepararSentencia.setInt(4, cuerpoAcademicoEdicion.getIdGradoConsolidacion());
                prepararSentencia.setInt(5, cuerpoAcademicoEdicion.getIdDependencia());
                prepararSentencia.setInt(6, cuerpoAcademicoEdicion.getIdCuerpoAcademico());
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
    
    public static int eliminarCuerpoAcademico(int idCuerpoAcademico) {
        int respuesta;
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if (conexionBD != null) {
            try {
                String sentencia = "DELETE FROM CuerpoAcademico WHERE idCuerpoAcademico = ?";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
                prepararSentencia.setInt(1, idCuerpoAcademico);
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
