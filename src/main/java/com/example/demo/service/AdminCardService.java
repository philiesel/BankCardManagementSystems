package com.example.demo.service;

import com.example.demo.dto.CardsDto;
import com.example.demo.dto.CreateCardDto;
import com.example.demo.dto.UpdateCardDto;
import com.example.demo.entity.CardEntity;
import com.example.demo.entity.CardStatus;
import com.example.demo.repository.CardRepository;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AdminCardService {
    private final CardRepository cardRepository;
    private final UserRepository userRepository;

    @Transactional
    public CardEntity createCard(CreateCardDto cardDto) {
        var user = userRepository.findById(cardDto.getUserId()).orElseThrow(() -> new UsernameNotFoundException("Данного пользователя нет в бд" + cardDto.getUserId()));
        var card = new CardEntity();
        card.setUser(user);
        card.setCardNumber(cardDto.getCardNumber());
        card.setCardholderName(cardDto.getCardholderName());
        card.setStatus(CardStatus.ACTIVE);
        card.setBalance(cardDto.getBalance());
        card.setExpiryDate(cardDto.getExpiryDate());
        return cardRepository.save(card);
    }

    @Transactional
    public CardEntity setBlockCard(Long id) {
        CardEntity cardEntity = cardRepository.findById(id).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Card not found with id " + id
        ));
        cardEntity.setStatus(CardStatus.BLOCKED);
        cardRepository.save(cardEntity);
        return cardEntity;
    }

    @Transactional
    public CardEntity setActivateCard(Long id) {
        CardEntity cardEntity = cardRepository.findById(id).orElseThrow(() -> new ResponseStatusException(
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

    @Transactional
    public void deleteCardById(Long id) throws RuntimeException {
        if (!cardRepository.existsById(id)) {
            new RuntimeException("Card not found with ID: " + id);
        }
        cardRepository.deleteById(id);
    }


    public CardEntity updateCard(Long id, UpdateCardDto updateCardDto) {
        CardEntity card = getCardById(id);
        card.setCardNumber(updateCardDto.getCardNumber());
        card.setExpiryDate(updateCardDto.getExpiryDate());
        card.setCardholderName(updateCardDto.getCardholderName());
        card.setBalance(updateCardDto.getBalance());
        card.setStatus(updateCardDto.getStatus());
        return card;
    }

    public List<CardsDto> getAllCards(Long id) {
        var user = userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));
        return user.getCards().stream().map(CardsDto::new).toList();
    }
}
