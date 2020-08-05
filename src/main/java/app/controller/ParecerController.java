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
import app.entity.User;
import app.service.ParecerServiceImpl;
import app.service.UserServiceImpl;
import app.util.CustomErrorType;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "API REST Parecer")
@RestController
@RequestMapping("/api")
public class ParecerController {

	public static final Logger logger = LoggerFactory.getLogger(ParecerController.class);

	@Autowired
	ParecerServiceImpl parecerService;
		
	@Autowired
	UserServiceImpl userService;

	@ApiOperation(value = "Lista todas os Pareceres")
	@RequestMapping(value = "/parecer", method = RequestMethod.GET)
	public ResponseEntity<List<Parecer>> listAllPareceres(Principal principal) {
		String username = (principal == null) ? "user" : principal.getName();
		User user = userService.findByUsername(username);
		
		List<Parecer> pareceres = null;
		if (user.getRole().equals("ADMIN")) {
			pareceres = parecerService.findAllByOrderByComentarioAsc();
		} else {
			pareceres = parecerService.findAllByStatusOrderByComentarioAsc("Ativo");
		}
		
		if (pareceres.isEmpty()) {
			return new ResponseEntity<List<Parecer>>(HttpStatus.NO_CONTENT);
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

	@ApiOperation(value = "Cria o Parecer")
	@RequestMapping(value = "/parecer", method = RequestMethod.POST)
	public ResponseEntity<?> createParecer(@RequestBody Parecer parecer, UriComponentsBuilder ucBuilder) {
		if (parecerService.isParecerExist(parecer)) {
			logger.error("Não é possível criar. Parecer com comentário {} já existe", parecer.getComentario());
			return new ResponseEntity<Object>(
					new CustomErrorType("Não é possível criar. Parecer com comentário " + parecer.getComentario() + " já existe."),
					HttpStatus.CONFLICT);
		}
		
		//Pega os dados do usuário logado
		User user = userService.userLogged();
		
		//Seta o Id do usuário
		parecer.setIduser(user.getId());
		
		//Seta a data da criação
		parecer.setData(new Date());
		
		parecerService.saveParecer(parecer);
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/api/parecer/{id}").buildAndExpand(parecer.getId()).toUri());
		return new ResponseEntity<String>(headers, HttpStatus.CREATED);
	}

	@ApiOperation(value = "Atualiza o Parecer")
	@RequestMapping(value = "/parecer/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> updateParecer(@PathVariable("id") Long id, @RequestBody Parecer parecer) {
		if (parecerService.findParecerById(id) == null) {
			logger.error("Não é possível atualizar. Parecer com id {} não encontrado.", id);
			return new ResponseEntity<Object>(
					new CustomErrorType("Não é possível atualizar. Parecer com id " + id + " não encontrado."),
					HttpStatus.NOT_FOUND);
		}
		
		parecerService.updateParecer(parecer);
		return new ResponseEntity<Parecer>(parecer, HttpStatus.OK);
	}

	@ApiOperation(value = "Exclui o Parecer")
	@RequestMapping(value = "/parecer/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteParecer(@PathVariable("id") Long id) {
		Parecer parecer = parecerService.findParecerById(id);
		if (parecer == null) {
			logger.error("Não é possível excluir. Parecer com id {} não encontrado.", id);
			return new ResponseEntity<Object>(
					new CustomErrorType("Não é possível excluir. Parecer com id " + id + " não encontrado."),
					HttpStatus.NOT_FOUND);
		}
		parecerService.deleteParecerById(id);
		return new ResponseEntity<Parecer>(HttpStatus.NO_CONTENT);
	}

	@ApiOperation(value = "Exclui todos os Pareceres")
	@RequestMapping(value = "/parecer", method = RequestMethod.DELETE)
	public ResponseEntity<Parecer> deleteAllPareceres() {
		parecerService.deleteAllPareceres();
		return new ResponseEntity<Parecer>(HttpStatus.NO_CONTENT);
	}

}
