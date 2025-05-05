package com.example.demo.controller;

import com.example.demo.dto.CardChangeStatusDto;
import com.example.demo.dto.CardsDto;
import com.example.demo.dto.CreateCardDto;
import com.example.demo.dto.UpdateCardDto;
import com.example.demo.entity.CardEntity;
import com.example.demo.service.AdminCardService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("api/v1/admin/cards")
@RequiredArgsConstructor
public class AdminCardController {
    private final AdminCardService adminCardService;

    /**
     * Создать карту
     *
     * @return созданная карта
     */
    //@ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/create")
    public ResponseEntity<CreateCardDto> createNewCard(@Valid @RequestBody CreateCardDto createCardDto) {
        CardEntity cardEntity = adminCardService.createCard(createCardDto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(cardEntity.getId())
                .toUri();
        return ResponseEntity.created(location).body(new CreateCardDto(cardEntity));
    }

    /**
     * Блокировать карту
     *
     * @return номер и статус карты
     */
    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    @PatchMapping("/{id}/block")
    public ResponseEntity<CardChangeStatusDto> setBlockCard(@PathVariable @NotNull Long id) {
        CardEntity cardEntity = adminCardService.setBlockCard(id);
        CardChangeStatusDto cardChangeStatusDto = new CardChangeStatusDto(
                cardEntity.getCardNumber(), cardEntity.getStatus()
        );
        return ResponseEntity.ok(cardChangeStatusDto);
    }

    /**
     * Активировать карту
     *
     * @return номер и статус карты
     */
    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    @PatchMapping("/{id}/activate")
    public ResponseEntity<CardChangeStatusDto> setActiveCard(@PathVariable @NotNull Long id) {
        CardEntity cardEntity = adminCardService.setActivateCard(id);
        CardChangeStatusDto cardChangeStatusDto = new CardChangeStatusDto(
                cardEntity.getCardNumber(), cardEntity.getStatus()
        );
        return ResponseEntity.ok(cardChangeStatusDto);
    }


    @GetMapping("/card/{id}")
    public CardEntity getCardById(@PathVariable Long id) {
        return adminCardService.getCardById(id);
    }

    /**
     * Удалить карту
     *
     * @return удаленную карту
     */
    @DeleteMapping("/{id}/delete")
    public ResponseEntity<CardChangeStatusDto> deleteCardById(@PathVariable Long id) {
        try {
            adminCardService.deleteCardById(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException exception) {  //TODO: заменить на свой exception
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Просматривать все карты
     *
     * @return Список всех карт
     */
    @GetMapping("/{id}/all-cards")
    public List<CardsDto> getAllCards(@PathVariable Long id) {
        return adminCardService.getAllCards(id);
    }

    /**
     * Обновить карту
     *
     * @return обновленная карта
     */
    @PatchMapping("/{id}")  //TODO temp delete later
    public ResponseEntity<UpdateCardDto> updateCard(@PathVariable Long id, @RequestBody UpdateCardDto updateCardDto) {
        CardEntity cardUpdated = adminCardService.updateCard(id, updateCardDto);
        return ResponseEntity.ok().body(new UpdateCardDto(cardUpdated));
    }
}
