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
    
    public static EstudianteRespuesta obtenerInformacionEstudiante(int idUsuario) {
        EstudianteRespuesta respuesta = new EstudianteRespuesta();
        Connection conexionBD = ConexionBD.abrirConexionBD();
        respuesta.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
        if (conexionBD != null) {
            try {
                String consulta = "SELECT idEstudiante, idAnteproyecto, matricula " +
                        "FROM Estudiante WHERE idUsuario = ?";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                prepararSentencia.setInt(1, idUsuario);
                ResultSet resultado = prepararSentencia.executeQuery();
                
                ArrayList<Estudiante> estudianteConsulta = new ArrayList();
                while (resultado.next())
                {
                    Estudiante estudiante = new Estudiante();
                    estudiante.setIdEstudiante(resultado.getInt("idEstudiante"));
                    estudiante.setIdAnteproyecto(resultado.getInt("idAnteproyecto"));
                    estudiante.setMatricula(resultado.getString("matricula"));
                    estudiante.setIdUsuario(idUsuario);
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
    
    public static EstudianteRespuesta obtenerInformacionEstudiantes() {
        EstudianteRespuesta respuesta = new EstudianteRespuesta();
        Connection conexionBD = ConexionBD.abrirConexionBD();
        respuesta.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
        if (conexionBD != null) {
            try {
                String consulta = "SELECT u.nombre, u.apellidoPaterno, u.apellidoMaterno, e.matricula, e.idEstudiante, a.nombreTrabajo " +
                                    "FROM usuario u " +
                                    "JOIN estudiante e ON u.idUsuario = e.idUsuario " +
                                    "JOIN anteproyecto a ON e.idAnteproyecto = a.idAnteproyecto;";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);                
                ResultSet resultado = prepararSentencia.executeQuery();
                
                ArrayList<Estudiante> estudianteConsulta = new ArrayList();
                while (resultado.next())
                {
                    Estudiante estudiante = new Estudiante();
                    estudiante.setNombre(resultado.getString("nombre"));
                    estudiante.setApellidoPaterno(resultado.getString("apellidoPaterno"));
                    estudiante.setApellidoMaterno(resultado.getString("apellidoMaterno"));
                    estudiante.setNombreTrabajo(resultado.getString("nombreTrabajo"));
                    estudiante.setIdEstudiante(resultado.getInt("idEstudiante"));                    
                    estudiante.setMatricula(resultado.getString("matricula"));                    
                    estudianteConsulta.add(estudiante);
                }
                respuesta.setEstudiantes(estudianteConsulta);
                conexionBD.close();
            } catch (SQLException e) {
                e.printStackTrace();
                respuesta.setCodigoRespuesta(Constantes.ERROR_CONSULTA);
            }
        } else {
            respuesta.setCodigoRespuesta(Constantes.ERROR_CONEXION);
        }
        return respuesta;
    }
    
    public static int verificarDisponibilidadMatricula(String matricula) {
        int respuesta = 1;
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if (conexionBD != null) {
            try {
                String sentencia = "SELECT COUNT(*) AS coincidencias "
                        + "FROM Estudiante WHERE matricula = ?";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
                prepararSentencia.setString(1, matricula);
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
    
    public static int agregarEstudianteAnteproyecto(Estudiante estudianteEdicion) {
        int respuesta;
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if (conexionBD != null) {
            try {
                String sentencia = "UPDATE Estudiante SET idAnteproyecto=? " +
                     "WHERE idEstudiante = ?";
                System.out.println("estudianteEdicion_idEstudiante = " + estudianteEdicion.getIdEstudiante());
                System.out.println("estudianteEdicion_idAnteproyecto = " + estudianteEdicion.getIdAnteproyecto());
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);                                
                prepararSentencia.setInt(1, estudianteEdicion.getIdAnteproyecto());
                prepararSentencia.setInt(2, estudianteEdicion.getIdEstudiante());
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
    
    public static int eliminarEstudianteAnteproyecto(Estudiante estudianteEdicion) {
        int respuesta;
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if (conexionBD != null) {
            try {
                String sentencia = "UPDATE Estudiante SET idAnteproyecto= NULL " +
                     "WHERE idEstudiante = ?";
                System.out.println("estudianteEdicion_idEstudiante = " + estudianteEdicion.getIdEstudiante());
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);                                
                prepararSentencia.setInt(1, estudianteEdicion.getIdEstudiante());
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
