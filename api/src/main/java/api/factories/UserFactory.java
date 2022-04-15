package api.factories;

import api.models.User;
import lombok.Getter;

@Getter
public class UserFactory {
    private Long id;
    private String name;
    private String email;
    private String password;

    public UserFactory() {
        this.id = null;
        this.name = "test test";
        this.email = "test@test.com";
        this.password = "secret123";
    }

    public User newInstance() {
        return new User(this.id, this.name, this.email, this.password);
    }

    public UserFactory setId(Long id) {
        this.id = id;
        return this;
    }

    public UserFactory setName(String name) {
        this.name = name;
        return this;
    }

    public UserFactory setEmail(String email) {
        this.email = email;
        return this;
    }

    public UserFactory setPassword(String password) {
        this.password = password;
        return this;
    }
}
