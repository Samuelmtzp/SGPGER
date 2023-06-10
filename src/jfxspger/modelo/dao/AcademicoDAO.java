/*
* Autor: Samuel Martínez Pazos
* Fecha de creación: 22/05/2023
* Descripción: Clase encargada de administrar la información de Academico
* en la base de datos
*/
package jfxspger.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import jfxspger.modelo.ConexionBD;
import jfxspger.modelo.pojo.AcademicoRespuesta;
import jfxspger.modelo.pojo.Academico;
import jfxspger.utilidades.Constantes;

public class AcademicoDAO {
    
    public static AcademicoRespuesta obtenerInformacionAcademico() {
        AcademicoRespuesta respuesta = new AcademicoRespuesta();
        Connection conexionBD = ConexionBD.abrirConexionBD();
        respuesta.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
        if (conexionBD != null) {
            try {
                String consulta = "SELECT idAcademico, idUsuario, " +
                        "numeroDePersonal, esAdministrador, idCuerpoAcademico " +
                        "FROM Academico";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                ResultSet resultado = prepararSentencia.executeQuery();
                ArrayList<Academico> academicoConsulta = new ArrayList();
                while (resultado.next())
                {
                    Academico academico = new Academico();
                    academico.setIdAcademico(resultado.getInt("idAcademico"));
                    academico.setIdUsuario(resultado.getInt("idUsuario"));
                    academico.setNumeroDePersonal(resultado.getInt("numeroDePersonal"));
                    academico.setEsAdministrador(resultado.getBoolean("esAdministrador"));
                    academico.setIdCuerpoAcademico(resultado.getInt("idCuerpoAcademico"));
                    academicoConsulta.add(academico);
                }
                respuesta.setAcademicos(academicoConsulta);
                conexionBD.close();
            } catch (SQLException e) {
                respuesta.setCodigoRespuesta(Constantes.ERROR_CONSULTA);
            }
        } else {
            respuesta.setCodigoRespuesta(Constantes.ERROR_CONEXION);
        }
        return respuesta;
    }
    
    public static int guardarAcademico(Academico nuevoAcademico) {
        int respuesta;
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if (conexionBD != null) {
            try {
                String sentencia = "INSERT INTO Academico (idUsuario, " + 
                        "numeroDePersonal, esAdministrador, idCuerpoAcademico) " +
                        "VALUES (?,?,?,?)";
                PreparedStatement prepararSentencia =  conexionBD.prepareStatement(sentencia);
                prepararSentencia.setInt(1, nuevoAcademico.getIdUsuario());
                prepararSentencia.setInt(2, nuevoAcademico.getNumeroDePersonal());
                prepararSentencia.setBoolean(3, nuevoAcademico.isEsAdministrador());
                prepararSentencia.setInt(4, nuevoAcademico.getIdCuerpoAcademico());
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
    
    public static int modificarAcademico(Academico academicoEdicion) {
        int respuesta;
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if (conexionBD != null) {
            try {
                String sentencia = "UPDATE Academico SET idUsuario = ?, " + 
                        "numeroDePersonal = ?, esAdministrador = ?, idCuerpoAcademico " +
                        "WHERE idAcademico = ?";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
                prepararSentencia.setInt(1, academicoEdicion.getIdUsuario());
                prepararSentencia.setInt(2, academicoEdicion.getNumeroDePersonal());
                prepararSentencia.setBoolean(3, academicoEdicion.isEsAdministrador());
                prepararSentencia.setInt(4, academicoEdicion.getIdCuerpoAcademico());
                prepararSentencia.setInt(5, academicoEdicion.getIdAcademico());
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
    
    public static int eliminarAcademico(int idAcademico) {
        int respuesta;
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if (conexionBD != null) {
            try {
                String sentencia = "DELETE FROM Academico WHERE idAcademico = ?";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
                prepararSentencia.setInt(1, idAcademico);
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
