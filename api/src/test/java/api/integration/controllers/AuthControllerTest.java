package api.integration.controllers;

import api.dtos.LoginDTO;
import api.dtos.ResponseDTO;
import api.dtos.TokenResponseDTO;
import api.factories.UserFactory;
import api.models.User;
import api.repositories.UserRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@TestPropertySource(locations = "classpath:application.properties")
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AuthControllerTest {
    private final String LOGIN_ROUTE = "/login";

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private UserRepository userRepository;

    private final UserFactory userFactory;

    public AuthControllerTest() {
        this.userFactory = new UserFactory();
    }

    @BeforeEach
    void setUp() {
        this.userRepository.deleteAll();
    }

    @Order(1)
    @Test
    void itShouldGetErrorMessageWhenTryingToGenerateTokenWithWrongCredentials() throws Exception {
        LoginDTO payload = new LoginDTO("test2@test.com", "secret123");
        ResponseDTO<LoginDTO> expected = new ResponseDTO<>(List.of("Email ou senha incorretos"), null);
        MvcResult result = this.mockMvc.perform(post(LOGIN_ROUTE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(payload)))
                .andReturn();
        MockHttpServletResponse response = result.getResponse();
        ResponseDTO<LoginDTO> responseBody = this.mapper.readValue(response.getContentAsString(StandardCharsets.UTF_8), new TypeReference<>() {});
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(expected).isEqualTo(responseBody);
    }

    @Order(2)
    @Test
    void itShouldReceiveAccessTokenWhenLoginIsSuccessful() throws Exception {
        User userPayload = this.userFactory.setEmail("test3@test.com").newInstance();
        LoginDTO loginPayload = new LoginDTO(userPayload.getEmail(), userPayload.getPassword());
        userPayload.setPassword(this.encoder.encode(userPayload.getPassword()));
        this.userRepository.save(userPayload);
        MvcResult result = this.mockMvc.perform(post(LOGIN_ROUTE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(loginPayload)))
                .andReturn();
        MockHttpServletResponse response = result.getResponse();
        ResponseDTO<TokenResponseDTO> responseBody = this.mapper.readValue(response.getContentAsString(StandardCharsets.UTF_8), new TypeReference<>() {});
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(responseBody.getResponseObject().getToken()).isNotNull();
    }
}
