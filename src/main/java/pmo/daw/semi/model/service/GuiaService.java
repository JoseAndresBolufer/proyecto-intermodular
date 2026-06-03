package pmo.daw.semi.model.service;

import java.sql.Connection;
import java.util.List;

import pmo.daw.semi.excepciones.ServiceException;
import pmo.daw.semi.model.entities.Destino;
import pmo.daw.semi.model.entities.Guia;
import pmo.daw.semi.model.repository.GuiaRepository;
import pmo.daw.semi.model.service.base.BaseService;

public class GuiaService extends BaseService<Guia, Integer> {

    private static final GuiaService INSTANCE = new GuiaService();

    private GuiaService() {}

    public static GuiaService getInstance() {
        return INSTANCE;
    }

    private final GuiaRepository guiaRepository = GuiaRepository.getInstance();
    private final DestinoService destinoService = DestinoService.getInstance();

    @Override
    public List<Guia> findAll(Connection conexion) throws ServiceException {
        return guiaRepository.findAll(conexion);
    }

    @Override
    public Guia findById(Connection conexion, Integer id) throws ServiceException {
        if (id == null) {
            throw new ServiceException("El id del guía no puede ser null");
        }

        Guia guia = guiaRepository.findById(conexion, id);

        if (guia == null) {
            throw new ServiceException("No existe ningún guía con id = " + id);
        }

        return guia;
    }

    @Override
    public Guia save(Connection conexion, Guia guia) throws ServiceException {
        validarGuia(conexion, guia);

        return guiaRepository.save(conexion, guia);
    }

    @Override
    public Guia update(Connection conexion, Integer id, Guia guia) throws ServiceException {
        if (id == null) {
            throw new ServiceException("El id del guía no puede ser null");
        }

        validarGuia(conexion, guia);

        Guia guiaExistente = guiaRepository.findById(conexion, id);

        if (guiaExistente == null) {
            throw new ServiceException("No existe ningún guía con id = " + id);
        }

        guia.setId(id);

        return guiaRepository.update(conexion, id, guia);
    }

    @Override
    public void deleteById(Connection conexion, Integer id) throws ServiceException {
        if (id == null) {
            throw new ServiceException("El id del guía no puede ser null");
        }

        Guia guiaExistente = guiaRepository.findById(conexion, id);

        if (guiaExistente == null) {
            throw new ServiceException("No existe ningún guía con id = " + id);
        }

        guiaRepository.deleteById(conexion, id);
    }

    public List<Guia> findByIdDestino(Connection conexion, Integer idDestino) throws ServiceException {
        if (idDestino == null) {
            throw new ServiceException("El id del destino no puede ser null");
        }

        return guiaRepository.findByIdDestino(conexion, idDestino);
    }

    public List<Guia> findByDestinoIsNull(Connection conexion) throws ServiceException {
        return guiaRepository.findIfIdDestinoIsNull(conexion);
    }

    public void addDestino(Connection conexion, Integer id, Integer idDestino) throws ServiceException {
        if (id == null) {
            throw new ServiceException("El id del guía no puede ser null");
        }

        if (idDestino == null) {
            throw new ServiceException("El id del destino no puede ser null");
        }

        Guia guiaExistente = guiaRepository.findById(conexion, id);

        if (guiaExistente == null) {
            throw new ServiceException("No existe ningún guía con id = " + id);
        }

        Destino destinoExistente = destinoService.findById(conexion, idDestino);

        if (destinoExistente == null) {
            throw new ServiceException("No existe ningún destino con id = " + idDestino);
        }

        guiaRepository.addDestino(conexion, id, idDestino);
    }

    public void removeDestino(Connection conexion, Integer id) throws ServiceException {
        if (id == null) {
            throw new ServiceException("El id del guía no puede ser null");
        }

        Guia guiaExistente = guiaRepository.findById(conexion, id);

        if (guiaExistente == null) {
            throw new ServiceException("No existe ningún guía con id = " + id);
        }

        guiaRepository.removeDestino(conexion, id);
    }

    public List<Guia> findByIdDestinoTransac(Integer idDestino) throws ServiceException {
        return ejecutarTransaccion(conexion -> findByIdDestino(conexion, idDestino));
    }

    public List<Guia> findByDestinoIsNullTransac() throws ServiceException {
        return ejecutarTransaccion(conexion -> findByDestinoIsNull(conexion));
    }

    public void addDestinoTransac(Integer id, Integer idDestino) throws ServiceException {
        ejecutarTransaccion(conexion -> {
            addDestino(conexion, id, idDestino);
            return null;
        });
    }

    public void removeDestinoTransac(Integer id) throws ServiceException {
        ejecutarTransaccion(conexion -> {
            removeDestino(conexion, id);
            return null;
        });
    }

    private void validarGuia(Connection conexion, Guia guia) throws ServiceException {
        if (guia == null) {
            throw new ServiceException("El guía no puede ser null");
        }

        if (guia.getNombre() == null || guia.getNombre().isBlank()) {
            throw new ServiceException("El nombre del guía es obligatorio");
        }

        if (guia.getApellidos() == null || guia.getApellidos().isBlank()) {
            throw new ServiceException("Los apellidos del guía son obligatorios");
        }

        if (guia.getEspecialidad() == null || guia.getEspecialidad().isBlank()) {
            throw new ServiceException("La especialidad del guía es obligatoria");
        }

        if (!guia.getEspecialidad().equals("Geografia")
                && !guia.getEspecialidad().equals("Historia")
                && !guia.getEspecialidad().equals("Arquitectura")
                && !guia.getEspecialidad().equals("Comida")) {
            throw new ServiceException("La especialidad debe ser Geografia, Historia, Arquitectura o Comida");
        }

        if (guia.getIdDestino() != null) {
            destinoService.findById(conexion, guia.getIdDestino());
        }
    }
}