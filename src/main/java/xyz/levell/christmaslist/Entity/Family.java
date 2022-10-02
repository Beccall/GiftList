package xyz.levell.christmaslist.Entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Family {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String familyName;

}


