package com.naher_farhsa.DTO.UserDTO.Response;

import com.naher_farhsa.Enum.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDTO {
        private Long id;
        private String name;
        private String email;
        private Role role;
}
