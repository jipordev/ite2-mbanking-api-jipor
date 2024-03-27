package co.istad.mbanking.domain;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Collate;

import java.time.LocalDateTime;

@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "notifications")
public class Notification {

    @Id
    private Long id;

    private String content;

    @ManyToOne
    private Transaction transactionId;

    @JoinColumn(name = "transactionAt")
    private LocalDateTime transactionAt;

    @ManyToOne
    private Account senderId;

    @ManyToOne
    private Account receiverId;
}
