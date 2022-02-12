package cl.codiner.capturador.back.controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cl.codiner.capturador.back.models.Data;
import cl.codiner.capturador.back.models.Medicion;
import cl.codiner.capturador.back.models.Tomador;
import cl.codiner.capturador.back.services.DataService;
import cl.codiner.capturador.back.services.MedicionService;
import cl.codiner.capturador.back.services.TomadorService;

@CrossOrigin(origins = "http://localhost:8100", methods = { RequestMethod.GET, RequestMethod.POST })
@RestController
public class MedicionController {
	@Autowired
	DataService dataService;

	@Autowired
	TomadorService tomadorService;

	@Autowired
	MedicionService medicionService;

	@GetMapping("/medicionbase/getAll")
	public List<Data> getAll() {
		return dataService.getAll();
	}

	@GetMapping("/medicionbase/getAllCount")
	public int getAllCount() {
		List<Data> dataList = dataService.getAll();
		return dataList.size();
	}

	@GetMapping("/mediciones/getAll")
	public List<Medicion> getAllM() {
		return medicionService.getAll();
	}

	@PostMapping("/medicion/add")
	public ResponseEntity<Medicion> addMedicion(@RequestParam("nroServicio") Integer nroServicio,
			@RequestParam("dv") Integer dv, @RequestParam("medidor") Long medidor,
			@RequestParam("lectura") Integer lectura,
			@RequestParam("fecha") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date fecha,
			@RequestParam(value = "motivo", required = false) String motivo, @RequestParam("tomador_id") Long id,
			@RequestParam(value = "longitude", required = false, defaultValue = "null") String longitude,
			@RequestParam(value = "latitude", required = false, defaultValue = "null") String latitude) {

		Tomador tomador = tomadorService.findById(id);
		if (tomador.isAuthorized() == false) {

			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
		} else {
			Medicion medicion = new Medicion();
			medicion.setNroServicio(nroServicio);
			medicion.setDv(dv);
			medicion.setMedidor(medidor);
			medicion.setLectura(lectura);
			medicion.setFecha(fecha);
			medicion.setMotivo(motivo);
			medicion.setTomador(tomador);
			medicion.setLongitude(longitude);
			medicion.setLatitude(latitude);
			Medicion dataSaved = medicionService.save(medicion);

			// ACTUALIZAR LA BASE DE DATOS DE LOS MEDIDORES
			// MODIFICAR PARA VER CUAL DE LOS 2 ANTERIORES MODIFICAR
			Data data = dataService.findByNroServicio(nroServicio);
			Long medidorNumber = medidor % 10;
			if (medidorNumber.equals((long) 1)) {
				data.setAnterior1(lectura);
			} else if (medidorNumber.equals((long) 2)) {
				data.setAnterior2(lectura);
			} else if (medidorNumber.equals((long) 3)) {
				data.setAnterior3(lectura);
			}
			dataService.save(data);

			return ResponseEntity.status(HttpStatus.OK).body(dataSaved);
		}

	}

	@PostMapping("/medicionbase/add")
	public ResponseEntity<Data> addMedicion(@RequestParam("nro_servicio") Integer nro_servicio,
			@RequestParam("dv") Integer dv, @RequestParam("nombre") String nombre,

			@RequestParam("nro_medidor1") Long nro_medidor1,
			@RequestParam(value = "nro_medidor2", required = false, defaultValue = "null") String nro_medidor2,
			@RequestParam(value = "nro_medidor3", required = false, defaultValue = "null") String nro_medidor3,

			@RequestParam("marca_medidor1") String marca_medidor1,
			@RequestParam(value = "marca_medidor2", required = false, defaultValue = "null") String marca_medidor2,
			@RequestParam(value = "marca_medidor3", required = false, defaultValue = "null") String marca_medidor3,

			@RequestParam("enteros1") Integer enteros1,
			@RequestParam(value = "enteros2", required = false, defaultValue = "null") String enteros2,
			@RequestParam(value = "enteros3", required = false, defaultValue = "null") String enteros3,

			@RequestParam("anterior1") Integer anterior1,
			@RequestParam(value = "anterior2", required = false, defaultValue = "null") String anterior2,
			@RequestParam(value = "anterior3", required = false, defaultValue = "null") String anterior3) {

		Data data = new Data();
		data.setNroServicio(nro_servicio);
		data.setDv(dv);
		data.setNombre(nombre);
		data.setNro_medidor1(nro_medidor1);
		data.setMarca_medidor1(marca_medidor1);
		data.setEnteros1(enteros1);
		data.setAnterior1(anterior1);

		/*
		 * System.out.println(nro_medidor1 + " " + nro_medidor2 + " " + nro_medidor3);
		 * System.out.println(marca_medidor1 + " " + marca_medidor2 + " " +
		 * marca_medidor3); System.out.println(enteros1 + " " + enteros2 + " " +
		 * enteros3); System.out.println(anterior1 + " " + anterior2 + " " + anterior3);
		 * 
		 */

		if (!"null".equals(nro_medidor2)) {
			data.setNro_medidor2(Long.valueOf(nro_medidor2));
		}
		if (!"null".equals(enteros2)) {
			data.setEnteros2(Integer.parseInt(enteros2));
		}
		if (!"null".equals(anterior2)) {
			data.setAnterior2(Integer.parseInt(anterior2));
		}
		if (!"null".equals(marca_medidor2)) {
			data.setMarca_medidor2(marca_medidor2);
		}

		if (!"null".equals(nro_medidor3)) {
			data.setNro_medidor3(Long.valueOf(nro_medidor3));
		}
		if (!"null".equals(enteros3)) {
			data.setEnteros3(Integer.parseInt(enteros3));
		}
		if (!"null".equals(anterior3)) {
			data.setAnterior3(Integer.parseInt(anterior3));
		}
		if (!"null".equals(marca_medidor3)) {
			data.setMarca_medidor3(marca_medidor3);
		}

		return ResponseEntity.status(HttpStatus.OK).body(dataService.save(data));
	}

	@DeleteMapping("/medicionbase/deleteAll")
	public boolean deleteAll() {
		dataService.deleteAll();
		return true;
	}

	@PostMapping("/medicion/findByFecha")
	public List<Medicion> findByFechaBetween(@RequestParam("start") String start, @RequestParam("end") String end,
			@RequestParam(value = "successErrorFilter", required = false, defaultValue = "null") String successErrorFilter)
			throws ParseException {
		// LocalDate startDate = LocalDate.parse(start);
		// LocalDate endDate = LocalDate.parse(end);

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date startDate = formatter.parse(start);
		Date endDate = formatter.parse(end);

		if (endDate.before(startDate)) {
			Date aux = startDate;
			startDate = endDate;
			endDate = aux;
		}

		boolean isError;
		if (!"null".equals(successErrorFilter)) {
			isError = Boolean.parseBoolean(successErrorFilter);
		} else {
			return medicionService.findByFechaBetween(startDate, endDate);
		}

		List<Medicion> medicionList = medicionService.findByFechaBetween(startDate, endDate);
		List<Medicion> medicionListToReturn = new ArrayList<Medicion>();
		if (isError) {
			for (int i = 0; i < medicionList.size(); i++) {
				if (!"null".equals(medicionList.get(i).getMotivo())) {
					medicionListToReturn.add(medicionList.get(i));
				}
			}
		} else {
			for (int i = 0; i < medicionList.size(); i++) {
				if ("null".equals(medicionList.get(i).getMotivo())) {
					medicionListToReturn.add(medicionList.get(i));
				}
			}
		}

		return medicionListToReturn;
	}

	@PostMapping("/medicion/getByNumeroServicio")
	public List<Medicion> findByNroServicio(@RequestParam("numeroServicio") Integer numeroServicio,
			@RequestParam(value = "successErrorFilter", required = false, defaultValue = "null") String successErrorFilter) {
		boolean isError;
		if (!"null".equals(successErrorFilter)) {
			isError = Boolean.parseBoolean(successErrorFilter);
		} else {
			return medicionService.findByNroServicio(numeroServicio);
		}
		List<Medicion> medicionList = medicionService.findByNroServicio(numeroServicio);
		List<Medicion> medicionListToReturn = new ArrayList<Medicion>();
		if (isError) {
			for (int i = 0; i < medicionList.size(); i++) {
				if (!"null".equals(medicionList.get(i).getMotivo())) {
					medicionListToReturn.add(medicionList.get(i));
				}
			}
		} else {
			for (int i = 0; i < medicionList.size(); i++) {
				if ("null".equals(medicionList.get(i).getMotivo())) {
					medicionListToReturn.add(medicionList.get(i));
				}
			}
		}

		return medicionListToReturn;
	}

	@PostMapping("/medicion/findByTomador")
	public List<Medicion> findByTomadorId(@RequestParam("tomadorId") Long tomadorId,
			@RequestParam(value = "successErrorFilter", required = false, defaultValue = "null") String successErrorFilter) {

		Tomador tomador = tomadorService.findById(tomadorId);
		boolean isError;
		if (!"null".equals(successErrorFilter)) {
			isError = Boolean.parseBoolean(successErrorFilter);
		} else {
			return tomador.getMediciones();
		}
		List<Medicion> medicionList = tomador.getMediciones();
		List<Medicion> medicionListToReturn = new ArrayList<Medicion>();
		if (isError) {
			for (int i = 0; i < medicionList.size(); i++) {
				if (!"null".equals(medicionList.get(i).getMotivo())) {
					medicionListToReturn.add(medicionList.get(i));
				}
			}
		} else {
			for (int i = 0; i < medicionList.size(); i++) {
				if ("null".equals(medicionList.get(i).getMotivo())) {
					medicionListToReturn.add(medicionList.get(i));
				}
			}
		}

		return medicionListToReturn;

	}

	@PostMapping("/medicion/findByYearAndMonth")
	public List<Medicion> findByYearAndMonth(
			@RequestParam(value = "year", required = false, defaultValue = "0") String yearParam,
			@RequestParam(value = "month", required = false, defaultValue = "0") String monthParam,
			@RequestParam(value = "successErrorFilter", required = false, defaultValue = "null") String successErrorFilter) {
		List<Medicion> medicionList = medicionService.getAll();

		List<Medicion> newMedicionList = new ArrayList<Medicion>();
		String year, month;
		for (int i = 0; i < medicionList.size(); i++) {
			year = medicionList.get(i).getFecha().toString().split("-")[0];
			month = medicionList.get(i).getFecha().toString().split("-")[1];
			if (yearParam.equals(year) && monthParam.equals(month)) {
				newMedicionList.add(medicionList.get(i));
			}
		}

		boolean isError;
		if (!"null".equals(successErrorFilter)) {
			isError = Boolean.parseBoolean(successErrorFilter);
		} else {
			return newMedicionList;
		}

		List<Medicion> medicionListToReturn = new ArrayList<Medicion>();
		if (isError) {
			for (int i = 0; i < newMedicionList.size(); i++) {
				if (!"null".equals(newMedicionList.get(i).getMotivo())) {
					medicionListToReturn.add(newMedicionList.get(i));
				}
			}
		} else {
			for (int i = 0; i < newMedicionList.size(); i++) {
				if ("null".equals(newMedicionList.get(i).getMotivo())) {
					medicionListToReturn.add(newMedicionList.get(i));
				}
			}
		}

		return medicionListToReturn;

	}

	@PostMapping("/medicion/getLetter")
	public String medicionGetLetter(@RequestParam("id") Long id) {
		return tomadorService.findById(id).getLetra();
	}
	
	@DeleteMapping("/deleteUserAndMediciones")
	public void deleteUserAndMediciones(@RequestParam("id") Long id) {
		Tomador tomador = tomadorService.findById(id);
		List<Medicion> mediciones = tomador.getMediciones();
		for(int i = 0 ; i < mediciones.size(); i++) {
			medicionService.deleteById(mediciones.get(i).getId());
		}
		tomadorService.deleteById(id);
	}

}
