package xyz.levell.christmaslist;


import javax.persistence.*;
import java.util.List;


@Entity
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @OneToMany(mappedBy = "person")
    private List<Gift> gifts;

    protected Person() {}

    public Person(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return String.format(
                "Person[id=%d, name='%s', gifts='%s']",
                id, name, gifts);
    }
    public void setName(String name) {
        this.name = name;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public List<Gift> getGifts() { return gifts; }
}


