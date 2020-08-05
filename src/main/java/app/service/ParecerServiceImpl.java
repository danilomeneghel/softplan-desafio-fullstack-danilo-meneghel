package app.service;

import app.entity.Parecer;
import app.repository.ParecerRepository;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ParecerServiceImpl {

    @Autowired
    private final ParecerRepository repository;

    @Autowired
    public ParecerServiceImpl(ParecerRepository repository) {
        this.repository = repository;
    }

    public Parecer findParecerById(Long id) {
        return repository.findById(id).orElse(new Parecer());
    }

    public List<Parecer> findAllByOrderByComentarioAsc() {
        return repository.findAllByOrderByComentarioAsc();
    }
    
    public List<Parecer> findAllByStatusOrderByComentarioAsc(String status) {
    	return repository.findAllByStatusOrderByComentarioAsc(status);
    }

    public Parecer findByComentario(String comentario) {
        return repository.findByComentario(comentario);
    }

    public void saveParecer(Parecer parecer) {
        repository.save(parecer);
    }

    public void updateParecer(Parecer parecer) {
        repository.save(parecer);
    }

    public void deleteParecerById(Long id) {
        repository.deleteById(id);
    }

    public void deleteAllPareceres() {
        repository.deleteAll();
    }

    public boolean isParecerExist(Parecer parecer) {
        return findByComentario(parecer.getComentario()) != null;
    }

}
