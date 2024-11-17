package com.naher_farhsa.Controller;

import com.naher_farhsa.DTO.TransactionDTO.TransactionResponseDTO;
import com.naher_farhsa.Entity.Transaction;
import com.naher_farhsa.Service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/transactions")
public class TransactionController {
    @Autowired
    private TransactionService transactionService;

    @GetMapping("/getById{id}")
    public ResponseEntity<TransactionResponseDTO> getTransactionById(@PathVariable Long id) {
        TransactionResponseDTO transaction = transactionService.getTransactionById(id);
        return ResponseEntity.ok(transaction);
    }

    @GetMapping("/getByWalletId/{walletId}")
    public ResponseEntity<List<TransactionResponseDTO>> getTransactionsByWalletId(@PathVariable Long walletId) {
        List<TransactionResponseDTO> transactions = transactionService.getTransactionsByWalletId(walletId);
        return ResponseEntity.ok(transactions);
    }

    /*@DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable Long id, @RequestParam String password) {
        transactionService.deleteTransaction(id, password);
        return ResponseEntity.noContent().build();
    }*/
}
