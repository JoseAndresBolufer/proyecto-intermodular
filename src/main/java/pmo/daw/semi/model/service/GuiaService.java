package pmo.daw.semi.model.service;

import java.sql.Connection;
import java.util.List;

import pmo.daw.semi.excepciones.ServiceException;
import pmo.daw.semi.model.entities.Guia;
import pmo.daw.semi.model.repository.GuiaRepository;
import pmo.daw.semi.model.service.base.BaseService;

public class GuiaService extends BaseService<Guia, Integer> {
	// Eager Singleton (JNA-style)
	private static final GuiaService INSTANCE = new GuiaService();
	private GuiaService() {}
	public static GuiaService getInstance() {
		return INSTANCE;
	}
	// Fin del Eager Singleton

	// Instancia los repositorios que necesites
	private final GuiaRepository guiaRepository = GuiaRepository.getInstance();

	@Override
	public List<Guia> findAll(Connection conexion) throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Guia findById(Connection conexion, Integer id) throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Guia save(Connection conexion, Guia guia) throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Guia update(Connection conexion, Integer id, Guia guia) throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteById(Connection conexion, Integer id) throws ServiceException {
		// TODO Auto-generated method stub
	}
	
	// Servicios extra
	public List<Guia> findByIdDestino(Connection conexion, Integer idDestino) throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Guia> findByDestinoIsNull(Connection conexion) throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	public void addDestino(Connection conexion, Integer id, Integer idDestino) throws ServiceException {
		// TODO Auto-generated method stub
	}

	public void removeDestino(Connection conexion, Integer id) throws ServiceException {
		// TODO Auto-generated method stub
	}	
}