package app.repository;

import app.entity.Processo;
import app.entity.User;
import app.dto.UserDTO;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProcessoRepository extends CrudRepository<Processo, Long> {

    Processo findByTitulo(String titulo);
    List<Processo> findAllByOrderByTituloAsc();
    List<Processo> findAllByCriador(UserDTO userDTO);

}
