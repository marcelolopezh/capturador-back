package cl.codiner.capturador.back.controllers;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cl.codiner.capturador.back.models.AppUser;
import cl.codiner.capturador.back.services.AppUserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
@RestController
public class AppUserController {
	@Autowired
	AppUserService appUserServ;

	@PostMapping("/appuser/admin/login")
	private ResponseEntity<String> login(@RequestParam("username") String username,
			@RequestParam("password") String password, @RequestParam("fecha") String fecha) {

		AppUser appUser = appUserServ.findByUsername(username);
		if (appUser == null)
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		if (!BCrypt.checkpw(password, appUser.getPassword()))
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		else {
			String token = getJWTToken(username + fecha + Math.random());
			return ResponseEntity.status(HttpStatus.OK).body(token);
		}

	}

	@PostMapping("/appuser/admin/register")
	private ResponseEntity<AppUser> registerUserApp(@RequestParam("username") String username,
			@RequestParam("password") String password) {
		AppUser _appUserDB = appUserServ.findByUsername(username);
		if(_appUserDB==null) {
			if (username != "" && password != "" && username != null && password != null) {
				AppUser appUser = new AppUser();
				appUser.setUsername(username);
				String hashed = BCrypt.hashpw(password, BCrypt.gensalt());
				appUser.setPassword(hashed);
				AppUser appUserDB = appUserServ.save(appUser);
				return ResponseEntity.status(HttpStatus.OK).body(appUserDB);
			} else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
			}
		}else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}
		
	}

	@GetMapping("/appuser/getAll")
	private List<AppUser> getAllUsersApp(){
		return appUserServ.getAll();
	}

	private String getJWTToken(String username) {
		String secretKey = "bullDog";
		List<GrantedAuthority> grantedAuthorities = AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER");

		String token = Jwts.builder().setId(UUID.randomUUID().toString()).setSubject(username)
				.claim("authorities",
						grantedAuthorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 43200000)) // 12 horas 43200000
				.signWith(SignatureAlgorithm.HS512, secretKey.getBytes()).compact();

		return "Bearer " + token;
	}

}
