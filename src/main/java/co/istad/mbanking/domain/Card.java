package co.istad.mbanking.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "cards")
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String number;

    @Column(nullable = false)
    private String cvv;

    private String holder;

    private LocalDate issuedAt;

    private LocalDate expiredAt;

    private Boolean isDeleted;

    @ManyToOne
    @JoinColumn(name = "type_id")
    private CardType cardType;

    @OneToOne(mappedBy = "card")
    private Account account;

}
