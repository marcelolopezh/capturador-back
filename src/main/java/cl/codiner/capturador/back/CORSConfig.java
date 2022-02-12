package cl.codiner.capturador.back;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CORSConfig {
	// .allowedOrigins("http://localhost:8081")
	// .allowedOrigins("http://localhost:8100")
	@Bean
	public WebMvcConfigurer CORSConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**")
						.allowedOrigins("capacitor://localhost","ionic://localhost","http://localhost","http://localhost:8080","http://localhost:8100", "http://localhost:8081")
						.allowedHeaders("*").allowCredentials(true).allowedMethods("GET", "POST", "PUT", "DELETE");

			}
		};
	}

}