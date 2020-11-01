package xyz.levell.christmaslist.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import xyz.levell.christmaslist.Entity.GiftPerson;
import xyz.levell.christmaslist.Entity.Person;
import xyz.levell.christmaslist.Repository.*;

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
    public Person findLoggedIn() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return personRepository.findByName(authentication.getName());
    }

    public Person getPersonByName(String name) {
        Person person = personRepository.findByName(name);
        return person;
    }
    public Person getPersonById(long personId) {
        Person person = personRepository.findById(personId);
        return person;
    }

    public List<GiftPerson> getOwnersByAdmin(Person personAdmin) {
        return giftPersonRepository.findByPersonAdmin(personAdmin);
    }



}
