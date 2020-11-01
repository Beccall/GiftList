package xyz.levell.christmaslist.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import xyz.levell.christmaslist.Entity.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
    Person findByName(String name);
    Person findById(long id);
}
