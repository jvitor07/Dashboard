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

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

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
    }
}
