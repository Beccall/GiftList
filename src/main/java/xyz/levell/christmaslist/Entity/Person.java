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
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @OneToMany(mappedBy = "person")
    private List<Gift> gifts;
}


