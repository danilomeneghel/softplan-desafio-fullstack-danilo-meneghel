package app.repository;

import app.entity.Parecer;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProcessoParecerRepository extends CrudRepository<Parecer, Long> {

    Parecer findParecerByProcessoAndUser(Long idprocesso, Long iduser);

}
