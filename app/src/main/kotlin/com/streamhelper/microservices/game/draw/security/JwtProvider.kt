package com.streamhelper.microservices.game.draw.security

import com.tinteccnc.util.MyLogger
import com.streamhelper.microservices.game.draw.expansions.toUUID
import com.streamhelper.microservices.game.draw.model.entity.Account
import com.streamhelper.microservices.game.draw.model.ro.RoToken
import com.streamhelper.microservices.game.draw.model.business.security.AccountAuthentication
import com.streamhelper.microservices.game.draw.model.business.security.AccountPrincipal
import com.streamhelper.microservices.game.draw.service.AccountDetailsService
import com.streamhelper.microservices.game.draw.util.DateTimeUtil
import io.jsonwebtoken.*
import io.jsonwebtoken.security.SecurityException
import jakarta.servlet.http.HttpServletRequest
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import java.time.ZonedDateTime
import java.util.*

/**
 * JWT 제공자 입니다.
 * [JwtOptions] 마다 여러 제공자를 생성할 수 있기 때문에, [org.springframework.stereotype.Component] 로 등록하지 않고,
 * 이 클래스의 인스턴스를 [org.springframework.context.annotation.Bean] 으로 사용합니다.
 */
class JwtProvider(
    private val options: JwtOptions,
    private val accountDetailService: AccountDetailsService
) : MyLogger {

    private val parser = Jwts.parserBuilder()
        .setSigningKey(options.secretKey)
        .build()

    fun generateTokens(account: Account): RoToken {
        // 동일 시간을 기준으로 토큰을 생성할 수 있도록 미리 시간값을 구합니다.
        val now = ZonedDateTime.now()
        val accessToken =
            generateToken("accessToken", account, DateTimeUtil.computeExpiration(now, options.validityDurationAccess))
        val refreshToken =
            generateToken("refreshToken", account, DateTimeUtil.computeExpiration(now, options.validityDurationRefresh))
        return RoToken(accessToken, refreshToken)
    }

    /**
     * - subject: 토큰으 이름
     * - audience: 토큰의 대상. 즉 [Account.id] 입니다. 계정아이디,이름, 이메일과 같은 사용자를 특정할 수 있는 값을 이용해서는 안됩니다.
     */
    fun generateToken(subject: String, account: Account, expiration: Date) = options.nowByZonId().let { now ->
        // 토큰에 PII(개인정보)를 넣지 않도록 주의합니다.
        // 개인정보에는 이름, 생일, 전화번호, 주소, 이메일 등 유추 가능한 정보가 있습니다.
        // accountId 같은 경우에는 개인 유추가 불가능한 정보이기 때문에
        Jwts.builder()
            .setId(UUID.randomUUID().toString())
            .setSubject(subject) // sub
            .setAudience(account.id.toString()) // aud
            .setIssuer(options.issuer) // iss. 보통은 도메인.
            .setIssuedAt(options.computeIssueAt(now)) // iat
            .claim("roles", account.authorities.map { it.authority })
            .setExpiration(expiration)
            .signWith(options.secretKey, options.algorithm)
            .also { debug(it.toString()) }
            .compact()
    }

    fun generateToken(subject: String, accountId: UUID, authorities: Collection<GrantedAuthority>, expiration: Date) =
        options.nowByZonId().let { now ->
            // 토큰에 PII(개인정보)를 넣지 않도록 주의합니다.
            // 개인정보에는 이름, 생일, 전화번호, 주소, 이메일 등 유추 가능한 정보가 있습니다.
            // accountId 같은 경우에는 개인 유추가 불가능한 정보이기 때문에
            Jwts.builder()
                .setId(UUID.randomUUID().toString())
                .setSubject(subject) // sub
                .setAudience(accountId.toString()) // aud
                .setIssuer(options.issuer) // iss. 보통은 도메인.
                .setIssuedAt(options.computeIssueAt(now)) // iat
                .claim("roles", authorities.map { it.authority })
                .setExpiration(expiration)
                .signWith(options.secretKey, options.algorithm)
                .also { debug(it.toString()) }
                .compact()
        }

    fun refreshToken(refreshToken: String): RoToken {
        val claims = parseClaims(refreshToken)
        val accountId = claims.audience.toUUID()
        val now = ZonedDateTime.now()
        val authentication = accountDetailService.loadUserByAccountId(accountId)
        return generateTokens(authentication.account)
    }

    fun toAuthentication(token: String): Authentication? {
        val claims = parseClaims(token) ?: return null
        val accountId = claims.audience.toUUID()
        val authorities = claims["roles"]
            .let { it as Collection<String> }
            .map(::SimpleGrantedAuthority)
        debug("$accountId is authenticated with roles [${authorities.joinToString(", ")}]")
        return AccountAuthentication(AccountPrincipal(accountId), token, authorities)
    }

    fun resolveToken(request: HttpServletRequest) = request
        .getHeader(options.headerName)?.trim()
        .takeUnless { it == null }
        ?.takeUnless { it.isBlank() }
        ?.takeIf { it.startsWith(options.headerPrefix) }
        ?.let { it.substring(options.headerPrefix.length) }

    @Throws(
        SecurityException::class,
        MalformedJwtException::class,
        ExpiredJwtException::class,
        UnsupportedJwtException::class,
        IllegalArgumentException::class,
        Exception::class
    )
    private fun parseClaims(token: String) = try {
        parser.parseClaimsJws(token).body
    } catch (e: Exception) {
        throw e.also {
            when (it) {
                is SecurityException -> debug("잘못된 JWT 서명.")
                is MalformedJwtException -> debug("잘못된 JWT 구조.")
                is ExpiredJwtException -> debug("만료된 JWT 토큰.")
                is UnsupportedJwtException -> debug("지원하지 않는 JWT 토큰.")
                is IllegalArgumentException -> debug("유효하지 않은 JWT 토큰.")
                else -> debug(e.message ?: "unknown error from parsing JWT")
            }
        }
    }

    /**
     * 파싱된 클레임의 유효성을 검사합니다.
     */
    fun validateClaims(claims: Claims) {
    }

}