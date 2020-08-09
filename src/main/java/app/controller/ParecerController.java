package app.controller;

import java.security.Principal;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import app.entity.Parecer;
import app.dto.UserDTO;

import app.service.ParecerService;
import app.service.UserService;

import app.util.CustomErrorType;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "API REST Parecer")
@RestController
@RequestMapping("/api")
public class ParecerController {

	public static final Logger logger = LoggerFactory.getLogger(ParecerController.class);

	@Autowired
	ParecerService parecerService;
	
	@Autowired
	UserService userService;

	@ApiOperation(value = "Lista todos os Pareceres")
	@RequestMapping(value = "/parecer", method = RequestMethod.GET)
	public ResponseEntity<List<Parecer>> listAllPareceres() {
		//Pega o Usuário logado
		UserDTO userDTO = userService.userLogged();

		List<Parecer> pareceres = null;
		if (userDTO != null) { 
			if (userDTO.getRole().equals("ADMIN"))
				pareceres = parecerService.findAllByOrderByProcessoAsc();
			else
				pareceres = parecerService.findAllByUser(userDTO);
		} 		
		return new ResponseEntity<List<Parecer>>(pareceres, HttpStatus.OK);
	}

	@ApiOperation(value = "Pega um Parecer")
	@RequestMapping(value = "/parecer/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getParecer(@PathVariable("id") Long id) {
		Parecer parecer = parecerService.findParecerById(id);
		if (parecer == null) {
			logger.error("Parecer com id {} não encontrada.", id);
			return new ResponseEntity<Object>(new CustomErrorType("Usuário com id " + id + " não encontrado."),
					HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Parecer>(parecer, HttpStatus.OK);
	}

}
