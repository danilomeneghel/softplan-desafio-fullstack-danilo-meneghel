package app.service;

import app.entity.Parecer;
import app.entity.Processo;
import app.entity.User;
import app.dto.ProcessoParecerDTO;
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

    /*public List<ProcessoParecerDTO> findAllProcessoParecerToUser() {
        //Pega o Usuário logado
		User user = userService.userLogged();
        
        List<Processo> processos = processoRepository.findAllByUser(user);

        List<ProcessoParecerDTO> processoParecerDTO = new ArrayList<>();

        //Verifica se há Parecer para cada Processo associado
        processos.forEach(processo -> {
            Parecer parecer = repository.findParecerByProcessoAndUser(processo.getId(), user.getId());
            if (parecer == null) {
                parecer = new Parecer();
                parecer.setUser(user);
                parecer.setComentario("");
            }
            processoParecer.add(
                processoParecerDTO.builder()
                    .processoId(processo.getId())
                    .titulo(processo.getTitulo())
                    .descricao(processo.getDescricao())
                    .processoCreatedDate(processo.getCreatedAt())
                    .parecerId(parecer.getId())
                    .parecerIduser(parecer.getUser().getId())
                    .parecerCreatedAt(parecer.getCreatedAt())
                    .comentario(parecer.getComentario())
                .build()
            );
        });
        return processoParecer;
    }*/

    public void saveParecer(ProcessoParecerDTO processoParecerDTO) {
        //Pega o Usuário logado
		UserDTO userDTO = userService.userLogged();
        //Pega o Processo associado
        Processo processo = processoRepository.findById(processoParecerDTO.getIdprocesso()).get();
        //Cria um novo Parecer
        Parecer parecer = new Parecer();
        parecer.setProcesso(processo);
        parecer.setUser(userDTO);
        parecer.setComentario(processoParecerDTO.getComentario());
        
        processo.getPareceres().add(parecer);
        processoRepository.save(processo);
    }
    
    public void updateParecer(ProcessoParecerDTO processoParecerDTO) {
        Processo processo = processoRepository.findById(processoParecerDTO.getIdprocesso()).get();

        processo.getPareceres().forEach(parecer -> {
            if (parecer.getId() == processoParecerDTO.getIdparecer()) {
                parecer.setComentario(processoParecerDTO.getComentario());
            }
        });
        processoRepository.save(processo);
    }

    public void deleteParecerById(Long id) {
        repository.deleteById(id);
    }

    public void deleteAllPareceres() {
        repository.deleteAll();
    }

}
