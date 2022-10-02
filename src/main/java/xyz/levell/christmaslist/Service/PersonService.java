package xyz.levell.christmaslist.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import xyz.levell.christmaslist.Entity.Family;
import xyz.levell.christmaslist.Entity.FamilyPerson;
import xyz.levell.christmaslist.Entity.GiftPerson;
import xyz.levell.christmaslist.Entity.Person;
import xyz.levell.christmaslist.Repository.FamilyPersonRepository;
import xyz.levell.christmaslist.Repository.GiftPersonRepository;
import xyz.levell.christmaslist.Repository.PersonRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class PersonService {
    private final PersonRepository personRepository;
    private final GiftPersonRepository giftPersonRepository;
    private final FamilyPersonRepository familyPersonRepository;
    private final AuthenticationService authenticationService;

    public Person getPersonByName(String name) {
        return personRepository.findByName(name);
    }

    public List<Person> getOwnersByAdminLoggedIn() {
        Person person = getPersonByName(authenticationService.getUserName());
        return giftPersonRepository.findByPersonAdmin(person)
                .stream()
                .map(GiftPerson::getPersonOwner)
                .collect(Collectors.toList());
    }

    public List<Family> getFamilyByUser() {
        Person person = getPersonByName(authenticationService.getUserName());
        return familyPersonRepository.findByPerson(person)
                .stream()
                .map(FamilyPerson::getFamily)
                .collect(Collectors.toList());
    }

    public List<Person> getOwnersByFamily() {
        return giftPersonRepository.findByFamilyIn(getFamilyByUser())
                .stream().map(GiftPerson::getPersonOwner)
                .distinct()
                .collect(Collectors.toList());
    }

    public void generatePersonModel(Model model) {
        model.addAttribute("adminOwners", getOwnersByAdminLoggedIn());
        model.addAttribute("ownersInFamily", getOwnersByFamily());
        model.addAttribute("personLoggedIn", getPersonByName(authenticationService.getUserName()));
    }
}
