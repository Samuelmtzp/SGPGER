/*
* Autor: Samuel Martínez Pazos
* Fecha de creación: 22/05/2023
* Descripción: Clase encargada de administrar la información de la relación entre 
* un estudiante y un curso en la base de datos
*/
package jfxspger.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import jfxspger.modelo.ConexionBD;
import jfxspger.modelo.pojo.Estudiante_CursoRespuesta;
import jfxspger.modelo.pojo.Estudiante_Curso;
import jfxspger.utilidades.Constantes;

public class Estudiante_CursoDAO {
    
    public static Estudiante_CursoRespuesta obtenerInformacionEstudiante_Curso() {
        Estudiante_CursoRespuesta respuesta = new Estudiante_CursoRespuesta();
        Connection conexionBD = ConexionBD.abrirConexionBD();
        respuesta.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
        if (conexionBD != null) {
            try {
                String consulta = "SELECT idEstudiante, idCurso " +
                        "FROM Estudiante_Curso";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                ResultSet resultado = prepararSentencia.executeQuery();
                ArrayList<Estudiante_Curso> estudiantes_cursoConsulta = new ArrayList();
                while (resultado.next())
                {
                    Estudiante_Curso estudiante_curso = new Estudiante_Curso();
                    estudiante_curso.setIdEstudiante(resultado.getInt("idEstudiante"));
                    estudiante_curso.setIdCurso(resultado.getInt("idCurso"));
                    estudiantes_cursoConsulta.add(estudiante_curso);
                }
                respuesta.setEstudiantes_curso(estudiantes_cursoConsulta);
                conexionBD.close();
            } catch (SQLException e) {
                respuesta.setCodigoRespuesta(Constantes.ERROR_CONSULTA);
            }
        } else {
            respuesta.setCodigoRespuesta(Constantes.ERROR_CONEXION);
        }
        return respuesta;
    }
    
    public static int guardarEstudiante_Curso(Estudiante_Curso nuevoEstudiante_Curso) {
        int respuesta;
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if (conexionBD != null) {
            try {
                String sentencia = "INSERT INTO Estudiante_Curso " + 
                        "(idEstudiante, idCurso) " +
                        "VALUES (?,?)";
                PreparedStatement prepararSentencia =  conexionBD.prepareStatement(sentencia);
                prepararSentencia.setInt(1, nuevoEstudiante_Curso.getIdEstudiante());
                prepararSentencia.setInt(2, nuevoEstudiante_Curso.getIdCurso());
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
    
    public static int eliminarEstudianteDeCurso(int idEstudiante, int idCurso) {
        int respuesta;
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if (conexionBD != null) {
            try {
                String sentencia = "DELETE FROM Estudiante_Curso " + 
                        "WHERE idEstudiante = ? " + 
                        "AND idCurso = ?";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
                prepararSentencia.setInt(1, idEstudiante);
                prepararSentencia.setInt(2, idCurso);
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
