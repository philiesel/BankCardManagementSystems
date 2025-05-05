package com.example.demo.service;

import com.example.demo.dto.*;
import com.example.demo.entity.CardEntity;
import com.example.demo.entity.CardStatus;
import com.example.demo.entity.TransactionEntity;
import com.example.demo.entity.User;
import com.example.demo.repository.CardRepository;
import com.example.demo.repository.TransactionRepository;
import com.example.demo.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class CardService {
    private final CardRepository cardRepository;
    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;

    /**
     * Список карт по User id
     *
     * @return список карт принадлежащих User
     */
    public List<CardsDto> getUserCards(Long id) {
        User user = findUserById(id);
        return cardRepository.findByUser(user).stream()
                .map(x -> new CardsDto(
                        x.getCardNumber(),
                        x.getCardholderName(),
                        x.getExpiryDate(),
                        x.getStatus(),
                        x.getBalance()
                ))
                .collect(Collectors.toList());
    }

    /**
     * Блокировка карты по id
     *
     * @return номер карты и статус
     */
    @Transactional
    public CardChangeStatusDto requsetBlockCard(Long id) { //TODO подумать о запросе блокировки
        CardEntity card = cardRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("Нет карты по данному " + id));
        if (card.getStatus() != CardStatus.BLOCKED) {
            card.setStatus(CardStatus.BLOCKED);
            cardRepository.save(card);
            return new CardChangeStatusDto(card.getCardNumber(), card.getStatus());
        }
        return new CardChangeStatusDto(card.getCardNumber(), card.getStatus());
    }

    public User findUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("Нет данного пользователя " + id));
    }

    /**
     * Переводы между своими картами
     *
     * @return созданная карта
     */
    @Transactional
    public CardStatusTransactionDto transferCardToCard(Long userId, TransferCardToCardDto transfer) {   //TODO ПЕРЕПИСАТЬ
        Long fromCardId = transfer.getFromCardId();
        Long toCardId = transfer.getToCardId();
        BigDecimal amount = transfer.getAmount();
        var user = userRepository.findById(userId).orElseThrow(() -> new UsernameNotFoundException("Нет данного пользователя"));
        List<CardEntity> cardsUser = user.getCards(); //TODO delete
        var fromCard = cardRepository.findById(fromCardId).orElseThrow(() -> new UsernameNotFoundException("Нет данной карты"));
        var toCard = cardRepository.findById(toCardId).orElseThrow(() -> new UsernameNotFoundException("Нет данной карты"));
        if (fromCard.getBalance().compareTo(amount) < 0) {
            throw new IllegalArgumentException("Недостаточно средств");
        }
        BigDecimal newAmountFromCard = fromCard.getBalance().subtract(amount);
        fromCard.setBalance(newAmountFromCard);
        BigDecimal newAmountToCard = toCard.getBalance().add(amount);
        toCard.setBalance(newAmountToCard);
        TransactionEntity transactionEntity = new TransactionEntity();
        transactionEntity.setFromCard(String.valueOf(fromCard.getCardNumber()));
        transactionEntity.setToCard(String.valueOf(toCard.getCardNumber()));
        transactionEntity.setAmountTransfer(amount);
        transactionEntity.setStatus("Перевод осуществлен");
        transactionEntity.setTimestamp(LocalDateTime.now());
        transactionRepository.save(
                transactionEntity
        );
        //TODO МЕТОД слишком много действий делает
        //TODO сохраняем транзакцию и переводим в dto и отправляем
        CardStatusTransactionDto cardStatusTransaction = CardStatusTransactionDto.builder()
                .fromCard(String.valueOf(fromCard.getCardNumber()))
                .toCard(String.valueOf(toCard.getCardNumber()))
                .amountTransfer(String.valueOf(amount))
                .status("Перевод осуществлен")
                .date(LocalDate.now())
                .build();
        return cardStatusTransaction;
    }



    /**
     * Просматривать историю транзакций по своим картам
     *
     * @return список транзакций
     */
    public List<TransactionEntity> getCardHistoryTransactionals(Long cardId) {
        return transactionRepository.findAllTransactionById(cardId);  // вернуть лист TransactionDto
    }
    private boolean checkDayLimitTransaction() {}
    private boolean checkMonthLimitTransaction() {}

    private boolean checkLimitTransaction(Long id, BigDecimal amount) {
        var card = cardRepository.findById(id).orElseThrow(
                ()-> new NoSuchElementException("Данной карты с " + id + "не существует"));
        //запрос транзакции за день сколько есть
        // запрос транзакции за месяц сколько есть
        // amount суммировать с кол-вом совершенных проверить дневного лимита
        // amount суммировать с кол-вом совершенных проверить месячного лимита

    }

    public statusTransWithdrawDto withdrawCash(Long id, BigDecimal amount) {
         if(checkLimitTransaction(id, amount)) {

         }
    }
}