package api.controllers.v1;

import api.dtos.ResponseDTO;
import api.exceptions.BadRequest;
import api.models.User;
import api.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(path = "/register")
    public ResponseEntity<ResponseDTO<User>> register(@RequestBody @Valid User model) {
        try {
            User result = this.userService.createUser(model);

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(new ResponseDTO<>(null, result));
        } catch (BadRequest badRequest) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseDTO<>(List.of(badRequest.getMessage()), null));
        }
    }
}
