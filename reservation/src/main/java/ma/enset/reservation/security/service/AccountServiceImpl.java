package ma.enset.reservation.security.service;

import lombok.AllArgsConstructor;
import ma.enset.reservation.DAO.entites.AppRole;
import ma.enset.reservation.DAO.entites.AppUser;
import ma.enset.reservation.DAO.repository.AppRoleRepository;
import ma.enset.reservation.DAO.repository.AppUserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@AllArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AppUserRepository appUserRepository;
    private final AppRoleRepository appRoleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public AppUser addNewUser(String username, String password, String confirmPassword) {
        if (!password.equals(confirmPassword)) throw new RuntimeException("Passwords do not match");
        if (appUserRepository.findByUsername(username) != null) throw new RuntimeException("User already exists");

        AppUser user = AppUser.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .roles(new ArrayList<>())
                .build();
        return appUserRepository.save(user);
    }

    @Override
    public AppRole addNewRole(String roleName) {
        AppRole role = appRoleRepository.findByRoleName(roleName);
        if (role != null) throw new RuntimeException("Role already exists");
        return appRoleRepository.save(AppRole.builder().roleName(roleName).build());
    }

    @Override
    public void addRoleToUser(String username, String roleName) {
        AppUser user = appUserRepository.findByUsername(username);
        AppRole role = appRoleRepository.findByRoleName(roleName);
        if (user == null || role == null) throw new RuntimeException("User or Role not found");
        user.getRoles().add(role);
        appUserRepository.save(user);
    }

    @Override
    public AppUser loadUserByUsername(String username) {
        return appUserRepository.findByUsername(username);
    }
}
