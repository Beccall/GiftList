package xyz.levell.christmaslist.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import xyz.levell.christmaslist.Entity.Family;
import xyz.levell.christmaslist.Entity.FamilyPerson;
import xyz.levell.christmaslist.Entity.GiftPerson;
import xyz.levell.christmaslist.Entity.Person;
import xyz.levell.christmaslist.Repository.FamilyPersonRepository;
import xyz.levell.christmaslist.Repository.GiftPersonRepository;
import xyz.levell.christmaslist.Repository.PersonRepository;
import xyz.levell.christmaslist.Service.PersonService;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PersonServiceTest {

    @InjectMocks
    private PersonService personService;

    @Mock
    private PersonRepository personRepository;

    @Mock
    private GiftPersonRepository giftPersonRepository;

    @Mock
    private FamilyPersonRepository familyPersonRepository;

    private Person personLoggedIn;

    @BeforeEach
    void setUp() {
        SecurityContextHolder securityContextHolder = new SecurityContextHolder();
        securityContextHolder.setContext(new SecurityContextImpl(new Authentication() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return null;
            }

            @Override
            public Object getCredentials() {
                return null;
            }

            @Override
            public Object getDetails() {
                return null;
            }

            @Override
            public Object getPrincipal() {
                return null;
            }

            @Override
            public boolean isAuthenticated() {
                return false;
            }

            @Override
            public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {

            }

            @Override
            public String getName() {
                return "Becca";
            }
        }));

        personLoggedIn = Person.builder()
                .name("Becca")
                .build();
    }

    @Test
    @DisplayName("should return list of Person that admin has control of")
    void getOwnersByAdminLoggedInTest() {
        Person nollie = Person.builder()
                .name("Nollie")
                .build();
        Person laylee = Person.builder()
                .name("Laylee")
                .build();

        GiftPerson giftPersonNollie = GiftPerson.builder()
                .personAdmin(personLoggedIn)
                .personOwner(nollie)
                .build();
        GiftPerson giftPersonLaylee = GiftPerson.builder()
                .personAdmin(personLoggedIn)
                .personOwner(laylee)
                .build();
        List<GiftPerson> giftPersonList = Arrays.asList(giftPersonNollie, giftPersonLaylee);

        when(giftPersonRepository.findByPersonAdmin(personLoggedIn)).thenReturn(giftPersonList);
        when(personRepository.findByName("Becca")).thenReturn(personLoggedIn);
        List<Person> personOwners = personService.getOwnersByAdminLoggedIn();
        assertTrue(personOwners.contains(nollie));
        assertTrue(personOwners.contains(laylee));
    }

    @Test
    @DisplayName("should return empty list when admin has no person owners")
    void getOwnersByAdminLoggedInTest_NoPersonOwners() {
        Person personAdmin = Person.builder()
                .name("Becca")
                .build();

        when(giftPersonRepository.findByPersonAdmin(personAdmin)).thenReturn(Collections.emptyList());
        when(personRepository.findByName("Becca")).thenReturn(personAdmin);
        List<Person> personOwners = personService.getOwnersByAdminLoggedIn();
        assertTrue(personOwners.isEmpty());
    }

    @Test
    @DisplayName("should return list of families that user is part of")
    void getFamilyByUserTest() {
        Family levell = Family.builder()
                .familyName("Levell")
                .build();
        Family jonesWatkins = Family.builder()
                .familyName("JonesWatkins")
                .build();

        FamilyPerson familyPersonLevell = FamilyPerson.builder()
                .family(levell)
                .person(personLoggedIn)
                .build();
        FamilyPerson familyPersonJonesWatkins = FamilyPerson.builder()
                .family(jonesWatkins)
                .person(personLoggedIn)
                .build();

        when(personRepository.findByName("Becca")).thenReturn(personLoggedIn);
        when(familyPersonRepository.findByPerson(personLoggedIn)).thenReturn(Arrays.asList(familyPersonLevell, familyPersonJonesWatkins));

        List<Family> familyList = personService.getFamilyByUser();
        assertTrue(familyList.contains(levell));
        assertTrue(familyList.contains(jonesWatkins));
    }

    @Test
    @DisplayName("should return an empty list when user has no family")
    void getFamilyByUserTest_NoFamily() {
        when(personRepository.findByName("Becca")).thenReturn(personLoggedIn);
        when(familyPersonRepository.findByPerson(personLoggedIn)).thenReturn(Collections.emptyList());

        List<Family> familyList = personService.getFamilyByUser();
        assertTrue(familyList.isEmpty());
    }

    @Test
    @DisplayName("should return list of all gift owners by family")
    void getOwnersByFamilyTest() {
        Family levell = Family.builder()
                .familyName("Levell")
                .build();
        Family jonesWatkins = Family.builder()
                .familyName("JonesWatkins")
                .build();
        List<Family> families = Arrays.asList(levell, jonesWatkins);

        Person nollie = Person.builder()
                .name("Nollie")
                .build();
        Person laylee = Person.builder()
                .name("Laylee")
                .build();

        GiftPerson giftPersonNollie = GiftPerson.builder()
                .personAdmin(personLoggedIn)
                .personOwner(nollie)
                .build();
        GiftPerson giftPersonLaylee = GiftPerson.builder()
                .personAdmin(personLoggedIn)
                .personOwner(laylee)
                .build();
        GiftPerson giftPersonLaylee2 = GiftPerson.builder()
                .personAdmin(Person.builder()
                        .name("Ryan")
                        .build())
                .personOwner(laylee)
                .build();

        FamilyPerson familyPersonLevell = FamilyPerson.builder()
                .family(levell)
                .person(personLoggedIn)
                .build();
        FamilyPerson familyPersonJonesWatkins = FamilyPerson.builder()
                .family(jonesWatkins)
                .person(personLoggedIn)
                .build();

        List<GiftPerson> giftPersonList = Arrays.asList(giftPersonNollie, giftPersonLaylee, giftPersonLaylee2);

        when(personRepository.findByName("Becca")).thenReturn(personLoggedIn);
        when(giftPersonRepository.findByFamilyIn(families)).thenReturn(giftPersonList);
        when(familyPersonRepository.findByPerson(personLoggedIn)).thenReturn(Arrays.asList(familyPersonLevell, familyPersonJonesWatkins));

        List<Person> giftOwners = personService.getOwnersByFamily();
        assertTrue(giftOwners.contains(nollie));
        assertTrue(giftOwners.contains(laylee));
        assertEquals(2, giftOwners.size());
        assertFalse(giftOwners.contains(personLoggedIn));
    }
}
