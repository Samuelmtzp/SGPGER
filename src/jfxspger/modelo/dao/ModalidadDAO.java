/*
* Autor: Samuel Martínez Pazos
* Fecha de creación: 10/06/2023
* Descripción: Clase encargada de administrar la 
* información de modalidades en la base de datos
*/
package jfxspger.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import jfxspger.modelo.ConexionBD;
import jfxspger.modelo.pojo.ModalidadRespuesta;
import jfxspger.modelo.pojo.Modalidad;
import jfxspger.utilidades.Constantes;

public class ModalidadDAO {
    public static ModalidadRespuesta obtenerInformacionModalidad() {
        ModalidadRespuesta respuesta = new ModalidadRespuesta();
        Connection conexionBD = ConexionBD.abrirConexionBD();
        respuesta.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
        if (conexionBD != null) {
            try {
                String consulta = "SELECT idModalidad, modalidad " +
                        "FROM Modalidad";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                ResultSet resultado = prepararSentencia.executeQuery();
                ArrayList<Modalidad> modalidadConsulta = new ArrayList();
                while (resultado.next())
                {
                    Modalidad modalidad = new Modalidad();
                    modalidad.setIdModalidad(resultado.getInt("idModalidad"));
                    modalidad.setModalidad(resultado.getString("modalidad"));
                    modalidadConsulta.add(modalidad);
                }
                respuesta.setModalidades(modalidadConsulta);
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
