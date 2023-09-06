package com.streamhelper.microservices.game.draw.repository

import com.streamhelper.microservices.game.draw.model.entity.Account
import com.streamhelper.microservices.game.draw.model.entity.embeddable.Email
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Repository
@Transactional(readOnly = true)
interface AccountRepository : JpaRepository<Account, UUID> {
    fun existsAccountById(id: UUID): Boolean
    fun existsAccountByEmailValue(email: String): Boolean

    fun findAccountByName(name: String): Account?
    fun findAccountByEmail(email: Email): Account?
    fun findAccountByEmailValue(email: String): Account?
    fun findAccountById(id: UUID): Account?
//    fun findAccountsByEmailEmailDomainPart(domainPart: String): Collection<Account>
}