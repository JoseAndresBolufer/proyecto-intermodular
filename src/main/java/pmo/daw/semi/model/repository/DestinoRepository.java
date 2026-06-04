package pmo.daw.semi.model.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import pmo.daw.semi.excepciones.RepositoryException;
import pmo.daw.semi.model.entities.Destino;
import pmo.daw.semi.model.repository.base.BaseRepository;

public class DestinoRepository extends BaseRepository<Destino, Integer> {

    private static final DestinoRepository INSTANCE = new DestinoRepository();

    private DestinoRepository() {}

    public static DestinoRepository getInstance() {
        return INSTANCE;
    }

    @Override
    public List<Destino> findAll(Connection conexion) throws RepositoryException {
        List<Destino> destinos = new ArrayList<>();

        String sql = "SELECT * FROM destino";

        try (PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Destino destino = Destino.mapResultSet(rs);
                destinos.add(destino);
            }

        } catch (SQLException sqlException) {
            throw new RepositoryException("Fallo buscando todos los destinos", sqlException);
        }

        return destinos;
    }

    @Override
    public Destino findById(Connection conexion, Integer id) throws RepositoryException {
        Destino destino = null;

        String sql = "SELECT * FROM destino WHERE id = ?";

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    destino = Destino.mapResultSet(rs);
                }
            }

        } catch (SQLException sqlException) {
            throw new RepositoryException("Fallo buscando el destino con id = " + id, sqlException);
        }

        return destino;
    }

    @Override
    public Destino save(Connection conexion, Destino destino) throws RepositoryException {
        String sql = "INSERT INTO destino (ciudad, pais, precio, requiere_pasaporte) VALUES (?, ?, ?, ?)";

        try (PreparedStatement ps = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, destino.getCiudad());
            ps.setString(2, destino.getPais());
            ps.setBigDecimal(3, destino.getPrecio());
            ps.setObject(4, destino.getRequierePasaporte(), java.sql.Types.BOOLEAN);

            int filasAfectadas = ps.executeUpdate();

            if (filasAfectadas != 1) {
                throw new RepositoryException("Se esperaba insertar 1 destino, pero se insertaron: " + filasAfectadas);
            }

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    destino.setId(rs.getInt(1));
                }
            }

        } catch (SQLException sqlException) {
            throw new RepositoryException("Fallo guardando el destino", sqlException);
        }

        return destino;
    }

    @Override
    public Destino update(Connection conexion, Integer id, Destino destino) throws RepositoryException {
        String sql = "UPDATE destino SET ciudad = ?, pais = ?, precio = ?, requiere_pasaporte = ? WHERE id = ?";

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, destino.getCiudad());
            ps.setString(2, destino.getPais());
            ps.setBigDecimal(3, destino.getPrecio());
            ps.setObject(4, destino.getRequierePasaporte(), java.sql.Types.BOOLEAN);
            ps.setInt(5, id);

            int filasAfectadas = ps.executeUpdate();

            if (filasAfectadas > 1) {
                throw new RepositoryException("Se esperaba actualizar 1 destino, pero se actualizaron: " + filasAfectadas);
            }

            destino.setId(id);

        } catch (SQLException sqlException) {
            throw new RepositoryException("Fallo modificando el destino con id = " + id, sqlException);
        }

        return destino;
    }

    @Override
    public void deleteById(Connection conexion, Integer id) throws RepositoryException {
        String sql = "DELETE FROM destino WHERE id = ?";

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, id);

            int filasAfectadas = ps.executeUpdate();

            if (filasAfectadas > 1) {
                throw new RepositoryException("Se esperaba borrar 1 destino, pero se borraron: " + filasAfectadas);
            }

        } catch (SQLException sqlException) {
            throw new RepositoryException("Fallo borrando el destino con id = " + id, sqlException);
        }
    }
}