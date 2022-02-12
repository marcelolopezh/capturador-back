package cl.codiner.capturador.back.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.codiner.capturador.back.models.Data;
import cl.codiner.capturador.back.repositories.DataRepository;

@Service
public class DataService {
	@Autowired
	DataRepository dataRepository;
	
	public Data save(Data data) {
		return dataRepository.save(data);
		
	}
	
	public List<Data> getAll(){
		return (List<Data>) dataRepository.findAll();
	}

	public Data findByNroServicio(Integer nro_servicio) {
		if(dataRepository.findByNroServicio(nro_servicio)==null) return null;
		else return dataRepository.findByNroServicio(nro_servicio);
	}
	
	public void deleteAll() {
		dataRepository.deleteAll();
		return;
	}
}
