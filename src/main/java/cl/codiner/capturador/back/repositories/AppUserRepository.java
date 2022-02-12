package cl.codiner.capturador.back.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import antlr.collections.List;
import cl.codiner.capturador.back.models.AppUser;

@Repository
public interface AppUserRepository extends CrudRepository<AppUser,Long> {
	AppUser findByUsername(String username);

}
