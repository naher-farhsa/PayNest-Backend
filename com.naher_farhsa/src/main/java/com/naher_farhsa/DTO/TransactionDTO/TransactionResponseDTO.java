package com.naher_farhsa.DTO.TransactionDTO;


import com.naher_farhsa.Enum.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionResponseDTO {
    private Long id;
    private Long walletId; // walletId extracted from the Wallet entity
    private BigDecimal amount;
    private LocalDateTime dateTime;
    private TransactionType type;
}
