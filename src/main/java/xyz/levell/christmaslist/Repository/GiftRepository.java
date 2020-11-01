package xyz.levell.christmaslist.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import xyz.levell.christmaslist.Entity.Gift;
import xyz.levell.christmaslist.Entity.Person;

import java.util.List;

@Repository
public interface GiftRepository extends JpaRepository<Gift, Long> {
    Gift findById(long id);
    List<Gift> findAllByPerson(Person person);
}
