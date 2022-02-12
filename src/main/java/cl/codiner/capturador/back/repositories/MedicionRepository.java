package cl.codiner.capturador.back.repositories;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import cl.codiner.capturador.back.models.Medicion;

@Repository
public interface MedicionRepository extends CrudRepository <Medicion,Long>{

	List<Medicion> findAllByFechaBetween(Date start, Date end);
	List<Medicion> findByNroServicio(Integer numeroServicio);
	List<Medicion> findByTomador(Long tomadorId);
}
