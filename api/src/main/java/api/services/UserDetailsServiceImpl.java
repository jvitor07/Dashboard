package api.services;

import api.models.User;
import api.models.UserDetailsImpl;
import api.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> optUser = this.userRepository.findByEmail(email);
        if(optUser.isEmpty()) {
            throw new UsernameNotFoundException("Usuário não encontrado");
        }
        return new UserDetailsImpl(optUser);
    }
}
