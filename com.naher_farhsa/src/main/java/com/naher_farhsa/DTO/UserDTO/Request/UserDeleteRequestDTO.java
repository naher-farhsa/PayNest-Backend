package com.naher_farhsa.DTO.UserDTO.Request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDeleteRequestDTO {

    private Long userId;
    private String email;
    private String password;
}


