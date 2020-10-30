package xyz.levell.christmaslist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GiftServiceImpl implements GiftService {
    private PersonRepository personRepository;
    private GiftRepository giftRepository;

    @Autowired
    public GiftServiceImpl(PersonRepository personRepository, GiftRepository giftRepository) {
        this.personRepository = personRepository;
        this.giftRepository = giftRepository;

    }
    public Gift findGiftById(long id) {
        return giftRepository.findById(id);
    }

    public Person addPerson(String name) {
        Person person = new Person(name);
        return personRepository.save(person);
    }

    public List<Person> getAllPersons() {
        return personRepository.findAll();
    }
    public Person getPerson() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Person person = personRepository.findByName(authentication.getName());
        return person;
    }

    public Gift addGift(String giftName, String giftUrl, String giftDescription) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Person person = personRepository.findByName(authentication.getName());
        if (!giftUrl.isEmpty()) {
            if (!giftUrl.contains("http")) {
                giftUrl = "https://" + giftUrl;
            }
        }
        Gift gift = new Gift(giftName, giftUrl, giftDescription, person);
        return giftRepository.save(gift);
    }

    public void delGift(Long id) {
        giftRepository.deleteById(id);
    }

    public Person findLoggedIn() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return personRepository.findByName(authentication.getName());
    }

    public List<Gift> getAllGiftsByPerson(Person person) {
        return giftRepository.findAllByPerson(person);
    }

    public Person getPersonByName(String name) {
        Person person = personRepository.findByName(name);
        return person;
    }

    public void updateGift(long giftId, Gift gift) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Person person = personRepository.findByName(authentication.getName());
        Gift newGift = new Gift(gift.getGiftName(), gift.getGiftUrl(), gift.getGiftDescription(), person);
        newGift.setId(giftId);
        giftRepository.save(newGift);
    }



}
