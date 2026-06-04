package pmo.daw.semi.model.service;

import java.sql.Connection;
import java.util.List;

import pmo.daw.semi.excepciones.ServiceException;
import pmo.daw.semi.model.entities.Destino;
import pmo.daw.semi.model.entities.Pasaporte;
import pmo.daw.semi.model.entities.Reserva;
import pmo.daw.semi.model.entities.Usuario;
import pmo.daw.semi.model.repository.ReservaRepository;
import pmo.daw.semi.model.service.base.BaseService;

public class ReservaService extends BaseService<Reserva, Integer> {

    private static final ReservaService INSTANCE = new ReservaService();

    private ReservaService() {}

    public static ReservaService getInstance() {
        return INSTANCE;
    }

    private final ReservaRepository reservaRepository = ReservaRepository.getInstance();
    private final UsuarioService usuarioService = UsuarioService.getInstance();
    private final DestinoService destinoService = DestinoService.getInstance();
    private final PasaporteService pasaporteService = PasaporteService.getInstance();

    @Override
    public List<Reserva> findAll(Connection conexion) throws ServiceException {
        return reservaRepository.findAll(conexion);
    }

    @Override
    public Reserva findById(Connection conexion, Integer id) throws ServiceException {
        if (id == null) {
            throw new ServiceException("El id de la reserva no puede ser null");
        }

        Reserva reserva = reservaRepository.findById(conexion, id);

        if (reserva == null) {
            throw new ServiceException("No existe ninguna reserva con id = " + id);
        }

        return reserva;
    }

    @Override
    public Reserva save(Connection conexion, Reserva reserva) throws ServiceException {
        validarReserva(conexion, reserva);

        Reserva reservaExistente = reservaRepository.findByUsuarioAndDestino(
                conexion,
                reserva.getIdUsuario(),
                reserva.getIdDestino()
        );

        if (reservaExistente != null) {
            throw new ServiceException("El usuario ya tiene una reserva para ese destino");
        }

        return reservaRepository.save(conexion, reserva);
    }

    @Override
    public Reserva update(Connection conexion, Integer id, Reserva reserva) throws ServiceException {
        if (id == null) {
            throw new ServiceException("El id de la reserva no puede ser null");
        }

        validarReserva(conexion, reserva);

        Reserva reservaExistente = reservaRepository.findById(conexion, id);

        if (reservaExistente == null) {
            throw new ServiceException("No existe ninguna reserva con id = " + id);
        }

        reserva.setId(id);

        return reservaRepository.update(conexion, id, reserva);
    }

    @Override
    public void deleteById(Connection conexion, Integer id) throws ServiceException {
        if (id == null) {
            throw new ServiceException("El id de la reserva no puede ser null");
        }

        Reserva reservaExistente = reservaRepository.findById(conexion, id);

        if (reservaExistente == null) {
            throw new ServiceException("No existe ninguna reserva con id = " + id);
        }

        reservaRepository.deleteById(conexion, id);
    }

    public List<Reserva> findByIdUsuario(Connection conexion, Integer idUsuario) throws ServiceException {
        if (idUsuario == null) {
            throw new ServiceException("El id del usuario no puede ser null");
        }

        return reservaRepository.findByIdUsuario(conexion, idUsuario);
    }

    public List<Reserva> findByIdDestino(Connection conexion, Integer idDestino) throws ServiceException {
        if (idDestino == null) {
            throw new ServiceException("El id del destino no puede ser null");
        }

        return reservaRepository.findByIdDestino(conexion, idDestino);
    }

    public void deleteByUsuarioAndDestino(Connection conexion, Integer idUsuario, Integer idDestino) throws ServiceException {
        if (idUsuario == null) {
            throw new ServiceException("El id del usuario no puede ser null");
        }

        if (idDestino == null) {
            throw new ServiceException("El id del destino no puede ser null");
        }

        reservaRepository.deleteByUsuarioAndDestino(conexion, idUsuario, idDestino);
    }

    public List<Reserva> findByIdUsuarioTransac(Integer idUsuario) throws ServiceException {
        return ejecutarTransaccion(conexion -> findByIdUsuario(conexion, idUsuario));
    }

    public List<Reserva> findByIdDestinoTransac(Integer idDestino) throws ServiceException {
        return ejecutarTransaccion(conexion -> findByIdDestino(conexion, idDestino));
    }

    public void deleteByUsuarioAndDestinoTransac(Integer idUsuario, Integer idDestino) throws ServiceException {
        ejecutarTransaccion(conexion -> {
            deleteByUsuarioAndDestino(conexion, idUsuario, idDestino);
            return null;
        });
    }

    private void validarReserva(Connection conexion, Reserva reserva) throws ServiceException {
        if (reserva == null) {
            throw new ServiceException("La reserva no puede ser null");
        }

        if (reserva.getIdUsuario() == null) {
            throw new ServiceException("El id del usuario es obligatorio");
        }

        if (reserva.getIdDestino() == null) {
            throw new ServiceException("El id del destino es obligatorio");
        }

        Usuario usuario = usuarioService.findById(conexion, reserva.getIdUsuario());
        Destino destino = destinoService.findById(conexion, reserva.getIdDestino());

        if (destino.getRequierePasaporte()) {
            Pasaporte pasaporte = pasaporteService.findByIdUsuario(conexion, usuario.getId());

            if (pasaporte == null) {
                throw new ServiceException("El usuario necesita pasaporte para reservar este destino");
            }
        }
    }
}