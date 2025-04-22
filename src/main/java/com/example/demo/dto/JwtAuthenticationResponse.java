package com.example.demo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Schema(description = "Ответ с токеном доступа")
@Builder
@AllArgsConstructor
public class JwtAuthenticationResponse {
    @Schema(description = "Токен доступа", example = "eyJhbI6MTYyMjUwNj...")
    private String token;
}
