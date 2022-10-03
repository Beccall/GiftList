package xyz.levell.christmaslist.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import xyz.levell.christmaslist.Entity.*;
import xyz.levell.christmaslist.Service.AuthenticationService;
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
@RequiredArgsConstructor
public class GiftController {

    private final GiftService giftService;
    private final PersonService personService;
    private final AuthenticationService authenticationService;

    @GetMapping("/login")
    public String login() {
        if (authenticationService.isAuthenticated()) {
            return "redirect:/";
        }
        return "login";
    }

    @GetMapping("/")
    public String hello(Model model) {
        personService.generatePersonModel(model);

        return "index";
    }

    @GetMapping("/myList/{personName}")
    public String addGift(@PathVariable String personName, Model model){
        personService.generatePersonModel(model);
        model.addAttribute("gifts", giftService.getAllGiftsByPerson(personName));
        model.addAttribute("gift", new Gift());

        return "myList";
    }

    @PostMapping("/myList/{personName}")
    public RedirectView processFormGift(@PathVariable String personName, Gift gift) {
        giftService.addGift(gift, personName);

        return new RedirectView("/myList/{personName}");
    }

    @GetMapping("/delGift/{giftId}/{personName}")
    public RedirectView delGift(@PathVariable Long giftId) {
        giftService.deleteGiftById(giftId);

        return new RedirectView("/myList/{personName}");
    }

    @GetMapping("/{personName}")
    public String allGifts(@PathVariable() String personName, Model model) {
        personService.generatePersonModel(model);
        model.addAttribute("person", personName);
        model.addAttribute("gifts", giftService.getAllGiftsByPerson(personName));

        return "othersList";
    }

    @GetMapping("/claimGift/{personName}/{giftId}")
    public RedirectView claimGift(@PathVariable long giftId) {
        giftService.addGiftClaimed(giftId);

        return new RedirectView("/{personName}");
    }

    @GetMapping("/editGift/{giftId}/{personName}")
    public String editGift(@PathVariable long giftId, @PathVariable String personName, Model model) {
        personService.generatePersonModel(model);
        model.addAttribute("giftToEdit", giftService.findGiftById(giftId));
        model.addAttribute("personName", personName);
        return "editGift";
    }

    @PostMapping("/editGift/{giftId}/{personName}")
    public RedirectView editGift(@PathVariable long giftId, Gift gift) {
        giftService.updateGift(giftId, gift);
        return new RedirectView("/myList/{personName}" );
    }

    @GetMapping("/shoppingList")
    public String shoppingList(Model model) {
        personService.generatePersonModel(model);
        giftService.setClaimedGifts(model);

        return "shoppingList";
    }

    @GetMapping("/remove/{claimId}")
    public RedirectView removeClaim(@PathVariable long claimId) {
        giftService.delClaimed(claimId);

        return new RedirectView("/shoppingList" );
    }
}

