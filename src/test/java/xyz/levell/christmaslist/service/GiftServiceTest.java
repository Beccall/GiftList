package xyz.levell.christmaslist.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.ui.Model;
import xyz.levell.christmaslist.Entity.Claimed;
import xyz.levell.christmaslist.Entity.Gift;
import xyz.levell.christmaslist.Entity.Person;
import xyz.levell.christmaslist.Repository.ClaimedRepository;
import xyz.levell.christmaslist.Repository.GiftRepository;
import xyz.levell.christmaslist.Repository.PersonRepository;
import xyz.levell.christmaslist.Service.AuthenticationService;
import xyz.levell.christmaslist.Service.GiftService;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GiftServiceTest {
    @InjectMocks
    private GiftService giftService;

    @Mock
    private GiftRepository giftRepository;

    @Mock
    private PersonRepository personRepository;

    @Mock
    private ClaimedRepository claimedRepository;

    @Spy
    private AuthenticationService authenticationService;

    @Mock
    private Model model;

    @Captor
    private ArgumentCaptor<Gift> giftArgumentCaptor;

    @Captor
    private ArgumentCaptor<Claimed> claimedArgumentCaptor;

    @Captor
    private ArgumentCaptor<List<Claimed>> listClaimedArgumentCaptor;



    private Gift gift;
    private Person person;

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

        person = Person.builder()
                .name("Becca")
                .gifts(Collections.emptyList())
                .build();

        gift = Gift.builder()
                .id(1L)
                .giftName("toy")
                .giftDescription("cool toy")
                .giftUrl("https://www.target.com")
                .build();
    }

    @Test
    @DisplayName("should save Gift record")
    void addGiftTest() {
        when(personRepository.findByName("Becca")).thenReturn(person);

        giftService.addGift(gift, "Becca");

        verify(giftRepository).save(giftArgumentCaptor.capture());

        Gift capturedGift = giftArgumentCaptor.getValue();
        assertEquals(gift.getGiftName(), capturedGift.getGiftName());
        assertEquals(gift.getGiftUrl(), capturedGift.getGiftUrl());
        assertEquals(gift.getGiftDescription(), capturedGift.getGiftDescription());
        assertEquals(person, capturedGift.getPerson());
    }

    @Test
    @DisplayName("should add https to beginning of url")
    void addGiftTest_WithoutHttpsURL() {
        when(personRepository.findByName("Becca")).thenReturn(person);

        gift.setGiftUrl("target.com");

        giftService.addGift(gift, "Becca");
        verify(giftRepository).save(giftArgumentCaptor.capture());

        Gift capturedGift = giftArgumentCaptor.getValue();
        assertEquals("https://target.com", capturedGift.getGiftUrl());
    }

    @Test
    @DisplayName("should remove any characters before http in gift url")
    void addGiftTest_HTTPSNotAtBeginngin() {
        when(personRepository.findByName("Becca")).thenReturn(person);

        gift.setGiftUrl("Cool toy with lasers https://www.target.com");

        giftService.addGift(gift, "Becca");

        verify(giftRepository).save(giftArgumentCaptor.capture());

        Gift capturedGift = giftArgumentCaptor.getValue();
        assertEquals("https://www.target.com", capturedGift.getGiftUrl());
    }

    @Test
    @DisplayName("should update gift with new values")
    void updateGiftTest() {
        gift.setPerson(person);
        when(giftRepository.findById(1)).thenReturn(gift);

        Gift updatedGift = Gift.builder()
                .id(1L)
                .giftName("cool laser toys")
                .giftUrl("https://www.abc.com")
                .giftDescription("blue or green only")
                .person(person)
                .build();
        giftService.updateGift(1, updatedGift);

        verify(giftRepository).save(giftArgumentCaptor.capture());

        Gift capturedGift = giftArgumentCaptor.getValue();
        assertEquals(updatedGift.getGiftName(), capturedGift.getGiftName());
        assertEquals(updatedGift.getGiftUrl(), capturedGift.getGiftUrl());
        assertEquals(updatedGift.getGiftDescription(), capturedGift.getGiftDescription());
        assertEquals(person, capturedGift.getPerson());
    }

    @Test
    @DisplayName("should create create a new record")
    void updateGiftTest_recordNull() {
        when(giftRepository.findById(1)).thenReturn(null);

        Gift updatedGift = Gift.builder()
                .id(1L)
                .giftName("cool laser toys")
                .giftUrl("https://www.abc.com")
                .giftDescription("blue or green only")
                .person(person)
                .build();
        giftService.updateGift(1, updatedGift);

        verify(giftRepository).save(giftArgumentCaptor.capture());

        Gift capturedGift = giftArgumentCaptor.getValue();
        assertEquals(updatedGift.getGiftName(), capturedGift.getGiftName());
        assertEquals(updatedGift.getGiftUrl(), capturedGift.getGiftUrl());
        assertEquals(updatedGift.getGiftDescription(), capturedGift.getGiftDescription());
        assertEquals(person, capturedGift.getPerson());
    }

    @Test
    @DisplayName("should return list of gifts for person")
    void getAllGiftsByPerson() {
        when(personRepository.findByName("Becca")).thenReturn(person);
        List<Gift> gifts = Collections.singletonList(gift);
        when(giftRepository.findAllByPerson(person)).thenReturn(gifts);

        List<Gift> result = giftService.getAllGiftsByPerson("Becca");
        assertEquals(gifts, result);
    }

    @Test
    @DisplayName("should return an empty list when none are found")
    void getAllGiftsByPerson_giftsEmpty() {
        when(personRepository.findByName("Becca")).thenReturn(person);
        when(giftRepository.findAllByPerson(person)).thenReturn(Collections.emptyList());

        List<Gift> result = giftService.getAllGiftsByPerson("Becca");
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    @DisplayName("should claim gift if not already claimed")
    void addGiftClaimedTest() {
        when(giftRepository.findById(1L)).thenReturn(gift);
        when(personRepository.findByName("Becca")).thenReturn(person);
        when(claimedRepository.findByPersonClaimerAndGift(person, gift)).thenReturn(null);

        giftService.addGiftClaimed(1L);
        verify(claimedRepository).save(claimedArgumentCaptor.capture());

        Claimed claimedGift = claimedArgumentCaptor.getValue();
        assertEquals(person, claimedGift.getPersonClaimer());
        assertEquals(gift, claimedGift.getGift());
    }

    @Test
    @DisplayName("should not claimed gift if already claimed by user")
    void addGiftClaimedTest_alreadyClaimedByUser() {
        when(giftRepository.findById(1L)).thenReturn(gift);
        when(personRepository.findByName("Becca")).thenReturn(person);
        when(claimedRepository.findByPersonClaimerAndGift(person, gift)).thenReturn(new Claimed());

        giftService.addGiftClaimed(1L);
        verify(claimedRepository, never()).save(any(Claimed.class));
    }

    @Test
    @DisplayName("should add claimed entry to model with all claimed by user")
    void getClaimedGifts() {
        Claimed claimed = Claimed.builder()
                .personClaimer(person)
                .gift(gift)
                .build();
        when(personRepository.findByName("Becca")).thenReturn(person);
        when(claimedRepository.findByPersonClaimer(person)).thenReturn(Collections.singletonList(claimed));
        giftService.setClaimedGifts(model);
        verify(model).addAttribute(eq("claimed"), listClaimedArgumentCaptor.capture());
        assertEquals(Collections.singletonList(claimed), listClaimedArgumentCaptor.getValue());
    }
}
