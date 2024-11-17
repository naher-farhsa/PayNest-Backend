package com.naher_farhsa.DTO.WalletDTO.Request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateWalletRequestDTO {

    private Long walletId;
    private String accNo;
    private BigDecimal amount;
    private String pin;
    private String updateType; //withdrawal , deposit
}
