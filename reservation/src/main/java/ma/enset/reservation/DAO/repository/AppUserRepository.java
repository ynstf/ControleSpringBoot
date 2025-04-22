package ma.enset.reservation.DAO.repository;

import ma.enset.reservation.DAO.entites.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    AppUser findByUsername(String username);
}