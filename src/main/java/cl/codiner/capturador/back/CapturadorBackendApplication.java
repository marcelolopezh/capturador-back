package cl.codiner.capturador.back;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import cl.codiner.capturador.back.models.AppUser;
import cl.codiner.capturador.back.models.Tomador;
import cl.codiner.capturador.back.services.AppUserService;
import cl.codiner.capturador.back.services.TomadorService;
import cl.codiner.capturador.security.JWTAuthorizationFilter;

@SpringBootApplication
public class CapturadorBackendApplication {
	@Autowired
	AppUserService appUserService;
	@Autowired
	TomadorService tomadorService;

	public static void main(String[] args) {
		SpringApplication.run(CapturadorBackendApplication.class, args);

	}

	@Bean
	public void createAdmin() {
		AppUser user = new AppUser();
		user.setId((long) 1);
		user.setUsername("codinerAdmin");
		String hashed = BCrypt.hashpw("codinerAdmin1", BCrypt.gensalt());
		user.setPassword(hashed);
		appUserService.save(user);
	}

	@EnableWebSecurity
	@Configuration
	class WebSecurityConfig extends WebSecurityConfigurerAdapter {

		@Override
		protected void configure(HttpSecurity http) throws Exception {

			http.csrf().disable()
					.addFilterAfter(new JWTAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
					.authorizeRequests().antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
					.antMatchers(HttpMethod.GET, "/").permitAll().antMatchers(HttpMethod.POST, "/login").permitAll()
					.antMatchers(HttpMethod.POST, "/appuser/admin/login").permitAll()
					//.antMatchers(HttpMethod.POST, "/medicionbase/add").permitAll()
					.antMatchers(HttpMethod.GET, "/medicionbase/getAllCount").permitAll()
					//.antMatchers(HttpMethod.POST, "/deleteUserById").permitAll()
					.antMatchers(HttpMethod.GET, "/mediciones/getAll").permitAll()
					//.antMatchers(HttpMethod.DELETE, "/medicionbase/deleteAll").permitAll()
					.anyRequest().authenticated();
			http.cors().and();
		}
	}

}
