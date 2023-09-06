package com.streamhelper.microservices.game.draw.security.filter

import com.streamhelper.microservices.game.draw.model.business.security.AccountAuthentication
import com.streamhelper.microservices.game.draw.security.filter.DevelopFilterChain.Companion.HEADER_NAME
import com.streamhelper.microservices.game.draw.service.AccountService
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.context.annotation.Profile
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.cors.CorsUtils
import org.springframework.web.filter.OncePerRequestFilter

/**
 * 개발환경의 경우, 개발 편의를 위해 헤더의 값으로 사용자를 인증 할 수 있는 필터입니다.
 * [HEADER_NAME] 으로 제공된 사용자 이름으로 사용자를 특정합니다.
 * [Profile] 어노테이션으로 제공환경을 결정합니다.
 */
@Component
@Profile("default", "development")
class DevelopFilterChain(
    private val accountService: AccountService
) : OncePerRequestFilter() {

    companion object {
        private const val HEADER_NAME = "X-DEV-ACCOUNT-EMAIL"
    }

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        if (CorsUtils.isPreFlightRequest(request)) {
            filterChain.doFilter(request, response)
            return
        }
        SecurityContextHolder.getContext().authentication = request
            .let { it.getHeader(HEADER_NAME)?.trim() }
            .takeUnless { it == null }
            ?.takeUnless { it.isBlank() }
            ?.let { accountService.findAccountForPrincipal(it) }
            ?.let { AccountAuthentication(it) }
//            ?.apply { isAuthenticated = true }
        filterChain.doFilter(request, response)
    }

}
