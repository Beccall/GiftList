package xyz.levell.christmaslist.Entity;

import net.bytebuddy.dynamic.loading.InjectionClassLoader;

import javax.persistence.*;
import java.util.List;

@Entity
public class Claimed {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    private Gift gift;

    @ManyToOne
    private Person personClaimer;

    @ManyToOne
    private Person personOwner;

    public Claimed(){}

    public Claimed(Gift gift, Person personClaimer, Person personOwner){
        this.gift = gift;
        this.personClaimer = personClaimer;
        this.personOwner = personOwner;
    }

    @Override
    public String toString() {
        return String.format(
                "Family[id=%d, gift='%s', personClaimer='%s', personOwner='%s']",
                id, gift, personClaimer, personOwner);
    }

    public Long getId() { return id; }
    public Person getPersonClaimer() { return personClaimer; }
    public Gift getGift() { return gift; }
    public Person getPersonOwner() { return personOwner; }

}
