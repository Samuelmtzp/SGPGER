/*
* Autor: Samuel Martínez Pazos
* Fecha de creación: 22/05/2023
* Descripción: Clase encargada de administrar la información de lgac
* en la base de datos
*/
package jfxspger.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import jfxspger.modelo.ConexionBD;
import jfxspger.modelo.pojo.LgacRespuesta;
import jfxspger.modelo.pojo.Lgac;
import jfxspger.utilidades.Constantes;

public class LgacDAO {
    
    public static LgacRespuesta obtenerInformacionLgac() {
        LgacRespuesta respuesta = new LgacRespuesta();
        Connection conexionBD = ConexionBD.abrirConexionBD();
        respuesta.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
        if (conexionBD != null) {
            try {
                String consulta = "SELECT idLgac, Lgac.nombre, Lgac.idCuerpoAcademico, "
                        + "cuerpoAcademico.nombre FROM Lgac INNER JOIN cuerpoAcademico ON "
                        + "cuerpoAcademico.idCuerpoAcademico= Lgac.idCuerpoAcademico";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                ResultSet resultado = prepararSentencia.executeQuery();
                ArrayList<Lgac> lgacConsulta = new ArrayList();
                while (resultado.next())
                {
                    Lgac lgac = new Lgac();
                    lgac.setIdLgac(resultado.getInt("idLgac"));
                    lgac.setNombre(resultado.getString("Lgac.nombre"));
                    lgac.setIdCuerpoAcademico(resultado.getInt(
                            "Lgac.idCuerpoAcademico"));
                    lgac.setCuerpoAcademico(resultado.getString(
                            "cuerpoAcademico.nombre"));
                    lgacConsulta.add(lgac);
                }
                respuesta.setListaLgac(lgacConsulta);
                conexionBD.close();
            } catch (SQLException e) {
                respuesta.setCodigoRespuesta(Constantes.ERROR_CONSULTA);
            }
        } else {
            respuesta.setCodigoRespuesta(Constantes.ERROR_CONEXION);
        }
        return respuesta;
    }
    
    public static int verificarDisponibilidadNombreLgac(String nombre) {
        int respuesta = 1;
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if (conexionBD != null) {
            try {
                String sentencia = "SELECT COUNT(*) AS coincidencias "
                        + "FROM Lgac WHERE nombre = ?";
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
    
    public static int guardarLgac(Lgac nuevaLgac) {
        int respuesta;
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if (conexionBD != null) {
            try {
                String sentencia = "INSERT INTO Lgac (nombre, idCuerpoAcademico) " +
                        "VALUES (?,?)";
                PreparedStatement prepararSentencia =  conexionBD.prepareStatement(sentencia);
                prepararSentencia.setString(1, nuevaLgac.getNombre());
                prepararSentencia.setInt(2, nuevaLgac.getIdCuerpoAcademico());
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
    
    public static int modificarLgac(Lgac lgacEdicion) {
        int respuesta;
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if (conexionBD != null) {
            try {
                String sentencia = "UPDATE Lgac SET nombre = ?, idCuerpoAcademico = ? " + 
                        "WHERE idLgac = ?";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
                prepararSentencia.setString(1, lgacEdicion.getNombre());
                prepararSentencia.setInt(2, lgacEdicion.getIdCuerpoAcademico());
                prepararSentencia.setInt(3, lgacEdicion.getIdLgac());
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
    
    public static int eliminarLgac(int idLgac) {
        int respuesta;
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if (conexionBD != null) {
            try {
                String sentencia = "DELETE FROM Lgac WHERE idLgac = ?";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
                prepararSentencia.setInt(1, idLgac);
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
