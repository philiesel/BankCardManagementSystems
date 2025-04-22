package com.example.demo.auth;

import com.example.demo.controller.AuthController;
import com.example.demo.dto.JwtAuthenticationResponse;
import com.example.demo.dto.SingUpRequestDto;
import com.example.demo.service.JwtAuthenticationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
@AutoConfigureMockMvc
//@WithMockUser(username = "test", roles = "USER")
public class AuthControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private JwtAuthenticationService jwtService;
    @InjectMocks
    private AuthController authController;
    private ObjectMapper objectMapper;
    @Value("${token.signing.key}")
    private String token;

    @BeforeEach
    public void jsonRegUser() {
        objectMapper = new ObjectMapper();
    }

    @Test
    public void shouldReturnJwtTokenWhenUserReg() throws Exception {
        SingUpRequestDto singUpRequestDto = new SingUpRequestDto();
        singUpRequestDto.setEmail("bob@mail.ru");
        singUpRequestDto.setPassword("1234");
        JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse(token);
        when(jwtService.jwtAuthentication(singUpRequestDto)).thenReturn(jwtAuthenticationResponse);
        mockMvc.perform(post("/api/v1/auth/registration")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(singUpRequestDto)))
                .andDo(print())
                .andExpect(jsonPath("$.token").value(token));
    }
}
