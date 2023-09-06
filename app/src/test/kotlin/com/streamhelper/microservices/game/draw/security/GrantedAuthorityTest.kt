package com.streamhelper.microservices.game.draw.security

import com.streamhelper.microservices.game.draw.expansions.spring.hasAllAuthority
import com.streamhelper.microservices.game.draw.expansions.spring.hasAnyAuthority
import com.streamhelper.microservices.game.draw.expansions.spring.hasAuthority
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.security.core.authority.SimpleGrantedAuthority

class GrantedAuthorityTest {

    @Test
    fun testHasAuthority() {
        val list = listOf(
            SimpleGrantedAuthority("ROLE_USER"),
            SimpleGrantedAuthority("ROLE_ADMIN"),
        )
        Assertions.assertThat(list.hasAuthority(SimpleGrantedAuthority("ROLE_ADMIN"))).isEqualTo(true)
        Assertions.assertThat(list.hasAuthority("ROLE_ADMIN")).isEqualTo(true)
        Assertions.assertThat(list.hasAuthority("A")).isEqualTo(false)

        Assertions.assertThat(list.hasAnyAuthority("ROLE_ADMIN", "A")).isEqualTo(true)
        Assertions.assertThat(list.hasAnyAuthority("A", "B")).isEqualTo(false)

        Assertions.assertThat(list.hasAllAuthority("ROLE_USER", "ROLE_ADMIN")).isEqualTo(true)
        Assertions.assertThat(list.hasAllAuthority("ROLE_USER", "ROLE_ADMINAAA")).isEqualTo(false)
    }

}