/*
* Autor: Luis Angel Elizalde Arroyo
* Fecha de creación: 21/06/2023
* Descripción: Clase encargada de cargar los datos de la revision de anteproyecto
*/
package jfxspger.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import jfxspger.modelo.ConexionBD;
import jfxspger.modelo.pojo.RevisionAnteproyecto;
import jfxspger.modelo.pojo.RevisionAnteproyectoRespuesta;
import jfxspger.utilidades.Constantes;

/**
 *
 * @author king_
 */
public class RevisionAnteproyectoDAO {
    
    public static RevisionAnteproyectoRespuesta obtenerInformacionRevisionAnteproyecto(int idAnteproyecto) {
        RevisionAnteproyectoRespuesta respuesta = new RevisionAnteproyectoRespuesta();
        Connection conexionBD = ConexionBD.abrirConexionBD();
        respuesta.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
        if (conexionBD != null) {
            try {
                String consulta = "SELECT idRevision, idAnteproyecto, comentarios, "
                        + "fechaRevision FROM revision WHERE idAnteproyecto = ?";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                prepararSentencia.setInt(1, idAnteproyecto);
                System.out.println("prepararSentenciaIdAnteproyecto = " + idAnteproyecto);
                ResultSet resultado = prepararSentencia.executeQuery();
                ArrayList<RevisionAnteproyecto> revisionAnteproyectoConsulta = new ArrayList();
                while (resultado.next())
                {
                    RevisionAnteproyecto revisionAnteproyecto = new RevisionAnteproyecto();
                    revisionAnteproyecto.setIdRevisionAnteproyecto(
                            resultado.getInt("idRevision"));
                    revisionAnteproyecto.setIdAnteproyecto(
                            resultado.getInt("idAnteproyecto"));
                    revisionAnteproyecto.setComentarioRevision(
                            resultado.getString("comentarios"));
                    revisionAnteproyecto.setFechaRevision(resultado.getString("fechaRevision"));
                    revisionAnteproyectoConsulta.add(revisionAnteproyecto);
                    System.out.println("revisionAnteproyecto = " + revisionAnteproyecto);
                }
                respuesta.setRevisionesAnteproyecto(revisionAnteproyectoConsulta);
                conexionBD.close();
            } catch (SQLException e) {
                respuesta.setCodigoRespuesta(Constantes.ERROR_CONSULTA);
            }
        } else {
            respuesta.setCodigoRespuesta(Constantes.ERROR_CONEXION);
        }
        return respuesta;
    }
    
    
     public static int guardarRevisionAnteproyecto(RevisionAnteproyecto nuevaRevision) {
        int respuesta;
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if (conexionBD != null) {
            try {
                String sentencia = "INSERT INTO revision (idRevision, idAnteproyecto, comentarios, "
                                 + "fechaRevision) VALUES" +
                                   " (?,?,?,current_timestamp())";

                PreparedStatement prepararSentencia =  conexionBD.prepareStatement(sentencia);
                prepararSentencia.setInt(1, nuevaRevision.getIdRevisionAnteproyecto());
                prepararSentencia.setInt(2, nuevaRevision.getIdAnteproyecto());
                prepararSentencia.setString(3, nuevaRevision.getComentarioRevision());
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
