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
import pmo.daw.semi.model.entities.Guia;
import pmo.daw.semi.model.service.GuiaService;

@RestController
@RequestMapping("/api/guia")
public class GuiaController extends BaseController<Guia, Integer> {

    private final GuiaService guiaService = GuiaService.getInstance();

    @Override
    @GetMapping
    public ResponseEntity<List<Guia>> findAll() {
        try {
            List<Guia> guias = guiaService.findAll();
            return ResponseEntity.ok(guias);

        } catch (TransactionManagerException e) {
            return ResponseEntity.internalServerError().build();

        } catch (ServiceException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<Guia> findById(@PathVariable Integer id) {
        try {
            Guia guia = guiaService.findById(id);
            return ResponseEntity.ok(guia);

        } catch (TransactionManagerException e) {
            return ResponseEntity.internalServerError().build();

        } catch (ServiceException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Override
    @PostMapping
    public ResponseEntity<Guia> save(@RequestBody Guia guia) {
        try {
            Guia guiaCreado = guiaService.save(guia);
            URI location = URI.create("/api/guia/" + guiaCreado.getId());

            return ResponseEntity.created(location).body(guiaCreado);

        } catch (TransactionManagerException e) {
            return ResponseEntity.internalServerError().build();

        } catch (ServiceException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<Guia> update(@PathVariable Integer id, @RequestBody Guia guia) {
        try {
            Guia guiaModificado = guiaService.update(id, guia);
            return ResponseEntity.ok(guiaModificado);

        } catch (TransactionManagerException e) {
            return ResponseEntity.internalServerError().build();

        } catch (ServiceException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Integer id) {
        try {
            guiaService.deleteById(id);
            return ResponseEntity.noContent().build();

        } catch (TransactionManagerException e) {
            return ResponseEntity.internalServerError().build();

        } catch (ServiceException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/destino/{idDestino}")
    public ResponseEntity<List<Guia>> findByIdDestino(@PathVariable Integer idDestino) {
        try {
            List<Guia> guias = guiaService.findByIdDestinoTransac(idDestino);
            return ResponseEntity.ok(guias);

        } catch (TransactionManagerException e) {
            return ResponseEntity.internalServerError().build();

        } catch (ServiceException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/sin-destino")
    public ResponseEntity<List<Guia>> findByDestinoIsNull() {
        try {
            List<Guia> guias = guiaService.findByDestinoIsNullTransac();
            return ResponseEntity.ok(guias);

        } catch (TransactionManagerException e) {
            return ResponseEntity.internalServerError().build();

        } catch (ServiceException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}/destino/{idDestino}")
    public ResponseEntity<Void> addDestino(@PathVariable Integer id, @PathVariable Integer idDestino) {
        try {
            guiaService.addDestinoTransac(id, idDestino);
            return ResponseEntity.noContent().build();

        } catch (TransactionManagerException e) {
            return ResponseEntity.internalServerError().build();

        } catch (ServiceException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}/destino")
    public ResponseEntity<Void> removeDestino(@PathVariable Integer id) {
        try {
            guiaService.removeDestinoTransac(id);
            return ResponseEntity.noContent().build();

        } catch (TransactionManagerException e) {
            return ResponseEntity.internalServerError().build();

        } catch (ServiceException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}