package app.controller;

import java.security.Principal;
import java.util.List;
import java.util.ArrayList;

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

import app.entity.User;
import app.dto.UserDTO;

import app.service.UserService;

import app.util.CustomErrorType;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "API REST Usuário")
@RestController
@RequestMapping("/api")
public class UserController {

	@Autowired
	UserService userService;

	@ApiOperation(value = "Lista todos os Usuários")
	@RequestMapping(value = "/user", method = RequestMethod.GET)
	public ResponseEntity<List<User>> listAllUsers() {
		List<User> users = userService.findAllByOrderByNameAsc();
		if (users.isEmpty())
			return new ResponseEntity<List<User>>(HttpStatus.NO_CONTENT);
		
		//Muda valor da senha para não ser mostrada
		users.forEach(user -> user.setPassword(null));

		return new ResponseEntity<List<User>>(users, HttpStatus.OK);
	}
	
	@ApiOperation(value = "Lista todos os Finalizadores")
	@RequestMapping(value = "/finalizador", method = RequestMethod.GET)
	public ResponseEntity<List<UserDTO>> listAllFinalizadores() {
		List<User> users = userService.findAllByRoleOrderByNameAsc("FINAL");
		if (users.isEmpty())
			return new ResponseEntity<List<UserDTO>>(HttpStatus.NO_CONTENT);
		
		//Cria uma nova lista de objetos com apenas os dados necessários do Usuário
		List<UserDTO> userDTO = new ArrayList<UserDTO>();
		for(User user : users) {
			userDTO.add(new UserDTO(user.getId(), user.getName(), user.getUsername(), user.getRole()));
		}

		return new ResponseEntity<List<UserDTO>>(userDTO, HttpStatus.OK);
	}

	@ApiOperation(value = "Pega um Usuário")
	@RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getUser(@PathVariable("id") Long id) {
		User user = userService.findUserById(id);
		if (user == null) {
			return new ResponseEntity<Object>(new CustomErrorType("Usuário com id " + id + " não encontrado."), HttpStatus.NOT_FOUND);
		}

		//Muda valor da senha para não ser mostrada
		user.setPassword(null);
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}

	@ApiOperation(value = "Cria o Usuário")
	@RequestMapping(value = "/user", method = RequestMethod.POST)
	public ResponseEntity<?> createUser(@RequestBody User user, UriComponentsBuilder ucBuilder) {
		if (userService.isUserExist(user.getUsername())) {
			return new ResponseEntity<Object>(new CustomErrorType("Usuário '" + user.getUsername() + "' já existente."), HttpStatus.CONFLICT);
		}

		userService.save(user);
		return new ResponseEntity<User>(user, HttpStatus.CREATED);
	}

	@ApiOperation(value = "Atualiza o Usuário")
	@RequestMapping(value = "/user/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> updateUser(@PathVariable("id") Long id, @RequestBody User user) {
		if (userService.findUserById(id) == null) {
			return new ResponseEntity<Object>(new CustomErrorType("Usuário com id " + id + " não encontrado."), HttpStatus.NOT_FOUND);
		}

		userService.save(user);
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}

	@ApiOperation(value = "Exclui o Usuário")
	@RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteUser(@PathVariable("id") Long id) {
		if (userService.findUserById(id) == null) {
			return new ResponseEntity<Object>(new CustomErrorType("Usuário com id " + id + " não encontrado."), HttpStatus.NOT_FOUND);
		}
		userService.deleteUserById(id);
		return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
	}

	@ApiOperation(value = "Exclui todos os Usuários")
	@RequestMapping(value = "/user", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteAllUsers() {
		userService.deleteAllUsers();
		return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
	}

}
