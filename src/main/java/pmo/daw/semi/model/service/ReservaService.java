package pmo.daw.semi.model.service;

import java.sql.Connection;
import java.util.List;

import pmo.daw.semi.excepciones.ServiceException;
import pmo.daw.semi.model.entities.Reserva;
import pmo.daw.semi.model.repository.ReservaRepository;
import pmo.daw.semi.model.service.base.BaseService;

public class ReservaService extends BaseService<Reserva, Integer> {
	// Eager Singleton (JNA-style)
	private static final ReservaService INSTANCE = new ReservaService();
	private ReservaService() {}
	public static ReservaService getInstance() {
		return INSTANCE;
	}
	// Fin del Eager Singleton

	// Instancia los repositorios que necesites
	private final ReservaRepository reservaRepository = ReservaRepository.getInstance();

	@Override
	public List<Reserva> findAll(Connection conexion) throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Reserva findById(Connection conexion, Integer id) throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Reserva save(Connection conexion, Reserva reserva) throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Reserva update(Connection conexion, Integer id, Reserva reserva) throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteById(Connection conexion, Integer id) throws ServiceException {
		// TODO Auto-generated method stub
	}
}