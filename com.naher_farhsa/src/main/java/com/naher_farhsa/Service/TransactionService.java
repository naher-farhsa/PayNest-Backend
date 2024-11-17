package com.naher_farhsa.Service;


import com.naher_farhsa.DTO.TransactionDTO.TransactionResponseDTO;
import com.naher_farhsa.Entity.Transaction;
import com.naher_farhsa.Entity.Wallet;
import com.naher_farhsa.Enum.TransactionType;
import com.naher_farhsa.Exception.AppException;
import com.naher_farhsa.Repository.TransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class TransactionService {

    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public void createTransaction(Wallet wallet, BigDecimal amount, TransactionType type) {
        Transaction transaction=new Transaction();
        transaction.setWallet(wallet);
        transaction.setAmount(amount);
        transaction.setType(type);
        transaction.setDateTime(LocalDateTime.now());
        transactionRepository.save(transaction);
    }

    public TransactionResponseDTO getTransactionById(Long id) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new AppException(AppException.ErrorType.TRANSACTION_NOT_FOUND));

        return new TransactionResponseDTO(
                transaction.getId(),
                transaction.getWallet().getId(), // Extract walletId from the Wallet entity
                transaction.getAmount(),
                transaction.getDateTime(),
                transaction.getType()
        );
    }

    public List<TransactionResponseDTO> getTransactionsByWalletId(Long walletId) {
        List<Transaction> transactions = transactionRepository.findByWalletId(walletId);

        return transactions.stream()
                .map(transaction -> new TransactionResponseDTO(
                        transaction.getId(),
                        transaction.getWallet().getId(),
                        transaction.getAmount(),
                        transaction.getDateTime(),
                        transaction.getType()))
                .collect(Collectors.toList());
    }

    public void updateDeletedWalletTransaction(Long walletId) {
        // Find all transactions linked to the wallet and either remove the wallet reference or flag them
        List<Transaction> transactions = transactionRepository.findByWalletId(walletId);

        for (Transaction transaction : transactions) {

            // Optionally mark the transaction as associated with a deleted wallet
            transaction.setWallet(null);
            transactionRepository.save(transaction);
        }
    }
   /*
    public void deleteTransaction(Long id) {
        transactionRepository.deleteById(id);
    }

    private TransactionDTO convertToDTO(Transaction transaction) {
        TransactionDTO dto = new TransactionDTO();
        dto.setId(transaction.getId());
        dto.setWalletId(transaction.getWallet().getId());
        dto.setAmount(transaction.getAmount());
        dto.setDateTime(transaction.getDateTime());
        dto.setType(transaction.getType());
        return dto;
    }*/
}
