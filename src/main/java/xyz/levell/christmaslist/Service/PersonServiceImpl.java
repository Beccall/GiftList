package xyz.levell.christmaslist.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import xyz.levell.christmaslist.Entity.Family;
import xyz.levell.christmaslist.Entity.FamilyPerson;
import xyz.levell.christmaslist.Entity.GiftPerson;
import xyz.levell.christmaslist.Entity.Person;
import xyz.levell.christmaslist.Repository.*;

import java.util.ArrayList;
import java.util.List;

@Service
public class PersonServiceImpl implements PersonService{
    private PersonRepository personRepository;
    private GiftRepository giftRepository;
    private GiftPersonRepository giftPersonRepository;
    private FamilyRepository familyRepository;
    private FamilyPersonRepository familyPersonRepository;

    @Autowired
    public PersonServiceImpl(PersonRepository personRepository, GiftRepository giftRepository, GiftPersonRepository giftPersonRepository, FamilyRepository familyRepository, FamilyPersonRepository familyPersonRepository) {
        this.personRepository = personRepository;
        this.giftRepository = giftRepository;
        this.familyPersonRepository = familyPersonRepository;
        this.familyRepository = familyRepository;
        this.giftPersonRepository = giftPersonRepository;

    }


//    Find All Persons in Person database
    public List<Person> getAllPersons() {
        return personRepository.findAll();
    }

//    Find Person by Username Logged in
    public Person getPersonByLoggedIn() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return personRepository.findByName(authentication.getName());
    }

//    Get Person by Name
    public Person getPersonByName(String name) {
        Person person = personRepository.findByName(name);
        return person;
    }

//   Get List of Person by Admin Logged in
    public List<Person> getOwnersByAdminLoggedIn() {
        List<GiftPerson> giftPerson = giftPersonRepository.findByPersonAdmin(getPersonByLoggedIn());
        List<Person> personOwners = new ArrayList<>();
        for (int i = 0; i < giftPerson.size(); i++) {
            personOwners.add(giftPerson.get(i).getPersonOwner());
        }
        return personOwners;
    }

//  Which Family is the User in, returns list of families.
    public List<Family> getFamilyByUser() {
        List<FamilyPerson> familyPerson = familyPersonRepository.findByPerson(getPersonByLoggedIn());
        List<Family> familyList = new ArrayList<>();
        for (int i = 0; i < familyPerson.size(); i++) {
            familyList.add(familyPerson.get(i).getFamily());
        }
        return familyList;
    }
//  list of Person Owners by Family Id --FOR ALL LISTS!
    public List<Person> getOwnersByFamily() {
        List<GiftPerson> giftPersons = giftPersonRepository.findByFamilyIn(getFamilyByUser());
        List<Person> ownersInFamily = new ArrayList<>();
//        for (int i =0; i < family.size(); i++) {
//            giftPersons.addAll(giftPersonRepository.findByFamily(family.get(i)));
//        }
        for (int i = 0; i < giftPersons.size(); i++) {
            if (!ownersInFamily.contains(giftPersons.get(i).getPersonOwner())) {
                ownersInFamily.add(giftPersons.get(i).getPersonOwner());
            }
        }
        return ownersInFamily;
    }




}
