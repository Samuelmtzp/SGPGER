/*
* Autor: Samuel Martínez Pazos
* Fecha de creación: 22/05/2023
* Descripción: Clase encargada de administrar la información de Archivo
* en la base de datos
*/
package jfxspger.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import jfxspger.modelo.ConexionBD;
import jfxspger.modelo.pojo.DocumentoRespuesta;
import jfxspger.modelo.pojo.Documento;
import jfxspger.utilidades.Constantes;

public class DocumentoDAO {
    
    public static DocumentoRespuesta obtenerInformacionArchivo() {
        DocumentoRespuesta respuesta = new DocumentoRespuesta();
        Connection conexionBD = ConexionBD.abrirConexionBD();
        respuesta.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
        if (conexionBD != null) {
            try {
                String consulta = "SELECT idDocumento, archivoDocumento, " +
                        "nombre, idEntrega " +
                        "FROM Documento";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                ResultSet resultado = prepararSentencia.executeQuery();
                ArrayList<Documento> archivoConsulta = new ArrayList();
                while (resultado.next())
                {
                    Documento archivo = new Documento();
                    archivo.setIdDocumento(resultado.getInt("idDocumento"));
                    archivo.setArchivoDocumento(resultado.getBytes("archivoDocumento"));
                    archivo.setNombre(resultado.getString("nombre"));
                    archivo.setIdEntrega(resultado.getInt("idEntrega"));
                    archivoConsulta.add(archivo);
                }
                respuesta.setDocumentos(archivoConsulta);
                conexionBD.close();
            } catch (SQLException e) {
                respuesta.setCodigoRespuesta(Constantes.ERROR_CONSULTA);
            }
        } else {
            respuesta.setCodigoRespuesta(Constantes.ERROR_CONEXION);
        }
        return respuesta;
    }
    
    public static int guardarArchivo(Documento nuevoArchivo) {
        int respuesta;
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if (conexionBD != null) {
            try {
                String sentencia = "INSERT INTO Documento (archivoDocumento, " + 
                        "nombre, idEntrega) " +
                        "VALUES (?,?,?)";
                PreparedStatement prepararSentencia =  conexionBD.prepareStatement(sentencia);
                prepararSentencia.setBytes(1, nuevoArchivo.getArchivoDocumento());
                prepararSentencia.setString(2, nuevoArchivo.getNombre());
                prepararSentencia.setInt(3, nuevoArchivo.getIdEntrega());
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
    
    public static int modificarArchivo(Documento archivoEdicion) {
        int respuesta;
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if (conexionBD != null) {
            try {
                String sentencia = "UPDATE Documento SET archivoDocumento = ?, " + 
                        "nombre = ?, idEntrega = ? " +
                        "WHERE idDocumento = ?";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
                prepararSentencia.setBytes(1, archivoEdicion.getArchivoDocumento());
                prepararSentencia.setString(2, archivoEdicion.getNombre());
                prepararSentencia.setInt(3, archivoEdicion.getIdEntrega());
                prepararSentencia.setInt(4, archivoEdicion.getIdDocumento());
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
    
    public static int eliminarArchivo(int idArchivo) {
        int respuesta;
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if (conexionBD != null) {
            try {
                String sentencia = "DELETE FROM Documento WHERE idDocumento = ?";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
                prepararSentencia.setInt(1, idArchivo);
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