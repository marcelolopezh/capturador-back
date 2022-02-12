package cl.codiner.capturador.back.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.codiner.capturador.back.models.AppUser;
import cl.codiner.capturador.back.repositories.AppUserRepository;

@Service
public class AppUserService {
	@Autowired
	AppUserRepository appUserRep;
	
	public AppUser findByUsername(String username) {
		return appUserRep.findByUsername(username);
	}
	
	public AppUser save(AppUser user) {
		return appUserRep.save(user);
	}
	
	public List<AppUser> getAll(){
		return (List<AppUser>) appUserRep.findAll();
	}

}
