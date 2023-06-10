package jfxspger.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import jfxspger.modelo.ConexionBD;
import jfxspger.modelo.pojo.EstadoAnteproyectoRespuesta;
import jfxspger.modelo.pojo.EstadoAnteproyecto;
import jfxspger.utilidades.Constantes;

public class EstadoAnteproyectoDAO {
    public static EstadoAnteproyectoRespuesta obtenerInformacionEstadoAnteproyecto() {
        EstadoAnteproyectoRespuesta respuesta = new EstadoAnteproyectoRespuesta();
        Connection conexionBD = ConexionBD.abrirConexionBD();
        respuesta.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
        if (conexionBD != null) {
            try {
                String consulta = "SELECT idEstadoAnteproyecto, estado " +
                        "FROM EstadoAnteproyecto";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                ResultSet resultado = prepararSentencia.executeQuery();
                ArrayList<EstadoAnteproyecto> estadoAnteproyectoConsulta = new ArrayList();
                while (resultado.next())
                {
                    EstadoAnteproyecto modalidad = new EstadoAnteproyecto();
                    modalidad.setIdEstadoAnteproyecto(resultado.getInt("idEstadoAnteproyecto"));
                    modalidad.setEstado(resultado.getString("estado"));
                    estadoAnteproyectoConsulta.add(modalidad);
                }
                respuesta.setEstadosAnteproyecto(estadoAnteproyectoConsulta);
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
