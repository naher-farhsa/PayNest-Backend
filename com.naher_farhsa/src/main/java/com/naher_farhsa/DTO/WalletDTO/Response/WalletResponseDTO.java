package com.naher_farhsa.DTO.WalletDTO.Response;


import com.naher_farhsa.Entity.User;
import com.naher_farhsa.Enum.WalletType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WalletResponseDTO {
        private Long walletId;
        private String userName;
        private String bank;
        private String accNo;
        private WalletType walletType;

}
