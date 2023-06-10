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
                String consulta = "SELECT idCurso, idExperienciaEducativa, " +
                        "idPeriodo, idPofesor, NRC, bloque, seccion, cupo " +
                        "FROM Curso";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                ResultSet resultado = prepararSentencia.executeQuery();
                ArrayList<Curso> cursoConsulta = new ArrayList();
                while (resultado.next())
                {
                    Curso curso = new Curso();
                    curso.setIdCurso(resultado.getInt("idCurso"));
                    curso.setIdExperienciaEducativa(
                            resultado.getInt("idExperienciaEducativa"));
                    curso.setIdPeriodo(resultado.getInt("idPeriodo"));
                    curso.setIdProfesor(resultado.getInt("idProfesor"));
                    curso.setNrc(resultado.getInt("NRC"));
                    curso.setBloque(resultado.getInt("bloque"));
                    curso.setSeccion(resultado.getString("seccion"));
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
    
    public static int guardarCurso(Curso nuevoCurso) {
        int respuesta;
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if (conexionBD != null) {
            try {
                String sentencia = "INSERT INTO Curso (idExperienciaEducativa, " + 
                        "idPeriodo, idProfesor, NRC, bloque, seccion, cupo) " +
                        "VALUES (?,?,?,?,?,?,?)";
                PreparedStatement prepararSentencia =  conexionBD.prepareStatement(sentencia);
                prepararSentencia.setInt(1, nuevoCurso.getIdExperienciaEducativa());
                prepararSentencia.setInt(2, nuevoCurso.getIdPeriodo());
                prepararSentencia.setInt(3, nuevoCurso.getIdProfesor());
                prepararSentencia.setInt(4, nuevoCurso.getNrc());
                prepararSentencia.setInt(5, nuevoCurso.getBloque());
                prepararSentencia.setString(6, nuevoCurso.getSeccion());
                prepararSentencia.setInt(7, nuevoCurso.getCupo());
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
                        "idProfesor = ?, NRC = ?, bloque = ?, seccion = ?, cupo = ? " +
                        "WHERE idCurso = ?";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
                prepararSentencia.setInt(1, cursoEdicion.getIdExperienciaEducativa());
                prepararSentencia.setInt(2, cursoEdicion.getIdPeriodo());
                prepararSentencia.setInt(3, cursoEdicion.getIdProfesor());
                prepararSentencia.setInt(4, cursoEdicion.getNrc());
                prepararSentencia.setInt(5, cursoEdicion.getBloque());
                prepararSentencia.setString(6, cursoEdicion.getSeccion());
                prepararSentencia.setInt(7, cursoEdicion.getCupo());
                prepararSentencia.setInt(8, cursoEdicion.getIdCurso());
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
