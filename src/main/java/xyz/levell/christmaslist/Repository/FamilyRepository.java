package xyz.levell.christmaslist.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import xyz.levell.christmaslist.Entity.Family;

@Repository
public interface FamilyRepository extends JpaRepository<Family, Long> {
    Family findById(long Id);
    Family findByFamilyName(String familyName);
}
