package com.naher_farhsa.Controller;



import com.naher_farhsa.DTO.UserDTO.Request.UpdatePasswordRequestDTO;
import com.naher_farhsa.DTO.UserDTO.Request.UpdateRequestDTO;
import com.naher_farhsa.DTO.UserDTO.Request.UserDeleteRequestDTO;
import com.naher_farhsa.DTO.UserDTO.Response.UserResponseDTO;
import com.naher_farhsa.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

    @RestController
    @RequestMapping("/users")
    public class UserController {


        private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/get/{id}")
        public ResponseEntity<UserResponseDTO> getUserById(@PathVariable Long id) {
            UserResponseDTO user = userService.getUserById(id);
            return ResponseEntity.ok(user);
        }

       @PutMapping("/update-userDetail")
        public ResponseEntity<UserResponseDTO> updateUser(@RequestBody UpdateRequestDTO userDTO) {
            UserResponseDTO updatedUser = userService.updateUser(userDTO);
            return ResponseEntity.ok(updatedUser);
        }
        @PutMapping("/update-password")
        public ResponseEntity<String> updateUser(@RequestBody UpdatePasswordRequestDTO userDTO) {

            userService.updatePassword(userDTO);
            return new ResponseEntity<>("Password updated successfully", HttpStatus.OK);
        }

        @DeleteMapping("/delete")
        public ResponseEntity<String> deleteUser(@RequestBody UserDeleteRequestDTO deleteRequestDTO) {
            String message = userService.deleteUser(deleteRequestDTO);
            return new ResponseEntity<>(message, HttpStatus.OK);
        }

    }
