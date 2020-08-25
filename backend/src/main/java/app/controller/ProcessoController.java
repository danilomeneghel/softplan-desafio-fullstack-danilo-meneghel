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
import app.dto.UserDTO;

import app.service.ProcessoService;
import app.service.UserService;

import app.util.CustomErrorType;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "API REST Processo")
@RestController
@RequestMapping("/api")
public class ProcessoController {

	@Autowired
	ProcessoService processoService;
	
	@Autowired
	UserService userService;

	@ApiOperation(value = "Lista todos os Processos")
	@RequestMapping(value = "/processo", method = RequestMethod.GET)
	public ResponseEntity<List<Processo>> listAllProcessos(Principal principal) {
		//Pega o Usuário logado
		UserDTO userDTO = userService.userLogged();

		List<Processo> processos = null;
		if(userDTO != null) {
			if (userDTO.getRole().equals("ADMIN")) 
				processos = processoService.findAllByOrderByTituloAsc();
			else
				processos = processoService.findAllByCriador(userDTO);
		} 
		return new ResponseEntity<List<Processo>>(processos, HttpStatus.OK);
	}

	@ApiOperation(value = "Pega um Processo")
	@RequestMapping(value = "/processo/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getProcesso(@PathVariable("id") Long id) {
		Processo processo = processoService.findProcessoById(id);
		if (processo == null) {
			return new ResponseEntity<Object>(new CustomErrorType("Processo com id " + id + " não encontrado."), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Processo>(processo, HttpStatus.OK);
	}

	@ApiOperation(value = "Cria o Processo")
	@RequestMapping(value = "/processo", method = RequestMethod.POST)
	public ResponseEntity<?> createProcesso(@RequestBody Processo processo, UriComponentsBuilder ucBuilder) {
		if (processoService.isProcessoExist(processo)) {
			return new ResponseEntity<Object>(new CustomErrorType("Processo com titulo " + processo.getTitulo() + " já existe."), HttpStatus.CONFLICT);
		}
		
		//Pega o Usuário logado
		UserDTO userDTO = userService.userLogged();
		//Seta o Usuário logado
		processo.setCriador(userDTO);
		
		processoService.save(processo);		
		return new ResponseEntity<Processo>(processo, HttpStatus.CREATED);
	}

	@ApiOperation(value = "Atualiza o Processo")
	@RequestMapping(value = "/processo/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> updateProcesso(@PathVariable("id") Long id, @RequestBody Processo processo) {
		if (processoService.findProcessoById(id) == null) {
			return new ResponseEntity<Object>(new CustomErrorType("Processo com id " + id + " não encontrado."), HttpStatus.NOT_FOUND);
		}
		
		//Pega o Usuário logado
		UserDTO userDTO = userService.userLogged();
		//Seta o Usuário logado
		processo.setCriador(userDTO);
		
		processoService.save(processo);
		return new ResponseEntity<Processo>(processo, HttpStatus.OK);
	}

	@ApiOperation(value = "Exclui o Processo")
	@RequestMapping(value = "/processo/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteProcesso(@PathVariable("id") Long id) {
		if (processoService.findProcessoById(id) == null) {
			return new ResponseEntity<Object>(new CustomErrorType("Processo com id " + id + " não encontrado."), HttpStatus.NOT_FOUND);
		}
		processoService.deleteProcessoById(id);
		return new ResponseEntity<Processo>(HttpStatus.NO_CONTENT);
	}

}
