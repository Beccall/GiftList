package xyz.levell.christmaslist.Service;

import org.springframework.stereotype.Service;
import xyz.levell.christmaslist.Entity.GiftPerson;
import xyz.levell.christmaslist.Entity.Person;

import java.util.List;

@Service
public interface PersonService {
    public Person addPerson(String name);
    public List<Person> getAllPersons();
    public Person getPerson();
    public Person findLoggedIn();
    public Person getPersonByName(String name);
    public List<GiftPerson> getOwnersByAdmin(Person personAdmin);
    public Person getPersonById(long personId);

    }
