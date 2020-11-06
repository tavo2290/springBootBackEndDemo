package demo.persistence.repositories;

import demo.persistence.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    List<User> findByUserName(String userName);

    Optional<User> findByUserName(Optional<String> usrName);

    List<User> findAllByUserNameAndProfile(String userName,String profile);
}
