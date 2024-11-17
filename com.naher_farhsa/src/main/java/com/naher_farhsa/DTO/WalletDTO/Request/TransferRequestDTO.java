package com.naher_farhsa.DTO.WalletDTO.Request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransferRequestDTO {

    private Long senderWalletId;
    private String senderAccNo;
    private Long  receiverWalletId;
    private String receiverAccNo;
    private BigDecimal amount;
    private String pin;

}
