package com.streamhelper.microservices.game.draw.configuration.properties

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.NestedConfigurationProperty
import java.time.Duration

/**
 * application 프로퍼티에서 jwt 로 시작하는 프로퍼티를 정의합니다.
 */
@ConfigurationProperties(prefix = "jwt")
data class JwtProperty(

    @NestedConfigurationProperty
    var token: JwtOptionProperty,

    )

/**
 * jwt 프로퍼티 관련해서 각 설정에 대한 하위 데이터 입니다.
 *
 * - validity 의 경우 Converting Durations 를 지원합니다. 다음 단위 중 어느 것을 사용해도 됩니다:
 *      - ns for nanoseconds
 *      - us for microseconds
 *      - ms for milliseconds
 *      - s for seconds
 *      - m for minutes
 *      - h for hours
 *      - d for days
 */
data class JwtOptionProperty(
    @field:NotBlank
    var issuer: String,

    @field:NotBlank
    @field:Size(message = "HS512 를 위해서 비밀키는 반드시 64자 이어야 합니다.", min = 64, max = 64)
    var secret: String,

    @field:NotNull
    var tokenValidityToAccess: Duration,

    @field:NotNull
    var tokenValidityToRefresh: Duration,

    @field:NotBlank
    var tokenValidityTimezone: String = "UTC",

    @field:NotBlank
    var headerName: String = "Authorization",

    @field:NotBlank
    var headerPrefix: String = "Bearer ",
)