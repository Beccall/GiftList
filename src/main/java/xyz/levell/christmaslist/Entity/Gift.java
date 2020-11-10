package xyz.levell.christmaslist.Entity;

import javax.persistence.*;
import java.util.List;

@Entity
public class Gift {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String giftName;
    @Column(length = 1000)
    private String giftUrl;
    private String giftDescription;
    @ManyToOne
    private Person person;

    @OneToMany(mappedBy = "gift")
    private List<Claimed> claimed;


    public Gift() {}

    public Gift(String giftName, String giftUrl, String giftDescription, Person person) {
        this.giftName = giftName;
        this.giftUrl = giftUrl;
        this.giftDescription = giftDescription;
        this.person = person;

    }

    public void setClaimed(List<Claimed> claimed) {
        this.claimed = claimed;
    }

    public boolean isClaimedBy(String userName) {
        for(Claimed c : claimed) {
            if(c.getPersonClaimer().getName().equals(userName)) {
                return true;
            }
        }
        return false;
    }

    public void setId(Long id) {
        this.id = id;
    }


    @Override
    public String toString() {
        return "Gift{" +
                "id=" + id +
                ", giftName='" + giftName + '\'' +
                ", giftUrl='" + giftUrl + '\'' +
                ", giftDescription='" + giftDescription + '\'' +
                ", person=" + person +
                ", claimed=" + claimed +
                '}';
    }

    public void setGiftName(String giftName) {
        this.giftName = giftName;
    }

    public void setGiftUrl(String giftUrl) {
        this.giftUrl = giftUrl;
    }

    public void setGiftDescription(String giftDescription) {
        this.giftDescription = giftDescription;
    }

    public void setPerson(Person person) { this.person = person;}
    public Long getId() { return id; }
    public String getGiftName() { return giftName; }
    public String getGiftUrl() { return giftUrl; }
    public String getGiftDescription() { return giftDescription; }
    public Person getPerson() { return person; }
    public List<Claimed> getClaimed() { return claimed; }
}
