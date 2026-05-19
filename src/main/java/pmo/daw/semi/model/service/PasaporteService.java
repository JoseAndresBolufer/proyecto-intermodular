package pmo.daw.semi.model.service;

import java.sql.Connection;
import java.util.List;

import pmo.daw.semi.excepciones.ServiceException;
import pmo.daw.semi.model.entities.Pasaporte;
import pmo.daw.semi.model.repository.PasaporteRepository;
import pmo.daw.semi.model.service.base.BaseService;

public class PasaporteService extends BaseService<Pasaporte, Integer> {

    // =============================================
    // Singleton estilo JNA
    // =============================================
    // Asegura que solo exista una instancia de PasaporteService
    private static final PasaporteService INSTANCE = new PasaporteService();
    private PasaporteService() {}
    public static PasaporteService getInstance() { return INSTANCE; }

    // =============================================
    // Repositorio asociado
    // =============================================
    private final PasaporteRepository pasaporteRepository = PasaporteRepository.getInstance();

    // =============================================
    // CRUD básicos
    // =============================================

    /**
     * Recupera todos los pasaportes de la base de datos.
     */
    @Override
    public List<Pasaporte> findAll(Connection conexion) throws ServiceException {
        return pasaporteRepository.findAll(conexion);
    }

    /**
     * Recupera un pasaporte por su id.
     * @param id Id del pasaporte
     * @return Pasaporte correspondiente
     * @throws ServiceException si el id es null o no se encuentra
     */
    @Override
    public Pasaporte findById(Connection conexion, Integer id) throws ServiceException {
        if (id == null) throw new ServiceException("id no puede ser null");

        Pasaporte pasaporte = pasaporteRepository.findById(conexion, id);
        if (pasaporte == null) throw new ServiceException("pasaporte no encontrado con id = " + id);

        return pasaporte;
    }

    /**
     * Crea un nuevo pasaporte.
     * @param pasaporte Objeto pasaporte a guardar
     * @return Pasaporte guardado con id generado
     * @throws ServiceException si el objeto es null o falla el guardado
     */
    @Override
    public Pasaporte save(Connection conexion, Pasaporte pasaporte) throws ServiceException {
        if (pasaporte == null) throw new ServiceException("pasaporte no puede ser null");

        return pasaporteRepository.save(conexion, pasaporte);
    }

    /**
     * Actualiza un pasaporte existente.
     * @param id Id del pasaporte a actualizar
     * @param pasaporte Datos nuevos del pasaporte
     * @return Pasaporte actualizado
     * @throws ServiceException si el id o el objeto son null, o si no se encuentra el pasaporte
     */
    @Override
    public Pasaporte update(Connection conexion, Integer id, Pasaporte pasaporte) throws ServiceException {
        if (id == null) throw new ServiceException("id no puede ser null");
        if (pasaporte == null) throw new ServiceException("pasaporte no puede ser null");

        // Buscamos pasaporte existente
        Pasaporte pasaporteExistente = pasaporteRepository.findById(conexion, id);
        if (pasaporteExistente == null) throw new ServiceException("pasaporte no encontrado con id = " + id);

        // Actualizamos campos
        pasaporteExistente.setFechaCaducidad(pasaporte.getFechaCaducidad());
        pasaporteExistente.setNumeroSafe(pasaporte.getNumero());
        pasaporteExistente.setPaisExpedicionSafe(pasaporte.getPaisExpedicion());
        pasaporteExistente.setIdUsuario(pasaporte.getIdUsuario());

        return pasaporteRepository.update(conexion, id, pasaporteExistente);
    }

    /**
     * Elimina un pasaporte por su id.
     * @param id Id del pasaporte a eliminar
     * @throws ServiceException si el id es null o no se encuentra el pasaporte
     */
    @Override
    public void deleteById(Connection conexion, Integer id) throws ServiceException {
        if (id == null) throw new ServiceException("id no puede ser null");

        // Validamos existencia antes de eliminar
        if (pasaporteRepository.findById(conexion, id) == null)
            throw new ServiceException("pasaporte no encontrado con id = " + id);

        pasaporteRepository.deleteById(conexion, id);
    }

    // =============================================
    // Queries auxiliares
    // =============================================

    /**
     * Recupera el pasaporte de un usuario dado su id.
     * @param idUsuario Id del usuario
     * @return Pasaporte asociado al usuario, o null si no existe
     * @throws ServiceException si el idUsuario es null
     */
    public Pasaporte findByIdUsuario(Connection conexion, Integer idUsuario) throws ServiceException {
        if (idUsuario == null) throw new ServiceException("idUsuario no puede ser null");

        return pasaporteRepository.findByIdUsuario(conexion, idUsuario);
    }
}