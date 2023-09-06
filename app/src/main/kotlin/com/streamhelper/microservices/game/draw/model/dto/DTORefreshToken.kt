package com.streamhelper.microservices.game.draw.model.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class DTORefreshToken(
    @field:NotNull(message = "갱신 토큰은 null 이어서는 안됩니다.")
    @field:NotBlank(message = "갱신 토큰은 비어서는 안됩니다.")
    val refreshToken: String
)
