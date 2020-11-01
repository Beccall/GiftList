package xyz.levell.christmaslist.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import xyz.levell.christmaslist.Entity.Family;
import xyz.levell.christmaslist.Entity.GiftPerson;
import xyz.levell.christmaslist.Entity.Person;

import java.util.List;

@Repository
public interface GiftPersonRepository extends JpaRepository<GiftPerson, Long> {
    GiftPerson findById(long id);
    List<GiftPerson> findByFamily(Family family);
    List<GiftPerson> findByPersonAdmin(Person person);

}
