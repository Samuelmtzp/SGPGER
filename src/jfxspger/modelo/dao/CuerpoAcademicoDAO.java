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
                String consulta = "SELECT DISTINCT CuerpoAcademico.idCuerpoAcademico, "
                        + "CuerpoAcademico.nombre, clave, "
                        + "if (idResponsable IS NULL, -1, idResponsable) AS idResponsable, "
                        + "if (idResponsable IS NULL, \"Sin responsable\", "
                        + "(CONCAT(Usuario.nombre, ' ', Usuario.apellidoPaterno, ' ', "
                        + "Usuario.apellidoMaterno))) AS nombreCompletoResponsable, "
                        + "CuerpoAcademico.idGradoConsolidacion, GradoConsolidacion.grado, "
                        + "CuerpoAcademico.idDependencia, Dependencia.dependencia "
                        + "FROM CuerpoAcademico "
                        + "INNER JOIN Academico "
                        + "ON CuerpoAcademico.idResponsable = Academico.idAcademico "
                        + "OR CuerpoAcademico.idResponsable IS NULL "
                        + "INNER JOIN Usuario "
                        + "ON Academico.idUsuario = Usuario.idUsuario "
                        + "INNER JOIN GradoConsolidacion "
                        + "ON CuerpoAcademico.idGradoConsolidacion = "
                        + "GradoConsolidacion.idGradoConsolidacion "
                        + "INNER JOIN Dependencia "
                        + "ON CuerpoAcademico.idDependencia = Dependencia.idDependencia "
                        + "WHERE CuerpoAcademico.idResponsable IS NULL "
                        + "OR CuerpoAcademico.idResponsable IS NOT NULL";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                ResultSet resultado = prepararSentencia.executeQuery();
                ArrayList<CuerpoAcademico> cuerpoAcademicoConsulta = new ArrayList();
                while (resultado.next())
                {
                    CuerpoAcademico cuerpoAcademico = new CuerpoAcademico();
                    cuerpoAcademico.setIdCuerpoAcademico(
                            resultado.getInt("CuerpoAcademico.idCuerpoAcademico"));
                    cuerpoAcademico.setNombre(resultado.getString("CuerpoAcademico.nombre"));
                    cuerpoAcademico.setClave(resultado.getString("clave"));
                    cuerpoAcademico.setIdResponsable(resultado.getInt("idResponsable"));
                    cuerpoAcademico.setNombreCompletoResponsable(
                            resultado.getString("nombreCompletoResponsable"));
                    cuerpoAcademico.setIdGradoConsolidacion(
                            resultado.getInt("CuerpoAcademico.idGradoConsolidacion"));
                    cuerpoAcademico.setGradoConsolidacion(
                            resultado.getString("GradoConsolidacion.grado"));
                    cuerpoAcademico.setIdDependencia(
                            resultado.getInt("CuerpoAcademico.idDependencia"));
                    cuerpoAcademico.setDependencia(
                            resultado.getString("Dependencia.dependencia"));
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
    
    public static int verificarDisponibilidadNombre(String nombre) {
        int respuesta = 1;
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if (conexionBD != null) {
            try {
                String sentencia = "SELECT COUNT(*) AS coincidencias "
                        + "FROM CuerpoAcademico WHERE nombre = ?";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
                prepararSentencia.setString(1, nombre);
                ResultSet resultado = prepararSentencia.executeQuery();
                if (resultado.next()) 
                    respuesta = resultado.getInt("coincidencias");
                conexionBD.close();
            } catch (SQLException e) {
                respuesta = Constantes.ERROR_CONSULTA;
            }
        } else {
            respuesta = Constantes.ERROR_CONEXION;
        }
        return respuesta;
    }
    
    public static int verificarDisponibilidadClave(String clave) {
        int respuesta = 1;
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if (conexionBD != null) {
            try {
                String sentencia = "SELECT COUNT(*) AS coincidencias "
                        + "FROM CuerpoAcademico WHERE clave = ?";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
                prepararSentencia.setString(1, clave);
                ResultSet resultado = prepararSentencia.executeQuery();
                if (resultado.next()) 
                    respuesta = resultado.getInt("coincidencias");
                conexionBD.close();
            } catch (SQLException e) {
                respuesta = Constantes.ERROR_CONSULTA;
            }
        } else {
            respuesta = Constantes.ERROR_CONEXION;
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
