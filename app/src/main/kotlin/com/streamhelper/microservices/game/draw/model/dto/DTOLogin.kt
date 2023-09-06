package com.streamhelper.microservices.game.draw.model.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class DTOLogin(
    @field:NotBlank
    @field:Size(min = 8, max = 32, message = "비밀번호는 8자 이상 32자 이하입니다.")
    val email: String,

    @field:NotBlank
    @field:Size(min = 8, max = 32, message = "비밀번호는 8자 이상 32자 이하입니다.")
    val password: String,
)
