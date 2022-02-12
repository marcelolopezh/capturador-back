package cl.codiner.capturador.back.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.codiner.capturador.back.models.Tomador;
import cl.codiner.capturador.back.repositories.TomadorRepository;

@Service
public class TomadorService {
	@Autowired
	TomadorRepository tomadorRepository;
	
	public List<Tomador> getAll(){
		return (List<Tomador>) tomadorRepository.findAll();
	}

	public Tomador findByNombreAndApellido(String nombre, String apellido) {
		return tomadorRepository.findByNombreAndApellido(nombre, apellido);
	}

	public Tomador save(Tomador tomador) {
		return tomadorRepository.save(tomador);
	}
	
	public Tomador findById(Long id) {
		return tomadorRepository.findById(id).orElse(null);
	}
	
	public void deleteById(Long id) {
		tomadorRepository.deleteById(id);
	}
}
