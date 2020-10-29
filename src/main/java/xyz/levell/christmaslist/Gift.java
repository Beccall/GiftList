package xyz.levell.christmaslist;

import javax.persistence.*;

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


    protected Gift() {}

    public Gift(String giftName, String giftUrl, String giftDescription, Person person) {
        this.giftName = giftName;
        this.giftUrl = giftUrl;
        this.giftDescription = giftDescription;
        this.person = person;

    }

//    public Gift(long id, String giftName, String giftUrl, String giftDescription, Person person) {
//        this(giftName, giftUrl, giftDescription, person);
//        this.id = id;
//    }


    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return String.format(
                "Person[id=%d, giftName='%s', giftUrl='%s', giftDescription='%s', person='%d']",
                id, giftName, giftUrl, giftDescription, person.getId());
    }

    public void setPerson(Person person) { this.person = person;}
    public Long getId() { return id; }
    public String getGiftName() { return giftName; }
    public String getGiftUrl() { return giftUrl; }
    public String getGiftDescription() { return giftDescription; }
    public Person getPerson() { return person; }
}
