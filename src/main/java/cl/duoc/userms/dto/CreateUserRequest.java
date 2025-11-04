package cl.duoc.userms.dto;

import java.time.LocalDate;
import java.util.Set;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateUserRequest {

    @Pattern(regexp = "^\\d{1,2}\\.\\d{3}\\.\\d{3}-[\\dkK]$", message = "Invalid Rut")
    @NotBlank
    @Size(min = 10, max = 12)
    private String rut;

    @NotBlank
    @Size(min = 5, max = 20)
    private String username;

    @NotBlank
    @Size(min = 2, max = 50)
    private String name;

    @NotBlank
    @Size(min = 2, max = 50)
    private String lastName;

    @NotBlank
    @Size(min = 8, max = 60) // 60 para BCrypt
    private String password;

    @NotBlank
    @Email
    @Size(min = 5, max = 100)
    private String email;

    @NotBlank
    @Size(min = 9, max = 15)
    private String phone;

    @NotNull
    private LocalDate birthDate;

    @NotBlank
    @Size(min = 5, max = 100)
    private String address;

    @NotNull
    private Long comunaId;

    private Long laboratoryId;

    @NotNull
    private Set<Long> roleIds;

    private Boolean active;
}