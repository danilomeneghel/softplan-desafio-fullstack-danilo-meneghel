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
import app.entity.User;
import app.service.ProcessoServiceImpl;
import app.service.UserServiceImpl;
import app.util.CustomErrorType;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "API REST Processo")
@RestController
@RequestMapping("/api")
public class ProcessoController {

	public static final Logger logger = LoggerFactory.getLogger(ProcessoController.class);

	@Autowired
	ProcessoServiceImpl processoService;
		
	@Autowired
	UserServiceImpl userService;

	@ApiOperation(value = "Lista todos os Processos")
	@RequestMapping(value = "/processo", method = RequestMethod.GET)
	public ResponseEntity<List<Processo>> listAllProcessos(Principal principal) {
		String username = (principal == null) ? "user" : principal.getName();
		User user = userService.findByUsername(username);
		
		List<Processo> processos = null;
		if (user.getRole().equals("ADMIN")) {
			processos = processoService.findAllByOrderByTituloAsc();
		} else {
			processos = processoService.findAllByUserId(user.getId());
		}
		
		if (processos.isEmpty()) {
			return new ResponseEntity<List<Processo>>(HttpStatus.NO_CONTENT);
		}
		
		return new ResponseEntity<List<Processo>>(processos, HttpStatus.OK);
	}

	@ApiOperation(value = "Pega um Processo")
	@RequestMapping(value = "/processo/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getProcesso(@PathVariable("id") Long id) {
		Processo processo = processoService.findProcessoById(id);
		if (processo == null) {
			logger.error("Processo com id {} não encontrada.", id);
			return new ResponseEntity<Object>(new CustomErrorType("Usuário com id " + id + " não encontrado."),
					HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Processo>(processo, HttpStatus.OK);
	}

	@ApiOperation(value = "Cria o Processo")
	@RequestMapping(value = "/processo", method = RequestMethod.POST)
	public ResponseEntity<?> createProcesso(@RequestBody Processo processo, UriComponentsBuilder ucBuilder) {
		if (processoService.isProcessoExist(processo)) {
			logger.error("Não é possível criar. Processo com titulo {} já existe", processo.getTitulo());
			return new ResponseEntity<Object>(
					new CustomErrorType("Não é possível criar. Processo com titulo " + processo.getTitulo() + " já existe."),
					HttpStatus.CONFLICT);
		}
		
		//Pega os dados do usuário logado
		User user = userService.userLogged();
		
		//Seta o Usuário
		processo.setUser(user);
			
		processoService.saveProcesso(processo);
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/api/processo/{id}").buildAndExpand(processo.getId()).toUri());
		return new ResponseEntity<String>(headers, HttpStatus.CREATED);
	}

	@ApiOperation(value = "Atualiza o Processo")
	@RequestMapping(value = "/processo/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> updateProcesso(@PathVariable("id") Long id, @RequestBody Processo processo) {
		if (processoService.findProcessoById(id) == null) {
			logger.error("Não é possível atualizar. Processo com id {} não encontrado.", id);
			return new ResponseEntity<Object>(
					new CustomErrorType("Não é possível atualizar. Processo com id " + id + " não encontrado."),
					HttpStatus.NOT_FOUND);
		}
		
		processoService.updateProcesso(processo);
		return new ResponseEntity<Processo>(processo, HttpStatus.OK);
	}

	@ApiOperation(value = "Exclui o Processo")
	@RequestMapping(value = "/processo/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteProcesso(@PathVariable("id") Long id) {
		Processo processo = processoService.findProcessoById(id);
		if (processo == null) {
			logger.error("Não é possível excluir. Processo com id {} não encontrado.", id);
			return new ResponseEntity<Object>(
					new CustomErrorType("Não é possível excluir. Processo com id " + id + " não encontrado."),
					HttpStatus.NOT_FOUND);
		}
		processoService.deleteProcessoById(id);
		return new ResponseEntity<Processo>(HttpStatus.NO_CONTENT);
	}

	@ApiOperation(value = "Exclui todos os Processos")
	@RequestMapping(value = "/processo", method = RequestMethod.DELETE)
	public ResponseEntity<Processo> deleteAllProcessos() {
		processoService.deleteAllProcessos();
		return new ResponseEntity<Processo>(HttpStatus.NO_CONTENT);
	}

}
