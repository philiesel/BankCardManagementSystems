package com.example.demo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(description = "Запрос на аутентификацию")
public class SingInRequestDto {
    @Schema(description = "Email пользователя", example = "Karl@mail.ru")
    @NotBlank(message = "Email пользователя не может быть пустыми")
    private String email;

    @Schema(description = "Пароль", example = "1234")
    @NotBlank(message = "Пароль не может быть пустыми")
    private String password;
}
