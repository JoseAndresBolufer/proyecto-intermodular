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
import pmo.daw.semi.model.entities.Destino;
import pmo.daw.semi.model.service.DestinoService;

@RestController
@RequestMapping("/api/destino")
public class DestinoController extends BaseController<Destino, Integer> {

    private final DestinoService destinoService = DestinoService.getInstance();

    @Override
    @GetMapping
    public ResponseEntity<List<Destino>> findAll() {
        try {
            List<Destino> destinos = destinoService.findAll();
            return ResponseEntity.ok(destinos);

        } catch (TransactionManagerException e) {
            return ResponseEntity.internalServerError().build();

        } catch (ServiceException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<Destino> findById(@PathVariable Integer id) {
        try {
            Destino destino = destinoService.findById(id);
            return ResponseEntity.ok(destino);

        } catch (TransactionManagerException e) {
            return ResponseEntity.internalServerError().build();

        } catch (ServiceException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Override
    @PostMapping
    public ResponseEntity<Destino> save(@RequestBody Destino destino) {
        try {
            Destino destinoCreado = destinoService.save(destino);
            URI location = URI.create("/api/destino/" + destinoCreado.getId());

            return ResponseEntity.created(location).body(destinoCreado);

        } catch (TransactionManagerException e) {
            return ResponseEntity.internalServerError().build();

        } catch (ServiceException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<Destino> update(@PathVariable Integer id, @RequestBody Destino destino) {
        try {
            Destino destinoModificado = destinoService.update(id, destino);
            return ResponseEntity.ok(destinoModificado);

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
            destinoService.deleteById(id);
            return ResponseEntity.noContent().build();

        } catch (TransactionManagerException e) {
            return ResponseEntity.internalServerError().build();

        } catch (ServiceException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}