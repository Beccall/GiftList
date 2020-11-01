package xyz.levell.christmaslist.Entity;

import javax.persistence.*;

@Entity
public class GiftPerson {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private Person personOwner;

    @ManyToOne
    private Person personAdmin;

    @ManyToOne
    private Family family;

    protected GiftPerson(){}

    public GiftPerson(Person personOwner, Person personAdmin, Family family) {
        this.personOwner = personOwner;
        this.personAdmin = personAdmin;
        this.family = family;
    }

    @Override
    public String toString() {
        return String.format(
                "Family[id=%d, personOwner='%s', personAdmin='%s', family='%s']",
                id, personOwner, personOwner, family);
    }

    public Long getId() { return id; }
    public Person getPersonOwner() { return personOwner; }
    public Person getPersonAdmin() { return personAdmin; }
    public Family getFamily() { return family; }

}
