package app.repository;

import app.entity.User;
import app.dto.UserDTO;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    User findByName(String name);
    User findByUsername(String username);
    List<User> findAllByOrderByNameAsc();
    List<User> findAllByRoleOrderByNameAsc(String role);
    Boolean existsByUsername(String username);
}
