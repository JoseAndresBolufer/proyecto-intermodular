package pmo.daw.semi.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pmo.daw.semi.controller.base.BaseController;
import pmo.daw.semi.model.entities.Destino;
import pmo.daw.semi.model.service.DestinoService;

@RestController
@RequestMapping("/api/destino")
public class DestinoController extends BaseController<Destino, Integer> {
	private final DestinoService destinoService = DestinoService.getInstance();

	@Override
	public ResponseEntity<List<Destino>> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<Destino> findById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<Destino> save(Destino entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<Destino> update(Integer id, Destino entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<Void> deleteById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}
}
