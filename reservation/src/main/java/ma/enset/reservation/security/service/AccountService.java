package ma.enset.reservation.security.service;


import ma.enset.reservation.DAO.entites.AppRole;
import ma.enset.reservation.DAO.entites.AppUser;

public interface AccountService {
    AppUser addNewUser(String username, String password, String confirmPassword);
    AppRole addNewRole(String roleName);
    void addRoleToUser(String username, String roleName);
    AppUser loadUserByUsername(String username);
}
