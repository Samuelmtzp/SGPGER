/*
* Autor: Samuel Martínez Pazos
* Fecha de creación: 09/05/2023
* Descripción: Clase encargada de administrar la información de usuario
* en la base de datos
*/

package jfxspger.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import jfxspger.modelo.ConexionBD;
import jfxspger.modelo.pojo.Usuario;
import jfxspger.modelo.pojo.UsuarioRespuesta;
import jfxspger.utilidades.Constantes;

public class UsuarioDAO {
    
    public static UsuarioRespuesta obtenerInformacionUsuarios() {
        UsuarioRespuesta respuesta = new UsuarioRespuesta();
        Connection conexionBD = ConexionBD.abrirConexionBD();
        respuesta.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
        if (conexionBD != null) {
            try {
                String consulta = "SELECT idUsuario, Usuario.idTipoUsuario, "
                        + "TipoUsuario.tipoUsuario, "
                        + "username, password, correo, nombre, apellidoPaterno, "
                        + "apellidoMaterno, telefono, fechaCreacion "
                        + "FROM Usuario "
                        + "INNER JOIN TipoUsuario "
                        + "ON Usuario.idTipoUsuario = TipoUsuario.idTipoUsuario ";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                ResultSet resultado = prepararSentencia.executeQuery();
                ArrayList<Usuario> usuariosConsulta = new ArrayList();
                while (resultado.next())
                {
                    Usuario usuario = new Usuario();
                    usuario.setIdUsuario(resultado.getInt("idUsuario"));
                    usuario.setIdTipoUsuario(resultado.getInt("Usuario.idTipoUsuario"));
                    usuario.setTipoUsuario(resultado.getString("TipoUsuario.tipoUsuario"));
                    usuario.setUsername(resultado.getString("username"));
                    usuario.setPassword(resultado.getString("password"));
                    usuario.setCorreo(resultado.getString("correo"));
                    usuario.setNombre(resultado.getString("nombre"));
                    usuario.setApellidoPaterno(resultado.getString("apellidoPaterno"));
                    usuario.setApellidoMaterno(resultado.getString("apellidoMaterno"));
                    usuario.setTelefono(resultado.getString("telefono"));
                    usuario.setFechaCreacion(resultado.getString("fechaCreacion"));
                    usuariosConsulta.add(usuario);
                }
                respuesta.setUsuarios(usuariosConsulta);
                conexionBD.close();
            } catch (SQLException e) {
                respuesta.setCodigoRespuesta(Constantes.ERROR_CONSULTA);
            }
        } else {
            respuesta.setCodigoRespuesta(Constantes.ERROR_CONEXION);
        }
        return respuesta;
    }
    
    public static UsuarioRespuesta obtenerInformacionAcademicos() {
        UsuarioRespuesta respuesta = new UsuarioRespuesta();
        Connection conexionBD = ConexionBD.abrirConexionBD();
        respuesta.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
        if (conexionBD != null) {
            try {
                String consulta = "SELECT Usuario.idUsuario, Usuario.idTipoUsuario, "
                        + "Academico.idAcademico, TipoUsuario.tipoUsuario, username, password, "
                        + "correo, nombre, apellidoPaterno, apellidoMaterno, "
                        + "telefono, fechaCreacion "
                        + "FROM Usuario "
                        + "INNER JOIN TipoUsuario "
                        + "ON Usuario.idTipoUsuario = TipoUsuario.idTipoUsuario "
                        + "INNER JOIN Academico "
                        + "ON Academico.idUsuario = Usuario.idUsuario "
                        + "WHERE Usuario.idTipoUsuario = 3";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                ResultSet resultado = prepararSentencia.executeQuery();
                ArrayList<Usuario> usuariosConsulta = new ArrayList();
                while (resultado.next())
                {
                    Usuario usuario = new Usuario();
                    usuario.setIdUsuario(resultado.getInt("idUsuario"));
                    usuario.setIdTipoUsuario(resultado.getInt("Usuario.idTipoUsuario"));
                    usuario.setIdAcademico(resultado.getInt("Academico.idAcademico"));
                    usuario.setTipoUsuario(resultado.getString("TipoUsuario.tipoUsuario"));
                    usuario.setUsername(resultado.getString("username"));
                    usuario.setPassword(resultado.getString("password"));
                    usuario.setCorreo(resultado.getString("correo"));
                    usuario.setNombre(resultado.getString("nombre"));
                    usuario.setApellidoPaterno(resultado.getString("apellidoPaterno"));
                    usuario.setApellidoMaterno(resultado.getString("apellidoMaterno"));
                    usuario.setTelefono(resultado.getString("telefono"));
                    usuario.setFechaCreacion(resultado.getString("fechaCreacion"));
                    usuariosConsulta.add(usuario);
                }
                respuesta.setUsuarios(usuariosConsulta);
                conexionBD.close();
            } catch (SQLException e) {
                respuesta.setCodigoRespuesta(Constantes.ERROR_CONSULTA);
            }
        } else {
            respuesta.setCodigoRespuesta(Constantes.ERROR_CONEXION);
        }
        return respuesta;
    }
    
    public static UsuarioRespuesta obtenerInformacionAcademicoEnCuerpoAcademico(
            int idCuerpoAcademico) {
        UsuarioRespuesta respuesta = new UsuarioRespuesta();
        Connection conexionBD = ConexionBD.abrirConexionBD();
        respuesta.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
        if (conexionBD != null) {
            try {
                String consulta = "SELECT Usuario.idUsuario, Usuario.idTipoUsuario, "
                        + "Academico.idAcademico, TipoUsuario.tipoUsuario, username, password, "
                        + "correo, Usuario.nombre, apellidoPaterno, apellidoMaterno, "
                        + "telefono, fechaCreacion "
                        + "FROM Usuario "
                        + "INNER JOIN TipoUsuario "
                        + "ON Usuario.idTipoUsuario = TipoUsuario.idTipoUsuario "
                        + "INNER JOIN Academico "
                        + "ON Academico.idUsuario = Usuario.idUsuario "
                        + "INNER JOIN CuerpoAcademico "
                        + "ON CuerpoAcademico.idResponsable = Academico.idAcademico "
                        + "WHERE Usuario.idTipoUsuario = 3 "
                        + "AND CuerpoAcademico.idCuerpoAcademico = ?";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                prepararSentencia.setInt(1, idCuerpoAcademico);
                ResultSet resultado = prepararSentencia.executeQuery();
                ArrayList<Usuario> usuariosConsulta = new ArrayList();
                if (resultado.next())
                {
                    Usuario usuario = new Usuario();
                    usuario.setIdUsuario(resultado.getInt("idUsuario"));
                    usuario.setIdTipoUsuario(resultado.getInt("Usuario.idTipoUsuario"));
                    usuario.setIdAcademico(resultado.getInt("Academico.idAcademico"));
                    usuario.setTipoUsuario(resultado.getString("TipoUsuario.tipoUsuario"));
                    usuario.setUsername(resultado.getString("username"));
                    usuario.setPassword(resultado.getString("password"));
                    usuario.setCorreo(resultado.getString("correo"));
                    usuario.setNombre(resultado.getString("nombre"));
                    usuario.setApellidoPaterno(resultado.getString("apellidoPaterno"));
                    usuario.setApellidoMaterno(resultado.getString("apellidoMaterno"));
                    usuario.setTelefono(resultado.getString("telefono"));
                    usuario.setFechaCreacion(resultado.getString("fechaCreacion"));
                    usuariosConsulta.add(usuario);
                }
                respuesta.setUsuarios(usuariosConsulta);
                conexionBD.close();
            } catch (SQLException e) {
                respuesta.setCodigoRespuesta(Constantes.ERROR_CONSULTA);
            }
        } else {
            respuesta.setCodigoRespuesta(Constantes.ERROR_CONEXION);
        }
        return respuesta;
    }
    
    public static UsuarioRespuesta obtenerInformacionAcademicosDisponibles() {
        UsuarioRespuesta respuesta = new UsuarioRespuesta();
        Connection conexionBD = ConexionBD.abrirConexionBD();
        respuesta.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
        if (conexionBD != null) {
            try {
                String consulta = "SELECT Usuario.idUsuario, Usuario.idTipoUsuario, "
                        + "Academico.idAcademico, TipoUsuario.tipoUsuario, username, password, "
                        + "correo, nombre, apellidoPaterno, apellidoMaterno, "
                        + "telefono, fechaCreacion "
                        + "FROM Usuario "
                        + "INNER JOIN TipoUsuario "
                        + "ON Usuario.idTipoUsuario = TipoUsuario.idTipoUsuario "
                        + "INNER JOIN Academico "
                        + "ON Academico.idUsuario = Usuario.idUsuario "
                        + "WHERE Usuario.idTipoUsuario = 3 "
                        + "AND Academico.idAcademico "
                        + "NOT IN (SELECT idResponsable FROM cuerpoacademico "
                        + "WHERE idResponsable IS NOT NULL)";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                ResultSet resultado = prepararSentencia.executeQuery();
                ArrayList<Usuario> usuariosConsulta = new ArrayList();
                while (resultado.next())
                {
                    Usuario usuario = new Usuario();
                    usuario.setIdUsuario(resultado.getInt("idUsuario"));
                    usuario.setIdTipoUsuario(resultado.getInt("Usuario.idTipoUsuario"));
                    usuario.setIdAcademico(resultado.getInt("Academico.idAcademico"));
                    usuario.setTipoUsuario(resultado.getString("TipoUsuario.tipoUsuario"));
                    usuario.setUsername(resultado.getString("username"));
                    usuario.setPassword(resultado.getString("password"));
                    usuario.setCorreo(resultado.getString("correo"));
                    usuario.setNombre(resultado.getString("nombre"));
                    usuario.setApellidoPaterno(resultado.getString("apellidoPaterno"));
                    usuario.setApellidoMaterno(resultado.getString("apellidoMaterno"));
                    usuario.setTelefono(resultado.getString("telefono"));
                    usuario.setFechaCreacion(resultado.getString("fechaCreacion"));
                    usuariosConsulta.add(usuario);
                }
                respuesta.setUsuarios(usuariosConsulta);
                conexionBD.close();
            } catch (SQLException e) {
                respuesta.setCodigoRespuesta(Constantes.ERROR_CONSULTA);
            }
        } else {
            respuesta.setCodigoRespuesta(Constantes.ERROR_CONEXION);
        }
        return respuesta;
    }
    
    public static UsuarioRespuesta obtenerInformacionEstudiantesEnCurso(int idCurso) {
        UsuarioRespuesta respuesta = new UsuarioRespuesta();
        Connection conexionBD = ConexionBD.abrirConexionBD();
        respuesta.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
        if (conexionBD != null) {
            try {
                String consulta = "SELECT Usuario.idUsuario, Usuario.idTipoUsuario, "
                        + "Estudiante.matricula, TipoUsuario.tipoUsuario, username, "
                        + "password, correo, nombre, apellidoPaterno, "
                        + "apellidoMaterno, telefono, fechaCreacion "
                        + "FROM Usuario "
                        + "INNER JOIN TipoUsuario "
                        + "ON Usuario.idTipoUsuario = TipoUsuario.idTipoUsuario "
                        + "INNER JOIN Estudiante "
                        + "ON Estudiante.idUsuario = Usuario.idUsuario "
                        + "INNER JOIN Estudiante_Curso "
                        + "ON Estudiante.idEstudiante = Estudiante_Curso.idEstudiante "
                        + "WHERE Usuario.idTipoUsuario = 2 AND Estudiante_Curso.idCurso = ?";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                prepararSentencia.setInt(1, idCurso);
                ResultSet resultado = prepararSentencia.executeQuery();
                ArrayList<Usuario> usuariosConsulta = new ArrayList();
                while (resultado.next())
                {
                    Usuario usuario = new Usuario();
                    usuario.setIdUsuario(resultado.getInt("Usuario.idUsuario"));
                    usuario.setIdTipoUsuario(resultado.getInt("Usuario.idTipoUsuario"));
                    usuario.setMatricula(resultado.getString("Estudiante.matricula"));
                    usuario.setTipoUsuario(resultado.getString("TipoUsuario.tipoUsuario"));
                    usuario.setUsername(resultado.getString("username"));
                    usuario.setPassword(resultado.getString("password"));
                    usuario.setCorreo(resultado.getString("correo"));
                    usuario.setNombre(resultado.getString("nombre"));
                    usuario.setApellidoPaterno(resultado.getString("apellidoPaterno"));
                    usuario.setApellidoMaterno(resultado.getString("apellidoMaterno"));
                    usuario.setTelefono(resultado.getString("telefono"));
                    usuario.setFechaCreacion(resultado.getString("fechaCreacion"));
                    usuariosConsulta.add(usuario);
                }
                respuesta.setUsuarios(usuariosConsulta);
                conexionBD.close();
            } catch (SQLException e) {
                respuesta.setCodigoRespuesta(Constantes.ERROR_CONSULTA);
            }
        } else {
            respuesta.setCodigoRespuesta(Constantes.ERROR_CONEXION);
        }
        return respuesta;
    }
    
    public static UsuarioRespuesta obtenerInformacionEstudiantesDisponibles() {
        UsuarioRespuesta respuesta = new UsuarioRespuesta();
        Connection conexionBD = ConexionBD.abrirConexionBD();
        respuesta.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
        if (conexionBD != null) {
            try {
                String consulta = "SELECT Usuario.idUsuario, Usuario.idTipoUsuario, "
                        + "Estudiante.matricula, Estudiante.idEstudiante, TipoUsuario.tipoUsuario, "
                        + "username, password, correo, nombre, "
                        + "apellidoPaterno, apellidoMaterno, telefono, fechaCreacion "
                        + "FROM Usuario "
                        + "INNER JOIN TipoUsuario "
                        + "ON TipoUsuario.idTipoUsuario = Usuario.idTipoUsuario "
                        + "INNER JOIN Estudiante "
                        + "ON Estudiante.idUsuario = Usuario.idUsuario "
                        + "WHERE Estudiante.idEstudiante "
                        + "NOT IN (SELECT Estudiante_Curso.idEstudiante FROM Estudiante_Curso) "
                        + "OR EXISTS (SELECT Estudiante_Curso.idEstudiante FROM Estudiante_Curso "
                        + "INNER JOIN Curso "
                        + "ON Curso.idCurso = Estudiante_Curso.idCurso "
                        + "INNER JOIN PeriodoEscolar "
                        + "ON Curso.idPeriodo = PeriodoEscolar.idPeriodoEscolar "
                        + "WHERE PeriodoEscolar.fechaFin < CURDATE())";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);                
                ResultSet resultado = prepararSentencia.executeQuery();
                ArrayList<Usuario> usuariosConsulta = new ArrayList();
                while (resultado.next())
                {
                    Usuario usuario = new Usuario();
                    usuario.setIdUsuario(resultado.getInt("Usuario.idUsuario"));
                    usuario.setIdTipoUsuario(resultado.getInt("Usuario.idTipoUsuario"));
                    usuario.setMatricula(resultado.getString("Estudiante.matricula"));
                    usuario.setIdEstudiante(resultado.getInt("Estudiante.idEstudiante"));
                    usuario.setTipoUsuario(resultado.getString("TipoUsuario.tipoUsuario"));
                    usuario.setUsername(resultado.getString("username"));
                    usuario.setPassword(resultado.getString("password"));
                    usuario.setCorreo(resultado.getString("correo"));
                    usuario.setNombre(resultado.getString("nombre"));
                    usuario.setApellidoPaterno(resultado.getString("apellidoPaterno"));
                    usuario.setApellidoMaterno(resultado.getString("apellidoMaterno"));
                    usuario.setTelefono(resultado.getString("telefono"));
                    usuario.setFechaCreacion(resultado.getString("fechaCreacion"));
                    usuariosConsulta.add(usuario);
                }
                respuesta.setUsuarios(usuariosConsulta);
                conexionBD.close();
            } catch (SQLException e) {
                respuesta.setCodigoRespuesta(Constantes.ERROR_CONSULTA);
            }
        } else {
            respuesta.setCodigoRespuesta(Constantes.ERROR_CONEXION);
        }
        return respuesta;
    }
    
    public static int verificarDisponibilidadUsername(String username) {
        int respuesta = 1;
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if (conexionBD != null) {
            try {
                String sentencia = "SELECT COUNT(*) AS coincidencias "
                        + "FROM Usuario WHERE username = ?";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
                prepararSentencia.setString(1, username);
                ResultSet resultado = prepararSentencia.executeQuery();
                if (resultado.next()) 
                    respuesta = resultado.getInt("coincidencias");
                conexionBD.close();
            } catch (SQLException e) {
                respuesta = Constantes.ERROR_CONSULTA;
            }
        } else {
            respuesta = Constantes.ERROR_CONEXION;
        }
        return respuesta;
    }
    
    public static int verificarDisponibilidadCorreo(String correo) {
        int respuesta = 1;
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if (conexionBD != null) {
            try {
                String sentencia = "SELECT COUNT(*) AS coincidencias "
                        + "FROM Usuario WHERE correo = ?";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
                prepararSentencia.setString(1, correo);
                ResultSet resultado = prepararSentencia.executeQuery();
                if (resultado.next()) 
                    respuesta = resultado.getInt("coincidencias");
                conexionBD.close();
            } catch (SQLException e) {
                respuesta = Constantes.ERROR_CONSULTA;
            }
        } else {
            respuesta = Constantes.ERROR_CONEXION;
        }
        return respuesta;
    }
    
    public static UsuarioRespuesta guardarUsuario(Usuario usuarioNuevo) {
        UsuarioRespuesta respuesta = new UsuarioRespuesta();
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if (conexionBD != null) {
            try {
                String sentencia = "INSERT INTO Usuario (idTipoUsuario, username, " + 
                        "password, correo, nombre, apellidoPaterno, apellidoMaterno, telefono, " + 
                        "fechaCreacion) " + 
                        "VALUES (?,?,?,?,?,?,?,?, CURRENT_TIMESTAMP)";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia, 
                                Statement.RETURN_GENERATED_KEYS);
                prepararSentencia.setInt(1, usuarioNuevo.getIdTipoUsuario());
                prepararSentencia.setString(2, usuarioNuevo.getUsername());
                prepararSentencia.setString(3, usuarioNuevo.getPassword());
                prepararSentencia.setString(4, usuarioNuevo.getCorreo());
                prepararSentencia.setString(5, usuarioNuevo.getNombre());                
                prepararSentencia.setString(6, usuarioNuevo.getApellidoPaterno());
                prepararSentencia.setString(7, usuarioNuevo.getApellidoMaterno());
                prepararSentencia.setString(8, usuarioNuevo.getTelefono());
                int filasAfectadas = prepararSentencia.executeUpdate();
                if (filasAfectadas == 1) {
                    respuesta.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
                    ResultSet resultadoLlave = prepararSentencia.getGeneratedKeys();
                    if (resultadoLlave.next()) {
                        respuesta.setIdUsuarioRegistrado(resultadoLlave.getInt(1));
                    }
                }
                else
                    respuesta.setCodigoRespuesta(Constantes.ERROR_CONSULTA);
                conexionBD.close();
            } catch (SQLException e) {
                respuesta.setCodigoRespuesta(Constantes.ERROR_CONSULTA);
            }
        } else {
            respuesta.setCodigoRespuesta(Constantes.ERROR_CONEXION);
        }
        return respuesta;
    }
    
    public static int modificarUsuario(Usuario usuarioEdicion) {
        int respuesta;
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if (conexionBD != null) {
            try {
                String sentencia = "UPDATE Usuario SET username = ?, " +
                        "password = ?, correo = ?, nombre = ?, apellidoPaterno = ?, " +
                        "apellidoMaterno = ?, telefono = ? " + 
                        "WHERE idUsuario = ?";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
                prepararSentencia.setString(1, usuarioEdicion.getUsername());
                prepararSentencia.setString(2, usuarioEdicion.getPassword());
                prepararSentencia.setString(3, usuarioEdicion.getCorreo());
                prepararSentencia.setString(4, usuarioEdicion.getNombre());
                prepararSentencia.setString(5, usuarioEdicion.getApellidoPaterno());
                prepararSentencia.setString(6, usuarioEdicion.getApellidoMaterno());
                prepararSentencia.setString(7, usuarioEdicion.getTelefono());
                prepararSentencia.setInt(8, usuarioEdicion.getIdUsuario());
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
    
    public static int eliminarUsuario(int idUsuario) {
        int respuesta;
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if (conexionBD != null) {
            try {
                String sentencia = "DELETE FROM Usuario WHERE idUsuario = ?";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
                prepararSentencia.setInt(1, idUsuario);
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
    
    public static UsuarioRespuesta consultarEstudiantesDisponibles(){
        UsuarioRespuesta respuesta = new UsuarioRespuesta();
        Connection conexionBD = ConexionBD.abrirConexionBD();
        respuesta.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
        if (conexionBD != null) {
            try {
                String consulta = "SELECT usuario.idUsuario, usuario.nombre, "
                        + "usuario.apellidoPaterno, usuario.apellidoMaterno , "
                        + "estudiante.matricula, estudiante.idEstudiante "
                        + "FROM usuario "
                        + "INNER JOIN estudiante "
                        + "ON estudiante.idUsuario = usuario.idUsuario "
                        + "WHERE estudiante.idAnteproyecto IS NULL";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                ResultSet resultado = prepararSentencia.executeQuery();
                ArrayList<Usuario> usuariosConsulta = new ArrayList();
                while (resultado.next())
                {
                    Usuario usuario = new Usuario();
                    usuario.setIdUsuario(resultado.getInt("idUsuario"));
                    usuario.setNombre(resultado.getString("nombre"));
                    usuario.setMatricula(resultado.getString("matricula"));
                    usuario.setIdEstudiante(resultado.getInt("idEstudiante"));
                    usuario.setApellidoPaterno(resultado.getString("apellidoPaterno"));
                    usuario.setApellidoMaterno(resultado.getString("apellidoMaterno"));
                    usuariosConsulta.add(usuario);
                }
                respuesta.setUsuarios(usuariosConsulta);
                conexionBD.close();
            } catch (SQLException e) {
                respuesta.setCodigoRespuesta(Constantes.ERROR_CONSULTA);
            }
        } else {
            respuesta.setCodigoRespuesta(Constantes.ERROR_CONEXION);
        }
        return respuesta;
    }
    
    public static UsuarioRespuesta consultarEstudiantesEnAnteproyecto(int idAnteproyecto){
        UsuarioRespuesta respuesta = new UsuarioRespuesta();
        Connection conexionBD = ConexionBD.abrirConexionBD();
        respuesta.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
        if (conexionBD != null) {
            try {
                String consulta = "SELECT usuario.idUsuario, usuario.nombre, "
                        + "usuario.apellidoPaterno, usuario.apellidoMaterno, "
                        + "estudiante.matricula, estudiante.idEstudiante "
                        + "FROM usuario "
                        + "INNER JOIN estudiante ON estudiante.idUsuario = "
                        + "usuario.idUsuario WHERE estudiante.idAnteproyecto = ?";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                prepararSentencia.setInt(1, idAnteproyecto);
                ResultSet resultado = prepararSentencia.executeQuery();
                ArrayList<Usuario> usuariosConsulta = new ArrayList();
                while (resultado.next())
                {
                    Usuario usuario = new Usuario();
                    usuario.setIdUsuario(resultado.getInt("idUsuario"));
                    usuario.setNombre(resultado.getString("nombre"));
                    usuario.setMatricula(resultado.getString("matricula"));
                    usuario.setIdEstudiante(resultado.getInt("idEstudiante"));
                    usuario.setApellidoPaterno(resultado.getString("apellidoPaterno"));
                    usuario.setApellidoMaterno(resultado.getString("apellidoMaterno"));
                    usuariosConsulta.add(usuario);
                }
                respuesta.setUsuarios(usuariosConsulta);
                conexionBD.close();
            } catch (SQLException e) {
                respuesta.setCodigoRespuesta(Constantes.ERROR_CONSULTA);
            }
        } else {
            respuesta.setCodigoRespuesta(Constantes.ERROR_CONEXION);
        }
        return respuesta;
    }
    
    public static UsuarioRespuesta consultarCodirectoresEnAnteproyecto(int idAnteproyecto){
        UsuarioRespuesta respuesta = new UsuarioRespuesta();
        Connection conexionBD = ConexionBD.abrirConexionBD();
        respuesta.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
        if (conexionBD != null) {
            try {
                String consulta = "SELECT usuario.idUsuario, usuario.nombre, "
                        + "usuario.apellidoPaterno, usuario.apellidoMaterno "
                        + "FROM Usuario "
                        + "INNER JOIN Academico "
                        + "ON Usuario.idUsuario = Academico.idUsuario "
                        + "INNER JOIN anteproyecto_codirector "
                        + "ON anteproyecto_codirector.idCodirector = Academico.idAcademico "
                        + "WHERE anteproyecto_codirector.idAnteproyecto = ?";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                prepararSentencia.setInt(1, idAnteproyecto);
                ResultSet resultado = prepararSentencia.executeQuery();
                ArrayList<Usuario> usuariosConsulta = new ArrayList();
                while (resultado.next())
                {
                    Usuario usuario = new Usuario();
                    usuario.setIdUsuario(resultado.getInt("idUsuario"));
                    usuario.setNombre(resultado.getString("nombre"));
                    usuario.setApellidoPaterno(resultado.getString("apellidoPaterno"));
                    usuario.setApellidoMaterno(resultado.getString("apellidoMaterno"));
                    usuariosConsulta.add(usuario);
                }
                respuesta.setUsuarios(usuariosConsulta);
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