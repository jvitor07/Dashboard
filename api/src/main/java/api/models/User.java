package api.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    @NotBlank(message = "É necessário informar um nome")
    @Size(min = 5, max = 70, message = "O nome deve conter entre {min} e {max} caracteres")
    private String name;

    @Column(name = "email", unique = true)
    @NotBlank(message = "É necessário informar um email")
    @Size(min = 10, max = 80, message = "O email deve conter entre {min} e {max} caracteres")
    @Email(message = "O email informado é inválido")
    private String email;

    @Column(name = "password")
    @NotBlank(message = "É necessário informar uma senha")
    @Size(min = 6, max = 70, message = "A senha deve conter entre {min} e {max} caracteres")
    private String password;
}
