package app.repository;

import app.entity.Parecer;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParecerRepository extends CrudRepository<Parecer, Long> {

    Parecer findByComentario(String comentario);
    List<Parecer> findAllByOrderByComentarioAsc();
    List<Parecer> findAllByStatusOrderByComentarioAsc(String status);

}
