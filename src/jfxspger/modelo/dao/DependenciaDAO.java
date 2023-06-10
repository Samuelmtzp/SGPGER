package jfxspger.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import jfxspger.modelo.ConexionBD;
import jfxspger.modelo.pojo.DependenciaRespuesta;
import jfxspger.modelo.pojo.Dependencia;
import jfxspger.utilidades.Constantes;

public class DependenciaDAO {
    
    public static DependenciaRespuesta obtenerInformacionDependencia() {
        DependenciaRespuesta respuesta = new DependenciaRespuesta();
        Connection conexionBD = ConexionBD.abrirConexionBD();
        respuesta.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
        if (conexionBD != null) {
            try {
                String consulta = "SELECT idDependencia, dependencia " +
                        "FROM Dependencia";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                ResultSet resultado = prepararSentencia.executeQuery();
                ArrayList<Dependencia> dependenciaConsulta = new ArrayList();
                while (resultado.next())
                {
                    Dependencia dependencia = new Dependencia();
                    dependencia.setIdDependencia(resultado.getInt("idDependencia"));
                    dependencia.setDependencia(resultado.getString("dependencia"));
                    dependenciaConsulta.add(dependencia);
                }
                respuesta.setDependencias(dependenciaConsulta);
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