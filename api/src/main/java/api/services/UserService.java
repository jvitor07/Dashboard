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
        if(this.userRepository.findByEmail(model.getEmail()).isPresent()) {
            throw new BadRequestException("O email informado já está em uso por outra conta");
        }
        return this.userRepository.save(model);
    }
}
