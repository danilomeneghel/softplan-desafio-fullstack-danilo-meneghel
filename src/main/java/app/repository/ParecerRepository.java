package app.repository;

import app.entity.Parecer;
import app.entity.User;
import app.dto.UserDTO;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParecerRepository extends CrudRepository<Parecer, Long> {

    List<Parecer> findAllByOrderByProcessoAsc();
    List<Parecer> findAllByUser(UserDTO userDTO);

}