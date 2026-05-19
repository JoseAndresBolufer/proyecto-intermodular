package pmo.daw.semi.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pmo.daw.semi.controller.base.BaseController;
import pmo.daw.semi.model.entities.Guia;
import pmo.daw.semi.model.service.GuiaService;

@RestController
@RequestMapping("/api/guia")
public class GuiaController extends BaseController<Guia, Integer> {
	private final GuiaService guiaService = GuiaService.getInstance();

	@Override
	@GetMapping("/{id}")
	public ResponseEntity<List<Guia>> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<Guia> findById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<Guia> save(Guia entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<Guia> update(Integer id, Guia entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<Void> deleteById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	public ResponseEntity<List<Guia>> findByIdDestino(Integer idDestino) {
		// TODO Auto-generated method stub
		return null;
	}

	public ResponseEntity<List<Guia>> findByDestinoIsNull() {
		// TODO Auto-generated method stub
		return null;
	}

	public ResponseEntity<Void> addDestino(Integer id, Integer idDestino) {
		// TODO Auto-generated method stub
		return null;
	}

	public ResponseEntity<Void> removeDestino(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}	
}