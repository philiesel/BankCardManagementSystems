package com.example.demo.controller;

import com.example.demo.dto.CardDto;
import com.example.demo.entity.CardEntity;
import com.example.demo.service.CardManagementService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1")
@RequiredArgsConstructor
public class CardController {
    private final CardManagementService cardManagementService;

    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/create")
    public CardEntity createNewCard(@RequestBody CardEntity cardEntity) {
        return cardManagementService.createCard(cardEntity);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PatchMapping("/{id}/block")
    public ResponseEntity<CardDto> setBlockCard(@PathVariable @NotNull Long id) {
        CardEntity cardEntity = cardManagementService.setBlockCard(id);
        CardDto cardDto = new CardDto(
                cardEntity.getCardNumber(), cardEntity.getStatus()
        );
        return ResponseEntity.ok(cardDto);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PatchMapping("/{cardId}/activate")
    public ResponseEntity<CardDto> setActiveCard(@PathVariable @NotNull Long cardId) {
        CardEntity cardEntity = cardManagementService.setActivateCard(cardId);
        CardDto cardDto = new CardDto(
                cardEntity.getCardNumber(), cardEntity.getStatus()
        );
        return ResponseEntity.ok(cardDto);
    }

    @GetMapping("/getCard/{id}")
    public CardEntity getCardById(@PathVariable Long id) {
        return cardManagementService.getCardById(id);
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<?> deleteCardById(@PathVariable Long id) {
        try {
            cardManagementService.deleteCardById(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException exception) {  //TODO: заменить на свой exception
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/updateCard/{id}")
    public CardEntity updateCard(@PathVariable Long id) {
        return null;
    }

    @GetMapping("/addres")
    public String greet() {
        System.out.println(new BCryptPasswordEncoder().encode("name"));
        return "hello";
    }

}
