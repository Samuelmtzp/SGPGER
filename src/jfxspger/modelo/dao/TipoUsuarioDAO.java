package jfxspger.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import jfxspger.modelo.ConexionBD;
import jfxspger.modelo.pojo.TipoUsuarioRespuesta;
import jfxspger.modelo.pojo.TipoUsuario;
import jfxspger.utilidades.Constantes;

public class TipoUsuarioDAO {
    
    public static TipoUsuarioRespuesta obtenerInformacionTipoUsuario() {
        TipoUsuarioRespuesta respuesta = new TipoUsuarioRespuesta();
        Connection conexionBD = ConexionBD.abrirConexionBD();
        respuesta.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
        if (conexionBD != null) {
            try {
                String consulta = "SELECT idTipoUsuario, tipoUsuario " +
                        "FROM TipoUsuario";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                ResultSet resultado = prepararSentencia.executeQuery();
                ArrayList<TipoUsuario> tipoUsuarioConsulta = new ArrayList();
                while (resultado.next())
                {
                    TipoUsuario tipoUsuario = new TipoUsuario();
                    tipoUsuario.setIdTipoUsuario(resultado.getInt("idTipoUsuario"));
                    tipoUsuario.setTipoUsuario(resultado.getString("tipoUsuario"));
                    tipoUsuarioConsulta.add(tipoUsuario);
                }
                respuesta.setTiposUsuario(tipoUsuarioConsulta);
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
