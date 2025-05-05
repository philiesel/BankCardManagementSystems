package com.example.demo.controller;

import com.example.demo.dto.*;
import com.example.demo.entity.TransactionEntity;
import com.example.demo.service.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/users")
public class UserCardController {
    private final CardService cardService;

    /**
     * Получить карту принадлежащее пользователю
     *
     * @return созданная карта
     */
    @GetMapping("/{id}/cards")
    @ResponseStatus(HttpStatus.OK)
    public List<CardsDto> getUserCards(@PathVariable Long id) {
        List<CardsDto> cards = cardService.getUserCards(id);
        return cards.stream()
                .map(x -> new CardsDto(
                        x.getCardNumber(),
                        x.getCardholderName(),
                        x.getExpiryDate(),
                        x.getStatus(),
                        x.getBalance()))
                .toList();
    }

//    /**
//     * Внесение суммы на карту
//     *
//     * @return обновленный баланс
//     */
//    @PatchMapping("/user-id/cards/{cardId)/add-cash/{amount}")
//    public BalanceCardDto addCashToCard(
//            @PathVariable("user-id") Long userId,
//            @PathVariable("card-id") Long cardId,
//            @PathVariable("amount") BigDecimal amount
//    ) {
//
//    }
    /**
     * Запрос на списание средств
     *
     * @return
     */
    @PostMapping("cards/{id}/{amount}")
    public statusTransWithdrawDto withdrawCash(
            @PathVariable("id") Long id,
            @PathVariable("amount")BigDecimal amount) {
        return cardService.withdrawCash(id, amount);
    }
    /**
     * Запрос на блокировку карты
     *
     * @return статус операции
     */
    @PatchMapping("/{user-id}/cards/status")
    @ResponseStatus(HttpStatus.OK)
    public CardChangeStatusDto requestBlockCard(@PathVariable("user-id") Long id) {
        return cardService.requsetBlockCard(id);
    }

    /**
     * Перевод на другую свою карту
     *
     * @return транзакция
     */
    @PatchMapping("/{user-id}/cards/transfer")
    public ResponseEntity<CardStatusTransactionDto> transferCardToCard(
            @PathVariable("user-id") Long userId,
            @RequestBody TransferCardToCardDto transfer
    ) {
        CardStatusTransactionDto statusTransaction = cardService.transferCardToCard(userId, transfer);
        return ResponseEntity.status(HttpStatus.OK).body(statusTransaction);
    }

    /**
     * Просмотр истории транзакций по карте
     *
     * @return транзакции по карте
     */
    @GetMapping("/cards/{card-id}/transactions")
    public List<TransactionEntity> getCardTransactions(
            @PathVariable("card-id") Long cardId) {
            return cardService.getCardHistoryTransactionals(cardId);
    }
}
