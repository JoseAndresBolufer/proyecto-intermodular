package pmo.daw.semi.model.service;

import java.sql.Connection;
import java.util.List;

import pmo.daw.semi.excepciones.ServiceException;
import pmo.daw.semi.model.entities.Destino;
import pmo.daw.semi.model.repository.DestinoRepository;
import pmo.daw.semi.model.service.base.BaseService;

public class DestinoService extends BaseService<Destino, Integer> {

    private static final DestinoService INSTANCE = new DestinoService();

    private DestinoService() {}

    public static DestinoService getInstance() {
        return INSTANCE;
    }

    private final DestinoRepository destinoRepository = DestinoRepository.getInstance();

    @Override
    public List<Destino> findAll(Connection conexion) throws ServiceException {
        return destinoRepository.findAll(conexion);
    }

    @Override
    public Destino findById(Connection conexion, Integer id) throws ServiceException {
        if (id == null) {
            throw new ServiceException("El id del destino no puede ser null");
        }

        Destino destino = destinoRepository.findById(conexion, id);

        if (destino == null) {
            throw new ServiceException("No existe ningún destino con id = " + id);
        }

        return destino;
    }

    @Override
    public Destino save(Connection conexion, Destino destino) throws ServiceException {
        validarDestino(destino);

        return destinoRepository.save(conexion, destino);
    }

    @Override
    public Destino update(Connection conexion, Integer id, Destino destino) throws ServiceException {
        if (id == null) {
            throw new ServiceException("El id del destino no puede ser null");
        }

        validarDestino(destino);

        Destino destinoExistente = destinoRepository.findById(conexion, id);

        if (destinoExistente == null) {
            throw new ServiceException("No existe ningún destino con id = " + id);
        }

        destino.setId(id);

        return destinoRepository.update(conexion, id, destino);
    }

    @Override
    public void deleteById(Connection conexion, Integer id) throws ServiceException {
        if (id == null) {
            throw new ServiceException("El id del destino no puede ser null");
        }

        Destino destinoExistente = destinoRepository.findById(conexion, id);

        if (destinoExistente == null) {
            throw new ServiceException("No existe ningún destino con id = " + id);
        }

        destinoRepository.deleteById(conexion, id);
    }

    private void validarDestino(Destino destino) throws ServiceException {
        if (destino == null) {
            throw new ServiceException("El destino no puede ser null");
        }

        if (destino.getCiudad() == null || destino.getCiudad().isBlank()) {
            throw new ServiceException("La ciudad del destino es obligatoria");
        }

        if (destino.getPais() == null || destino.getPais().isBlank()) {
            throw new ServiceException("El país del destino es obligatorio");
        }

        if (destino.getPrecio() == null) {
            throw new ServiceException("El precio del destino es obligatorio");
        }

        if (destino.getPrecio().doubleValue() < 0) {
            throw new ServiceException("El precio del destino no puede ser negativo");
        }

        if (destino.getRequierePasaporte() == null) {
            throw new ServiceException("Debe indicarse si el destino requiere pasaporte");
        }
    }
}