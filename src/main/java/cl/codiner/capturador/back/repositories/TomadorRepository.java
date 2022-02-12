package cl.codiner.capturador.back.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import cl.codiner.capturador.back.models.Tomador;
@Repository
public interface TomadorRepository extends CrudRepository<Tomador,Long>{

	Tomador findByNombreAndApellido(String nombre, String apellido);
	void deleteById(Long id);
}
