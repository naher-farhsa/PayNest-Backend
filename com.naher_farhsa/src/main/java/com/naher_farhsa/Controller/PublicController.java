package com.naher_farhsa.Controller;


import com.naher_farhsa.DTO.UserDTO.Request.LoginRequestDTO;
import com.naher_farhsa.DTO.UserDTO.Request.RegisterRequestDTO;
import com.naher_farhsa.DTO.UserDTO.Response.LoginResponseDTO;
import com.naher_farhsa.DTO.UserDTO.Response.UserResponseDTO;
import com.naher_farhsa.Service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/public")
public class PublicController {


    private final UserService userService;

    public PublicController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> registerUser(@RequestBody RegisterRequestDTO userDTO) {
        UserResponseDTO registeredUser = userService.registerUser(userDTO);
        return new ResponseEntity<>(registeredUser, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> loginUser(@RequestBody LoginRequestDTO loginRequestDTO) {
        return ResponseEntity.ok(userService.loginUser(loginRequestDTO.getEmail(), loginRequestDTO.getPassword()));
    }
    }

