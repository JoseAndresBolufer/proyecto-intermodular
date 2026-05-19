package pmo.daw.semi.model.repository;

import java.sql.Connection;
import java.util.List;

import pmo.daw.semi.excepciones.RepositoryException;
import pmo.daw.semi.model.entities.Guia;
import pmo.daw.semi.model.repository.base.BaseRepository;

public class GuiaRepository extends BaseRepository<Guia, Integer> {
	// Eager Singleton (JNA-style)
	private static final GuiaRepository INSTANCE = new GuiaRepository();
	private GuiaRepository() {}
	public static GuiaRepository getInstance() {
		return INSTANCE;
	}
	// Fin del Eager Singleton

	@Override
	public List<Guia> findAll(Connection conexion) throws RepositoryException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Guia findById(Connection conexion, Integer id) throws RepositoryException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Guia save(Connection conexion, Guia entity) throws RepositoryException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Guia update(Connection conexion, Integer id, Guia entity) throws RepositoryException {
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
	public List<Guia> findByIdDestino(Connection conexion, Integer idDestino) throws RepositoryException {
		// TODO Auto-generated method stub
		return null;		
	}

	public List<Guia> findIfIdDestinoIsNull(Connection conexion) throws RepositoryException {
		// TODO Auto-generated method stub
		return null;		
	}

	public void addDestino(Connection conexion, Integer id, Integer idDestino) throws RepositoryException {
		// TODO Auto-generated method stub
	}

	public void removeDestino(Connection conexion, Integer id) throws RepositoryException {
		// TODO Auto-generated method stub
	}
}