package com.streamhelper.microservices.game.draw.controller

import com.streamhelper.microservices.game.draw.model.dto.DTOLogin
import com.streamhelper.microservices.game.draw.model.dto.DTORefreshToken
import com.streamhelper.microservices.game.draw.model.ro.RoLogon
import com.streamhelper.microservices.game.draw.service.AuthService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@CrossOrigin
@RestController
@RequestMapping("/api/v1/auth")
class AuthController(
    private val authService: AuthService
) {

    @CrossOrigin
    @PostMapping("/login")
    fun login(@RequestBody @Valid body: DTOLogin): ResponseEntity<RoLogon> {
        return authService.login(body.email, body.password)
            .let { ResponseEntity.ok(it) }
    }

    @PostMapping("/token/refresh")
    fun refreshToken(@RequestBody @Valid body: DTORefreshToken) {

    }

}