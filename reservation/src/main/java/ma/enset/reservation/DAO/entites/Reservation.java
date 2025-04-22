package ma.enset.reservation.DAO.entites;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate dateReservation;

    @Enumerated(EnumType.STRING)
    private TypeReservation typeReservation;

    private int dureeReservation;
    private double cout;

    @Enumerated(EnumType.STRING)
    private EtatReservation etat;

    @ManyToOne
    @JoinColumn(name = "entreprise_id")
    @ToString.Exclude
    private Entreprise entreprise;
}
