package com.naher_farhsa.DTO.WalletDTO;

import com.naher_farhsa.DTO.WalletDTO.Request.CreateRequestDTO;
import com.naher_farhsa.DTO.WalletDTO.Request.UpdatePINRequestDTO;
import com.naher_farhsa.DTO.WalletDTO.Response.WalletResponseDTO;
import com.naher_farhsa.Entity.User;
import com.naher_farhsa.Entity.Wallet;
import com.naher_farhsa.Exception.AppException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class WalletDTOMapper {

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Wallet -> WalletResponseDTO
    public WalletResponseDTO toResponseDTO(Wallet wallet) {
        return new WalletResponseDTO(
                wallet.getId(),
                wallet.getUser().getName(),
                wallet.getBank(),
                wallet.getAccNo(),
                wallet.getWalletType()
        );
    }

    // CreateRequestDTO -> Wallet
    public Wallet registerToWallet(CreateRequestDTO dto, User user) {
        Wallet wallet = new Wallet();
        wallet.setUser(user);
        wallet.setAccNo(dto.getAccNo());
        wallet.setBank(dto.getBank());
        System.out.println(dto.getPIN());
        if (dto.getPIN() != null) {
            wallet.setPin(passwordEncoder.encode(dto.getPIN()));
        } else {
            throw new IllegalArgumentException("PIN cannot be null."); //EXCEPTION
        }

        wallet.setWalletType(dto.getWalletType());
        wallet.setWalletBalance(BigDecimal.ZERO);
        return wallet;
    }


    // UpdatePINRequestDTO -> Update PIN of an existing Wallet
    public void updatePIN(UpdatePINRequestDTO dto, Wallet existingWallet) {
            // Check if old PIN or new PIN is null to prevent errors
            if (dto.getOldPin() == null || dto.getNewPin() == null) {
                throw new AppException(AppException.ErrorType.PIN_CANNOT_BE_NULL); //EXCEPTION
            }

            // Check if the old PIN matches the stored PIN
            if (passwordEncoder.matches(dto.getOldPin(), existingWallet.getPin())) {
                existingWallet.setPin(passwordEncoder.encode(dto.getNewPin()));
            } else {
                throw new AppException(AppException.ErrorType.INVALID_OLD_PIN);  // EXCEPTION
            }
        }


    // Map Wallet and User to WalletResponseDTO
    public WalletResponseDTO mapToWalletResponseDTO(Wallet wallet, User user) {
        if (wallet == null || user == null) {
            throw new IllegalArgumentException("Wallet or User cannot be null.");
        }
        return new WalletResponseDTO(
                wallet.getId(),
                user.getName(),
                wallet.getBank(),
                wallet.getAccNo(),
                wallet.getWalletType()
        );
    }
}

