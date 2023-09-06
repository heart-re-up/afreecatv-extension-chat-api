package com.streamhelper.microservices.game.draw.security

import com.streamhelper.microservices.game.draw.configuration.properties.JwtOptionProperty
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import java.time.Duration
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.*

class JwtOptions(property: JwtOptionProperty) {

    val issuer = property.issuer
    val secretKey = Decoders.BASE64.decode(property.secret).let { Keys.hmacShaKeyFor(it) }
    val algorithm = SignatureAlgorithm.HS256
    val validityDurationAccess = property.tokenValidityToAccess
    val validityDurationRefresh = property.tokenValidityToRefresh
    val zoneId = ZoneId.of(property.tokenValidityTimezone)
    val headerName = property.headerName
    val headerPrefix = property.headerPrefix

    fun nowByZonId(zoneId: ZoneId? = null) = ZonedDateTime.now(zoneId ?: this.zoneId)

    /**
     * 토큰 발급일 계산
     */
    fun computeIssueAt(now: ZonedDateTime? = null): Date = (now ?: ZonedDateTime.now())
        .toInstant()
        .let { Date.from(it) }

    /**
     * 토큰 만료일 계산
     */
    fun computeExpireAt(now: ZonedDateTime? = null, duration: Duration? = null): Date = (now ?: ZonedDateTime.now())
        .plus(duration ?: validityDurationAccess)
        .toInstant()
        .let { Date.from(it) }
}