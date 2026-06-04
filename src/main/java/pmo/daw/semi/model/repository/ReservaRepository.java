package pmo.daw.semi.model.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import pmo.daw.semi.excepciones.RepositoryException;
import pmo.daw.semi.model.entities.Reserva;
import pmo.daw.semi.model.repository.base.BaseRepository;

public class ReservaRepository extends BaseRepository<Reserva, Integer> {

    private static final ReservaRepository INSTANCE = new ReservaRepository();

    private ReservaRepository() {}

    public static ReservaRepository getInstance() {
        return INSTANCE;
    }

    @Override
    public List<Reserva> findAll(Connection conexion) throws RepositoryException {
        List<Reserva> reservas = new ArrayList<>();

        String sql = "SELECT * FROM reserva";

        try (PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Reserva reserva = Reserva.mapResultSet(rs);
                reservas.add(reserva);
            }

        } catch (SQLException sqlException) {
            throw new RepositoryException("Fallo buscando todas las reservas", sqlException);
        }

        return reservas;
    }

    @Override
    public Reserva findById(Connection conexion, Integer id) throws RepositoryException {
        Reserva reserva = null;

        String sql = "SELECT * FROM reserva WHERE id = ?";

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    reserva = Reserva.mapResultSet(rs);
                }
            }

        } catch (SQLException sqlException) {
            throw new RepositoryException("Fallo buscando la reserva con id = " + id, sqlException);
        }

        return reserva;
    }

    @Override
    public Reserva save(Connection conexion, Reserva reserva) throws RepositoryException {
        String sql = "INSERT INTO reserva (id_usuario, id_destino) VALUES (?, ?)";

        try (PreparedStatement ps = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setObject(1, reserva.getIdUsuario(), java.sql.Types.INTEGER);
            ps.setObject(2, reserva.getIdDestino(), java.sql.Types.INTEGER);

            int filasAfectadas = ps.executeUpdate();

            if (filasAfectadas != 1) {
                throw new RepositoryException("Se esperaba insertar 1 reserva, pero se insertaron: " + filasAfectadas);
            }

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    reserva.setId(rs.getInt(1));
                }
            }

        } catch (SQLException sqlException) {
            throw new RepositoryException("Fallo guardando la reserva", sqlException);
        }

        return reserva;
    }

    @Override
    public Reserva update(Connection conexion, Integer id, Reserva reserva) throws RepositoryException {
        String sql = "UPDATE reserva SET id_usuario = ?, id_destino = ? WHERE id = ?";

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setObject(1, reserva.getIdUsuario(), java.sql.Types.INTEGER);
            ps.setObject(2, reserva.getIdDestino(), java.sql.Types.INTEGER);
            ps.setInt(3, id);

            int filasAfectadas = ps.executeUpdate();

            if (filasAfectadas > 1) {
                throw new RepositoryException("Se esperaba actualizar 1 reserva, pero se actualizaron: " + filasAfectadas);
            }

            reserva.setId(id);

        } catch (SQLException sqlException) {
            throw new RepositoryException("Fallo modificando la reserva con id = " + id, sqlException);
        }

        return reserva;
    }

    @Override
    public void deleteById(Connection conexion, Integer id) throws RepositoryException {
        String sql = "DELETE FROM reserva WHERE id = ?";

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, id);

            int filasAfectadas = ps.executeUpdate();

            if (filasAfectadas > 1) {
                throw new RepositoryException("Se esperaba borrar 1 reserva, pero se borraron: " + filasAfectadas);
            }

        } catch (SQLException sqlException) {
            throw new RepositoryException("Fallo borrando la reserva con id = " + id, sqlException);
        }
    }

    public List<Reserva> findByIdUsuario(Connection conexion, Integer idUsuario) throws RepositoryException {
        List<Reserva> reservas = new ArrayList<>();

        String sql = "SELECT * FROM reserva WHERE id_usuario = ?";

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, idUsuario);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Reserva reserva = Reserva.mapResultSet(rs);
                    reservas.add(reserva);
                }
            }

        } catch (SQLException sqlException) {
            throw new RepositoryException("Fallo buscando reservas por idUsuario = " + idUsuario, sqlException);
        }

        return reservas;
    }

    public List<Reserva> findByIdDestino(Connection conexion, Integer idDestino) throws RepositoryException {
        List<Reserva> reservas = new ArrayList<>();

        String sql = "SELECT * FROM reserva WHERE id_destino = ?";

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, idDestino);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Reserva reserva = Reserva.mapResultSet(rs);
                    reservas.add(reserva);
                }
            }

        } catch (SQLException sqlException) {
            throw new RepositoryException("Fallo buscando reservas por idDestino = " + idDestino, sqlException);
        }

        return reservas;
    }

    public Reserva findByUsuarioAndDestino(Connection conexion, Integer idUsuario, Integer idDestino) throws RepositoryException {
        Reserva reserva = null;

        String sql = "SELECT * FROM reserva WHERE id_usuario = ? AND id_destino = ?";

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, idUsuario);
            ps.setInt(2, idDestino);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    reserva = Reserva.mapResultSet(rs);
                }
            }

        } catch (SQLException sqlException) {
            throw new RepositoryException("Fallo buscando reserva por usuario y destino", sqlException);
        }

        return reserva;
    }

    public void deleteByUsuarioAndDestino(Connection conexion, Integer idUsuario, Integer idDestino) throws RepositoryException {
        String sql = "DELETE FROM reserva WHERE id_usuario = ? AND id_destino = ?";

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, idUsuario);
            ps.setInt(2, idDestino);

            ps.executeUpdate();

        } catch (SQLException sqlException) {
            throw new RepositoryException("Fallo borrando reserva por usuario y destino", sqlException);
        }
    }
}