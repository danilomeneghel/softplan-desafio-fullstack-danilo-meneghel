package app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

import app.entity.User;
import app.service.UserService;
import java.security.Principal;

@Controller
public class CadastroController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "cadastro")
    public String novoCadastro(Model model) {
        model.addAttribute("cadastro", new User());
        return "cadastro";
    }

    @RequestMapping(value = "perfil")
    public String editarCadastro(Principal principal, Model model) {
        model.addAttribute("cadastro", userService.findByUsername(principal.getName()));
        return "perfil";
    }

    @RequestMapping(value = "salvarCadastro", method = RequestMethod.POST)
    public String salvarCadastro(@Valid @ModelAttribute("cadastro") User user, BindingResult bindingResult, RedirectAttributes redirAttrs) {
		//Verifica se o nome de usuário já existe
		if (userService.isUserExist(user.getUsername())) { 
			bindingResult.rejectValue("username", "error.userexists", "Usuário já existente");
		}

		//Valida o formulário e verifica se as senhas são iguais
		if (!bindingResult.hasErrors() && user.getPassword().equals(user.getPasswordCheck())) { 		
			user.setRole("TRIAD");
			user.setStatus("ATIVO");
			userService.save(user);
			redirAttrs.addFlashAttribute("message", "Usuário cadastrado com sucesso!");
			return "redirect:/login";
		} else {
			bindingResult.rejectValue("passwordCheck", "error.pwdmatch", "Senhas não são iguais");
		}
		return "cadastro";
	}

	@RequestMapping(value = "salvarPerfil", method = RequestMethod.POST)
    public String salvarPerfil(@Valid @ModelAttribute("cadastro") User user, BindingResult bindingResult, Model model) {
		//Valida o formulário
		if (!bindingResult.hasErrors()) { 
			User currentUser = userService.findByUsername(user.getUsername());
			user.setRole(currentUser.getRole());
			user.setStatus(currentUser.getStatus());
			user.setCreatedAt(currentUser.getCreatedAt());
			userService.save(user);
			model.addAttribute("message", "Usuário atualizado com sucesso!");
		}
		return "perfil";
	}
}
