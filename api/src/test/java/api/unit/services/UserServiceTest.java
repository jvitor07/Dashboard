package api.unit.services;

import api.factories.UserFactory;
import api.models.User;
import api.repositories.UserRepository;
import api.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(OrderAnnotation.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    private final UserFactory userFactory;
    private UserService userService;

    public UserServiceTest() {
        this.userFactory = new UserFactory();
    }

    @BeforeEach
    void setUp() {
        this.userService = new UserService(this.userRepository);
    }

    @Order(1)
    @Test
    void itShouldVerifyIfTheCreateUserMethodInUserServiceUseSaveMethodOfUserRepository() {
        User payload = this.userFactory.newInstance();
        this.userService.createUser(payload);
        verify(this.userRepository).save(payload);
    }

    @Order(2)
    @Test
    void itShouldVerifyIfTheCreateUserMethodInUserServiceUseFindByEmailMethodOfUserRepository() {
        User payload = this.userFactory.newInstance();
        this.userService.createUser(payload);
        verify(this.userRepository).findByEmail(payload.getEmail());
    }
}
