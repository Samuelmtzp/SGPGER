/*
* Autor: Samuel Martínez Pazos
* Fecha de creación: 10/06/2023
* Descripción: Clase encargada de administrar la 
* información de los grados de consolidación en la base de datos
*/
package jfxspger.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import jfxspger.modelo.ConexionBD;
import jfxspger.modelo.pojo.GradoConsolidacionRespuesta;
import jfxspger.modelo.pojo.GradoConsolidacion;
import jfxspger.utilidades.Constantes;

public class GradoConsolidacionDAO {
    public static GradoConsolidacionRespuesta obtenerInformacionGradoConsolidacion() {
        GradoConsolidacionRespuesta respuesta = new GradoConsolidacionRespuesta();
        Connection conexionBD = ConexionBD.abrirConexionBD();
        respuesta.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
        if (conexionBD != null) {
            try {
                String consulta = "SELECT idGradoConsolidacion, grado " +
                        "FROM GradoConsolidacion";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                ResultSet resultado = prepararSentencia.executeQuery();
                ArrayList<GradoConsolidacion> gradoConsolidacionConsulta = new ArrayList();
                while (resultado.next())
                {
                    GradoConsolidacion gradoConsolidacion = new GradoConsolidacion();
                    gradoConsolidacion.setIdGradoConsolidacion(
                            resultado.getInt("idGradoConsolidacion"));
                    gradoConsolidacion.setGrado(resultado.getString("grado"));
                    gradoConsolidacionConsulta.add(gradoConsolidacion);
                }
                respuesta.setGradosConsolidacion(gradoConsolidacionConsulta);
                conexionBD.close();
            } catch (SQLException e) {
                respuesta.setCodigoRespuesta(Constantes.ERROR_CONSULTA);
            }
        } else {
            respuesta.setCodigoRespuesta(Constantes.ERROR_CONEXION);
        }
        return respuesta;
    }
}
