package app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

    @RequestMapping("/")
    public String index() {
        return "index";
    }

    @RequestMapping("/login")
    public String login() {
        return "login";
    }
    
    @RequestMapping("/processos")
    public String processos() {
        return "processos";
    }
    
    @RequestMapping("/processoForm")
    public String processoForm() {
        return "processoForm";
    }
    
    @RequestMapping("/pareceres")
    public String pareceres() {
    	return "pareceres";
    }
    
    @RequestMapping("/users")
    public String users(ModelMap modal) {
        return "users";
    }

    @RequestMapping("/partials/{page}")
    public String partialHandler(@PathVariable("page") final String page) {
        return page;
    }

}
