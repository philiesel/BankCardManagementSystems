package com.example.demo;

import com.example.demo.controller.CardController;
import com.example.demo.entity.CardEntity;
import com.example.demo.entity.CardStatus;
import com.example.demo.service.CardManagementService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CardControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private CardManagementService cardManagementService;
    @InjectMocks
    private CardController cardController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void checkCreateCard() throws Exception {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate parsedDate = LocalDate.parse("13.04.2026", formatter);
        CardEntity cardEntity = new CardEntity();
        cardEntity.setCardNumber(123);
        cardEntity.setCardholderName("John");
        cardEntity.setExpiryDate(parsedDate);
        cardEntity.setStatus(CardStatus.ACTIVE);
        cardEntity.setBalance(BigDecimal.valueOf(0));
        when(cardManagementService.createCard(any(CardEntity.class))).thenReturn(cardEntity);
        mockMvc.perform(post("/api/v1/createCard")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"cardholderName\":\"John\", " +
                                "   \"cardNumber\":\"123\", " +
                                "   \"expiryDate\":\"13.04.2026\", " +
                                "   \"status\":\"ACTIVE\"," +
                                "    \"balance\":0}")
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.cardholderName").value("John"));
    }

    @Test
    public void getCardById() throws Exception {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate parsedDate = LocalDate.parse("13.04.2026", formatter);
        CardEntity cardEntity2 = new CardEntity();
        cardEntity2.setId(1L);
        cardEntity2.setCardNumber(1234);
        cardEntity2.setCardholderName("Marta");
        cardEntity2.setExpiryDate(parsedDate);
        cardEntity2.setStatus(CardStatus.BLOCKED);
        cardEntity2.setBalance(BigDecimal.valueOf(100));

        when(cardManagementService.getCardById(1L)).thenReturn(cardEntity2);
        mockMvc.perform(get("/api/v1/getCard/1"))
                .andExpect(status().isOk()) // 200
                .andExpect(jsonPath("$.cardholderName").value("Marta"))
                .andExpect(jsonPath("$.status").value("BLOCKED"))
                .andExpect(jsonPath("$.cardNumber").value(1234));
    }

    @Test
    public void deleteCardById() throws Exception{
        doNothing().when(cardManagementService).deleteCardById(1L);
        mockMvc.perform(delete("/api/v1/deleteCard/1"))
                .andExpect(status().isNoContent()); 
    }
}
