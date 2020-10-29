package xyz.levell.christmaslist;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface GiftService {
    public Person addPerson(String name);
    public List<Person> getAllPersons();
    public Gift addGift(String giftName, String giftUrl, String giftDescription);
    public void delGift(Long id);
    public Person getPerson();
    public List<Gift> getAllGiftsByPerson(Person loggedIn);
    public Person getPersonByName(String name);
    public Person findLoggedIn();
    public Gift findGiftById(long id);
    public void updateGift(long giftId, Gift gift);

    }
