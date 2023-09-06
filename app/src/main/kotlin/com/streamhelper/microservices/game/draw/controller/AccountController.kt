package com.streamhelper.microservices.game.draw.controller

import com.streamhelper.microservices.game.draw.expansions.spring.accountAuthorities
import com.streamhelper.microservices.game.draw.expansions.spring.hasAnyAuthority
import com.streamhelper.microservices.game.draw.expansions.spring.principal
import com.streamhelper.microservices.game.draw.expansions.spring.resourceLocation
import com.streamhelper.microservices.game.draw.expansions.toUUID
import com.streamhelper.microservices.game.draw.model.dto.DTOCreateAccount
import com.streamhelper.microservices.game.draw.model.ro.RoAccount
import com.streamhelper.microservices.game.draw.service.AccountService
import com.tinteccnc.util.MyLogger
import jakarta.validation.Valid
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.annotation.Secured
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/accounts")
class AccountController(
    private val accountService: AccountService
) : MyLogger {

    ///////////////////////////////////////////////////////////////////////////
    // 관리자 수준에서 사용하는 API
    ///////////////////////////////////////////////////////////////////////////

    /**
     * 관리자 수준의 계정만 다른 계정에 접근할 수 있다.
     */
    private fun canAccessOther() = accountAuthorities.hasAnyAuthority("ROLE_ADMIN", "ROLE_SU").also {
        info(
            "${principal.name} can access other account? $it"
        )
    }

    @PostMapping
    @Secured("ROLE_ADMIN", "ROLE_SU")
    fun createAccount(@RequestBody @Valid body: DTOCreateAccount) =
        accountService.createAccount(body.name, body.password, body.email, listOf("USER"))
            .let { ResponseEntity.created(resourceLocation(it.id)) }
            .build<Unit>()

    @GetMapping
    @Secured("ROLE_ADMIN", "ROLE_SU")
    fun getAccounts() =
        // 남의 정보를 조회할 수 있는 권한이 없다면 403
        if (!canAccessOther()) ResponseEntity.status(HttpStatus.FORBIDDEN).build<Unit>()
        // 찾으면 200, 못찾으면 404
        else accountService.findAccounts()
            ?.let { ResponseEntity.ok(it) } // 200
            ?: ResponseEntity.notFound() // 400

    @GetMapping("/find")
    @Secured("ROLE_ADMIN", "ROLE_SU")
    fun findAccountByEmail(@RequestParam @Valid @NotNull @NotBlank email: String) =
        // 남의 정보를 조회할 수 있는 권한이 없다면 403
        if (!canAccessOther()) ResponseEntity.status(HttpStatus.FORBIDDEN).build<Unit>()
        // 찾으면 200, 못찾으면 404
        else accountService.findAccountByEmail(email)
            ?.let { ResponseEntity.ok(it) } // 200

    @GetMapping("/{id}")
    @Secured("ROLE_USER", "ROLE_ADMIN")
    fun getAccount(@PathVariable @Valid @NotNull @NotBlank id: String) =
        // 남의 정보를 조회할 수 있는 권한이 없다면 403
        if (!canAccessOther()) ResponseEntity.status(HttpStatus.FORBIDDEN).build<Unit>()
        // 찾으면 200, 못찾으면 404
        else accountService.findAccountById(id.toUUID())
            ?.let { ResponseEntity.ok(RoAccount(it)) } // 200
            ?: ResponseEntity.notFound() // 400
    // 권한이 없으면 403

    @PatchMapping("/{id}")
    @Secured("ROLE_USER")
    fun updateAccount(@RequestParam @Valid @NotNull @NotBlank id: String) {
        TODO("")
    }

    @DeleteMapping("/{id}")
    @Secured("ROLE_USER")
    fun suspendAccount(@RequestParam @Valid @NotNull @NotBlank id: String) {
        TODO("")
    }

}