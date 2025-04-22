package com.example.demo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(description = "Запрос на регистрацию")
public class SingUpRequestDto {
    @Schema(description = "Email пользователя", example = "Karl@mail.ru")
    @NotBlank(message = "Email пользователя не может быть пустыми")
    @Email(message = "Email адрес должен быть в формате user@example.com")
    private String email;
    @Schema(description = "Пароль пользователя", example = "1234")
    @NotBlank(message = "Пароль пользователя не может быть пустыми")
    private String password;
}
