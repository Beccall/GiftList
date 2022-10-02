package xyz.levell.christmaslist.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import xyz.levell.christmaslist.Entity.Claimed;
import xyz.levell.christmaslist.Entity.Gift;
import xyz.levell.christmaslist.Entity.Person;

import java.util.List;

@Repository
public interface ClaimedRepository extends JpaRepository<Claimed, Long> {
    List<Claimed> findByPersonClaimer(Person personClaimer);
    Claimed findByPersonClaimerAndGift(Person personClaimer, Gift gift);
}
