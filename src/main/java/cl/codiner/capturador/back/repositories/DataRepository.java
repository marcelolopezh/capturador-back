package cl.codiner.capturador.back.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import cl.codiner.capturador.back.models.Data;
@Repository
public interface DataRepository extends CrudRepository<Data,Long>{

	Data findByNroServicio(Integer nro_servicio);

}
