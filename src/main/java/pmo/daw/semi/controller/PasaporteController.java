package pmo.daw.semi.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pmo.daw.semi.controller.base.BaseController;
import pmo.daw.semi.excepciones.ServiceException;
import pmo.daw.semi.excepciones.TransactionManagerException;
import pmo.daw.semi.model.entities.Pasaporte;
import pmo.daw.semi.model.service.PasaporteService;

/**
 * Controlador REST para la entidad Pasaporte.
 * Expone endpoints CRUD sobre pasaportes.
 */
@RestController
@RequestMapping("/api/pasaporte")
public class PasaporteController extends BaseController<Pasaporte, Integer> {

    // Servicio Singleton para la lógica de negocio de Pasaporte
    private final PasaporteService pasaporteService = PasaporteService.getInstance();

    // =============================================
    // MÉTODOS CRUD
    // =============================================

    /**
     * GET /api/pasaporte
     * Devuelve la lista completa de pasaportes.
     * Retorna 200 con la lista o 500/400 en caso de error.
     */
    @Override
    @GetMapping
    public ResponseEntity<List<Pasaporte>> findAll() {
        try {
            List<Pasaporte> pasaportes = pasaporteService.findAll();
            return ResponseEntity.ok(pasaportes); // 200 OK + la lista de pasaportes
        } catch (TransactionManagerException e) {
        	// tme.printStackTrace();
            // Error inesperado de transacción -> 500
            return ResponseEntity.internalServerError().build();
        } catch (ServiceException e) {
        	// se.printStackTrace();
            // Error de negocio, ej. parámetros incorrectos -> 400
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * GET /api/pasaporte/{id}
     * Devuelve un pasaporte por su ID.
     * Retorna 200 con el pasaporte, 400 si id inválido, 500 si fallo interno.
     */
    @Override
    @GetMapping("/{id}")
    public ResponseEntity<Pasaporte> findById(@PathVariable Integer id) {
        try {
            Pasaporte pasaporte = pasaporteService.findById(id);
            return ResponseEntity.ok(pasaporte); // 200 OK + el pasaporte
        } catch (TransactionManagerException e) {
        	// tme.printStackTrace();
            return ResponseEntity.internalServerError().build(); // 500
        } catch (ServiceException e) {
        	// se.printStackTrace();
            return ResponseEntity.badRequest().build(); // 400
        }
    }

    /**
     * POST /api/pasaporte
     * Crea un nuevo pasaporte.
     * Retorna 201 con el pasaporte creado y la cabecera Location.
     */
    @Override
    @PostMapping
    public ResponseEntity<Pasaporte> save(@RequestBody Pasaporte pasaporte) {
        try {
            Pasaporte creado = pasaporteService.save(pasaporte);
            URI location = URI.create("/api/pasaporte/" + creado.getId());
            return ResponseEntity.created(location).body(creado); // 201 Creado + el pasaporte
        } catch (TransactionManagerException e) {
        	// tme.printStackTrace();
            return ResponseEntity.internalServerError().build(); // 500
        } catch (ServiceException e) {
        	// se.printStackTrace();
            return ResponseEntity.badRequest().build(); // 400
        }
    }

    /**
     * PUT /api/pasaporte/{id}
     * Actualiza un pasaporte existente.
     * Retorna 200 con el pasaporte actualizado, 400 si id/pasaporte inválido, 500 si error interno.
     */
    @Override
    @PutMapping("/{id}")
    public ResponseEntity<Pasaporte> update(@PathVariable Integer id, @RequestBody Pasaporte pasaporte) {
        try {
            Pasaporte modificado = pasaporteService.update(id, pasaporte); // 200 OK + el pasaporte
            return ResponseEntity.ok(modificado);
        } catch (TransactionManagerException e) {
        	// tme.printStackTrace();
            return ResponseEntity.internalServerError().build(); // 500
        } catch (ServiceException e) {
        	// se.printStackTrace();
            return ResponseEntity.badRequest().build(); // 400
        }
    }

    /**
     * DELETE /api/pasaporte/{id}
     * Elimina un pasaporte por su ID.
     * Retorna 204 si se elimina correctamente, 400 si id inválido, 500 si error interno.
     */
    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Integer id) {
        try {
            pasaporteService.deleteById(id);
            return ResponseEntity.noContent().build(); // 204
        } catch (TransactionManagerException e) {
        	// tme.printStackTrace();
            return ResponseEntity.internalServerError().build(); // 500
        } catch (ServiceException e) {
        	// se.printStackTrace();
            return ResponseEntity.badRequest().build(); // 400
        }
    }
}