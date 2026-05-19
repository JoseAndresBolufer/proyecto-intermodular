package pmo.daw.semi.model.repository;

import java.sql.Connection;
import java.util.List;

import pmo.daw.semi.excepciones.RepositoryException;
import pmo.daw.semi.model.entities.Reserva;
import pmo.daw.semi.model.repository.base.BaseRepository;

public class ReservaRepository extends BaseRepository<Reserva, Integer> {
	// Eager Singleton (JNA-style)
	private static final ReservaRepository INSTANCE = new ReservaRepository();
	private ReservaRepository() {}
	public static ReservaRepository getInstance() {
		return INSTANCE;
	}
	// Fin del Eager Singleton

	@Override
	public List<Reserva> findAll(Connection conexion) throws RepositoryException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Reserva findById(Connection conexion, Integer id) throws RepositoryException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Reserva save(Connection conexion, Reserva entity) throws RepositoryException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Reserva update(Connection conexion, Integer id, Reserva entity) throws RepositoryException {
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