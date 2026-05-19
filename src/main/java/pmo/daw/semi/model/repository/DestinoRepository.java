package pmo.daw.semi.model.repository;

import java.sql.Connection;
import java.util.List;

import pmo.daw.semi.excepciones.RepositoryException;
import pmo.daw.semi.model.entities.Destino;
import pmo.daw.semi.model.repository.base.BaseRepository;

public class DestinoRepository extends BaseRepository<Destino, Integer> {
	// Eager Singleton (JNA-style)
	private static final DestinoRepository INSTANCE = new DestinoRepository();
	private DestinoRepository() {}
	public static DestinoRepository getInstance() {
		return INSTANCE;
	}
	// Fin del Eager Singleton

	@Override
	public List<Destino> findAll(Connection conexion) throws RepositoryException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Destino findById(Connection conexion, Integer id) throws RepositoryException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Destino save(Connection conexion, Destino entity) throws RepositoryException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Destino update(Connection conexion, Integer id, Destino entity) throws RepositoryException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteById(Connection conexion, Integer id) throws RepositoryException {
		// TODO Auto-generated method stub
	}

	// =============================================
	// Queries extra
	// =============================================
}