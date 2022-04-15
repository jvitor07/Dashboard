package api.integration.controllers;

import api.dtos.ResponseDTO;
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
public class UserControllerTest {
    private final String USER_REGISTER_ROUTE = "/api/v1/users/register";

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private UserRepository userRepository;
    private final UserFactory userFactory;

    public UserControllerTest() {
        this.userFactory = new UserFactory();
    }

    @AfterEach
    void tearDown() {
        this.userRepository.deleteAll();
    }

    @Order(1)
    @Test
    void itShouldCreateNewUser() throws Exception {
        User payload = this.userFactory.newInstance();
        ResponseDTO<User> expected = new ResponseDTO<>(null, this.userFactory.setId(1L).setPassword(null).newInstance());
        MvcResult result = this.mockMvc.perform(post(USER_REGISTER_ROUTE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(payload)))
        .andReturn();
        MockHttpServletResponse response = result.getResponse();
        ResponseDTO<User> responseBody = this.mapper.readValue(response.getContentAsString(StandardCharsets.UTF_8), new TypeReference<>() {});
        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(expected).isEqualTo(responseBody);
        assertThat(responseBody.getResponseObject().getPassword()).isNotEqualTo(payload.getPassword());
    }

    @Order(2)
    @Test
    void itShouldReceiveErrorMessageWhenTryingToRegisterNewUserWithoutName() throws Exception {
        User payload = this.userFactory.setName(null).newInstance();
        ResponseDTO<User> expected = new ResponseDTO<>(List.of("É necessário informar um nome"), null);
        MvcResult result = this.mockMvc.perform(post(USER_REGISTER_ROUTE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(payload)))
                .andReturn();
        MockHttpServletResponse response = result.getResponse();
        ResponseDTO<User> responseBody = this.mapper.readValue(response.getContentAsString(StandardCharsets.UTF_8), new TypeReference<>() {});
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(expected.getMessages()).containsAll(responseBody.getMessages());
    }

    @Order(3)
    @Test
    void itShouldReceiveErrorMessageWhenTryingToRegisterNewUserWithoutEmail() throws Exception {
        User payload = this.userFactory.setEmail(null).newInstance();
        ResponseDTO<User> expected = new ResponseDTO<>(List.of("É necessário informar um email"), null);
        MvcResult result = this.mockMvc.perform(post(USER_REGISTER_ROUTE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(payload)))
                .andReturn();
        MockHttpServletResponse response = result.getResponse();
        ResponseDTO<User> responseBody = this.mapper.readValue(response.getContentAsString(StandardCharsets.UTF_8), new TypeReference<>() {});
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(expected.getMessages()).containsAll(responseBody.getMessages());
    }

    @Order(4)
    @Test
    void itShouldReceiveErrorMessageWhenTryingToRegisterNewUserWithoutPassword() throws Exception {
        User payload = this.userFactory.setPassword(null).newInstance();
        ResponseDTO<User> expected = new ResponseDTO<>(List.of("É necessário informar uma senha"), null);
        MvcResult result = this.mockMvc.perform(post(USER_REGISTER_ROUTE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(payload)))
                .andReturn();
        MockHttpServletResponse response = result.getResponse();
        ResponseDTO<User> responseBody = this.mapper.readValue(response.getContentAsString(StandardCharsets.UTF_8), new TypeReference<>() {});
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(expected.getMessages()).containsAll(responseBody.getMessages());
    }

    @Order(5)
    @Test
    void itShouldReceiveErrorMessageWhenTryingToRegisterNewUserWithBlankName() throws Exception {
        User payload = this.userFactory.setName("").newInstance();
        ResponseDTO<User> expected = new ResponseDTO<>(List.of("É necessário informar um nome", "O nome deve conter entre 5 e 70 caracteres"), null);
        MvcResult result = this.mockMvc.perform(post(USER_REGISTER_ROUTE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(payload)))
                .andReturn();
        MockHttpServletResponse response = result.getResponse();
        ResponseDTO<User> responseBody = this.mapper.readValue(response.getContentAsString(StandardCharsets.UTF_8), new TypeReference<>() {});
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(expected.getMessages()).containsAll(responseBody.getMessages());
    }

    @Order(6)
    @Test
    void itShouldReceiveErrorMessageWhenTryingToRegisterNewUserWithBlankEmail() throws Exception {
        User payload = this.userFactory.setEmail("").newInstance();
        ResponseDTO<User> expected = new ResponseDTO<>(List.of("É necessário informar um email", "O email deve conter entre 10 e 80 caracteres"), null);
        MvcResult result = this.mockMvc.perform(post(USER_REGISTER_ROUTE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(payload)))
                .andReturn();
        MockHttpServletResponse response = result.getResponse();
        ResponseDTO<User> responseBody = this.mapper.readValue(response.getContentAsString(StandardCharsets.UTF_8), new TypeReference<>() {});
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(expected.getMessages()).containsAll(responseBody.getMessages());
    }

    @Order(7)
    @Test
    void itShouldReceiveErrorMessageWhenTryingToRegisterNewUserWithBlankPassword() throws Exception {
        User payload = this.userFactory.setPassword("").newInstance();
        ResponseDTO<User> expected = new ResponseDTO<>(List.of("A senha deve conter entre 6 e 70 caracteres", "É necessário informar uma senha"), null);
        MvcResult result = this.mockMvc.perform(post(USER_REGISTER_ROUTE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(payload)))
                .andReturn();
        MockHttpServletResponse response = result.getResponse();
        ResponseDTO<User> responseBody = this.mapper.readValue(response.getContentAsString(StandardCharsets.UTF_8), new TypeReference<>() {});
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(expected.getMessages()).containsAll(responseBody.getMessages());
    }

    @Order(8)
    @Test
    void itShouldReceiveErrorMessageWhenTryingToRegisterNewUserWithInvalidEmail() throws Exception {
        User payload = this.userFactory.setEmail("testtestcom").newInstance();
        ResponseDTO<User> expected = new ResponseDTO<>(List.of("O email informado é inválido"), null);
        MvcResult result = this.mockMvc.perform(post(USER_REGISTER_ROUTE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(payload)))
                .andReturn();
        MockHttpServletResponse response = result.getResponse();
        ResponseDTO<User> responseBody = this.mapper.readValue(response.getContentAsString(StandardCharsets.UTF_8), new TypeReference<>() {});
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(expected.getMessages()).containsAll(responseBody.getMessages());
    }

    @Order(9)
    @Test
    void itShouldReceiveErrorMessageWhenTryingToRegisterNewUserWithAnEmailThatIsAlreadyInUse() throws Exception {
        User payload = this.userFactory.newInstance();
        ResponseDTO<User> expected = new ResponseDTO<>(List.of("O email informado está sendo utilizado por outra conta"), null);
        this.userRepository.save(payload);
        MvcResult result = this.mockMvc.perform(post(USER_REGISTER_ROUTE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(payload)))
                .andReturn();
        MockHttpServletResponse response = result.getResponse();
        ResponseDTO<User> responseBody = this.mapper.readValue(response.getContentAsString(StandardCharsets.UTF_8), new TypeReference<>() {});
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(expected.getMessages()).containsAll(responseBody.getMessages());
    }
}
