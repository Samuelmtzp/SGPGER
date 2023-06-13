/*
* Autor: Samuel Martínez Pazos
* Fecha de creación: 22/05/2023
* Descripción: Clase encargada de administrar la información de estudiante
* en la base de datos
*/
package jfxspger.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import jfxspger.modelo.ConexionBD;
import jfxspger.modelo.pojo.EstudianteRespuesta;
import jfxspger.modelo.pojo.Estudiante;
import jfxspger.utilidades.Constantes;

public class EstudianteDAO {
    
    public static EstudianteRespuesta obtenerInformacionEstudiante() {
        EstudianteRespuesta respuesta = new EstudianteRespuesta();
        Connection conexionBD = ConexionBD.abrirConexionBD();
        respuesta.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
        if (conexionBD != null) {
            try {
                String consulta = "SELECT idEstudiante, idUsuario, idAnteproyecto, matricula " +
                        "FROM Estudiante";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                ResultSet resultado = prepararSentencia.executeQuery();
                ArrayList<Estudiante> estudianteConsulta = new ArrayList();
                while (resultado.next())
                {
                    Estudiante estudiante = new Estudiante();
                    estudiante.setIdEstudiante(resultado.getInt("idEstudiante"));
                    estudiante.setIdUsuario(resultado.getInt("idUsuario"));
                    estudiante.setIdAnteproyecto(resultado.getInt("idAnteproyecto"));
                    estudiante.setMatricula(resultado.getString("matricula"));
                    estudianteConsulta.add(estudiante);
                }
                respuesta.setEstudiantes(estudianteConsulta);
                conexionBD.close();
            } catch (SQLException e) {
                respuesta.setCodigoRespuesta(Constantes.ERROR_CONSULTA);
            }
        } else {
            respuesta.setCodigoRespuesta(Constantes.ERROR_CONEXION);
        }
        return respuesta;
    }
    
    public static int guardarEstudiante(Estudiante nuevoEstudiante) {
        int respuesta;
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if (conexionBD != null) {
            try {
                String sentencia = "INSERT INTO Estudiante (idUsuario, matricula) " +
                        "VALUES (?,?)";
                PreparedStatement prepararSentencia =  conexionBD.prepareStatement(sentencia);
                prepararSentencia.setInt(1, nuevoEstudiante.getIdUsuario());
                prepararSentencia.setString(2, nuevoEstudiante.getMatricula());
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
    
    public static int modificarEstudiante(Estudiante estudianteEdicion) {
        int respuesta;
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if (conexionBD != null) {
            try {
                String sentencia = "UPDATE Estudiante SET matricula = ?, idAnteproyecto = ?" +
                        "WHERE idEstudiante = ?";
                
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);                                
                prepararSentencia.setString(1, estudianteEdicion.getMatricula());
                prepararSentencia.setInt(2, estudianteEdicion.getIdAnteproyecto());
                prepararSentencia.setInt(3, estudianteEdicion.getIdEstudiante());
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
    
    public static int eliminarEstudiante(int idEstudiante) {
        int respuesta;
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if (conexionBD != null) {
            try {
                String sentencia = "DELETE FROM Estudiante WHERE idEstudiante = ?";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
                prepararSentencia.setInt(1, idEstudiante);
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
