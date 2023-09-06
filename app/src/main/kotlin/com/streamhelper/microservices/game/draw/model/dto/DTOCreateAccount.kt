package com.streamhelper.microservices.game.draw.model.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class DTOCreateAccount(
    @field:NotBlank
    val name: String,
    @field:NotBlank
    @field:Size(min = 8, message = "비밀번호는 10자 이상입니다.")
    @field:Size(max = 32, message = "비밀번호는 32자 이하입니다.")
    val password: String,
    @field:NotBlank
    val email: String
)
