package pmo.daw.semi.controller.base;

import java.util.List;

import org.springframework.http.ResponseEntity;

public interface IBaseController<E, ID> {
    ResponseEntity<List<E>> findAll();
    ResponseEntity<E> findById(ID id);
    ResponseEntity<E> save(E entity);
    ResponseEntity<E> update(ID id, E entity);
    ResponseEntity<Void> deleteById(ID id);
}
