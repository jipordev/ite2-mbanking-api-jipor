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
@Table(name = "roles")
public class Role {

    @Id
    private Integer id;

    @Column(length = 15, nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "role")
    private List<RoleAuthority> roleAuthorities;

    @OneToMany(mappedBy = "role")
    private List<UserRole> userRoles;
}
