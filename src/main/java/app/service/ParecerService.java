package app.service;

import app.entity.Parecer;
import app.entity.User;
import app.dto.UserDTO;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.repository.ParecerRepository;

@Service
public class ParecerService {

    @Autowired
    private final ParecerRepository repository;

    @Autowired
    public ParecerService(ParecerRepository repository) {
        this.repository = repository;
    }

    public Parecer findParecerById(Long id) {
        return repository.findById(id).orElse(new Parecer());
    }
    
    public List<Parecer> findAllByOrderByProcessoAsc() {
    	return repository.findAllByOrderByProcessoAsc();
    }
    
    public List<Parecer> findAllByUser(UserDTO userDTO) {
    	return repository.findAllByUser(userDTO);
    }

    public Parecer findByComentario(String comentario) {
        return repository.findByComentario(comentario);
    }

    public void saveParecer(Parecer parecer) {
        repository.save(parecer);
    }

    public void updateParecer(Parecer parecer) {
        Parecer update = findParecerById(parecer.getId());
        update.setComentario(parecer.getComentario());

        repository.save(update);
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