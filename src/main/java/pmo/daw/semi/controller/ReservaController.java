package pmo.daw.semi.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pmo.daw.semi.controller.base.BaseController;
import pmo.daw.semi.model.entities.Reserva;
import pmo.daw.semi.model.service.ReservaService;

@RestController
@RequestMapping("/api/reserva")
public class ReservaController extends BaseController<Reserva, Integer> {
	private final ReservaService reservaService = ReservaService.getInstance();

	@Override
	public ResponseEntity<List<Reserva>> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<Reserva> findById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<Reserva> save(Reserva entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<Reserva> update(Integer id, Reserva entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<Void> deleteById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}
}