package xyz.levell.christmaslist.Service;

import org.springframework.stereotype.Service;
import xyz.levell.christmaslist.Entity.Family;
import xyz.levell.christmaslist.Entity.GiftPerson;
import xyz.levell.christmaslist.Entity.Person;

import java.util.List;

@Service
public interface PersonService {
    public List<Person> getAllPersons();
    public Person getPersonByLoggedIn();
    public Person getPersonByName(String name);
    public List<Person> getOwnersByAdminLoggedIn();
    public List<Family> getFamilyByUser();
    public List<Person> getOwnersByFamily();
    }
