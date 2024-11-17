package com.naher_farhsa.DTO.UserDTO.Request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePasswordRequestDTO{

    private Long userId;
    // @Pattern(regexp = "\\d{4}", message = "PIN must be exactly 4 digits")
    private String oldPassword;
    // @Pattern(regexp = "\\d{4}", message = "PIN must be exactly 4 digits")
    private String newPassword;
}
