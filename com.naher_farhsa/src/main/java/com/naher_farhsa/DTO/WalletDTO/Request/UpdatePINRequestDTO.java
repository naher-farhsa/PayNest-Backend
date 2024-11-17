package com.naher_farhsa.DTO.WalletDTO.Request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePINRequestDTO {

        private Long walletId;
      // @Pattern(regexp = "\\d{4}", message = "PIN must be exactly 4 digits")
        private String oldPin;
     // @Pattern(regexp = "\\d{4}", message = "PIN must be exactly 4 digits")
        private String newPin;
}