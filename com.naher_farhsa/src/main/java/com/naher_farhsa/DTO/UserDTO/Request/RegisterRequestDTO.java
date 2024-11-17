package com.naher_farhsa.DTO.UserDTO.Request;

import com.naher_farhsa.Enum.Role;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequestDTO {

    private String name;
    private String email;
   /* @Pattern(
            regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$",
            message = "Password must have 8+ characters, including numbers, uppercase, lowercase and special characters"
    )*/
    private String password;
    private Role role;
}
