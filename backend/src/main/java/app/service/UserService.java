package app.service;

import app.entity.User;
import app.dto.UserDTO;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import app.repository.UserRepository;

@Service
public class UserService implements UserDetailsService {

	@Autowired
	private final UserRepository repository;

	@Autowired
	public UserService(UserRepository repository) {
		this.repository = repository;
	}

	public User findUserById(Long id) {
		return repository.findById(id).orElse(new User());
	}

	public List<User> findAllByOrderByNameAsc() {
		return repository.findAllByOrderByNameAsc();
	}
	
	public List<User> findAllByRoleOrderByNameAsc(String role) {
		return repository.findAllByRoleOrderByNameAsc(role);
	}

	public User findByName(String name) {
		return repository.findByName(name);
	}

	public User findByUsername(String username) {
		return repository.findByUsername(username);
	}
	
	public void save(User user) {
		//Criptografa a senha
		encryptPassword(user);

		repository.save(user);
	}

	public void deleteUserById(Long id) {
		repository.deleteById(id);
	}

	public void deleteAllUsers() {
		repository.deleteAll();
	}

	public List<User> findAllUsers() {
		return (List<User>) repository.findAll();
	}

	public boolean isUserExist(String username) {
		return findByUsername(username) != null;
	}

	public User encryptPassword(User user) {
		BCryptPasswordEncoder bc = new BCryptPasswordEncoder();
		String hashPwd = bc.encode(user.getPassword());
		user.setPassword(hashPwd);

		return user;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User curruser = findByUsername(username);

		if(curruser == null || curruser.getStatus().equals("INATIVO")){
	        throw new UsernameNotFoundException("Usuário não encontrado ou sem autorização");
	    }
		
		UserDetails user = new org.springframework.security.core.userdetails.User(username, curruser.getPassword(),
				true, true, true, true, AuthorityUtils.createAuthorityList(curruser.getRole()));

		System.out.println("ROLE: " + curruser.getRole());
		return user;
	}
	
	public UserDTO userLogged() {
		//Pega o usuário logado
		Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
		if(loggedInUser != null) {
			String username = loggedInUser.getName();
			User user = findByUsername(username);
			//Cria um novo objeto com apenas os dados necessários do Usuário
			return new UserDTO(user.getId(), user.getName(), user.getUsername(), user.getRole());
		} else {
			return null;
		}
	}
}
