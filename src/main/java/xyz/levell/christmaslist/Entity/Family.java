package xyz.levell.christmaslist.Entity;

import javax.persistence.*;

@Entity
public class Family {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String familyName;

    public Family() {}

    public Family(String familyName){
        this.familyName = familyName;
    }
    @Override
    public String toString() {
        return String.format(
                "Family[id=%d, familyName='%s']",
                id, familyName);
    }

    public Long getId() { return id; }
    public String getFamilyName() { return familyName; }

}


