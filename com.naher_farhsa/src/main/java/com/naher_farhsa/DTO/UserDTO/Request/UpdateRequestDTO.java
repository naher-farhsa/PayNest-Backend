package com.naher_farhsa.DTO.UserDTO.Request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateRequestDTO {
        private Long userId;
        private String name;         // Optional
        private String email;        // Optional, with @Email validation
        // No password here - should have separate endpoint for password update

}
