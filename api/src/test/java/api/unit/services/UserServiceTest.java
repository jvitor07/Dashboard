package api.unit.services;

import api.exceptions.BadRequest;
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
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(OrderAnnotation.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder encoder;
    private final UserFactory userFactory;
    private UserService userService;

    public UserServiceTest() {
        this.userFactory = new UserFactory();
    }

    @BeforeEach
    void setUp() {
        this.userService = new UserService(userRepository, encoder);
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

    @Order(3)
    @Test
    void itShouldVerifyIfTheCreateUserMethodInUserServiceUseEncodeMethodOfPasswordEncoder() {
        User payload = this.userFactory.newInstance();
        String expectedPassword = payload.getPassword();
        this.userService.createUser(payload);
        verify(this.encoder).encode(expectedPassword);
    }

    @Order(4)
    @Test
    void itShouldReceiveErrorMessageWhenPassAUserWithEmailThatIsAlreadyInUseToCreateUserMethodOfUserService() {
        User payload = this.userFactory.newInstance();
        given(this.userRepository.findByEmail(payload.getEmail())).willReturn(Optional.of(payload));
        assertThatThrownBy(() -> this.userService.createUser(payload))
                .isInstanceOf(BadRequest.class)
                .hasMessageContaining("O email informado está sendo utilizado por outra conta");
    }
}
