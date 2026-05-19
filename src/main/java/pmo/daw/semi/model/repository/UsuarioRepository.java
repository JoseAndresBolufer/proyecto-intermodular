package pmo.daw.semi.model.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import pmo.daw.semi.excepciones.RepositoryException;
import pmo.daw.semi.model.entities.Pasaporte;
import pmo.daw.semi.model.entities.Usuario;
import pmo.daw.semi.model.repository.base.BaseRepository;

/**
 * Repositorio para la entidad Usuario.
 * Gestiona operaciones CRUD directamente con la base de datos.
 * Singleton estilo JNA (Eager) para reutilizar la misma instancia.
 */
public class UsuarioRepository extends BaseRepository<Usuario, Integer> {

    // =============================================
    // Singleton estilo JNA (Eager)
    // =============================================
    private static final UsuarioRepository INSTANCE = new UsuarioRepository();
    private UsuarioRepository() {}
    public static UsuarioRepository getInstance() { return INSTANCE; }

    // =============================================
    // CRUD básicos
    // =============================================

    /**
     * Recupera todos los usuarios de la base de datos.
     * @param conexion Conexion a la base de datos
     * @return Lista de usuarios
     * @throws RepositoryException si ocurre un error de SQL
     */
    @Override
    public List<Usuario> findAll(Connection conexion) throws RepositoryException {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT u.*, p.id AS p_id, " +
        			"p.numero AS p_numero, " +
        			"p.pais_expedicion AS p_pais_expedicion, " +
        			"p.fecha_caducidad AS p_fecha_caducidad, " +
        			"p.id_usuario AS p_id_usuario, p.* " +
                     "FROM usuario u " +
                     "LEFT JOIN pasaporte p ON u.id = p.id_usuario ";

        try (PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

        	while (rs.next()) {
                // Mapear cada fila a un objeto Usuario
                Usuario usuario = Usuario.mapResultSet(rs);
                // Si tiene pasaporte
                Integer pasaporteId = rs.getObject("p_id", Integer.class);
                if (pasaporteId != null) {
					Pasaporte pasaporte = Pasaporte.mapResultSet(rs, "p_");
                    usuario.setPasaporte(pasaporte);
                }
                usuarios.add(usuario);
            }
        } catch (SQLException sqlException) {
            throw new RepositoryException("Fallo buscando todos los usuarios", sqlException);
        }
        return usuarios;
    }

    /**
     * Recupera un usuario por su ID
     * @param conexion Conexion a la base de datos
     * @param id ID del usuario
     * @return Usuario encontrado o null si no existe
     * @throws RepositoryException si ocurre un error de SQL
     */
    @Override
    public Usuario findById(Connection conexion, Integer id) throws RepositoryException {
        Usuario usuario = null;
        String sql = "SELECT * FROM usuario WHERE id = ?";

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    usuario = Usuario.mapResultSet(rs);
                }
            }
        } catch (SQLException sqlException) {
            throw new RepositoryException("Fallo buscando el usuario con id = " + id, sqlException);
        }
        return usuario;
    }

    /**
     * Inserta un nuevo usuario en la base de datos
     * @param conexion Conexion a la base de datos
     * @param usuario Usuario a insertar
     * @return Usuario con ID generado automáticamente
     * @throws RepositoryException si ocurre un error de SQL
     */
    @Override
    public Usuario save(Connection conexion, Usuario usuario) throws RepositoryException {
        String sql = "INSERT INTO usuario (nombre, apellidos, email, telefono, fecha_nacimiento) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            // Establecemos valores, manejando posibles nulls
            ps.setString(1, usuario.getNombre());
            ps.setString(2, usuario.getApellidos());
            ps.setString(3, usuario.getEmail());
            ps.setString(4, usuario.getTelefono());
            ps.setDate(5, usuario.getFechaNacimientoDate());

            int filasAfectadas = ps.executeUpdate();
            if (filasAfectadas != 1) throw new RepositoryException("Se esperaba insertar 1 fila, pero se insertaron: " + filasAfectadas);

            // Obtenemos el ID generado automáticamente
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    usuario.setId(rs.getInt(1));
                }
            }
        } catch (SQLException sqlException) {
            throw new RepositoryException("Fallo guardando el usuario", sqlException);
        }
        return usuario;
    }

    /**
     * Actualiza un usuario existente
     * @param conexion Conexion a la base de datos
     * @param id ID del usuario a actualizar
     * @param usuario Usuario con los nuevos valores
     * @return Usuario actualizado
     * @throws RepositoryException si ocurre un error de SQL
     */
    @Override
    public Usuario update(Connection conexion, Integer id, Usuario usuario) throws RepositoryException {
        String sql = "UPDATE usuario SET nombre = ?, apellidos = ?, email = ?, telefono = ?, fecha_nacimiento = ? WHERE id = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, usuario.getNombre());
            ps.setString(2, usuario.getApellidos());
            ps.setString(3, usuario.getEmail());
            ps.setString(4, usuario.getTelefono());
            ps.setDate(5, usuario.getFechaNacimientoDate());
            ps.setInt(6, id);

            int filasAfectadas = ps.executeUpdate();
            if (filasAfectadas > 1) throw new RepositoryException("Se esperaba actualizar 1 fila, pero se actualizaron: " + filasAfectadas);
        } catch (SQLException sqlException) {
            throw new RepositoryException("Fallo modificando el usuario con id = " + id, sqlException);
        }
        return usuario;
    }

    /**
     * Elimina un usuario por su ID
     * @param conexion Conexion a la base de datos
     * @param id ID del usuario a eliminar
     * @throws RepositoryException si ocurre un error de SQL
     */
    @Override
    public void deleteById(Connection conexion, Integer id) throws RepositoryException {
        String sqlUsuario = "DELETE FROM usuario WHERE id = ?";
//        String sqlReservas = "DELETE FROM reserva WHERE id_usuario = ?";
        try (PreparedStatement psUsuario = conexion.prepareStatement(sqlUsuario);
        		/*PreparedStatement psReservas = conexion.prepareStatement(sqlReservas)*/) {
            //psReservas.setInt(1, id);
            //psReservas.executeUpdate();

        	psUsuario.setInt(1, id);
            int filasAfectadas = psUsuario.executeUpdate();
            if (filasAfectadas > 1) throw new RepositoryException("Se esperaba borrar 1 fila, pero se borraron: " + filasAfectadas);
        } catch (SQLException sqlException) {
            throw new RepositoryException("Fallo borrando el usuario con id = " + id, sqlException);
        }
    }

	// =============================================
	// Queries extra
	// =============================================
	public List<Usuario> findByIdDestino(Connection conexion, Integer idDestino) throws RepositoryException {
		// Necesita que funcione reservas
		List<Usuario> usuarios = new ArrayList<>();
		String sql = "SELECT u.* FROM usuario u " + "JOIN reserva r ON u.id = r.id_usuario " + "WHERE r.id_destino = ?";

		try (PreparedStatement ps = conexion.prepareStatement(sql)) {
			ps.setInt(1, idDestino);

			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					Usuario usuario = Usuario.mapResultSet(rs);
					usuarios.add(usuario);
				}
			}
		} catch (SQLException sqlException) {
			throw new RepositoryException("Fallo buscando usuarios por idDestino = " + idDestino, sqlException);
		}

		return usuarios;
	}
}