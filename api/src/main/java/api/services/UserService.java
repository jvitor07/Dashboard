package api.services;

import api.exceptions.BadRequest;
import api.models.User;
import api.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    public User createUser(User model) {
        this.validateIfTheEmailIsAlreadyInUse(model.getEmail());
        this.encryptPassword(model);
        return this.saveUser(model);
    }

    private void encryptPassword(User model) {
        model.setPassword(this.encoder.encode(model.getPassword()));
    }

    private User saveUser(User model) {
        return this.userRepository.save(model);
    }

    private void validateIfTheEmailIsAlreadyInUse(String email) {
        if(Boolean.TRUE.equals(this.emailInUse(email))) {
            throw new BadRequest("O email informado está sendo utilizado por outra conta");
        }
    }

    private boolean emailInUse(String email) {
        return this.userRepository.findByEmail(email).isPresent();
    }
}
