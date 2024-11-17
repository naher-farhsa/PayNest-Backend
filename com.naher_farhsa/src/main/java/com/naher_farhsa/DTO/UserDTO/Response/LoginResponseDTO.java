package com.naher_farhsa.DTO.UserDTO.Response;

import com.naher_farhsa.DTO.UserDTO.Response.UserResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponseDTO {
    private UserResponseDTO user; // User details
    private String token; // JWT token
}
