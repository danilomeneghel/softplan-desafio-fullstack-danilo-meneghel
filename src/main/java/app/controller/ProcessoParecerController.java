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

import app.entity.Processo;
import app.entity.Parecer;
import app.dto.ProcessoParecerDTO;
import app.dto.UserDTO;

import app.service.ProcessoParecerService;
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

	public static final Logger logger = LoggerFactory.getLogger(ProcessoParecerController.class);

	@Autowired
	ProcessoParecerService processoParecerService;

	@Autowired
	ProcessoService processoService;

	@Autowired
	ParecerService parecerService;
	
	@Autowired
	UserService userService;

	@ApiOperation(value = "Lista todos os Pareceres")
	@RequestMapping(value = "/processo-parecer", method = RequestMethod.GET)
	public ResponseEntity<List<Parecer>> listAllProcessoParecer() {
		//Pega o Usuário logado
		UserDTO userDTO = userService.userLogged();

		List<Parecer> parecer = null;
		if (userDTO != null) { 
			if (userDTO.getRole().equals("ADMIN"))
				parecer = parecerService.findAllByOrderByProcessoAsc();
			else
				parecer = parecerService.findAllByUser(userDTO);
		} 		
		return new ResponseEntity<List<Parecer>>(parecer, HttpStatus.OK);
	}

	@ApiOperation(value = "Pega um Parecer")
	@RequestMapping(value = "/processo-parecer/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getParecer(@PathVariable("id") Long id) {
		Parecer parecer = parecerService.findParecerById(id);
		if (parecer == null) {
			logger.error("Parecer com id {} não encontrada.", id);
			return new ResponseEntity<Object>(new CustomErrorType("Usuário com id " + id + " não encontrado."),
					HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Parecer>(parecer, HttpStatus.OK);
	}

	@ApiOperation(value = "Cria o Parecer")
	@RequestMapping(value = "/processo-parecer", method = RequestMethod.POST)
	public ResponseEntity<?> createParecer(@RequestBody ProcessoParecerDTO processoParecerDTO, UriComponentsBuilder ucBuilder) {
		processoParecerService.saveParecer(processoParecerDTO);
		return new ResponseEntity<ProcessoParecerDTO>(processoParecerDTO, HttpStatus.CREATED);
	}

	@ApiOperation(value = "Atualiza o Parecer")
	@RequestMapping(value = "/processo-parecer/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> updateParecer(@PathVariable("id") Long id, @RequestBody ProcessoParecerDTO processoParecerDTO) {
		if (parecerService.findParecerById(id) == null) {
			logger.error("Não é possível atualizar. Parecer com id {} não encontrado.", id);
			return new ResponseEntity<Object>(
					new CustomErrorType("Não é possível atualizar. Parecer com id " + id + " não encontrado."),
					HttpStatus.NOT_FOUND);
		}
		
		processoParecerService.updateParecer(processoParecerDTO);
		return new ResponseEntity<ProcessoParecerDTO>(processoParecerDTO, HttpStatus.OK);
	}

	@ApiOperation(value = "Exclui o Parecer")
	@RequestMapping(value = "/processo-parecer/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteParecer(@PathVariable("id") Long id) {
		if (parecerService.findParecerById(id) == null) {
			logger.error("Não é possível excluir. Parecer com id {} não encontrado.", id);
			return new ResponseEntity<Object>(
					new CustomErrorType("Não é possível excluir. Parecer com id " + id + " não encontrado."),
					HttpStatus.NOT_FOUND);
		}
		parecerService.deleteParecerById(id);
		return new ResponseEntity<ProcessoParecerDTO>(HttpStatus.NO_CONTENT);
	}

	@ApiOperation(value = "Exclui todos os Pareceres")
	@RequestMapping(value = "/processo-parecer", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteAllPareceres() {
		parecerService.deleteAllPareceres();
		return new ResponseEntity<ProcessoParecerDTO>(HttpStatus.NO_CONTENT);
	}

}
