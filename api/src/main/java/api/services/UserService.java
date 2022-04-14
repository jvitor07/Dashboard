package api.services;

import api.exceptions.BadRequestException;
import api.models.User;
import api.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(User model) {
        this.validateIfTheEmailIsAlreadyInUse(model.getEmail());
        return this.saveUser(model);
    }

    private User saveUser(User model) {
        return this.userRepository.save(model);
    }

    private void validateIfTheEmailIsAlreadyInUse(String email) {
        if(Boolean.TRUE.equals(this.emailInUse(email))) {
            throw new BadRequestException("O email informado está sendo utilizado por outra conta");
        }
    }

    private boolean emailInUse(String email) {
        return this.userRepository.findByEmail(email).isPresent();
    }
}
