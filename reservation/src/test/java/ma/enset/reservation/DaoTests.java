package ma.enset.reservation;

import ma.enset.reservation.DAO.entites.Entreprise;
import ma.enset.reservation.DAO.entites.EtatReservation;
import ma.enset.reservation.DAO.entites.Reservation;
import ma.enset.reservation.DAO.entites.TypeReservation;
import ma.enset.reservation.DAO.repository.EntrepriseRepository;
import ma.enset.reservation.DAO.repository.ReservationRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
class DaoTests {

    @Autowired
    private EntrepriseRepository entrepriseRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @Test
    void testCreateEntrepriseAndReservation() {
        Entreprise e = new Entreprise();
        e.setNom("TechCorp");
        e.setEmail("contact@techcorp.com");
        e.setDomaineActivite("Informatique");
        e.setUsername("techcorp");

        Entreprise saved = entrepriseRepository.save(e);

        Reservation r = new Reservation();
        r.setDateReservation(LocalDate.now());
        r.setTypeReservation(TypeReservation.SALLE_CONFERENCE);
        r.setDureeReservation(3);
        r.setCout(300);
        r.setEtat(EtatReservation.PENDING);
        r.setEntreprise(saved);

        reservationRepository.save(r);

        List<Reservation> reservations = reservationRepository.findByEntrepriseId(saved.getId());
        assertFalse(reservations.isEmpty());
    }
}
