package com.streamhelper.microservices.game.draw.configuration

import com.streamhelper.microservices.game.draw.configuration.properties.JwtProperty
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestConstructor
import org.springframework.test.context.TestPropertySource
import java.time.Duration

@SpringBootTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@EnableConfigurationProperties(JwtProperty::class)
@TestPropertySource("classpath:application.yml")
internal class JwtConfigTest(
    val jwtProperty: JwtProperty
) {
    @Test
    fun `jwt 프로퍼티 테스트`() {
        Assertions.assertThat(jwtProperty.token.headerName).isEqualTo("Authorization")
        Assertions.assertThat(jwtProperty.token.secret).isEqualTo("1q2w3e4r")
        Assertions.assertThat(jwtProperty.token.tokenValidityToAccess).isEqualTo(Duration.ofSeconds(86400))
    }
}