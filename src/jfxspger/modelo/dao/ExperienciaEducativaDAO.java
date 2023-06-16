/*
* Autor: Samuel Martínez Pazos
* Fecha de creación: 22/05/2023
* Descripción: Clase encargada de administrar la información de experiencia educativa
* en la base de datos
*/
package jfxspger.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import jfxspger.modelo.ConexionBD;
import jfxspger.modelo.pojo.ExperienciaEducativaRespuesta;
import jfxspger.modelo.pojo.ExperienciaEducativa;
import jfxspger.utilidades.Constantes;

public class ExperienciaEducativaDAO {
    
    public static ExperienciaEducativaRespuesta obtenerInformacionExperienciaEducativa() {
        ExperienciaEducativaRespuesta respuesta = new ExperienciaEducativaRespuesta();
        Connection conexionBD = ConexionBD.abrirConexionBD();
        respuesta.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
        if (conexionBD != null) {
            try {
                String consulta = "SELECT idExperienciaEducativa, nombre " +
                        "FROM ExperienciaEducativa";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                ResultSet resultado = prepararSentencia.executeQuery();
                ArrayList<ExperienciaEducativa> experienciaEducativaConsulta = new ArrayList();
                while (resultado.next())
                {
                    ExperienciaEducativa experienciaEducativa = new ExperienciaEducativa();
                    experienciaEducativa.setIdExperienciaEducativa(resultado.getInt(
                            "idExperienciaEducativa"));
                    experienciaEducativa.setNombre(resultado.getString("nombre"));
                    experienciaEducativaConsulta.add(experienciaEducativa);
                }
                respuesta.setExperienciasEducativas(experienciaEducativaConsulta);
                conexionBD.close();
            } catch (SQLException e) {
                respuesta.setCodigoRespuesta(Constantes.ERROR_CONSULTA);
            }
        } else {
            respuesta.setCodigoRespuesta(Constantes.ERROR_CONEXION);
        }
        return respuesta;
    }
    
    public static int guardarExperienciaEducativa(ExperienciaEducativa nuevaExperienciaEducativa) {
        int respuesta;
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if (conexionBD != null) {
            try {
                String sentencia = "INSERT INTO ExperienciaEducativa (nombre) " +
                        "VALUES (?)";
                PreparedStatement prepararSentencia =  conexionBD.prepareStatement(sentencia);
                prepararSentencia.setString(1, nuevaExperienciaEducativa.getNombre());
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
    
    public static int modificarExperienciaEducativa(
            ExperienciaEducativa experienciaEducativaEdicion) {
        int respuesta;
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if (conexionBD != null) {
            try {
                String sentencia = "UPDATE ExperienciaEducativa SET nombre = ? " + 
                        "WHERE idExperienciaEducativa = ?";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
                prepararSentencia.setString(1, experienciaEducativaEdicion.getNombre());
                prepararSentencia.setInt(2, experienciaEducativaEdicion.
                        getIdExperienciaEducativa());
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
    
    public static int eliminarExperienciaEducativa(int idExperienciaEducativa) {
        int respuesta;
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if (conexionBD != null) {
            try {
                String sentencia = "DELETE FROM ExperienciaEducativa " + 
                        "WHERE idExperienciaEducativa = ?";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
                prepararSentencia.setInt(1, idExperienciaEducativa);
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
