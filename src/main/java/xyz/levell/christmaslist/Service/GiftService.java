package xyz.levell.christmaslist.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import xyz.levell.christmaslist.Entity.Claimed;
import xyz.levell.christmaslist.Entity.Gift;
import xyz.levell.christmaslist.Entity.Person;
import xyz.levell.christmaslist.Repository.ClaimedRepository;
import xyz.levell.christmaslist.Repository.GiftRepository;
import xyz.levell.christmaslist.Repository.PersonRepository;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class GiftService {
    private final GiftRepository giftRepository;
    private final ClaimedRepository claimedRepository;
    private final PersonRepository personRepository;
    private final AuthenticationService authenticationService;

    public Gift findGiftById(long id) {
        return giftRepository.findById(id);
    }

    public void addGift(Gift gift, String personName) {
        Person person = personRepository.findByName(personName);
        gift.setPerson(person);
        gift.setGiftUrl(validateUrl(gift.getGiftUrl()));

        giftRepository.save(gift);
        log.info("Successfully created record for '{}'", gift.getGiftName());
    }

    public void deleteGiftById(long id) {
        Gift gift = giftRepository.findById(id);
        claimedRepository.findAllByGift(gift).stream()
                .forEach(claimed -> claimedRepository.deleteById(claimed.getId()));

        giftRepository.deleteById(id);
        log.info("Successfully deleted Gift record with id {}", id);
    }

    public List<Gift> getAllGiftsByPerson(String personName) {
        Person person = personRepository.findByName(personName);
        List<Gift> gifts = giftRepository.findAllByPerson(person);
        if (gifts.isEmpty()) {
            log.info("User {} does not have any gifts", person.getName());
        }
        return gifts;
    }

    public void updateGift(long giftId, Gift updatedGift) {
        Gift originalGift = giftRepository.findById(giftId);
        String updatedUrl = updatedGift.getGiftUrl();

        if(originalGift == null) {
            log.warn("Unable to find record for gift '{}'. Creating new record", updatedGift.getGiftName());
            updatedGift.setGiftUrl(validateUrl(updatedUrl));
            giftRepository.save(updatedGift);
            return;
        }
        originalGift.setGiftName(updatedGift.getGiftName());
        originalGift.setGiftDescription(updatedGift.getGiftDescription());
        originalGift.setGiftUrl(validateUrl(updatedGift.getGiftUrl()));

        log.info("Updating gift with record id {}", giftId);
        giftRepository.save(originalGift);
    }

    public void addGiftClaimed(long giftId) {
        Gift gift = findGiftById(giftId);

        String userName = authenticationService.getUserName();
        Person personClaimer = personRepository.findByName(userName);

        Claimed claimed= claimedRepository.findByPersonClaimerAndGift(personClaimer, gift);
        if(claimed != null) {
            log.warn("User {} has already claimed this gift", userName);
            return;
        }

        Claimed newGiftClaimed = Claimed.builder()
                .gift(gift)
                .personClaimer(personClaimer)
                .build();
        claimedRepository.save(newGiftClaimed);
    }

    public void setClaimedGifts(Model model) {
        Person user = personRepository.findByName(authenticationService.getUserName());
        List<Claimed> claimed = claimedRepository.findByPersonClaimer(user);
        model.addAttribute("claimed", claimed);
    }

    public void delClaimed(Long id) {
        claimedRepository.deleteById(id);
    }

    private String validateUrl(String giftUrl) {
        if (StringUtils.hasLength(giftUrl) && !giftUrl.toLowerCase().contains("http")) {
            log.info("Adding https:// to url '{}'", giftUrl);
            return "https://" + giftUrl;
        }

        if(StringUtils.hasLength(giftUrl) && !StringUtils.startsWithIgnoreCase(giftUrl, "http")) {
            log.info("Removing extra characteres from url '{}'", giftUrl);
            return giftUrl.substring(giftUrl.indexOf("http"));
        }
        return giftUrl;
    }


}
