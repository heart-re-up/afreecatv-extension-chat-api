package com.streamhelper.microservices.game.draw.security

import com.streamhelper.microservices.game.draw.configuration.properties.JwtProperty
import com.streamhelper.microservices.game.draw.model.entity.Wallet
import com.streamhelper.microservices.game.draw.repository.AccountRepository
import net.bytebuddy.utility.RandomString
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestConstructor
import org.springframework.test.context.TestPropertySource

@SpringBootTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@EnableConfigurationProperties(JwtProperty::class)
@TestPropertySource("classpath:application.yml")
internal class JwtProviderTest(
    @Autowired private val jwtProvider: JwtProvider,
    @Autowired private val accountRepo: AccountRepository
) {


    @Test
    fun `생성 및 검증 테스트`() {
        val email = RandomString.make(10) + '@' + RandomString.make(10) + ".com";
        val password = "1q2w3e4r5t"
        val username = "testusername"
        val authorities = listOf(
            "USER",
            "ADMIN",
            "SYSTEM",
        )
        val account = Wallet(email, password, username, authorities)
        accountRepo.save(account)

        Assertions.assertThat(account.name).isEqualTo(username)
        Assertions.assertThat(account.password).isEqualTo(password)
        Assertions.assertThat(account.email.value).isEqualTo(email)
//        Assertions.assertThat(user.name).isEqualTo(username)

//        val token = jwtProvider.generateToken(user)
//        Assertions.assertThat(jwtProvider.validateToken(token)).isEqualTo(true)
//        val authentication = jwtProvider.toAuthentication(token)
//        Assertions.assertThat(authentication.name).isEqualTo(username)
    }
}