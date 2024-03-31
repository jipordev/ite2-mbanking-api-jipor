package co.istad.mbanking.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String uuid;

    @Column(unique = true, nullable = false)
    private String nationalCardId;

    @Column(nullable = false)
    private Integer pin;  // Store 4-digit

    @Column(nullable = false, length = 30)
    private String phoneNumber;

    @Column(nullable = false)
    private String password;

    @Column(length = 50)
    private String name;

    private String profileImage;

    @Column(length = 8)
    private String gender;

    private LocalDate dob;

    @Column(length = 100)
    private String cityOrProvince; //u

    @Column(length = 100)
    private String khanOrDistrict; //u

    @Column(length = 100)
    private String sangkatOrCommune; //u

    @Column(length = 100)
    private String village;

    @Column(length = 100)
    private String street;

    @Column(length = 100)
    private String employeeType; //u

    @Column(length = 100)
    private String position; //u

    @Column(length = 100)
    private String companyName; //u

    @Column(length = 100)
    private String mainSourceOfIncome; //u

    private BigDecimal monthlyIncomeRange; //u

    @Column(unique = true)
    private String oneSignalId;

    @Column(unique = true)
    private String studentIdCard;

    @OneToMany(mappedBy = "user")
    private List<UserAccount> userAccountList;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private List<Role> roles;

    private Boolean isDeleted; // manage delete status (admin want to disable or remove an account)
    private Boolean isBlocked; // manage block status (when there is bad action happened)

    private LocalDateTime createdAt;
}
