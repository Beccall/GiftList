package xyz.levell.christmaslist.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import xyz.levell.christmaslist.Entity.*;
import xyz.levell.christmaslist.Service.GiftService;
import xyz.levell.christmaslist.Service.PersonService;

import java.util.ArrayList;
import java.util.List;


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
    public String login(Model model) {
        List<Person> ownersInFamily = personService.getOwnersByFamily();
        model.addAttribute("ownersInFamily", ownersInFamily);

        return "login";
    }

    @GetMapping("/")
    public String hello(Model model) {
        model.addAttribute("adminOwners", personService.getOwnersByAdminLoggedIn());
        model.addAttribute("ownersInFamily", personService.getOwnersByFamily());
        model.addAttribute("personLoggedIn", personService.getPersonByLoggedIn());
        return "index";
    }

    @GetMapping("/myList/{personName}")
    public String addGift(@PathVariable String personName, Model model){
        model.addAttribute("adminOwners", personService.getOwnersByAdminLoggedIn());
        model.addAttribute("ownersInFamily", personService.getOwnersByFamily());
        model.addAttribute("personLoggedIn", personService.getPersonByLoggedIn());
        Person person = personService.getPersonByName(personName);
        model.addAttribute("gifts", giftService.getAllGiftsByPerson(person));
        Gift gift = new Gift();
        gift.setPerson(new Person());
        model.addAttribute("gift", gift);
        return "myList";

    }

    @PostMapping("/myList/{personName}")
    public RedirectView processFormGift(@PathVariable String personName, Gift gift, Model model) {
        model.addAttribute("adminOwners", personService.getOwnersByAdminLoggedIn());
        model.addAttribute("ownersInFamily", personService.getOwnersByFamily());
        model.addAttribute("personLoggedIn", personService.getPersonByLoggedIn());
        model.addAttribute("personName", personName);
        Person person = personService.getPersonByName(personName);
        giftService.addGift(gift.getGiftName(), gift.getGiftUrl(), gift.getGiftDescription(), person);
        return new RedirectView("/myList/{personName}");
    }

    @GetMapping("/delGift/{giftId}/{personName}")
    public RedirectView delGift(@PathVariable Long giftId, Model model) {
        model.addAttribute("adminOwners", personService.getOwnersByAdminLoggedIn());
        model.addAttribute("ownersInFamily", personService.getOwnersByFamily());
        model.addAttribute("personLoggedIn", personService.getPersonByLoggedIn());
        giftService.delGift(giftId);
        return new RedirectView("/myList/{personName}");

    }

    @GetMapping("/{personName}")
    public String allGifts(@PathVariable() String personName, Model model) {
        model.addAttribute("adminOwners", personService.getOwnersByAdminLoggedIn());
        model.addAttribute("ownersInFamily", personService.getOwnersByFamily());
        model.addAttribute("personLoggedIn", personService.getPersonByLoggedIn());
        Person personOwner = personService.getPersonByName(personName);
        model.addAttribute("person", personName);
        model.addAttribute("gifts", giftService.getAllGiftsByPerson(personOwner));
        return "othersList";
    }

    @GetMapping("/claimGift/{personName}/{giftId}")
    public RedirectView claimGift(@PathVariable long giftId, @PathVariable String personName, Model model) {
        model.addAttribute("adminOwners", personService.getOwnersByAdminLoggedIn());
        model.addAttribute("ownersInFamily", personService.getOwnersByFamily());
        model.addAttribute("personLoggedIn", personService.getPersonByLoggedIn());
        giftService.addGiftClaimed(giftService.findGiftById(giftId), personService.getPersonByLoggedIn(), personService.getPersonByName(personName));
        return new RedirectView("/{personName}");
    }

    @GetMapping("/editGift/{giftId}/{personName}")
    public String editGift(@PathVariable long giftId, @PathVariable String personName,  Model model) {
        model.addAttribute("adminOwners", personService.getOwnersByAdminLoggedIn());
        model.addAttribute("ownersInFamily", personService.getOwnersByFamily());
        model.addAttribute("personLoggedIn", personService.getPersonByLoggedIn());
        model.addAttribute("giftToEdit", giftService.findGiftById(giftId));
        model.addAttribute("personName", personName);
        return "editGift";
    }

    @PostMapping("/editGift/{giftId}/{personName}")
    public RedirectView editGift(@PathVariable String personName, @PathVariable long giftId, Gift gift, Model model) {
        model.addAttribute("adminOwners", personService.getOwnersByAdminLoggedIn());
        model.addAttribute("ownersInFamily", personService.getOwnersByFamily());
        model.addAttribute("personLoggedIn", personService.getPersonByLoggedIn());
        Person person = personService.getPersonByName(personName);
        model.addAttribute("personName", personName);
        giftService.updateGift(giftId, gift, person);
        return new RedirectView("/myList/{personName}" );
    }

    @GetMapping("/shoppingList")
    public String shoppingList(Model model) {
        model.addAttribute("adminOwners", personService.getOwnersByAdminLoggedIn());
        model.addAttribute("ownersInFamily", personService.getOwnersByFamily());
        model.addAttribute("personLoggedIn", personService.getPersonByLoggedIn());

        model.addAttribute("claimed", giftService.findClaimedByPersonClaimer(personService.getPersonByLoggedIn()));
        return "shoppingList";
    }

    @GetMapping("/remove/{claimId}")
    public RedirectView removeClaim(@PathVariable long claimId, Model model) {
        model.addAttribute("adminOwners", personService.getOwnersByAdminLoggedIn());
        model.addAttribute("ownersInFamily", personService.getOwnersByFamily());
        model.addAttribute("personLoggedIn", personService.getPersonByLoggedIn());
        giftService.delClaimed(claimId);
        return new RedirectView("/shoppingList" );

    }
}

