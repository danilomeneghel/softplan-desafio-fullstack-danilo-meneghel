package app.service;

import app.entity.Parecer;
import app.entity.Processo;
import app.entity.User;
import app.dto.UserDTO;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.service.UserService;

import app.repository.ProcessoParecerRepository;
import app.repository.ProcessoRepository;

@Service
public class ProcessoParecerService {

    @Autowired
    private final ProcessoParecerRepository repository;
    private final ProcessoRepository processoRepository;
    private final UserService userService;

    @Autowired
    public ProcessoParecerService(ProcessoParecerRepository repository, ProcessoRepository processoRepository, UserService userService) {
        this.repository = repository;
        this.processoRepository = processoRepository;
        this.userService = userService;
    }

    public void saveParecer(Processo processo) {
        //Pega o Usuário logado
		UserDTO userDTO = userService.userLogged();
        //Cria um novo Parecer
        Parecer parecer = new Parecer();
        parecer.setProcesso(processo);
        parecer.setUser(userDTO);
        for(Parecer p : processo.getPareceres()) {
            parecer.setComentario(p.getComentario());
        }
        
        processo.getPareceres().add(parecer);
        processoRepository.save(processo);
    }
    
    public void updateParecer(Processo processo) {
        processoRepository.save(processo);
    }

    public void deleteParecerById(Long id) {
        repository.deleteById(id);
    }

    public void deleteAllPareceres() {
        repository.deleteAll();
    }

}
