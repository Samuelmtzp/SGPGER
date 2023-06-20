/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jfxspger.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import jfxspger.modelo.ConexionBD;
import jfxspger.modelo.pojo.DuracionAprox;
import jfxspger.modelo.pojo.DuracionAproxRespuesta;
import jfxspger.utilidades.Constantes;

/**
 *
 * @author king_
 */
public class DuracionAproximadaDAO {
    
    public static DuracionAproxRespuesta obtenerDuracionAproximada() {
        DuracionAproxRespuesta respuesta = new DuracionAproxRespuesta();
        Connection conexionBD = ConexionBD.abrirConexionBD();
        respuesta.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
        if (conexionBD != null) {
            try {
                String consulta = "Select idDuracionAproximada, duracionAproximada "
                        + "FROM duracionAproximada";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                ResultSet resultado = prepararSentencia.executeQuery();
                ArrayList<DuracionAprox> duracionConsulta = new ArrayList();
                while (resultado.next())
                {
                    DuracionAprox duracion = new DuracionAprox();
                    duracion.setIdDuracionAproximada(resultado.getInt("idDuracionAproximada"));
                    duracion.setDuracion(resultado.getString("duracionAproximada"));
                    duracionConsulta.add(duracion);
                }
                respuesta.setDuracionAproximada(duracionConsulta);
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
