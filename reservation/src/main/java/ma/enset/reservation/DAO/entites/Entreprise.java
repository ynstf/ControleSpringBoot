package ma.enset.reservation.DAO.entites;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Entreprise {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;
    private String email;
    private String domaineActivite;
    private String username;

    @OneToMany(mappedBy = "entreprise", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Reservation> reservations;
}





