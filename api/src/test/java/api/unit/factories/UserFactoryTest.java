package api.unit.factories;

import api.factories.UserFactory;
import api.models.User;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class UserFactoryTest {
    private final UserFactory userFactory;

    public UserFactoryTest() {
        this.userFactory = new UserFactory();
    }

    @Test
    void itShouldCreateAnInstanceOfUserUsingUserFactory() {
        assertThat(this.userFactory.newInstance()).isInstanceOf(User.class);
    }

    @Test
    void itShouldVerifyIfSetNameChangeDefaultValueOfFactoryId() {
        assertThat(this.userFactory.setId(8L).newInstance().getId())
                .isNotEqualTo(new UserFactory().newInstance().getId());
    }

    @Test
    void itShouldVerifyIfSetNameChangeDefaultValueOfFactoryName() {
        assertThat(this.userFactory.setName("new name").newInstance().getName())
                .isNotEqualTo(new UserFactory().newInstance().getName());
    }

    @Test
    void itShouldVerifyIfSetEmailChangeDefaultValueOfFactoryEmail() {
        assertThat(this.userFactory.setEmail("new email").newInstance().getEmail())
                .isNotEqualTo(new UserFactory().newInstance().getEmail());
    }

    @Test
    void itShouldVerifyIfSetPasswordChangeDefaultValueOfFactoryPassword() {
        assertThat(this.userFactory.setPassword("new password").newInstance().getPassword())
                .isNotEqualTo(new UserFactory().newInstance().getPassword());
    }
}
