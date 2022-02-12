package cl.codiner.capturador.back.controllers;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;




@RestController
public class apiController {

	@GetMapping("/")
	public ResponseEntity<Boolean> isAuthorized() {
		return ResponseEntity.status(HttpStatus.OK).body(true);
	}

	@GetMapping("/confirmToken")
	public ResponseEntity<Boolean> confirmToken() {
		System.out.println("han entrado a corroborar token");
		return ResponseEntity.status(HttpStatus.OK).body(true);
	}
}
