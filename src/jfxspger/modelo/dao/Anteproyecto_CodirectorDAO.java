/*
* Autor: Samuel Martínez Pazos
* Fecha de creación: 22/05/2023
* Descripción: Clase encargada de administrar la información de la relación entre
* un codirector y un anteproyecto en la base de datos
*/
package jfxspger.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import jfxspger.modelo.ConexionBD;
import jfxspger.modelo.pojo.Anteproyecto_CodirectorRespuesta;
import jfxspger.modelo.pojo.Anteproyecto_Codirector;
import jfxspger.utilidades.Constantes;

public class Anteproyecto_CodirectorDAO {

    public static Anteproyecto_CodirectorRespuesta obtenerInformacionAnteproyecto_Codirector() {
        Anteproyecto_CodirectorRespuesta respuesta = new Anteproyecto_CodirectorRespuesta();
        Connection conexionBD = ConexionBD.abrirConexionBD();
        respuesta.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
        if (conexionBD != null) {
            try {
                String consulta = "SELECT idCodirector, idAnteproyecto " +
                        "FROM Anteproyecto_Codirector";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                ResultSet resultado = prepararSentencia.executeQuery();
                ArrayList<Anteproyecto_Codirector> academicos_anteproyectoConsulta = 
                        new ArrayList();
                while (resultado.next())
                {
                    Anteproyecto_Codirector academico_anteproyecto = new Anteproyecto_Codirector();
                    academico_anteproyecto.setIdCodirector(resultado.getInt("idCodirector"));
                    academico_anteproyecto.setIdAnteproyecto(resultado.getInt("idAnteproyecto"));
                    academicos_anteproyectoConsulta.add(academico_anteproyecto);
                }
                respuesta.setAnteproyectos_codirectores(academicos_anteproyectoConsulta);
                conexionBD.close();
            } catch (SQLException e) {
                respuesta.setCodigoRespuesta(Constantes.ERROR_CONSULTA);
            }
        } else {
            respuesta.setCodigoRespuesta(Constantes.ERROR_CONEXION);
        }
        return respuesta;
    }

    public static int guardarAcademico_Anteproyecto(Anteproyecto_Codirector
            nuevoAcademico_Anteproyecto) {
        int respuesta;
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if (conexionBD != null) {
            try {
                String sentencia = "INSERT INTO Anteproyecto_Codirector " +
                        "(idCodirector, idAnteproyecto) " +
                        "VALUES (?, ?)";
                PreparedStatement prepararSentencia =  conexionBD.prepareStatement(sentencia);
                prepararSentencia.setInt(1, nuevoAcademico_Anteproyecto.getIdCodirector());
                prepararSentencia.setInt(2, nuevoAcademico_Anteproyecto.getIdAnteproyecto());
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

    public static int eliminarAcademico_Anteproyecto(int idAnteproyecto, int idCodirector) {
        int respuesta;
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if (conexionBD != null) {
            try {
                String sentencia = "DELETE FROM Anteproyecto_Codirector " +
                        "WHERE idCodirector = ? " +
                        "AND idAnteproyecto = ?";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
                prepararSentencia.setInt(1, idCodirector);
                prepararSentencia.setInt(2, idAnteproyecto);
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
