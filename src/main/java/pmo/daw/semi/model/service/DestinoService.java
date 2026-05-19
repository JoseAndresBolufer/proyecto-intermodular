package pmo.daw.semi.model.service;

import java.sql.Connection;
import java.util.List;

import pmo.daw.semi.excepciones.ServiceException;
import pmo.daw.semi.model.entities.Destino;
import pmo.daw.semi.model.repository.DestinoRepository;
import pmo.daw.semi.model.service.base.BaseService;


public class DestinoService extends BaseService<Destino, Integer> {
	// Eager Singleton (JNA-style)
	private static final DestinoService INSTANCE = new DestinoService();
	private DestinoService() {}
	public static DestinoService getInstance() {
		return INSTANCE;
	}
	// Fin del Eager Singleton

	// Instancia los repositorios que necesites
	private final DestinoRepository destinoRepository = DestinoRepository.getInstance();

	@Override
	public List<Destino> findAll(Connection conexion) throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Destino findById(Connection conexion, Integer id) throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Destino save(Connection conexion, Destino destino) throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Destino update(Connection conexion, Integer id, Destino destino) throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteById(Connection conexion, Integer id) throws ServiceException {
		// TODO Auto-generated method stub
	}
}