package com.example.demo.service.BankCardManagementSystems;

import com.example.demo.entity.BankCardManagementSystems.CardEntity;
import com.example.demo.entity.BankCardManagementSystems.CardStatus;
import com.example.demo.repository.BankCardManagementSystems.CardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@RequiredArgsConstructor
@Service
public class CardManagementService {
    private final CardRepository cardRepository;

    public CardEntity createCard(CardEntity cardEntity) {
         return cardRepository.save(cardEntity);
    }

    public CardEntity setBlockCard(Long id) {
        CardEntity cardEntity = cardRepository.findById(id).orElseThrow(()-> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Card not found with id " + id
        ));
        cardEntity.setStatus(CardStatus.BLOCKED);
        cardRepository.save(cardEntity);
        return cardEntity;
    }

    public CardEntity setActivateCard(Long id) {
        CardEntity cardEntity = cardRepository.findById(id).orElseThrow(()-> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Card not found with id " + id
        ));
        cardEntity.setStatus(CardStatus.ACTIVE);
        cardRepository.save(cardEntity);
        return cardEntity;
    }

    public CardEntity getCardById(Long id) {
        return cardRepository.findById(id).orElseThrow(() -> new RuntimeException("Card not found with ID: " + id));
    }

    public void deleteCardById(Long id) throws RuntimeException{
         if(!cardRepository.existsById(id)) {
             new RuntimeException("Card not found with ID: " + id);
         }
         cardRepository.deleteById(id);
    }


}
