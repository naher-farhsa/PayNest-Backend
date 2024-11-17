package com.naher_farhsa.DTO.UserDTO;

import com.naher_farhsa.DTO.UserDTO.Request.RegisterRequestDTO;
import com.naher_farhsa.DTO.UserDTO.Request.UpdatePasswordRequestDTO;
import com.naher_farhsa.DTO.UserDTO.Request.UpdateRequestDTO;
import com.naher_farhsa.DTO.UserDTO.Response.LoginResponseDTO;
import com.naher_farhsa.DTO.UserDTO.Response.UserResponseDTO;
import com.naher_farhsa.DTO.WalletDTO.Request.UpdatePINRequestDTO;
import com.naher_farhsa.Entity.User;
import com.naher_farhsa.Entity.Wallet;
import com.naher_farhsa.Exception.AppException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserDTOMapper {

    @Autowired
    PasswordEncoder passwordEncoder;
    // User -> UserResponseDTO
    public UserResponseDTO toResponseDTO(User user) {
        return new UserResponseDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole()
        );
    }
 // User -> LoginResponseDTO
    public LoginResponseDTO toResponseDTO(User user, String token) {
        if (user == null) {
            return null;
        }
        UserResponseDTO userResponseDTO = toResponseDTO(user);
        return new LoginResponseDTO(userResponseDTO, token);
    }

    // RegisterRequestDTO -> User
    public User registerToUser(RegisterRequestDTO dto) {
        User user = new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRole(dto.getRole());
        return user;
    }
    // LoginRequestDTO --> User not required

    // UpdateRequestDTO -> User
    public void updateToUser(User user, UpdateRequestDTO dto) {
        // Only update non-null fields (optional updates)
        if (dto.getName() != null) {
            user.setName(dto.getName());
        }
        if (dto.getEmail() != null) {
            user.setEmail(dto.getEmail());
        }

    }


    // UpdatePasswordRequestDTO -> Update Password of an existing User
    public void updatePassword(UpdatePasswordRequestDTO dto, User user) {
        // Check if old PIN or new PIN is null to prevent errors
        if (dto.getOldPassword() == null || dto.getNewPassword() == null) {
            throw new AppException(AppException.ErrorType.PASSWORD_CANNOT_BE_NULL); //EXCEPTION
        }

        // Check if the old PIN matches the stored PIN
        if (passwordEncoder.matches(dto.getOldPassword(), user.getPassword())) {
            user.setPassword(passwordEncoder.encode(dto.getNewPassword()));
        } else {
            throw new AppException(AppException.ErrorType.INVALID_OLD_PASSWORD);
        }
    }
}

