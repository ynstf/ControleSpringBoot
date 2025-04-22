package ma.enset.reservation.DAO.repository;

import ma.enset.reservation.DAO.entites.AppRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppRoleRepository extends JpaRepository<AppRole, Long> {
    AppRole findByRoleName(String roleName);
}
