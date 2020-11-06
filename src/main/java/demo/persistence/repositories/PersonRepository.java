package demo.persistence.repositories;

import demo.persistence.models.Person;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonRepository extends PagingAndSortingRepository<Person, Long> {

    List<Person> findByLastName(String lastName);

    List<Person> findAllByDelete(boolean delete, Pageable pageable);
}
