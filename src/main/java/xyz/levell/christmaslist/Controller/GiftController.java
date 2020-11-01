package xyz.levell.christmaslist.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import xyz.levell.christmaslist.Entity.Gift;
import xyz.levell.christmaslist.Entity.Person;
import xyz.levell.christmaslist.Service.GiftService;
import xyz.levell.christmaslist.Service.PersonService;


/**
 * In Terminal run 'ifconfig'.
 *
 * Find 'inet 192.168.1.XX'.
 *
 * Go to 192.168.1.XX:8080 in browser.
 */
@Controller
public class GiftController {

    private GiftService giftService;
    private PersonService personService;

    @Autowired
    public GiftController(GiftService giftService, PersonService personService) {
        this.giftService = giftService;
        this.personService = personService;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/")
    public String hello() {
        return "index";
    }

    @GetMapping("/myList")
    public String addGift(Model model){
        Person person = personService.findLoggedIn();
        model.addAttribute("gifts", giftService.getAllGiftsByPerson(person));
        Gift gift = new Gift();
        gift.setPerson(new Person());
        model.addAttribute("gift", gift);
        return "myList";
    }

    @PostMapping("/myList")
    public RedirectView processFormGift(Gift gift, Model model) {
        giftService.addGift(gift.getGiftName(), gift.getGiftUrl(), gift.getGiftDescription());
        return new RedirectView("/myList");
    }

    @GetMapping("/delGift/{giftId}")
    public RedirectView delGift(@PathVariable Long giftId) {
        giftService.delGift(giftId);
        return new RedirectView("/myList");

    }

    @GetMapping("/{personName}")
    public String myGifts(@PathVariable() String personName, Model model) {
        Person person = personService.getPersonByName(personName);model.addAttribute("gifts", giftService.getAllGiftsByPerson(person));
        model.addAttribute("person", personName);
        model.addAttribute("persons", personService.getAllPersons());
        return "othersList";
    }

    @GetMapping("/editGift/{giftId}")
    public String editGift(@PathVariable long giftId, Model model) {
        model.addAttribute("giftToEdit", giftService.findGiftById(giftId));
        return "editGift";
    }

    @PostMapping("/editGift/{giftId}")
    public RedirectView editGift(Gift gift, @PathVariable long giftId) {
        giftService.updateGift(giftId, gift);
        return new RedirectView("/myList");
    }

}

