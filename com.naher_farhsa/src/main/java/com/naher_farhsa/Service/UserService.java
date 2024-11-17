package com.naher_farhsa.Service;


import com.naher_farhsa.Authentication.JwtUtil;
import com.naher_farhsa.DTO.UserDTO.Request.UserDeleteRequestDTO;
import com.naher_farhsa.DTO.UserDTO.Request.RegisterRequestDTO;
import com.naher_farhsa.DTO.UserDTO.Request.UpdatePasswordRequestDTO;
import com.naher_farhsa.DTO.UserDTO.Request.UpdateRequestDTO;
import com.naher_farhsa.DTO.UserDTO.Response.LoginResponseDTO;
import com.naher_farhsa.DTO.UserDTO.UserDTOMapper;
import com.naher_farhsa.DTO.UserDTO.Response.UserResponseDTO;
import com.naher_farhsa.Entity.User;
import com.naher_farhsa.Entity.Wallet;
import com.naher_farhsa.Exception.AppException;
import com.naher_farhsa.Repository.UserRepository;
import com.naher_farhsa.Repository.WalletRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final UserDTOMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailService customUserDetailService;
    private final JwtUtil jwtUtil;
    private final WalletRepository walletRepository;

    public UserService(
            UserRepository userRepository,
            UserDTOMapper userMapper,
            PasswordEncoder passwordEncoder,
            AuthenticationManager authenticationManager,
            CustomUserDetailService customUserDetailService,
            JwtUtil jwtUtil,WalletRepository walletRepository) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.customUserDetailService = customUserDetailService;
        this.jwtUtil = jwtUtil;
        this.walletRepository=walletRepository;
    }


    // RegisterUser
    public UserResponseDTO registerUser(RegisterRequestDTO userDTO) {
        if (userRepository.existsByEmail(userDTO.getEmail())) {
            throw new AppException(AppException.ErrorType.EMAIL_ALREADY_EXISTS); // ------EXCEPTION--------
        }
        // Check if password is provided
        if (userDTO.getPassword() == null || userDTO.getPassword().isEmpty()) {
            throw new AppException(AppException.ErrorType.PASSWORD_CANNOT_BE_NULL); // ------EXCEPTION--------
        }


        User user= userMapper.registerToUser(userDTO);


        User registeredUser = userRepository.save(user);
        return userMapper.toResponseDTO(registeredUser);
    }


    //LoginUser
    public LoginResponseDTO loginUser(String email, String password) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password)
            );

            User user = userRepository.findByEmail(email);
            if (user == null) {
                return new LoginResponseDTO(null, "User not found");  //EXCEPTION
            }

            UserDetails userDetails = customUserDetailService.loadUserByUsername(email);
            String jwt = jwtUtil.generateToken(userDetails.getUsername());

            return userMapper.toResponseDTO(user, jwt);
        } catch (Exception e) {
            return new LoginResponseDTO(null, "Invalid email or password");  // ------EXCEPTION--------
        }
    }

    //GetUser
    public UserResponseDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new AppException(AppException.ErrorType.USER_NOT_FOUND));
        return userMapper.toResponseDTO(user);
    }

    //NOTE: Once User detail or password is updated then login again otherwise cause user not found exception

    //UpdateUser
   public UserResponseDTO updateUser(UpdateRequestDTO userDTO) {

          User user= userRepository.findById(userDTO.getUserId())
                  .orElseThrow(()  -> new AppException(AppException.ErrorType.USER_NOT_FOUND)); // ------EXCEPTION--------
          userMapper.updateToUser(user,userDTO);
        return userMapper.toResponseDTO(userRepository.save(user));
    }


    //Password Update
    public void updatePassword(UpdatePasswordRequestDTO dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new AppException(AppException.ErrorType.USER_NOT_FOUND));  // ------EXCEPTION--------

        userMapper.updatePassword(dto,user);


        userRepository.save(user);
    }

   //Delete User
   public String deleteUser(UserDeleteRequestDTO dto) {
       // Fetch user by ID
       User user = userRepository.findById(dto.getUserId())
               .orElseThrow(() -> new AppException(AppException.ErrorType.USER_NOT_FOUND));

       // Validate email and password
       if (!user.getEmail().equals(dto.getEmail()) ||
               !passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
           throw new AppException(AppException.ErrorType.INVALID_PASSWORD);
       }

       // Delete associated wallets
       List<Wallet> wallets = walletRepository.findByUserId(user.getId());
       walletRepository.deleteAll(wallets);

       // Delete the user
       userRepository.delete(user);

       return "User ("+user.getName()+") with associated wallets+ deleted successfully.";
   }
}
