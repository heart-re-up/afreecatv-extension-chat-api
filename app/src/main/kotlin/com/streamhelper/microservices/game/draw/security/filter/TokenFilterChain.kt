package com.streamhelper.microservices.game.draw.security.filter

import com.streamhelper.microservices.game.draw.security.JwtProvider
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.security.SecurityException
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

/**
 * 토큰으로 부터 인증을 처리할 수 있는 필터입니다.
 * 토큰이 유효한 경우에는 인증이 처리합니다.
 * 데이터베이스 접근을 최소화 하기 위해, 이 단계에서는 토큰 정보로만 [Authentication] 을 구성합니다.
 * 추후 사용자의 자세한 정보가 필요한 경우에는 별도로 [AccountService] 또는 [AccountRepository] 통해서 조회해야 합니다.
 */
@Component
class TokenFilterChain(
    private val jwtProvider: JwtProvider
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        // 토큰 파싱 과정에서 발생하는 예외를 처리
        try {
            // 처리 가능한 요청의 경우에 토큰을 이용해서 Authentication 구성.
            jwtProvider.resolveToken(request)
                ?.let { jwtProvider.toAuthentication(it) }
                ?.also { SecurityContextHolder.getContext().authentication = it }
        } catch (e: Exception) {
            when (e) {
                // 토큰 자체를 인식할 수 없는 경우에는 인증 안됨 = 401
                is SecurityException, is MalformedJwtException
                -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "JWT error.")
                // 토큰 유효기간이 만료된 경우에는 인가 안됨 = 403
                is ExpiredJwtException
                -> response.sendError(HttpServletResponse.SC_FORBIDDEN, "JWT error.")
                // 그 외에는 알 수없는 오류로 취급하고 그냥 초기화.
                else -> SecurityContextHolder.clearContext()
            }
        }
        // 다음 필터 수행.
        filterChain.doFilter(request, response)
    }

}