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
import pmo.daw.semi.model.repository.base.BaseRepository;

/**
 * Repositorio de la entidad Pasaporte.
 * Implementa operaciones CRUD directamente sobre la base de datos.
 * 
 * Singleton estilo JNA (Eager Initialization) para acceso global.
 */
public class PasaporteRepository extends BaseRepository<Pasaporte, Integer> {

	// =============================================
    // Singleton estilo JNA (Eager)
    // =============================================
    private static final PasaporteRepository INSTANCE = new PasaporteRepository();
    private PasaporteRepository() {}
    public static PasaporteRepository getInstance() { return INSTANCE; }

    // =============================================
    // CRUD Básico
    // =============================================

    /**
     * Devuelve todos los pasaportes.
     * @param conexion Conexión a la base de datos
     * @return Lista de pasaportes
     * @throws RepositoryException Si ocurre algún error SQL
     */
    @Override
    public List<Pasaporte> findAll(Connection conexion) throws RepositoryException {
        List<Pasaporte> pasaportes = new ArrayList<>();
        String sql = "SELECT * FROM pasaporte";

        try (PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                // Mapear cada fila a un objeto Usuario
            	Pasaporte pasaporte  = Pasaporte.mapResultSet(rs);
                pasaportes.add(pasaporte);
            }

        } catch (SQLException sqlException) {
            throw new RepositoryException("Fallo buscando todos los pasaportes", sqlException);
        }

        return pasaportes;
    }

    /**
     * Busca un pasaporte por ID.
     * @param conexion Conexión a la base de datos
     * @param id ID del pasaporte
     * @return Pasaporte encontrado o null si no existe
     * @throws RepositoryException Si ocurre algún error SQL
     */
    @Override
    public Pasaporte findById(Connection conexion, Integer id) throws RepositoryException {
        Pasaporte pasaporte = null;
        String sql = "SELECT * FROM pasaporte WHERE id = ?";

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                	pasaporte = Pasaporte.mapResultSet(rs);
                }
            }
        } catch (SQLException sqlException) {
            throw new RepositoryException("Fallo buscando el pasaporte con id = " + id, sqlException);
        }
        return pasaporte;
    }

    /**
     * Inserta un nuevo pasaporte en la base de datos.
     * @param conexion Conexión a la base de datos
     * @param pasaporte Objeto pasaporte a guardar
     * @return Pasaporte guardado con ID asignado
     * @throws RepositoryException Si ocurre algún error SQL o no se inserta exactamente 1 fila
     */
    @Override
    public Pasaporte save(Connection conexion, Pasaporte pasaporte) throws RepositoryException {
        String sql = "INSERT INTO pasaporte (numero, pais_expedicion, fecha_caducidad, id_usuario) VALUES (?, ?, ?, ?)";

        try (PreparedStatement ps = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            // Valores, usando métodos "Safe" que evitan nulls no permitidos
            ps.setString(1, pasaporte.getNumeroSafe());
            ps.setString(2, pasaporte.getPaisExpedicionSafe());
            ps.setDate(3, pasaporte.getFechaCaducidadDate());
            ps.setObject(4, pasaporte.getIdUsuario(), java.sql.Types.INTEGER); // soporta null

            int filasAfectadas = ps.executeUpdate();
            if (filasAfectadas != 1)
                throw new RepositoryException("Se esperaba insertar 1 fila, pero se insertaron: " + filasAfectadas);

            // Obtenemos el ID generado automáticamente por la BD
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    pasaporte.setId(rs.getInt(1));
                }
            }
        } catch (SQLException sqlException) {
            throw new RepositoryException("Fallo guardando el pasaporte", sqlException);
        }
        return pasaporte;
    }

    /**
     * Actualiza un pasaporte existente.
     * @param conexion Conexión a la base de datos
     * @param id ID del pasaporte a actualizar
     * @param pasaporte Datos actualizados
     * @return Pasaporte actualizado
     * @throws RepositoryException Si ocurre algún error SQL o se actualizan más de 1 fila
     */
    @Override
    public Pasaporte update(Connection conexion, Integer id, Pasaporte pasaporte) throws RepositoryException {
        String sql = "UPDATE pasaporte SET numero = ?, pais_expedicion = ?, fecha_caducidad = ? WHERE id = ?";

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, pasaporte.getNumeroSafe());
            ps.setString(2, pasaporte.getPaisExpedicionSafe());
            ps.setDate(3, pasaporte.getFechaCaducidadDate());
            ps.setInt(4, id);

            int filasAfectadas = ps.executeUpdate();
            if (filasAfectadas > 1) throw new RepositoryException("Se esperaba actualizar 1 fila, pero se actualizaron: " + filasAfectadas);
        } catch (SQLException sqlException) {
            throw new RepositoryException("Fallo modificando el pasaporte con id = " + id, sqlException);
        }
        return pasaporte;
    }

    /**
     * Elimina un pasaporte por su ID.
     * @param conexion Conexión a la base de datos
     * @param id ID del pasaporte a eliminar
     * @throws RepositoryException Si ocurre algún error SQL o se eliminan más de 1 fila
     */
    @Override
    public void deleteById(Connection conexion, Integer id) throws RepositoryException {
        String sql = "DELETE FROM pasaporte WHERE id = ?";

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, id);

            int filasAfectadas = ps.executeUpdate();
            if (filasAfectadas > 1) throw new RepositoryException("Se esperaba borrar 1 fila, pero se borraron: " + filasAfectadas);
        } catch (SQLException sqlException) {
            throw new RepositoryException("Fallo borrando el pasaporte con id = " + id, sqlException);
        }
    }

    // =============================================
    // Queries extra
    // =============================================
    public Pasaporte findByIdUsuario(Connection conexion, Integer idUsuario) throws RepositoryException {
        Pasaporte pasaporte = null;
        String sql = "SELECT * FROM pasaporte WHERE id_usuario = ?";

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, idUsuario);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    pasaporte = Pasaporte.mapResultSet(rs);
                }
            }
        } catch (SQLException sqlException) {
            throw new RepositoryException("Fallo buscando el pasaporte con id_usuario = " + idUsuario, sqlException);
        }
        return pasaporte; // null si no existe ningún pasaporte para ese usuario
    }
}