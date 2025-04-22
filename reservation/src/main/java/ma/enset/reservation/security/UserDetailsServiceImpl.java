package ma.enset.reservation.security;

import lombok.RequiredArgsConstructor;
import ma.enset.reservation.DAO.entites.AppUser;
import ma.enset.reservation.DAO.repository.AppUserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final AppUserRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) {
        AppUser user = userRepo.findByUsername(username);
        if (user == null)
            throw new UsernameNotFoundException("User not found");
        var authorities = user.getRoles().stream()
                .map(r -> new SimpleGrantedAuthority(r.getRoleName()))
                .collect(Collectors.toList());
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(), user.getPassword(), authorities
        );
    }
}
