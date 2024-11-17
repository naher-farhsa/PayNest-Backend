package com.naher_farhsa.Controller;

import com.naher_farhsa.DTO.WalletDTO.Request.*;
import com.naher_farhsa.DTO.WalletDTO.Response.WalletResponseDTO;
import com.naher_farhsa.Service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/wallets")
public class WalletController {
    @Autowired
    private WalletService walletService;

    @PostMapping("/create")
    public ResponseEntity<WalletResponseDTO> createWallet(@RequestBody CreateRequestDTO createRequestDTO) {
        WalletResponseDTO responseDTO = walletService.createWallet(createRequestDTO);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @PutMapping("/update-pin")
    public ResponseEntity<String> updateWallet(@RequestBody UpdatePINRequestDTO updatePINRequestDTO) {
        walletService.updatePIN(updatePINRequestDTO);
        return new ResponseEntity<>("PIN updated successfully",HttpStatus.OK);
    }


    @PostMapping("/check-balance")
    public ResponseEntity<String> checkBalance(@RequestBody CheckBalanceRequestDTO checkBalanceRequestDTO) {
        BigDecimal balance = walletService.checkBalance(checkBalanceRequestDTO);
        return new ResponseEntity<>("Current balance: " + balance, HttpStatus.OK);
    }

    @PutMapping("/update-balance")
    public ResponseEntity<String> updateBalance(@RequestBody UpdateWalletRequestDTO updateWalletRequestDTO) {
        String message = walletService.updateBalance(updateWalletRequestDTO);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @PutMapping("/wallet-transfer")
    public ResponseEntity<String> walletTransfer(@RequestBody TransferRequestDTO transferRequestDTO) {
        String message = walletService.walletTransfer(transferRequestDTO);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @GetMapping("/getWallet/{walletId}")
    public ResponseEntity<WalletResponseDTO> getWalletById(@PathVariable Long walletId) {
        WalletResponseDTO walletResponseDTO = walletService.getWalletById(walletId);
        return new ResponseEntity<>(walletResponseDTO, HttpStatus.OK);
    }

    @GetMapping("/getAllWallets/{userId}")
    public ResponseEntity<List<WalletResponseDTO>> getAllWallets(@PathVariable Long userId) {
        List<WalletResponseDTO> walletResponseDTOs = walletService.getAllWallets(userId);
        return new ResponseEntity<>(walletResponseDTOs, HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteWallet(@RequestBody WalletDeleteRequestDTO walletDeleteRequestDTO) {
        String message = walletService.deleteWallet(walletDeleteRequestDTO);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

}
