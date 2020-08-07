package app.service;

import app.entity.Processo;
import app.repository.ProcessoRepository;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProcessoServiceImpl {

    @Autowired
    private final ProcessoRepository repository;

    @Autowired
    public ProcessoServiceImpl(ProcessoRepository repository) {
        this.repository = repository;
    }

    public Processo findProcessoById(Long id) {
        return repository.findById(id).orElse(new Processo());
    }

    public List<Processo> findAllByOrderByTituloAsc() {
        return repository.findAllByOrderByTituloAsc();
    }
    
    public List<Processo> findAllByCriador(Long id) {
    	return repository.findAllByCriador(id);
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
