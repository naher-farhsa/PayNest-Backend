package com.naher_farhsa.DTO.WalletDTO.Request;

import com.naher_farhsa.Entity.User;
import com.naher_farhsa.Enum.WalletType;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateRequestDTO {
    private Long userId;
    private String accNo;
    private String bank;
    //@Pattern(regexp = "\\d{4}", message = "PIN must be exactly 4 digits")
    private String PIN;
    private WalletType walletType;
}
