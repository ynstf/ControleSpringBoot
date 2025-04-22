package ma.enset.reservation.security;

import lombok.RequiredArgsConstructor;
import ma.enset.reservation.DAO.entites.AppUser;
import ma.enset.reservation.security.service.AccountService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final AccountService accountService;

    @Override
    public void run(String... args) throws Exception {
        System.out.println(">>> DataInitializer STARTED");

        // If “admin” already exists, skip seeding
        AppUser existingAdmin = accountService.loadUserByUsername("admin");
        if (existingAdmin != null) {
            System.out.println(">>> Admin user already exists, skipping seed.");
            return;
        }

        System.out.println(">>> Seeding roles and users…");
        accountService.addNewRole("USER");
        accountService.addNewRole("ADMIN");

        accountService.addNewUser("user1", "1234", "1234");
        accountService.addNewUser("admin", "admin", "admin");

        accountService.addRoleToUser("user1", "USER");
        accountService.addRoleToUser("admin", "ADMIN");

        System.out.println(">>> Seeding complete.");
    }

}
