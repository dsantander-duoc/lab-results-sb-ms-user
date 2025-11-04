package cl.duoc.userms.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponse {
    private Long userId;
    private String rut;
    private String username;
    private String name;
    private String lastName;
    private String email;
    private String phone;
    private LocalDate birthDate;
    private String address;
    private boolean active;
    private Long comunaId;
    private Long laboratoryId;
    private Set<Long> roleIds;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}