package xyz.levell.christmaslist.Entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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

    public boolean isClaimedBy(String userName) {
        for(Claimed c : claimed) {
            if(c.getPersonClaimer().getName().equals(userName)) {
                return true;
            }
        }
        return false;
    }
}
