package com.uade.grupo4.backend_ecommerce.service.implementations;

import com.uade.grupo4.backend_ecommerce.controller.dto.CartDto;
import com.uade.grupo4.backend_ecommerce.controller.dto.ProductDto;
import com.uade.grupo4.backend_ecommerce.controller.dto.ProfileDto;
import com.uade.grupo4.backend_ecommerce.controller.dto.TransactionDto;
import com.uade.grupo4.backend_ecommerce.repository.ProfileRepository;
import com.uade.grupo4.backend_ecommerce.repository.TransactionRepository;
import com.uade.grupo4.backend_ecommerce.repository.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProfileService {
    @Autowired
    private ProfileRepository profileRepository;

    public ProfileDto getProfile(Long userId) {
        Profile profile = profileRepository.findById(userId).orElseThrow();
        return convertProfileToDTO(profile);
    }

    private ProfileDto convertProfileToDTO(Profile profile) {
        ProfileDto profileDTO = new ProfileDto();
        profileDTO.setId(profile.getId());
        profileDTO.setName(profile.getFirstName());
        profileDTO.setLastName(profile.getLastName());
        profileDTO.setEmail(profile.getEmail());
        profileDTO.setTransactions(convertTransactionsToDTO(profile.getTransactions()));
        return profileDTO;
    }

    private List<TransactionDto> getTransactionsForProfile(Long profileId) {
        List<Transaction> transactions = TransactionRepository.findByProfileId(profileId);
        return convertTransactionsToDTO(transactions);
    }

    private List<TransactionDto> convertTransactionsToDTO(List<Transaction> transactions) {
        List<TransactionDto> transactionDTOs = new ArrayList<>();
        for (Transaction transaction : transactions) {
            TransactionDto transactionDTO = new TransactionDto();
            transactionDTO.setId(transaction.getId());
            transactionDTO.setCart(convertCartToDTO(transaction.getCart()));
            transactionDTO.setTransactionDate(transaction.getTransactionDate());
            transactionDTOs.add(transactionDTO);
        }
        return transactionDTOs;
    }

    private CartDto convertCartToDTO(CartItem cart) {
        if (cart == null) {
            throw new RuntimeException("Carrito no encontrado");
        }
        CartDto cartDTO = new CartDto();
        cartDTO.setId(cart.getId());
        cartDTO.setItems(convertProductsToDTO(cart.getItems())); // Cambio aquí
        return cartDTO;
    }

    private List<ProductDto> convertProductsToDTO(List<Product> products) {
        List<ProductDto> productDTOs = new ArrayList<>();
        for (Product product : products) {
            ProductDto productDTO = new ProductDto();
            productDTO.setId(product.getId());
            productDTO.setName(product.getName());
            productDTO.setDescription(product.getDescription());
            productDTO.setPrice(product.getPrice());
            productDTOs.add(productDTO);
        }
        return productDTOs;
    }
}