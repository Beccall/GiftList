package xyz.levell.christmaslist.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.levell.christmaslist.Entity.Gift;
import xyz.levell.christmaslist.Entity.Claimed;
import xyz.levell.christmaslist.Repository.*;
import xyz.levell.christmaslist.Entity.Person;

import java.util.ArrayList;
import java.util.List;

@Service
public class GiftServiceImpl implements GiftService {
    private PersonRepository personRepository;
    private GiftRepository giftRepository;
    private GiftPersonRepository giftPersonRepository;
    private FamilyRepository familyRepository;
    private FamilyPersonRepository familyPersonRepository;
    private ClaimedRepository claimedRepository;

    @Autowired
    public GiftServiceImpl(PersonRepository personRepository, GiftRepository giftRepository, GiftPersonRepository giftPersonRepository,
                           FamilyRepository familyRepository, FamilyPersonRepository familyPersonRepository, ClaimedRepository claimedRepository) {
        this.personRepository = personRepository;
        this.giftRepository = giftRepository;
        this.familyPersonRepository = familyPersonRepository;
        this.familyRepository = familyRepository;
        this.giftPersonRepository = giftPersonRepository;
        this.claimedRepository = claimedRepository;

    }
    public Gift findGiftById(long id) {
        return giftRepository.findById(id);
    }


    public Gift addGift(String giftName, String giftUrl, String giftDescription, Person person) {
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
        Gift newGift = new Gift(gift.getGiftName(), gift.getGiftUrl(), gift.getGiftDescription(), person);
        newGift.setId(giftId);
        giftRepository.save(newGift);
    }

    public void addGiftClaimed(Gift gift, Person personClaimer, Person personOwner) {
        Claimed newGiftClaimed = new Claimed(gift, personClaimer, personOwner);
        claimedRepository.save(newGiftClaimed);
    }

    public List<Claimed> findClaimedByPersonClaimer(Person personClaimer) {
        return claimedRepository.findByPersonClaimer(personClaimer);
    }
    public void delClaimed(Long id) {
        claimedRepository.deleteById(id);
    }





}
