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
    
    public static AcademicoRespuesta obtenerInformacionAcademico(int idUsuario) {
        AcademicoRespuesta respuesta = new AcademicoRespuesta();
        Connection conexionBD = ConexionBD.abrirConexionBD();
        respuesta.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
        if (conexionBD != null) {
            try {
                String consulta = "SELECT idAcademico, " +
                        "numeroDePersonal, idCuerpoAcademico " +
                        "FROM Academico WHERE idUsuario = ?";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                prepararSentencia.setInt(1, idUsuario);
                ResultSet resultado = prepararSentencia.executeQuery();
                ArrayList<Academico> academicoConsulta = new ArrayList();
                while (resultado.next())
                {
                    Academico academico = new Academico();
                    academico.setIdAcademico(resultado.getInt("idAcademico"));
                    academico.setIdUsuario(idUsuario);
                    academico.setNumeroDePersonal(resultado.getInt("numeroDePersonal"));
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
    
    public static int verificarDisponibilidadNumeroDePersonal(int numeroDePersonal) {
        int respuesta = 0;
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if (conexionBD != null) {
            try {
                String sentencia = "SELECT COUNT(*) AS coincidencias "
                        + "FROM Academico WHERE numeroDePersonal = ?";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
                prepararSentencia.setInt(1, numeroDePersonal);
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
    
    public static int obtenerCantidadAnteproyectosAcademicoEsDirector(int idAcademico) {
        int respuesta = 0;
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if (conexionBD != null) {
            try {
                String sentencia = "SELECT COUNT(*) AS coincidencias "
                        + "FROM anteproyecto "
                        + "WHERE idDirector = ?";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
                prepararSentencia.setInt(1, idAcademico);
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
    
    public static int obtenerCantidadCuerposAcademicoSAcademicoEsResponsable(int idAcademico) {
        int respuesta = 0;
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if (conexionBD != null) {
            try {
                String sentencia = "SELECT COUNT(*) AS coincidencias "
                        + "FROM cuerpoAcademico "
                        + "WHERE idResponsable = ?";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
                prepararSentencia.setInt(1, idAcademico);
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
    
    public static int obtenerCantidadAnteproyectosAcademicoEsCodirector(int idAcademico) {
        int respuesta = 0;
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if (conexionBD != null) {
            try {
                String sentencia = "SELECT COUNT(*) AS coincidencias "
                        + "FROM anteproyecto_codirector "
                        + "WHERE idCodirector = ?";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
                prepararSentencia.setInt(1, idAcademico);
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
    
    public static int consultarCoincidenciasCodirectorDeAnteproyecto(int idAcademico, 
            int idAnteproyecto) {
        int respuesta = 0;
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if (conexionBD != null) {
            try {
                String sentencia = "SELECT COUNT(*) AS coincidencias "
                        + "FROM anteproyecto_codirector "
                        + "WHERE idCodirector = ? AND idAnteproyecto = ?";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
                prepararSentencia.setInt(1, idAcademico);
                prepararSentencia.setInt(2, idAnteproyecto);
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
    
    public static int obtenerCantidadCursosAcademicoEsProfesor(int idAcademico) {
        int respuesta = 0;
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if (conexionBD != null) {
            try {
                String sentencia = "SELECT COUNT(*) AS coincidencias "
                        + "FROM curso "
                        + "WHERE idProfesor = ?";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
                prepararSentencia.setInt(1, idAcademico);
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
    
    public static int guardarAcademico(Academico nuevoAcademico) {
        int respuesta;
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if (conexionBD != null) {
            try {
                String sentencia = "INSERT INTO Academico (idUsuario, numeroDePersonal) " +
                        "VALUES (?,?)";
                PreparedStatement prepararSentencia =  conexionBD.prepareStatement(sentencia);
                prepararSentencia.setInt(1, nuevoAcademico.getIdUsuario());
                prepararSentencia.setInt(2, nuevoAcademico.getNumeroDePersonal());
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
                String sentencia = "UPDATE Academico SET numeroDePersonal = ?, "
                        + "idCuerpoAcademico = ? "
                        + "WHERE idAcademico = ?";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
                prepararSentencia.setInt(1, academicoEdicion.getNumeroDePersonal());
                prepararSentencia.setInt(2, academicoEdicion.getIdCuerpoAcademico());
                prepararSentencia.setInt(3, academicoEdicion.getIdAcademico());
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
    
    public static int removerAcademicoDeCuerpoAcademico(int idAcademico) {
        int respuesta;
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if (conexionBD != null) {
            try {
                String sentencia = "UPDATE Academico SET idCuerpoAcademico = NULL "
                        + "WHERE idAcademico = ?";
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
    
    public static int agregarAcademicoACuerpoAcademico(int idAcademico, int idCuerpoAcademico) {
        int respuesta;
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if (conexionBD != null) {
            try {
                String sentencia = "UPDATE Academico SET idCuerpoAcademico = ? "
                        + "WHERE idAcademico = ?";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
                prepararSentencia.setInt(1, idCuerpoAcademico);
                prepararSentencia.setInt(2, idAcademico);
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
