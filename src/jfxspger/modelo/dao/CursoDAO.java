/*
* Autor: Samuel Martínez Pazos
* Fecha de creación: 22/05/2023
* Descripción: Clase encargada de administrar la información de curso
* en la base de datos
*/
package jfxspger.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import jfxspger.modelo.ConexionBD;
import jfxspger.modelo.pojo.CursoRespuesta;
import jfxspger.modelo.pojo.Curso;
import jfxspger.utilidades.Constantes;

public class CursoDAO {
    
    public static CursoRespuesta obtenerInformacionCurso() {
        CursoRespuesta respuesta = new CursoRespuesta();
        Connection conexionBD = ConexionBD.abrirConexionBD();
        respuesta.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
        if (conexionBD != null) {
            try {
                String consulta = "SELECT DISTINCT idCurso, Curso.idExperienciaEducativa, "
                        + "ExperienciaEducativa.nombre, curso.idPeriodo, PeriodoEscolar.nombre, "
                        + "if (idProfesor IS NULL, -1, idProfesor) AS idProfesor, "
                        + "if (idProfesor IS NULL, \"Sin profesor\",CONCAT(Usuario.nombre, ' ', "
                        + "Usuario.apellidoPaterno, ' ', Usuario.apellidoMaterno)) "
                        + "AS nombreCompleto, NRC, cupo "
                        + "FROM Curso "
                        + "INNER JOIN ExperienciaEducativa ON Curso.idExperienciaEducativa = "
                        + "ExperienciaEducativa.idExperienciaEducativa "
                        + "INNER JOIN Academico "
                        + "ON Curso.idProfesor = Academico.idAcademico "
                        + "OR Curso.idProfesor IS NULL "
                        + "INNER JOIN Usuario "
                        + "ON Academico.idUsuario = Usuario.idUsuario "
                        + "INNER JOIN PeriodoEscolar "
                        + "ON Curso.idPeriodo = PeriodoEscolar.idPeriodoEscolar";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                ResultSet resultado = prepararSentencia.executeQuery();
                ArrayList<Curso> cursoConsulta = new ArrayList();
                while (resultado.next())
                {
                    Curso curso = new Curso();
                    curso.setIdCurso(resultado.getInt("idCurso"));
                    curso.setIdExperienciaEducativa(
                            resultado.getInt("Curso.idExperienciaEducativa"));
                    curso.setExperienciaEducativa(
                            resultado.getString("ExperienciaEducativa.nombre"));
                    curso.setIdPeriodo(resultado.getInt("Curso.idPeriodo"));
                    curso.setPeriodo(resultado.getString("PeriodoEscolar.nombre"));
                    curso.setIdProfesor(resultado.getInt("idProfesor"));
                    curso.setNombreCompletoProfesor(resultado.getString("nombrecompleto"));
                    curso.setNrc(resultado.getInt("NRC"));
                    curso.setCupo(resultado.getInt("cupo"));
                    cursoConsulta.add(curso);
                }
                respuesta.setCursos(cursoConsulta);
                conexionBD.close();
            } catch (SQLException e) {
                respuesta.setCodigoRespuesta(Constantes.ERROR_CONSULTA);
            }
        } else {
            respuesta.setCodigoRespuesta(Constantes.ERROR_CONEXION);
        }
        return respuesta;
    }
    
    public static int verificarDisponibilidadNrc(int nrc) {
        int respuesta = 1;
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if (conexionBD != null) {
            try {
                String sentencia = "SELECT COUNT(*) AS coincidencias "
                        + "FROM Curso WHERE nrc = ?";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
                prepararSentencia.setInt(1, nrc);
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
    
    public static int guardarCurso(Curso nuevoCurso) {
        int respuesta;
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if (conexionBD != null) {
            try {
                String sentencia = "INSERT INTO Curso (idExperienciaEducativa, " + 
                        "idPeriodo, idProfesor, NRC, cupo) " +
                        "VALUES (?,?,?,?,?)";
                PreparedStatement prepararSentencia =  conexionBD.prepareStatement(sentencia);
                prepararSentencia.setInt(1, nuevoCurso.getIdExperienciaEducativa());
                prepararSentencia.setInt(2, nuevoCurso.getIdPeriodo());
                prepararSentencia.setInt(3, nuevoCurso.getIdProfesor());
                prepararSentencia.setInt(4, nuevoCurso.getNrc());
                prepararSentencia.setInt(5, nuevoCurso.getCupo());
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
    
    public static int modificarCurso(Curso cursoEdicion) {
        int respuesta;
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if (conexionBD != null) {
            try {
                String sentencia = "UPDATE Curso SET idExperienciaEducativa = ?, idPeriodo = ?, " + 
                        "idProfesor = ?, NRC = ?, cupo = ? " +
                        "WHERE idCurso = ?";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
                prepararSentencia.setInt(1, cursoEdicion.getIdExperienciaEducativa());
                prepararSentencia.setInt(2, cursoEdicion.getIdPeriodo());
                prepararSentencia.setInt(3, cursoEdicion.getIdProfesor());
                prepararSentencia.setInt(4, cursoEdicion.getNrc());
                prepararSentencia.setInt(5, cursoEdicion.getCupo());
                prepararSentencia.setInt(6, cursoEdicion.getIdCurso());
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
    
    public static int eliminarCurso(int idCurso) {
        int respuesta;
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if (conexionBD != null) {
            try {
                String sentencia = "DELETE FROM Curso WHERE idCurso = ?";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
                prepararSentencia.setInt(1, idCurso);
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
