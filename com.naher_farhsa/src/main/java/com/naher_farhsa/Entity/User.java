package com.naher_farhsa.Entity;

import com.naher_farhsa.Enum.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;


    @Column(nullable = false, unique = true)
    @Email
    private String email;

    @Column(nullable = false,unique = true)

    private String password;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;


}