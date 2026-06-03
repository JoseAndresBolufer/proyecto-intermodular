package pmo.daw.semi.model.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import pmo.daw.semi.excepciones.RepositoryException;
import pmo.daw.semi.model.entities.Guia;
import pmo.daw.semi.model.repository.base.BaseRepository;

public class GuiaRepository extends BaseRepository<Guia, Integer> {

    private static final GuiaRepository INSTANCE = new GuiaRepository();

    private GuiaRepository() {}

    public static GuiaRepository getInstance() {
        return INSTANCE;
    }

    @Override
    public List<Guia> findAll(Connection conexion) throws RepositoryException {
        List<Guia> guias = new ArrayList<>();

        String sql = "SELECT * FROM guia";

        try (PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Guia guia = Guia.mapResultSet(rs);
                guias.add(guia);
            }

        } catch (SQLException sqlException) {
            throw new RepositoryException("Fallo buscando todos los guías", sqlException);
        }

        return guias;
    }

    @Override
    public Guia findById(Connection conexion, Integer id) throws RepositoryException {
        Guia guia = null;

        String sql = "SELECT * FROM guia WHERE id = ?";

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    guia = Guia.mapResultSet(rs);
                }
            }

        } catch (SQLException sqlException) {
            throw new RepositoryException("Fallo buscando el guía con id = " + id, sqlException);
        }

        return guia;
    }

    @Override
    public Guia save(Connection conexion, Guia guia) throws RepositoryException {
        String sql = "INSERT INTO guia (nombre, apellidos, especialidad, id_destino) VALUES (?, ?, ?, ?)";

        try (PreparedStatement ps = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, guia.getNombre());
            ps.setString(2, guia.getApellidos());
            ps.setString(3, guia.getEspecialidad());
            ps.setObject(4, guia.getIdDestino(), java.sql.Types.INTEGER);

            int filasAfectadas = ps.executeUpdate();

            if (filasAfectadas != 1) {
                throw new RepositoryException("Se esperaba insertar 1 guía, pero se insertaron: " + filasAfectadas);
            }

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    guia.setId(rs.getInt(1));
                }
            }

        } catch (SQLException sqlException) {
            throw new RepositoryException("Fallo guardando el guía", sqlException);
        }

        return guia;
    }

    @Override
    public Guia update(Connection conexion, Integer id, Guia guia) throws RepositoryException {
        String sql = "UPDATE guia SET nombre = ?, apellidos = ?, especialidad = ?, id_destino = ? WHERE id = ?";

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, guia.getNombre());
            ps.setString(2, guia.getApellidos());
            ps.setString(3, guia.getEspecialidad());
            ps.setObject(4, guia.getIdDestino(), java.sql.Types.INTEGER);
            ps.setInt(5, id);

            int filasAfectadas = ps.executeUpdate();

            if (filasAfectadas > 1) {
                throw new RepositoryException("Se esperaba actualizar 1 guía, pero se actualizaron: " + filasAfectadas);
            }

            guia.setId(id);

        } catch (SQLException sqlException) {
            throw new RepositoryException("Fallo modificando el guía con id = " + id, sqlException);
        }

        return guia;
    }

    @Override
    public void deleteById(Connection conexion, Integer id) throws RepositoryException {
        String sql = "DELETE FROM guia WHERE id = ?";

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, id);

            int filasAfectadas = ps.executeUpdate();

            if (filasAfectadas > 1) {
                throw new RepositoryException("Se esperaba borrar 1 guía, pero se borraron: " + filasAfectadas);
            }

        } catch (SQLException sqlException) {
            throw new RepositoryException("Fallo borrando el guía con id = " + id, sqlException);
        }
    }

    public List<Guia> findByIdDestino(Connection conexion, Integer idDestino) throws RepositoryException {
        List<Guia> guias = new ArrayList<>();

        String sql = "SELECT * FROM guia WHERE id_destino = ?";

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, idDestino);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Guia guia = Guia.mapResultSet(rs);
                    guias.add(guia);
                }
            }

        } catch (SQLException sqlException) {
            throw new RepositoryException("Fallo buscando guías por idDestino = " + idDestino, sqlException);
        }

        return guias;
    }

    public List<Guia> findIfIdDestinoIsNull(Connection conexion) throws RepositoryException {
        List<Guia> guias = new ArrayList<>();

        String sql = "SELECT * FROM guia WHERE id_destino IS NULL";

        try (PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Guia guia = Guia.mapResultSet(rs);
                guias.add(guia);
            }

        } catch (SQLException sqlException) {
            throw new RepositoryException("Fallo buscando guías sin destino", sqlException);
        }

        return guias;
    }

    public void addDestino(Connection conexion, Integer id, Integer idDestino) throws RepositoryException {
        String sql = "UPDATE guia SET id_destino = ? WHERE id = ?";

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, idDestino);
            ps.setInt(2, id);

            int filasAfectadas = ps.executeUpdate();

            if (filasAfectadas > 1) {
                throw new RepositoryException("Se esperaba actualizar 1 guía, pero se actualizaron: " + filasAfectadas);
            }

        } catch (SQLException sqlException) {
            throw new RepositoryException("Fallo añadiendo destino al guía con id = " + id, sqlException);
        }
    }

    public void removeDestino(Connection conexion, Integer id) throws RepositoryException {
        String sql = "UPDATE guia SET id_destino = NULL WHERE id = ?";

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, id);

            int filasAfectadas = ps.executeUpdate();

            if (filasAfectadas > 1) {
                throw new RepositoryException("Se esperaba actualizar 1 guía, pero se actualizaron: " + filasAfectadas);
            }

        } catch (SQLException sqlException) {
            throw new RepositoryException("Fallo quitando destino al guía con id = " + id, sqlException);
        }
    }
}