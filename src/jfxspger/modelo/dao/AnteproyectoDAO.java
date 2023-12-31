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
import jfxspger.utilidades.Utilidades;

public class AnteproyectoDAO {
    
    public static AnteproyectoRespuesta obtenerInformacionAnteproyectos() {
        AnteproyectoRespuesta respuesta = new AnteproyectoRespuesta();
        Connection conexionBD = ConexionBD.abrirConexionBD();
        respuesta.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
        if (conexionBD != null) {
            try {
                String consulta = "SELECT idAnteproyecto, Anteproyecto.idCuerpoAcademico, "
                        + "CuerpoAcademico.nombre, anteproyecto.idDirector, "
                        + "CONCAT(usuario.nombre, ' ', usuario.apellidoPaterno, ' ', "
                        + "apellidoMaterno) directorNombre, Anteproyecto.idEstado, "
                        + "estadoanteproyecto.estado, Anteproyecto.idModalidad, "
                        + "Modalidad.modalidad, Anteproyecto.idLgac, Lgac.nombre, "
                        + "proyectoInvestigacion, lineaInvestigacion, "
                        + "duracionAproximada.IdDuracionAproximada, "
                        + "duracionAproximada.duracionAproximada, nombreTrabajo, requisitos, "
                        + "maximoAlumnosParticipantes, cantidadAlumnosParticipantes, "
                        + "cantidadCodirectores, maximoCodirectores, "
                        + "descripcionProyectoInvestigacion, descripcionTrabajoRecepcional, "
                        + "resultadosEsperados, bibliografiaRecomendada, "
                        + "anteproyecto.fechaCreacion "
                        + "FROM Anteproyecto "
                        + "INNER JOIN cuerpoAcademico "
                        + "ON Anteproyecto.idCuerpoAcademico = CuerpoAcademico.idCuerpoAcademico "
                        + "INNER JOIN estadoanteproyecto "
                        + "ON anteproyecto.idEstado = estadoanteproyecto.idEstadoAnteproyecto "
                        + "INNER JOIN Modalidad "
                        + "ON Anteproyecto.idModalidad = Modalidad.idModalidad "
                        + "INNER JOIN Academico "
                        + "ON Academico.idAcademico = Anteproyecto.idDirector "
                        + "INNER JOIN usuario "
                        + "ON Academico.idUsuario = Usuario.idUsuario "
                        + "INNER JOIN duracionaproximada "
                        + "ON Anteproyecto.idDuracionAproximada = "
                        + "duracionaproximada.idDuracionAproximada "
                        + "INNER JOIN Lgac "
                        + "ON Anteproyecto.idLgac = Lgac.idLgac";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                ResultSet resultado = prepararSentencia.executeQuery();
                ArrayList<Anteproyecto> anteproyectoConsulta = new ArrayList();
                while (resultado.next())
                {
                    Anteproyecto anteproyecto = new Anteproyecto();
                    anteproyecto.setIdAnteproyecto(resultado.getInt("idAnteproyecto"));
                    anteproyecto.setIdCuerpoAcademico(resultado.getInt(
                            "Anteproyecto.idCuerpoAcademico"));
                    anteproyecto.setCuerpoAcademico(resultado.getString(
                            "CuerpoAcademico.nombre"));
                    anteproyecto.setIdDirector(resultado.getInt("idDirector"));
                    anteproyecto.setDirector(resultado.getString("directorNombre"));
                    anteproyecto.setIdEstado(resultado.getInt("idEstado"));
                    anteproyecto.setEstado(resultado.getString("estado"));
                    anteproyecto.setIdModalidad(resultado.getInt(
                            "Anteproyecto.idModalidad"));
                    anteproyecto.setModalidad(resultado.getString("Modalidad.modalidad"));
                    anteproyecto.setIdLgac(resultado.getInt("Anteproyecto.idLgac"));
                    anteproyecto.setLgac(resultado.getString("Lgac.nombre"));
                    anteproyecto.setProyectoInvestigacion(
                            resultado.getString("proyectoInvestigacion"));
                    anteproyecto.setLineaInvestigacion(resultado.getString(
                            "lineaInvestigacion"));
                    anteproyecto.setIdDuracionAproximada(resultado.getInt(
                            "idDuracionAproximada"));
                    anteproyecto.setDuracionAproximada(resultado.getString(
                            "duracionAproximada"));
                    anteproyecto.setNombreTrabajo(resultado.getString("nombreTrabajo"));
                    anteproyecto.setRequisitos(resultado.getString("requisitos"));
                    anteproyecto.setMaximoAlumnosParticipantes(
                    resultado.getInt("maximoAlumnosParticipantes"));
                    anteproyecto.setCantidadAlumnosParticipantes(
                 resultado.getInt("cantidadAlumnosParticipantes"));
                    anteproyecto.setCantidadCodirectores(resultado.getInt(
                            "cantidadCodirectores"));
                    anteproyecto.setCantidadMaximaCodirectores(resultado.getInt(
                            "maximoCodirectores"));
                    anteproyecto.setDescripcionProyectoInvestigacion(
                            resultado.getString(
                                    "descripcionProyectoInvestigacion"));
                    anteproyecto.setDescripcionTrabajoRecepcional(
            resultado.getString("descripcionTrabajoRecepcional"));
                    anteproyecto.setResultadosEsperados(resultado.getString(
                            "resultadosEsperados"));
                    anteproyecto.setBibliografiaRecomendada(
                        resultado.getString((String) "bibliografiaRecomendada"));
                    anteproyecto.setFechaCreacion(Utilidades.convertirTimeStampAStringFecha(
                            resultado.getTimestamp("fechaCreacion")));
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
    
    public static AnteproyectoRespuesta obtenerInformacionAnteproyecto(int idAnteproyecto) {
        AnteproyectoRespuesta respuesta = new AnteproyectoRespuesta();
        Connection conexionBD = ConexionBD.abrirConexionBD();
        respuesta.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
        if (conexionBD != null) {
            try {
                String consulta = "SELECT idAnteproyecto, Anteproyecto.idCuerpoAcademico, "
                        + "CuerpoAcademico.nombre, anteproyecto.idDirector, "
                        + "CONCAT(usuario.nombre, ' ', usuario.apellidoPaterno, ' ', "
                        + "apellidoMaterno) directorNombre, Anteproyecto.idEstado, "
                        + "estadoanteproyecto.estado, Anteproyecto.idModalidad, "
                        + "Modalidad.modalidad, Anteproyecto.idLgac, Lgac.nombre, "
                        + "proyectoInvestigacion, lineaInvestigacion, "
                        + "duracionAproximada.IdDuracionAproximada, "
                        + "duracionAproximada.duracionAproximada, nombreTrabajo, requisitos, "
                        + "maximoAlumnosParticipantes, cantidadAlumnosParticipantes, "
                        + "cantidadCodirectores, maximoCodirectores, "
                        + "descripcionProyectoInvestigacion, descripcionTrabajoRecepcional, "
                        + "resultadosEsperados, bibliografiaRecomendada, "
                        + "anteproyecto.fechaCreacion "
                        + "FROM Anteproyecto "
                        + "INNER JOIN cuerpoAcademico "
                        + "ON Anteproyecto.idCuerpoAcademico = CuerpoAcademico.idCuerpoAcademico "
                        + "INNER JOIN estadoanteproyecto "
                        + "ON anteproyecto.idEstado = estadoanteproyecto.idEstadoAnteproyecto "
                        + "INNER JOIN Modalidad "
                        + "ON Anteproyecto.idModalidad = Modalidad.idModalidad "
                        + "INNER JOIN Academico "
                        + "ON Academico.idAcademico = Anteproyecto.idDirector "
                        + "INNER JOIN usuario "
                        + "ON Academico.idUsuario = Usuario.idUsuario "
                        + "INNER JOIN duracionaproximada "
                        + "ON Anteproyecto.idDuracionAproximada = "
                        + "duracionaproximada.idDuracionAproximada "
                        + "INNER JOIN Lgac "
                        + "ON Anteproyecto.idLgac = Lgac.idLgac "
                        + "WHERE Anteproyecto.idAnteproyecto = ?";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                prepararSentencia.setInt(1, idAnteproyecto);
                ResultSet resultado = prepararSentencia.executeQuery();
                ArrayList<Anteproyecto> anteproyectoConsulta = new ArrayList();
                if (resultado.next())
                {
                    Anteproyecto anteproyecto = new Anteproyecto();
                    anteproyecto.setIdAnteproyecto(resultado.getInt("idAnteproyecto"));
                    anteproyecto.setIdCuerpoAcademico(resultado.getInt(
                            "Anteproyecto.idCuerpoAcademico"));
                    anteproyecto.setCuerpoAcademico(resultado.getString("CuerpoAcademico.nombre"));
                    anteproyecto.setIdDirector(resultado.getInt("idDirector"));
                    anteproyecto.setDirector(resultado.getString("directorNombre"));
                    anteproyecto.setIdEstado(resultado.getInt("idEstado"));
                    anteproyecto.setEstado(resultado.getString("estado"));
                    anteproyecto.setIdModalidad(resultado.getInt("Anteproyecto.idModalidad"));
                    anteproyecto.setModalidad(resultado.getString("Modalidad.modalidad"));
                    anteproyecto.setIdLgac(resultado.getInt("Anteproyecto.idLgac"));
                    anteproyecto.setLgac(resultado.getString("Lgac.nombre"));
                    anteproyecto.setProyectoInvestigacion(
                            resultado.getString("proyectoInvestigacion"));
                    anteproyecto.setLineaInvestigacion(resultado.getString("lineaInvestigacion"));
                    anteproyecto.setIdDuracionAproximada(resultado.getInt("idDuracionAproximada"));
                    anteproyecto.setDuracionAproximada(resultado.getString("duracionAproximada"));
                    anteproyecto.setNombreTrabajo(resultado.getString("nombreTrabajo"));
                    anteproyecto.setRequisitos(resultado.getString("requisitos"));
                    anteproyecto.setMaximoAlumnosParticipantes(resultado.getInt(
                            "maximoAlumnosParticipantes"));
                    anteproyecto.setCantidadAlumnosParticipantes(
                            resultado.getInt("cantidadAlumnosParticipantes"));
                    anteproyecto.setCantidadCodirectores(resultado.getInt("cantidadCodirectores"));
                    anteproyecto.setCantidadMaximaCodirectores(resultado.getInt("maximoCodirectores"));
                    anteproyecto.setDescripcionProyectoInvestigacion(
                            resultado.getString("descripcionProyectoInvestigacion"));
                    anteproyecto.setDescripcionTrabajoRecepcional(
                            resultado.getString("descripcionTrabajoRecepcional"));
                    anteproyecto.setResultadosEsperados(resultado.getString("resultadosEsperados"));
                    anteproyecto.setBibliografiaRecomendada(
                            resultado.getString((String) "bibliografiaRecomendada"));
                    anteproyecto.setFechaCreacion(Utilidades.convertirTimeStampAStringFecha(
                            resultado.getTimestamp("fechaCreacion")));
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
    
    public static AnteproyectoRespuesta obtenerInformacionAnteproyectosEnCuerpoAcademico(
            int idCuerpoAcademico) {
        AnteproyectoRespuesta respuesta = new AnteproyectoRespuesta();
        Connection conexionBD = ConexionBD.abrirConexionBD();
        respuesta.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
        if (conexionBD != null) {
            try {
                String consulta = "SELECT idAnteproyecto, Anteproyecto.idCuerpoAcademico, "
                        + "CuerpoAcademico.nombre, anteproyecto.idDirector, "
                        + "CONCAT(usuario.nombre, ' ', usuario.apellidoPaterno, ' ', "
                        + "apellidoMaterno) directorNombre, Anteproyecto.idEstado, "
                        + "estadoanteproyecto.estado, Anteproyecto.idModalidad, "
                        + "Modalidad.modalidad, Anteproyecto.idLgac, Lgac.nombre, "
                        + "proyectoInvestigacion, lineaInvestigacion, "
                        + "duracionAproximada.IdDuracionAproximada, "
                        + "duracionAproximada.duracionAproximada, nombreTrabajo, requisitos, "
                        + "maximoAlumnosParticipantes, cantidadAlumnosParticipantes, "
                        + "cantidadCodirectores, maximoCodirectores, "
                        + "descripcionProyectoInvestigacion, descripcionTrabajoRecepcional, "
                        + "resultadosEsperados, bibliografiaRecomendada, "
                        + "anteproyecto.fechaCreacion "
                        + "FROM Anteproyecto "
                        + "INNER JOIN cuerpoAcademico "
                        + "ON Anteproyecto.idCuerpoAcademico = CuerpoAcademico.idCuerpoAcademico "
                        + "INNER JOIN estadoanteproyecto "
                        + "ON anteproyecto.idEstado = estadoanteproyecto.idEstadoAnteproyecto "
                        + "INNER JOIN Modalidad "
                        + "ON Anteproyecto.idModalidad = Modalidad.idModalidad "
                        + "INNER JOIN Academico "
                        + "ON Academico.idAcademico = Anteproyecto.idDirector "
                        + "INNER JOIN usuario "
                        + "ON Academico.idUsuario = Usuario.idUsuario "
                        + "INNER JOIN duracionaproximada "
                        + "ON Anteproyecto.idDuracionAproximada = "
                        + "duracionaproximada.idDuracionAproximada "
                        + "INNER JOIN Lgac "
                        + "ON Anteproyecto.idLgac = Lgac.idLgac "
                        + "WHERE Anteproyecto.idCuerpoAcademico = ?";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                prepararSentencia.setInt(1, idCuerpoAcademico);
                ResultSet resultado = prepararSentencia.executeQuery();
                ArrayList<Anteproyecto> anteproyectoConsulta = new ArrayList();
                while (resultado.next())
                {
                    Anteproyecto anteproyecto = new Anteproyecto();
                    anteproyecto.setIdAnteproyecto(resultado.getInt("idAnteproyecto"));
                    anteproyecto.setIdCuerpoAcademico(resultado.
                            getInt("Anteproyecto.idCuerpoAcademico"));
                    anteproyecto.setCuerpoAcademico(resultado.getString("CuerpoAcademico.nombre"));
                    anteproyecto.setIdDirector(resultado.getInt("idDirector"));
                    anteproyecto.setDirector(resultado.getString("directorNombre"));
                    anteproyecto.setIdEstado(resultado.getInt("idEstado"));
                    anteproyecto.setEstado(resultado.getString("estado"));
                    anteproyecto.setIdModalidad(resultado.getInt("Anteproyecto.idModalidad"));
                    anteproyecto.setModalidad(resultado.getString("Modalidad.modalidad"));
                    anteproyecto.setIdLgac(resultado.getInt("Anteproyecto.idLgac"));
                    anteproyecto.setLgac(resultado.getString("Lgac.nombre"));
                    anteproyecto.setProyectoInvestigacion(
                            resultado.getString("proyectoInvestigacion"));
                    anteproyecto.setLineaInvestigacion(resultado.getString("lineaInvestigacion"));
                    anteproyecto.setIdDuracionAproximada(resultado.getInt("idDuracionAproximada"));
                    anteproyecto.setDuracionAproximada(resultado.getString("duracionAproximada"));
                    anteproyecto.setNombreTrabajo(resultado.getString("nombreTrabajo"));
                    anteproyecto.setRequisitos(resultado.getString("requisitos"));
                    anteproyecto.setMaximoAlumnosParticipantes(resultado.
                            getInt("maximoAlumnosParticipantes"));
                    anteproyecto.setCantidadAlumnosParticipantes(
                            resultado.getInt("cantidadAlumnosParticipantes"));
                    anteproyecto.setCantidadCodirectores(resultado.getInt("cantidadCodirectores"));
                    anteproyecto.setCantidadMaximaCodirectores(resultado.getInt("maximoCodirectores"));
                    anteproyecto.setDescripcionProyectoInvestigacion(
                            resultado.getString("descripcionProyectoInvestigacion"));
                    anteproyecto.setDescripcionTrabajoRecepcional(
                            resultado.getString("descripcionTrabajoRecepcional"));
                    anteproyecto.setResultadosEsperados(resultado.getString("resultadosEsperados"));
                    anteproyecto.setBibliografiaRecomendada(
                            resultado.getString((String) "bibliografiaRecomendada"));
                    anteproyecto.setFechaCreacion(Utilidades.convertirTimeStampAStringFecha(
                            resultado.getTimestamp("fechaCreacion")));
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
    
    public static AnteproyectoRespuesta obtenerInformacionRevisionesAnteproyectos(
            int idCuerpoAcademico) {
        AnteproyectoRespuesta respuesta = new AnteproyectoRespuesta();
        Connection conexionBD = ConexionBD.abrirConexionBD();
        respuesta.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
        if (conexionBD != null) {
            try {
                String consulta = "SELECT idAnteproyecto, Anteproyecto.idCuerpoAcademico, "
                        + "CuerpoAcademico.nombre, anteproyecto.idDirector, "
                        + "CONCAT(usuario.nombre, ' ', usuario.apellidoPaterno, ' ', "
                        + "apellidoMaterno) directorNombre, Anteproyecto.idEstado, "
                        + "estadoanteproyecto.estado, Anteproyecto.idModalidad, "
                        + "Modalidad.modalidad, Anteproyecto.idLgac, Lgac.nombre, "
                        + "proyectoInvestigacion, lineaInvestigacion, "
                        + "duracionAproximada.IdDuracionAproximada, "
                        + "duracionAproximada.duracionAproximada, nombreTrabajo, requisitos, "
                        + "maximoAlumnosParticipantes, cantidadAlumnosParticipantes, "
                        + "cantidadCodirectores, maximoCodirectores, "
                        + "descripcionProyectoInvestigacion, descripcionTrabajoRecepcional, "
                        + "resultadosEsperados, bibliografiaRecomendada, "
                        + "anteproyecto.fechaCreacion "
                        + "FROM Anteproyecto "
                        + "INNER JOIN cuerpoAcademico "
                        + "ON Anteproyecto.idCuerpoAcademico = CuerpoAcademico.idCuerpoAcademico "
                        + "INNER JOIN estadoanteproyecto "
                        + "ON anteproyecto.idEstado = estadoanteproyecto.idEstadoAnteproyecto "
                        + "INNER JOIN Modalidad "
                        + "ON Anteproyecto.idModalidad = Modalidad.idModalidad "
                        + "INNER JOIN Academico "
                        + "ON Academico.idAcademico = Anteproyecto.idDirector "
                        + "INNER JOIN usuario "
                        + "ON Academico.idUsuario = Usuario.idUsuario "
                        + "INNER JOIN duracionaproximada "
                        + "ON Anteproyecto.idDuracionAproximada = "
                        + "duracionaproximada.idDuracionAproximada "
                        + "INNER JOIN Lgac "
                        + "ON Anteproyecto.idLgac = Lgac.idLgac "
                        + "WHERE Anteproyecto.idCuerpoAcademico = ? "
                        + "AND Anteproyecto.idEstado != 1";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                prepararSentencia.setInt(1, idCuerpoAcademico);
                ResultSet resultado = prepararSentencia.executeQuery();
                ArrayList<Anteproyecto> anteproyectoConsulta = new ArrayList();
                while (resultado.next())
                {
                    Anteproyecto anteproyecto = new Anteproyecto();
                    anteproyecto.setIdAnteproyecto(resultado.getInt("idAnteproyecto"));
                    anteproyecto.setIdCuerpoAcademico(resultado.
                            getInt("Anteproyecto.idCuerpoAcademico"));
                    anteproyecto.setCuerpoAcademico(resultado.getString("CuerpoAcademico.nombre"));
                    anteproyecto.setIdDirector(resultado.getInt("idDirector"));
                    anteproyecto.setDirector(resultado.getString("directorNombre"));
                    anteproyecto.setIdEstado(resultado.getInt("idEstado"));
                    anteproyecto.setEstado(resultado.getString("estado"));
                    anteproyecto.setIdModalidad(resultado.getInt("Anteproyecto.idModalidad"));
                    anteproyecto.setModalidad(resultado.getString("Modalidad.modalidad"));
                    anteproyecto.setIdLgac(resultado.getInt("Anteproyecto.idLgac"));
                    anteproyecto.setLgac(resultado.getString("Lgac.nombre"));
                    anteproyecto.setProyectoInvestigacion(
                            resultado.getString("proyectoInvestigacion"));
                    anteproyecto.setLineaInvestigacion(resultado.getString("lineaInvestigacion"));
                    anteproyecto.setIdDuracionAproximada(resultado.getInt("idDuracionAproximada"));
                    anteproyecto.setDuracionAproximada(resultado.getString("duracionAproximada"));
                    anteproyecto.setNombreTrabajo(resultado.getString("nombreTrabajo"));
                    anteproyecto.setRequisitos(resultado.getString("requisitos"));
                    anteproyecto.setMaximoAlumnosParticipantes(resultado.
                            getInt("maximoAlumnosParticipantes"));
                    anteproyecto.setCantidadAlumnosParticipantes(
                            resultado.getInt("cantidadAlumnosParticipantes"));
                    anteproyecto.setCantidadCodirectores(resultado.getInt("cantidadCodirectores"));
                    anteproyecto.setCantidadMaximaCodirectores(resultado.getInt("maximoCodirectores"));
                    anteproyecto.setDescripcionProyectoInvestigacion(
                            resultado.getString("descripcionProyectoInvestigacion"));
                    anteproyecto.setDescripcionTrabajoRecepcional(
                            resultado.getString("descripcionTrabajoRecepcional"));
                    anteproyecto.setResultadosEsperados(resultado.getString("resultadosEsperados"));
                    anteproyecto.setBibliografiaRecomendada(
                            resultado.getString((String) "bibliografiaRecomendada"));
                    anteproyecto.setFechaCreacion(Utilidades.convertirTimeStampAStringFecha(
                            resultado.getTimestamp("fechaCreacion")));
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
                        "lineaInvestigacion, idDuracionAproximada, nombreTrabajo, " + 
                        "requisitos, maximoAlumnosParticipantes, cantidadAlumnosParticipantes, " + 
                        "descripcionProyectoInvestigacion, descripcionTrabajoRecepcional, "+ 
                        "resultadosEsperados, bibliografiaRecomendada, fechaCreacion, " +
                        "cantidadCodirectores, maximoCodirectores) " +
                        "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,CURRENT_TIMESTAMP(),0,2)";

                PreparedStatement prepararSentencia =  conexionBD.prepareStatement(sentencia);
                prepararSentencia.setInt(1, nuevoAnteproyecto.getIdCuerpoAcademico());
                prepararSentencia.setInt(2, nuevoAnteproyecto.getIdDirector());
                prepararSentencia.setInt(3, nuevoAnteproyecto.getIdEstado());
                prepararSentencia.setInt(4, nuevoAnteproyecto.getIdModalidad());
                prepararSentencia.setInt(5, nuevoAnteproyecto.getIdLgac());
                prepararSentencia.setString(6, nuevoAnteproyecto.getProyectoInvestigacion());
                prepararSentencia.setString(7, nuevoAnteproyecto.getLineaInvestigacion());
                prepararSentencia.setInt(8, nuevoAnteproyecto.getIdDuracionAproximada());
                prepararSentencia.setString(9, nuevoAnteproyecto.getNombreTrabajo());
                prepararSentencia.setString(10, nuevoAnteproyecto.getRequisitos());
                prepararSentencia.setInt(11, nuevoAnteproyecto.getMaximoAlumnosParticipantes());
                prepararSentencia.setInt(12, nuevoAnteproyecto.getCantidadAlumnosParticipantes());
                prepararSentencia.setString(13, 
                        nuevoAnteproyecto.getDescripcionProyectoInvestigacion());
                prepararSentencia.setString(14, 
                        nuevoAnteproyecto.getDescripcionTrabajoRecepcional());
                prepararSentencia.setString(15, nuevoAnteproyecto.getResultadosEsperados());
                prepararSentencia.setString(16, nuevoAnteproyecto.getBibliografiaRecomendada());
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
                String sentencia = "UPDATE Anteproyecto SET idModalidad = ?, idLgac = ?, "
                        + "proyectoInvestigacion = ?, lineaInvestigacion = ?, "
                        + "idDuracionAproximada = ?, nombreTrabajo = ?, requisitos = ?, "
                        + "maximoAlumnosParticipantes = ?, "
                        + "descripcionProyectoInvestigacion = ?, "
                        + "descripcionTrabajoRecepcional = ?, resultadosEsperados = ?, "
                        + "bibliografiaRecomendada = ? "
                        + "WHERE idAnteproyecto = ?";
                
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
                prepararSentencia.setInt(1, anteproyectoEdicion.getIdModalidad());
                prepararSentencia.setInt(2, anteproyectoEdicion.getIdLgac());
                prepararSentencia.setString(3, anteproyectoEdicion.getProyectoInvestigacion());
                prepararSentencia.setString(4, anteproyectoEdicion.getLineaInvestigacion());
                prepararSentencia.setInt(5, anteproyectoEdicion.getIdDuracionAproximada());
                prepararSentencia.setString(6, anteproyectoEdicion.getNombreTrabajo());
                prepararSentencia.setString(7, anteproyectoEdicion.getRequisitos());
                prepararSentencia.setInt(8, anteproyectoEdicion.getMaximoAlumnosParticipantes());
                prepararSentencia.setString(9, anteproyectoEdicion.
                        getDescripcionProyectoInvestigacion());
                prepararSentencia.setString(10, anteproyectoEdicion.
                        getDescripcionTrabajoRecepcional());
                prepararSentencia.setString(11, anteproyectoEdicion.getResultadosEsperados());
                prepararSentencia.setString(12, anteproyectoEdicion.getBibliografiaRecomendada());
                prepararSentencia.setInt(13, anteproyectoEdicion.getIdAnteproyecto());
                
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
    
    public static int incrementarContadorAlumnosEnAnteproyecto(int idAnteproyecto) {
        int respuesta;
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if (conexionBD != null) {
            try {
                String sentencia = "UPDATE anteproyecto SET cantidadAlumnosParticipantes = "
                        + "cantidadAlumnosParticipantes + 1 WHERE idAnteproyecto = ?";
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
    
      public static int incrementarContadorCodirectoresEnAnteproyecto(int idAnteproyecto) {
        int respuesta;
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if (conexionBD != null) {
            try {
                String sentencia = "UPDATE anteproyecto SET cantidadCodirectores = "
                        + "cantidadCodirectores + 1 WHERE idAnteproyecto = ?";
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
     
    public static int decrementarContadorAlumnosEnAnteproyecto(int idAnteproyecto) {
        int respuesta;
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if (conexionBD != null) {
            try {
                String sentencia = "UPDATE anteproyecto SET cantidadAlumnosParticipantes = "
                        + "cantidadAlumnosParticipantes - 1 WHERE idAnteproyecto = ?";
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
    
    public static int decrementarContadorCodirectoresEnAnteproyecto(int idAnteproyecto) {
        int respuesta;
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if (conexionBD != null) {
            try {
                String sentencia = "UPDATE anteproyecto SET cantidadCodirectores = "
                        + "cantidadCodirectores - 1 WHERE idAnteproyecto = ?";
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
    
    public static int actualizarEstadoNoDisponibleAnteproyecto(int idAnteproyecto) {
        int respuesta;
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if (conexionBD != null) {
            try {
                String sentencia = "UPDATE anteproyecto SET idEstado = 3 "
                        + "WHERE idAnteproyecto = ?";
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
    
    public static int actualizarEstadoDisponibleAnteproyecto(int idAnteproyecto) {
        int respuesta;
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if (conexionBD != null) {
            try {
                String sentencia = "UPDATE anteproyecto SET idEstado = 2 "
                        + "WHERE idAnteproyecto = ?";
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
    
     public static int actualizarEstadoRechazadoAnteproyecto(int idAnteproyecto) {
        int respuesta;
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if (conexionBD != null) {
            try {
                String sentencia = "UPDATE anteproyecto SET idEstado = 4 "
                        + "WHERE idAnteproyecto = ?";
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
    
    public static AnteproyectoRespuesta obtenerInformacionAnteproyectoConValidacionPendiente(
            int idCuerpoAcademico) {
        AnteproyectoRespuesta respuesta = new AnteproyectoRespuesta();
        Connection conexionBD = ConexionBD.abrirConexionBD();
        respuesta.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
        if (conexionBD != null) {
            try {
                String consulta = "SELECT idAnteproyecto, Anteproyecto.idCuerpoAcademico, "
                        + "CuerpoAcademico.nombre, anteproyecto.idDirector, "
                        + "CONCAT(usuario.nombre, ' ', usuario.apellidoPaterno, ' ', "
                        + "apellidoMaterno) directorNombre, Anteproyecto.idEstado, "
                        + "estadoanteproyecto.estado, Anteproyecto.idModalidad, "
                        + "Modalidad.modalidad, Anteproyecto.idLgac, Lgac.nombre, "
                        + "proyectoInvestigacion, lineaInvestigacion, "
                        + "duracionAproximada.IdDuracionAproximada, "
                        + "duracionAproximada.duracionAproximada, nombreTrabajo, requisitos, "
                        + "cantidadAlumnosParticipantes, descripcionProyectoInvestigacion, "
                        + "descripcionTrabajoRecepcional, resultadosEsperados, "
                        + "bibliografiaRecomendada, anteproyecto.fechaCreacion "
                        + "FROM Anteproyecto "
                        + "INNER JOIN cuerpoAcademico "
                        + "ON Anteproyecto.idCuerpoAcademico = CuerpoAcademico.idCuerpoAcademico "
                        + "INNER JOIN estadoanteproyecto "
                        + "ON anteproyecto.idEstado = estadoanteproyecto.idEstadoAnteproyecto "
                        + "INNER JOIN Modalidad "
                        + "ON Anteproyecto.idModalidad = Modalidad.idModalidad "
                        + "INNER JOIN Academico "
                        + "ON Academico.idAcademico = Anteproyecto.idDirector "
                        + "INNER JOIN usuario "
                        + "ON Academico.idUsuario = Usuario.idUsuario "
                        + "INNER JOIN duracionaproximada "
                        + "ON Anteproyecto.idDuracionAproximada = "
                        + "duracionaproximada.idDuracionAproximada "
                        + "INNER JOIN Lgac "
                        + "ON Anteproyecto.idLgac = Lgac.idLgac "
                        + "WHERE idEstado = 1 AND anteproyecto.idCuerpoAcademico = ?";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                prepararSentencia.setInt(1, idCuerpoAcademico);
                ResultSet resultado = prepararSentencia.executeQuery();
                ArrayList<Anteproyecto> anteproyectoConsulta = new ArrayList();
                while (resultado.next())
                {
                    Anteproyecto anteproyecto = new Anteproyecto();
                    anteproyecto.setIdAnteproyecto(resultado.getInt("idAnteproyecto"));
                    anteproyecto.setIdCuerpoAcademico(resultado.getInt("Anteproyecto.idCuerpoAcademico"));
                    anteproyecto.setCuerpoAcademico(resultado.getString("CuerpoAcademico.nombre"));
                    anteproyecto.setIdDirector(resultado.getInt("idDirector"));
                    anteproyecto.setDirector(resultado.getString("directorNombre"));
                    anteproyecto.setIdEstado(resultado.getInt("idEstado"));
                    anteproyecto.setEstado(resultado.getString("estado"));
                    anteproyecto.setIdModalidad(resultado.getInt("Anteproyecto.idModalidad"));
                    anteproyecto.setModalidad(resultado.getString("Modalidad.modalidad"));
                    anteproyecto.setIdLgac(resultado.getInt("Anteproyecto.idLgac"));
                    anteproyecto.setLgac(resultado.getString("Lgac.nombre"));
                    anteproyecto.setProyectoInvestigacion(
                            resultado.getString("proyectoInvestigacion"));
                    anteproyecto.setLineaInvestigacion(resultado.getString("lineaInvestigacion"));
                    anteproyecto.setIdDuracionAproximada(resultado.getInt("idDuracionAproximada"));
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
                    anteproyecto.setFechaCreacion(Utilidades.convertirTimeStampAStringFecha(
                            resultado.getTimestamp("fechaCreacion")));
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
    
    public static AnteproyectoRespuesta obtenerInformacionAnteproyectosEstadoDisponible() {
        AnteproyectoRespuesta respuesta = new AnteproyectoRespuesta();
        Connection conexionBD = ConexionBD.abrirConexionBD();
        respuesta.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
        if (conexionBD != null) {
            try {
                String consulta = "SELECT idAnteproyecto, Anteproyecto.idCuerpoAcademico, "
                        + "CuerpoAcademico.nombre, anteproyecto.idDirector, "
                        + "CONCAT(usuario.nombre, ' ', usuario.apellidoPaterno, ' ', "
                        + "apellidoMaterno) directorNombre, Anteproyecto.idEstado, "
                        + "estadoanteproyecto.estado, Anteproyecto.idModalidad, "
                        + "Modalidad.modalidad, Anteproyecto.idLgac, Lgac.nombre, "
                        + "proyectoInvestigacion, lineaInvestigacion, "
                        + "duracionAproximada.IdDuracionAproximada, "
                        + "duracionAproximada.duracionAproximada, nombreTrabajo, requisitos, "
                        + "cantidadAlumnosParticipantes, descripcionProyectoInvestigacion, "
                        + "descripcionTrabajoRecepcional, resultadosEsperados, "
                        + "bibliografiaRecomendada, anteproyecto.fechaCreacion "
                        + "FROM Anteproyecto "
                        + "INNER JOIN cuerpoAcademico "
                        + "ON Anteproyecto.idCuerpoAcademico = CuerpoAcademico.idCuerpoAcademico "
                        + "INNER JOIN estadoanteproyecto "
                        + "ON anteproyecto.idEstado = estadoanteproyecto.idEstadoAnteproyecto "
                        + "INNER JOIN Modalidad "
                        + "ON Anteproyecto.idModalidad = Modalidad.idModalidad "
                        + "INNER JOIN Academico "
                        + "ON Academico.idAcademico = Anteproyecto.idDirector "
                        + "INNER JOIN usuario "
                        + "ON Academico.idUsuario = Usuario.idUsuario "
                        + "INNER JOIN duracionaproximada "
                        + "ON Anteproyecto.idDuracionAproximada = "
                        + "duracionaproximada.idDuracionAproximada "
                        + "INNER JOIN Lgac "
                        + "ON Anteproyecto.idLgac = Lgac.idLgac "
                        + "WHERE idEstado = 2";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                ResultSet resultado = prepararSentencia.executeQuery();
                ArrayList<Anteproyecto> anteproyectoConsulta = new ArrayList();
                while (resultado.next())
                {
                    Anteproyecto anteproyecto = new Anteproyecto();
                    anteproyecto.setIdAnteproyecto(resultado.getInt("idAnteproyecto"));
                    anteproyecto.setIdCuerpoAcademico(resultado.getInt("Anteproyecto.idCuerpoAcademico"));
                    anteproyecto.setCuerpoAcademico(resultado.getString("CuerpoAcademico.nombre"));
                    anteproyecto.setIdDirector(resultado.getInt("idDirector"));
                    anteproyecto.setDirector(resultado.getString("directorNombre"));
                    anteproyecto.setIdEstado(resultado.getInt("idEstado"));
                    anteproyecto.setEstado(resultado.getString("estado"));
                    anteproyecto.setIdModalidad(resultado.getInt("Anteproyecto.idModalidad"));
                    anteproyecto.setModalidad(resultado.getString("Modalidad.modalidad"));
                    anteproyecto.setIdLgac(resultado.getInt("Anteproyecto.idLgac"));
                    anteproyecto.setLgac(resultado.getString("Lgac.nombre"));
                    anteproyecto.setProyectoInvestigacion(
                            resultado.getString("proyectoInvestigacion"));
                    anteproyecto.setLineaInvestigacion(resultado.getString("lineaInvestigacion"));
                    anteproyecto.setIdDuracionAproximada(resultado.getInt("idDuracionAproximada"));
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
                    anteproyecto.setFechaCreacion(Utilidades.convertirTimeStampAStringFecha(
                            resultado.getTimestamp("fechaCreacion")));
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
    
}
