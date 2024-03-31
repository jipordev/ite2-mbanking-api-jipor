package co.istad.mbanking.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "authorities")
public class Authority {

    @Id
    private Long id;

    @Column(length = 15, nullable = false, unique = true)
    private String name;

    @ManyToMany(mappedBy = "authorities")
    private List<Role> roles;

}
