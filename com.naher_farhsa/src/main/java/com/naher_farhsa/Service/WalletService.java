package com.naher_farhsa.Service;

import com.naher_farhsa.DTO.WalletDTO.Request.*;
import com.naher_farhsa.DTO.WalletDTO.Response.WalletResponseDTO;

import com.naher_farhsa.DTO.WalletDTO.WalletDTOMapper;
import com.naher_farhsa.Entity.Transaction;
import com.naher_farhsa.Entity.User;
import com.naher_farhsa.Entity.Wallet;
import com.naher_farhsa.Enum.TransactionType;
import com.naher_farhsa.Exception.AppException;
import com.naher_farhsa.Repository.UserRepository;
import com.naher_farhsa.Repository.WalletRepository;;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class WalletService {

    private final WalletRepository walletRepository;
    private final UserService userService;
    private final WalletDTOMapper walletMapper;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TransactionService transactionService;

    public WalletService(WalletRepository walletRepository, UserService userService,
                         WalletDTOMapper walletMapper,UserRepository userRepository,
                         PasswordEncoder passwordEncoder,TransactionService transactionService) {
        this.walletRepository = walletRepository;
        this.userService = userService;
        this.walletMapper = walletMapper;
        this.userRepository = userRepository;
        this.passwordEncoder=passwordEncoder;
        this.transactionService=transactionService;
    }


    //Create Wallet
    public WalletResponseDTO createWallet(CreateRequestDTO dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new AppException(AppException.ErrorType.USER_NOT_FOUND)); // ------EXCEPTION--------

        Wallet wallet = walletMapper.registerToWallet(dto,user);


        Wallet savedWallet = walletRepository.save(wallet);
        return walletMapper.toResponseDTO(savedWallet);
    }

    //PIN Update
    public void updatePIN(UpdatePINRequestDTO updatePINRequestDTO) {
        Wallet wallet = walletRepository.findById(updatePINRequestDTO.getWalletId())
                .orElseThrow(() -> new AppException(AppException.ErrorType.WALLET_NOT_FOUND));  // ------EXCEPTION--------

        walletMapper.updatePIN(updatePINRequestDTO,wallet);


        walletRepository.save(wallet);
    }


    //Check Balance
    public BigDecimal checkBalance(CheckBalanceRequestDTO dto) {
        Wallet wallet = walletRepository.findById(dto.getWalletId())
                .orElseThrow(() -> new AppException(AppException.ErrorType.WALLET_NOT_FOUND)); // EXCEPTION

        // Check PIN
        if (!passwordEncoder.matches(dto.getPin(), wallet.getPin())) {
            throw new AppException(AppException.ErrorType.INVALID_PIN);   //EXCEPTION
        }

        return wallet.getWalletBalance();
    }

    //Wallet Update - Deposit, Withdraw
    public String updateBalance(UpdateWalletRequestDTO dto) {
        Wallet wallet = walletRepository.findById(dto.getWalletId())
                .orElseThrow(() -> new AppException(AppException.ErrorType.WALLET_NOT_FOUND)); //EXCEPTION

        // Check PIN
        if (!passwordEncoder.matches(dto.getPin(), wallet.getPin())) {
            throw new AppException(AppException.ErrorType.INVALID_PIN);
        }

        String message;

        if ("deposit".equalsIgnoreCase(dto.getUpdateType())) {
            wallet.setWalletBalance(wallet.getWalletBalance().add(dto.getAmount()));
            message = "Deposit successful with "+dto.getAmount()+" amount.";
            transactionService.createTransaction(wallet, dto.getAmount(), TransactionType.DEPOSIT);
        }
        else if ("withdrawal".equalsIgnoreCase(dto.getUpdateType())) {
            if (wallet.getWalletBalance().compareTo(dto.getAmount()) < 0) {
                throw new AppException(AppException.ErrorType.INSUFFICIENT_BALANCE);  //EXCEPTION
            }
            wallet.setWalletBalance(wallet.getWalletBalance().subtract(dto.getAmount()));
            message = "Withdrawal successful with "+dto.getAmount()+" amount.";
            transactionService.createTransaction(wallet,dto.getAmount(), TransactionType.WITHDRAWAL);
        }
        else {
            throw new AppException(AppException.ErrorType.INVALID_UPDATE_TYPE); //EXCEPTION
        }

        walletRepository.save(wallet);
        return message;
    }



    //Wallet Transfer
    public String walletTransfer(TransferRequestDTO dto){

        Wallet senderWallet=walletRepository.findById(dto.getSenderWalletId())
                .orElseThrow(()->new AppException(AppException.ErrorType.WALLET_NOT_FOUND));

        Wallet receiverWallet=walletRepository.findById(dto.getReceiverWalletId())
                .orElseThrow(()->new AppException(AppException.ErrorType.WALLET_NOT_FOUND));

        if(!passwordEncoder.matches(dto.getPin(), senderWallet.getPin())){
            throw new AppException(AppException.ErrorType.INVALID_PIN);
        }

        if(senderWallet.getWalletBalance().compareTo(dto.getAmount())<0){
            throw new AppException(AppException.ErrorType.INSUFFICIENT_BALANCE);
        }


        senderWallet.setWalletBalance(senderWallet.getWalletBalance().subtract(dto.getAmount()));
        receiverWallet.setWalletBalance(receiverWallet.getWalletBalance().add(dto.getAmount()));

        walletRepository.save(senderWallet);
        walletRepository.save(receiverWallet);


        transactionService.createTransaction(senderWallet, dto.getAmount(), TransactionType.TRANSFER_IN);
        transactionService.createTransaction(receiverWallet, dto.getAmount(), TransactionType.TRANSFER_OUT);

        return "Transfer successful: " + dto.getAmount() + " transferred from Wallet ID " +
                dto.getSenderWalletId() + " to Wallet ID " + dto.getReceiverWalletId();

    }

    // Get Wallet by ID
    public WalletResponseDTO getWalletById(Long walletId) {
        Wallet wallet = walletRepository.findById(walletId)
                .orElseThrow(() -> new AppException(AppException.ErrorType.WALLET_NOT_FOUND));

        User user = userRepository.findById(wallet.getUser().getId())
                .orElseThrow(() -> new AppException(AppException.ErrorType.USER_NOT_FOUND));

        return walletMapper.mapToWalletResponseDTO(wallet, user);
    }

    // Get All Wallets
    public List<WalletResponseDTO> getAllWallets(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException(AppException.ErrorType.USER_NOT_FOUND));

        List<Wallet> wallets = walletRepository.findByUserId(userId);
        if (wallets.isEmpty()) {
            throw new AppException(AppException.ErrorType.WALLET_NOT_FOUND);
        }

        return wallets.stream()
                .map(wallet -> walletMapper.mapToWalletResponseDTO(wallet, user))
                .collect(Collectors.toList());
    }

   //Delete wallet
    public String deleteWallet(WalletDeleteRequestDTO dto) {

        Wallet wallet = walletRepository.findById(dto.getWalletId())
                .orElseThrow(() -> new AppException(AppException.ErrorType.WALLET_NOT_FOUND));


        if (!wallet.getAccNo().equals(dto.getAccNo()) || !passwordEncoder.matches(dto.getPin(), wallet.getPin())) {
            throw new AppException(AppException.ErrorType.INVALID_PIN);
        }
        // Optionally, mark wallet as deleted in the transaction records
        transactionService.updateDeletedWalletTransaction(dto.getWalletId());  // setting walletId = null in Transaction


        walletRepository.delete(wallet);

        return "Wallet deleted successfully.";
    }





}
