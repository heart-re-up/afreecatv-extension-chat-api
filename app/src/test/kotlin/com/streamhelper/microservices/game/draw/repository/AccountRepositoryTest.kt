package com.streamhelper.microservices.game.draw.repository

import com.streamhelper.microservices.game.draw.model.entity.Wallet
import net.bytebuddy.utility.RandomString
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest

@DataJpaTest
class AccountRepositoryTest(
    @Autowired val repo: AccountRepository
) {

    @Test
    @DisplayName("사용자가 잘 저장되는지 확인")
    fun testSave() {
        val email = RandomString.make(10) + '@' + RandomString.make(10) + ".com";
        val password = "1q2w3e4r5t"
        val password2 = "1234567890"
        val username = "testusername"
        val authorities = listOf(
            "USER",
            "ADMIN",
            "SYSTEM",
        )
        val account = Wallet(email, password, username, authorities)
        val saved = repo.save(account)

        Assertions.assertThat(saved.email.value).isEqualTo(email)
        Assertions.assertThat(saved.password).isEqualTo(password)
        Assertions.assertThat(saved.name).isEqualTo(username)

        // JPA 에서 기존 객체에 ID 를 추가 했기 때문에 같은 객체로 판단해야 한다.
        Assertions.assertThat(saved).isSameAs(account)

        saved.password = password2
    }
}