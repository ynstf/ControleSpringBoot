package ma.enset.reservation.DAO.repository;

import ma.enset.reservation.DAO.entites.EtatReservation;
import ma.enset.reservation.DAO.entites.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    // Recherche paginée des réservations dont la date correspond
    Page<Reservation> findByDateReservation(
            LocalDate dateReservation,
            Pageable pageable
    );
    List<Reservation> findByEntrepriseId(Long entrepriseId);
    List<Reservation> findByEtat(EtatReservation etat);
}