package xyz.levell.christmaslist.Entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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
}
