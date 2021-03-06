package app.controller;

import java.security.Principal;
import java.util.Date;
import java.util.List;

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

import app.entity.Processo;
import app.entity.Parecer;
import app.dto.UserDTO;

import app.service.ParecerService;
import app.service.ProcessoService;
import app.service.UserService;

import app.util.CustomErrorType;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "API REST Parecer")
@RestController
@RequestMapping("/api")
public class ProcessoParecerController {

	@Autowired
	ProcessoService processoService;

	@Autowired
	ParecerService parecerService;
	
	@Autowired
	UserService userService;

	@ApiOperation(value = "Lista todos os Pareceres")
	@RequestMapping(value = "/processo-parecer", method = RequestMethod.GET)
	public ResponseEntity<List<Processo>> listAllProcessoParecer() {
		//Pega o Usuário logado
		UserDTO userDTO = userService.userLogged();

		List<Processo> processo = null;
		if (userDTO != null) { 
			if (userDTO.getRole().equals("ADMIN"))
				processo = processoService.findAllByOrderByTituloAsc();
			else
				processo = processoService.findAllByUsers(userDTO);
		} 		
		return new ResponseEntity<List<Processo>>(processo, HttpStatus.OK);
	}

	@ApiOperation(value = "Cria o Parecer")
	@RequestMapping(value = "/processo-parecer", method = RequestMethod.POST)
	public ResponseEntity<?> createParecer(@RequestBody Processo processo, UriComponentsBuilder ucBuilder) {
		if(processo.getCriador() == null) {
			//Pega o Usuário logado
			UserDTO userDTO = userService.userLogged();

			//Cria um novo Parecer
			Parecer parecer = new Parecer();
			parecer.setProcesso(processo);
			parecer.setUser(userDTO);
			for(Parecer p : processo.getPareceres()) {
				parecer.setComentario(p.getComentario());
			}
			parecerService.save(parecer);
		} else {
			processoService.save(processo);	
		}
		return new ResponseEntity<Processo>(processo, HttpStatus.CREATED);
	}

	@ApiOperation(value = "Atualiza o Parecer")
	@RequestMapping(value = "/processo-parecer/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> updateParecer(@PathVariable("id") Long id, @RequestBody Parecer parecer) {
		Parecer p = parecerService.findParecerById(id);
		if (p == null) {
			return new ResponseEntity<Object>(new CustomErrorType("Parecer com id " + id + " não encontrado."), HttpStatus.NOT_FOUND);
		}

		p.setComentario(parecer.getComentario());
		parecerService.save(p);
		return new ResponseEntity<Parecer>(parecer, HttpStatus.OK);
	}

}
