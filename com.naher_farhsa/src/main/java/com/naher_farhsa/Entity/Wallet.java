package com.naher_farhsa.Entity;


import com.naher_farhsa.Enum.WalletType;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Wallets")
public class Wallet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false, unique = true)
    private String accNo;

    @Column(nullable = false)
    private String bank;

    @Column(nullable = false)
    private BigDecimal walletBalance;

    @Column(nullable = false,unique = true)
  // @Pattern(regexp = "\\d{4}", message = "PIN must be exactly 4 digits")
    private String pin;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private WalletType walletType;

}

