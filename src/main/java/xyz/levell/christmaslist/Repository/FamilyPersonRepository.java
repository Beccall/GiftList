package xyz.levell.christmaslist.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import xyz.levell.christmaslist.Entity.FamilyPerson;
import xyz.levell.christmaslist.Entity.Person;

import java.util.List;

@Repository
public interface FamilyPersonRepository extends JpaRepository<FamilyPerson, Long> {
    FamilyPerson findById(long Id);
    List<FamilyPerson> findByPerson(Person person);

}
