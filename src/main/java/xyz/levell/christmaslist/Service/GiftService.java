package xyz.levell.christmaslist.Service;

import org.springframework.stereotype.Service;
import xyz.levell.christmaslist.Entity.Gift;
import xyz.levell.christmaslist.Entity.Person;

import java.util.List;

@Service
public interface GiftService {
    public Gift addGift(String giftName, String giftUrl, String giftDescription);
    public void delGift(Long id);
    public List<Gift> getAllGiftsByPerson(Person loggedIn);
    public Gift findGiftById(long id);
    public void updateGift(long giftId, Gift gift);
    }
