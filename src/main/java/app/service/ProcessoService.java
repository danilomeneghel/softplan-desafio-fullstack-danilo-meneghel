package app.service;

import app.entity.Processo;
import app.entity.Parecer;
import app.entity.User;
import app.dto.UserDTO;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.repository.ProcessoRepository;

@Service
public class ProcessoService {

    @Autowired
    private final ProcessoRepository repository;

    @Autowired
    public ProcessoService(ProcessoRepository repository) {
        this.repository = repository;
    }

    public Processo findProcessoById(Long id) {
        return repository.findById(id).orElse(new Processo());
    }

    public List<Processo> findAllByUsers(UserDTO userDTO) {
    	return repository.findAllByUsers(userDTO);
    }

    public List<Processo> findAllByOrderByTituloAsc() {
        return repository.findAllByOrderByTituloAsc();
    }
    
    public List<Processo> findAllByCriador(UserDTO userDTO) {
    	return repository.findAllByCriador(userDTO);
    }

    public Processo findByTitulo(String titulo) {
        return repository.findByTitulo(titulo);
    }

    public void saveProcesso(Processo processo) {
        repository.save(processo);
    }

    public void updateProcesso(Processo processo) {
        repository.save(processo);
    }

    public void deleteProcessoById(Long id) {
        repository.deleteById(id);
    }

    public void deleteAllProcessos() {
        repository.deleteAll();
    }

    public boolean isProcessoExist(Processo processo) {
        return findByTitulo(processo.getTitulo()) != null;
    }

}
