package ma.enset.reservation.DAO.repository;

import ma.enset.reservation.DAO.entites.Entreprise;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface EntrepriseRepository extends JpaRepository<Entreprise, Long> {
    Page<Entreprise> findByNomContains(String keyword, Pageable pageable);

    @Query("select p from Entreprise p where p.nom like :x")
    Page<Entreprise> chercher(@Param("x") String keyword, Pageable pageable);

    Optional<Entreprise> findByUsername(String username);
}
