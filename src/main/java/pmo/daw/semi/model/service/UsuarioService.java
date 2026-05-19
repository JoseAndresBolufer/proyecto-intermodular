package pmo.daw.semi.model.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import pmo.daw.semi.excepciones.ServiceException;
import pmo.daw.semi.model.entities.Pasaporte;
import pmo.daw.semi.model.entities.Usuario;
import pmo.daw.semi.model.repository.UsuarioRepository;
import pmo.daw.semi.model.service.base.BaseService;
import pmo.daw.semi.transactionmanager.TransactionalOperation;

public class UsuarioService extends BaseService<Usuario, Integer> {

    // =============================================
    // Singleton estilo JNA
    // =============================================
    // Asegura que solo exista una instancia de UsuarioService
    private static final UsuarioService INSTANCE = new UsuarioService();
    private UsuarioService() {}
    public static UsuarioService getInstance() { return INSTANCE; }

    // =============================================
    // Repositorios y servicios asociados
    // =============================================
    private final UsuarioRepository usuarioRepository = UsuarioRepository.getInstance();
    private final PasaporteService pasaporteService = PasaporteService.getInstance();

    // =============================================
    // CRUD básicos
    // =============================================

    /**
     * Recupera todos los usuarios de la base de datos, incluyendo sus pasaportes si existen.
     */
    @Override
    public List<Usuario> findAll(Connection conexion) throws ServiceException {
        return usuarioRepository.findAll(conexion);
    }

    /**
     * Recupera un usuario por su id.
     * @param id Id del usuario
     * @return Usuario con su pasaporte asociado (si existe)
     * @throws ServiceException si el id es null o el usuario no existe
     */
    @Override
    public Usuario findById(Connection conexion, Integer id) throws ServiceException {
        if (id == null) throw new ServiceException("id no puede ser null");

        Usuario usuario = usuarioRepository.findById(conexion, id);
        if (usuario == null) throw new ServiceException("usuario no encontrado con id = " + id);

        // Se asocia el pasaporte correspondiente
        Pasaporte pasaporte = pasaporteService.findByIdUsuario(conexion, id);
        usuario.setPasaporte(pasaporte);

        return usuario;
    }

    /**
     * Crea un nuevo usuario y, si incluye un pasaporte, lo guarda también.
     */
    @Override
    public Usuario save(Connection conexion, Usuario usuario) throws ServiceException {
        if (usuario == null) throw new ServiceException("usuario no puede ser null");

        // Guardamos primero el usuario
        Usuario nuevoUsuario = usuarioRepository.save(conexion, usuario);

        // Si el usuario incluye pasaporte, se guarda también
        Pasaporte pasaporte = usuario.getPasaporte();
        if (pasaporte != null) {
            pasaporte.setIdUsuario(usuario.getId());
            Pasaporte nuevoPasaporte = pasaporteService.save(conexion, pasaporte);
            nuevoUsuario.setPasaporte(nuevoPasaporte);
        }
        return nuevoUsuario;
    }

    /**
     * Actualiza un usuario existente y su pasaporte asociado.
     * - Si se elimina el pasaporte, se borra el anterior.
     * - Si se añade un nuevo pasaporte, se crea.
     * - Si se modifica, se actualiza.
     */
    @Override
    public Usuario update(Connection conexion, Integer id, Usuario usuario) throws ServiceException {
        if (id == null) throw new ServiceException("id no puede ser null");
        if (usuario == null) throw new ServiceException("usuario no puede ser null");

        // Buscamos usuario existente
        Usuario usuarioExistente = usuarioRepository.findById(conexion, id);
        if (usuarioExistente == null) throw new ServiceException("usuario no encontrado con id = " + id);

        // Actualizamos campos básicos
        usuarioExistente.setNombre(usuario.getNombre());
        usuarioExistente.setApellidos(usuario.getApellidos());
        usuarioExistente.setEmail(usuario.getEmail());
        usuarioExistente.setTelefono(usuario.getTelefono());
        usuarioExistente.setFechaNacimiento(usuario.getFechaNacimiento());

        // Guardamos cambios del usuario
        Usuario usuarioModificado = usuarioRepository.update(conexion, id, usuarioExistente);

        // ================================
        // Gestión del Pasaporte
        // ================================
        Pasaporte pasaporteExistente = pasaporteService.findByIdUsuario(conexion, id);
        Pasaporte pasaporteNuevo = usuario.getPasaporte();

        if (pasaporteNuevo == null && pasaporteExistente != null) {
            // Se elimina pasaporte existente
            pasaporteService.deleteById(conexion, pasaporteExistente.getId());
            usuarioModificado.setPasaporte(null);
        } else if (pasaporteNuevo != null) {
            if (pasaporteExistente == null) {
                // Se crea un nuevo pasaporte
                pasaporteNuevo.setIdUsuario(id);
                Pasaporte creado = pasaporteService.save(conexion, pasaporteNuevo);
                usuarioModificado.setPasaporte(creado);
            } else {
                // Se actualiza pasaporte existente
                Pasaporte modificado = pasaporteService.update(conexion, pasaporteExistente.getId(), pasaporteNuevo);
                usuarioModificado.setPasaporte(modificado);
            }
        }
        return usuarioModificado;
    }

    /**
     * Elimina un usuario y su pasaporte asociado si existe.
     */
    @Override
    public void deleteById(Connection conexion, Integer id) throws ServiceException {
        if (id == null) throw new ServiceException("id no puede ser null");

        Usuario usuarioExistente = usuarioRepository.findById(conexion, id);
        if (usuarioExistente == null) throw new ServiceException("usuario no encontrado con id = " + id);

        // Se elimina pasaporte asociado si existe
        Pasaporte pasaporteExistente = usuarioExistente.getPasaporte();
        if (pasaporteExistente != null) {
            pasaporteService.deleteById(conexion, pasaporteExistente.getId());
        }

        // Finalmente se elimina el usuario
        usuarioRepository.deleteById(conexion, id);
    }

    // =============================================
    // Queries auxiliares
    // =============================================
    /**
     * Recupera todos los usuarios que tienen reservas para un destino específico.
     * @param idDestino Id del destino
     * @return Lista de usuarios
     * @throws ServiceException si ocurre un error en la conexión o el parámetro es null
     */
    // como ejecutar un servicio no crud
    public List<Usuario> findByIdDestino(Integer idDestino) throws ServiceException {
        if (idDestino == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "usuario.destino.id no puede ser null");

        try (Connection conexion = getConnection()) {
            return usuarioRepository.findByIdDestino(conexion, idDestino);
        } catch (SQLException sqlException) {
            throw new ServiceException("Fallo obteniendo la conexion", sqlException);
        }
    } 
    
    // como ejecutar una transaccion desde un servicio no crud
    public List<Usuario> findByIdDestinoTransac(Integer idDestino) throws ServiceException {
        if (idDestino == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "usuario.destino.id no puede ser null");

        return ejecutarTransaccion(new TransactionalOperation<List<Usuario>>() {
			@Override
			public List<Usuario> execute(Connection conexion) throws ServiceException {
	            return usuarioRepository.findByIdDestino(conexion, idDestino);
			}
		});
    }
}