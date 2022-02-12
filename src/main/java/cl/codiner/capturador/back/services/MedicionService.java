package cl.codiner.capturador.back.services;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.codiner.capturador.back.models.Medicion;
import cl.codiner.capturador.back.repositories.MedicionRepository;

@Service
public class MedicionService {

	@Autowired
	MedicionRepository medicionRepository;

	public Medicion save(Medicion medicion) {
		return medicionRepository.save(medicion);

	}

	public List<Medicion> getAll() {
		return (List<Medicion>) medicionRepository.findAll();
	}
	
	public void deleteAll() {
		medicionRepository.deleteAll();
		return;
	}
	
	public List<Medicion> findByFechaBetween(Date startDate, Date endDate){
		return medicionRepository.findAllByFechaBetween(startDate, endDate);
	}
	
	public List<Medicion> findByNroServicio(Integer numeroServicio){
		return medicionRepository.findByNroServicio(numeroServicio);
	}
	
	public List<Medicion> findByTomador(Long tomadorId){
		return medicionRepository.findByTomador(tomadorId);
	}

	public void deleteById(Long id) {
		medicionRepository.deleteById(id);
	}
 }
