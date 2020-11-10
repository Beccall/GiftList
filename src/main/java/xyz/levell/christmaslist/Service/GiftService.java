package xyz.levell.christmaslist.Service;

import org.springframework.stereotype.Service;
import xyz.levell.christmaslist.Entity.Claimed;
import xyz.levell.christmaslist.Entity.Gift;
import xyz.levell.christmaslist.Entity.Person;

import java.util.List;

@Service
public interface GiftService {
    public Gift addGift(String giftName, String giftUrl, String giftDescription, Person person);
    public void delGift(Long id);
    public List<Gift> getAllGiftsByPerson(Person loggedIn);
    public Gift findGiftById(long id);
    public void updateGift(long giftId, Gift gift, Person person);
    public void addGiftClaimed(Gift gift, Person personClaimer, Person personOwner);
    public List<Claimed> findClaimedByPersonClaimer(Person personClaimer);
    public void delClaimed(Long id);
    }
