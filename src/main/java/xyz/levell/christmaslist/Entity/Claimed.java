package xyz.levell.christmaslist.Entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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
}
