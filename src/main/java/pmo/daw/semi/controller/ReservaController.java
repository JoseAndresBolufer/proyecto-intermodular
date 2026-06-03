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
import pmo.daw.semi.model.entities.Reserva;
import pmo.daw.semi.model.service.ReservaService;

@RestController
@RequestMapping("/api/reserva")
public class ReservaController extends BaseController<Reserva, Integer> {

    private final ReservaService reservaService = ReservaService.getInstance();

    @Override
    @GetMapping
    public ResponseEntity<List<Reserva>> findAll() {
        try {
            List<Reserva> reservas = reservaService.findAll();
            return ResponseEntity.ok(reservas);

        } catch (TransactionManagerException e) {
            return ResponseEntity.internalServerError().build();

        } catch (ServiceException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<Reserva> findById(@PathVariable Integer id) {
        try {
            Reserva reserva = reservaService.findById(id);
            return ResponseEntity.ok(reserva);

        } catch (TransactionManagerException e) {
            return ResponseEntity.internalServerError().build();

        } catch (ServiceException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Override
    @PostMapping
    public ResponseEntity<Reserva> save(@RequestBody Reserva reserva) {
        try {
            Reserva reservaCreada = reservaService.save(reserva);
            URI location = URI.create("/api/reserva/" + reservaCreada.getId());

            return ResponseEntity.created(location).body(reservaCreada);

        } catch (TransactionManagerException e) {
            return ResponseEntity.internalServerError().build();

        } catch (ServiceException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<Reserva> update(@PathVariable Integer id, @RequestBody Reserva reserva) {
        try {
            Reserva reservaModificada = reservaService.update(id, reserva);
            return ResponseEntity.ok(reservaModificada);

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
            reservaService.deleteById(id);
            return ResponseEntity.noContent().build();

        } catch (TransactionManagerException e) {
            return ResponseEntity.internalServerError().build();

        } catch (ServiceException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<Reserva>> findByIdUsuario(@PathVariable Integer idUsuario) {
        try {
            List<Reserva> reservas = reservaService.findByIdUsuarioTransac(idUsuario);
            return ResponseEntity.ok(reservas);

        } catch (TransactionManagerException e) {
            return ResponseEntity.internalServerError().build();

        } catch (ServiceException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/destino/{idDestino}")
    public ResponseEntity<List<Reserva>> findByIdDestino(@PathVariable Integer idDestino) {
        try {
            List<Reserva> reservas = reservaService.findByIdDestinoTransac(idDestino);
            return ResponseEntity.ok(reservas);

        } catch (TransactionManagerException e) {
            return ResponseEntity.internalServerError().build();

        } catch (ServiceException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/usuario/{idUsuario}/destino/{idDestino}")
    public ResponseEntity<Void> deleteByUsuarioAndDestino(@PathVariable Integer idUsuario, @PathVariable Integer idDestino) {
        try {
            reservaService.deleteByUsuarioAndDestinoTransac(idUsuario, idDestino);
            return ResponseEntity.noContent().build();

        } catch (TransactionManagerException e) {
            return ResponseEntity.internalServerError().build();

        } catch (ServiceException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}