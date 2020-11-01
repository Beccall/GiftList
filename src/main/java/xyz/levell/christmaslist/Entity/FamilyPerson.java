package xyz.levell.christmaslist.Entity;

import javax.persistence.*;

@Entity
public class FamilyPerson {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private Person person;

    @ManyToOne
    private Family family;

    protected FamilyPerson(){}

    public FamilyPerson(Person person, Family family){
        this.person = person;
        this.family = family;
    }

    @Override
    public String toString() {
        return String.format(
                "Family[id=%d, person='%s', family='%s']",
                id, person, family);
    }

    public Long getId() { return id; }
    public Person getPerson() { return person; }
    public Family getFamily() { return family; }
}
