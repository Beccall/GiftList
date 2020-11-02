package xyz.levell.christmaslist.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import xyz.levell.christmaslist.Entity.Gift;
import xyz.levell.christmaslist.Repository.*;
import xyz.levell.christmaslist.Entity.Person;

import java.util.List;

@Service
public class GiftServiceImpl implements GiftService {
    private PersonRepository personRepository;
    private GiftRepository giftRepository;
    private GiftPersonRepository giftPersonRepository;
    private FamilyRepository familyRepository;
    private FamilyPersonRepository familyPersonRepository;

    @Autowired
    public GiftServiceImpl(PersonRepository personRepository, GiftRepository giftRepository, GiftPersonRepository giftPersonRepository, FamilyRepository familyRepository, FamilyPersonRepository familyPersonRepository) {
        this.personRepository = personRepository;
        this.giftRepository = giftRepository;
        this.familyPersonRepository = familyPersonRepository;
        this.familyRepository = familyRepository;
        this.giftPersonRepository = giftPersonRepository;

    }
    public Gift findGiftById(long id) {
        return giftRepository.findById(id);
    }


    public Gift addGift(String giftName, String giftUrl, String giftDescription, Person person) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        Person person = personRepository.findByName(authentication.getName());
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

    public List<Gift> getAllGiftsByPerson(Person person) {
        return giftRepository.findAllByPerson(person);
    }


    public void updateGift(long giftId, Gift gift, Person person) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        Person person = personRepository.findByName(authentication.getName());
        Gift newGift = new Gift(gift.getGiftName(), gift.getGiftUrl(), gift.getGiftDescription(), person);
        newGift.setId(giftId);
        giftRepository.save(newGift);
    }


}
