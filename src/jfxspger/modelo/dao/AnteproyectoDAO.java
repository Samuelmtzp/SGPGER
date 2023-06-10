/*
* Autor: Samuel Martínez Pazos
* Fecha de creación: 22/05/2023
* Descripción: Clase encargada de administrar la información de Anteproyecto
* en la base de datos
*/
package jfxspger.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import jfxspger.modelo.ConexionBD;
import jfxspger.modelo.pojo.AnteproyectoRespuesta;
import jfxspger.modelo.pojo.Anteproyecto;
import jfxspger.utilidades.Constantes;

public class AnteproyectoDAO {
    
    public static AnteproyectoRespuesta obtenerInformacionAnteproyecto() {
        AnteproyectoRespuesta respuesta = new AnteproyectoRespuesta();
        Connection conexionBD = ConexionBD.abrirConexionBD();
        respuesta.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
        if (conexionBD != null) {
            try {
                String consulta = "SELECT idAnteproyecto, idCuerpoAcademico, " +
                        "idDirector, idEstado, idModalidad, idLgac, proyectoInvestigacion, " + 
                        "lineaInvestigacion, duracionAproximada, nombreTrabajo, requisitos, " + 
                        "cantidadAlumnosParticipantes, descripcionProyectoInvestigacion, " + 
                        "descripcionTrabajoRecepcional, resultadosEsperados,  " + 
                        "bibliografiaRecomendada, comentarios " +
                        "FROM Anteproyecto";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                ResultSet resultado = prepararSentencia.executeQuery();
                ArrayList<Anteproyecto> anteproyectoConsulta = new ArrayList();
                while (resultado.next())
                {
                    Anteproyecto anteproyecto = new Anteproyecto();
                    anteproyecto.setIdAnteproyecto(resultado.getInt("idAnteproyecto"));
                    anteproyecto.setIdCuerpoAcademico(resultado.getInt("idCuerpoAcademico"));
                    anteproyecto.setIdDirector(resultado.getInt("idDirector"));
                    anteproyecto.setIdEstado(resultado.getInt("idEstado"));
                    anteproyecto.setIdModalidad(resultado.getInt("idModalidad"));
                    anteproyecto.setIdLgac(resultado.getInt("idLgac"));
                    anteproyecto.setProyectoInvestigacion(
                            resultado.getString("proyectoInvestigacion"));
                    anteproyecto.setLineaInvestigacion(resultado.getString("lineaInvestigacion"));
                    anteproyecto.setDuracionAproximada(resultado.getString("duracionAproximada"));
                    anteproyecto.setNombreTrabajo(resultado.getString("nombreTrabajo"));
                    anteproyecto.setRequisitos(resultado.getString("requisitos"));
                    anteproyecto.setCantidadAlumnosParticipantes(
                            resultado.getInt("cantidadAlumnosParticipantes"));
                    anteproyecto.setDescripcionProyectoInvestigacion(
                            resultado.getString("descripcionProyectoInvestigacion"));
                    anteproyecto.setDescripcionTrabajoRecepcional(
                            resultado.getString("descripcionTrabajoRecepcional"));
                    anteproyecto.setResultadosEsperados(resultado.getString("resultadosEsperados"));
                    anteproyecto.setBibliografiaRecomendada(
                            resultado.getString("bibliografiaRecomendada"));
                    anteproyecto.setComentarios(resultado.getString("comentarios"));
                    anteproyectoConsulta.add(anteproyecto);
                }
                respuesta.setAnteproyectos(anteproyectoConsulta);
                conexionBD.close();
            } catch (SQLException e) {
                respuesta.setCodigoRespuesta(Constantes.ERROR_CONSULTA);
            }
        } else {
            respuesta.setCodigoRespuesta(Constantes.ERROR_CONEXION);
        }
        return respuesta;
    }
    
    public static int guardarAnteproyecto(Anteproyecto nuevoAnteproyecto) {
        int respuesta;
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if (conexionBD != null) {
            try {
                String sentencia = "INSERT INTO Anteproyecto (idCuerpoAcademico, " + 
                        "idDirector, idEstado, idModalidad, idLgac, proyectoInvestigacion, " + 
                        "lineaInvestigacion, duracionAproximada, nombreTrabajo, " + 
                        "requisitos, cantidadAlumnosParticipantes, " + 
                        "descripcionProyectoInvestigacion, descripcionTrabajoRecepcional, "+ 
                        "resultadosEsperados, bibliografiaRecomendada, comentarios) " +
                        "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

                PreparedStatement prepararSentencia =  conexionBD.prepareStatement(sentencia);
                prepararSentencia.setInt(1, nuevoAnteproyecto.getIdCuerpoAcademico());
                prepararSentencia.setInt(2, nuevoAnteproyecto.getIdDirector());
                prepararSentencia.setInt(3, nuevoAnteproyecto.getIdEstado());
                prepararSentencia.setInt(4, nuevoAnteproyecto.getIdModalidad());
                prepararSentencia.setInt(5, nuevoAnteproyecto.getIdLgac());
                prepararSentencia.setString(6, nuevoAnteproyecto.getProyectoInvestigacion());
                prepararSentencia.setString(7, nuevoAnteproyecto.getLineaInvestigacion());
                prepararSentencia.setString(8, nuevoAnteproyecto.getDuracionAproximada());
                prepararSentencia.setString(9, nuevoAnteproyecto.getNombreTrabajo());
                prepararSentencia.setString(10, nuevoAnteproyecto.getRequisitos());
                prepararSentencia.setInt(11, nuevoAnteproyecto.getCantidadAlumnosParticipantes());
                prepararSentencia.setString(12, 
                        nuevoAnteproyecto.getDescripcionProyectoInvestigacion());
                prepararSentencia.setString(13, 
                        nuevoAnteproyecto.getDescripcionTrabajoRecepcional());
                prepararSentencia.setString(14, nuevoAnteproyecto.getResultadosEsperados());
                prepararSentencia.setString(15, nuevoAnteproyecto.getBibliografiaRecomendada());
                prepararSentencia.setString(16, nuevoAnteproyecto.getComentarios());

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
    
    public static int modificarAnteproyecto(Anteproyecto anteproyectoEdicion) {
        int respuesta;
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if (conexionBD != null) {
            try {
                String sentencia = "UPDATE Anteproyecto SET idCuerpoAcademico = ?, " + 
                        "idDirector = ?, idEstado = ?, idModalidad = ?, idLgac = ?, " + 
                        "proyectoInvestigacion = ?, lineaInvestigacion = ?, " + 
                        "duracionAproximada = ?, nombreTrabajo = ?, requisitos = ?, " + 
                        "cantidadAlumnosParticipantes = ?, descripcionProyectoInvestigacion = ?, " + 
                        "descripcionTrabajoRecepcional = ?, resultadosEsperados = ?, " + 
                        "bibliografiaRecomendada = ?, comentarios = ? " +
                        "WHERE idAnteproyecto = ?";
                
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
                prepararSentencia.setInt(1, anteproyectoEdicion.getIdCuerpoAcademico());
                prepararSentencia.setInt(2, anteproyectoEdicion.getIdDirector());
                prepararSentencia.setInt(3, anteproyectoEdicion.getIdEstado());
                prepararSentencia.setInt(4, anteproyectoEdicion.getIdModalidad());
                prepararSentencia.setInt(5, anteproyectoEdicion.getIdLgac());
                prepararSentencia.setString(6, anteproyectoEdicion.getProyectoInvestigacion());
                prepararSentencia.setString(7, anteproyectoEdicion.getLineaInvestigacion());
                prepararSentencia.setString(8, anteproyectoEdicion.getDuracionAproximada());
                prepararSentencia.setString(9, anteproyectoEdicion.getNombreTrabajo());
                prepararSentencia.setString(10, anteproyectoEdicion.getRequisitos());
                prepararSentencia.setInt(11, anteproyectoEdicion.getCantidadAlumnosParticipantes());
                prepararSentencia.setString(12, anteproyectoEdicion.getDescripcionProyectoInvestigacion());
                prepararSentencia.setString(13, anteproyectoEdicion.getDescripcionTrabajoRecepcional());
                prepararSentencia.setString(14, anteproyectoEdicion.getResultadosEsperados());
                prepararSentencia.setString(15, anteproyectoEdicion.getBibliografiaRecomendada());
                prepararSentencia.setString(16, anteproyectoEdicion.getComentarios());
                prepararSentencia.setInt(17, anteproyectoEdicion.getIdAnteproyecto());
                
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
    
    public static int eliminarAnteproyecto(int idAnteproyecto) {
        int respuesta;
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if (conexionBD != null) {
            try {
                String sentencia = "DELETE FROM Anteproyecto WHERE idAnteproyecto = ?";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
                prepararSentencia.setInt(1, idAnteproyecto);
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
