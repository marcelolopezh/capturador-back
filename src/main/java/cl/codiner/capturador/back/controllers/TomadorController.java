package cl.codiner.capturador.back.controllers;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cl.codiner.capturador.back.models.Tomador;
import cl.codiner.capturador.back.services.TomadorService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@RestController
public class TomadorController {
	@Autowired
	TomadorService tomadorService;

	// CON ESTAS VARIABLES ARMO EL username y pwd
	@PostMapping("/login")
	private ResponseEntity<Tomador> login(@RequestParam("nombre") String nombre,
			@RequestParam("apellido") String apellido,
			@RequestParam("fecha") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fecha,
			@RequestParam("password") String password) {
		nombre = nombre.toLowerCase();
		apellido = apellido.toLowerCase();
		Tomador tomadorFindDB = tomadorService.findByNombreAndApellido(nombre, apellido);
		if (tomadorFindDB == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}
		boolean flag = false;
		if (BCrypt.checkpw(password, tomadorFindDB.getPassword())) {
			flag = true;
		}
		if (flag && tomadorFindDB.isAuthorized()) {
			String token = getJWTToken(nombre + apellido + fecha);
			tomadorFindDB.setToken(token);
			tomadorService.save(tomadorFindDB);
			return ResponseEntity.status(HttpStatus.OK).body(tomadorFindDB);
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}
	}

	private String getJWTToken(String username) {
		String secretKey = "bullDog";
		List<GrantedAuthority> grantedAuthorities = AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER");

		String token = Jwts.builder().setId(UUID.randomUUID().toString()).setSubject(username)
				.claim("authorities",
						grantedAuthorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 2073600000)) // 24 dias 2073600000
				.signWith(SignatureAlgorithm.HS512, secretKey.getBytes()).compact();

		return "Bearer " + token;
	}

	@PostMapping("/register/tomador")
	private ResponseEntity<Tomador> registerTomador(@RequestParam("nombre") String nombre,
			@RequestParam("apellido") String apellido, @RequestParam("fecha") String fecha,
			@RequestParam("password") String password, @RequestParam("letra") String letra) {
		nombre = nombre.toLowerCase();
		apellido = apellido.toLowerCase();
		letra = letra.toUpperCase();

		Tomador isTomadorRegistered = tomadorService.findByNombreAndApellido(nombre, apellido);
		if (isTomadorRegistered != null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}

		if (nombre != "" && apellido != "" && fecha != "" && password != "" && letra != "") {
			Tomador tomador = new Tomador();
			tomador.setNombre(nombre);
			tomador.setApellido(apellido);
			String token = getJWTToken(nombre + apellido + fecha);
			tomador.setToken(token);
			String hashed = BCrypt.hashpw(password, BCrypt.gensalt());
			tomador.setPassword(hashed);
			tomador.setLetra(letra);
			tomador.setAuthorized(true);
			Tomador tomadorDB = tomadorService.save(tomador);
			return ResponseEntity.status(HttpStatus.OK).body(tomadorDB);
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}
	}

	@GetMapping("/tomador/getAll")
	private List<Tomador> getAll() {
		return tomadorService.getAll();
	}

	@PutMapping("/update/authorized/tomador")
	private List<Tomador> updateAuthorized(@RequestParam("tomador_id") Long id) {
		Tomador tomador = tomadorService.findById(id);
		tomador.setAuthorized(!tomador.isAuthorized());
		tomadorService.save(tomador);
		return tomadorService.getAll();
	}

	@PostMapping("/deleteUserById")
	private void deleteUserById(@RequestParam("id") Long id) {
		tomadorService.deleteById(id);
		return;
	}

	@PutMapping("/editTomador")
	private ResponseEntity<Tomador> editTomador(@RequestParam("id") Long id, @RequestParam("newNombre") String nombre,
			@RequestParam("newApellido") String apellido, @RequestParam("newPassword") String password, @RequestParam("newLetra") String letra) {
		System.out.println(id+" "+nombre+" "+apellido+" "+password);
		
		if (nombre == null || "".equals(nombre)  || apellido == null || "".equals(apellido))
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		Tomador tomador = tomadorService.findById(id);
		if (tomador == null)
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		tomador.setNombre(nombre.toLowerCase());
		tomador.setApellido(apellido.toLowerCase());
		
		// EDITAR PASSWORD SOLO SI NO ES NULA
		if(!"".equals(password) && password != null && !"null".equals(password)) {
			String hashed = BCrypt.hashpw(password, BCrypt.gensalt());
			tomador.setPassword(hashed);
			System.out.println("Se ha realizado un cambio de password");
		}
		
		tomador.setLetra(letra.toUpperCase());
		tomador = tomadorService.save(tomador);
		return ResponseEntity.status(HttpStatus.OK).body(tomador);
	}

}
