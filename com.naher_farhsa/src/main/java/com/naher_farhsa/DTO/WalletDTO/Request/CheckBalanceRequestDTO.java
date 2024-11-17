package com.naher_farhsa.DTO.WalletDTO.Request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CheckBalanceRequestDTO {
    private  Long walletId;
    private String accNo;
    private String pin;

}
