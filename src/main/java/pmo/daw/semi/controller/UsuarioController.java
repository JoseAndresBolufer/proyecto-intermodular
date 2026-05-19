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
import pmo.daw.semi.model.entities.Usuario;
import pmo.daw.semi.model.service.UsuarioService;

/**
 * Controlador REST para operaciones CRUD sobre la entidad Usuario.
 * Gestiona usuarios y sus pasaportes a través de UsuarioService.
 */
@RestController
@RequestMapping("/api/usuario")
public class UsuarioController extends BaseController<Usuario, Integer> {

    // Servicio principal para manejar la lógica de negocio de Usuario y Pasaporte
    private final UsuarioService usuarioService = UsuarioService.getInstance();

    /**
     * GET /api/usuario
     * Devuelve la lista completa de usuarios y su pasaporte.
     * Retorna 200 con la lista completa de usuarios y su pasaporte o 500/400 en caso de error.
     */
    @Override
    @GetMapping
    public ResponseEntity<List<Usuario>> findAll() {
        try {
            List<Usuario> usuarios = usuarioService.findAll();
            return ResponseEntity.ok(usuarios); // 200 OK + la lista de usuarios y pasaporte
        } catch (TransactionManagerException tme) {
        	// tme.printStackTrace();
            // Error interno de base de datos o transacción -> 500
            return ResponseEntity.internalServerError().build();
        } catch (ServiceException se) {
        	// se.printStackTrace();
            // Error en la lógica de negocio, datos inválidos -> 400
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * GET /api/usuario/{id}
     * Devuelve un usuario con su pasaporte por su ID.
     * Retorna 200 con el usuario y su pasaporte, 400 si id inválido, 500 si fallo interno.
     */
    @Override
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> findById(@PathVariable Integer id) {
        try {
            Usuario usuario = usuarioService.findById(id);
            return ResponseEntity.ok(usuario); // 200 OK + el usuario y pasaporte
        } catch (TransactionManagerException e) {
        	// tme.printStackTrace();
            return ResponseEntity.internalServerError().build(); // 500
        } catch (ServiceException e) {
        	// se.printStackTrace();
            return ResponseEntity.badRequest().build(); // 400
        }
    }

    /**
     * POST /api/usuario
     * Crea un nuevo usuario y su pasaporte.
     * Retorna 201 con el usuario y su pasaporte creados y la cabecera Location.
     */
    @Override
    @PostMapping
    public ResponseEntity<Usuario> save(@RequestBody Usuario usuario) {
        try {
            Usuario creado = usuarioService.save(usuario);
            URI location = URI.create("/api/usuario/" + creado.getId());
            return ResponseEntity.created(location).body(creado); // 201 Creado + el usuario y pasaporte
        } catch (TransactionManagerException e) {
        	// tme.printStackTrace();
            return ResponseEntity.internalServerError().build(); // 500
        } catch (ServiceException e) {
        	// se.printStackTrace();
            return ResponseEntity.badRequest().build(); // 400
        }
    }

    /**
     * PUT /api/usuario/{id}
     * Actualiza un usuario existente y su pasaporte .
     * Retorna 200 con el usuario y su pasaporte actualizados, 400 si id/pasaporte inválido, 500 si error interno.
     */
    @Override
    @PutMapping("/{id}")
    public ResponseEntity<Usuario> update(@PathVariable Integer id, @RequestBody Usuario usuario) {
        try {
            Usuario modificado = usuarioService.update(id, usuario);
            return ResponseEntity.ok(modificado); // 200 OK + el usuario y pasaporte
        } catch (TransactionManagerException e) {
        	// tme.printStackTrace();
            return ResponseEntity.internalServerError().build(); // 500
        } catch (ServiceException e) {
        	// se.printStackTrace();
            return ResponseEntity.badRequest().build(); // 400
        }
    }

    /**
     * DELETE /api/usuario/{id}
     * Elimina un usuario por su ID y su pasaporte.
     * Retorna 204 si se elimina correctamente, 400 si id inválido, 500 si error interno.
     */
    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Integer id) {
        try {
            usuarioService.deleteById(id);
            return ResponseEntity.noContent().build(); // 204 No Content
        } catch (TransactionManagerException e) {
        	// tme.printStackTrace();
            return ResponseEntity.internalServerError().build(); // 500
        } catch (ServiceException e) {
        	// se.printStackTrace();
            return ResponseEntity.badRequest().build(); // 400
        }
    }

    // ========================================
    // 
    // Devuelve usuarios filtrados por idDestino
    // ========================================
    /**
     * GET /api/usuario/destino/{idDestino}
     * Devuelve la lista de usuarios y su pasaporte filtrados por idDestino.
     * Retorna 200 con la lista de usuarios y su pasaporte o 500/400 en caso de error.
    */
    @GetMapping("/destino/{idDestino}")
    public ResponseEntity<List<Usuario>> findByIdDestino(@PathVariable Integer idDestino) {
        try {
            List<Usuario> usuarios = usuarioService.findByIdDestino(idDestino);
            return ResponseEntity.ok(usuarios); // 200 OK + la lista de usuarios y pasaporte
        } catch (TransactionManagerException e) {
        	// tme.printStackTrace();
            return ResponseEntity.internalServerError().build(); // 500
        } catch (ServiceException e) {
        	// se.printStackTrace();
            return ResponseEntity.badRequest().build(); // 400
        }
    }
}